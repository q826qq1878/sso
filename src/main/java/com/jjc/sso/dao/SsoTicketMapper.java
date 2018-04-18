package com.jjc.sso.dao;


import com.jjc.sso.po.SsoTicket;
import org.apache.ibatis.annotations.Param;

public interface SsoTicketMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SsoTicket record);

    int insertSelective(SsoTicket record);

    SsoTicket selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SsoTicket record);

    int updateByPrimaryKey(SsoTicket record);

    SsoTicket selectByPhoneCode(@Param("userId") Integer userId);

    SsoTicket selectByUUID(String  uuid);
    
}