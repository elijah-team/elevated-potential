package tripleo.elijah.nextgen.reactive;

/**
 * The other base element of <b>Reactive</b>
 *
 * Implement this to have {@link Reactive}s notified of
 * (all) Dimensions.
 * 
 * FYI where does one add {@link ReactiveDimension} ??
 *   answer: CE#addReactiveDimension(...) in your constructor
 *    - looks like a job for AutoService or sisu/goog.inject...
 * TODO add "when" directive/annotation/parameter etc
 *  ie: #when(ReactiveEvent) ...
 *      CE#addReactiveEvent(...)
 */
//@MarkerInterface
public interface ReactiveDimension {
//	com.sun.source.tree.CompilationUnitTree foo();;
}
