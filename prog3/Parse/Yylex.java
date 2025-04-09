package Parse;
import ErrorMsg.ErrorMsg;


class Yylex implements Lexer {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final char YYEOF = '\uFFFF';

private void newline() {
  errorMsg.newline(yychar);
}
private void err(int pos, String s) {
  errorMsg.error(pos, s);
}
private void err(String s) {
  err(yychar, s);
}
private java_cup.runtime.Symbol tok(int kind) {
    return tok(kind, null);
}
private java_cup.runtime.Symbol tok(int kind, Object value) {
    return new java_cup.runtime.Symbol(kind, yychar, yychar + yylength(), value);
}
private ErrorMsg errorMsg;
private StringBuffer string = new StringBuffer();
Yylex(java.io.InputStream s, ErrorMsg e) {
  this(s);
  errorMsg = e;
}
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yy_lexical_state;

	Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int STRING = 1;
	private final int YYINITIAL = 0;
	private final int COMMENT = 2;
	private final int yy_state_dtrans[] = {
		0,
		169,
		173
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private char yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YYEOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YYEOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_start () {
		++yychar;
		++yy_buffer_start;
	}
	private void yy_pushback () {
		--yy_buffer_end;
	}
	private void yy_mark_start () {
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
private int [][] unpackFromString(int size1, int size2, String st)
    {
      int colonIndex = -1;
      String lengthString;
      int sequenceLength = 0;
      int sequenceInteger = 0;
      int commaIndex;
      String workString;
      int res[][] = new int[size1][size2];
      for (int i= 0; i < size1; i++)
	for (int j= 0; j < size2; j++)
	  {
	    if (sequenceLength == 0) 
	      {	
		commaIndex = st.indexOf(',');
		if (commaIndex == -1)
		  workString = st;
		else
		  workString = st.substring(0, commaIndex);
		st = st.substring(commaIndex+1);
		colonIndex = workString.indexOf(':');
		if (colonIndex == -1)
		  {
		    res[i][j] = Integer.parseInt(workString);
		  }
		else 
		  {
		    lengthString = workString.substring(colonIndex+1);  
		    sequenceLength = Integer.parseInt(lengthString);
		    workString = workString.substring(0,colonIndex);
		    sequenceInteger = Integer.parseInt(workString);
		    res[i][j] = sequenceInteger;
		    sequenceLength--;
		  }
	      }
	    else 
	      {
		res[i][j] = sequenceInteger;
		sequenceLength--;
	      }
	  }
      return res;
    }
	private int yy_acpt[] = {
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR
	};
	private int yy_cmap[] = {
		0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 1, 0, 0, 2, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0,
		3, 4, 5, 0, 0, 6, 7, 0,
		8, 9, 10, 11, 12, 13, 14, 15,
		16, 17, 17, 17, 17, 17, 17, 17,
		18, 18, 19, 20, 21, 22, 23, 0,
		0, 24, 24, 24, 24, 25, 26, 27,
		27, 27, 27, 27, 27, 27, 27, 28,
		27, 27, 27, 27, 27, 27, 27, 27,
		29, 27, 27, 30, 31, 32, 33, 27,
		0, 24, 24, 24, 24, 24, 34, 27,
		27, 27, 27, 27, 27, 27, 35, 27,
		27, 27, 27, 27, 36, 27, 27, 27,
		29, 27, 27, 37, 38, 39, 40, 0
		
	};
	private int yy_rmap[] = {
		0, 1, 1, 1, 1, 2, 3, 1,
		1, 4, 5, 1, 6, 7, 8, 9,
		1, 1, 10, 11, 12, 13, 1, 1,
		14, 1, 15, 1, 1, 1, 1, 1,
		1, 1, 1, 1, 1, 1, 1, 1,
		1, 16, 1, 1, 1, 17, 1, 1,
		1, 1, 18, 1, 1, 19, 1, 1,
		1, 1, 1, 1, 20, 1, 20, 21,
		22, 19, 19, 20, 23, 24, 19, 19,
		25, 26, 19, 27, 28, 29, 30, 19,
		19, 31, 32, 33, 34, 19, 19, 35,
		19, 36, 19, 19, 19, 19, 19, 19,
		19, 19, 19, 19, 19, 19, 19, 37,
		19, 19, 19, 38, 19, 19, 19, 19,
		39, 19, 19, 40, 41, 18, 42, 20,
		20, 43, 44, 20, 20, 45, 46, 20,
		47, 48, 49, 50, 20, 20, 51, 52,
		53, 54, 20, 20, 55, 20, 56, 20,
		20, 20, 20, 20, 20, 20, 20, 20,
		20, 20, 20, 57, 20, 20, 20, 58,
		20, 20, 20, 20, 59, 20, 20, 60,
		61, 62, 63, 39, 59, 64, 65, 66,
		67, 68, 69, 70 
	};
	private int yy_nxt[][] = unpackFromString(71,41,
"1,2:2,3,64,4,5,6,7,8,9,10,11,12,13,14,15,78:2,16,17,18,19,20,21:6,22,1,23,24,21:3,25,26,27,28,-1:63,30,-1:25,31,-1:14,32,-1:40,33,-1:29,34,-1:10,35,-1:31,36,-1:8,37,38,-1:31,63,-1:36,39,-1:11,40,-1:34,78:3,-1:10,117,-1:32,41,42,-1:40,43,-1:40,44,45,-1:33,21:3,-1:5,21:6,-1:4,21:3,-1:26,46,-1:40,47,-1:15,48,-1:24,51,-1:40,52,-1:34,50:3,-1:5,50:3,-1:7,50,-1:6,53,-1,53:3,-1,53:25,-1,53:9,60,-1,60:8,-1,60:30,-1:14,49,-1:48,29,-1:18,53,-1,53:3,-1,53:16,93,53:8,-1,53:10,-1,53:3,-1,53,94,53:14,95,53:8,-1,53:10,-1,53:3,-1,53:16,96,53:8,-1,53:10,-1,53:3,-1,53:5,97,53:10,98,53:8,-1,53:10,-1,53:3,-1,53:7,99,53:8,100,101,53:7,-1,53:10,-1,53:3,-1,53:8,167,53:16,-1,53:10,-1,53:3,-1,53:16,102,53:8,-1,53:9,-1:16,78:3,-1:22,53,-1,53:3,-1,53:15,103,104,53:8,-1,53:10,-1,53:3,-1,53:16,105,53:8,-1,53:10,-1,53:3,-1,53:16,106,107,53:7,-1,53:10,-1,53:3,-1,53:10,84:3,53:5,84:6,53,-1,53:2,84:3,53:5,-1,53:3,-1,53:16,108,53:8,-1,53:10,-1,53:3,-1,53:16,109,53:8,-1,53:6,110,53:3,-1,53:3,-1,53:16,113,53:8,-1,53:10,-1,53:3,-1,53:16,114,53:8,-1,53:10,-1,53:3,-1,53:10,112:3,53:5,112:3,53:4,-1,53:2,112,53:7,-1,53:3,-1,53:16,92,53:8,-1,53:9,60,-1,60:8,-1,60:11,145,60:18,54:5,55,54:10,56:2,54:13,57,54:2,-1,58,59,54:4,60,-1,60:8,-1,60:11,146,60:19,-1,60:5,147,60:2,-1,60:11,148,60:18,-1:15,61,-1:6,33,-1:18,60,-1,60:8,-1,149,60:10,150,60:19,-1,60:8,-1,60:2,151,60:8,152,153,60:18,-1,60:8,-1,60:3,168,60:27,-1,60:8,39,60:11,154,60:18,53,-1,53:3,-1,53:10,170:3,53:10,171,53,-1,53:9,60,-1,60:8,-1,60:10,155,156,60:19,-1,60:8,-1,60:11,157,60:19,-1,60:8,-1,60:11,158,159,60:18,-1,60:8,-1,60:5,137:3,60:5,137:6,60:4,137:3,60:5,-1,60:8,-1,60:11,160,60:19,-1,60:8,-1,60:11,161,60:15,162,60:3,-1,60:8,-1,60:11,165,60:2,179,60:16,-1,60:8,-1,60:11,166,60:19,-1,60:8,-1,60:5,164:3,60:5,164:3,60:7,164,60:6,53,-1,53:3,-1,53:8,111,53:16,-1,53:9,60,-1,60:8,-1,60:3,163,60:26,53,2,65,66,115,4,68,69,70,71,72,73,74,75,76,77,131,170:2,79,80,81,82,83,84:6,85,118,86,87,84:3,88,89,90,91,53,-1,53:3,-1,53:10,170:3,53:12,-1,53:9,60,2,119,120,116,67,121,122,123,124,125,126,127,128,129,130,174,176:2,132,133,134,135,136,137:6,138,60,139,140,137:3,141,142,143,144,60,-1,60:8,-1,60:5,176:3,60:10,172,60:12,-1,60:8,-1,60:12,62,60:18,-1,60:8,-1,60:5,176:3,60:23,-1,60:8,-1,60:12,175,60:18,-1,60:8,-1,60:15,177,60:15,-1,60:8,-1,60:17,178,60:12");
	public java_cup.runtime.Symbol nextToken ()
		throws java.io.IOException {
		char yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			if (YYEOF != yy_lookahead) {
				yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YYEOF == yy_lookahead && true == yy_initial) {

    return tok(sym.EOF, null);
				}
				else if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_to_mark();
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_pushback();
					}
					if (0 != (YY_START & yy_anchor)) {
						yy_move_start();
					}
					switch (yy_last_accept_state) {
					case 1:
						{err("Illegal character: " + yytext());}
					case -2:
						break;
					case 2:
						{newline();}
					case -3:
						break;
					case 3:
						{}
					case -4:
						break;
					case 4:
						{string.setLength(0); yybegin(STRING);}
					case -5:
						break;
					case 5:
						{return tok(sym.MODULUS, null);}
					case -6:
						break;
					case 6:
						{return tok(sym.BITWISEAND, null);}
					case -7:
						break;
					case 7:
						{return tok(sym.LPAREN, null);}
					case -8:
						break;
					case 8:
						{return tok(sym.RPAREN, null);}
					case -9:
						break;
					case 9:
						{return tok(sym.TIMES, null);}
					case -10:
						break;
					case 10:
						{return tok(sym.PLUS, null);}
					case -11:
						break;
					case 11:
						{return tok(sym.COMMA, null);}
					case -12:
						break;
					case 12:
						{return tok(sym.MINUS, null);}
					case -13:
						break;
					case 13:
						{return tok(sym.PERIOD, null);}
					case -14:
						break;
					case 14:
						{return tok(sym.DIVIDE, null);}
					case -15:
						break;
					case 15:
						{return tok(sym.DECIMAL_LITERAL, Integer.parseInt(yytext()));}
					case -16:
						break;
					case 16:
						{return tok(sym.COLON, null);}
					case -17:
						break;
					case 17:
						{return tok(sym.SEMICOLON, null);}
					case -18:
						break;
					case 18:
						{return tok(sym.LT, null);}
					case -19:
						break;
					case 19:
						{return tok(sym.ASSIGN, null);}
					case -20:
						break;
					case 20:
						{return tok(sym.GT, null);}
					case -21:
						break;
					case 21:
						{return tok(sym.ID, yytext());}
					case -22:
						break;
					case 22:
						{return tok(sym.LBRACK, null);}
					case -23:
						break;
					case 23:
						{return tok(sym.RBRACK, null);}
					case -24:
						break;
					case 24:
						{return tok(sym.BWISEXOR, null);}
					case -25:
						break;
					case 25:
						{return tok(sym.LBRACE, null);}
					case -26:
						break;
					case 26:
						{return tok(sym.BWISEOR, null);}
					case -27:
						break;
					case 27:
						{return tok(sym.RBRACE, null);}
					case -28:
						break;
					case 28:
						{return tok(sym.TILDE, null);}
					case -29:
						break;
					case 29:
						{return tok(sym.NEQ, null);}
					case -30:
						break;
					case 30:
						{return tok(sym.MODASSIGN, null);}
					case -31:
						break;
					case 31:
						{return tok(sym.AND, null);}
					case -32:
						break;
					case 32:
						{return tok(sym.BWISEANDASSIGN, null);}
					case -33:
						break;
					case 33:
						{return tok(sym.MULASSIGN, null);}
					case -34:
						break;
					case 34:
						{return tok(sym.INCREMENT, null);}
					case -35:
						break;
					case 35:
						{return tok(sym.ADDASSIGN, null);}
					case -36:
						break;
					case 36:
						{return tok(sym.DECREMENT, null);}
					case -37:
						break;
					case 37:
						{return tok(sym.SUBASSIGN, null);}
					case -38:
						break;
					case 38:
						{return tok(sym.ARROW, null);}
					case -39:
						break;
					case 39:
						{yybegin(COMMENT);}
					case -40:
						break;
					case 40:
						{return tok(sym.DIVASSIGN, null);}
					case -41:
						break;
					case 41:
						{return tok(sym.LSHIFT, null);}
					case -42:
						break;
					case 42:
						{return tok(sym.LE, null);}
					case -43:
						break;
					case 43:
						{return tok(sym.EQ, null);}
					case -44:
						break;
					case 44:
						{return tok(sym.GE, null);}
					case -45:
						break;
					case 45:
						{return tok(sym.RSHIFT, null);}
					case -46:
						break;
					case 46:
						{return tok(sym.BWISEXORASSIGN, null);}
					case -47:
						break;
					case 47:
						{return tok(sym.BWISEORASSIGN, null);}
					case -48:
						break;
					case 48:
						{return tok(sym.OR, null);}
					case -49:
						break;
					case 49:
						{return tok(sym.ELIPSES, null);}
					case -50:
						break;
					case 50:
						{return tok(sym.DECIMAL_LITERAL, Integer.decode(yytext()));}
					case -51:
						break;
					case 51:
						{return tok(sym.LSHIFTASSIGN, null);}
					case -52:
						break;
					case 52:
						{return tok(sym.RSHIFTASSIGN, null);}
					case -53:
						break;
					case 53:
						{string.append(yytext());}
					case -54:
						break;
					case 54:
						{err("Illegal escape sequence: " + yytext());}
					case -55:
						break;
					case 55:
						{string.append('\"');}
					case -56:
						break;
					case 56:
						{
    try {
        int value = Integer.parseInt(yytext().substring(1), 8);
        string.append((char)value);
    } catch (NumberFormatException e) {
        err("Invalid octal escape sequence");
    }
}
					case -57:
						break;
					case 57:
						{string.append('\\');}
					case -58:
						break;
					case 58:
						{string.append('\n');}
					case -59:
						break;
					case 59:
						{string.append('\t');}
					case -60:
						break;
					case 60:
						{}
					case -61:
						break;
					case 61:
						{yybegin(YYINITIAL);}
					case -62:
						break;
					case 62:
						{err("EOF read inside comment"); return tok(sym.EOF, null);}
					case -63:
						break;
					case 64:
						{err("Illegal character: " + yytext());}
					case -64:
						break;
					case 65:
						{newline();}
					case -65:
						break;
					case 66:
						{}
					case -66:
						break;
					case 67:
						{string.setLength(0); yybegin(STRING);}
					case -67:
						break;
					case 68:
						{return tok(sym.MODULUS, null);}
					case -68:
						break;
					case 69:
						{return tok(sym.BITWISEAND, null);}
					case -69:
						break;
					case 70:
						{return tok(sym.LPAREN, null);}
					case -70:
						break;
					case 71:
						{return tok(sym.RPAREN, null);}
					case -71:
						break;
					case 72:
						{return tok(sym.TIMES, null);}
					case -72:
						break;
					case 73:
						{return tok(sym.PLUS, null);}
					case -73:
						break;
					case 74:
						{return tok(sym.COMMA, null);}
					case -74:
						break;
					case 75:
						{return tok(sym.MINUS, null);}
					case -75:
						break;
					case 76:
						{return tok(sym.PERIOD, null);}
					case -76:
						break;
					case 77:
						{return tok(sym.DIVIDE, null);}
					case -77:
						break;
					case 78:
						{return tok(sym.DECIMAL_LITERAL, Integer.parseInt(yytext()));}
					case -78:
						break;
					case 79:
						{return tok(sym.COLON, null);}
					case -79:
						break;
					case 80:
						{return tok(sym.SEMICOLON, null);}
					case -80:
						break;
					case 81:
						{return tok(sym.LT, null);}
					case -81:
						break;
					case 82:
						{return tok(sym.ASSIGN, null);}
					case -82:
						break;
					case 83:
						{return tok(sym.GT, null);}
					case -83:
						break;
					case 84:
						{return tok(sym.ID, yytext());}
					case -84:
						break;
					case 85:
						{return tok(sym.LBRACK, null);}
					case -85:
						break;
					case 86:
						{return tok(sym.RBRACK, null);}
					case -86:
						break;
					case 87:
						{return tok(sym.BWISEXOR, null);}
					case -87:
						break;
					case 88:
						{return tok(sym.LBRACE, null);}
					case -88:
						break;
					case 89:
						{return tok(sym.BWISEOR, null);}
					case -89:
						break;
					case 90:
						{return tok(sym.RBRACE, null);}
					case -90:
						break;
					case 91:
						{return tok(sym.TILDE, null);}
					case -91:
						break;
					case 92:
						{return tok(sym.NEQ, null);}
					case -92:
						break;
					case 93:
						{return tok(sym.MODASSIGN, null);}
					case -93:
						break;
					case 94:
						{return tok(sym.AND, null);}
					case -94:
						break;
					case 95:
						{return tok(sym.BWISEANDASSIGN, null);}
					case -95:
						break;
					case 96:
						{return tok(sym.MULASSIGN, null);}
					case -96:
						break;
					case 97:
						{return tok(sym.INCREMENT, null);}
					case -97:
						break;
					case 98:
						{return tok(sym.ADDASSIGN, null);}
					case -98:
						break;
					case 99:
						{return tok(sym.DECREMENT, null);}
					case -99:
						break;
					case 100:
						{return tok(sym.SUBASSIGN, null);}
					case -100:
						break;
					case 101:
						{return tok(sym.ARROW, null);}
					case -101:
						break;
					case 102:
						{return tok(sym.DIVASSIGN, null);}
					case -102:
						break;
					case 103:
						{return tok(sym.LSHIFT, null);}
					case -103:
						break;
					case 104:
						{return tok(sym.LE, null);}
					case -104:
						break;
					case 105:
						{return tok(sym.EQ, null);}
					case -105:
						break;
					case 106:
						{return tok(sym.GE, null);}
					case -106:
						break;
					case 107:
						{return tok(sym.RSHIFT, null);}
					case -107:
						break;
					case 108:
						{return tok(sym.BWISEXORASSIGN, null);}
					case -108:
						break;
					case 109:
						{return tok(sym.BWISEORASSIGN, null);}
					case -109:
						break;
					case 110:
						{return tok(sym.OR, null);}
					case -110:
						break;
					case 111:
						{return tok(sym.ELIPSES, null);}
					case -111:
						break;
					case 112:
						{return tok(sym.DECIMAL_LITERAL, Integer.decode(yytext()));}
					case -112:
						break;
					case 113:
						{return tok(sym.LSHIFTASSIGN, null);}
					case -113:
						break;
					case 114:
						{return tok(sym.RSHIFTASSIGN, null);}
					case -114:
						break;
					case 115:
						{string.append(yytext());}
					case -115:
						break;
					case 116:
						{}
					case -116:
						break;
					case 118:
						{err("Illegal character: " + yytext());}
					case -117:
						break;
					case 119:
						{newline();}
					case -118:
						break;
					case 120:
						{}
					case -119:
						break;
					case 121:
						{return tok(sym.MODULUS, null);}
					case -120:
						break;
					case 122:
						{return tok(sym.BITWISEAND, null);}
					case -121:
						break;
					case 123:
						{return tok(sym.LPAREN, null);}
					case -122:
						break;
					case 124:
						{return tok(sym.RPAREN, null);}
					case -123:
						break;
					case 125:
						{return tok(sym.TIMES, null);}
					case -124:
						break;
					case 126:
						{return tok(sym.PLUS, null);}
					case -125:
						break;
					case 127:
						{return tok(sym.COMMA, null);}
					case -126:
						break;
					case 128:
						{return tok(sym.MINUS, null);}
					case -127:
						break;
					case 129:
						{return tok(sym.PERIOD, null);}
					case -128:
						break;
					case 130:
						{return tok(sym.DIVIDE, null);}
					case -129:
						break;
					case 131:
						{return tok(sym.DECIMAL_LITERAL, Integer.parseInt(yytext()));}
					case -130:
						break;
					case 132:
						{return tok(sym.COLON, null);}
					case -131:
						break;
					case 133:
						{return tok(sym.SEMICOLON, null);}
					case -132:
						break;
					case 134:
						{return tok(sym.LT, null);}
					case -133:
						break;
					case 135:
						{return tok(sym.ASSIGN, null);}
					case -134:
						break;
					case 136:
						{return tok(sym.GT, null);}
					case -135:
						break;
					case 137:
						{return tok(sym.ID, yytext());}
					case -136:
						break;
					case 138:
						{return tok(sym.LBRACK, null);}
					case -137:
						break;
					case 139:
						{return tok(sym.RBRACK, null);}
					case -138:
						break;
					case 140:
						{return tok(sym.BWISEXOR, null);}
					case -139:
						break;
					case 141:
						{return tok(sym.LBRACE, null);}
					case -140:
						break;
					case 142:
						{return tok(sym.BWISEOR, null);}
					case -141:
						break;
					case 143:
						{return tok(sym.RBRACE, null);}
					case -142:
						break;
					case 144:
						{return tok(sym.TILDE, null);}
					case -143:
						break;
					case 145:
						{return tok(sym.NEQ, null);}
					case -144:
						break;
					case 146:
						{return tok(sym.MODASSIGN, null);}
					case -145:
						break;
					case 147:
						{return tok(sym.AND, null);}
					case -146:
						break;
					case 148:
						{return tok(sym.BWISEANDASSIGN, null);}
					case -147:
						break;
					case 149:
						{return tok(sym.INCREMENT, null);}
					case -148:
						break;
					case 150:
						{return tok(sym.ADDASSIGN, null);}
					case -149:
						break;
					case 151:
						{return tok(sym.DECREMENT, null);}
					case -150:
						break;
					case 152:
						{return tok(sym.SUBASSIGN, null);}
					case -151:
						break;
					case 153:
						{return tok(sym.ARROW, null);}
					case -152:
						break;
					case 154:
						{return tok(sym.DIVASSIGN, null);}
					case -153:
						break;
					case 155:
						{return tok(sym.LSHIFT, null);}
					case -154:
						break;
					case 156:
						{return tok(sym.LE, null);}
					case -155:
						break;
					case 157:
						{return tok(sym.EQ, null);}
					case -156:
						break;
					case 158:
						{return tok(sym.GE, null);}
					case -157:
						break;
					case 159:
						{return tok(sym.RSHIFT, null);}
					case -158:
						break;
					case 160:
						{return tok(sym.BWISEXORASSIGN, null);}
					case -159:
						break;
					case 161:
						{return tok(sym.BWISEORASSIGN, null);}
					case -160:
						break;
					case 162:
						{return tok(sym.OR, null);}
					case -161:
						break;
					case 163:
						{return tok(sym.ELIPSES, null);}
					case -162:
						break;
					case 164:
						{return tok(sym.DECIMAL_LITERAL, Integer.decode(yytext()));}
					case -163:
						break;
					case 165:
						{return tok(sym.LSHIFTASSIGN, null);}
					case -164:
						break;
					case 166:
						{return tok(sym.RSHIFTASSIGN, null);}
					case -165:
						break;
					case 167:
						{string.append(yytext());}
					case -166:
						break;
					case 168:
						{}
					case -167:
						break;
					case 170:
						{return tok(sym.DECIMAL_LITERAL, Integer.parseInt(yytext()));}
					case -168:
						break;
					case 171:
						{string.append(yytext());}
					case -169:
						break;
					case 172:
						{}
					case -170:
						break;
					case 174:
						{return tok(sym.DECIMAL_LITERAL, Integer.parseInt(yytext()));}
					case -171:
						break;
					case 175:
						{}
					case -172:
						break;
					case 176:
						{return tok(sym.DECIMAL_LITERAL, Integer.parseInt(yytext()));}
					case -173:
						break;
					case 177:
						{}
					case -174:
						break;
					case 178:
						{}
					case -175:
						break;
					case 179:
						{}
					case -176:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
					}
				}
			}
		}
	}
}
