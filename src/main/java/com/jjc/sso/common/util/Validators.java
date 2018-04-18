/**
 * Copyright(c) 2000-2013 HC360.COM, All Rights Reserved.
 * Project: tradedb 
 * Author: dixingxing
 * Createdate: 下午6:37:04
 * Version: 1.0
 *
 */

package com.jjc.sso.common.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * <p>
 * 对<a href = "https://github.com/hibernate/hibernate-validator">hibernate validator</a> 
 * 的validator进行简单封装，简化使用。
 * <a href="http://docs.jboss.org/hibernate/validator/4.0.1/reference/en/html_single/">官方手册</a>
 * </p>
 * 
 * @project tradedb
 * @author dixingxing
 * @version 1.0
 * @date 2013-1-16 下午6:37:04
 */

public class Validators {
	private final static Validator validator;

	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	/**
	 * 检查对象的合法性。对应 {@link Validator#validate(Object, Class...)}
	 * 
	 * @author dixingxing
	 * @version 1.0
	 * @date 2013-1-16 下午7:13:32
	 * @param obj
	 * @param groups
	 * @return Set<ConstraintViolation<T>>
	 */
	public static <T> Set<ConstraintViolation<T>> validate(T obj, Class<?>... groups) {
		return validator.validate(obj, groups);
	}

}
