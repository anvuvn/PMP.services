package cs545.property.services;

import cs545.property.domain.Favourite;
import cs545.property.dto.FavouriteCreateRequest;
import cs545.property.dto.FavouriteResponseDto;
import cs545.property.dto.PropertyResponseDto;
import cs545.property.repository.FavouriteRepo;
import cs545.property.repository.PropertyRepo;
import cs545.property.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FavouriteService {
    @Autowired
    FavouriteRepo favouriteRepo;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PropertyRepo propertyRepo;
    public FavouriteResponseDto add(FavouriteCreateRequest model){
        var f =new Favourite();
        var user = userRepository.getReferenceById(model.getUserId());
        var property = propertyRepo.getReferenceById(model.getPropertyId());
        if(user == null){
            throw new RuntimeException("User not found");
        }
        if(property == null){
            throw new RuntimeException("property not found");
        }
        f.setUser(user);
        f.setProperty(property);
        favouriteRepo.save(f);
        return new FavouriteResponseDto(f);
    }

    public List<FavouriteResponseDto> getByUserId(Long userId){
       var fs = favouriteRepo.findByUserId(userId);
       return fs.stream().map(f->new FavouriteResponseDto(f)).toList();
    }
}
