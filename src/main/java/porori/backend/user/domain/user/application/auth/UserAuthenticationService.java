package porori.backend.user.domain.user.application.auth;

import porori.backend.user.domain.user.presentation.dto.req.UserRequestDto.AdditionInfoRequest;
import porori.backend.user.domain.user.presentation.dto.req.UserRequestDto.DeleteAccountRequest;
import porori.backend.user.domain.user.presentation.dto.req.UserRequestDto.LoginRequest;
import porori.backend.user.domain.user.presentation.dto.res.UserResponseDto.LoginResponse;

public interface UserAuthenticationService {

    LoginResponse login(LoginRequest loginRequest);

    LoginResponse signup(AdditionInfoRequest additionInfoRequest);

    void logout(LoginRequest loginRequest);

    void deleteAccount(DeleteAccountRequest deleteAccountRequest);

}
