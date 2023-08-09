package porori.backend.user.domain.user.application.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import porori.backend.user.domain.user.domain.entity.User;
import porori.backend.user.global.dto.TokenInfoResponse;

import java.util.ArrayList;
import java.util.List;

public class UserResponseDto {



    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "로그인을 위한 응답 객체")
    public static class LoginResponse {
        private String accessToken;
        private String refreshToken;
        private boolean registrationStatus;

        public static LoginResponse from(TokenInfoResponse tokenInfoResponse, boolean registrationStatus) {
            return LoginResponse.builder()
                    .accessToken(tokenInfoResponse.getAccessToken())
                    .refreshToken(tokenInfoResponse.getRefreshToken())
                    .registrationStatus(registrationStatus)
                    .build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "사용자 정보 객체")
    public static class GetUserInfoResponse{
        private Long userId;
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
                    .userId(user.getUserId())
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

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(description = "사용자 정보 객체")
    public static class GetCommunityUserInfoResponse {
        private List<CommunityUserInfoBlocks> communityUserInfoBlocks = new ArrayList<>();
    }

    @Getter
    public static class CommunityUserInfoBlocks {
        private Long userId;
        private String image;
        private String backgroundColor;
        private String nickname;

        @QueryProjection
        public CommunityUserInfoBlocks(Long userId, String image, String backgroundColor, String nickname) {
            this.userId = userId;
            this.image = image;
            this.backgroundColor = backgroundColor;
            this.nickname = nickname;
        }
    }


}
