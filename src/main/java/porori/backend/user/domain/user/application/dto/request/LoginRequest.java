package porori.backend.user.domain.user.application.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@Builder
@ApiModel(description = "애플 로그인을 위한 요청 객체")
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "애플 identity token을 입력해주세요.")
    @ApiModelProperty(notes = "카카오 identity token을 주세요.")
    private String token;

}
