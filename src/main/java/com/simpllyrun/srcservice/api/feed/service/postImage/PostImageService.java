package com.simpllyrun.srcservice.api.feed.service.postImage;

import com.simpllyrun.srcservice.api.feed.dto.PostImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostImageService {

    List<PostImageDto> uploadImage(List<MultipartFile> multipartFiles);
}
