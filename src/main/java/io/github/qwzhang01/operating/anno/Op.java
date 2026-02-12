package io.github.qwzhang01.operating.anno;

import io.github.qwzhang01.operating.strategy.DefaultOpStrategy;
import io.github.qwzhang01.operating.strategy.OpStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Operation Record Annotation.
 * <p>
 * This annotation is used to mark methods that require operation recording.
 * It leverages AOP (Aspect-Oriented Programming) to automatically capture
 * operation details before and after method execution.
 *
 * <p>Usage example:
 * <pre>
 * &#64;Op(target = TargetType.class, action = ActionType.class,
 *     strategy = CustomStrategy.class, args = UserDto.class, comparable = true)
 * public void updateUser(UserDto userDto) {
 *     // method implementation
 * }
 * </pre>
 *
 * @author avinzhang
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Op {

    /**
     * Specifies the strategy class to handle the operation recording logic.
     * Defaults to {@link DefaultOpStrategy} if not specified.
     * Custom strategies can be provided by implementing {@link OpStrategy}.
     *
     * @return the strategy class for operation recording
     */
    Class<? extends OpStrategy> strategy() default DefaultOpStrategy.class;

    /**
     * Specifies the argument type to extract from the method parameters.
     * The processor will search for arguments matching this type and pass them
     * to the strategy for recording. Defaults to Object.class if not specified.
     *
     * @return the class type of the argument to extract
     */
    Class<?> args() default Object.class;
}