/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.elijah.nextgen;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.g.GClassInvocation;
import tripleo.elijah.g.GEvaClass;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.nextgen.ClassDefinition;
import tripleo.elijah.nextgen.composable.IComposable;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.ClassInvocation;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.EvaClass;

import java.util.HashSet;
import java.util.Set;

/**
 * Created 3/4/22 7:14 AM
 */
public class ClassDefinition__ implements ClassDefinition {
	private final ClassStatement      primary;
	private final Set<ClassStatement> extended = new HashSet<ClassStatement>();
	private       ClassInvocation     invocation;
	private       EvaClass            node;
	private       IComposable         composable;

	public ClassDefinition__(final @NotNull ClassInvocation aClassInvocation) {
		primary = aClassInvocation.getKlass();
		invocation = aClassInvocation;
	}

	//public ClassDefinition__(final ClassStatement aPrimary) {
	//	primary = aPrimary;
	//}

	@Override
	public IComposable getComposable() {
		return composable;
	}

	@Override
	public @NotNull Set<ClassStatement> getExtended() {
		return extended;
	}

	@Override
	public ClassInvocation getInvocation() {
		return invocation;
	}

	@Override
	public EvaClass getNode() {
		return node;
	}

	@Override
	public ClassStatement getPrimary() {
		return primary;
	}

	@Override
	public void setComposable(final IComposable aComposable) {
		composable = aComposable;
	}

	@Override
	public void setInvocation(final GClassInvocation aInvocation) {
		invocation = (ClassInvocation) aInvocation;
	}

	@Override
	public void setNode(final GEvaClass aNode) {
		node = (EvaClass) aNode;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
