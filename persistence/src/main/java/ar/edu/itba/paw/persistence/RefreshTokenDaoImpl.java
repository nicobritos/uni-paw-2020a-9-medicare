package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.RefreshTokenDao;
import ar.edu.itba.paw.models.RefreshToken;
import ar.edu.itba.paw.models.RefreshToken_;
import ar.edu.itba.paw.persistence.generics.GenericDaoImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class RefreshTokenDaoImpl extends GenericDaoImpl<RefreshToken, Integer> implements RefreshTokenDao {
    public RefreshTokenDaoImpl() {
        super(RefreshToken.class, RefreshToken_.id);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        if (token == null) {
            throw new IllegalArgumentException();
        }
        return this.findBy(RefreshToken_.token, token).stream().findFirst();
    }

    @Override
    protected void insertOrderBy(CriteriaBuilder builder, CriteriaQuery<RefreshToken> query, Root<RefreshToken> root) {
    }
}