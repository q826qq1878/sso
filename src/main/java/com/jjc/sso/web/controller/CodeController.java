package com.jjc.sso.web.controller;

import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.jjc.sso.common.constants.UserConstants;
import com.jjc.sso.web.util.VerifyCodeUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * 验证码
 * @author Administrator
 * v1.0
 */

@Controller
public class CodeController {
	private Logger logger = Logger.getLogger(getClass());
	/**
	 * 默认生成图形二维码（128*41）
	 * @param request
	 * @param response
	 */
	@Login(action = Action.Skip)
	@RequestMapping(value = "/code" ,method = RequestMethod.GET)
	public void createCodeImg(HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/jpeg");  
          
        //生成随机字串  
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //存入会话session  
        HttpSession session = request.getSession(true);  
        session.setAttribute(UserConstants.LOGIN_CODE, verifyCode.toLowerCase());

        //生成图片  
        int w = 128, h = 41;
        try {
			VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("生成验证码异常");
		}  
	}
}
