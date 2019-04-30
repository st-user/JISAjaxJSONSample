package com.example.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.config.data.Param;
import com.example.config.data.Result;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/servlet/jis/showResult")
@SuppressWarnings("serial")
public class JISServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setCharacterEncoding(parseToCharset(req.getHeader("Accept")).name());
		resp.setContentType("application/json");

		try (
				Reader requestBodyReader = req.getReader(); 
				Writer responseBodyWriter = resp.getWriter();
				) {

			ObjectMapper objectMapper = new ObjectMapper();
			Param param = objectMapper.readValue(requestBodyReader, Param.class);
			System.out.println(param);

			Result result = new Result();
			result.setResult1(100);
			result.setResult2("ハローワールド");
			objectMapper.writeValue(responseBodyWriter, result);
		}

	}

	private Charset parseToCharset(String headeField) {

		Charset defaultCharset = Charset.forName("UTF-8");
		if (headeField == null) {
			return defaultCharset;
		}

		String[] splitByCharset = headeField.split("charset\\s*=");
		if (splitByCharset.length <= 1) {
			return defaultCharset;
		}

		try {
			return Charset.forName(splitByCharset[1].trim());
		} catch (IllegalCharsetNameException e) {
			return defaultCharset;
		}

	}
}
