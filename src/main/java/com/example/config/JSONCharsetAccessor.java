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

	private static final String REQUEST_ATTRIBUTE_KEY = JSONCharsetAccessor.class + "json.mediatype.request";
	private static final String RESPONSE_ATTRIBUTE_KEY = JSONCharsetAccessor.class + "json.mediatype.response";

	/**
	 * リクエスト属性(リクエストボディ用)にContent-Typeヘッダーフィールドで指定された{@link MediaType}をセットします。
	 * {@link #getRequestCharsetAttribute()}を使用することで同一リクエスト内の後続処理でエンコーディングが取得できます。
	 * 
	 * @see JSONCharsetAccessor#getRequestCharsetAttribute()
	 * @param request
	 */
	public static void setRequestMediaTypetAttribute(HttpServletRequest request) {
		MediaType mediaType = parseMediaType(request.getContentType());
		if (mediaType != null) {
			request.setAttribute(REQUEST_ATTRIBUTE_KEY, mediaType);
		}
	}

	private static MediaType parseMediaType(String contentType) {
		try {
			return MediaType.parseMediaType(contentType);
		} catch (InvalidMediaTypeException e) {
			return null;
		}
	}

	/**
	 * リクエスト属性(レスポンスボディ用)に<code>mediaType</code>で指定された{@link MediaType}をセットします。
	 * {@link #getResponseCharsetAttribute()}を使用することで同一リクエスト内の後続処理でエンコーディングが取得できます。
	 * 
	 * @see JSONCharsetAccessor#getResponseCharsetAttribute()
	 * @param mediaType
	 */
	public static void setResponseMediaTypeAttribute(MediaType mediaType) {
		if (mediaType != null) {
			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
			requestAttributes.setAttribute(RESPONSE_ATTRIBUTE_KEY, mediaType, RequestAttributes.SCOPE_REQUEST);
		}
	}

	/**
	 * リクエストのContent-TypeヘッダーフィールドのMediaTypeが「text/plain」の場合にcharsetに指定されたエンコーディングを取得します。
	 * 
	 * @see #setRequestMediaTypetAttribute(HttpServletRequest)
	 * @return MediaTypeが「text/plain」の場合にcharsetに指定されたエンコーディング。それ以外の場合null。
	 */
	public static Charset getRequestCharsetAttribute() {
		return getCharsetIfTextPlain(REQUEST_ATTRIBUTE_KEY);
	}

	/**
	 * レスポンスのContent-TypeヘッダーフィールドのMediaTypeが「text/plain」の場合にcharsetに指定されたエンコーディングを取得します。
	 * 
	 * @see #setResponseMediaTypeAttribute(MediaType)
	 * @return MediaTypeが「text/plain」の場合にcharsetに指定されたエンコーディング。それ以外の場合null。
	 */
	public static Charset getResponseCharsetAttribute() {
		return getCharsetIfTextPlain(RESPONSE_ATTRIBUTE_KEY);
	}

	private static Charset getCharsetIfTextPlain(String key) {
		MediaType mediaType = getAttribute(key);
		if (mediaType == null || !MediaType.TEXT_PLAIN.includes(mediaType)) {
			return null;
		}
		return mediaType.getCharset();
	}

	@SuppressWarnings("unchecked")
	private static <T> T getAttribute(String key) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		Object attr = requestAttributes.getAttribute(key, RequestAttributes.SCOPE_REQUEST);
		return (T) attr;
	}
}
