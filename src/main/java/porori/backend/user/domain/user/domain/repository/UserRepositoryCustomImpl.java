package porori.backend.user.domain.user.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import porori.backend.user.domain.user.application.dto.response.CommunityUserInfoBlocks;
import porori.backend.user.domain.user.application.dto.response.CommunityUserInfoResponse;
import porori.backend.user.domain.user.application.dto.response.QCommunityUserInfoBlocks;
import porori.backend.user.domain.user.domain.entity.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static porori.backend.user.domain.user.domain.entity.QUser.user;


public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Optional<User> findNotWithdrawByAppleId(String appleId) {
        return Optional.ofNullable(queryFactory.selectFrom(user)
                .where(user.appleId.eq(appleId),
                        user.withdrawalStatus.eq(false))
                .fetchFirst());
    }

    @Override
    public CommunityUserInfoResponse findCommunityUserInfoByUserIdList(List<Long> userIdList) {
    List<CommunityUserInfoBlocks> blocks = queryFactory.select(new QCommunityUserInfoBlocks(
                        user.userId,
                        user.imageUrl,
                        user.backgroundColor,
                        user.nickName))
                .from(user)
                .where(user.userId.in(userIdList)) // userIdList 내의 userId들을 기준으로 조회
                .fetch();
        return new CommunityUserInfoResponse(blocks);
    }


}
