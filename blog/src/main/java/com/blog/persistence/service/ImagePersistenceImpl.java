package com.blog.persistence.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.blog.core.entity.Image;
import com.blog.core.persistence.ImagePersistence;

@Service
public class ImagePersistenceImpl implements ImagePersistence {

	@Override
	public String saveImage(Image image) {
		FileOutputStream fileOutput;
		String fileName = randomName(image.getSuffix());
			try {
				File rutaImagen = new File("src\\main\\resources\\image\\" + fileName);
				File path = new File("src\\main\\resources\\image\\");
				if(!path.exists()) {
					path.mkdir();
				}
				fileOutput = new FileOutputStream(rutaImagen.getAbsolutePath());
				fileOutput.write(image.getImage());
				fileOutput.flush();
				fileOutput.close();
				
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e.getMessage());
			}catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		return fileName;
	}

	@Override
	public byte[] findImage(String name) {
		FileInputStream fileInput;
		File rutaImagen = new File("src\\main\\resources\\image\\" + name);
		if(!rutaImagen.exists()) {
			return null; //Excepcion file not found
		}
		try {
			fileInput = new FileInputStream(rutaImagen.getAbsolutePath());
			byte[] image = fileInput.readAllBytes();
			fileInput.close();
			return image;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param fileType is a type of file to save. example: .png .jpeg .png
	 * @return random name asigne to image to save
	 */
	private String randomName(String fileType) {
		String uuid = java.util.UUID.randomUUID().toString();
		String fileName = uuid + "." + fileType;
		return fileName;
	}

	private boolean exist(String fileName) {
		return true;
	}
}
