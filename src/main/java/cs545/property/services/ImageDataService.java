package cs545.property.services;

import cs545.property.domain.ImageData;
import cs545.property.dto.ImageDataResponse;
import cs545.property.repository.ImageDataRepository;
import cs545.property.util.ImageUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageDataService {

    @Autowired
    private ImageDataRepository imageDataRepository;

    @Transactional
    public String uploadImage(MultipartFile file) throws IOException {

        imageDataRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes())).build());

        return "Image uploaded successfully: " +
                file.getOriginalFilename();

    }

    @Transactional
    public ImageData getInfoByImageByName(String name) {
        Optional<ImageData> dbImage = imageDataRepository.findByName(name);

        return ImageData.builder()
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .imageData(ImageUtil.decompressImage(dbImage.get().getImageData())).build();

    }

    @Transactional
    public byte[] getImage(String name) {
        Optional<ImageData> dbImage = imageDataRepository.findByName(name);
        byte[] image = ImageUtil.decompressImage(dbImage.get().getImageData());
        return image;
    }


    @Transactional
    public List<ImageDataResponse> getAll() {
        var images = imageDataRepository.findAll();
        var result = new ArrayList<ImageDataResponse>();
        images.forEach(i->{
            result.add(new ImageDataResponse(
                    i.getId(),ImageUtil.decompressImage(i.getImageData()))
            );
        });
        return result;
    }
}