package porori.backend.user.domain.user.application.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import porori.backend.user.domain.user.domain.entity.User;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "사용자 정보 객체")
public class UserInfoResponse {
    private Long userId;
    private String name;
    private String nickName;
    private String phoneNumber;
    private boolean gender;
    private String address;
    private String imageUrl;
    private String email;
    private String backgroundColor;

    public static UserInfoResponse from(User user){
        return UserInfoResponse.builder()
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