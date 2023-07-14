package porori.backend.user.domain.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import porori.backend.user.domain.user.application.dto.req.UserRequestDto;
import porori.backend.user.domain.user.domain.entity.UserConstant.Role;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String appleId;
    private boolean gender;
    private String address;
    private String imageUrl;
    private String email;
    private String backgroundColor;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean withdrawalStatus;

    @Builder
    public User(String appleId, Role role) {
        this.appleId = appleId;
        this.role = role;
    }

    @Builder
    public User(UserRequestDto.TestSignUpRequest signUpRequest) {
        this.name=signUpRequest.getName();
        this.nickName = signUpRequest.getNickName();
        this.phoneNumber = signUpRequest.getPhoneNumber();
        this.gender = signUpRequest.getGender();
        this.address = signUpRequest.getAddress();
        this.imageUrl = signUpRequest.getImageUrl();
        this.email = signUpRequest.getEmail();
        this.appleId=signUpRequest.getAppleID();
        this.role=Role.ROLE_USER;
    }

    public void signup(String name, String nickName, String phoneNumber, boolean gender, String address, String imageUrl, String email) {
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.address = address;
        this.imageUrl = imageUrl;
        this.email = email;
    }

    public void withdraw(){
        this.withdrawalStatus=true;
    }
}
