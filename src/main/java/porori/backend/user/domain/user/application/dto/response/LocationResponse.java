package porori.backend.user.domain.user.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {

    private Long locationId;
    private String name;
    private Double longitude;
    private Double latitude;
    private boolean isDefault;

}
