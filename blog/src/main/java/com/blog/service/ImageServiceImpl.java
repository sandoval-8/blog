package com.blog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.core.entity.Image;
import com.blog.core.persistence.ImagePersistence;
import com.blog.core.service.ImageService;
import com.blog.mapper.ImageMapper;
import com.blog.persistence.BlogRepository;
import com.blog.persistence.ImageRepository;
import com.blog.persistence.dao.BlogDao;
import com.blog.persistence.dao.ImageDao;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImagePersistence imagePersistence;

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private ImageMapper imageMapper;
	
	@Autowired
	private BlogRepository blogRepository;

	@Override
	public Image createEntity(Image createObject) {
		String fileName = imagePersistence.saveImage(createObject);
		createObject.setName(fileName);
		ImageDao imageDao = imageRepository.save(imageMapper.imageToImageDao(createObject));
		Optional<BlogDao> blogOptional = blogRepository.findById(createObject.getBlogId());
		if (blogOptional.isPresent()) {
			BlogDao blogDao = blogOptional.get();
			List<ImageDao> imagenes = blogDao.getImages();
			imagenes.add(imageDao);
			blogDao.setImages(imagenes);
			blogRepository.save(blogDao);
		}
		Image imageResponse = imageMapper.imageDaoToImage(imageDao);
		imageResponse.setImage(null);
		return imageResponse;
	}

	@Override
	public Image updateEntity(Image updateObject) {
		//Tiene un problema: cuando actualicemos una imagen por lo que sea tambien se va a actualizar
		//la fecha de creacion, revisar eso, deberia haber un campo de actualizacion y no modificar el 
		//campode creación.
		if (null != updateObject.getId()) {
			Optional<ImageDao> imageDaoOptional = imageRepository.findById(updateObject.getId());
			if(imageDaoOptional.isPresent()) {
				ImageDao imageDao = imageDaoOptional.get();
				String fileName = imagePersistence.saveImage(updateObject);
				imageDao.setName(fileName);
				imageDao = imageRepository.save(imageDao);
				return imageMapper.imageDaoToImage(imageDao);
				
			} else {
				//implementar funcionalidad si el id es nulo.
				return null;
			}
		} else {
			//implementar funcionalidad si el id no existe en BBDD.
			return null;
		}
	}

	@Override
	public Image deleteEntity(Long deleteObject) {
		if (null != deleteObject) {
			Optional<ImageDao> imageDaoOptional = imageRepository.findById(deleteObject);
			if(imageDaoOptional.isPresent()) {
				ImageDao imageDisabled = imageDaoOptional.get();
				imageDisabled.setEnabled(false);
				ImageDao imageResponse = imageRepository.save(imageDisabled);
				return imageMapper.imageDaoToImage(imageResponse);
			}
		}
		//Implementar funcionalidad si no existe la imagen a eliminar.
		return null;
	}

	@Override
	public List<Image> getEntity(Long gettingObect) {
		//Falta implementar busqueda por filename
		Optional<ImageDao> imageDaoOptional = imageRepository.findById(gettingObect);
		if (imageDaoOptional.isPresent()) {
			List<Image> imageList = new ArrayList<>();
			ImageDao imageDao = imageDaoOptional.get();
			Image image = imageMapper.imageDaoToImage(imageDao);
			byte[] imageBytes = imagePersistence.findImage(imageDao.getName());
			image.setImage(imageBytes);
			imageList.add(image);
			return imageList;
		}
		//Implementar funcionalidad si no existe la imagen a recuperar.
		return null;
	}

}
