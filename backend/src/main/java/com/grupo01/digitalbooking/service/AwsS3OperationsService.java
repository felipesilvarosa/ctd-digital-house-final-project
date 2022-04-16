package com.grupo01.digitalbooking.service;


import com.grupo01.digitalbooking.domain.Image;
import com.grupo01.digitalbooking.domain.Product;
import com.grupo01.digitalbooking.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AwsS3OperationsService {

    private final ImageRepository repository;
    private final S3Client s3;
    private final String bucket;
    private final String awsDomain;
    private final String folder;

    @Autowired
    public AwsS3OperationsService(ImageRepository repository){
        this.bucket = "t2g1-s3";
        this.awsDomain = "https://t2g1-s3.s3.amazonaws.com/";
        this.folder = "detalhes/";
        this.repository = repository;
        this.s3 = S3Client.builder().region(Region.US_EAST_1).build();
    }

    @Transactional
    public List<Image> uploadAndRegisterImages(List<MultipartFile> images, Product product) {
        List<Image> response = new ArrayList<>();
        images.forEach(image-> {
            try {
                File file = new File(image.getName());
                image.transferTo(file);
                String imageTitle = image.getOriginalFilename();
                PutObjectRequest request = PutObjectRequest
                        .builder()
                        .bucket(bucket)
                        .acl("public-read")
                        .key(folder+imageTitle)
                        .build();
                s3.putObject(request, RequestBody.fromFile(file));

                String url = awsDomain+folder+imageTitle;
                Image entity = repository.findByTitle(imageTitle)
                        .orElse(new Image(null,imageTitle,url,product));
                response.add(entity);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return repository.saveAll(response);

    }
}
