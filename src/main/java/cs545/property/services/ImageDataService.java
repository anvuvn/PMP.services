package cs545.property.services;

import cs545.property.domain.ImageData;
import cs545.property.dto.ImageDataResponse;
import cs545.property.dto.ThumbsImageDataResponse;
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
import java.util.stream.Collectors;

@Service
public class ImageDataService {

    @Autowired
    private ImageDataRepository imageDataRepository;

    @Autowired
    private PropertyRepo propertyRepository;

    @Transactional
    public String uploadImage(MultipartFile file) throws IOException {

        var img = imageDataRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes())).build());

        return "Image uploaded successfully: " +
                file.getOriginalFilename();

    }

    @Transactional
    public String uploadPropertyImages(Long propertyId, MultipartFile[] files) throws IOException {

        var property = propertyRepository.getReferenceById(propertyId);

        var uploadedImages = new ArrayList();

        Arrays.stream(files).forEach(
                file -> {
                    try {
                        uploadedImages.add( imageDataRepository.save(ImageData.builder()
                                .name(file.getOriginalFilename())
                                .type(file.getContentType())
                                .imageData(ImageUtil.compressImage(file.getBytes())).build())
                        );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        property.setImages(uploadedImages);
        propertyRepository.save(property);

        return "Image uploaded successfully: " + files.toString();

    }




    @Transactional
    public ImageData getInfoByImageByName(String name) {
        Optional<ImageData> dbImage = imageDataRepository.findByName(name);
        if(!dbImage.isPresent())
            return null;

        return ImageData.builder()
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .imageData(ImageUtil.decompressImage(dbImage.get().getImageData())).build();

    }

    @Transactional
    public byte[] getImage(String name) {
        Optional<ImageData> dbImage = imageDataRepository.findByName(name);
        if(!dbImage.isPresent())
            return null;

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

    public  List<ImageDataResponse> getImagesByPropertyId(Long id) {
        var result = new ArrayList<ImageDataResponse>();
        var prop = propertyRepository.getReferenceById(id);
        if(prop == null)
        {
            return result;
        }
        var images = prop.getImages();
        if(images == null)
        {
            return result;
        }
        images.forEach(i->{
            result.add(new ImageDataResponse(
                    i.getId(),ImageUtil.decompressImage(i.getImageData()))
            );
        });
        return result;
    }

    public List<ThumbsImageDataResponse> getThumbByIds(Long[] ids) {
        //
        var result = new ArrayList<ThumbsImageDataResponse>();
        Arrays.stream(ids).forEach(id-> {
            //
            var imgs = propertyRepository.getReferenceById(id).getImages();
            if (imgs != null)
            {
                var img = imgs.get(0);
                result.add(
                        new ThumbsImageDataResponse(
                                id,
                                ImageUtil.decompressImage(img.getImageData())
                        )
                );
            }
        });
        return result;
    }
}
