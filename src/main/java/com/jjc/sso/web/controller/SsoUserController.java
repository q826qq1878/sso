package com.jjc.sso.web.controller;

import com.jjc.sso.common.constants.UserConstants;
import com.jjc.sso.common.enums.ValidationTimeEnum;
import com.jjc.sso.po.SsoUser;
import com.jjc.sso.service.SsoUserService;
import com.jjc.sso.vo.PhoneCodeValiDationVO;
import com.jjc.sso.web.util.Md5Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 用户操作相关的控制层
 */
@Controller
public class SsoUserController extends  BaseApiController{

    private static Logger log = Logger.getLogger(SsoUserController.class);

    @Autowired
    private SsoUserService ssoUserService;

    //注册
    @RequestMapping("/register")
    @ResponseBody
    public Object register(String code ,  HttpServletRequest request , @RequestBody SsoUser bean) {
        log.info("注册方法");

        //认证手机验证码----开始
        HttpSession session = request.getSession();
        PhoneCodeValiDationVO phoneCodeValiDationVO = (PhoneCodeValiDationVO) session.getAttribute(UserConstants.REG_PHONE_CODE);
        String falgStr = null;
        if(null != phoneCodeValiDationVO){
            if(phoneCodeValiDationVO.getCode().equals(code)){
                Date createDate = new Date(phoneCodeValiDationVO.getCodeTime().getTime() + (1000 * 60 * ValidationTimeEnum.PHONE_VALIDATION_TIME.getValue()));
                Date nowDate = new Date();
                if(nowDate.getTime() > createDate.getTime()){//当前时间大于校验时间
                    falgStr = "验证码失效";
                }
            }else{
                falgStr = "手机验证码错误";
            }
        }else{
        	falgStr="请先获取验证码";
        }
        if(null  != falgStr){
            log.info(falgStr);
            return  this.operateError(falgStr);
        }
        //认证手机验证码----结束

        try {
            bean.setPassword(Md5Util.MD5Encode(bean.getPassword()));
            falgStr = ssoUserService.insert(bean);
        }catch (Exception e){
            log.error("注册相关异常信息",e);
            falgStr = e.getMessage();
        }
        return  retView(falgStr);
    }

    //检查验证码是否正确
    @RequestMapping("/checkCode")
    @ResponseBody
    public Object checkCode(String code ,HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionCode = (String) session.getAttribute(UserConstants.LOGIN_CODE);
        log.info("SSO检查验证码--checkCode"+"- user:"+code + "--session" +sessionCode);
        if(null != sessionCode && null != code ){
            if(code.equalsIgnoreCase(sessionCode)){
                log.info("成功");
                return  this.operateSuccess(null);
            }else{
                log.info("失败");
                return  this.operateError("验证码错误");
            }
        }else{
            log.info("验证码错误");
            return  this.operateError("验证码错误");
        }
    }


    //检查用户名是否重复
    @RequestMapping("/userNameCheck")
    @ResponseBody
    public Object userNameCheck(String userName) {
        log.info("SSO检查用户名是否重复--userNameCheck:"+userName);
        SsoUser ssoUser = null;
        ssoUser = ssoUserService.findByName(userName);
        if(null  == ssoUser){
            return  this.operateSuccess(null);
        }else{
            return  this.operateError("用户名重复");
        }
    }

    //检查邮箱是否重复
    @RequestMapping("/emailCheck")
    @ResponseBody
    public Object emailCheck(String email) {
        log.info("SSO检查邮箱名是否重复--emailCheck:"+email);

        SsoUser ssoUser = ssoUserService.findByEmail(email);
        if(null  == ssoUser){
            log.info("不重复");
            return  this.operateSuccess(null);
        }else{
            log.info("邮箱重复");
            return  this.operateError("邮箱重复");
        }
    }

    //检查发送重置密码邮件
    @RequestMapping("/sendMail")
    @ResponseBody
    public Object sendMail(String ReturnURL , String email, HttpServletRequest request) {
        log.info("发送重置密码的邮件--sendMail:"+email);

        SsoUser ssoUser = ssoUserService.findByEmail(email);
        String falgStr = "";
        try {
            falgStr = ssoUserService.sendMail(ReturnURL , ssoUser,request);
        }catch (Exception e){
            log.error("发送重置密码邮件相关异常",e);
            e.printStackTrace();
            falgStr = e.getMessage();
        }
        return  retView(falgStr);
    }

    //检查手机号是否重复
    @RequestMapping("/userPhoneCheck")
    @ResponseBody
    public Object userPhoneCheck(String userPhone) {
        log.info("SSO检查手机号是否重复--userPhone:"+userPhone);

        SsoUser ssoUser = null;
        String falgStr = "";
        ssoUser = ssoUserService.findByPhone(userPhone);
        if(null  == ssoUser){
            log.info("不重复");
            return  this.operateSuccess(null);
        }else{
            log.info("重复");
            return  this.operateError("手机号重复");
        }
    }

    //发送短信（注册的短信，存入Session）
    @RequestMapping("/phoneSms")
    @ResponseBody
    public Object phoneSms(String phone , HttpServletRequest request) {
        log.info("发送短信-注册-userPhone:" + phone);

        HttpSession session = request.getSession();
        String falgStr = "";
        falgStr = ssoUserService.phoneSms(phone,session);
        return  retView(falgStr);
    }


    //发送短信（忘记密码的短信，存入数据库）
    @RequestMapping("/findPhoneSms")
    @ResponseBody
    public Object findPhoneSms(String phone) {
        log.info("发送短信-忘记密码-findPhoneSms:" + phone);
        SsoUser ssoUser = ssoUserService.findByPhone(phone);
        String falgStr = "";
        try {
            falgStr = ssoUserService.findPhoneSms(ssoUser);
        }catch (Exception e){
            log.error("忘记密码发送短信相关异常信息",e);
            e.printStackTrace();
            falgStr = e.getMessage();
        }
        return  retView(falgStr);
    }

    //验证发送的验证码是否正确
    @RequestMapping("/findPhoneSmsCheck")
    @ResponseBody
    public Object findPhoneSmsCheck(String phone , String code) {
        log.info("验证发送的验证码是否正确-findPhoneSmsCheck:" + phone + " - code：" + code);

        SsoUser ssoUser = ssoUserService.findByPhone(phone);
        String falgStr = "";
        try {
            falgStr = ssoUserService.PhoneCode(ssoUser,code);
        }catch (Exception e){
            log.error("验证发送的验证码是否正确-findPhoneSmsCheck-相关异常信息",e);
            e.printStackTrace();
            falgStr = e.getMessage();
        }
        return  retView(falgStr);
    }
    //判断是否与旧密码相同
    @RequestMapping("/newPasssword")
    @ResponseBody
    public Object newPasssword(String phone,String pass){
    	if(   null == phone || "".equals(phone)){
            return  this.operateError("非法请求");
        }
    	log.info("手机号方式设置新密码判断新密码是否与旧密码相同" + phone + "- "+ "-pass："+pass);
    	String falgStr = "";
		try {
			SsoUser ssoUser = ssoUserService.findByPhone(phone);
			if(ssoUser.getPassword().equals(Md5Util.MD5Encode(pass))){
				falgStr="新密码不能与旧密码重复";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("手机号方式校验新密码-newPasssword异常信息",e);
            e.printStackTrace();
            falgStr = e.getMessage();
		}
		return retView(falgStr);
    	
    }
    //手机号方式-》设置新密码
    @RequestMapping("/updatePass")
    @ResponseBody
    public Object updatePass(String phone , String code, String pass) {
        if(   null == phone || "".equals(phone)  || null == code || "".equals(code)    ){
            return  this.operateError("非法请求");
        }
        log.info("手机号方式设置新密码-updatePass-" + phone + "- " + "code： "+ code + "-pass："+pass);

        SsoUser ssoUser = ssoUserService.findByPhone(phone);
        String falgStr = "";
        
        try {
            //再次认证手机号、验证码 -- 防止JS直接请求
            falgStr = ssoUserService.PhoneCode(ssoUser,code);
            if(ssoUser.getPassword().equals(Md5Util.MD5Encode(pass))){
            	falgStr = "新密码不能与旧密码重复!";
            }
            //开始修改密码
            if(falgStr == null){
                ssoUser.setPassword(Md5Util.MD5Encode(pass));
                int i = ssoUserService.update(ssoUser);
                if(i > 0 ){
                    falgStr = null;
                }else{
                    falgStr = "修改密码失败，稍后重试！";
                }
            }
        }catch (Exception e){
            log.error("手机号方式设置新密码-updatePass异常信息",e);
            e.printStackTrace();
            falgStr = e.getMessage();
        }
        return  retView(falgStr);
    }

    //找回密码页面跳转
    @RequestMapping("/findPsw")
    public String findPsw(String ReturnURL , Model model) {
        model.addAttribute("ReturnURL" , ReturnURL);
        return "find_psw";
    }

    //注册页面跳转
    @RequestMapping("/registerView")
    public String registerView(String ReturnURL , Model model) {
        model.addAttribute("ReturnURL" , ReturnURL);
        return "register";
    }



    //邮箱找回密码页面跳转
    @RequestMapping("/findMailPsw")
    public String findMailPsw(String ReturnURL , String uuid ,Model model) {
        log.info("邮箱找回密码页面跳转-findMailPsw:" + uuid);
        String falgStr = null;
        try {
            falgStr = ssoUserService.checkEmailCode(uuid);
        } catch (Exception e) {
            log.error("邮箱找回密码页面跳转相关异常",e);
            e.printStackTrace();
            falgStr = e.getMessage();
        }
        model.addAttribute("uuid",uuid);
        model.addAttribute("msg",falgStr);
        model.addAttribute("ReturnURL" , ReturnURL);
        return "find_mail_psw";
    }

    //邮箱方式-》设置新密码
    @RequestMapping("/updatePassByEmail")
    @ResponseBody
    public Object updatePassByEmail(String uuid ,  String pass) {
        log.info("邮箱方式-》设置新密码-updatePassByEmail:" + uuid + "-" + pass);

        String falgStr = "";
        try {
            //再次认证手机号、验证码 -- 防止JS直接请求
            falgStr = ssoUserService.checkEmailCodeAndUpdatePass(uuid,pass);
        }catch (Exception e){
            log.error("邮箱方式-》设置新密码-updatePassByEmail相关异常信息",e);
            e.printStackTrace();
            falgStr = e.getMessage();
        }
        return  retView(falgStr);
    }


    //打印LOG-返回消息辅助方法
    public Object retView(String falgStr){
        if(null  == falgStr){
            log.info("成功");
            return  this.operateSuccess(null);
        }else{
            log.info(falgStr);
            return  this.operateError(falgStr);
        }
    }


}
