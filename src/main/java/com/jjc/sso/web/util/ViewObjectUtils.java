/**  
 * Project Name:yunxin-sso  
 * File Name:ViewObjectUtils.java  
 * Package Name:com.jjc.sso.web.util
 * Date:2018年4月10日上午9:42:41  
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.  
 *  
*/  
package com.jjc.sso.web.util;

import org.apache.commons.lang.StringUtils;

import com.jjc.sso.vo.LoginPortalViewObject;

/**  
 * ClassName: ViewObjectUtils <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason: TODO ADD REASON(可选). <br/>  
 * date: 2018年4月10日 上午9:42:41 <br/>  
 * @author Administrator  
 * @version   
 * @since JDK 1.7  
 */
public class ViewObjectUtils {
	
	private static final String RUHEXIU_DOMAIN = "ruhexiu.com";
	/**
	 * 根据页面请求封装页面对象
	 * @author Administrator  
	 * @param returnUrl
	 * @return  
	 * @since JDK 1.7
	 */
	public static LoginPortalViewObject getLoginPortalViewObject(String returnURL){
		LoginPortalViewObject vo = null;
		if(StringUtils.isNotEmpty(returnURL) && returnURL.contains(RUHEXIU_DOMAIN)){
			vo = new LoginPortalViewObject();
			vo.setPageTitle("如何秀");
			vo.setLogHeaderTitle("登录如何秀");
			vo.setRegHeaderTitle("注册如何秀账号");
			vo.setPageLogo("rhx-logo");
		}
		return vo;
	}
}
  
