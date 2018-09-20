package com.wen.mall.file.service.impl;


import com.wen.mall.exception.FileStorageException;
import com.wen.mall.exception.MyFileNotFoundException;
import com.wen.mall.file.entity.File;
import com.wen.mall.file.entity.FileStorageProperties;
import com.wen.mall.file.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    private IFileService fileService;


    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        int lastPoint=fileName.lastIndexOf(".");
        if (lastPoint > 0) {
            String name = fileName.substring(0, lastPoint);
            String suffix = fileName.substring(lastPoint + 1);
            String uuid = UUID.randomUUID().toString();
            File dbFile = new File();
            dbFile.setUuid(uuid);
            dbFile.setRealName(name);
            dbFile.setSuffix(suffix);
            dbFile.setPath(this.fileStorageLocation.toString());
            this.fileService.save(dbFile);
            fileName = uuid + "." + suffix;
        }
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        File file=this.fileService.getById(fileName);
        if (null == file) {
            throw new MyFileNotFoundException("File not found" + fileName);
        }
        fileName = file.getUuid() + "." + file.getSuffix();
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}