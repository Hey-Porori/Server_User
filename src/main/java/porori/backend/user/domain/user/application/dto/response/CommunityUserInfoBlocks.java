package porori.backend.user.domain.user.application.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class CommunityUserInfoBlocks {
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
