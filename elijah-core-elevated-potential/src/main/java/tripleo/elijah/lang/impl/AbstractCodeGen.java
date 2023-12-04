/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.ElElementVisitor;

/**
 * Created 11/18/21 1:02 PM
 */
public abstract class AbstractCodeGen implements ElElementVisitor {
	@Override
	public void addClass(final ClassStatement klass) {
		defaultAction(klass);
	}

	@Override
	public void addFunctionItem(final FunctionItem element) {
		defaultAction(element);
	}

	@Override
	public void addModule(final OS_Module module) {
		defaultAction(module);
	}

	public void defaultAction(final OS_Element anElement) {

	}

	@Override
	public void visitAccessNotation(final AccessNotation aAccessNotation) {
		defaultAction(aAccessNotation);
	}

	@Override
	public void visitAliasStatement(final AliasStatement aAliasStatement) {
		defaultAction(aAliasStatement);
	}
	
	@Override
	public void visitCaseConditional(final CaseConditional aCaseConditional) {
		defaultAction(aCaseConditional);
	}
	
	@Override
	public void visitConstructorDef(final ConstructorDef aConstructorDef) {
		defaultAction(aConstructorDef);
	}

	@Override
	public void visitConstructStatement(final ConstructStatement aConstructExpression) {
		defaultAction(aConstructExpression);
	}

	@Override
	public void visitDefFunction(final DefFunctionDef aDefFunctionDef) {
		defaultAction(aDefFunctionDef);
	}

	@Override
	public void visitDestructor(final DestructorDef aDestructorDef) {
		defaultAction(aDestructorDef);
	}

	@Override
	public void visitFormalArgListItem(final FormalArgListItem aFormalArgListItem) {
		defaultAction(aFormalArgListItem);
	}

	@Override
	public void visitFuncExpr(final FuncExpr aFuncExpr) {
		defaultAction(aFuncExpr);
	}

	@Override
	public void visitFunctionDef(final FunctionDef aFunctionDef) {
		defaultAction(aFunctionDef);
	}

	@Override
	public void visitIdentExpression(final IdentExpression aIdentExpression) {
		defaultAction(aIdentExpression);
	}

	@Override
	public void visitIfConditional(final IfConditional aIfConditional) {
		defaultAction(aIfConditional);
	}

	@Override
	public void visitImportStatment(final ImportStatement aImportStatement) {
		defaultAction(aImportStatement);
	}

	@Override
	public void visitLoop(final Loop aLoop) {
		defaultAction(aLoop);
	}

	@Override
	public void visitMatchConditional(final MatchConditional aMatchConditional) {
		defaultAction(aMatchConditional);
	}

	@Override
	public void visitMC1(final MC1 aMC1) {
		defaultAction(aMC1);
	}

	@Override
	public void visitNamespaceStatement(final NamespaceStatement aNamespaceStatement) {
		defaultAction(aNamespaceStatement);
	}

	@Override
	public void visitPropertyStatement(final PropertyStatement aPropertyStatement) {
		defaultAction(aPropertyStatement);
	}

	@Override
	public void visitStatementWrapper(final StatementWrapper aStatementWrapper) {
		defaultAction(aStatementWrapper);
	}

	@Override
	public void visitSyntacticBlock(final SyntacticBlock aSyntacticBlock) {
		defaultAction(aSyntacticBlock);
	}

	@Override
	public void visitTypeAlias(final TypeAliasStatement aTypeAliasStatement) {
		defaultAction(aTypeAliasStatement);
	}

	//@Override
	//public void visitTypeNameElement(final OS_TypeNameElement aOS_typeNameElement) {
	//	defaultAction(aOS_typeNameElement);
	//}

	@Override
	public void visitVariableSequence(VariableSequence aVariableSequence) {
		defaultAction(aVariableSequence);
	}

	@Override
	public void visitVariableStatement(VariableStatement aVariableStatement) {
		defaultAction(aVariableStatement);
	}

	@Override
	public void visitWithStatement(WithStatement aWithStatement) {
		defaultAction(aWithStatement);
	}

	@Override
	public void visitYield(YieldExpression aYieldExpression) {
		defaultAction(aYieldExpression);
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
