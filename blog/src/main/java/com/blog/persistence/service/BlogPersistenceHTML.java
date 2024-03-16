package com.blog.persistence.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.stereotype.Service;

import com.blog.core.entity.Blog;
import com.blog.core.persistence.BlogPersistence;
import com.blog.exception.NotFoundException;

@Service
public class BlogPersistenceHTML implements BlogPersistence<Blog> {

	@Override
	public String saveBlog(Blog blog) {
		String fileName = randomName("html");
		File rutaImagen = new File("src\\main\\resources\\blogs\\" + fileName);
		File path = new File("src\\main\\resources\\blogs\\");
		if (!path.exists()) {
			path.mkdir();
		}
		FileWriter filewriter = null;
		try {
			filewriter = new FileWriter(rutaImagen.getAbsolutePath());// declarar el archivo
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter printw = new PrintWriter(filewriter);// declarar un impresor
		printw.println(blog.getContentBlog());
		printw.close();// cerramos el archivo
		return fileName;
	}

	@Override
	public String findBlog(String name) {
		File rutaHtml = new File("src\\main\\resources\\blogs\\" + name);
		if (!rutaHtml.exists()) {
			throw new NotFoundException("No se encontro el fichero " + name);
		}
		FileReader fileReader = null;
		char[] chars = null;
		try {
			fileReader = new FileReader(rutaHtml.getAbsolutePath());// declarar el archivo
			chars = new char[(int) rutaHtml.length()];
			fileReader.read(chars); // Leemos los caracteres y los guardamos en el objeto 'chars'
			fileReader.close(); // cerramos el archivo
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		String htmlContent = new String(chars);
		return htmlContent;
	}

	private String randomName(String fileType) {
		String uuid = java.util.UUID.randomUUID().toString();
		String fileName = uuid + "." + fileType;
		return fileName;
	}

}
