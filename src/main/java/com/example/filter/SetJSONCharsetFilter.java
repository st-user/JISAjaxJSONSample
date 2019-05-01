package com.example.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.example.config.JSONCharsetAccessor;
import com.example.config.RequestParamAccessor;

/**
 * 同一リクエスト内後続処理でエンコーディングの情報にアクセスできるようにするためのフィルター
 *
 */
public class SetJSONCharsetFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		JSONCharsetAccessor.setRequestMediaTypetAttribute(httpRequest);

		httpRequest.getParameterNames().asIterator().forEachRemaining(name -> {
			RequestParamAccessor.setParameter(httpRequest, name, httpRequest.getParameter(name));
		});

		chain.doFilter(request, response);
	}

}
