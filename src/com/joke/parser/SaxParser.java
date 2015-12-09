package com.joke.parser;

public class SaxParser implements DocumentParser {
	//바로바로 파싱?
	//tag started event
	//tag ended event

	public static class SaxException extends Exception {
		public SaxException() {
			super();
		}
	}
}
