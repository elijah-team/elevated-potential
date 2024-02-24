package tripleo.elijah.ci_impl;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.GenerateStatement;
import tripleo.elijah.xlang.LocatableString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created 9/6/20 12:04 PM
 */
public class GenerateStatementImpl implements GenerateStatement {
	@Override
	public void addDirective(LocatableString token, CiExpression expression) {
		dirs.add(new Directive(token, expression));
	}

	@Override
	public Stream<Directive> dirStream() {
		return dirs.stream();
	}

	public final List<Directive> dirs = new ArrayList<Directive>();
}

//
//
//
