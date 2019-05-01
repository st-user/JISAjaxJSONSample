package com.example.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 以下２つの目的のために{@link MappingJackson2HttpMessageConverter}を拡張したクラスです。
 * <ul>
 * <li>
 * リクエストのContent-Typeヘッダーフィールドが「text/plain」かつ、
 * リクエストパラメータに「textRequestType=json」の指定がある場合、
 * リクエストボディをJSONのパーサーでパースする
 * </li>
 * <li>
 * レスポンスのContent-Typeヘッダーフィールドが「text/plain」となる場合に、
 * 同フィールドのcharsetでエンコードされたJSONがレスポンスボディに書き出されるようにする
 * </li>
 * </ul>
 *
 */
public class ArbitrarilyEncodingMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

	public ArbitrarilyEncodingMappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
		super(objectMapper);
		List<MediaType> supportedMediaTypes = new ArrayList<>(getSupportedMediaTypes());
		supportedMediaTypes.add(MediaType.TEXT_PLAIN);
		setSupportedMediaTypes(supportedMediaTypes);
	}

	@Override
	protected boolean canRead(MediaType mediaType) {
		if (MediaType.TEXT_PLAIN.includes(mediaType)) {
			if ("json".equalsIgnoreCase(RequestParamAccessor.getParameter("textRequestType"))) {
				return true;
			}
		}
		return super.canRead(mediaType);
	}

	@Override
	protected boolean canWrite(MediaType mediaType) {
		if (MediaType.TEXT_PLAIN.includes(mediaType)) {
			if ("json".equalsIgnoreCase(RequestParamAccessor.getParameter("textResponseType"))) {
				return true;
			}
		}
		return super.canWrite(mediaType);
	}

	@Override
	protected JsonEncoding getJsonEncoding(MediaType contentType) {
		if (contentType != null) {
			JSONCharsetAccessor.setResponseMediaTypeAttribute(contentType);
		}
		return super.getJsonEncoding(contentType);
	}

}
