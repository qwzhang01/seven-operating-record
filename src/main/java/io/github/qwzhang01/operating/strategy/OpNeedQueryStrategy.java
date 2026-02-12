package io.github.qwzhang01.operating.strategy;

import io.github.qwzhang01.operating.exception.NonsupportedOpException;

/**
 * Query-based Operation Recording Strategy Interface.
 * <p>
 * This strategy interface specializes in operation recording scenarios that
 * require querying existing data from external sources (typically databases)
 * before method execution to capture the current state for comparison.
 * It extends the base {@link OpStrategy} interface with specific behaviors
 * for query-intensive operations.
 *
 * <p>Key characteristics:
 * <ul>
 *   <li>Requires database queries to fetch current state before execution</li>
 *   <li>Supports comparison between queried old data and method parameters</li>
 *   <li>Does not support return value processing (throws {@link NonsupportedOpException})</li>
 *   <li>Ideal for update operations where current state needs to be compared</li>
 * </ul>
 *
 * <p>Type Parameters:
 * <ul>
 *   <li><b>N</b> - The type of new data from method arguments</li>
 *   <li><b>O</b> - The type of old data queried before method execution</li>
 * </ul>
 *
 * <p>Usage example:
 * <pre>
 * public class UserQueryStrategy implements OpNeedQueryStrategy&lt;UserDto, User&gt; {
 *     &#64;Override
 *     public User beforeAction(UserDto args) {
 *         // Query current user data from database
 *         return userRepository.findById(args.getId());
 *     }
 *
 *     &#64;Override
 *     public void afterAction(User oldData, UserDto newData) {
 *         // Compare queried data with new parameters
 *         logDatabaseChanges(oldData, newData);
 *     }
 * }
 * </pre>
 *
 * @param <N> the type of new data from method arguments
 * @param <O> the type of old data queried before method execution
 * @author avinzhang
 */
public interface OpNeedQueryStrategy<N, O, Void>
        extends OpStrategy<N, O, Void> {

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
    default O beforeAction(N args) {
        return null;
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
    default O beforeAction(String clazz, String method, N args) {
        return null;
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
    default void afterAction(N args) {
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
    default void afterAction(String clazz, String method, N args) {
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
    default void afterAction(O dbData, N args) {
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
                             O dbData, N args) {
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
    default void afterReturn(Void returnData) {
        throw new NonsupportedOpException();
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
    default void afterReturn(String clazz, String method, Void returnData) {
        throw new NonsupportedOpException();
    }
}