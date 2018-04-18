
/**
 * Copyright ? Jan 10, 2013 hc360.com.Co.,Ltd
 * transaction 1:30:46 PM
 * Version TODO
 * All right reserved.
 *
 */

package com.jjc.sso.common.constants;


/**
 * 类描述：交易用户常量类（用户身份、会员类型...）
 * @project transaction
 * @author huguoqing 
 * @version 1.0
 * @date Jan 16, 2013 4:44:54 PM
 */

public interface UserConstants {
    public static final String LOGIN_CODE = "LOGIN_CODE";//验证码

    public static final String REG_PHONE_CODE = "REG_PHONE_CODE";//手机验证码
    public static final int REG_PHONE_CODE_TIME = 15;//手机验证码有效时间

    public static final String LOGIN_USER = "LOGIN_USER";//登录用户
    public static final String LOGIN_USER_SALT = "DSFWETFGSDGDFHSDFGSD";//登录用户加密的salt
    public static final String LOGIN_USER_RESOURCES = "USERRESOURCE";//
    public static final String CURRENT_USER_ROLES = "ROLES";//
    public static final String CURRENT_USER_RESOURCES = "RESOURCES";//
    public static final Object CURRENT_USER_ROLES_STRING= "ROLESTRING";
}
