package com.simpllyrun.srcservice.api.feed.service;

import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class FileStore {

    //확장자 추출
    public String getExt(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(index + 1);
        return ext;
    }
    //파일이름 추출
    public String getFilename(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        String filename = originalFilename.substring(0, index);
        return filename;
    }
    //저장될 파일 이름 만들기
    public String createStoreUuid(String originalFilename) {
        String ext = getExt(originalFilename);

        String filename = getFilename(originalFilename);

        String uuid = UUID.randomUUID().toString();
        String storeImageName = filename + "_" + uuid + "." + ext;
        return storeImageName;
    }

    // 저장 폴더 만들기
    public String makeFolder(String uploadPath){
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
