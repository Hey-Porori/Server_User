package porori.backend.user.domain.user.application.mapper;

import org.springframework.stereotype.Service;
import porori.backend.user.domain.user.application.dto.request.UserLocationRequest;
import porori.backend.user.domain.user.application.dto.response.LocationResponse;
import porori.backend.user.domain.user.application.dto.response.UserLocationResponse;
import porori.backend.user.domain.user.domain.entity.Location;
import porori.backend.user.domain.user.domain.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {
    public UserLocationResponse toUserLocationResponse(User user) {
        return new UserLocationResponse(
                user.getUserId(),
                user.getMyLocations().stream()
                        .map(l -> toLocationResponse(l))
                        .collect(Collectors.toList())
        );
    }
    private LocationResponse toLocationResponse(Location location) {
        return LocationResponse.builder()
                .locationId(location.getLocationId())
                .name(location.getName())
                .longitude(location.getLongitude())
                .latitude(location.getLatitude())
                .isDefault(location.isDefault())
                .build();
    }
    public Location toLocation(UserLocationRequest userLocationRequest) {
        return Location.builder()
                .name(userLocationRequest.getName())
                .longitude(userLocationRequest.getLongitude())
                .latitude(userLocationRequest.getLatitude())
                .build();
    }
}
