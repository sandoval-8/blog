package com.blog.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.core.entity.Image;
import com.blog.core.persistence.ImagePersistence;
import com.blog.core.service.ImageService;
import com.blog.dto.ImageDto;
import com.blog.exception.NotFoundException;
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
		ImageDto image = (ImageDto) createObject;
		String fileName = null;
		try {
			fileName = imagePersistence.saveImage(imageMapper.imageDtoToImage(image));
		} catch (IOException e) {
			throw new RuntimeException("Se produjo un error inesperado al guardar la imagen.");
		}
		image.setName(fileName);
		ImageDao imageDao = imageRepository.save(imageMapper.imageDtoToImageDao(image));
		Optional<BlogDao> blogOptional = blogRepository.findById(image.getBlogId());
		if (blogOptional.isPresent()) {
			BlogDao blogDao = blogOptional.get();
			List<ImageDao> imagenes = blogDao.getImages();
			imagenes.add(imageDao);
			blogDao.setImages(imagenes);
			blogRepository.save(blogDao);
		}
		ImageDto imageResponse = imageMapper.imageDaoToImageDto(imageDao);
		imageResponse.setImage(null);
		return imageResponse;
	}

	//Esta re buggeada esta funcinalidad
	@Override
	public Image updateEntity(Image updateObject) {
		//Tiene un problema: cuando actualicemos una imagen por lo que sea tambien se va a actualizar
		//la fecha de creacion, revisar eso, deberia haber un campo de actualizacion y no modificar el 
		//campode creación.
		ImageDto image = (ImageDto) updateObject;
		if (null != updateObject.getId()) {
			Optional<ImageDao> imageDaoOptional = imageRepository.findById(updateObject.getId());
			if(imageDaoOptional.isPresent()) {
				ImageDao imageDao = imageDaoOptional.get();
				ImageDao updateImageDao = imageMapper.imageDtoToImageDao(image);
				if(null != image.getFile()) {
					String fileName;
					try {
						fileName = imagePersistence.saveImage(imageMapper.imageDtoToImage(image));
					} catch (IOException e) {
						throw new RuntimeException("Hubo un error al guardar la imagen");
					}
					updateImageDao.setName(fileName);
				}
				
				updateImageDao.setCreated(imageDao.getCreated());
				ImageDao imageDaoResponse = imageRepository.save(updateImageDao);
				return imageMapper.imageDaoToImageDto(imageDaoResponse);
				
			} else {
				//implementar funcionalidad si el id no existe en BBDD.
				throw new NotFoundException("No hay imagenes con id=" + updateObject.getId());
			}
		} else {
			//implementar funcionalidad si el id es nulo.
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
	public List<? extends Image> getEntity(Long gettingObect) {
		//Falta implementar busqueda por filename
		Optional<ImageDao> imageDaoOptional = imageRepository.findById(gettingObect);
		if (imageDaoOptional.isPresent()) {
			List<ImageDto> imageList = new ArrayList<ImageDto>();
			ImageDao imageDao = imageDaoOptional.get();
			ImageDto image = imageMapper.imageDaoToImageDto(imageDao);
			byte[] imageBytes = imagePersistence.findImage(imageDao.getName());
			image.setImage(imageBytes);
			imageList.add(image);
			return imageList;
		}
		//Implementar funcionalidad si no existe la imagen a recuperar.
		return null;
	}

	

}
