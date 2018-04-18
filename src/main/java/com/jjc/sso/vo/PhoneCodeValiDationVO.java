package com.jjc.sso.vo;

import java.io.Serializable;
import java.util.Date;

/**
 *用于注册手机号验证
 */
public class PhoneCodeValiDationVO implements Serializable {
	
	

    /**
	 * 
	 */
	private static final long serialVersionUID = -8194097171401989348L;
	private String code;
    private Date codeTime;


    public PhoneCodeValiDationVO(String code, Date codeTime) {
        this.code = code;
        this.codeTime = codeTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCodeTime() {
        return codeTime;
    }

    public void setCodeTime(Date codeTime) {
        this.codeTime = codeTime;
    }
}
