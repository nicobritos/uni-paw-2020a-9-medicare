package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.ProvinceDao;
import ar.edu.itba.paw.models.Country;
import ar.edu.itba.paw.models.Province;
import ar.edu.itba.paw.models.Province_;
import ar.edu.itba.paw.persistence.generics.GenericSearchableDaoImpl;
import ar.edu.itba.paw.persistence.utils.StringSearchType;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

@Repository
public class ProvinceDaoImpl extends GenericSearchableDaoImpl<Province, Integer> implements ProvinceDao {
    public ProvinceDaoImpl() {
        super(Province.class, Province_.id);
    }

    @Override
    public List<Province> findByCountry(Country country) {
        return this.findBy(Province_.country, country);
    }

    @Override
    public List<Province> findByCountryAndName(Country country, String name) {
        if (country == null || name == null) {
            throw new IllegalArgumentException();
        }
        CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Province> query = builder.createQuery(Province.class);
        Root<Province> root = query.from(Province.class);

        query.select(root);
        query.where(
                builder.and(
                        builder.like(
                                builder.lower(root.get(Province_.name).as(String.class)),
                                StringSearchType.CONTAINS_NO_ACC.transform(name.toLowerCase())
                        ),
                        builder.equal(root.get(Province_.country), country)
                )
        );

        return this.getEntityManager().createQuery(query).getResultList();
    }

    @Override
    protected SingularAttribute<? super Province, ?> getNameAttribute() {
        return Province_.name;
    }

    @Override
    protected void insertOrderBy(CriteriaBuilder builder, CriteriaQuery<Province> query, Root<Province> root) {
        query.orderBy(builder.asc(root.get(Province_.name)));
    }
}
