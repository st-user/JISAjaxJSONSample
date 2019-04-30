package com.example.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.data.Param;
import com.example.data.Result;

@Controller
@RequestMapping("/jis")
public class JISController {

	@ResponseBody
	@RequestMapping("showResult")
	public Result showResult(@RequestBody Param param, HttpServletResponse res) {
		System.out.println(param);
		Result result = new Result();
		result.setResult1(100);
		result.setResult2("ハローワールド");
		return result;
	}
}
