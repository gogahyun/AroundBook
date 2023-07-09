package com.ggh_dev.AroundBook;

import com.ggh_dev.AroundBook.domain.item.ItemImage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {
    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir+filename;
    }

    public List<ItemImage> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<ItemImage> storeFiles=new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFiles.add(storeFile(multipartFile));
            }
        }
        return storeFiles;

    }

    public ItemImage storeFile(MultipartFile multipartFile) throws IOException
    {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new ItemImage(originalFilename, storeFileName);
    }

    //--서버 저장용 파일 이름 생성--//
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    //--파일 확장자 추출--//
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }


}
