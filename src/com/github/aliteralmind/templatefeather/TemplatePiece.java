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
   import  java.util.Objects;
/**
   <P>A single element in a parsed template: Either a gap name, or its &quot;between&quot; text.</P>

   @since 0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://templatefeather.aliteralmind.com">{@code http://templatefeather.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/templatefeather">{@code https://github.com/aliteralmind/templatefeather}</A>
 **/
public abstract class TemplatePiece  {
   private final String        rawText   ;
   private final GapMap        map       ;
   private final GapCharConfig charConfig;
   /**
      <P>Create a new instance.</P>

      @param  raw_text  May not be {@code null}. Get with {@link #getRaw() getRaw}{@code ()}.
      @param  map  May not be {@code null}. Get with {@link #getGapMap() getGapMap}{@code ()}. For <I>read-only</I>.
      @param  char_config  May not be {@code null}. Get with {@link #getCharConfig() getCharConfig}{@code ()}. For <I>read-only</I>.
    **/
   public TemplatePiece(String raw_text, GapMap map, GapCharConfig char_config)  {
      Objects.requireNonNull(raw_text, "raw_text");
      Objects.requireNonNull(map, "map");
      Objects.requireNonNull(char_config, "char_config");
      rawText = raw_text;
      this.map = map;
      charConfig = char_config;
   }
   /**
      <P>The gap map, for <I>read-only</I>.</P>

      @see  #TemplatePiece(String, GapMap, GapCharConfig)
    **/
   protected final GapMap getGapMap()  {
      return  map;
   }
   /**
      <P>The char config, for <I>read-only</I>.</P>

      @see  #TemplatePiece(String, GapMap, GapCharConfig)
    **/
   protected final GapCharConfig getCharConfig()  {
      return  charConfig;
   }
   /**
      <P>The original between text, or the name-only of the gap (no pre/post chars).</P>

      @see  #TemplatePiece(String, GapMap, GapCharConfig) this(s,gm,gcc)
      @see  #getRendered()
      @see  #getOriginal()
      @see  #getRenderedOrOriginalIfNot()
    **/
   protected final String getRaw()  {
      return  rawText;
   }
   /**
      <P>The text as it exists in the pre-parsed template.</P>

      @see  #TemplatePiece(String, GapMap, GapCharConfig) this(s,gm,gcc)
      @see  #getRaw()
    **/
   public abstract String getOriginal();
   /**
      <P>Get the rendered text if it's ready. Otherwise, get the original text.</P>

      @return  <CODE>({@link #isRendered() isRendered}() ? {@link #getRendered() getRendered}() : {@link #getOriginal() getOriginal}())</CODE>
    **/
   public abstract String getRenderedOrOriginalIfNot();
   /**
      <P>Get the fully-rendered text.</P>

      @exception  GapFilledException  If {@link #isRendered() isRendered}{@code ()} is {@code false}
      @see  #getRaw()
    **/
   public abstract String getRendered();
   /**
      <P>Is this element ready to be rendered?.</P>

      @return  {@code true} If {@link #getRendered() getRendered}{@code ()} may be safely called.
    **/
   public abstract boolean isRendered();
   /**
      <P>Duplicate this piece for a new template.</P>

      @param  map  May not be {@code null}.
      @param  char_config  May not be {@code null}.
    **/
   public abstract TemplatePiece getPieceCopy(GapMap map, GapCharConfig char_config);
}
