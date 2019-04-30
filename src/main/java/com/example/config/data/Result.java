package com.example.config.data;

/**
 * JSONに変換してレスポンスボディに書き込むクラス
 *
 */
public class Result {

	private int result1;
	private String result2;

	public int getResult1() {
		return result1;
	}

	public void setResult1(int result1) {
		this.result1 = result1;
	}

	public String getResult2() {
		return result2;
	}

	public void setResult2(String result2) {
		this.result2 = result2;
	}

	@Override
	public String toString() {
		return "Result [result1=" + result1 + ", result2=" + result2 + "]";
	}

}