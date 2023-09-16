package porori.backend.user.domain.user.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.domain.entity.Location;
import porori.backend.user.domain.user.domain.repository.LocationRepository;

@Service
@RequiredArgsConstructor
public class LocationGetService {

    private final LocationRepository locationRepository;

    public Location getLocationById(Long locationId){
        return locationRepository.findByLocationId(locationId);
    }
}
