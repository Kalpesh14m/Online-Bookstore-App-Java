package com.bridgelabz.bookstore.utils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Template {

	private Template() {
	}

	public static String readContentFromTemplet(String path) throws IOException {
		Path filePath = FileSystems.getDefault().getPath(path).normalize();
		byte[] fileBytes = Files.readAllBytes(filePath);
		return new String(fileBytes);
	}
}