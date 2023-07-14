package porori.backend.user.domain.user.application.dto.res;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import porori.backend.user.domain.user.domain.entity.User;
import porori.backend.user.global.dto.TokenInfoResponse;

public class UserResponseDto {



    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "로그인을 위한 응답 객체")
    public static class LoginResponse {
        private String accessToken;
        private String refreshToken;
        private Long userId;
        private String process;

        public static LoginResponse from(TokenInfoResponse tokenInfoResponse, String process, Long userId) {
            return LoginResponse.builder()
                    .accessToken(tokenInfoResponse.getAccessToken())
                    .refreshToken(tokenInfoResponse.getRefreshToken())
                    .process(process)
                    .userId(userId)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "사용자 정보 객체")
    public static class GetUserInfoResponse{
        private String name;
        private String nickName;
        private String phoneNumber;
        private boolean gender;
        private String address;
        private String imageUrl;
        private String email;
        private String backgroundColor;

        public static GetUserInfoResponse from(User user){
            return GetUserInfoResponse.builder()
                    .name(user.getName())
                    .nickName(user.getNickName())
                    .phoneNumber(user.getPhoneNumber())
                    .gender(user.isGender())
                    .imageUrl(user.getImageUrl())
                    .email(user.getEmail())
                    .backgroundColor(user.getBackgroundColor())
                    .address(user.getAddress())
                    .build();
        }
    }
}
