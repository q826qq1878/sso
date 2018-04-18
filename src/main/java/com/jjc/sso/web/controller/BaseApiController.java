package com.jjc.sso.web.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName: BaseApiController
 * @Description: API控制层父类 提供公共的方法
 * @author Qawine
 * @version v1.0
 * @date 2017年3月4日 下午12:34:39
 *
 */
public class BaseApiController {
	
	/**
	 * 操作成功，正确返回的信息
	 * @version v1.0
	 * @return
	 */
	public Object operateSuccess(Object obj) {
		Map<String, Object> map = this.createMap();
		map.put("ret", "200");
		map.put("msg", "success");
		map.put("content", obj);
		return map;
	}
	/**
	 * 操作失败，程序内部问题
	 * @version v1.0
	 * @return
	 */
	public Object operateError(String message) {
		Map<String, Object> map = this.createMap();
		map.put("ret", "404");
		map.put("msg", message);
		return map;
	}
	/**
	 * 操作提示，返回给前台提示信息
	 * @version v1.0
	 * @return
	 */
	public Object operateTip(String message) {
		Map<String, Object> map = this.createMap();
		map.put("ret", "300");
		map.put("msg", message);
		return map;
	}
	/**
	 * 通用返回json数据  
	 * @param status 状态码
	 * @param msg 提示信息
	 * @param obj 业务信息
	 * @return
	 */
	public Object operateUnity(String status, String msg, Object obj) {
		Map<String, Object> map = this.createMap();
		map.put("ret", status);
		map.put("msg", msg);
		map.put("content", obj);
		return map;
	}
	public Map<String, Object> createMap(){
		return new HashMap<String, Object>();
	}
}
