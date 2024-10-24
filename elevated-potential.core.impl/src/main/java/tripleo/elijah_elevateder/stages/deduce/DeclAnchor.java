/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.stages.deduce;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;

/**
 * Created 1/3/22 10:31 PM
 */
public class DeclAnchor {
	public enum AnchorType {
		CLOSURE, FOREIGN /* (esp NS, DT/enum) */, INHERITED, MEMBER, PARAMS, VAR
	}

	AnchorType anchorType;

	OS_Element element;
	private IInvocation invocation;

	public DeclAnchor(final OS_Element aElement, final AnchorType aAnchorType) {
		anchorType = aAnchorType;
		element = aElement;
	}

	public IInvocation getInvocation() {
		return invocation;
	}

	public void setInvocation(final IInvocation aInvocation) {
		// assert aInvocation != null; //FIXME/TODO 06/19
		invocation = aInvocation;
	}

	@Override
	public @NotNull String toString() {
		return "DeclAnchor [anchorType=" + anchorType + ", element=" + element + ", invocation=" + invocation + "]";
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
