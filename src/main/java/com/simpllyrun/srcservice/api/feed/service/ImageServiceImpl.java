package com.simpllyrun.srcservice.api.feed.service;

import com.simpllyrun.srcservice.api.feed.domain.Image;
import com.simpllyrun.srcservice.api.feed.dto.ImageDto;
import com.simpllyrun.srcservice.api.feed.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private final ImageRepository imageRepository;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Override
    @Transactional
    public List<ImageDto> uploadImage(List<MultipartFile> multipartFiles) {
        List<ImageDto> imageDtoList = new ArrayList<>();

        if (multipartFiles == null) {
                return null;
             }
        for (MultipartFile multipartFile : multipartFiles) {
            String originalFilename = multipartFile.getOriginalFilename();
            String storeUuid = createStoreUuid(originalFilename);
            String folderPath = makeFolder(uploadPath);

            Image image = imageRepository.save(Image.builder()
                    .originalFilename(originalFilename)
                    .storeFilename(storeUuid)
                    .build());

            ImageDto imageDto = ImageDto.of(image);
            imageDtoList.add(imageDto);

            String saveName = uploadPath + File.separator + folderPath + File.separator + storeUuid;

            Path savePath = Paths.get(saveName);

            try{
                multipartFile.transferTo(savePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return imageDtoList;
    }

    private String makeFolder(String uploadPath){
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM//dd"));

        String folderPath = str.replace("/", File.separator);

        //make folder
        File uploadPathFolder = new File(uploadPath, folderPath);

        if (uploadPathFolder.exists() == false){
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }
}
