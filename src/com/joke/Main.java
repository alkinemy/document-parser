package com.joke;

import com.joke.parser.DomParser;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
		DomParser parser = new DomParser();

		List<DomParser.Node> items = parser.findElements(new File("/Users/joke/Downloads/World.xml"), "item");

		System.out.println("end");
	}
}
