package com.blog.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.core.entity.Image;
import com.blog.core.service.ImageService;
import com.blog.dto.ImageDto;
import com.blog.mapper.ImageMapper;

@RestController
@RequestMapping(path = "/image")
public class ImageController {

	@Autowired
	private ImageService imageService;

	@Autowired
	private ImageMapper imageMapper;

	@GetMapping(path = "/all")
	public ResponseEntity<?> getAllImages() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getImage(Long id, String filename) {
		//Falta implementar funcionalidad para filename
		List<Image> imageList = imageService.getEntity(id);
		if(Objects.isNull(imageList)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Image image = imageList.get(0);
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"");
		return new ResponseEntity<>(image.getImage(), cabecera, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> createImage(ImageDto imageCreate) throws IOException {
		String[] filename = (imageCreate.getFile().getOriginalFilename()).split("\\.");
		imageCreate.setSuffix(filename[filename.length - 1]);
		Image image = imageService.createEntity(imageMapper.imageDtoToImage(imageCreate));
		return new ResponseEntity<>(image.getId(), HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> updateImage(ImageDto imageUpdate) throws IOException {
		Image image = imageService.updateEntity(imageMapper.imageDtoToImage(imageUpdate));
		ImageDto imageResponse = imageMapper.imageToImageDto(image);
		return new ResponseEntity<ImageDto>(imageResponse, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteImage(Long id) {
		Image imageResponse = imageService.deleteEntity(id);
		return new ResponseEntity<Long>(imageResponse.getId(), HttpStatus.OK);
	}

}
