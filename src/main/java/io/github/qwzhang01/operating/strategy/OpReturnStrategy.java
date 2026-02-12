package io.github.qwzhang01.operating.strategy;

import io.github.qwzhang01.operating.exception.NonsupportedOpException;

/**
 * Return-based Operation Recording Strategy Interface.
 * <p>
 * This strategy interface specializes in operation recording scenarios that
 * focus on processing method return values rather than parameter comparisons.
 * It extends the base {@link OpStrategy} interface with specific behaviors
 * for return value-centric operations.
 *
 * <p>Key characteristics:
 * <ul>
 *   <li>Focuses exclusively on method return values for operation recording</li>
 *   <li>Does not support parameter-based operations (throws {@link NonsupportedOpException})</li>
 *   <li>Does not support before-execution data capture</li>
 *   <li>Ideal for query operations, creation operations, or any scenario where
 *   the return value contains the operation result</li>
 * </ul>
 *
 * <p>Type Parameters:
 * <ul>
 *   <li><b>R</b> - The type of return data from method execution</li>
 * </ul>
 *
 * <p>Usage example:
 * <pre>
 * public class UserReturnStrategy implements OpReturnStrategy&lt;User&gt; {
 *     &#64;Override
 *     public void afterReturn(User returnData) {
 *         // Process and record the returned user data
 *         logUserCreation(returnData);
 *     }
 * }
 * </pre>
 *
 * @param <R> the type of return data from method execution
 * @author avinzhang
 */
public interface OpReturnStrategy<Void, R> extends OpStrategy<Void, Void, R> {

    /**
     * Captures the current state before method execution.
     * <p>
     * This method is called when {@code comparable} is enabled in the
     * {@link io.github.qwzhang01.operating.anno.Op} annotation.
     *
     * @param args the arguments extracted from the method parameters
     * @return the current state to be compared later, or null if not needed
     */
    @Override
    default Void beforeAction(Void args) {
        throw new NonsupportedOpException();
    }

    /**
     * Captures the current state before method execution with class and
     * method context.
     * <p>
     * This overloaded version provides additional context about which class
     * and method is being executed, useful for context-aware recording.
     *
     * @param clazz  the fully qualified name of the class containing the method
     * @param method the name of the method being executed
     * @param args   the arguments extracted from the method parameters
     * @return the current state to be compared later, or null if not needed
     */
    @Override
    default Void beforeAction(String clazz, String method, Void args) {
        throw new NonsupportedOpException();
    }

    /**
     * Records operation after method execution with only new data.
     * <p>
     * This method is called when no comparison is needed (when
     * {@code comparable} is false or no old data was captured).
     *
     * @param args the new data extracted from method arguments
     */
    @Override
    default void afterAction(Void args) {
        throw new NonsupportedOpException();
    }

    /**
     * Records operation after method execution with context information.
     *
     * @param clazz  the fully qualified name of the class containing the method
     * @param method the name of the method being executed
     * @param args   the new data extracted from method arguments
     */
    @Override
    default void afterAction(String clazz, String method, Void args) {
        throw new NonsupportedOpException();
    }

    /**
     * Records operation after method execution with both old and new data.
     * <p>
     * This method is called when comparison is enabled, allowing the strategy
     * to analyze differences between the old and new state.
     *
     * @param dbData the old data captured before method execution
     * @param args   the new data extracted from method arguments
     */
    @Override
    default void afterAction(Void dbData, Void args) {
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
     * @param clazz  the fully qualified name of the class containing the method
     * @param method the name of the method being executed
     * @param dbData the old data captured before method execution
     * @param args   the new data extracted from method arguments
     */
    @Override
    default void afterAction(String clazz, String method,
                             Void dbData, Void args) {
        throw new NonsupportedOpException();
    }

    /**
     * Processes the return value after method execution.
     * <p>
     * This method is called with the method's return value, allowing the
     * strategy
     * to record or react to the execution result.
     *
     * @param returnData the value returned by the method
     */
    @Override
    default void afterReturn(R returnData) {

    }

    /**
     * Processes the return value with class and method context.
     *
     * @param clazz      the fully qualified name of the class containing the
     *                   method
     * @param method     the name of the method being executed
     * @param returnData the value returned by the method
     */
    @Override
    default void afterReturn(String clazz, String method, R returnData) {

    }
}