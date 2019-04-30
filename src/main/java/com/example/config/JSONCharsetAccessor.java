package com.example.config;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * リクエストボディ、レスポンスボディのエンコーディングを取得するために使用するクラス
 * 
 *
 */
public class JSONCharsetAccessor {

	private static final String REQUEST_ATTRIBUTE_KEY = JSONCharsetAccessor.class + "json.charset.request";
	private static final String RESPONSE_ATTRIBUTE_KEY = JSONCharsetAccessor.class + "json.charset.response";

	/**
	 * リクエスト属性(リクエストボディ用)にContent-Typeヘッダーフィールドのcharsetに指定されたエンコーディングをセットします
	 * {@link #getRequestCharsetAttribute()}を使用することで同一リクエスト内の後続処理でエンコーディングが取得できます。
	 * 
	 * @see JSONCharsetAccessor#getRequestCharsetAttribute()
	 * @param request
	 */
	public static void setRequestCharsetAttribute(HttpServletRequest request) {
		request.setAttribute(REQUEST_ATTRIBUTE_KEY, parseMediaTypeToCharset(request.getContentType()));
	}

	/**
	 * リクエスト属性(レスポンスボディ用)にAcceptヘッダーフィールドのcharsetに指定されたエンコーディングをセットします。
	 * {@link #getResponseCharsetAttribute()}を使用することで同一リクエスト内の後続処理でエンコーディングが取得できます。
	 * 
	 * @see JSONCharsetAccessor#getResponseCharsetAttribute()
	 * @param request
	 */
	public static void setResponseCharsetAttribute(HttpServletRequest request) {
		Charset charset = parseMediaTypeToCharset(request.getHeader("Accept"));
		request.setAttribute(RESPONSE_ATTRIBUTE_KEY, charset);
	}

	/**
	 * リクエスト属性(レスポンスボディ用)に<code>charset</code>で指定されたエンコーディングをセットします。
	 * {@link #getResponseCharsetAttribute()}を使用することで同一リクエスト内の後続処理で同エンコーディングが取得できます。
	 * 
	 * @see JSONCharsetAccessor#getResponseCharsetAttribute()
	 * @param charset
	 */
	public static void setResponseCharsetAttribute(Charset charset) {
		if (charset != null) {
			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
			requestAttributes.setAttribute(RESPONSE_ATTRIBUTE_KEY, charset, RequestAttributes.SCOPE_REQUEST);
		}
	}

	/**
	 * リクエストのContent-Typeヘッダーフィールドのcharsetに指定されたエンコーディングを取得します。
	 * 
	 * @see #setRequestCharsetAttribute(HttpServletRequest)
	 */
	public static Charset getRequestCharsetAttribute() {
		return getCharsetAttribute(REQUEST_ATTRIBUTE_KEY);
	}

	/**
	 * リクエストのAcceptヘッダーフィールドのcharsetに指定されたエンコーディングを取得します。
	 * 
	 * @see #setResponseCharsetAttribute(Charset)
	 * @see #setResponseCharsetAttribute(HttpServletRequest)
	 * @return
	 */
	public static Charset getResponseCharsetAttribute() {
		return getCharsetAttribute(RESPONSE_ATTRIBUTE_KEY);
	}

	private static Charset parseMediaTypeToCharset(String contentType) {
		try {
			MediaType mediaType = MediaType.parseMediaType(contentType);
			return mediaType.getCharset();
		} catch (InvalidMediaTypeException e) {
			return Charset.forName("UTF-8");
		}
	}

	private static Charset getCharsetAttribute(String key) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		return (Charset) requestAttributes.getAttribute(key, RequestAttributes.SCOPE_REQUEST);
	}
}
