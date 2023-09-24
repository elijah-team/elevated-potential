/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.nextgen.reactive.DefaultReactive;
import tripleo.elijah.nextgen.reactive.Reactive;
import tripleo.elijah.stages.garish.GarishNamespace_Generator;
import tripleo.elijah.stages.gen_generic.CodeGenerator;
import tripleo.elijah.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.Maybe;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.world.impl.DefaultLivingNamespace;

import java.util.function.Consumer;

/**
 * Created 12/22/20 5:39 PM
 */
public class EvaNamespace extends EvaContainerNC implements GNCoded {
	class _Reactive_EvaNamespace extends DefaultReactive {
		@Override
		public <T> void addListener(final Consumer<T> t) {
			throw new UnintendedUseException();
		}
	}

	public DefaultLivingNamespace _living;
	private final OS_Module module;

	private final NamespaceStatement namespaceStatement;

	private _Reactive_EvaNamespace reactiveEvaNamespace = new _Reactive_EvaNamespace();

	private final GarishNamespace_Generator _gng = new GarishNamespace_Generator(this);

	public EvaNamespace(NamespaceStatement aNamespaceStatement, OS_Module aModule) {
		namespaceStatement = aNamespaceStatement;
		module = aModule;
	}

	public void addAccessNotation(AccessNotationImpl an) {
		throw new NotImplementedException();
	}

	public void createCtor0() {
		// TODO implement me
		FunctionDef fd = new FunctionDefImpl(namespaceStatement, namespaceStatement.getContext());
		fd.setName(Helpers.string_to_ident("<ctor$0>"));
		Scope3Impl scope3 = new Scope3Impl(fd);
		fd.scope(scope3);
		for (VarTableEntry varTableEntry : varTable) {
			if (varTableEntry.initialValue != IExpression.UNASSIGNED) {
				IExpression left = varTableEntry.nameToken;
				IExpression right = varTableEntry.initialValue;

				IExpression e = ExpressionBuilder.build(left, ExpressionKind.ASSIGNMENT, right);
				scope3.add(new StatementWrapperImpl(e, fd.getContext(), fd));
			} else {
				if (getPragma("auto_construct")) {
					scope3.add(new ConstructStatementImpl(fd, fd.getContext(), varTableEntry.nameToken, null, null));
				}
			}
		}
	}

	@Override
	public void generateCode(final GenerateResultEnv aFileGen, final CodeGenerator aGgc) {
		throw new NotImplementedException();
	}

	public GarishNamespace_Generator generator() {
		return _gng;
	}

	@Override
	public int getCode() {
		return _living.getCode();
	}

	@Override
	public OS_Element getElement() {
		return getNamespaceStatement();
	}

	public DefaultLivingNamespace getLiving() {
		return _living;
	}

	public String getName() {
		return namespaceStatement.getName();
	}

	public NamespaceStatement getNamespaceStatement() {
		return this.namespaceStatement;
	}

	private boolean getPragma(String auto_construct) { // TODO this should be part of ContextImpl
		return false;
	}

	@Override
	public @NotNull Role getRole() {
		return Role.NAMESPACE;
	}

	@Override
	public @NotNull Maybe<VarTableEntry> getVariable(String aVarName) {
		for (VarTableEntry varTableEntry : varTable) {
			if (varTableEntry.nameToken.getText().equals(aVarName))
				return new Maybe<>(varTableEntry, null);
		}
		return new Maybe<>(null, _def_VarNotFound);
	}

	@Override
	public String identityString() {
		return String.valueOf(namespaceStatement);
	}

	@Override
	public OS_Module module() {
		return module;
	}

	public Reactive reactive() {
		return reactiveEvaNamespace;
	}

	@Override
	public void register(final @NotNull ICodeRegistrar aCr) {
		aCr.registerNamespace(this);
	}

	@Override
	public void setCode(final int aCode) {
		_living.setCode(aCode);
	}
}

//
//
//
