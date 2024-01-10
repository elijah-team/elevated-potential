package tripleo.elijah.util;

/**
 * Prompt:
 * <p>Write a javadoc for this class and summarize the usages of the instances of this exception in the javadoc</p>
 *
 * <p>This exception is thrown when a program is likely to be wrong or has encountered an unexpected condition.</p>
 * <p>It is a subclass of the RuntimeException class.</p>
 *
 * <p>
 * Instances of this exception are typically used to indicate that there is a logical error or an unexpected situation
 * in the program that cannot be handled at runtime. This exception should be thrown when the program encounters a
 * condition that violates its assumptions or when it is unable to proceed due to an unexpected state.
 * </p>
 *
 * <p>
 * This exception can be caught and handled by the calling code, allowing it to take appropriate action such as logging
 * the error, displaying an error message to the user, or terminating the program gracefully.
 * </p>
 *
 * <p>
 * It is recommended to provide a descriptive error message when throwing this exception to help with debugging and
 * troubleshooting.
 * </p>
 *
 * @see RuntimeException
 */
public class ProgramIsLikelyWrong extends RuntimeException {
}
