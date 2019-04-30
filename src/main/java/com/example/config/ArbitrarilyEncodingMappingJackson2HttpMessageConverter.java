package com.example.config;

import java.nio.charset.Charset;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * レスポンスのContent-Typeヘッダーフィールドのcharsetを指定できるように{@link MappingJackson2HttpMessageConverter}を拡張したクラスです。
 * <p>
 * 以下の順でレスポンスのContent-Typeヘッダーフィールドのcharsetが選択されます。
 * <ul>
 * <li>個々のコントローラークラスで設定したContent-Typeヘッダーフィールドのcharset</li>
 * <li>リクエストのAcceptヘッダーフィールドにて指定されたcharset</li>
 * <li>UTF-8</li>
 * </ul>
 *
 */
public class ArbitrarilyEncodingMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

	public ArbitrarilyEncodingMappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
		super(objectMapper);
	}

	@Override
	public Charset getDefaultCharset() {
		Charset charset = JSONCharsetAccessor.getResponseCharsetAttribute();
		if (charset != null) {
			return charset;
		}
		return super.getDefaultCharset();
	}

	@Override
	protected JsonEncoding getJsonEncoding(MediaType contentType) {
		if (contentType != null && contentType.getCharset() != null) {
			JSONCharsetAccessor.setResponseCharsetAttribute(contentType.getCharset());
		}
		return super.getJsonEncoding(contentType);
	}

}
