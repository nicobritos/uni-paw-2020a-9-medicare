package ar.edu.itba.paw.persistenceAnnotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    String name() default "";
    boolean required() default false;
    TableRelation relation() default TableRelation.NULL;
    String intermediateTable() default "";
    Class<?> elementClass() default Object.class;
}
