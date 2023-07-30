package porori.backend.user.domain.user.application.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import porori.backend.user.domain.user.domain.entity.UserConstant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

public class UserRequestDto {
    @Getter
    @AllArgsConstructor
    @Builder
    @ApiModel(description = "애플 로그인을 위한 요청 객체")
    @NoArgsConstructor
    public static class LoginRequest {
        @NotBlank(message = "애플 identity token을 입력해주세요.")
        @ApiModelProperty(notes = "카카오 identity token을 주세요.")
        private String token;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "추가 정보 입력을 위한 요청 객체")
    public static class AdditionInfoRequest {
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

    @Getter
    @AllArgsConstructor
    @Builder
    @ApiModel(description = "회원 탈퇴를 위한 요청 객체")
    @NoArgsConstructor
    public static class DeleteAccountRequest {
        @NotBlank(message = "카카오 액세스 토큰을 입력해주세요.")
        @ApiModelProperty(notes = "카카오 accessToken을 주세요.")
        private String token;

        @NotBlank(message = "탈퇴 이유를 입력해주세요.")
        @ApiModelProperty(notes = "탈퇴 이유를 입력해주세요.")
        private String reasonToLeave;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class TestSignUpRequest {

        private String name;
        private String phoneNumber;
        private String appleID;
        private String nickName;
        private Boolean gender;
        private String imageUrl;
        private String email;
        private String address;


    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class TestLoginRequest {

        private String appleId;

    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class GetUserInfoRequest {

        private String accessToken;

    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class GetCommunityUserInfoRequest{
        private List<Long> userIdList;
    }
}
