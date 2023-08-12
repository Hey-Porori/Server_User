package porori.backend.user.domain.user.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLocationResponse {
    private Long userId;
    private List<LocationResponse> myLocations = new ArrayList<>();
}
