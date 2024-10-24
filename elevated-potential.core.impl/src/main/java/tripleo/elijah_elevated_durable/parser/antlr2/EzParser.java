// $ANTLR 2.7.7 (20060906): "ez.g" -> "EzParser.java"$

package tripleo.elijah_elevated_durable.parser.antlr2;

import antlr.*;
import antlr.collections.impl.BitSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionList;
import tripleo.elijah.ci.CiIndexingStatement;
import tripleo.elijah.ci.CiListExpression;
import tripleo.elijah.ci.CiProcedureCallExpression;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.ci.ExpressionKind;
import tripleo.elijah.ci.GenerateStatement;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.ci.cii.GetItemExpression;
import tripleo.elijah.ci.cii.IdentExpression;
import tripleo.elijah.ci.cii.ProcedureCallExpression;
import tripleo.elijah.ci.cii.Qualident;
import tripleo.elijah.ci.cii.QualidentList;
import tripleo.elijah.ci.cii.TypeCastExpression;
import tripleo.elijah.ci.cii.TypeName;
import tripleo.elijah_elevated_durable.parser.PCon;
import tripleo.elijah.xlang.LocatableString;

public class EzParser extends antlr.LLkParser implements EzTokenTypes {

	public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"program\"",
			"\"library\"", "\"shared\"", "IDENT", "\"end\"", "\"lib\"", "\"libraries\"", "TOK_COLON", "STRING_LITERAL",
			"LBRACK", "RBRACK", "\"generate\"", "\"indexing\"", "CHAR_LITERAL", "NUM_INT", "NUM_FLOAT", "DOT", "SEMI",
			"COMMA", "LPAREN", "RPAREN", "BECOMES", "PLUS_ASSIGN", "MINUS_ASSIGN", "STAR_ASSIGN", "DIV_ASSIGN",
			"MOD_ASSIGN", "SR_ASSIGN", "BSR_ASSIGN", "SL_ASSIGN", "BAND_ASSIGN", "BXOR_ASSIGN", "BOR_ASSIGN", "LOR",
			"LAND", "BOR", "BXOR", "BAND", "NOT_EQUAL", "EQUAL", "LT_", "GT", "LE", "GE", "SL", "SR", "BSR", "PLUS",
			"MINUS", "STAR", "DIV", "MOD", "INC", "DEC", "BNOT", "LNOT", "\"true\"", "\"false\"", "\"this\"",
			"\"null\"", "QUESTION", "LCURLY", "RCURLY", "TOK_ARROW", "ANNOT", "WS", "SL_COMMENT", "ML_COMMENT", "ESC",
			"HEX_DIGIT", "VOCAB", "EXPONENT", "FLOAT_SUFFIX" };
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());

	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L };
		return data;
	}

	private static final long[] mk_tokenSet_1() {
		long[] data = { 112L, 0L };
		return data;
	}

	private static final long[] mk_tokenSet_10() {
		long[] data = { -2164238L, 0L };
		return data;
	}

	private static final long[] mk_tokenSet_11() {
		long[] data = { -65302194570776448L, 0L };
		return data;
	}

	private static final long[] mk_tokenSet_2() {
		long[] data = { 32768L, 0L };
		return data;
	}

	private static final long[] mk_tokenSet_3() {
		long[] data = { 256L, 0L };
		return data;
	}

	private static final long[] mk_tokenSet_4() {
		long[] data = { 36992L, 0L };
		return data;
	}

	private static final long[] mk_tokenSet_5() {
		long[] data = { 288230376148591088L, 0L };
		return data;
	}

	private static final long[] mk_tokenSet_6() {
		long[] data = { 16793840L, 0L };
		return data;
	}

	private static final long[] mk_tokenSet_7() {
		long[] data = { 4194306L, 0L };
		return data;
	}

	private static final long[] mk_tokenSet_8() {
		long[] data = { 288230376148591090L, 0L };
		return data;
	}

	private static final long[] mk_tokenSet_9() {
		long[] data = { -65302194587553664L, 0L };
		return data;
	}

	public PCon pcon;

	public @NotNull CompilerInstructions ci;

	@Nullable
	CiExpression expr;

	public EzParser(ParserSharedInputState state) {
		super(state, 2);
		tokenNames = _tokenNames;
	}

	public EzParser(TokenBuffer tokenBuf) {
		this(tokenBuf, 2);
	}

	protected EzParser(TokenBuffer tokenBuf, int k) {
		super(tokenBuf, k);
		tokenNames = _tokenNames;
	}

	public EzParser(TokenStream lexer) {
		this(lexer, 2);
	}

	protected EzParser(TokenStream lexer, int k) {
		super(lexer, k);
		tokenNames = _tokenNames;
	}

	public final @Nullable CiExpression additiveExpression() throws RecognitionException, TokenStreamException {
		CiExpression ee;

		ee = null;
		ExpressionKind e2 = null;
		CiExpression    e3 = null;

		try { // for error handling
			ee = multiplicativeExpression();
			{
				_loop80: do {
					if ((LA(1) == PLUS || LA(1) == MINUS) && (_tokenSet_9.member(LA(2)))) {
						{
							switch (LA(1)) {
							case PLUS: {
								match(PLUS);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.ADDITION;
								}
								break;
							}
							case MINUS: {
								match(MINUS);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.SUBTRACTION;
								}
								break;
							}
							default: {
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
						}
						e3 = multiplicativeExpression();
						if (inputState.guessing == 0) {
							ee = pcon.ExpressionBuilder_build(ee, e2, e3);
						}
					} else {
						break _loop80;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable CiExpression andExpression() throws RecognitionException, TokenStreamException {
		CiExpression ee;

		ee = null;
		CiExpression e3 = null;

		try { // for error handling
			ee = equalityExpression();
			{
				_loop63: do {
					if ((LA(1) == BAND) && (_tokenSet_9.member(LA(2)))) {
						match(BAND);
						e3 = equalityExpression();
						if (inputState.guessing == 0) {
							ee = pcon.ExpressionBuilder_build(ee, ExpressionKind.BAND, e3);
						}
					} else {
						break _loop63;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable CiExpression assignmentExpression() throws RecognitionException, TokenStreamException {
		CiExpression ee;

		ee = null;
		CiExpression e = null;
		CiExpression e2;
		ExpressionKind ek = null;

		try { // for error handling
			ee = conditionalExpression();
			{
				if (((LA(1) >= BECOMES && LA(1) <= BOR_ASSIGN)) && (_tokenSet_9.member(LA(2)))) {
					{
						switch (LA(1)) {
						case BECOMES: {
							match(BECOMES);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.ASSIGNMENT);
							}
							break;
						}
						case PLUS_ASSIGN: {
							match(PLUS_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_PLUS);
							}
							break;
						}
						case MINUS_ASSIGN: {
							match(MINUS_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_MINUS);
							}
							break;
						}
						case STAR_ASSIGN: {
							match(STAR_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_MULT);
							}
							break;
						}
						case DIV_ASSIGN: {
							match(DIV_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_DIV);
							}
							break;
						}
						case MOD_ASSIGN: {
							match(MOD_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_MOD);
							}
							break;
						}
						case SR_ASSIGN: {
							match(SR_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_SR);
							}
							break;
						}
						case BSR_ASSIGN: {
							match(BSR_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_BSR);
							}
							break;
						}
						case SL_ASSIGN: {
							match(SL_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_SL);
							}
							break;
						}
						case BAND_ASSIGN: {
							match(BAND_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_BAND);
							}
							break;
						}
						case BXOR_ASSIGN: {
							match(BXOR_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_BXOR);
							}
							break;
						}
						case BOR_ASSIGN: {
							match(BOR_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_BOR);
							}
							break;
						}
						default: {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
					}
					e2 = assignmentExpression();
					if (inputState.guessing == 0) {
						ee = pcon.ExpressionBuilder_build(ee, ek, e2);
					}
				} else if ((_tokenSet_5.member(LA(1))) && (_tokenSet_10.member(LA(2)))) {
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable CiExpression conditionalExpression() throws RecognitionException, TokenStreamException {
		CiExpression ee;

		ee = null;

		try { // for error handling
			ee = logicalOrExpression();
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable CiExpression constantValue() throws RecognitionException, TokenStreamException {
		CiExpression e;

		Token s = null;
		Token c = null;
		Token n = null;
		Token f = null;
		e = null;

		try { // for error handling
			switch (LA(1)) {
			case STRING_LITERAL: {
				s = LT(1);
				match(STRING_LITERAL);
				if (inputState.guessing == 0) {
					e = pcon.newStringExpressionImpl(s);
				}
				break;
			}
			case CHAR_LITERAL: {
				c = LT(1);
				match(CHAR_LITERAL);
				if (inputState.guessing == 0) {
					e = pcon.newCharLitExpressionImpl(c);
				}
				break;
			}
			case NUM_INT: {
				n = LT(1);
				match(NUM_INT);
				if (inputState.guessing == 0) {
					e = pcon.newNumericExpressionImpl(n);
				}
				break;
			}
			case NUM_FLOAT: {
				f = LT(1);
				match(NUM_FLOAT);
				if (inputState.guessing == 0) {
					e = pcon.newFloatExpressionImpl(f);
				}
				break;
			}
			default: {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return e;
	}

	public final @Nullable CiExpression dot_expression_or_procedure_call(CiExpression e1)
			throws RecognitionException, TokenStreamException {
		CiExpression ee;

		Token lp2 = null;
		ee = null;
		CiExpressionList el = null;
		IdentExpression e = null;

		try { // for error handling
			e = ident();
			if (inputState.guessing == 0) {
				ee = pcon.newDotExpressionImpl(e1, e);
			}
			{
				if ((LA(1) == LPAREN) && (_tokenSet_11.member(LA(2)))) {
					lp2 = LT(1);
					match(LPAREN);
					{
						switch (LA(1)) {
						case IDENT:
						case STRING_LITERAL:
						case LBRACK:
						case CHAR_LITERAL:
						case NUM_INT:
						case NUM_FLOAT:
						case LPAREN:
						case PLUS:
						case MINUS:
						case INC:
						case DEC:
						case BNOT:
						case LNOT:
						case LITERAL_true:
						case LITERAL_false:
						case LITERAL_this:
						case LITERAL_null: {
							el = expressionList2();
							break;
						}
						case RPAREN: {
							break;
						}
						default: {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
					}
					if (inputState.guessing == 0) {
						CiProcedureCallExpression pce = pcon.newCiProcedureCallExpressionImpl();
						pce.identifier(ee);
						pce.setArgs(el);
						ee = pce;
					}
					match(RPAREN);
				} else if ((_tokenSet_5.member(LA(1))) && (_tokenSet_10.member(LA(2)))) {
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable CiExpression equalityExpression() throws RecognitionException, TokenStreamException {
		CiExpression ee;

		ee = null;
		ExpressionKind e2 = null;
		CiExpression e3 = null;

		try { // for error handling
			ee = relationalExpression();
			{
				_loop67: do {
					if ((LA(1) == NOT_EQUAL || LA(1) == EQUAL) && (_tokenSet_9.member(LA(2)))) {
						{
							switch (LA(1)) {
							case NOT_EQUAL: {
								match(NOT_EQUAL);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.NOT_EQUAL;
								}
								break;
							}
							case EQUAL: {
								match(EQUAL);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.EQUAL;
								}
								break;
							}
							default: {
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
						}
						e3 = relationalExpression();
						if (inputState.guessing == 0) {
							ee = pcon.ExpressionBuilder_build(ee, e2, e3);
						}
					} else {
						break _loop67;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable CiExpression exclusiveOrExpression() throws RecognitionException, TokenStreamException {
		CiExpression ee;

		ee = null;
		CiExpression e3 = null;

		try { // for error handling
			ee = andExpression();
			{
				_loop60: do {
					if ((LA(1) == BXOR) && (_tokenSet_9.member(LA(2)))) {
						match(BXOR);
						e3 = andExpression();
						if (inputState.guessing == 0) {
							ee = pcon.ExpressionBuilder_build(ee, ExpressionKind.BXOR, e3);
						}
					} else {
						break _loop60;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable CiExpression expression() throws RecognitionException, TokenStreamException {
		CiExpression ee;

		ee = null;

		try { // for error handling
			ee = assignmentExpression();
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @NotNull CiExpressionList expressionList2() throws RecognitionException, TokenStreamException {
		CiExpressionList el;

		el = pcon.newCiExpressionListImpl();

		try { // for error handling
			expr = expression();
			if (inputState.guessing == 0) {
				el.next(expr);
			}
			{
				_loop41: do {
					if ((LA(1) == COMMA)) {
						match(COMMA);
						expr = expression();
						if (inputState.guessing == 0) {
							el.next(expr);
						}
					} else {
						break _loop41;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_6);
			} else {
				throw ex;
			}
		}
		return el;
	}

	public final @NotNull GenerateStatement generate_statement() throws RecognitionException, TokenStreamException {
		GenerateStatement gen;

		Token i1 = null;
		gen = pcon.newGenerateStatementImpl();

		try { // for error handling
			match(LITERAL_generate);
			{
				_loop15: do {
					if ((LA(1) == IDENT)) {
						i1 = LT(1);
						match(IDENT);
						match(TOK_COLON);
						expr = expression();
						if (inputState.guessing == 0) {
							gen.addDirective(i1, expr);
						}
					} else {
						break _loop15;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_3);
			} else {
				throw ex;
			}
		}
		return gen;
	}

	public final @Nullable IdentExpression ident() throws RecognitionException, TokenStreamException {
		IdentExpression id;

		Token r1 = null;
		id = null;

		try { // for error handling
			r1 = LT(1);
			match(IDENT);
			if (inputState.guessing == 0) {
				id = pcon.newIdentExpressionImpl(r1, null);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_8);
			} else {
				throw ex;
			}
		}
		return id;
	}

	public final @Nullable CiExpression inclusiveOrExpression() throws RecognitionException, TokenStreamException {
		CiExpression ee;

		ee = null;
		CiExpression e3 = null;

		try { // for error handling
			ee = exclusiveOrExpression();
			{
				_loop57: do {
					if ((LA(1) == BOR) && (_tokenSet_9.member(LA(2)))) {
						match(BOR);
						e3 = exclusiveOrExpression();
						if (inputState.guessing == 0) {
							ee = pcon.ExpressionBuilder_build(ee, ExpressionKind.BOR, e3);
						}
					} else {
						break _loop57;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final void indexingStatement(@NotNull CiIndexingStatement idx)
			throws RecognitionException, TokenStreamException {

		Token i1 = null;
		CiExpressionList el = null;

		try { // for error handling
			match(LITERAL_indexing);
			{
				_loop18: do {
					if ((LA(1) == IDENT)) {
						i1 = LT(1);
						match(IDENT);
						if (inputState.guessing == 0) {
							idx.setName(i1);
						}
						match(TOK_COLON);
						el = expressionList2();
						if (inputState.guessing == 0) {
							idx.setExprs(el);
						}
					} else {
						break _loop18;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_1);
			} else {
				throw ex;
			}
		}
	}

	public final void library_statement() throws RecognitionException, TokenStreamException {

		LibraryStatementPart lsp = null;

		try { // for error handling
			{
				switch (LA(1)) {
				case LITERAL_lib: {
					match(LITERAL_lib);
					break;
				}
				case LITERAL_libraries: {
					match(LITERAL_libraries);
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			{
				_loop7: do {
					if ((LA(1) == IDENT || LA(1) == STRING_LITERAL)) {
						lsp = library_statement_part();
						if (inputState.guessing == 0) {
							ci.add(lsp);
						}
					} else {
						break _loop7;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_2);
			} else {
				throw ex;
			}
		}
	}

	public final @NotNull LibraryStatementPart library_statement_part()
			throws RecognitionException, TokenStreamException {
		LibraryStatementPart lsp;

		Token i1 = null;
		Token dirname = null;
		Token i2 = null;
		lsp = pcon.newLibraryStatementPartImpl();

		try { // for error handling
			{
				switch (LA(1)) {
				case IDENT: {
					i1 = LT(1);
					match(IDENT);
					match(TOK_COLON);
					if (inputState.guessing == 0) {
						lsp.setName(i1);
					}
					break;
				}
				case STRING_LITERAL: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			dirname = LT(1);
			match(STRING_LITERAL);
			if (inputState.guessing == 0) {
				lsp.setDirName(dirname);
			}
			{
				switch (LA(1)) {
				case LBRACK: {
					match(LBRACK);
					{
						_loop12: do {
							if ((LA(1) == IDENT)) {
								i2 = LT(1);
								match(IDENT);
								match(TOK_COLON);
								expr = expression();
							} else {
								break _loop12;
							}

						} while (true);
					}
					if (inputState.guessing == 0) {
						lsp.addDirective(i2, expr);
					}
					match(RBRACK);
					break;
				}
				case IDENT:
				case STRING_LITERAL:
				case LITERAL_generate: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_4);
			} else {
				throw ex;
			}
		}
		return lsp;
	}

	public final @Nullable CiExpression logicalAndExpression() throws RecognitionException, TokenStreamException {
		CiExpression ee;

		ee = null;
		CiExpression e3 = null;

		try { // for error handling
			ee = inclusiveOrExpression();
			{
				_loop54: do {
					if ((LA(1) == LAND) && (_tokenSet_9.member(LA(2)))) {
						match(LAND);
						e3 = inclusiveOrExpression();
						if (inputState.guessing == 0) {
							ee = pcon.ExpressionBuilder_build(ee, ExpressionKind.LAND, e3);
						}
					} else {
						break _loop54;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable CiExpression logicalOrExpression() throws RecognitionException, TokenStreamException {
		CiExpression ee;

		ee = null;
		CiExpression e3 = null;

		try { // for error handling
			ee = logicalAndExpression();
			{
				_loop51: do {
					if ((LA(1) == LOR) && (_tokenSet_9.member(LA(2)))) {
						match(LOR);
						e3 = logicalAndExpression();
						if (inputState.guessing == 0) {
							ee = pcon.ExpressionBuilder_build(ee, ExpressionKind.LOR, e3);
						}
					} else {
						break _loop51;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable CiExpression multiplicativeExpression() throws RecognitionException, TokenStreamException {
		CiExpression ee;

		ee = null;
		CiExpression e3 = null;
		ExpressionKind e2 = null;

		try { // for error handling
			ee = unaryExpression();
			{
				_loop84: do {
					if (((LA(1) >= STAR && LA(1) <= MOD)) && (_tokenSet_9.member(LA(2)))) {
						{
							switch (LA(1)) {
							case STAR: {
								match(STAR);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.MULTIPLY;
								}
								break;
							}
							case DIV: {
								match(DIV);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.DIVIDE;
								}
								break;
							}
							case MOD: {
								match(MOD);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.MODULO;
								}
								break;
							}
							default: {
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
						}
						e3 = unaryExpression();
						if (inputState.guessing == 0) {
							ee = pcon.ExpressionBuilder_build(ee, e2, e3);
						}
					} else {
						break _loop84;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final void opt_semi() throws RecognitionException, TokenStreamException {

		try { // for error handling
			{
				switch (LA(1)) {
				case SEMI: {
					match(SEMI);
					break;
				}
				case EOF: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_0);
			} else {
				throw ex;
			}
		}
	}

	public final @Nullable CiExpression postfixExpression() throws RecognitionException, TokenStreamException {
		CiExpression ee;

		Token lb = null;
		Token rb = null;
		Token lp = null;
		Token in = null;
		Token de = null;
		ee = null;
		TypeCastExpression tc = null;
		TypeName tn = null;
		CiExpression e3 = null;
		CiExpressionList el = null;

		try { // for error handling
			ee = primaryExpression();
			{
				_loop92: do {
					if ((LA(1) == DOT) && (LA(2) == IDENT)) {
						match(DOT);
						{
							ee = dot_expression_or_procedure_call(ee);
						}
					} else if ((LA(1) == LBRACK) && (_tokenSet_9.member(LA(2)))) {
						lb = LT(1);
						match(LBRACK);
						expr = expression();
						rb = LT(1);
						match(RBRACK);
						if (inputState.guessing == 0) {
							ee = pcon.newGetItemExpressionImpl(ee, expr);
							((GetItemExpression) ee).parens(lb, rb);
						}
						{
							if ((LA(1) == BECOMES) && (_tokenSet_9.member(LA(2)))) {
								match(BECOMES);
								expr = expression();
								if (inputState.guessing == 0) {
									ee = pcon.newSetItemExpressionImpl((GetItemExpression) ee, expr);
								}
							} else if ((_tokenSet_5.member(LA(1))) && (_tokenSet_10.member(LA(2)))) {
							} else {
								throw new NoViableAltException(LT(1), getFilename());
							}

						}
					} else if ((LA(1) == LPAREN) && (_tokenSet_11.member(LA(2)))) {
						lp = LT(1);
						match(LPAREN);
						{
							switch (LA(1)) {
							case IDENT:
							case STRING_LITERAL:
							case LBRACK:
							case CHAR_LITERAL:
							case NUM_INT:
							case NUM_FLOAT:
							case LPAREN:
							case PLUS:
							case MINUS:
							case INC:
							case DEC:
							case BNOT:
							case LNOT:
							case LITERAL_true:
							case LITERAL_false:
							case LITERAL_this:
							case LITERAL_null: {
								el = expressionList2();
								break;
							}
							case RPAREN: {
								break;
							}
							default: {
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
						}
						if (inputState.guessing == 0) {
							CiProcedureCallExpression pce = pcon.newCiProcedureCallExpressionImpl();
							pce.identifier(ee);
							pce.setExpressionList(el);
							ee = pce;
						}
						match(RPAREN);
					} else {
						break _loop92;
					}

				} while (true);
			}
			{
				if ((LA(1) == INC) && (_tokenSet_5.member(LA(2)))) {
					in = LT(1);
					match(INC);
					if (inputState.guessing == 0) {
						ee.setKind(ExpressionKind.POST_INCREMENT);
					}
				} else if ((LA(1) == DEC) && (_tokenSet_5.member(LA(2)))) {
					de = LT(1);
					match(DEC);
					if (inputState.guessing == 0) {
						ee.setKind(ExpressionKind.POST_DECREMENT);
					}
				} else if ((_tokenSet_5.member(LA(1))) && (_tokenSet_10.member(LA(2)))) {
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable CiExpression primaryExpression() throws RecognitionException, TokenStreamException {
		CiExpression ee;

		ee = null;
		//FuncExpr        ppc = null;
		CiExpression     e  = null;
		CiExpressionList el = null;

		try { // for error handling
			switch (LA(1)) {
			case IDENT: {
				e = ident();
				if (inputState.guessing == 0) {
					ee = e;
				}
				break;
			}
			case STRING_LITERAL:
			case CHAR_LITERAL:
			case NUM_INT:
			case NUM_FLOAT: {
				ee = constantValue();
				break;
			}
			case LITERAL_true: {
				match(LITERAL_true);
				break;
			}
			case LITERAL_false: {
				match(LITERAL_false);
				break;
			}
			case LITERAL_this: {
				match(LITERAL_this);
				break;
			}
			case LITERAL_null: {
				match(LITERAL_null);
				break;
			}
			case LPAREN: {
				match(LPAREN);
				ee = assignmentExpression();
				match(RPAREN);
				if (inputState.guessing == 0) {
					ee = pcon.newSubExpressionImpl(ee);
				}
				break;
			}
			case LBRACK: {
				match(LBRACK);
				if (inputState.guessing == 0) {
					ee = pcon.newListExpressionImpl();
				}
				el = expressionList2();
				if (inputState.guessing == 0) {
					((CiListExpression) ee).setContents(el);
				}
				match(RBRACK);
				break;
			}
			default: {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final void program() throws RecognitionException, TokenStreamException {

		Token i1 = null;
		GenerateStatement gen = null;

		try { // for error handling
			{
				switch (LA(1)) {
				case LITERAL_indexing: {
					indexingStatement(ci.indexingStatement());
					break;
				}
				case LITERAL_program:
				case LITERAL_library:
				case LITERAL_shared: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			{
				switch (LA(1)) {
				case LITERAL_program: {
					match(LITERAL_program);
					break;
				}
				case LITERAL_library: {
					match(LITERAL_library);
					break;
				}
				case LITERAL_shared: {
					match(LITERAL_shared);
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			i1 = LT(1);
			match(IDENT);
			if (inputState.guessing == 0) {
				ci.setName(LocatableString.of(i1));
			}
			library_statement();
			gen = generate_statement();
			if (inputState.guessing == 0) {
				ci.add(gen);
			}
			match(LITERAL_end);
			match(Token.EOF_TYPE);
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_0);
			} else {
				throw ex;
			}
		}
	}

	public final @NotNull Qualident qualident() throws RecognitionException, TokenStreamException {
		tripleo.elijah.ci.cii.Qualident q;

		Token d1 = null;
		q = pcon.newQualidentImpl();
		IdentExpression r1 = null, r2 = null;

		try { // for error handling
			r1 = ident();
			if (inputState.guessing == 0) {
				q.append(r1);
			}
			{
				_loop22: do {
					if ((LA(1) == DOT)) {
						d1 = LT(1);
						match(DOT);
						r2 = ident();
						if (inputState.guessing == 0) {
							q.appendDot(d1);
							q.append(r2);
						}
					} else {
						break _loop22;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return q;
	}

	public final void qualidentList(@NotNull QualidentList qal) throws RecognitionException, TokenStreamException {

		Qualident qid;

		try { // for error handling
			qid = qualident();
			if (inputState.guessing == 0) {
				qal.add(qid);
			}
			{
				_loop37: do {
					if ((LA(1) == COMMA)) {
						match(COMMA);
						qid = qualident();
						if (inputState.guessing == 0) {
							qal.add(qid);
						}
					} else {
						break _loop37;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_0);
			} else {
				throw ex;
			}
		}
	}

	public final @Nullable CiExpression relationalExpression() throws RecognitionException, TokenStreamException {
		CiExpression ee;

		ee = null;
		ExpressionKind e2 = null; // should never be null (below)
		CiExpression e3 = null;
		TypeName tn = null;

		try { // for error handling
			ee = shiftExpression();
			{
				{
					_loop72: do {
						if (((LA(1) >= LT_ && LA(1) <= GE)) && (_tokenSet_9.member(LA(2)))) {
							{
								switch (LA(1)) {
								case LT_: {
									match(LT_);
									if (inputState.guessing == 0) {
										e2 = ExpressionKind.LT_;
									}
									break;
								}
								case GT: {
									match(GT);
									if (inputState.guessing == 0) {
										e2 = ExpressionKind.GT;
									}
									break;
								}
								case LE: {
									match(LE);
									if (inputState.guessing == 0) {
										e2 = ExpressionKind.LE;
									}
									break;
								}
								case GE: {
									match(GE);
									if (inputState.guessing == 0) {
										e2 = ExpressionKind.GE;
									}
									break;
								}
								default: {
									throw new NoViableAltException(LT(1), getFilename());
								}
								}
							}
							e3 = shiftExpression();
							if (inputState.guessing == 0) {
								//final OS_Type t = pcon.newOS_BuiltinType(BuiltInTypes.Boolean);
								ee = pcon.ExpressionBuilder_build(ee, e2, e3, null);
							}
						} else {
							break _loop72;
						}

					} while (true);
				}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable CiExpression shiftExpression() throws RecognitionException, TokenStreamException {
		CiExpression ee;

		ee = null;
		ExpressionKind e2 = null;
		CiExpression e3 = null;

		try { // for error handling
			ee = additiveExpression();
			{
				_loop76: do {
					if (((LA(1) >= SL && LA(1) <= BSR)) && (_tokenSet_9.member(LA(2)))) {
						{
							switch (LA(1)) {
							case SL: {
								match(SL);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.LSHIFT;
								}
								break;
							}
							case SR: {
								match(SR);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.RSHIFT;
								}
								break;
							}
							case BSR: {
								match(BSR);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.BSHIFTR;
								}
								break;
							}
							default: {
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
						}
						e3 = additiveExpression();
						if (inputState.guessing == 0) {
							ee = pcon.ExpressionBuilder_build(ee, e2, e3);
						}
					} else {
						break _loop76;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable CiExpression unaryExpression() throws RecognitionException, TokenStreamException {
		CiExpression ee;

		ee = null;
		CiExpression e3 = null;

		try { // for error handling
			switch (LA(1)) {
			case INC: {
				match(INC);
				ee = unaryExpression();
				if (inputState.guessing == 0) {
					ee.setKind(ExpressionKind.INCREMENT);
				}
				break;
			}
			case DEC: {
				match(DEC);
				ee = unaryExpression();
				if (inputState.guessing == 0) {
					ee.setKind(ExpressionKind.DECREMENT);
				}
				break;
			}
			case MINUS: {
				match(MINUS);
				ee = unaryExpression();
				if (inputState.guessing == 0) {
					ee.setKind(ExpressionKind.NEG);
				}
				break;
			}
			case PLUS: {
				match(PLUS);
				ee = unaryExpression();
				if (inputState.guessing == 0) {
					ee.setKind(ExpressionKind.POS);
				}
				break;
			}
			case IDENT:
			case STRING_LITERAL:
			case LBRACK:
			case CHAR_LITERAL:
			case NUM_INT:
			case NUM_FLOAT:
			case LPAREN:
			case BNOT:
			case LNOT:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_this:
			case LITERAL_null: {
				ee = unaryExpressionNotPlusMinus();
				break;
			}
			default: {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable CiExpression unaryExpressionNotPlusMinus() throws RecognitionException, TokenStreamException {
		CiExpression ee;

		ee = null;
		CiExpression e3 = null;

		try { // for error handling
			switch (LA(1)) {
			case BNOT: {
				match(BNOT);
				ee = unaryExpression();
				if (inputState.guessing == 0) {
					ee.setKind(ExpressionKind.BNOT);
				}
				break;
			}
			case LNOT: {
				match(LNOT);
				ee = unaryExpression();
				if (inputState.guessing == 0) {
					ee.setKind(ExpressionKind.LNOT);
				}
				break;
			}
			case IDENT:
			case STRING_LITERAL:
			case LBRACK:
			case CHAR_LITERAL:
			case NUM_INT:
			case NUM_FLOAT:
			case LPAREN:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_this:
			case LITERAL_null: {
				ee = postfixExpression();
				break;
			}
			default: {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable CiExpression variableReference() throws RecognitionException, TokenStreamException {
		CiExpression ee;

		Token lp = null;
		ProcedureCallExpression pcx;
		CiExpressionList el = null;
		ee = null;
		IdentExpression r1 = null, r2 = null;

		try { // for error handling
			r1 = ident();
			if (inputState.guessing == 0) {
				ee = r1;
			}
			{
				switch (LA(1)) {
				case DOT: {
					match(DOT);
					r2 = ident();
					if (inputState.guessing == 0) {
						ee = pcon.newDotExpressionImpl(ee, r2);
					}
					break;
				}
				case LBRACK: {
					match(LBRACK);
					expr = expression();
					match(RBRACK);
					if (inputState.guessing == 0) {
						ee = pcon.newGetItemExpressionImpl(ee, expr);
					}
					break;
				}
				case LPAREN: {
					lp = LT(1);
					match(LPAREN);
					{
						switch (LA(1)) {
						case IDENT:
						case STRING_LITERAL:
						case LBRACK:
						case CHAR_LITERAL:
						case NUM_INT:
						case NUM_FLOAT:
						case LPAREN:
						case PLUS:
						case MINUS:
						case INC:
						case DEC:
						case BNOT:
						case LNOT:
						case LITERAL_true:
						case LITERAL_false:
						case LITERAL_this:
						case LITERAL_null: {
							el = expressionList2();
							break;
						}
						case RPAREN: {
							break;
						}
						default: {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
					}
					if (inputState.guessing == 0) {
						CiProcedureCallExpression pce = pcon.newCiProcedureCallExpressionImpl();
						pce.identifier(ee);
						pce.setArgs(el);
						ee = pce;
					}
					match(RPAREN);
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_0);
			} else {
				throw ex;
			}
		}
		return ee;
	}

}
