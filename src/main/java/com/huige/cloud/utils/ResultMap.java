package com.huige.cloud.utils;

import java.util.HashMap;

/**
 *@Author xiezh
 *@Description 通用请求响应结构
 *@Date 2018/4/2 9:28
 */
@SuppressWarnings("serial")
public class ResultMap extends HashMap<String, Object> {

	public ResultMap() {

	}

	public ResultMap success() {
		this.put("success", true);
		return this;
	}

	public ResultMap fail() {
		this.put("success", false);
		return this;
	}

	public ResultMap info(String info) {
		this.put("info", info);
		return this;
	}

	public ResultMap msg(String msg) {
		this.put("message", msg);
		return this;
	}

	public ResultMap data(Object obj) {
		this.put("data", obj);
		return this;
	}

	public ResultMap total(int total) {
		this.put("total", total);
		return this;
	}
}
