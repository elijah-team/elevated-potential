package tripleo.elijah_elevateder.stages.deduce.post_bytecode;

import org.jdeferred2.Promise;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_fluffy.util.NotImplementedException;
import tripleo.elijah_elevated_durable.lang_impl.AliasStatementImpl;
import tripleo.elijah_elevateder.stages.deduce.*;

public class DG_AliasStatement implements DG_Item {
	private final          AliasStatementImpl                          aliasStatement;
	private @NotNull       DeferredObject<DG_Item, ResolveError, Void> _resolvePromise = new DeferredObject<>();
	private                boolean                                     _resolveStarted;
	private final @NotNull DeduceTypes2                                __dt2;

	public DG_AliasStatement(final AliasStatementImpl aAliasStatement, final @NotNull DeduceTypes2 aDt2) {
		aliasStatement = aAliasStatement;
		__dt2 = aDt2;
	}

	public Promise<DG_Item, ResolveError, Void> resolvePromise() {
		if (!_resolveStarted) {
			_resolveStarted = true;
			try {
				final @Nullable OS_Element ra = DeduceLookupUtils._resolveAlias2(aliasStatement, __dt2);

				if (ra instanceof ClassStatement) {
					_resolvePromise.resolve(__dt2.DG_ClassStatement((ClassStatement) ra));
				} else if (ra instanceof FunctionDef) {
					_resolvePromise.resolve(__dt2.DG_FunctionDef((FunctionDef) ra));
				} else {
					throw new NotImplementedException();
				}
			} catch (ResolveError aE) {
				_resolvePromise.reject(aE);
			}
		}
		return _resolvePromise;
	}
}
