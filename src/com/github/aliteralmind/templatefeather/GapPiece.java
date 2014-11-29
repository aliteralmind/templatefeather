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
	import  com.github.xbn.lang.CrashIfObject;
/**
   <p>A single gap in a parsed template.</p>

	@since  0.1.0
	@author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://templatefeather.aliteralmind.com">{@code http://templatefeather.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/templatefeather">{@code https://github.com/aliteralmind/templatefeather}</a>
 **/
public class GapPiece extends TemplatePiece  {
	private final String originalWChars;
	/**
		<p>Create a new instance.</p>

		<p>Steps:<ol>
			<li>Calls <code>{@link com.github.xbn.text.template.TemplatePiece#TemplatePiece(String, GapMap, GapCharConfig) super}(gapNameOnly_noPrePostChars, map, char_config)</code></li>
			<li>Sets {@link #getOriginal() getOriginal}{@code ()} to <code>{@link #getCharConfig() getCharConfig}().{@link com.github.aliteralmind.templatefeather.GapCharConfig#getGapNameWithChars(String) getGapNameWithChars}(gapNameOnly_noPrePostChars)</code></li>
		</ol></p>

		@param  gapNameOnly_noPrePostChars  The name of the gap. May not be {@code null} or empty, and <i>should</i> not contain its {@linkplain com.github.aliteralmind.templatefeather.GapCharConfig#getStart() start} or {@linkplain com.github.aliteralmind.templatefeather.GapCharConfig#getEnd() end} characters. Get with {@link #getName() getName}{@code ()}*
		@see  #getPieceCopy(GapMap, GapCharConfig)
	 **/
	public GapPiece(String gapNameOnly_noPrePostChars, GapMap map, GapCharConfig char_config)  {
		super(gapNameOnly_noPrePostChars, map, char_config);
		originalWChars = getCharConfig().getGapNameWithChars(gapNameOnly_noPrePostChars);
	}
	/**
		<p>The name of this gap.</p>

		@return  <code>{@link com.github.aliteralmind.templatefeather.TemplatePiece TemplatePiece}.{@link com.github.aliteralmind.templatefeather.TemplatePiece#getRaw() getRaw}</code>
		@see  #GapPiece(String, GapMap, GapCharConfig)
	 **/
	public String getName()  {
		return  getRaw();
	}
	/**
		<p>Get the fill text.</p>
	 **/
	@Override
	public String getOriginal()  {
		return  originalWChars;
	}
	/**
		<p>Get the gap's fill text.</p>

		@return  <code>{@link com.github.aliteralmind.templatefeather.TemplatePiece#getGapMap() getGapMap}().{@link com.github.aliteralmind.templatefeather.GapMap#getFillText(String) getFillText}({@link #getName() getName})</code>
		@see  #getOriginal()
		@see  #getRenderedOrOriginalIfNot()
	 **/
	public String getRendered()  {
		return  getGapMap().getFillText(getName());
	}
	/**
		<p>Get the gap's fill text if it was filled, or the original gap text if not.</p>
	 **/
	public final String getRenderedOrOriginalIfNot()  {
		return  (isRendered() ? getRendered() : getOriginal());
	}
	/**
		<p>Is this element ready to be rendered?.</p>

		@return  {@code true} if {@link #getRendered() getRendered}{@code ()} may be safely called.
		@see  #getRenderedOrOriginalIfNot()
	 **/
	public boolean isRendered()  {
		return  getGapMap().isFilled(getName());
	}
	/**
	 	@return  <code>true</code> If {@code to_compareTo} is non-{@code null}, a {@code GapPiece}, and all its fields {@linkplain #areFieldsEqual(GapPiece) are equal}. This is implemented as suggested by Joshua Bloch in &quot;Effective Java&quot; (2nd ed, item 8, page 46).
	 **/
	@Override
	public boolean equals(Object to_compareTo)  {
		//Check for object equality first, since it's faster than instanceof.
		if(this == to_compareTo)  {
			return  true;
		}
		if(!(to_compareTo instanceof GapPiece))  {
			//to_compareTo is either null or not an GapPiece.
			//java.lang.Object.object(o):
			// "For any non-null reference value x, x.equals(null) should return false."
			//See the bottom of this class for a counter-argument (which I'm not going with).
			return  false;
		}

		//Safe to cast
		GapPiece o = (GapPiece)to_compareTo;

		//Finish with field-by-field comparison.
		return  areFieldsEqual(o);
	}
	/**
		<p>Are all relevant fields equal?.</p>

		@return  {@code true}  If {@code to_compareTo} has the same {@linkplain #getName() gap name} and, if {@linkplain #isRendered() filled}, the same {@linkplain #getRendered() fill-text}.
	 **/
	public boolean areFieldsEqual(GapPiece to_compareTo)  {
		try  {
			return  (isRendered() == to_compareTo.isRendered()  &&
				to_compareTo.getName().equals(getName())  &&
				to_compareTo.getRendered().equals(getRendered()));
		}  catch(RuntimeException rx)  {
			throw  CrashIfObject.nullOrReturnCause(to_compareTo, "to_compareTo", null, rx);
		}
	}
	public int hashCode()  {
		return  getOriginal().hashCode();
	}
	public String toString()  {
		return  getOriginal() + " (" + (isRendered() ? "" : "un") + "filled)";
	}
	/**
		@return  <code>(new {@link #GapPiece(String, GapMap, GapCharConfig) GapPiece}({@link #getName() getName}(), map, char_config))</code>
	 **/
	public GapPiece getPieceCopy(GapMap map, GapCharConfig char_config)  {
		return  (new GapPiece(getOriginal(), getName(), map, char_config));
	}
		private GapPiece(String gapNameWith_chars, String gapNameOnly_noPrePostChars, GapMap map, GapCharConfig char_config)  {
			super(gapNameOnly_noPrePostChars, map, char_config);
			originalWChars = gapNameWith_chars;
		}
}
