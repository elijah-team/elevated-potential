/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.stages.deduce.declarations;

import lombok.Getter;
import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.VariableStatement;
import tripleo.elijah_elevateder.stages.deduce.DeduceElementWrapper;
import tripleo.elijah_elevateder.stages.deduce.IInvocation;
import tripleo.elijah_elevateder.stages.gen_fn.EvaNode;
import tripleo.elijah_elevateder.stages.gen_fn.GenType;
import tripleo.elijah_fluffy.diagnostic.Diagnostic;

/**
 * Created 6/27/21 1:41 AM
 */
public class DeferredMember {
	public static class DeferredMemberInjector {

		public DeferredObject<EvaNode, java.lang.Void, java.lang.Void> new_DeferredObject__EvaNode() {
			return new DeferredObject<EvaNode, Void, Void>();
		}

		public DeferredObject<GenType, Diagnostic, Void> new_DeferredObject__GenType() {
			return new DeferredObject<GenType, Diagnostic, Void>();
		}
	}

	private final DeferredObject<GenType, Diagnostic, Void> typePromise = new DeferredObject<>();

	private final DeferredObject<EvaNode, Void, Void> externalRef = new DeferredObject<>();
	@Getter
	private final IInvocation                         invocation;
	@Getter
	private final DeduceElementWrapper                parent;

	@Getter
	private final @NotNull VariableStatement variableStatement;

	private final DeferredMemberInjector __inj = new DeferredMemberInjector();

	public DeferredMember(DeduceElementWrapper aParent, IInvocation aInvocation,
	                      @NotNull VariableStatement aVariableStatement) {
		parent = aParent;
		invocation = aInvocation;
		variableStatement = aVariableStatement;
	}

	public DeferredMemberInjector _inj() {
		return __inj;
	}

	public Promise<EvaNode, Void, Void> externalRef() {
		return externalRef.promise();
	}

	public @NotNull DeferredObject<EvaNode, Void, Void> externalRefDeferred() {
		return externalRef;
	}

	@Override
	public @NotNull String toString() {
		return "DeferredMember{" + "parent=" + parent + ", variableName=" + variableStatement.getName() + '}';
	}

	public @NotNull Promise<GenType, Diagnostic, Void> typePromise() {
		return typePromise;
	}

	// for DeducePhase
	public @NotNull DeferredObject<GenType, Diagnostic, Void> typeResolved() {
		return typePromise;
	}

	public DeduceElementWrapper getParent() {
		// 24/01/04 back and forth
		return this.parent;
	}

	public VariableStatement getVariableStatement() {
		// 24/01/04 back and forth
		return this.variableStatement;
	}

	public IInvocation getInvocation() {
		// 24/01/04 back and forth
		return this.invocation;
	}
}

//
//
//
