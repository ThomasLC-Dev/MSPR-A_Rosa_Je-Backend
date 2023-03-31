package fr.cttt.arosaje.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class StorageService {
    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 S3Client;

    public String uploadFile(MultipartFile multipartFile, String type, Long id){
        File file = this.convertMultiPartFileToFile(multipartFile);
        String fileName = type + "/" + System.currentTimeMillis() + "_" + type + "_" + id;
        PutObjectResult uploadResult = S3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
        file.delete();
        return "https://arosaje.s3.eu-west-3.amazonaws.com/" + fileName;
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            System.out.println(e);
        }
        return convertedFile;
    }

    public File retriveDb() throws IOException {
        S3Object dbObject = S3Client.getObject(new GetObjectRequest(bucketName, "arosaje.db"));
        S3ObjectInputStream dbObjectStream = dbObject.getObjectContent();
        File dbFile = new File("arosaje.db");
        FileUtils.copyInputStreamToFile(dbObjectStream, dbFile);
        return dbFile;
    }

    public void saveDb(File dbFile){
        String fileName = "arosaje.db";
        PutObjectResult uploadResult = S3Client.putObject(new PutObjectRequest(bucketName, fileName, dbFile));
    }
}
