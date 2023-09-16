package porori.backend.user.domain.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import porori.backend.user.domain.user.application.dto.request.AdditionInfoRequest;
import porori.backend.user.domain.user.application.dto.request.TestSignUpRequest;
import porori.backend.user.domain.user.domain.entity.UserConstant.RegistrationStatus;
import porori.backend.user.domain.user.domain.entity.UserConstant.Role;
import porori.backend.user.global.exception.LocationLimitException;
import porori.backend.user.global.exception.NotFoundLocationException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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


    //내 위치 설정 (최대 2개)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Location> myLocations = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private RegistrationStatus registrationStatus;

    @Builder
    public User(String appleId, Role role) {
        this.appleId = appleId;
        this.role = role;
        this.registrationStatus=RegistrationStatus.UNCOMPLETED;
    }

    @Builder
    public User(TestSignUpRequest signUpRequest) {
        this.name = signUpRequest.getName();
        this.nickName = signUpRequest.getNickName();
        this.phoneNumber = signUpRequest.getPhoneNumber();
        this.gender = signUpRequest.getGender();
        this.address = signUpRequest.getAddress();
        this.imageUrl = signUpRequest.getImageUrl();
        this.email = signUpRequest.getEmail();
        this.appleId = signUpRequest.getAppleID();
        this.role = Role.ROLE_USER;
    }

    public void signup(AdditionInfoRequest additionInfoRequest) {
        this.name = additionInfoRequest.getName();
        this.nickName = additionInfoRequest.getNickName();
        this.phoneNumber = additionInfoRequest.getPhoneNumber();
        this.gender = additionInfoRequest.getGender();
        this.address = additionInfoRequest.getAddress();
        this.imageUrl = additionInfoRequest.getImageUrl();
        this.email = additionInfoRequest.getEmail();
        this.registrationStatus = RegistrationStatus.COMPLETED;
    }


    public void updateRegistrationStatus() {
        this.registrationStatus = RegistrationStatus.COMPLETED;
    }

    public void withdraw() {
        this.withdrawalStatus = true;
    }

}
