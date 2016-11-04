package com.bottle.business.common;

import com.alibaba.fastjson.JSONObject;

public interface IHttpClientHelper {
	JSONObject postJSON(String url, final JSONObject json);
}
