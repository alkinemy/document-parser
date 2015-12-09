package com.joke.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DomParser implements DocumentParser {
	//전부 메모리에 읽어서 객체로 만들어놓고 처리하는듯
	//트리를 미리 만드는듯 -> 트리를 따라가면서 필요한 정보를 얻거나 출력

	public Node findElement(File file, String elementName) {
		Node document = parse(file);
		return findElement(elementName);
	}

	private Node findElement(String elementName) {
		return null;
	}

	private Node parse(File file) {
		Stack<Node> nodes = new Stack<>();
		nodes.push(new Node("root"));

		try(Scanner scanner = new Scanner(file)) {
			scanner.useDelimiter("<");
			while(scanner.hasNext()) {
				String element = scanner.next();

				if (element.isEmpty()) {
					continue;
				}

				if (isSelfCloseElement(element)) {
					//create child
					//pop parent
					//add child
					//push parent
					//push child
				} else if (isOpenElement(element)) {
					//create node
					//if it has value, set value
					//push node
				} else if (isCloseElement(element)) {
					//pop node
				} else {
					//???
				}
				System.out.println(element);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean isSelfCloseElement(String element) {
		return (element.startsWith("?") && element.contains("?>")) || element.contains("/>");
	}

	private boolean isOpenElement(String element) {
		return element.contains(">");
	}

	private boolean isCloseElement(String element) {
		return element.startsWith("/") && element.endsWith(">");
	}

	public static class Node {
		private List<Node> children;
		private Element element;

		public Node(String name) {
			this.element = new Element(name);
		}

		public List<Node> getChildren() {
			return children;
		}

		public void setChildren(List<Node> children) {
			this.children = children;
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
	}

	public static class Element {
		private String name;

		private String value;

		private Map<String, String> attributes;

		public Element(String name) {
			this.name = name;
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
