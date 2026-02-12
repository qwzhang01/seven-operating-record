package io.github.qwzhang01.operating.strategy;

import io.github.qwzhang01.operating.exception.NonsupportedOpException;

/**
 * Parameter-based Operation Recording Strategy Interface.
 * <p>
 * This strategy interface specializes in operation recording scenarios where
 * the method parameters themselves contain all the necessary information
 * for recording operations. It extends {@link OpNeedQueryStrategy} and
 * provides default implementations optimized for parameter-only scenarios.
 *
 * <p>Key characteristics:
 * <ul>
 *   <li>Uses method parameters as both old and new data for comparison</li>
 *   <li>Does not support return value processing (throws {@link NonsupportedOpException})</li>
 *   <li>Ideal for simple CRUD operations where parameters contain complete state</li>
 *   <li>Simplifies implementation by eliminating the need for database queries</li>
 * </ul>
 *
 * <p>Type Parameters:
 * <ul>
 *   <li><b>P</b> - The type of data used for both old and new state (method parameters)</li>
 * </ul>
 *
 * <p>Usage example:
 * <pre>
 * public class UserParamStrategy implements OpParamStrategy&lt;UserDto&gt; {
 *     &#64;Override
 *     public void afterAction(UserDto oldData, UserDto newData) {
 *         // Compare parameter changes directly without database queries
 *         logParameterChanges(oldData, newData);
 *     }
 * }
 * </pre>
 *
 * @param <P> the type of data used for both old and new state (method parameters)
 * @author avinzhang
 */
public interface OpParamStrategy<P, Void> extends OpNeedQueryStrategy<P,
        P, Void> {

    /**
     * Captures the current state before method execution.
     * <p>
     * This method is called when {@code comparable} is enabled in the
     * {@link io.github.qwzhang01.operating.anno.Op} annotation.
     *
     * @param methodArgs the arguments extracted from the method parameters
     * @return the current state to be compared later, or null if not needed
     */
    @Override
    default P beforeAction(P methodArgs) {
        return methodArgs;
    }

    /**
     * Captures the current state before method execution with class and
     * method context.
     * <p>
     * This overloaded version provides additional context about which class
     * and method is being executed, useful for context-aware recording.
     *
     * @param clazz      the fully qualified name of the class containing the
     *                   method
     * @param method     the name of the method being executed
     * @param methodArgs the arguments extracted from the method parameters
     * @return the current state to be compared later, or null if not needed
     */
    @Override
    default P beforeAction(String clazz, String method, P methodArgs) {
        return methodArgs;
    }

    /**
     * Records operation after method execution with only new data.
     * <p>
     * This method is called when no comparison is needed (when
     * {@code comparable} is false or no old data was captured).
     *
     * @param methodArgs the new data extracted from method arguments
     */
    @Override
    default void afterAction(P methodArgs) {
    }

    /**
     * Records operation after method execution with context information.
     *
     * @param clazz      the fully qualified name of the class containing the
     *                   method
     * @param method     the name of the method being executed
     * @param methodArgs the new data extracted from method arguments
     */
    @Override
    default void afterAction(String clazz, String method, P methodArgs) {
    }

    /**
     * Records operation after method execution with both old and new data.
     * <p>
     * This method is called when comparison is enabled, allowing the strategy
     * to analyze differences between the old and new state.
     *
     * @param beforeDbData the old data captured before method execution
     * @param methodArgs   the new data extracted from method arguments
     */
    @Override
    default void afterAction(P beforeDbData, P methodArgs) {
        throw new NonsupportedOpException();
    }

    /**
     * Records operation after method execution with old data, new data, and
     * context.
     * <p>
     * This is the most comprehensive variant, providing all available
     * information
     * including class/method context and both old and new data states.
     *
     * @param clazz        the fully qualified name of the class containing
     *                     the method
     * @param method       the name of the method being executed
     * @param beforeDbData the old data captured before method execution
     * @param methodArgs   the new data extracted from method arguments
     */
    @Override
    default void afterAction(String clazz, String method,
                             P beforeDbData, P methodArgs) {
        throw new NonsupportedOpException();
    }

    /**
     * Processes the return value after method execution.
     * <p>
     * This method is called with the method's return value, allowing the
     * strategy
     * to record or react to the execution result.
     *
     * @param methodReturn the value returned by the method
     */
    @Override
    default void afterReturn(Void methodReturn) {
        throw new NonsupportedOpException();
    }

    /**
     * Processes the return value with class and method context.
     *
     * @param clazz        the fully qualified name of the class containing the
     *                     method
     * @param method       the name of the method being executed
     * @param methodReturn the value returned by the method
     */
    @Override
    default void afterReturn(String clazz, String method, Void methodReturn) {
        throw new NonsupportedOpException();
    }
}