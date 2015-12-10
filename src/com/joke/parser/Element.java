package com.joke.parser;

import java.util.HashMap;
import java.util.Map;

public class Element {
	private String name;

	private String value;

	private Map<String, String> attributes;

	public Element(String name) {
		this.name = name;
	}

	public static Element parseSelfCloseElement(String raw) {
		String elementLine = null;
		if (raw.startsWith("?") && raw.contains("?>")) {
			int lastIndex = raw.lastIndexOf("?>");
			elementLine = raw.substring(0, lastIndex + 2);
			elementLine = elementLine.replaceFirst("\\?", "").replaceFirst("\\?>", "").trim();
		} else if (raw.contains("/>")) {
			int lastIndex = raw.lastIndexOf("/>");
			elementLine = raw.substring(0, lastIndex + 2);
			elementLine = elementLine.replaceFirst("/>", "").trim();
		} else {
			System.out.println(raw);
			throw new RuntimeException("????");
		}
		return makeElementWithAttributes(elementLine);
	}

	public static Element parseOpenElement(String raw) {
		String[] elementPart = raw.split(">");
		Element element = makeElementWithAttributes(elementPart[0]);
		if (elementPart.length == 2) {
			element.setValue(elementPart[1]);
		}
		return element;
	}

	private static Element makeElementWithAttributes(String elementLine) {
		String[] attributes = elementLine.split(" ");
		Element element = new Element(attributes[0]);
		for(int i = 1; i < attributes.length; i++) {
			String[] keyValue = attributes[i].split("=");
			String key = keyValue[0];
			if (keyValue[1].length() > 2) {
				String value = keyValue[1].substring(1, keyValue[1].length() - 2);
				element.addAttribute(key, value);
			} else {
				element.addAttribute(key, keyValue[1]);
			}
		}
		return element;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(String key, String value) {
		if (attributes == null) {
			attributes = new HashMap<>();
		}
		attributes.put(key, value);
	}
}
