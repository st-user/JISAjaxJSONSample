package com.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingJsonFactory;

/**
 * {@link JsonParser},{@link JsonGenerator}生成時に必要に応じてエンコーディングを指定できるように{@link MappingJsonFactory}を拡張したクラスです。
 * {@link JsonParser},{@link JsonGenerator}のエンコーディングは以下の通りに選択されます。
 * <ul>
 * <li>
 * {@link JsonParser}:
 * リクエストのContent-Typeヘッダーフィールドが「text/plain」の場合、同ヘッダーフィールドのcharset。
 * それ以外の場合{@link InputStream}のバイトストリームから自動判定されます。</li>
 * <li>
 * {@link JsonGenerator}:
 * リクエストのContent-Typeヘッダーフィールドが「text/plain」となる場合、
 * 同ヘッダーフィールドのcharset。それ以外の場合{@link JsonEncoding}に合致するものが選択されます。
 * </li>
 * </ul>
 */
@SuppressWarnings("serial")
public class ArbitrarilyEncodingMappingJsonFactory extends MappingJsonFactory {

	public ArbitrarilyEncodingMappingJsonFactory() {
		super();
	}

	@Override
	public JsonParser createParser(InputStream in) throws IOException, JsonParseException {
		Charset charset = JSONCharsetAccessor.getRequestCharsetAttribute();
		if (charset != null) {
			InputStreamReader isr = new InputStreamReader(in, charset);
			return super.createParser(isr);
		}
		return super.createParser(in);
	}

	@Override
	public JsonGenerator createGenerator(OutputStream out, JsonEncoding enc) throws IOException {
		Charset charset = JSONCharsetAccessor.getResponseCharsetAttribute();
		if (charset != null) {
			OutputStreamWriter osw = new OutputStreamWriter(out, charset);
			return super.createGenerator(osw);
		}
		return super.createGenerator(out, enc);
	}

}
