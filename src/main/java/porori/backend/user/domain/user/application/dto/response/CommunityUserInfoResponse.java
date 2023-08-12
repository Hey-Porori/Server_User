package porori.backend.user.domain.user.application.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "사용자 정보 객체")
public class CommunityUserInfoResponse {
    private List<CommunityUserInfoBlocks> communityUserInfoBlocks = new ArrayList<>();
}
