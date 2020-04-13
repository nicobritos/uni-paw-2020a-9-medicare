package ar.edu.itba.paw.persistenceAnnotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation enables a given model's field to be treated as a OneToMany relation in the DB.
 * For this to work properly, the model must have been annotated with @Table.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OneToMany {
    /**
     * @return The column's name in the other table
     */
    String name();

    /**
     * @return The other's class' name
     */
    Class<?> className();

    /**
     * If this is set to true, then an exception will be thrown when trying to save a model
     * with the annotated field set to null
     * @return if this field is required
     */
    boolean required() default false;
}
