/*license*\
   Template Featherweight: Copyright (C) 2014, Jeff Epstein (aliteralmind __DASH__ github __AT__ yahoo __DOT__ com)

   This software is dual-licensed under the:
   - Lesser General Public License (LGPL) version 3.0 or, at your option, any later version;
   - Apache Software License (ASL) version 2.0.

   Either license may be applied at your discretion. More information may be found at
   - http://en.wikipedia.org/wiki/Multi-licensing.

   The text of both licenses is available in the root directory of this project, under the names "LICENSE_lgpl-3.0.txt" and "LICENSE_asl-2.0.txt". The latest copies may be downloaded at:
   - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
   - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
\*license*/
package  com.github.aliteralmind.templatefeather;
	import  com.github.xbn.lang.Copyable;
	import  com.github.xbn.lang.CrashIfObject;
	import  com.github.xbn.lang.IllegalArgumentStateException;
	import  com.github.xbn.regexutil.RegexReplacer;
	import  com.github.xbn.regexutil.RegexTokenizer;
	import  com.github.xbn.regexutil.z.RegexReplacer_Cfg;
	import  com.github.xbn.regexutil.z.RegexTokenizer_Cfg;
	import  java.util.regex.Matcher;
	import  java.util.regex.Pattern;
/**
   <p>The characters that must precede and follow gap names, their literal representations, and pre-compiled regular expressions as needed internally by the template.</p>

	@since  0.1.0
	@author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://templatefeather.aliteralmind.com">{@code http://templatefeather.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/templatefeather">{@code https://github.com/aliteralmind/templatefeather}</a>
 **/
public class GapCharConfig implements Copyable  {
	public static final char DEFAULT_PREFIX_CHAR     = '%';
	public static final char DEFAULT_POSTFIX_CHAR    = DEFAULT_PREFIX_CHAR;
	public static final String DEFAULT_LITERAL_PREFIX  = "__PCT__";
	public static final String DEFAULT_LITERAL_POSTFIX = DEFAULT_LITERAL_PREFIX;
	private final char           startChar                ;
	private final String         literalStartChar         ;
	private final char           endChar                  ;
	private final String         literalEndChar           ;
	//Replace literal with actual
	private final RegexReplacer  rplcrLiteralStartWithActual;
	private final RegexReplacer  rplcrActualStartWithLiteral;
	private final RegexReplacer  rplcrLiteralEndWithActual  ;
	private final RegexReplacer  rplcrActualEndWithLiteral  ;
	/**
		<p>Create a new instance with defaults.</p>

		<p>Equal to
		<br> &nbsp; &nbsp; <code>{@link #GapCharConfig(char, char, String, String) super}({@link #DEFAULT_PREFIX_CHAR}, {@link #DEFAULT_POSTFIX_CHAR}, {@link #DEFAULT_LITERAL_PREFIX}, {@link #DEFAULT_LITERAL_POSTFIX})</code></p>
	 **/
	public GapCharConfig()  {
		this(DEFAULT_PREFIX_CHAR, DEFAULT_POSTFIX_CHAR, DEFAULT_LITERAL_PREFIX, DEFAULT_LITERAL_POSTFIX);
	}
	/**
		<p>Create a new instance with specific characters and their literals.</p>

		<p>This pre-compiles the objects needed to {@linkplain #getWithLiteralsMadeActual(String) replace literal chars} to actual, and XXXvice-versa.</p>

		@param  start  The character that precedes a gap name. Get with {@link #getStart() getStart}{@code ()}
		@param  end  The character that follows a gap name. Get with {@link #getEnd() getEnd}{@code ()}.
		@param  literal_start  The string that must be used when a literal start character is needed. This and {@code literal_end} may both not be {@code null} or empty, and must contain only letters, digits, and underscores, <i>except</i> the start and end characters. Get with {@link #getLiteralStart() getLiteralStart}{@code ()}.
		@param  literal_end  The string that must be used when a literal end character is needed.. Get with {@link #getLiteralEnd() getLiteralEnd}{@code ()}.
		@see  #GapCharConfig()
	 **/
	public GapCharConfig(char start, char end, String literal_start, String literal_end)  {
		crashIfBadLiteral("Start", literal_start, start, end);
		crashIfBadLiteral("End", literal_end, start, end);

		rplcrLiteralStartWithActual = new RegexReplacer_Cfg().all().
			directLiteral(literal_start, Matcher.quoteReplacement((new Character(start)).toString())).
			build();
		rplcrActualStartWithLiteral = new RegexReplacer_Cfg().all().
			directLiteral((new Character(start)).toString(), Matcher.quoteReplacement(literal_start)).
			build();
		rplcrLiteralEndWithActual = new RegexReplacer_Cfg().all().
			directLiteral(literal_end, Matcher.quoteReplacement((new Character(end)).toString())).
			build();
		rplcrActualEndWithLiteral = new RegexReplacer_Cfg().all().
			directLiteral((new Character(end)).toString(), Matcher.quoteReplacement(literal_end)).
			build();

		startChar        = start;
		endChar          = end;
		literalStartChar = literal_start;
		literalEndChar   = literal_end;
	}
	/**
		<p>Create a new instance as a duplicate of another.</p>

		@param  to_copy  May not be {@code null}.
		@see  #getObjectCopy()
	 **/
	public GapCharConfig(GapCharConfig to_copy)  {
		try  {
			startChar     = to_copy.getStart();
		}  catch(RuntimeException rx)  {
			throw  CrashIfObject.nullOrReturnCause(to_copy, "to_copy", null, rx);
		}
		endChar          = to_copy.getEnd();
		literalStartChar = to_copy.getLiteralStart();
		literalEndChar   = to_copy.getLiteralEnd();

		rplcrLiteralStartWithActual = to_copy.rplcrLiteralStartWithActual.getObjectCopy();
		rplcrLiteralEndWithActual = to_copy.rplcrLiteralEndWithActual.getObjectCopy();
		rplcrActualStartWithLiteral = to_copy.rplcrActualStartWithLiteral.getObjectCopy();
		rplcrActualEndWithLiteral = to_copy.rplcrActualEndWithLiteral.getObjectCopy();
	}
		private static final void crashIfBadLiteral(String start_orEnd, String literal, char start, char end)  {
			if(literal.contains((new Character(start)).toString())  ||
					literal.contains((new Character(end)).toString())  ||
					!Pattern.matches("\\w+", literal))  {
				throw  new IllegalArgumentStateException("fieldable.getLiteralGap" + start_orEnd + "Char() (" + literal + ") must be only letters, digits, and underscores, and may not contain fieldable.getStartChar() ('" + start + "') or fieldable.getEndChar() ('" + end + "')");
			}
		}
	/**
		<p>The character that immediately-precedes a gap name in the original text.</p>

		@see  #getLiteralStart()
		@see  #getEnd()
	 **/
	public char getStart()  {
		return  startChar;
	}
	/**
		<p>The character that immediately-follows a gap name in the original text.</p>

		@see  #getLiteralEnd()
		@see  #getStart()
	 **/
	public char getEnd()  {
		return  endChar;
	}
	/**
		<p>The text to use when a literal start-char is needed. This is only for use in the text between gaps.</p>

		@see  #getStart()
		@see  #getLiteralEnd()
	 **/
	public String getLiteralStart()  {
		return  literalStartChar;
	}
	/**
		<p>The text to use when a literal end-char is needed. This is only for use in the text between gaps.</p>

		@see  #getEnd()
		@see  #getLiteralStart()
	 **/
	public String getLiteralEnd()  {
		return  literalEndChar;
	}
	/**
		<p>ReplacedInEachInput all literal-start and end characters with their actuals.</p>

		@return  <i>Essentially:</i>

<blockquote><pre>text.{@link java.lang.String#replaceAll(String, String) replaceAll}(getLiteralStart(), {@link java.util.regex.Matcher Matcher}.{@link java.util.regex.Matcher#quoteReplacement(String) quoteReplacement}((new Character({@link #getStart() getStart}())).toString())).
   replaceAll({@link #getLiteralEnd() getLiteralEnd}(), Matcher.quoteReplacement((new Character({@link #getEnd() getEnd}())).toString()))</pre></blockquote>
   	@see  #getWithActualsMadeLiteral(String)
	 **/
	public final String getWithLiteralsMadeActual(String text)  {
		String s = rplcrLiteralStartWithActual.getReplaced(text);
		return  rplcrLiteralEndWithActual.getReplaced(s);
	}
	/**
		<p>ReplacedInEachInput all literal-start and end characters with their actuals.</p>

		@return  <i>Essentially:</i>

<blockquote><pre>text.{@link java.lang.String#replaceAll(String, String) replaceAll}(new Character({@link #getStart() getStart}()), {@link java.util.regex.Matcher Matcher}.{@link java.util.regex.Matcher#quoteReplacement(String) quoteReplacement}((new Character({@link #getEnd() getEnd}())).toString()).
   replaceAll({@link #getLiteralEnd() getLiteralEnd}(), Matcher.quoteReplacement((getLiteralStart()).toString())))</pre></blockquote>
   	@see  #getWithActualsMadeLiteral(String)
	 **/
	public final String getWithActualsMadeLiteral(String text)  {
		String s = rplcrActualStartWithLiteral.getReplaced(text);
		return  rplcrActualEndWithLiteral.getReplaced(s);
	}
	/**
		<p>Get a new tokenizer for parsing the original template text into its parts.</p>

		@param  original_text  May not be {@code null}.
		@return  A new {@linkplain #newTemplateTokenizer(String, Appendable) regex-tokenizer} that returns both {@linkplain com.github.xbn.regexutil.z.RegexTokenizer_CfgForNeeder#separators() separators} (gaps) and {@linkplain com.github.xbn.regexutil.z.RegexTokenizer_CfgForNeeder#allBetweens() all betweens}, where the separator regular expression is {@link #getGapRegex(char, char) getGapRegex}, and the {@linkplain com.github.xbn.regexutil.RegexTokenizer#setNewSearch(Object, int) search text} is {@code original_text}.
	 **/
	public RegexTokenizer newTemplateTokenizer(String original_text, Appendable debugDest_ifNonNull)  {
		Pattern gapPtrn = Pattern.compile(GapCharConfig.getGapRegex(getStart(), getEnd()));
		RegexTokenizer tokenizer = new RegexTokenizer_Cfg().separators().allBetweens().separator(gapPtrn).debugTo(debugDest_ifNonNull).build();
		tokenizer.setNewSearch(original_text, 1);
		return  tokenizer;
	}
	/**
		<p>Get the index of the first-found start <i>or</i> end character.</p>

		@param  text  May not be {@code null}, and <i>should</i> be non-empty.
		@return  The character index of the first {@linkplain #getStart() start} or {@linkplain #getEnd() end} character found in the text. If not found: {@code -1}.
	 **/
	public int indexOfStartOrEndChar(String text)  {
		int idxStart = -1;
		try  {
			idxStart = text.indexOf(getStart());
		}  catch(RuntimeException rx)  {
			throw  CrashIfObject.nullOrReturnCause(text, "text", null, rx);
		}
		int idxEnd = text.indexOf(getEnd());
		if(idxStart == -1)  {
			if(idxEnd == -1)  {
				return  -1;
			}
			return  idxStart;
		}  else if(idxEnd == -1)  {
			return  idxStart;
		}  else  {
			return  ((idxStart <= idxEnd) ? idxStart : idxEnd);
		}
	}
	/**
		<p>Get a gap as it exists in the original text--surrounded by its start and end characters.</p>

		@param  gap_name  <i>Should</i> not be {@code null} or empty, and <i>should</i> be a valid gap name.
		@return  <code>{@link #getStart() getStart}() + gap_name + {@link #getEnd() getEnd}()</code>
	 **/
	public String getGapNameWithChars(String gap_name)  {
		return  getStart() + gap_name + getEnd();
	}
	/**
		<p>Strip the start and end characters from the gap text ({@code "%gapname%"} to {@code "gapname"}).</p>

		@param  prefix_name_postfix  May not be {@code null} or contain less than three characters, and <i>should</i> be equal to
		<br> &nbsp; &nbsp; <code>{@link #getStart() getStart}() + <i>[gap-name]</i> + {@link #getEnd() getEnd}()</code>
	 **/
	public static final String getGapNameWithNoChars(String prefix_name_postfix)  {
		try  {
			return  prefix_name_postfix.substring(1, (prefix_name_postfix.length() - 1));
		}  catch(StringIndexOutOfBoundsException sbx)  {
			throw  new IllegalArgumentException("prefix_name_postfix (\"" + prefix_name_postfix + "\") is less than three in length.", sbx);
		}  catch(RuntimeException rx)  {
			throw  CrashIfObject.nullOrReturnCause(prefix_name_postfix, "prefix_name_postfix", null, rx);
		}
	}
	/**
		<p>Get a duplicate of this <code>GapCharConfig</code>.</p>

		@return <code>({@link #GapCharConfig(GapCharConfig) GapCharConfig}(this))</code>
	 **/
	public GapCharConfig getObjectCopy()  {
		return  (new GapCharConfig(this));
	}
	/**
		<p>The regular expression representing a gap in the original (unparsed) template text.</p>

		@return  <code>&quot;\\Q&quot; + start + &quot;\\E\\w+\\Q&quot; + end + &quot;\\E&quot;</code>
	 **/
	public static final String getGapRegex(char start, char end)  {
		return  "\\Q" + start + "\\E\\w+\\Q" + end + "\\E";
	}
	public String toString()  {
		return  "start='" + getStart() + "' (\"" + getLiteralStart() + "\"), end='" + getEnd() + "' (\"" + getLiteralEnd() + "\")";
	}
}