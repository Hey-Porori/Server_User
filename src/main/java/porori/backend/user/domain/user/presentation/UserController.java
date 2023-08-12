package porori.backend.user.domain.user.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import porori.backend.user.domain.user.application.dto.request.*;
import porori.backend.user.domain.user.application.dto.response.*;
import porori.backend.user.domain.user.application.service.*;
import porori.backend.user.global.config.security.dto.CustomUser;
import porori.backend.user.global.dto.ResponseDto;

import javax.validation.Valid;

import java.util.List;

import static porori.backend.user.domain.user.presentation.constant.EUserResponseMessage.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
@Api(tags = "User API")
public class UserController {

    private final UserLoginService userLoginService;
    private final UserSignUpService userSignUpService;
    private final UserDeleteService userDeleteService;
    private final UserInfoService userInfoService;
    private final ReissueTokenService reissueTokenService;
    private final UserLocationService userLocationService;


    @ApiOperation(value = "애플 로그인", notes = "애플 로그인을 합니다.")
    @PostMapping("/auth/apple")
    public ResponseEntity<ResponseDto<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LOGIN_SUCCESS.getMessage(), this.userLoginService.login(loginRequest)));
    }

    @ApiOperation(value = "추가 정보 입력", notes = "추가 정보를 입력합니다.")
    @PutMapping("/additional-info")
    public ResponseEntity<ResponseDto<LoginResponse>> additionalInfo(@Valid @RequestBody AdditionInfoRequest additionInfoRequest) {
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), SIGN_UP_SUCCESS.getMessage(), this.userSignUpService.signup(additionInfoRequest)));
    }


    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴를 합니다.")
    @DeleteMapping
    public ResponseEntity<ResponseDto> delete(@AuthenticationPrincipal CustomUser customUser) {
        this.userDeleteService.deleteAccount(customUser.getAppleId());
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), DELETE_SUCCESS.getMessage()));
    }


    @ApiOperation(value = "사용자 정보 보기", notes = "사용자 정보를 확인합니다.")
    @GetMapping("/token/me")
    public ResponseEntity<ResponseDto<UserInfoResponse>> getUserInfo(
            @AuthenticationPrincipal CustomUser customUser) {
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_USERINFO_SUCCESS.getMessage(), userInfoService.getUserInfo(customUser.getAppleId())));
    }

    @ApiOperation(value = "(테스트)회원가입", notes = "회원가입을 합니다")
    @PostMapping("/test/signup")
    public ResponseEntity<ResponseDto<LoginResponse>> signUp(@Valid @RequestBody TestSignUpRequest testSignUpRequest) {
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), SIGN_UP_SUCCESS.getMessage(), this.userSignUpService.testSignUp(testSignUpRequest)));
    }

    @ApiOperation(value = "(테스트)로그인", notes = "로그인을 합니다")
    @PostMapping("/test/login")
    public ResponseEntity<ResponseDto<LoginResponse>> login(@Valid @RequestBody TestLoginRequest testLoginRequest) {
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LOGIN_SUCCESS.getMessage(), this.userLoginService.testLogin(testLoginRequest)));
    }

    @ApiOperation(value = "(테스트)jwt", notes = "jwt 테스트를 합니다")
    @PostMapping("/test/jwt")
    public ResponseEntity<ResponseDto<LoginResponse>> jwtTokenTest() {
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), VALID_TOKEN.getMessage()));
    }

    @ApiOperation(value = "커뮤니티 회원 정보 보기", notes = "커뮤니티 회원 정보 보기")
    @GetMapping("/communities/info")
    public ResponseEntity<ResponseDto<CommunityUserInfoResponse>> getCommunityUserInfo(@RequestParam List<Long> userIdList) {
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_COMMUNITY_USERINFO_SUCCESS.getMessage(), this.userInfoService.getCommunityUserInfo(userIdList)));
    }

    @ApiOperation(value = "토큰 재발급", notes = "토큰을 재발급합니다.")
    @GetMapping("/reissue")
    public ResponseEntity<ResponseDto<ReissueTokenResponse>> reissue(@RequestHeader(value = "RefreshToken") String token) {
        ReissueTokenResponse reissueToken = reissueTokenService.reissueToken(token);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), REISSUE_TOKEN_SUCCESS.getMessage(), reissueToken));
    }

    @ApiOperation(value = "사용자 위치 보기", notes = "사용자 위치를 확인합니다.")
    @GetMapping("/locations")
    public ResponseEntity<ResponseDto<UserLocationResponse>> getUserLocation(
            @AuthenticationPrincipal CustomUser customUser) {
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_USERLOCATION_SUCCESS.getMessage(), userLocationService.getUserLocation(customUser.getAppleId())));
    }

    @ApiOperation(value = "사용자 위치 조회", notes = "사용자 위치를 조회합니다.")
    @PostMapping("/locations")
    public ResponseEntity<ResponseDto> addUserLocation(@AuthenticationPrincipal CustomUser customUser,
                                                       @Valid @RequestBody UserLocationRequest userLocationRequest) {
        userLocationService.addUserLocation(customUser.getAppleId(), userLocationRequest);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), ADD_USERLOCATION_SUCCESS.getMessage()));
    }
    @ApiOperation(value = "사용자 위치 수정", notes = "사용자 위치를 수정합니다.")
    @PutMapping("/locations/{locationId}")
    public ResponseEntity<ResponseDto> updateUserLocation(@AuthenticationPrincipal CustomUser customUser,
                                                          @PathVariable Long locationId,
                                                          @Valid @RequestBody UserLocationRequest userLocationRequest) {
        userLocationService.updateUserLocation(customUser.getAppleId(), locationId, userLocationRequest);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), UPDATE_USERLOCATION_SUCCESS.getMessage()));
    }

    @ApiOperation(value = "사용자 위치 삭제", notes = "사용자 위치를 삭제합니다.")
    @DeleteMapping("/locations/{locationId}")
    public ResponseEntity<ResponseDto> deleteUserLocation(@AuthenticationPrincipal CustomUser customUser,
                                                          @PathVariable Long locationId) {
        userLocationService.deleteUserLocation(customUser.getAppleId(), locationId);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), DELETE_USERLOCATION_SUCCESS.getMessage()));
    }

}

