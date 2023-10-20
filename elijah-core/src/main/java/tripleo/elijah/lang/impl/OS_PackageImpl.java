/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import tripleo.elijah.contexts.*;
import tripleo.elijah.lang.i.Context;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.lang.i.Qualident;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/*
 * Created on 5/3/2019 at 21:41
 */
public class OS_PackageImpl implements tripleo.elijah.lang.i.OS_Package {
	private final List<OS_Element> elements = new ArrayList<OS_Element>();

	public int __getCode() {
		return _code;
	}

	private final int            _code;
	private Qualident       _name;
	private IPackageContext _ctx;

	// TODO packages, elements

	public OS_PackageImpl(final Qualident aName, final int aCode) {
		_code = aCode;
		_name = aName;
	}

	@Override
	public void addElement(final OS_Element element) {
		elements.add(element);
	}

	//
	// ELEMENTS
	//

	@Override
	public Context getContext() {
		return _ctx;
	}

	@Override
	public @NotNull List<OS_Element> getElements() {
		return elements;
	}

	//
	// NAME
	//

	@Override
	public String getName() {
		if (_name == null) {
//			tripleo.elijah.util.Stupidity.println_err_2("*** name is null for package");
			return "";
		}
		return _name.toString();
	}

	@Override
	public Qualident getName2() {
		return _name;
	}

	@Override
	public void setContext(IPackageContext cur) {
		_ctx = cur;
	}
}

//
//
//
