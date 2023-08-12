package porori.backend.user.domain.user.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.domain.entity.Location;
import porori.backend.user.domain.user.domain.repository.LocationRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LocationDeleteService {
    private final LocationRepository locationRepository;

    public void deleteLocation(Location location){
        locationRepository.delete(location);
    }
}
