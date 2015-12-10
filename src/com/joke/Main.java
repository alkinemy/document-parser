package com.joke;

import com.joke.parser.DomParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
		findElementWithDomParser();
	}

	private static void findElementWithDomParser() {
		System.out.println("****** DOM Parser start ******");

		DomParser parser = new DomParser();
		List<String> elementNames = new ArrayList<>();
		elementNames.add("title");
		elementNames.add("link");
		parser.printElements(new File("/Users/joke/Downloads/World.xml"), "item", elementNames);

		System.out.println("****** DOM Parser end ******");
	}

}
