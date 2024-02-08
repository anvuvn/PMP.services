package cs545.property.services;

import cs545.property.domain.Image3D;
import cs545.property.domain.ImageData;
import cs545.property.dto.ImageDataResponse;
import cs545.property.dto.ThumbsImageDataResponse;
import cs545.property.repository.Image3DRepository;
import cs545.property.repository.ImageDataRepository;
import cs545.property.repository.PropertyRepo;
import cs545.property.util.ImageUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class Image3DImageService {

    @Autowired
    private Image3DRepository image3DRepository;

    @Autowired
    private PropertyRepo propertyRepository;

    @Transactional
    public String uploadPropertyImages(Long propertyId, MultipartFile file) throws IOException {
        var property = propertyRepository.getReferenceById(propertyId);

        Image3D image3d = image3DRepository.save(
            Image3D.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes())).build()
            );
        //
        property.setImage3d(image3d);
        propertyRepository.save(property);
        //
        return "3D Image uploaded successfully: " +  file.getOriginalFilename().toString();

    }


    @Transactional
    public ImageDataResponse getByPropertyId(Long propertyId) {
        //
        var prop = propertyRepository.getReferenceById(propertyId);
        if(prop == null)
        {
            return null;
        }
        var image3d = prop.getImage3d();
        if(image3d == null)
        {
            return null;
        }
        return new ImageDataResponse(image3d.getId(),ImageUtil.decompressImage(image3d.getImageData()));
    }
}