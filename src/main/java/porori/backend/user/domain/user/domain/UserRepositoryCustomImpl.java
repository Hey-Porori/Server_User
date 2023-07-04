package porori.backend.user.domain.user.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Optional;

import static porori.backend.user.domain.user.domain.QUser.user;

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
}
