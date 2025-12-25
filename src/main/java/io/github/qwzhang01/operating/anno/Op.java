package io.github.qwzhang01.operating.anno;

import io.github.qwzhang01.operating.strategy.DefaultOpStrategy;
import io.github.qwzhang01.operating.strategy.OpStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Op {

    Class<? extends Enum> target();

    Class<? extends Enum> action();

    Class<? extends OpStrategy> strategy() default DefaultOpStrategy.class;

    boolean comparable() default false;
}