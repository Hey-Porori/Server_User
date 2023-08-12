package porori.backend.user.domain.user.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserLocationRequest {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotNull(message = "경도를 입력해주세요.")
    private Double longitude;

    @NotNull(message = "위도를 입력해주세요.")
    private Double latitude;
}
