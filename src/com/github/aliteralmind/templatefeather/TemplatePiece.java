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
   <p>A single element in a parsed template: Either a gap name, or its &quot;between&quot; text.</p>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://templatefeather.aliteralmind.com">{@code http://templatefeather.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/templatefeather">{@code https://github.com/aliteralmind/templatefeather}</a>
 **/
public abstract class TemplatePiece  {
   private final String        rawText   ;
   private final GapMap        map       ;
   private final GapCharConfig charConfig;
   /**
      <p>Create a new instance.</p>

      @param  raw_text  May not be {@code null}. Get with {@link #getRaw() getRaw}{@code ()}.
      @param  map  May not be {@code null}. Get with {@link #getGapMap() getGapMap}{@code ()}. For <i>read-only</i>.
      @param  char_config  May not be {@code null}. Get with {@link #getCharConfig() getCharConfig}{@code ()}. For <i>read-only</i>.
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
      <p>The gap map, for <i>read-only</i>.</p>

      @see  #TemplatePiece(String, GapMap, GapCharConfig)
    **/
   protected final GapMap getGapMap()  {
      return  map;
   }
   /**
      <p>The char config, for <i>read-only</i>.</p>

      @see  #TemplatePiece(String, GapMap, GapCharConfig)
    **/
   protected final GapCharConfig getCharConfig()  {
      return  charConfig;
   }
   /**
      <p>The original between text, or the name-only of the gap (no pre/post chars).</p>

      @see  #TemplatePiece(String, GapMap, GapCharConfig) this(s,gm,gcc)
      @see  #getRendered()
      @see  #getOriginal()
      @see  #getRenderedOrOriginalIfNot()
    **/
   protected final String getRaw()  {
      return  rawText;
   }
   /**
      <p>The text as it exists in the pre-parsed template.</p>

      @see  #TemplatePiece(String, GapMap, GapCharConfig) this(s,gm,gcc)
      @see  #getRaw()
    **/
   public abstract String getOriginal();
   /**
      <p>Get the rendered text if it's ready. Otherwise, get the original text.</p>

      @return  <code>({@link #isRendered() isRendered}() ? {@link #getRendered() getRendered}() : {@link #getOriginal() getOriginal}())</code>
    **/
   public abstract String getRenderedOrOriginalIfNot();
   /**
      <p>Get the fully-rendered text.</p>

      @exception  GapFilledException  If {@link #isRendered() isRendered}{@code ()} is {@code false}
      @see  #getRaw()
    **/
   public abstract String getRendered();
   /**
      <p>Is this element ready to be rendered?.</p>

      @return  {@code true} If {@link #getRendered() getRendered}{@code ()} may be safely called.
    **/
   public abstract boolean isRendered();
   /**
      <p>Duplicate this piece for a new template.</p>

      @param  map  May not be {@code null}.
      @param  char_config  May not be {@code null}.
    **/
   public abstract TemplatePiece getPieceCopy(GapMap map, GapCharConfig char_config);
}
