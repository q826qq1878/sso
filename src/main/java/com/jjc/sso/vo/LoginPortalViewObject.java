/**  
 * Project Name:yunxin-sso  
 * File Name:LoginPortalViewObject.java  
 * Package Name:com.jjc.sso.vo
 * Date:2018年4月10日上午9:36:35  
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved.  
 *  
*/  
package com.jjc.sso.vo;
/**  
 * 登录页面根据请求来源展示不同的内容
 * date: 2018年4月10日 上午9:36:35 <br/>  
 *  
 * @author hugq  
 * @version   
 * @since JDK 1.7  
 */
public class LoginPortalViewObject {
	
	private String pageTitle;
	
	private String logHeaderTitle;

	private String pageLogo;
	
	private String regHeaderTitle;
	
	/**  
	 * pageTitle.  
	 *  
	 * @return  the pageTitle  
	 * @since   JDK 1.7  
	 */
	public String getPageTitle() {
		return pageTitle;
	}

	/**  
	 * pageTitle.  
	 *  
	 * @param   pageTitle    the pageTitle to set  
	 * @since   JDK 1.7  
	 */
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	/**  
	 * logHeaderTitle.  
	 *  
	 * @return  the logHeaderTitle  
	 * @since   JDK 1.7  
	 */
	public String getLogHeaderTitle() {
		return logHeaderTitle;
	}

	/**  
	 * logHeaderTitle.  
	 *  
	 * @param   logHeaderTitle    the logHeaderTitle to set  
	 * @since   JDK 1.7  
	 */
	public void setLogHeaderTitle(String logHeaderTitle) {
		this.logHeaderTitle = logHeaderTitle;
	}

	/**  
	 * pageLogo.  
	 *  
	 * @return  the pageLogo  
	 * @since   JDK 1.7  
	 */
	public String getPageLogo() {
		return pageLogo;
	}

	/**  
	 * pageLogo.  
	 *  
	 * @param   pageLogo    the pageLogo to set  
	 * @since   JDK 1.7  
	 */
	public void setPageLogo(String pageLogo) {
		this.pageLogo = pageLogo;
	}

	/**  
	 * regHeaderTitle.  
	 *  
	 * @return  the regHeaderTitle  
	 * @since   JDK 1.7  
	 */
	public String getRegHeaderTitle() {
		return regHeaderTitle;
	}

	/**  
	 * regHeaderTitle.  
	 *  
	 * @param   regHeaderTitle    the regHeaderTitle to set  
	 * @since   JDK 1.7  
	 */
	public void setRegHeaderTitle(String regHeaderTitle) {
		this.regHeaderTitle = regHeaderTitle;
	}
}