package com.jjc.sso.dao;


import com.jjc.sso.po.SsoUser;

public interface SsoUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SsoUser record);

    int insertSelective(SsoUser record);

    SsoUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SsoUser record);

    int updateByPrimaryKey(SsoUser record);

    SsoUser findByName(String loginId);

    SsoUser findByEmail(String email);

    SsoUser findByPhone(String phone);





}