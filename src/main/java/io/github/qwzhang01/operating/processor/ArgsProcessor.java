package io.github.qwzhang01.operating.processor;

import io.github.qwzhang01.operating.anno.Op;

/**
 * Arguments Processor for Operation Recording.
 * <p>
 * This processor extracts the relevant argument from the method's parameter
 * list based on the type specified in the {@link Op} annotation's args
 * attribute.
 *
 * <p>The processor searches through all method arguments and returns the first
 * argument whose type matches the configured type in the annotation.
 *
 * <p>This is a utility class with only static methods and should not be
 * instantiated.
 *
 * @author avinzhang
 */
public class ArgsProcessor {

    /**
     * Private constructor to prevent instantiation.
     * This is a utility class with only static methods.
     */
    private ArgsProcessor() {
        throw new UnsupportedOperationException("Utility class cannot be " +
                "instantiated");
    }

    /**
     * Processes method arguments to extract the relevant data for operation
     * recording.
     *
     * <p>This method iterates through all method arguments and returns the
     * first
     * argument that matches the type specified in {@code op.args()}. This
     * allows
     * the framework to automatically extract the relevant business object from
     * the method parameters without manual configuration.
     *
     * @param op   the operation annotation containing the target argument type
     * @param args the array of method arguments to search through
     * @return the first argument matching the configured type, or null if no
     * match is found
     */
    public static Object process(Op op, Object[] args) {

        if (args != null) {
            for (Object arg : args) {
                if (arg != null && op.args().isAssignableFrom(arg.getClass())) {
                    return arg;
                }
            }
        }

        return null;
    }
}
