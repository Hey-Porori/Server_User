package porori.backend.user.domain.user.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TestSignUpRequest {

    private String name;
    private String phoneNumber;
    private String appleID;
    private String nickName;
    private Boolean gender;
    private String imageUrl;
    private String email;
    private String address;


}