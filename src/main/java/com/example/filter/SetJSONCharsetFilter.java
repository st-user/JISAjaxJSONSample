package com.example.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.config.JSONCharsetAccessor;

/**
 * 同一リクエスト内後続処理でエンコーディングの情報にアクセスできるようにするためのフィルター
 *
 */
public class SetJSONCharsetFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		JSONCharsetAccessor.setRequestCharsetAttribute(httpRequest);
		JSONCharsetAccessor.setResponseCharsetAttribute(httpRequest);

		httpRequest.setCharacterEncoding("ISO-8859-1");
		httpResponse.setCharacterEncoding("ISO-8859-1");

		chain.doFilter(request, response);
	}

}
