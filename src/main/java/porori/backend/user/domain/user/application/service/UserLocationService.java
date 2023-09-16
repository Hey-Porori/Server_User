package porori.backend.user.domain.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.application.dto.request.UserLocationRequest;
import porori.backend.user.domain.user.application.dto.response.UserLocationResponse;
import porori.backend.user.domain.user.application.mapper.UserMapper;
import porori.backend.user.domain.user.domain.entity.Location;
import porori.backend.user.domain.user.domain.entity.User;
import porori.backend.user.domain.user.domain.service.LocationDeleteService;
import porori.backend.user.domain.user.domain.service.LocationGetService;
import porori.backend.user.domain.user.domain.service.LocationSaveService;
import porori.backend.user.domain.user.domain.service.UserValidationService;
import porori.backend.user.global.exception.NotFoundLocationException;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserLocationService {

    private final UserValidationService userValidationService;
    private final UserMapper userMapper;
    private final LocationSaveService locationSaveService;
    private final LocationDeleteService locationDeleteService;
    private final LocationGetService locationGetService;

    public UserLocationResponse getUserLocation(String appleId){
        User user = userValidationService.validateAppleId(appleId);
        return userMapper.toUserLocationResponse(user);
    }

    public void addUserLocation(String appleId, UserLocationRequest userLocationRequest) {
        Location newLocation = userMapper.toLocation(userLocationRequest);
        User user = userValidationService.validateAppleId(appleId);
        newLocation.updateUser(user);
        locationSaveService.saveLocation(newLocation);
    }

    public void updateUserLocation(String appleId, Long locationId, UserLocationRequest userLocationRequest) {
        User user = userValidationService.validateAppleId(appleId);
        Location newLocation = userMapper.toLocation(userLocationRequest);
        Location oldLocation = locationGetService.getLocationById(locationId);
        oldLocation.updateLocation(newLocation);
    }

    public void deleteUserLocation(String appleId, Long locationId) {
        User user = userValidationService.validateAppleId(appleId);
        Location location = locationGetService.getLocationById(locationId);
        locationDeleteService.deleteLocation(location);
    }

    public void selectUserLocation(String appleId, Long locationId){
        User user = userValidationService.validateAppleId(appleId);
        Location location=locationGetService.getLocationById(locationId);
        location.selectLocation();
    }
}
