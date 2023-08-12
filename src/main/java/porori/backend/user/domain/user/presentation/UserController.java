package porori.backend.user.domain.user.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import porori.backend.user.domain.user.application.dto.req.UserRequestDto.*;
import porori.backend.user.domain.user.application.dto.res.ReissueTokenResponse;
import porori.backend.user.domain.user.application.dto.res.UserResponseDto;
import porori.backend.user.domain.user.application.dto.res.UserResponseDto.LoginResponse;
import porori.backend.user.domain.user.application.service.*;
import porori.backend.user.global.config.security.dto.CustomUser;
import porori.backend.user.global.dto.ResponseDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
    public ResponseEntity<ResponseDto> delete(@Valid @RequestBody DeleteAccountRequest deleteAccountRequest) {
        this.userDeleteService.deleteAccount(deleteAccountRequest);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), DELETE_SUCCESS.getMessage()));
    }


    @ApiOperation(value = "사용자 정보 보기", notes = "사용자 정보를 확인합니다.")
    @GetMapping("/token/me")
    public ResponseEntity<ResponseDto<UserResponseDto.GetUserInfoResponse>> getUserInfo(
            @AuthenticationPrincipal CustomUser customUser) {
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_USERINFO_SUCCESS.getMessage(),userInfoService.getUserInfo(customUser.getAppleId())));
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

    @ApiOperation(value="커뮤니티 회원 정보 보기" , notes="커뮤니티 회원 정보 보기")
    @PostMapping("/communities/info")
    public ResponseEntity<ResponseDto<UserResponseDto.GetCommunityUserInfoResponse>> getCommunityUserInfo(
            @Valid @RequestBody GetCommunityUserInfoRequest getCommunityUserInfoRequest){
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), GET_COMMUNITY_USERINFO_SUCCESS.getMessage(), this.userInfoService.getCommunityUserInfo(getCommunityUserInfoRequest)));
    }

    @GetMapping("/reissue")
    public ResponseEntity<ResponseDto<ReissueTokenResponse>> reissue(HttpServletRequest request) {

        // 헤더로부터 RefreshToken 추출.
        String token = request.getHeader("RefreshToken");
        // 토큰 재발행
        ReissueTokenResponse reissueToken = reissueTokenService.reissueToken(token);

        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), REISSUE_TOKEN_SUCCESS.getMessage(), reissueToken));
    }
}

