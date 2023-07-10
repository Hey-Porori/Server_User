package porori.backend.user.domain.user.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import porori.backend.user.domain.user.application.auth.UserAuthenticationService;
import porori.backend.user.domain.user.presentation.dto.req.UserRequestDto;
import porori.backend.user.domain.user.presentation.dto.res.UserResponseDto;
import porori.backend.user.global.dto.ResponseDto;

import javax.validation.Valid;

import static porori.backend.user.domain.user.presentation.constant.EUserResponseMessage.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
@Api(tags = "User API")
public class UserController {

    private final UserAuthenticationService authenticationService;

    @ApiOperation(value = "애플 로그인", notes = "애ㅇ 로그인을 합니다.")
    @PostMapping("/auth/apple")
    public ResponseEntity<ResponseDto<UserResponseDto.LoginResponse>> login(@Valid @RequestBody UserRequestDto.LoginRequest loginRequest) {
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), LOGIN_SUCCESS.getMessage(), this.authenticationService.login(loginRequest)));
    }

    @ApiOperation(value = "추가 정보 입력", notes = "추가 정보를 입력합니다.")
    @PostMapping("/additional-info")
    public ResponseEntity<ResponseDto<UserResponseDto.LoginResponse>> additionalInfo(@Valid @RequestBody UserRequestDto.AdditionInfoRequest additionInfoRequest) {
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), SIGN_UP_SUCCESS.getMessage(), this.authenticationService.signup(additionInfoRequest)));
    }


    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴를 합니다.")
    @DeleteMapping
    public ResponseEntity<ResponseDto> delete(@Valid @RequestBody UserRequestDto.DeleteAccountRequest deleteAccountRequest) {
        this.authenticationService.deleteAccount(deleteAccountRequest);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(), DELETE_SUCCESS.getMessage()));
    }


    @ApiOperation(value = "로그아웃", notes = "로그아웃을 합니다.")
    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout(@Valid @RequestBody UserRequestDto.LoginRequest loginRequest){
        this.authenticationService.logout(loginRequest);
        return ResponseEntity.ok(ResponseDto.create(HttpStatus.OK.value(),LOGOUT_SUCCESS.getMessage()));
    }

    //토큰 받고 -> 사용자 정보 넘겨주기


}

