package com.joke;

import com.joke.parser.DomParser;

import java.io.File;

public class Main {

    public static void main(String[] args) {
		DomParser parser = new DomParser();

		parser.findElements(new File("/Users/joke/Downloads/World.xml"), "item");
	}
}
