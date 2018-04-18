package com.jjc.sso.service.impl;

import com.jjc.sso.common.constants.UserConstants;
import com.jjc.sso.common.enums.TicketTypeEnum;
import com.jjc.sso.common.enums.ValidationTimeEnum;
import com.jjc.sso.dao.SsoTicketMapper;
import com.jjc.sso.dao.SsoUserMapper;
import com.jjc.sso.po.SsoTicket;
import com.jjc.sso.po.SsoUser;
import com.jjc.sso.service.SsoUserService;
import com.jjc.sso.vo.PhoneCodeValiDationVO;
import com.jjc.sso.web.util.Md5Util;
import com.jjc.sso.web.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

@Service
public class SsoUserServiceImpl implements SsoUserService {
	@Resource
	private SsoUserMapper ssoUserMapper;
	@Autowired
	private SsoTicketMapper ssoTicketMapper;

	@Override
	public SsoUser findByName(String loginId) {
		return ssoUserMapper.findByName(loginId);
	}

	@Override
	public SsoUser findByEmail(String email) {
		return ssoUserMapper.findByEmail(email);
	}
	@Override
	public String insert(SsoUser ssoUser) throws  Exception {
		ssoUser.setCreateMan(ssoUser.getUsername());
		ssoUser.setCreateTime(new Date());
		ssoUser.setUpdateMan(ssoUser.getUsername());
		ssoUser.setUpdateTime(new Date());
		int i = ssoUserMapper.insertSelective(ssoUser);
		if(i  > 0 ){
			return  null;
		}else{
			throw  new Exception("注册失败");
		}
	}
	@Override
	public SsoUser findById(Integer Id) {
		// TODO Auto-generated method stub
		return ssoUserMapper.selectByPrimaryKey(Id);
	}
	@Override
	public SsoUser findByPhone(String phone) {
		return ssoUserMapper.findByPhone(phone);
	}

	@Override
	public String phoneSms(String phone, HttpSession session) {
		String code = "1111";
		Date  smsCreateTime = new Date();
		//在session中存放手机发送出去的手机验证码
		System.out.println("发送的短信验证码-"+code);
		PhoneCodeValiDationVO phoneCodeValiDationVO = new PhoneCodeValiDationVO(code,smsCreateTime);
		session.setAttribute(UserConstants.REG_PHONE_CODE, phoneCodeValiDationVO);
		return  null;
	}

	@Override
	public String findPhoneSms(SsoUser ssoUser) throws Exception {
		return  sendSmsCode(ssoUser.getId() , ssoUser.getMobile());
	}

	private String sendSmsCode (Integer userId , String phone) throws  Exception{

		SsoTicket ssoTicket = new SsoTicket();
		String code = RandomUtil.getFourRandom();
		String retStr;

		ssoTicket.setTicketType(TicketTypeEnum.PHONE.getValue());
		ssoTicket.setUserId(userId);
		ssoTicket.setCreateTime(new Date());
		ssoTicket.setValidateTime(ValidationTimeEnum.PHONE_VALIDATION_TIME.getValue());
		ssoTicket.setTicket(code);
		int i = ssoTicketMapper.insertSelective(ssoTicket);
		if(i > 0 ) {
			return null;
		}else{
			throw  new Exception("插入票据失败");
		}
	}

	@Override
	public String PhoneCode(SsoUser bean , String code) throws Exception {
		SsoTicket ssoTicket = ssoTicketMapper.selectByPhoneCode(bean.getId());
		if(null != ssoTicket){
			if(ssoTicket.getTicket().equals(code)){
				Date createDate = new Date(ssoTicket.getCreateTime().getTime() + (1000 * 60 * ssoTicket.getValidateTime()));
				Date nowDate = new Date();
				if(nowDate.getTime() > createDate.getTime()){//当前时间大于校验时间
					throw  new Exception("手机验证码失效");
				}
				return  null;
			}else{
				throw  new Exception("手机验证码错误");
			}
		}else{
			throw  new Exception("请重新发送验证码");
		}
	}

	@Override
	public int update(SsoUser ssoUser) {
		return ssoUserMapper.updateByPrimaryKeySelective(ssoUser);
	}

	@Override
	public String sendMail(String ReturnURL , SsoUser ssoUser,HttpServletRequest request) throws Exception {
		return null;
	}

	@Override
	public String checkEmailCode(String uuid) throws Exception {
		SsoTicket ssoTicket = ssoTicketMapper.selectByUUID(uuid);
		if(ssoTicket == null){
			throw new Exception("票据认证失败");
		}
		Integer validateTime = ssoTicket.getValidateTime();
		Date createDate = new Date(ssoTicket.getCreateTime().getTime() + (1000 * 60 * validateTime));
		Date nowDate = new Date();

		if(nowDate.getTime() > createDate.getTime()){//当前时间大于校验时间
			throw  new Exception("该链接已经失效，请重新找回密码");
		}else{
			return null;
		}
	}

	@Override
	public String checkEmailCodeAndUpdatePass(String uuid,String pass) throws Exception {
		SsoTicket ssoTicket = ssoTicketMapper.selectByUUID(uuid);
		if(ssoTicket == null){
			throw new Exception("票据认证失败");
		}
		Integer validateTime = ssoTicket.getValidateTime();
		Date createDate = new Date(ssoTicket.getCreateTime().getTime() + (1000 * 60 * validateTime));
		Date nowDate = new Date();

		if(nowDate.getTime() > createDate.getTime()){//当前时间大于校验时间
			throw  new Exception("该链接已经失效，请重新找回密码");
		}else{
			SsoUser ssoUser = ssoUserMapper.selectByPrimaryKey(ssoTicket.getUserId());
			if(ssoUser !=null){
//				ssoUser.setPassword(pass);
				ssoUser.setPassword(Md5Util.MD5Encode(pass));
				ssoUser.setUpdateTime(new Date());
				ssoUser.setUpdateMan(ssoUser.getUsername());
				int i = ssoUserMapper.updateByPrimaryKeySelective(ssoUser);
				if(i>0){
					return  null;
				}else{
					throw  new Exception("密码修改失败");
				}
			}else{
				throw  new Exception("无该用户信息");
			}
		}
	}


}
