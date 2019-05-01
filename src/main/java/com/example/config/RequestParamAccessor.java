package com.example.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class RequestParamAccessor {

	private static final String PRAM_MAP_KEY = RequestParamAccessor.class + ".param.map.key";

	public static void setParameter(HttpServletRequest req, String name, String value) {
		@SuppressWarnings("unchecked")
		Map<String, List<String>> requestParamMap = (Map<String, List<String>>) req.getAttribute(PRAM_MAP_KEY);
		if (requestParamMap == null) {
			requestParamMap = new HashMap<>();
			req.setAttribute(PRAM_MAP_KEY, requestParamMap);
		}
		requestParamMap.computeIfAbsent(name, k -> new ArrayList<>()).add(value);
	}

	public static String getParameter(String name) {
		List<String> parameters = getParameters(name);
		if (parameters.isEmpty()) {
			return null;
		}
		return parameters.get(0);
	}

	public static List<String> getParameters(String name) {
		Map<String, List<String>> parameterMap = getParameterMap();
		if (parameterMap == null) {
			return Collections.emptyList();
		}
		return parameterMap.getOrDefault(name, Collections.emptyList());
	}

	@SuppressWarnings("unchecked")
	private static Map<String, List<String>> getParameterMap() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		return (Map<String, List<String>>) attributes.getAttribute(PRAM_MAP_KEY, RequestAttributes.SCOPE_REQUEST);
	}
}
