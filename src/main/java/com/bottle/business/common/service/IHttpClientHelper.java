package com.bottle.business.common.service;

import com.alibaba.fastjson.JSONObject;

public interface IHttpClientHelper {
	JSONObject postJSON(String url, final JSONObject json);
	JSONObject getJSON(String url);
}
