package com.blog.mapper;

import java.io.IOException;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.blog.core.entity.Image;
import com.blog.dto.ImageDto;
import com.blog.persistence.dao.ImageDao;

@Mapper(componentModel = "spring")
public interface ImageMapper {

	Image imageDaoToImage(ImageDao imageDao);
	ImageDao imageToImageDao(Image image);
	
	@Mapping(target = "image", expression = "java(imageDto.getFile().getBytes())")
	Image imageDtoToImage(ImageDto imageDto) throws IOException;
	
	@Mapping(target = "file", expression = "java(null)")
	ImageDto imageToImageDto(Image image);
	
	@Mappings(value = {
			@Mapping(target = "file", expression = "java(null)"),
			@Mapping(target = "blogId", expression = "java(null)")
	})
	ImageDto imageDaoToImageDto(ImageDao imageDao);
	ImageDao imageDtoToImageDao(ImageDto imageDto);
	
}
