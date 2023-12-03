package tripleo.vendor.com.github.dritter.hd.dlog.internal;

import org.junit.jupiter.api.Test;
import tripleo.vendor.com.github.dritter.hd.dlog.*;
import tripleo.vendor.com.github.dritter.hd.dlog.algebra.DataIterator;
import tripleo.vendor.com.github.dritter.hd.dlog.utils.Utils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuiltInPrediateContainsTest {

	@Test
    public void testEvaluatingSelectionWithStringArgumentsInContainsCondition() {
        
        /*
         * p(X, Y) :- r(X, Y) & X =c c.
         * 
         * r(a, b).
         * r(c, d).
         * r(dcd, d).
         * r(e, f).
         * r(g, h).
         * 
         * Expected IDB facts:
         * 
         * p(c, d).
         * p(dcd, d).
         */
        
        Predicate p = Predicate.create("p", 2);
        Predicate r = Predicate.create("r", 2);
        
        Parameter<?> argumentX = Parameter.createVariable("X");
        Parameter<?> argumentY = Parameter.createVariable("Y");
        Parameter<?> argumentc = Parameter.createConstant("c");
        
        Literal head     = Literal.create(p, argumentX, argumentY);
        Literal subgoal1 = Literal.create(r, argumentX, argumentY);
        Literal subgoal2 = Literal.create(BuiltInPredicates.CONTAINS, argumentX, argumentc);
        
        IRule rule = Rule.create(head, subgoal1, subgoal2);
        
        List<IRule> rules = new ArrayList<IRule>();
        rules.add(rule);
        
        String[][] relationR = {
            {"a", "b"},
            {"c", "d"},
            {"dcd", "d"},
            {"e", "f"},
            {"g", "h"}
        };
        
        DataIterator relationRIterator = Utils.createRelationIterator(relationR);
        
        IFacts relationRFacts = Facts.create(r, relationRIterator);
        
        Collection<IFacts> edbRelations = new ArrayList<IFacts>();
        edbRelations.add(relationRFacts);
        
        IEvaluator evaluator = new NonRecursiveEvaluator(rules);
        Collection<IFacts> idbRelations = evaluator.eval(edbRelations);
        
        assertEquals(1, idbRelations.size());
        for (IFacts relation : idbRelations) {
            DataIterator iterator = relation.getValues();
            iterator.open();
            int size = 0;
            while (iterator.next() != null) {
                size = size + 1;
            }
            assertEquals(2, size);
        }
        assertEquals("[p(\"c\", \"d\"). p(\"dcd\", \"d\"). ]", idbRelations.toString());
    }
	
	@Test
    public void testEvaluatingSelectionWithStringArgumentsInContainsConditionReverse() {
        
        /*
         * p(X, Y) :- r(X, Y) & c =c X.
         * 
         * r(a, b).
         * r(c, d).
         * r(dcd, d).
         * r(e, f).
         * r(g, h).
         * 
         * Expected IDB facts:
         * 
         * p(c, d).
         */
        
        Predicate p = Predicate.create("p", 2);
        Predicate r = Predicate.create("r", 2);
        
        Parameter<?> argumentX = Parameter.createVariable("X");
        Parameter<?> argumentY = Parameter.createVariable("Y");
        Parameter<?> argumentc = Parameter.createConstant("c");
        
        Literal head     = Literal.create(p, argumentX, argumentY);
        Literal subgoal1 = Literal.create(r, argumentX, argumentY);
        Literal subgoal2 = Literal.create(BuiltInPredicates.CONTAINS, argumentc, argumentX);
        
        IRule rule = Rule.create(head, subgoal1, subgoal2);
        
        List<IRule> rules = new ArrayList<IRule>();
        rules.add(rule);
        
        String[][] relationR = {
            {"a", "b"},
            {"c", "d"},
            {"dcd", "d"},
            {"e", "f"},
            {"g", "h"}
        };
        
        DataIterator relationRIterator = Utils.createRelationIterator(relationR);
        
        IFacts relationRFacts = Facts.create(r, relationRIterator);
        
        Collection<IFacts> edbRelations = new ArrayList<IFacts>();
        edbRelations.add(relationRFacts);
        
        IEvaluator evaluator = new NonRecursiveEvaluator(rules);
        Collection<IFacts> idbRelations = evaluator.eval(edbRelations);
        
        assertEquals(1, idbRelations.size());
        for (IFacts relation : idbRelations) {
            DataIterator iterator = relation.getValues();
            iterator.open();
            int size = 0;
            while (iterator.next() != null) {
                size = size + 1;
            }
            assertEquals(1, size);
        }
        assertEquals("[p(\"c\", \"d\"). ]", idbRelations.toString());
    }
}
