package com.simpllyrun.srcservice.api.feed.service;

import com.simpllyrun.srcservice.api.feed.dto.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ImageService {

    List<ImageDto> uploadImage(List<MultipartFile> multipartFiles);

    //확장자 추출
    default String getExt(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(index + 1);
        return ext;
    }
    //파일이름 추출
    default String getFilename(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        String filename = originalFilename.substring(0, index);
        return filename;
    }
    //저장될 파일 이름 만들기
    default String createStoreUuid(String originalFilename) {
        String ext = getExt(originalFilename);

        String filename = getFilename(originalFilename);

        String uuid = UUID.randomUUID().toString();
        String storeImageName = filename + "_" + uuid + "." + ext;
        return storeImageName;
    }
}
