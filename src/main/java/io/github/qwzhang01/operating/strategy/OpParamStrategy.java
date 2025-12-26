package io.github.qwzhang01.operating.strategy;

import io.github.qwzhang01.operating.exception.NonsupportedOpException;

/**
 * Operation Recording Strategy Interface.
 * <p>
 * This interface defines the contract for custom operation recording
 * strategies.
 * Implementations can customize how operations are captured, compared, and
 * recorded
 * throughout the method execution lifecycle.
 *
 * <p>The strategy provides hooks at three key points:
 * <ol>
 *   <li><b>Before execution</b> - Capture the current state of data</li>
 *   <li><b>After execution</b> - Record changes by comparing old and new
 *   data</li>
 *   <li><b>After return</b> - Process the method's return value</li>
 * </ol>
 *
 * <p>Type Parameters:
 * <ul>
 *   <li><b>N</b> - The type of new data (method arguments)</li>
 *   <li><b>O</b> - The type of old data (captured before execution)</li>
 *   <li><b>R</b> - The type of return data (method return value)</li>
 * </ul>
 *
 * <p>All methods have default implementations that do nothing, allowing
 * implementations to override only the methods they need.
 *
 * <p>Usage example:
 * <pre>
 * public class UserOpStrategy implements OpStrategy&lt;UserDto, User, Boolean&gt; {
 *     &#64;Override
 *     public User beforeAction(UserDto args) {
 *         // Fetch current user data from database
 *         return userRepository.findById(args.getId());
 *     }
 *
 *     &#64;Override
 *     public void afterAction(User oldData, UserDto newData) {
 *         // Compare and log the differences
 *         logChanges(oldData, newData);
 *     }
 * }
 * </pre>
 *
 * @param <P> the type of old data captured before method execution
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
     * @param args the arguments extracted from the method parameters
     * @return the current state to be compared later, or null if not needed
     */
    @Override
    default P beforeAction(P args) {
        return args;
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
    default P beforeAction(String clazz, String method, P args) {
        return args;
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
    default void afterAction(P args) {
    }

    /**
     * Records operation after method execution with context information.
     *
     * @param clazz  the fully qualified name of the class containing the method
     * @param method the name of the method being executed
     * @param args   the new data extracted from method arguments
     */
    @Override
    default void afterAction(String clazz, String method, P args) {
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
    default void afterAction(P dbData, P args) {
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
                             P dbData, P args) {
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