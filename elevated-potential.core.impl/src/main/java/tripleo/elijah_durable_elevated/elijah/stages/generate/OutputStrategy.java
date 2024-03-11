/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.elijah.stages.generate;

/**
 * Created 1/8/21 10:31 PM
 */
public class OutputStrategy {
	public enum By {
		BY_EZ, BY_PACKAGE
	}

	public enum Name {
		NAME_CLASS_NAME, NAME_Z_TYPE
	}

	public enum Per {
		PER_CLASS, PER_MODULE, PER_PACKAGE, PER_PROGRAM
	}

	protected By _by = By.BY_EZ;

	protected Per _per = Per.PER_MODULE;

	protected Name _name = Name.NAME_Z_TYPE;

	public By by() {
		return _by;
	}

	public void by(By aBy) {
		_by = aBy;
	}

	public Name name() {
		return _name;
	}

	public void name(Name aName) {
		_name = aName;
	}

	public Per per() {
		return _per;
	}

	public void per(Per aPer) {
		_per = aPer;
	}
}

//
//
//
