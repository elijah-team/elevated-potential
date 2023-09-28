package tripleo.vendor.com.github.dritter.hd.dlog.evaluator;

import tripleo.vendor.com.github.dritter.hd.dlog.algebra.ParameterValue;

import java.util.Collection;
import java.util.List;

public interface IFacts {
	String getPredicate();
	int getArity();
	Collection<List<ParameterValue<?>>> getValues();
}
