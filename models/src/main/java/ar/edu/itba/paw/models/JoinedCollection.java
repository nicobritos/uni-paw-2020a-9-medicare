package ar.edu.itba.paw.models;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

/**
 * This class provides a wrapper so that a DAO can keep track of the models added or removed
 * This is done by keeping a copy of the collections in privateModels.
 * Albeit this solution prevents the usage of normal Collections inside models to represent a OneToMany or ManyToMany
 * relation field, this is the easiest and fastest to implement method.
 * @param <M> the model type
 */
public class JoinedCollection<M> {
    /**
     * This provides the field name to be able to access "privateModels" via reflections
     * {@link #privateModels}
     */
    public static final String _PRIVATE_COLLECTION_NAME = "privateModels";
    public static final String _PUBLIC_COLLECTION_NAME = "models";

    private Collection<M> privateModels = new LinkedList<>();
    private Collection<M> models = new LinkedList<>();

    public Collection<M> getModels() {
        return this.models;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinedCollection<?> that = (JoinedCollection<?>) o;
        return Objects.equals(privateModels, that.privateModels) &&
                Objects.equals(models, that.models);
    }

    @Override
    public int hashCode() {
        return Objects.hash(privateModels, models);
    }
}
