package com.joke.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DomParser implements DocumentParser {
	//전부 메모리에 읽어서 객체로 만들어놓고 처리하는듯
	//트리를 미리 만드는듯 -> 트리를 따라가면서 필요한 정보를 얻거나 출력

	public List<Node> findElements(File file, String elementName) {
		Node document = parse(file);
		return findElements(document, elementName);
	}

	private List<Node> findElements(Node document, String elementName) {
		List<Node> result = new ArrayList<>();
		if (elementName.equals(document.getName())) {
			result.add(document);
			return result;
		}

		if (document.hasChildren()) {
			for(Node child : document.getChildren()) {
				result.addAll(findElements(child, elementName));
			}
		}
		return result;
	}

	private Node parse(File file) {
		Stack<Node> nodes = new Stack<>();
		nodes.push(new Node("root"));

		try(Scanner scanner = new Scanner(file)) {
			scanner.useDelimiter("<");
			while(scanner.hasNext()) {
				String elementLine = scanner.next();
				if (elementLine.isEmpty()) {
					continue;
				}

				if (isSelfCloseElement(elementLine)) {
					Element element = Element.parseSelfCloseElement(elementLine);
					Node child = new Node(element);
					nodes.peek().addChild(child);
				} else if (isCloseElement(elementLine)) {
					String elementName = elementLine.replaceFirst("/", "").replaceFirst(">", "").trim();
					Node node = nodes.pop();
					if (!node.getName().equals(elementName)) {
						System.out.println(elementLine);
						throw new RuntimeException("Invalid XML file");
					}
				} else if (isOpenElement(elementLine)) {
					Element element = Element.parseOpenElement(elementLine);
					Node child = new Node(element);
					nodes.peek().addChild(child);
					nodes.push(child);
				} else {
					//???
					System.out.println("I don't know... ??????");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		return nodes.pop();
	}

	private boolean isSelfCloseElement(String element) {
		return (element.startsWith("?") && element.contains("?>")) || element.contains("/>");
	}

	private boolean isOpenElement(String element) {
		return element.contains(">");
	}

	private boolean isCloseElement(String element) {
		return element.startsWith("/") && element.contains(">");
	}

	public static class Node {
		private List<Node> children;

		private Element element;

		public Node(String name) {
			this.element = new Element(name);
		}

		public Node(Element element) {
			this.element = element;
		}

		public List<Node> getChildren() {
			return children;
		}

		public void setChildren(List<Node> children) {
			this.children = children;
		}

		public boolean hasChildren() {
			return children != null && !children.isEmpty();
		}

		public void addChild(Node node) {
			if (children == null) {
				children = new ArrayList<>();
			}
			children.add(node);
		}

		public void setValue(String value) {
			element.setValue(value);
		}

		public String getName() {
			return element.getName();
		}
	}

	public static class Element {
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
}
