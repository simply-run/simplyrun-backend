package com.simpllyrun.srcservice.api.feed.service.postImage;

import com.simpllyrun.srcservice.api.feed.dto.PostImageDto;
import com.simpllyrun.srcservice.api.feed.repository.PostImageRepository;
import com.simpllyrun.srcservice.api.feed.service.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImageServiceImpl implements PostImageService {

    private final PostImageRepository postImageRepository;
    private final FileStore fileStore;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Override
    @Transactional
    public List<PostImageDto> uploadImage(List<MultipartFile> multipartFiles) {
        List<PostImageDto> postImageDtoList = new ArrayList<>();

        if (multipartFiles == null) {
                return null;
             }
        for (MultipartFile multipartFile : multipartFiles) {
            String originalFilename = multipartFile.getOriginalFilename();
            String storeUuid = fileStore.createStoreUuid(originalFilename);
            String folderPath = fileStore.makeFolder(uploadPath);

//            PostImage postImage = postImageRepository.save(PostImage.builder()
//                    .originalFilename(originalFilename)
//                    .storeFilename(storeUuid)
//                    .build());

//            PostImageDto postImageDto = PostImageDto.of(postImage);
            PostImageDto postImageDto = PostImageDto.builder()
                    .originalFilename(originalFilename)
                    .storeFilename(storeUuid)
                    .build();
            postImageDtoList.add(postImageDto);

            String saveName = uploadPath + File.separator + folderPath + File.separator + storeUuid;

            Path savePath = Paths.get(saveName);

            try{
                multipartFile.transferTo(savePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return postImageDtoList;
    }

}
