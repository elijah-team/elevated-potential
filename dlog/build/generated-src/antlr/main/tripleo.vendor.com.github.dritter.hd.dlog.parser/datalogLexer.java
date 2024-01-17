// $ANTLR 3.5.2 tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g 2024-01-04 16:08:02
package tripleo.vendor.com.github.dritter.hd.dlog.parser;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class datalogLexer extends Lexer {
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
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public datalogLexer() {} 
	public datalogLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public datalogLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g"; }

	// $ANTLR start "T__21"
	public final void mT__21() throws RecognitionException {
		try {
			int _type = T__21;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:4:7: ( '(' )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:4:9: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__21"

	// $ANTLR start "T__22"
	public final void mT__22() throws RecognitionException {
		try {
			int _type = T__22;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:5:7: ( ')' )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:5:9: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__22"

	// $ANTLR start "T__23"
	public final void mT__23() throws RecognitionException {
		try {
			int _type = T__23;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:6:7: ( ',' )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:6:9: ','
			{
			match(','); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__23"

	// $ANTLR start "T__24"
	public final void mT__24() throws RecognitionException {
		try {
			int _type = T__24;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:7:7: ( '.' )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:7:9: '.'
			{
			match('.'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__24"

	// $ANTLR start "T__25"
	public final void mT__25() throws RecognitionException {
		try {
			int _type = T__25;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:8:7: ( ':-' )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:8:9: ':-'
			{
			match(":-"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__25"

	// $ANTLR start "T__26"
	public final void mT__26() throws RecognitionException {
		try {
			int _type = T__26;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:9:7: ( '?-' )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:9:9: '?-'
			{
			match("?-"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__26"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException {
		try {
			int _type = COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:20:5: ( '%' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:20:9: '%' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
			{
			match('%'); 
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:20:13: (~ ( '\\n' | '\\r' ) )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= '\u0000' && LA1_0 <= '\t')||(LA1_0 >= '\u000B' && LA1_0 <= '\f')||(LA1_0 >= '\u000E' && LA1_0 <= '\uFFFF')) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop1;
				}
			}

			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:20:27: ( '\\r' )?
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0=='\r') ) {
				alt2=1;
			}
			switch (alt2) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:20:27: '\\r'
					{
					match('\r'); 
					}
					break;

			}

			match('\n'); 
			_channel=HIDDEN;
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMENT"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:23:5: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:23:9: ( ' ' | '\\t' | '\\r' | '\\n' )
			{
			if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			_channel=HIDDEN;
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	// $ANTLR start "STRING"
	public final void mSTRING() throws RecognitionException {
		try {
			int _type = STRING;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:31:5: ( '\"' ( ESC_SEQ |~ ( '\\\\' | '\"' ) )* '\"' )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:31:8: '\"' ( ESC_SEQ |~ ( '\\\\' | '\"' ) )* '\"'
			{
			match('\"'); 
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:31:12: ( ESC_SEQ |~ ( '\\\\' | '\"' ) )*
			loop3:
			while (true) {
				int alt3=3;
				int LA3_0 = input.LA(1);
				if ( (LA3_0=='\\') ) {
					alt3=1;
				}
				else if ( ((LA3_0 >= '\u0000' && LA3_0 <= '!')||(LA3_0 >= '#' && LA3_0 <= '[')||(LA3_0 >= ']' && LA3_0 <= '\uFFFF')) ) {
					alt3=2;
				}

				switch (alt3) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:31:14: ESC_SEQ
					{
					mESC_SEQ(); 

					}
					break;
				case 2 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:31:24: ~ ( '\\\\' | '\"' )
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop3;
				}
			}

			match('\"'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "STRING"

	// $ANTLR start "HEX_DIGIT"
	public final void mHEX_DIGIT() throws RecognitionException {
		try {
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:36:11: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:
			{
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'F')||(input.LA(1) >= 'a' && input.LA(1) <= 'f') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "HEX_DIGIT"

	// $ANTLR start "ESC_SEQ"
	public final void mESC_SEQ() throws RecognitionException {
		try {
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:40:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | UNICODE_ESC | OCTAL_ESC )
			int alt4=3;
			int LA4_0 = input.LA(1);
			if ( (LA4_0=='\\') ) {
				switch ( input.LA(2) ) {
				case '\"':
				case '\'':
				case '\\':
				case 'b':
				case 'f':
				case 'n':
				case 'r':
				case 't':
					{
					alt4=1;
					}
					break;
				case 'u':
					{
					alt4=2;
					}
					break;
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
					{
					alt4=3;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 4, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}

			switch (alt4) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:40:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
					{
					match('\\'); 
					if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;
				case 2 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:41:9: UNICODE_ESC
					{
					mUNICODE_ESC(); 

					}
					break;
				case 3 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:42:9: OCTAL_ESC
					{
					mOCTAL_ESC(); 

					}
					break;

			}
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ESC_SEQ"

	// $ANTLR start "OCTAL_ESC"
	public final void mOCTAL_ESC() throws RecognitionException {
		try {
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:47:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
			int alt5=3;
			int LA5_0 = input.LA(1);
			if ( (LA5_0=='\\') ) {
				int LA5_1 = input.LA(2);
				if ( ((LA5_1 >= '0' && LA5_1 <= '3')) ) {
					int LA5_2 = input.LA(3);
					if ( ((LA5_2 >= '0' && LA5_2 <= '7')) ) {
						int LA5_4 = input.LA(4);
						if ( ((LA5_4 >= '0' && LA5_4 <= '7')) ) {
							alt5=1;
						}

						else {
							alt5=2;
						}

					}

					else {
						alt5=3;
					}

				}
				else if ( ((LA5_1 >= '4' && LA5_1 <= '7')) ) {
					int LA5_3 = input.LA(3);
					if ( ((LA5_3 >= '0' && LA5_3 <= '7')) ) {
						alt5=2;
					}

					else {
						alt5=3;
					}

				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 5, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 5, 0, input);
				throw nvae;
			}

			switch (alt5) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:47:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
					{
					match('\\'); 
					if ( (input.LA(1) >= '0' && input.LA(1) <= '3') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;
				case 2 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:48:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
					{
					match('\\'); 
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;
				case 3 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:49:9: '\\\\' ( '0' .. '7' )
					{
					match('\\'); 
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

			}
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "OCTAL_ESC"

	// $ANTLR start "UNICODE_ESC"
	public final void mUNICODE_ESC() throws RecognitionException {
		try {
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:54:5: ( '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:54:9: '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
			{
			match('\\'); 
			match('u'); 
			mHEX_DIGIT(); 

			mHEX_DIGIT(); 

			mHEX_DIGIT(); 

			mHEX_DIGIT(); 

			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "UNICODE_ESC"

	// $ANTLR start "INTEGER"
	public final void mINTEGER() throws RecognitionException {
		try {
			int _type = INTEGER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:57:2: ( ( '+' | '-' )? ( '0' .. '9' )* )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:57:4: ( '+' | '-' )? ( '0' .. '9' )*
			{
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:57:4: ( '+' | '-' )?
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0=='+'||LA6_0=='-') ) {
				alt6=1;
			}
			switch (alt6) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:
					{
					if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

			}

			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:57:15: ( '0' .. '9' )*
			loop7:
			while (true) {
				int alt7=2;
				int LA7_0 = input.LA(1);
				if ( ((LA7_0 >= '0' && LA7_0 <= '9')) ) {
					alt7=1;
				}

				switch (alt7) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop7;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INTEGER"

	// $ANTLR start "DOUBLE"
	public final void mDOUBLE() throws RecognitionException {
		try {
			int _type = DOUBLE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:61:2: ( ( '+' | '-' )? ( '0' .. '9' )* '.' ( '0' .. '9' )+ ( ( 'E' | 'e' ) ( '+' | '-' )? ( '0' .. '9' )+ )? )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:61:4: ( '+' | '-' )? ( '0' .. '9' )* '.' ( '0' .. '9' )+ ( ( 'E' | 'e' ) ( '+' | '-' )? ( '0' .. '9' )+ )?
			{
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:61:4: ( '+' | '-' )?
			int alt8=2;
			int LA8_0 = input.LA(1);
			if ( (LA8_0=='+'||LA8_0=='-') ) {
				alt8=1;
			}
			switch (alt8) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:
					{
					if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

			}

			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:61:15: ( '0' .. '9' )*
			loop9:
			while (true) {
				int alt9=2;
				int LA9_0 = input.LA(1);
				if ( ((LA9_0 >= '0' && LA9_0 <= '9')) ) {
					alt9=1;
				}

				switch (alt9) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop9;
				}
			}

			match('.'); 
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:61:31: ( '0' .. '9' )+
			int cnt10=0;
			loop10:
			while (true) {
				int alt10=2;
				int LA10_0 = input.LA(1);
				if ( ((LA10_0 >= '0' && LA10_0 <= '9')) ) {
					alt10=1;
				}

				switch (alt10) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt10 >= 1 ) break loop10;
					EarlyExitException eee = new EarlyExitException(10, input);
					throw eee;
				}
				cnt10++;
			}

			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:61:43: ( ( 'E' | 'e' ) ( '+' | '-' )? ( '0' .. '9' )+ )?
			int alt13=2;
			int LA13_0 = input.LA(1);
			if ( (LA13_0=='E'||LA13_0=='e') ) {
				alt13=1;
			}
			switch (alt13) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:61:44: ( 'E' | 'e' ) ( '+' | '-' )? ( '0' .. '9' )+
					{
					if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:61:54: ( '+' | '-' )?
					int alt11=2;
					int LA11_0 = input.LA(1);
					if ( (LA11_0=='+'||LA11_0=='-') ) {
						alt11=1;
					}
					switch (alt11) {
						case 1 :
							// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:
							{
							if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

					}

					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:61:65: ( '0' .. '9' )+
					int cnt12=0;
					loop12:
					while (true) {
						int alt12=2;
						int LA12_0 = input.LA(1);
						if ( ((LA12_0 >= '0' && LA12_0 <= '9')) ) {
							alt12=1;
						}

						switch (alt12) {
						case 1 :
							// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:
							{
							if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							if ( cnt12 >= 1 ) break loop12;
							EarlyExitException eee = new EarlyExitException(12, input);
							throw eee;
						}
						cnt12++;
					}

					}
					break;

			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DOUBLE"

	// $ANTLR start "CHARACTER"
	public final void mCHARACTER() throws RecognitionException {
		try {
			int _type = CHARACTER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:65:2: ( '\\'' (~ ( '\\'' | '\\\\' ) | ESC_SEQ ) '\\'' )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:65:4: '\\'' (~ ( '\\'' | '\\\\' ) | ESC_SEQ ) '\\''
			{
			match('\''); 
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:65:9: (~ ( '\\'' | '\\\\' ) | ESC_SEQ )
			int alt14=2;
			int LA14_0 = input.LA(1);
			if ( ((LA14_0 >= '\u0000' && LA14_0 <= '&')||(LA14_0 >= '(' && LA14_0 <= '[')||(LA14_0 >= ']' && LA14_0 <= '\uFFFF')) ) {
				alt14=1;
			}
			else if ( (LA14_0=='\\') ) {
				alt14=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 14, 0, input);
				throw nvae;
			}

			switch (alt14) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:65:10: ~ ( '\\'' | '\\\\' )
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '&')||(input.LA(1) >= '(' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;
				case 2 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:65:27: ESC_SEQ
					{
					mESC_SEQ(); 

					}
					break;

			}

			match('\''); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CHARACTER"

	// $ANTLR start "BOOLEAN"
	public final void mBOOLEAN() throws RecognitionException {
		try {
			int _type = BOOLEAN;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:69:2: ( 'true' | 'false' )
			int alt15=2;
			int LA15_0 = input.LA(1);
			if ( (LA15_0=='t') ) {
				alt15=1;
			}
			else if ( (LA15_0=='f') ) {
				alt15=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 15, 0, input);
				throw nvae;
			}

			switch (alt15) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:69:4: 'true'
					{
					match("true"); 

					}
					break;
				case 2 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:69:13: 'false'
					{
					match("false"); 

					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "BOOLEAN"

	// $ANTLR start "DATE"
	public final void mDATE() throws RecognitionException {
		try {
			int _type = DATE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:73:2: ( ( '0' .. '3' )? ( '0' .. '9' ) '.' ( '0' .. '1' )? ( '0' .. '9' ) '.' ( '0' .. '9' ) ( '0' .. '9' ) ( '0' .. '9' ) ( '0' .. '9' )+ )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:73:4: ( '0' .. '3' )? ( '0' .. '9' ) '.' ( '0' .. '1' )? ( '0' .. '9' ) '.' ( '0' .. '9' ) ( '0' .. '9' ) ( '0' .. '9' ) ( '0' .. '9' )+
			{
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:73:4: ( '0' .. '3' )?
			int alt16=2;
			int LA16_0 = input.LA(1);
			if ( ((LA16_0 >= '0' && LA16_0 <= '3')) ) {
				int LA16_1 = input.LA(2);
				if ( ((LA16_1 >= '0' && LA16_1 <= '9')) ) {
					alt16=1;
				}
			}
			switch (alt16) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '3') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

			}

			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			match('.'); 
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:73:31: ( '0' .. '1' )?
			int alt17=2;
			int LA17_0 = input.LA(1);
			if ( ((LA17_0 >= '0' && LA17_0 <= '1')) ) {
				int LA17_1 = input.LA(2);
				if ( ((LA17_1 >= '0' && LA17_1 <= '9')) ) {
					alt17=1;
				}
			}
			switch (alt17) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '1') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

			}

			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			match('.'); 
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:73:91: ( '0' .. '9' )+
			int cnt18=0;
			loop18:
			while (true) {
				int alt18=2;
				int LA18_0 = input.LA(1);
				if ( ((LA18_0 >= '0' && LA18_0 <= '9')) ) {
					alt18=1;
				}

				switch (alt18) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt18 >= 1 ) break loop18;
					EarlyExitException eee = new EarlyExitException(18, input);
					throw eee;
				}
				cnt18++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DATE"

	// $ANTLR start "IDENTIFIER"
	public final void mIDENTIFIER() throws RecognitionException {
		try {
			int _type = IDENTIFIER;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:77:2: ( ( '=c' | '<' | '=' | '>' | '<=' | '!=' | '>=' | 'A' .. 'Z' | 'a' .. 'z' ) ( '-' | '0' .. '9' | 'A' .. 'Z' | 'a' .. 'z' )* )
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:77:4: ( '=c' | '<' | '=' | '>' | '<=' | '!=' | '>=' | 'A' .. 'Z' | 'a' .. 'z' ) ( '-' | '0' .. '9' | 'A' .. 'Z' | 'a' .. 'z' )*
			{
			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:77:4: ( '=c' | '<' | '=' | '>' | '<=' | '!=' | '>=' | 'A' .. 'Z' | 'a' .. 'z' )
			int alt19=9;
			switch ( input.LA(1) ) {
			case '=':
				{
				int LA19_1 = input.LA(2);
				if ( (LA19_1=='c') ) {
					alt19=1;
				}

				else {
					alt19=3;
				}

				}
				break;
			case '<':
				{
				int LA19_2 = input.LA(2);
				if ( (LA19_2=='=') ) {
					alt19=5;
				}

				else {
					alt19=2;
				}

				}
				break;
			case '>':
				{
				int LA19_3 = input.LA(2);
				if ( (LA19_3=='=') ) {
					alt19=7;
				}

				else {
					alt19=4;
				}

				}
				break;
			case '!':
				{
				alt19=6;
				}
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				{
				alt19=8;
				}
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
				{
				alt19=9;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 19, 0, input);
				throw nvae;
			}
			switch (alt19) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:77:5: '=c'
					{
					match("=c"); 

					}
					break;
				case 2 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:77:10: '<'
					{
					match('<'); 
					}
					break;
				case 3 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:77:14: '='
					{
					match('='); 
					}
					break;
				case 4 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:77:18: '>'
					{
					match('>'); 
					}
					break;
				case 5 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:77:22: '<='
					{
					match("<="); 

					}
					break;
				case 6 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:77:27: '!='
					{
					match("!="); 

					}
					break;
				case 7 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:77:32: '>='
					{
					match(">="); 

					}
					break;
				case 8 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:77:37: 'A' .. 'Z'
					{
					matchRange('A','Z'); 
					}
					break;
				case 9 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:77:46: 'a' .. 'z'
					{
					matchRange('a','z'); 
					}
					break;

			}

			// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:77:56: ( '-' | '0' .. '9' | 'A' .. 'Z' | 'a' .. 'z' )*
			loop20:
			while (true) {
				int alt20=2;
				int LA20_0 = input.LA(1);
				if ( (LA20_0=='-'||(LA20_0 >= '0' && LA20_0 <= '9')||(LA20_0 >= 'A' && LA20_0 <= 'Z')||(LA20_0 >= 'a' && LA20_0 <= 'z')) ) {
					alt20=1;
				}

				switch (alt20) {
				case 1 :
					// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:
					{
					if ( input.LA(1)=='-'||(input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop20;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "IDENTIFIER"

	@Override
	public void mTokens() throws RecognitionException {
		// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:1:8: ( T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | COMMENT | WS | STRING | INTEGER | DOUBLE | CHARACTER | BOOLEAN | DATE | IDENTIFIER )
		int alt21=15;
		alt21 = dfa21.predict(input);
		switch (alt21) {
			case 1 :
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:1:10: T__21
				{
				mT__21(); 

				}
				break;
			case 2 :
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:1:16: T__22
				{
				mT__22(); 

				}
				break;
			case 3 :
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:1:22: T__23
				{
				mT__23(); 

				}
				break;
			case 4 :
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:1:28: T__24
				{
				mT__24(); 

				}
				break;
			case 5 :
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:1:34: T__25
				{
				mT__25(); 

				}
				break;
			case 6 :
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:1:40: T__26
				{
				mT__26(); 

				}
				break;
			case 7 :
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:1:46: COMMENT
				{
				mCOMMENT(); 

				}
				break;
			case 8 :
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:1:54: WS
				{
				mWS(); 

				}
				break;
			case 9 :
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:1:57: STRING
				{
				mSTRING(); 

				}
				break;
			case 10 :
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:1:64: INTEGER
				{
				mINTEGER(); 

				}
				break;
			case 11 :
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:1:72: DOUBLE
				{
				mDOUBLE(); 

				}
				break;
			case 12 :
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:1:79: CHARACTER
				{
				mCHARACTER(); 

				}
				break;
			case 13 :
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:1:89: BOOLEAN
				{
				mBOOLEAN(); 

				}
				break;
			case 14 :
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:1:97: DATE
				{
				mDATE(); 

				}
				break;
			case 15 :
				// tripleo.vendor.com.github.dritter.hd.dlog.parser/datalog.g:1:102: IDENTIFIER
				{
				mIDENTIFIER(); 

				}
				break;

		}
	}


	protected DFA21 dfa21 = new DFA21(this);
	static final String DFA21_eotS =
		"\1\14\3\uffff\1\22\5\uffff\2\14\2\uffff\2\21\1\14\3\uffff\1\14\1\uffff"+
		"\2\21\2\23\2\21\1\uffff\1\37\1\21\1\uffff\1\37";
	static final String DFA21_eofS =
		"\41\uffff";
	static final String DFA21_minS =
		"\1\11\3\uffff\1\60\5\uffff\2\56\2\uffff\1\162\1\141\1\56\3\uffff\1\56"+
		"\1\60\1\165\1\154\2\56\1\145\1\163\1\uffff\1\55\1\145\1\uffff\1\55";
	static final String DFA21_maxS =
		"\1\172\3\uffff\1\71\5\uffff\2\71\2\uffff\1\162\1\141\1\71\3\uffff\2\71"+
		"\1\165\1\154\1\71\1\56\1\145\1\163\1\uffff\1\172\1\145\1\uffff\1\172";
	static final String DFA21_acceptS =
		"\1\uffff\1\1\1\2\1\3\1\uffff\1\5\1\6\1\7\1\10\1\11\2\uffff\1\12\1\14\3"+
		"\uffff\1\17\1\4\1\13\10\uffff\1\16\2\uffff\1\15\1\uffff";
	static final String DFA21_specialS =
		"\41\uffff}>";
	static final String[] DFA21_transitionS = {
			"\2\10\2\uffff\1\10\22\uffff\1\10\1\21\1\11\2\uffff\1\7\1\uffff\1\15\1"+
			"\1\1\2\1\uffff\1\12\1\3\1\12\1\4\1\uffff\4\13\6\20\1\5\1\uffff\3\21\1"+
			"\6\1\uffff\32\21\6\uffff\5\21\1\17\15\21\1\16\6\21",
			"",
			"",
			"",
			"\12\23",
			"",
			"",
			"",
			"",
			"",
			"\1\23\1\uffff\12\24",
			"\1\25\1\uffff\12\20",
			"",
			"",
			"\1\26",
			"\1\27",
			"\1\25\1\uffff\12\24",
			"",
			"",
			"",
			"\1\23\1\uffff\12\24",
			"\2\30\10\31",
			"\1\32",
			"\1\33",
			"\1\34\1\uffff\12\31",
			"\1\34",
			"\1\35",
			"\1\36",
			"",
			"\1\21\2\uffff\12\21\7\uffff\32\21\6\uffff\32\21",
			"\1\40",
			"",
			"\1\21\2\uffff\12\21\7\uffff\32\21\6\uffff\32\21"
	};

	static final short[] DFA21_eot = DFA.unpackEncodedString(DFA21_eotS);
	static final short[] DFA21_eof = DFA.unpackEncodedString(DFA21_eofS);
	static final char[] DFA21_min = DFA.unpackEncodedStringToUnsignedChars(DFA21_minS);
	static final char[] DFA21_max = DFA.unpackEncodedStringToUnsignedChars(DFA21_maxS);
	static final short[] DFA21_accept = DFA.unpackEncodedString(DFA21_acceptS);
	static final short[] DFA21_special = DFA.unpackEncodedString(DFA21_specialS);
	static final short[][] DFA21_transition;

	static {
		int numStates = DFA21_transitionS.length;
		DFA21_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA21_transition[i] = DFA.unpackEncodedString(DFA21_transitionS[i]);
		}
	}

	protected class DFA21 extends DFA {

		public DFA21(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 21;
			this.eot = DFA21_eot;
			this.eof = DFA21_eof;
			this.min = DFA21_min;
			this.max = DFA21_max;
			this.accept = DFA21_accept;
			this.special = DFA21_special;
			this.transition = DFA21_transition;
		}
		@Override
		public String getDescription() {
			return "1:1: Tokens : ( T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | COMMENT | WS | STRING | INTEGER | DOUBLE | CHARACTER | BOOLEAN | DATE | IDENTIFIER );";
		}
	}

}
