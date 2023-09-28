package tripleo.vendor.com.github.dritter.hd.dlog;

import tripleo.vendor.com.github.dritter.hd.dlog.algebra.DataIterator;

public interface IFacts {

    /**
     * @return the predicate
     */
    Predicate getPredicate();

    /**
     * @return the values
     */
    DataIterator getValues();

}