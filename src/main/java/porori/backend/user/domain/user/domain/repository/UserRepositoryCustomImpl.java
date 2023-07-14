package porori.backend.user.domain.user.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import porori.backend.user.domain.user.domain.entity.QUser;
import porori.backend.user.domain.user.domain.entity.User;

import javax.persistence.EntityManager;
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
}
