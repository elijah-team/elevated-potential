package tripleo.vendor.com.github.dritter.hd.dlog.internal;

import tripleo.vendor.com.github.dritter.hd.dlog.IRule;

public interface RuleSafety {
    public static final String UNLIMITED = "unlimited";

    public abstract IRule process(IRule rule);

}