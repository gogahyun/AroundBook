package com.ggh_dev.AroundBook;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ggh_dev.AroundBook.domain.item.ItemImage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3ManageFile {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    //S3 스토리지에 이미지 목록 파일 업로드
    public List<ItemImage> uploadFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<ItemImage> storeFiles=new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFiles.add(uploadFile(multipartFile));
            }
        }
        return storeFiles;

    }

    //S3 스토리지에 단건 이미지 파일 업로드
    public ItemImage uploadFile(MultipartFile file) throws IOException{
        String originalFilename=file.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);

        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(bucket,storeFileName,file.getInputStream(),metadata);

        return new ItemImage(originalFilename, storeFileName);
    }

    //S3 스토리지에 이미지 파일 삭제
    public void deleteFile(List<ItemImage> itemImages) {
        for (ItemImage itemImage : itemImages) {
            amazonS3Client.deleteObject(bucket,itemImage.getStoreFileName());
        }
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
