/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevated_durable.lang_impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.nextgen.names.i.EN_Name;
import tripleo.elijah.lang2.ElElementVisitor;
import tripleo.elijah_fluffy.util.NotImplementedException;

public class FormalArgListItemImpl implements FormalArgListItem {

	private IdentExpression name;
	private @Nullable TypeName tn = null;

	private EN_Name __n;

	@Override
	public @Nullable AccessNotation getAccess() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable El_Category getCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override // OS_Element
	public Context getContext() {
//        throw new NotImplementedException();
//        return null;
		return name.getContext();
	}

	@Override
	public @NotNull EN_Name getEnName() {
		if (__n == null) {
			__n = EN_Name.create(name());
		}
		return __n;
	}

	@Override
	public IdentExpression getNameToken() {
		return name;
	}

	@Override // OS_Element
	public OS_Element getParent() {
		throw new NotImplementedException();
//        return null;
	}

	@Override // OS_Element2
	public @NotNull String name() {
		return name.getText();
	}

	@Override
	public void serializeTo(final SmallWriter sw) {

	}

	@Override
	public void setAccess(AccessNotation aNotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCategory(El_Category aCategory) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setName(final IdentExpression s) {
		name = s;
	}

	@Override
	public void setTypeName(final TypeName tn1) {
		tn = tn1;
	}

	@Override
	public @NotNull String toString() {
		String t;

		if (tn != null) {
			t = tn.toString();
		} else {
			t = "";
		}

		String n = name.getText();
		return n + ":" + t;
	}

	@Override
	public TypeName typeName() {
		return tn;
	}

	@Override // OS_Element
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.visitFormalArgListItem(this);
	}
}

//
//
//
