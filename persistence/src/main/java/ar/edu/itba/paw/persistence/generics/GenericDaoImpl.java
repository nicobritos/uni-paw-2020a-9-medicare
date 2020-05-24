package ar.edu.itba.paw.persistence.generics;

import ar.edu.itba.paw.interfaces.daos.generic.GenericDao;
import ar.edu.itba.paw.models.GenericModel;
import ar.edu.itba.paw.models.ModelMetadata;
import ar.edu.itba.paw.models.Paginator;
import ar.edu.itba.paw.persistence.utils.StringSearchType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * This provides a generic DAO implementation with lots of useful methods
 *
 * @param <M> the DAO model type
 * @param <I> the Model's id type
 */
public abstract class GenericDaoImpl<M extends GenericModel<I>, I> implements GenericDao<M, I> {
    private static final Function<Tuple, ModelMetadata> modelMetadataExtractor = tuple -> {
        Long count;
        Object min, max;
        count = (Long) tuple.get("count");
        max = tuple.get("max");
        min = tuple.get("min");

        return new ModelMetadata(count, min, max);
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final Class<M> mClass;
    private final SingularAttribute<? super M, I> idAttribute;

    public GenericDaoImpl(Class<M> mClass, SingularAttribute<? super M, I> idAttribute) {
        this.mClass = mClass;
        this.idAttribute = idAttribute;
    }

    @Override
    public Optional<M> findById(I id) {
        return Optional.of(this.entityManager.find(this.mClass, id));
    }

    @Override
    public List<M> findByIds(Collection<I> ids) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<M> query = builder.createQuery(this.mClass);
        Root<M> root = query.from(this.mClass);

        query.select(root);
        Path<I> expression = root.get(this.idAttribute);
        Predicate predicate = expression.in(ids);
        query.where(predicate);

        return this.selectQuery(builder, query, root);
    }

    @Override
    public M create(M model) {
        this.entityManager.persist(model);
        return model;
    }

    @Override
    public void update(M model) {
        this.entityManager.persist(model);
    }

    @Override
    public void remove(M model) {
        this.remove(model.getId());
    }

    @Override
    public void remove(I id) {
        M model = this.entityManager.find(this.mClass, id);
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(model);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public List<M> list() {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<M> query = builder.createQuery(this.mClass);
        Root<M> root = query.from(this.mClass);

        query.select(root);

        return this.selectQuery(builder, query, root);
    }

    @Override
    public ModelMetadata count() {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createQuery(Tuple.class);
        Root<M> root = query.from(this.mClass);

        query.multiselect(builder.count(root).alias("count"));
        query.distinct(true);

        return modelMetadataExtractor.apply(this.entityManager.createQuery(query).getSingleResult());
    }

    @Override
    public ModelMetadata count(Map<SingularAttribute<? super M, ?>, Object> parametersValues) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createQuery(Tuple.class);
        Root<M> root = query.from(this.mClass);
        Predicate[] predicates = new Predicate[parametersValues.size()];

        int i = 0;
        for (Map.Entry<SingularAttribute<? super M, ?>, Object> parameter : parametersValues.entrySet()) {
            predicates[i++] = builder.equal(root.get(parameter.getKey()), parameter.getValue());
        }

        query.where(builder.and(predicates));
        return this.count(builder, query, root);
    }

    protected ModelMetadata metadata(SingularAttribute<? super M, ?> countAttribute, SingularAttribute<? super M, Number> minMaxAttribute) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createQuery(Tuple.class);
        Root<M> root = query.from(this.mClass);

        query.multiselect(
                builder.count(root.get(countAttribute)).alias("count"),
                builder.min(root.get(minMaxAttribute)).alias("min"),
                builder.max(root.get(minMaxAttribute)).alias("max")
        );

        return modelMetadataExtractor.apply(this.entityManager.createQuery(query).getSingleResult());
    }

    protected List<M> findBy(SingularAttribute<? super M, ?> attribute, Object value) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<M> query = builder.createQuery(this.mClass);
        Root<M> root = query.from(this.mClass);

        query.select(root);
        query.where(builder.equal(root.get(attribute), value));

        return this.selectQuery(builder, query, root);
    }

    protected List<M> findBy(Map<SingularAttribute<? super M, ?>, Object> parametersValues) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<M> query = builder.createQuery(this.mClass);
        Root<M> root = query.from(this.mClass);
        Predicate[] predicates = new Predicate[parametersValues.size()];
        int i = 0;
        for (Map.Entry<SingularAttribute<? super M, ?>, ?> parameter : parametersValues.entrySet()) {
            predicates[i++] = builder.equal(root.get(parameter.getKey()), parameter.getValue());
        }

        query.select(root);
        query.where(builder.and(predicates));

        return this.selectQuery(builder, query, root);
    }

    protected List<M> findByIn(SingularAttribute<? super M, ?> attribute, Collection<?> values) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<M> query = builder.createQuery(this.mClass);
        Root<M> root = query.from(this.mClass);

        query.select(root);
        Path<?> expression = root.get(attribute);
        Predicate predicate = expression.in(values);
        query.where(predicate);

        return this.selectQuery(builder, query, root);
    }

    protected List<M> findByIgnoreCase(SingularAttribute<? super M, ?> attribute, String value) {
        return this.findByIgnoreCase(attribute, value, StringSearchType.CONTAINS_NO_ACC);
    }

    protected List<M> findByIgnoreCase(SingularAttribute<? super M, ?> attribute, String value, StringSearchType stringSearchType) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<M> query = builder.createQuery(this.mClass);
        Root<M> root = query.from(this.mClass);

        query.select(root);
        query.where(
                builder.like(
                        builder.lower(root.get(attribute).as(String.class)),
                        stringSearchType.transform(value.toLowerCase())
                )
        );

        return this.selectQuery(builder, query, root);
    }

    protected List<M> selectQuery(CriteriaBuilder builder, CriteriaQuery<M> query, Root<M> root) {
        this.insertOrderBy(builder, query, root);
        return this.entityManager.createQuery(query).getResultList();
    }

    protected Paginator<M> selectQuery(CriteriaBuilder builder, CriteriaQuery<M> query, CriteriaQuery<Tuple> tupleQuery, Root<M> root, int page, int pageSize) {
        this.insertOrderBy(builder, query, root);

        List<M> list = this.entityManager.createQuery(query)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        return new Paginator<>(list, page, pageSize, this.selectQueryMetadata(tupleQuery, root).getCount());
    }

    protected Optional<M> selectSingleQuery(CriteriaBuilder builder, CriteriaQuery<M> query, Root<M> root) {
        this.insertOrderBy(builder, query, root);
        return Optional.of(this.entityManager.createQuery(query).getSingleResult());
    }

    protected ModelMetadata selectQueryMetadata(CriteriaQuery<Tuple> query, Root<?> root) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

        query.multiselect(builder.count(root).alias("count"));
        if (query.getRoots().isEmpty())
            query.from(this.mClass);

        return modelMetadataExtractor.apply(this.entityManager.createQuery(query).getSingleResult());
    }

    protected boolean exists(Map<SingularAttribute<? super M, ?>, Object> parametersValues) {
        return this.count(parametersValues).getCount() > 0;
    }

    protected <T> ModelMetadata count(CriteriaBuilder builder, CriteriaQuery<Tuple> query, Root<T> root) {
        query.multiselect(builder.count(root).alias("count"));
        query.distinct(true);
        if (query.getRoots().isEmpty())
            query.from(this.mClass);

        return modelMetadataExtractor.apply(this.entityManager.createQuery(query).getSingleResult());
    }

    protected <T> boolean exists(CriteriaBuilder builder, CriteriaQuery<Tuple> query, Root<T> root) {
        return this.count(builder, query, root).getCount() > 0;
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    protected abstract void insertOrderBy(CriteriaBuilder builder, CriteriaQuery<M> query, Root<M> root);
}
