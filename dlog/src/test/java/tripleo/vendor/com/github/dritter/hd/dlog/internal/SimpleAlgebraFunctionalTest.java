package tripleo.vendor.com.github.dritter.hd.dlog.internal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import tripleo.vendor.com.github.dritter.hd.dlog.algebra.ParameterValue;

public class SimpleAlgebraFunctionalTest {
    @Test
	public void testNumericParameterValue_Equals() throws Exception {
    	int value = 1;
		assertTrue(ParameterValue.create(value).equals(ParameterValue.create(value)));
    }
    
    @Test
	public void testNumericParameterValue_NotEquals() throws Exception {
    	int refValue = 1;
		int value = 2;
		assertFalse(ParameterValue.create(refValue).equals(ParameterValue.create(value)));
    }
}
