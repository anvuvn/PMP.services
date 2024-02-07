package cs545.property.services;

import cs545.property.domain.PropertyImage;
import cs545.property.dto.PropertyImageResponse;
import cs545.property.repository.PropertyImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FileUploadService {

    @Autowired
    private PropertyImageRepo imagesRepository;

    private final Path UPLOAD_PATH =
            Paths.get(new ClassPathResource("").getFile().getAbsolutePath()
                    + File.separator + "static"
                    + File.separator + "image");

    public FileUploadService() throws IOException {
    }


    public PropertyImageResponse uploadFile(MultipartFile file, String uploaderName) throws IOException {
        if (!Files.exists(UPLOAD_PATH)) {
            Files.createDirectories(UPLOAD_PATH);
        }

        // file format validation
//        if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")) {
//            throw new IOException("only .jpeg and .png images are " + "supported");
//        }

        String timeStampedFileName = new SimpleDateFormat("ssmmHHddMMyyyy")
                .format(new Date()) + "_" + file.getOriginalFilename();

        Path filePath = UPLOAD_PATH.resolve(timeStampedFileName);
        Files.copy(file.getInputStream(), filePath);

        String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image/").path(timeStampedFileName).toUriString();

        String fileDownloadUri =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/file/download/").path(timeStampedFileName).toUriString();

        PropertyImage fileDetails = new PropertyImage(file.getOriginalFilename(),
                fileUri,
                fileDownloadUri, file.getSize(), uploaderName);

        this.imagesRepository.save(fileDetails);

        PropertyImageResponse fileUploadResponse =
                new PropertyImageResponse(fileDetails.getId(),
                        file.getOriginalFilename(), fileUri, fileDownloadUri,
                        file.getSize(),
                        uploaderName);

        return fileUploadResponse;
    }

    public Resource fetchFileAsResource(String fileName) throws FileNotFoundException {
        try {
            Path filePath = UPLOAD_PATH.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName);
        }
    }

    public List<PropertyImage> getAllFiles() {
        return this.imagesRepository.findAll();
    }
}
