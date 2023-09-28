package tripleo.vendor.com.github.dritter.hd.dlog.internal;

import tripleo.vendor.com.github.dritter.hd.dlog.Parameter;

import java.util.List;

public interface Operator {
    List<Operator> getChildren();

    List<Parameter<?>> getFreeVariables();

    int getArity();
}
