package com.joke.parser;

import java.io.File;
import java.util.List;

public interface DocumentParser {
	void printElements(File file, String parentElementName, List<String> childElementNames);
}
