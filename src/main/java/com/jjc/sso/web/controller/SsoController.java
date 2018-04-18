package com.jjc.sso.web.controller;

import com.baomidou.kisso.*;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.common.SSOProperties;
import com.baomidou.kisso.common.util.HttpUtil;
import com.baomidou.kisso.web.waf.request.WafRequestWrapper;
import com.jjc.sso.po.SsoUser;
import com.jjc.sso.service.SsoUserService;
import com.jjc.sso.web.util.AjaxHelper;
import com.jjc.sso.web.util.Md5Util;
import com.jjc.sso.web.util.ViewObjectUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Created by jiangjiacheng on 2017/7/13.
 */
@Controller
public class SsoController extends  BaseApiController{
    private static Logger log = Logger.getLogger(SsoController.class);
    @Autowired
    private SsoUserService ssoUserService;

    protected String redirectTo(String url) {
        StringBuffer rto = new StringBuffer("redirect:");
        rto.append(url);
        return rto.toString();
    }
    /**
     * 登录 （注解跳过权限验证）
     */
    @Login(action = Action.Skip)
    @RequestMapping("/login")
    public String login(RedirectAttributesModelMap modelMap, Model model, HttpServletRequest request, HttpServletResponse response) {
        log.info("登录 （注解跳过权限验证）");
        String returnUrl = request.getParameter(SSOConfig.getInstance().getParamReturl());
        Token token = SSOHelper.getToken(request);
        
        if (token == null) {
            /**
             * 正常登录 需要过滤sql及脚本注入
             */
            WafRequestWrapper wr = new WafRequestWrapper(request);
            String loginUser = wr.getParameter("loginUser");
            String loginPass = wr.getParameter("loginPass");
            //定义校验失败标识符
            boolean falg = false;
            //定义校验失败的字符串
            String falgStr = "";
            if (loginUser != null && !"".equals(loginUser)) {
                SsoUser user = ssoUserService.findByName(loginUser);
                if(user != null){
                    if(user.getPassword().equals(Md5Util.MD5Encode(loginPass))){
                        /*
                         * 设置登录 Cookie
                         * 最后一个参数 true 时添加 cookie 同时销毁当前 JSESSIONID 创建信任的 JSESSIONID
                         */
                        SSOToken st = new SSOToken(request, user.getId()+"");
//                        st.setData("jjc看源码哦");
                        //记住密码就设置
                        if(request.getParameter("checkbox").equals("true")){
                        	SSOConfig.getInstance().setCookieMaxage(604800);
                        }
                        SSOHelper.setSSOCookie(request, response, st, true);

                    }else{//证明密码不匹配
                        falg = true;
                        falgStr = "密码不正确";
                    }
                }else{//证明没有用户名
                    falg = true;
                    falgStr = "用户名不正确";
                }

            } else {
                falg = true;
                falgStr = "";
            }

            if(falg){//校验失败。跳转回登录页
                model.addAttribute("msg", falgStr);
                if(request.getParameter("falg")!=null){
                	if(request.getParameter("falg").equals("")){
                		model.addAttribute("falg", 0);
                	}else {
                		Integer parameter = Integer.parseInt(request.getParameter("falg"));
                		Integer integer = parameter+1;
                		model.addAttribute("falg", integer);
                	}
                }
                if (StringUtils.isNotEmpty(returnUrl)) {
                    model.addAttribute("ReturnURL", returnUrl);
                    model.addAttribute("portal",ViewObjectUtils.getLoginPortalViewObject(returnUrl));
                }
                model.addAttribute("loginUser", loginUser);
                model.addAttribute("loginPass", loginPass);
                return "login";
            }else{//校验成功，重定向到业务系统
                // 重定向到指定地址 returnUrl
                if (StringUtils.isEmpty(returnUrl)) {
                    returnUrl = "/index.html";
                } else {
                    returnUrl = HttpUtil.decodeURL(returnUrl);
                }
                return redirectTo(returnUrl);
            }
        } else {
            if (StringUtils.isEmpty(returnUrl)) {
                returnUrl = "/index.html";
            }
            return redirectTo(returnUrl);
        }
    }

    @ResponseBody
    @RequestMapping("/replylogin")
    public void replylogin(HttpServletRequest request, HttpServletResponse response) {
        log.info("SSO回复子系统");

        StringBuffer replyData = new StringBuffer();
        replyData.append(request.getParameter("callback")).append("({\"msg\":\"");
        Token token = SSOHelper.getToken(request);
        if (token != null) {
            String askData = request.getParameter("askData");
            if (askData != null && !"".equals(askData)) {
                /**
                 *
                 * 用户自定义配置获取
                 *
                 * <p>
                 * 由于不确定性，kisso 提倡，用户自己定义配置。
                 * </p>
                 *
                 */
                SSOProperties prop = SSOConfig.getSSOProperties();

                //下面开始验证票据，签名新的票据每一步都必须有。
                AuthToken at = SSOHelper.replyCiphertext(request, askData);
                if (at != null) {

                    //1、业务系统公钥验证签名合法性（此处要支持多个跨域端，取 authToken 的 app 名找到对应系统公钥验证签名）
                    at = at.verify(prop.get("sso.defined." + at.getApp() + "_public_key"));
                    if (at != null) {

                        //at.getUuid() 作为 key 设置 authToken 至分布式缓存中，然后 sso 系统二次验证
                        //at.setData(data); 设置自定义信息，当然你也可以直接 at.setData(token.jsonToken()); 把当前 SSOToken 传过去。

                        at.setUid(token.getUid());//设置绑定用户ID
                        at.setTime(token.getTime());//设置登录时间

                        //2、SSO 的私钥签名
                        at.sign(prop.get("sso.defined.sso_private_key"));

                        //3、生成回复密文票据
                        replyData.append(at.encryptAuthToken());
                    } else {
                        //非法签名, 可以重定向至无权限界面，自己处理
                        replyData.append("-2");
                    }
                } else {
                    //非法签名, 可以重定向至无权限界面，自己处理
                    replyData.append("-2");
                }
            }
        } else {
            // 未登录
            replyData.append("-1");
        }
        try {
            replyData.append("\"})");
            AjaxHelper.outPrint(response, replyData.toString(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/ssoHeader")
    public void ssoHeader(HttpServletRequest request, HttpServletResponse response) {
        log.info("SSO回复子系统 ssoHeader");
        StringBuffer replyData = new StringBuffer();
        replyData.append(request.getParameter("callback")).append("({\"msg\":\"");
        Token token = SSOHelper.getToken(request);
        if (token != null) {
            SsoUser ssoUser = ssoUserService.findById(Integer.parseInt(token.getUid()));
            replyData.append(ssoUser.getUsername());
        } else {
            replyData.append("-1");
        }
        try {
            replyData.append("\"})");
            AjaxHelper.outPrint(response, replyData.toString(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 统一退出，调用对外提供退出的所有接口
     */
    @RequestMapping("/logout")
    @ResponseBody
    public void logout(HttpServletRequest request,HttpServletResponse response) {
        log.info("SSO退出接口");
        SSOHelper.clearLogin(request, response);
        StringBuffer replyData = new StringBuffer();
        replyData.append(request.getParameter("callback")).append("({\"msg\":\"");
        replyData.append("200");
        try {
            replyData.append("\"})");
            AjaxHelper.outPrint(response, replyData.toString(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request,HttpServletResponse response) {
        return "index";
    }




}
