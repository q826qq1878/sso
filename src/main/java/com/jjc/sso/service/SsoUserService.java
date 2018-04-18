package com.jjc.sso.service;


import com.jjc.sso.po.SsoUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface SsoUserService {
	SsoUser findByName(String loginId);
	SsoUser findByEmail(String email);

	String insert(SsoUser ssoUser) throws  Exception;

	SsoUser findById(Integer Id);

	SsoUser findByPhone(String phone);

	String phoneSms(String phone,HttpSession session);

	String findPhoneSms(SsoUser ssoUser) throws  Exception;

	String PhoneCode(SsoUser bean,String code) throws Exception ;

	int update(SsoUser ssoUser);

	String sendMail(String ReturnURL , SsoUser user,HttpServletRequest request) throws Exception ;

	String checkEmailCode(String uuid) throws Exception;

	String checkEmailCodeAndUpdatePass(String uuid,String pass) throws Exception;

}
