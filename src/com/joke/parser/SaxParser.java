package com.joke.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class SaxParser implements DocumentParser {
	//tag started event
	//바로바로 파싱?
	//tag ended event

	@Override public void printElements(File file, String parentElementName, List<String> childElementNames) {
		try (Scanner scanner = new Scanner(file)) {
			scanner.useDelimiter("<");

			boolean isParent = false;
			while(scanner.hasNext()) {
				String elementLine = scanner.next();
				if (elementLine.isEmpty()) {
					continue;
				}

				if (isSelfCloseElement(elementLine)) {
					continue;
				} else if (isCloseElement(elementLine)) {
					String elementName = elementLine.replaceFirst("/", "").replaceFirst(">", "").trim();
					if (parentElementName.equals(elementName)) {
						isParent = false;
					}
				} else if (isOpenElement(elementLine)) {
					Element element = Element.parseOpenElement(elementLine);
					if (parentElementName.equals(element.getName())) {
						isParent = true;
					} else if (childElementNames.contains(element.getName())) {
						if (isParent) {
							System.out.println(element.getName() + ": " + element.getValue());
						}
					}
				} else {
					System.out.println("I don't know... ??????");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
}
