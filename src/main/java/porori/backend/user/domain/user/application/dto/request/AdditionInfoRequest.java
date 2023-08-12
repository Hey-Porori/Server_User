package porori.backend.user.domain.user.application.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "추가 정보 입력을 위한 요청 객체")
public class AdditionInfoRequest {
    @NotBlank(message = "자체 액세스 토큰을 입력해주세요.")
    private String accessToken;

    @NotBlank(message = "본명을 입력해주세요.")
    private String name;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickName;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phoneNumber;

    @NotNull(message = "성별을 입력해주세요.")
    private Boolean gender;

    @NotBlank(message="프로필 사진 URl입력해주세요.")
    private String imageUrl;

    @NotBlank(message="이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;

}