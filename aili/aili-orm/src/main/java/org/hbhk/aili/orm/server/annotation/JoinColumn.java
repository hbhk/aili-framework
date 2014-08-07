package org.hbhk.aili.orm.server.annotation;


import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
@Target({TYPE, METHOD, FIELD})
@Retention(RUNTIME)

public @interface JoinColumn {

    String name() default "";

    String referencedColumnName() default "";

    boolean unique() default false;

    boolean nullable() default true;

    boolean insertable() default true;

    boolean updatable() default true;

    String columnDefinition() default "";
    String table() default "";
}
