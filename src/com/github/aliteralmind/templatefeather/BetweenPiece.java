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
   <p>Static text in a parsed template, found between two gaps (or before the first or after the last).</p>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://templatefeather.aliteralmind.com">{@code http://templatefeather.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/templatefeather">{@code https://github.com/aliteralmind/templatefeather}</a>
 **/
public class BetweenPiece extends TemplatePiece  {
   private final String rendered;
   /**
      <p>Create a new instance.</p>

      <p>Steps:<ol>
         <li>Calls <code>{@link com.github.xbn.text.template.TemplatePiece#TemplatePiece(String, GapMap, GapCharConfig) super}(original_text, map, char_config)</code></li>
         <li>Sets {@link #getRendered() getRendered}{@code ()} to <code>{@link #getCharConfig() getCharConfig}().{@link com.github.aliteralmind.templatefeather.GapCharConfig#getWithLiteralsMadeActual(String) getWithLiteralsMadeActual}(original_text)</code></li>
      </ol></p>

      @param  original_text  The text existing between either the start-or-end of the text and a gap, or between two gaps. May not be {@code null}, and <i>should</i> have all its {@linkplain com.github.aliteralmind.templatefeather.GapCharConfig#getLiteralStart() literal start} and {@linkplain com.github.aliteralmind.templatefeather.GapCharConfig#getLiteralEnd() end} characters already interpeted. Get with {@link com.github.aliteralmind.codelet.TemplatePiece#getRaw() getRaw}{@code ()}*
      @see  #getPieceCopy(GapMap, GapCharConfig)
    **/
   public BetweenPiece(String original_text, GapMap map, GapCharConfig char_config)  {
      super(original_text, map, char_config);
      rendered = getCharConfig().getWithLiteralsMadeActual(original_text);
   }
   /**
      <p>Create a new instance as a duplicate of another.</p>

      <p>Equal to
      <br/> &nbsp; &nbsp; <code>{@link com.github.aliteralmind.templatefeather.TemplatePiece#TemplatePiece(TemplatePiece) super}(to_copy)</code></p>

      @param  to_copy  May not be <code>null</code>.
      @see  #getObjectCopy()
      @see  #BetweenPiece(String, GapMap, GapCharConfig) this(s,gm,gcc)
   public BetweenPiece(BetweenPiece to_copy)  {
      super(to_copy);
   }
    **/
   /**
      <p>Get the original text, with any literal start or end characters restored.</p>

      @return  {@link com.github.aliteralmind.codelet.TemplatePiece#getRaw() getRaw}()*
      @return  <code>{@link com.github.xbn.text.template.TemplatePiece#getCharConfig() getCharConfig}()*.{@link com.github.xbn.text.template.GapCharConfig#getWithActualsMadeLiteral(String) getWithActualsMadeLiteral}({@link com.github.aliteralmind.codelet.TemplatePiece#getRaw() getRaw}()*)</code>
    **/
   public String getOriginal()  {
      return  getRaw();
   }
   /**
      <p>Get the rendered text (between text is always ready).</p>

      @return  {@link #getRendered() getRendered}{@code ()}
    **/
   public final String getRenderedOrOriginalIfNot()  {
      return  getRendered();
   }
   /**
      <p>Get the rendered text, with literal start and end characters interpreted.</p>

      @return  {@link com.github.aliteralmind.codelet.TemplatePiece#getRaw() getRaw}()*
      @see  #BetweenPiece(String, GapMap, GapCharConfig) this(s,gm,gcc)
    **/
   public String getRendered()  {
      return  rendered;
   }
   /**
      <p>Is this element ready to be rendered?.</p>

      @return  Yes. Yes it is.
    **/
   public boolean isRendered()  {
      return  true;
   }
   /**
    	@return  <code>true</code> If {@code to_compareTo} is non-{@code null}, a {@code BetweenPiece}, and all its fields {@linkplain #areFieldsEqual(BetweenPiece) are equal}. This is implemented as suggested by Joshua Bloch in &quot;Effective Java&quot; (2nd ed, item 8, page 46).
    **/
   @Override
   public boolean equals(Object to_compareTo)  {
      //Check for object equality first, since it's faster than instanceof.
      if(this == to_compareTo)  {
         return  true;
      }
      if(!(to_compareTo instanceof BetweenPiece))  {
         //to_compareTo is either null or not an BetweenPiece.
         //java.lang.Object.object(o):
         // "For any non-null reference value x, x.equals(null) should return false."
         //See the bottom of this class for a counter-argument (which I'm not going with).
         return  false;
      }

      //Safe to cast
      BetweenPiece o = (BetweenPiece)to_compareTo;

      //Finish with field-by-field comparison.
      return  areFieldsEqual(o);
   }
   /**
      <p>Are all relevant fields equal?.</p>

      @return  {@code true}  If {@code to_compareTo} has the same {@linkplain #getRaw() text}.
    **/
   public boolean areFieldsEqual(BetweenPiece to_compareTo)  {
      try  {
         return  to_compareTo.equals(getRaw());
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(to_compareTo, "to_compareTo", null, rx);
      }
   }
   public int hashCode()  {
      return  getRaw().hashCode();
   }
   public String toString()  {
      return  "\"" + getRaw() + "\"";
   }
   /**
      @return  <code>(new {@link #BetweenPiece(String, GapMap, GapCharConfig) BetweenPiece}({@link #getOriginal() getOriginal}(), map, char_config))</code>
    **/
   public BetweenPiece getPieceCopy(GapMap map, GapCharConfig char_config)  {
      return  (new BetweenPiece(getRendered(), getOriginal(), map, char_config));
   }
      private BetweenPiece(String rendered, String original_text, GapMap map, GapCharConfig char_config)  {
         super(original_text, map, char_config);
         this.rendered = rendered;
      }
}
