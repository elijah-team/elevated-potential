// $ANTLR 3.5.2 tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g 2024-01-04 16:08:02
package tripleo.vendor.com.github.dritter.hd.dlog.parser;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings("all")
public class datalogParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "BODY", "BOOLEAN", "CHARACTER", 
		"COMMENT", "DATE", "DOUBLE", "ESC_SEQ", "HEX_DIGIT", "IDENTIFIER", "INTEGER", 
		"OCTAL_ESC", "PROGRAM", "QUERY", "RULE", "STRING", "UNICODE_ESC", "WS", 
		"'('", "')'", "','", "'.'", "':-'", "'?-'"
	};
	public static final int EOF=-1;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int T__24=24;
	public static final int T__25=25;
	public static final int T__26=26;
	public static final int BODY=4;
	public static final int BOOLEAN=5;
	public static final int CHARACTER=6;
	public static final int COMMENT=7;
	public static final int DATE=8;
	public static final int DOUBLE=9;
	public static final int ESC_SEQ=10;
	public static final int HEX_DIGIT=11;
	public static final int IDENTIFIER=12;
	public static final int INTEGER=13;
	public static final int OCTAL_ESC=14;
	public static final int PROGRAM=15;
	public static final int QUERY=16;
	public static final int RULE=17;
	public static final int STRING=18;
	public static final int UNICODE_ESC=19;
	public static final int WS=20;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public datalogParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public datalogParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return datalogParser.tokenNames; }
	@Override public String getGrammarFileName() { return "tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g"; }


	public static class parameter_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "parameter"
	// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:81:1: fragment parameter : ( INTEGER | DOUBLE | CHARACTER | BOOLEAN | STRING | DATE | IDENTIFIER );
	public final datalogParser.parameter_return parameter() throws RecognitionException {
		datalogParser.parameter_return retval = new datalogParser.parameter_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token set1=null;

		CommonTree set1_tree=null;

		try {
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:82:2: ( INTEGER | DOUBLE | CHARACTER | BOOLEAN | STRING | DATE | IDENTIFIER )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:
			{
			root_0 = (CommonTree)adaptor.nil();


			set1=input.LT(1);
			if ( (input.LA(1) >= BOOLEAN && input.LA(1) <= CHARACTER)||(input.LA(1) >= DATE && input.LA(1) <= DOUBLE)||(input.LA(1) >= IDENTIFIER && input.LA(1) <= INTEGER)||input.LA(1)==STRING ) {
				input.consume();
				adaptor.addChild(root_0, (CommonTree)adaptor.create(set1));
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "parameter"


	public static class literal_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "literal"
	// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:86:1: fragment literal : IDENTIFIER ( '(' parameter ( ',' parameter )* ')' ) -> ^( IDENTIFIER ( parameter )* ) ;
	public final datalogParser.literal_return literal() throws RecognitionException {
		datalogParser.literal_return retval = new datalogParser.literal_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token IDENTIFIER2=null;
		Token char_literal3=null;
		Token char_literal5=null;
		Token char_literal7=null;
		ParserRuleReturnScope parameter4 =null;
		ParserRuleReturnScope parameter6 =null;

		CommonTree IDENTIFIER2_tree=null;
		CommonTree char_literal3_tree=null;
		CommonTree char_literal5_tree=null;
		CommonTree char_literal7_tree=null;
		RewriteRuleTokenStream stream_22=new RewriteRuleTokenStream(adaptor,"token 22");
		RewriteRuleTokenStream stream_23=new RewriteRuleTokenStream(adaptor,"token 23");
		RewriteRuleTokenStream stream_IDENTIFIER=new RewriteRuleTokenStream(adaptor,"token IDENTIFIER");
		RewriteRuleTokenStream stream_21=new RewriteRuleTokenStream(adaptor,"token 21");
		RewriteRuleSubtreeStream stream_parameter=new RewriteRuleSubtreeStream(adaptor,"rule parameter");

		try {
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:87:2: ( IDENTIFIER ( '(' parameter ( ',' parameter )* ')' ) -> ^( IDENTIFIER ( parameter )* ) )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:87:4: IDENTIFIER ( '(' parameter ( ',' parameter )* ')' )
			{
			IDENTIFIER2=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_literal667);  
			stream_IDENTIFIER.add(IDENTIFIER2);

			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:87:15: ( '(' parameter ( ',' parameter )* ')' )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:87:17: '(' parameter ( ',' parameter )* ')'
			{
			char_literal3=(Token)match(input,21,FOLLOW_21_in_literal671);  
			stream_21.add(char_literal3);

			pushFollow(FOLLOW_parameter_in_literal673);
			parameter4=parameter();
			state._fsp--;

			stream_parameter.add(parameter4.getTree());
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:87:31: ( ',' parameter )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==23) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:87:33: ',' parameter
					{
					char_literal5=(Token)match(input,23,FOLLOW_23_in_literal677);  
					stream_23.add(char_literal5);

					pushFollow(FOLLOW_parameter_in_literal679);
					parameter6=parameter();
					state._fsp--;

					stream_parameter.add(parameter6.getTree());
					}
					break;

				default :
					break loop1;
				}
			}

			char_literal7=(Token)match(input,22,FOLLOW_22_in_literal685);  
			stream_22.add(char_literal7);

			}

			// AST REWRITE
			// elements: parameter, IDENTIFIER
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (CommonTree)adaptor.nil();
			// 87:57: -> ^( IDENTIFIER ( parameter )* )
			{
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:87:60: ^( IDENTIFIER ( parameter )* )
				{
				CommonTree root_1 = (CommonTree)adaptor.nil();
				root_1 = (CommonTree)adaptor.becomeRoot(stream_IDENTIFIER.nextNode(), root_1);
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:87:73: ( parameter )*
				while ( stream_parameter.hasNext() ) {
					adaptor.addChild(root_1, stream_parameter.nextTree());
				}
				stream_parameter.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "literal"


	public static class body_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "body"
	// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:91:1: fragment body : literal ( ',' literal )* -> ^( BODY ( literal )* ) ;
	public final datalogParser.body_return body() throws RecognitionException {
		datalogParser.body_return retval = new datalogParser.body_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token char_literal9=null;
		ParserRuleReturnScope literal8 =null;
		ParserRuleReturnScope literal10 =null;

		CommonTree char_literal9_tree=null;
		RewriteRuleTokenStream stream_23=new RewriteRuleTokenStream(adaptor,"token 23");
		RewriteRuleSubtreeStream stream_literal=new RewriteRuleSubtreeStream(adaptor,"rule literal");

		try {
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:92:2: ( literal ( ',' literal )* -> ^( BODY ( literal )* ) )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:92:4: literal ( ',' literal )*
			{
			pushFollow(FOLLOW_literal_in_body710);
			literal8=literal();
			state._fsp--;

			stream_literal.add(literal8.getTree());
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:92:12: ( ',' literal )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( (LA2_0==23) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:92:14: ',' literal
					{
					char_literal9=(Token)match(input,23,FOLLOW_23_in_body714);  
					stream_23.add(char_literal9);

					pushFollow(FOLLOW_literal_in_body716);
					literal10=literal();
					state._fsp--;

					stream_literal.add(literal10.getTree());
					}
					break;

				default :
					break loop2;
				}
			}

			// AST REWRITE
			// elements: literal
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (CommonTree)adaptor.nil();
			// 92:30: -> ^( BODY ( literal )* )
			{
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:92:33: ^( BODY ( literal )* )
				{
				CommonTree root_1 = (CommonTree)adaptor.nil();
				root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(BODY, "BODY"), root_1);
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:92:40: ( literal )*
				while ( stream_literal.hasNext() ) {
					adaptor.addChild(root_1, stream_literal.nextTree());
				}
				stream_literal.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "body"


	public static class rule_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "rule"
	// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:96:1: fragment rule : literal ( ':-' body )? '.' -> ^( RULE literal ( body )? ) ;
	public final datalogParser.rule_return rule() throws RecognitionException {
		datalogParser.rule_return retval = new datalogParser.rule_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token string_literal12=null;
		Token char_literal14=null;
		ParserRuleReturnScope literal11 =null;
		ParserRuleReturnScope body13 =null;

		CommonTree string_literal12_tree=null;
		CommonTree char_literal14_tree=null;
		RewriteRuleTokenStream stream_24=new RewriteRuleTokenStream(adaptor,"token 24");
		RewriteRuleTokenStream stream_25=new RewriteRuleTokenStream(adaptor,"token 25");
		RewriteRuleSubtreeStream stream_body=new RewriteRuleSubtreeStream(adaptor,"rule body");
		RewriteRuleSubtreeStream stream_literal=new RewriteRuleSubtreeStream(adaptor,"rule literal");

		try {
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:97:2: ( literal ( ':-' body )? '.' -> ^( RULE literal ( body )? ) )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:97:4: literal ( ':-' body )? '.'
			{
			pushFollow(FOLLOW_literal_in_rule745);
			literal11=literal();
			state._fsp--;

			stream_literal.add(literal11.getTree());
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:97:12: ( ':-' body )?
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==25) ) {
				alt3=1;
			}
			switch (alt3) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:97:14: ':-' body
					{
					string_literal12=(Token)match(input,25,FOLLOW_25_in_rule749);  
					stream_25.add(string_literal12);

					pushFollow(FOLLOW_body_in_rule751);
					body13=body();
					state._fsp--;

					stream_body.add(body13.getTree());
					}
					break;

			}

			char_literal14=(Token)match(input,24,FOLLOW_24_in_rule757);  
			stream_24.add(char_literal14);

			// AST REWRITE
			// elements: literal, body
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (CommonTree)adaptor.nil();
			// 97:32: -> ^( RULE literal ( body )? )
			{
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:97:35: ^( RULE literal ( body )? )
				{
				CommonTree root_1 = (CommonTree)adaptor.nil();
				root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(RULE, "RULE"), root_1);
				adaptor.addChild(root_1, stream_literal.nextTree());
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:97:50: ( body )?
				if ( stream_body.hasNext() ) {
					adaptor.addChild(root_1, stream_body.nextTree());
				}
				stream_body.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "rule"


	public static class query_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "query"
	// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:101:1: fragment query : '?-' body '.' -> ^( QUERY body ) ;
	public final datalogParser.query_return query() throws RecognitionException {
		datalogParser.query_return retval = new datalogParser.query_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token string_literal15=null;
		Token char_literal17=null;
		ParserRuleReturnScope body16 =null;

		CommonTree string_literal15_tree=null;
		CommonTree char_literal17_tree=null;
		RewriteRuleTokenStream stream_24=new RewriteRuleTokenStream(adaptor,"token 24");
		RewriteRuleTokenStream stream_26=new RewriteRuleTokenStream(adaptor,"token 26");
		RewriteRuleSubtreeStream stream_body=new RewriteRuleSubtreeStream(adaptor,"rule body");

		try {
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:102:2: ( '?-' body '.' -> ^( QUERY body ) )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:102:4: '?-' body '.'
			{
			string_literal15=(Token)match(input,26,FOLLOW_26_in_query784);  
			stream_26.add(string_literal15);

			pushFollow(FOLLOW_body_in_query786);
			body16=body();
			state._fsp--;

			stream_body.add(body16.getTree());
			char_literal17=(Token)match(input,24,FOLLOW_24_in_query788);  
			stream_24.add(char_literal17);

			// AST REWRITE
			// elements: body
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (CommonTree)adaptor.nil();
			// 102:18: -> ^( QUERY body )
			{
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:102:21: ^( QUERY body )
				{
				CommonTree root_1 = (CommonTree)adaptor.nil();
				root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(QUERY, "QUERY"), root_1);
				adaptor.addChild(root_1, stream_body.nextTree());
				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "query"


	public static class program_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "program"
	// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:105:1: program : ( rule )* ( query )? -> ^( PROGRAM ( rule )* ( query )? ) ;
	public final datalogParser.program_return program() throws RecognitionException {
		datalogParser.program_return retval = new datalogParser.program_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		ParserRuleReturnScope rule18 =null;
		ParserRuleReturnScope query19 =null;

		RewriteRuleSubtreeStream stream_query=new RewriteRuleSubtreeStream(adaptor,"rule query");
		RewriteRuleSubtreeStream stream_rule=new RewriteRuleSubtreeStream(adaptor,"rule rule");

		try {
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:106:2: ( ( rule )* ( query )? -> ^( PROGRAM ( rule )* ( query )? ) )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:106:4: ( rule )* ( query )?
			{
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:106:4: ( rule )*
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( (LA4_0==IDENTIFIER) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:106:4: rule
					{
					pushFollow(FOLLOW_rule_in_program809);
					rule18=rule();
					state._fsp--;

					stream_rule.add(rule18.getTree());
					}
					break;

				default :
					break loop4;
				}
			}

			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:106:11: ( query )?
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==26) ) {
				alt5=1;
			}
			switch (alt5) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:106:11: query
					{
					pushFollow(FOLLOW_query_in_program813);
					query19=query();
					state._fsp--;

					stream_query.add(query19.getTree());
					}
					break;

			}

			// AST REWRITE
			// elements: query, rule
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (CommonTree)adaptor.nil();
			// 106:19: -> ^( PROGRAM ( rule )* ( query )? )
			{
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:106:22: ^( PROGRAM ( rule )* ( query )? )
				{
				CommonTree root_1 = (CommonTree)adaptor.nil();
				root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(PROGRAM, "PROGRAM"), root_1);
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:106:32: ( rule )*
				while ( stream_rule.hasNext() ) {
					adaptor.addChild(root_1, stream_rule.nextTree());
				}
				stream_rule.reset();

				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:106:39: ( query )?
				if ( stream_query.hasNext() ) {
					adaptor.addChild(root_1, stream_query.nextTree());
				}
				stream_query.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "program"

	// Delegated rules



	public static final BitSet FOLLOW_IDENTIFIER_in_literal667 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_21_in_literal671 = new BitSet(new long[]{0x0000000000043360L});
	public static final BitSet FOLLOW_parameter_in_literal673 = new BitSet(new long[]{0x0000000000C00000L});
	public static final BitSet FOLLOW_23_in_literal677 = new BitSet(new long[]{0x0000000000043360L});
	public static final BitSet FOLLOW_parameter_in_literal679 = new BitSet(new long[]{0x0000000000C00000L});
	public static final BitSet FOLLOW_22_in_literal685 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_literal_in_body710 = new BitSet(new long[]{0x0000000000800002L});
	public static final BitSet FOLLOW_23_in_body714 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_literal_in_body716 = new BitSet(new long[]{0x0000000000800002L});
	public static final BitSet FOLLOW_literal_in_rule745 = new BitSet(new long[]{0x0000000003000000L});
	public static final BitSet FOLLOW_25_in_rule749 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_body_in_rule751 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_24_in_rule757 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_26_in_query784 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_body_in_query786 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_24_in_query788 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_rule_in_program809 = new BitSet(new long[]{0x0000000004001002L});
	public static final BitSet FOLLOW_query_in_program813 = new BitSet(new long[]{0x0000000000000002L});
}
