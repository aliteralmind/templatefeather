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
/**
   <p>Can the template be {@linkplain com.github.aliteralmind.templatefeather.FeatherTemplate#isResettable() reset}?--This implies whether gaps can be {@linkplain com.github.aliteralmind.templatefeather.GapMap#isUnfillOk() unfilled}.</p>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://templatefeather.aliteralmind.com">{@code http://templatefeather.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/templatefeather">{@code https://github.com/aliteralmind/templatefeather}</a>
 **/
public enum Resettable  {
   /**
      <p>The template is resettable--gaps can be unfilled.</p>

      @see  #NO
      @see  #isYes()
    **/
   YES,
   /**
      <p>The template is not resettable--gaps cannot be unfilled.</p>

      @see  #YES
      @see  #isNo()
    **/
   NO;
   /**
      <p>Is this {@code Resettable} equal to {@code YES}?.</p>

      @return  <code>this == {@link #YES}</code>

      @see  #isNo()
    **/
   public final boolean isYes()  {
      return  this == YES;
   }
   /**
      <p>Is this {@code Resettable} equal to {@code NO}?.</p>

      @return  <code>this == {@link #NO}</code>
      @see  #isYes()
    **/
   public final boolean isNo()  {
      return  this == NO;
   }
   /**
      @return  {@link #YES}
    **/
   public Resettable trueValue()  {
      return  YES;
   }
   /**
      @return  {@link #NO}
    **/
   public Resettable falseValue()  {
      return  NO;
   }
   /**
      <p>Get a {@code Resettable} from an actual boolean.</p>

      @return  <code>(b ? {@link #YES} : {@link #NO})</code>
    **/
   public static final Resettable getForBoolean(boolean b)  {
      return  (b ? YES : NO);
   }
};
