package tripleo.vendor.com.github.dritter.hd.dlog.internal;

import tripleo.vendor.com.github.dritter.hd.dlog.Parameter;

import java.util.List;

public interface IRuleSafetyValidator {

    /**
     * @return List<Parameter>
     */
    public abstract List<Parameter<?>> getUnlimitedParameters();

}