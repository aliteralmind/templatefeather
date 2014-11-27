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
	<P>Can the template be {@linkplain com.github.aliteralmind.templatefeather.FeatherTemplate#isResettable() reset}?--This implies whether gaps can be {@linkplain com.github.aliteralmind.templatefeather.GapMap#isUnfillOk() unfilled}.</P>

	@since 0.1.0
	@author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://templatefeather.aliteralmind.com">{@code http://templatefeather.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/templatefeather">{@code https://github.com/aliteralmind/templatefeather}</A>
 **/
public enum Resettable  {
	/**
		<P>The template is resettable--gaps can be unfilled.</P>

		@see  #NO
		@see  #isYes()
	 **/
	YES,
	/**
		<P>The template is not resettable--gaps cannot be unfilled.</P>

		@see  #YES
		@see  #isNo()
	 **/
	NO;
	/**
		<P>Is this {@code Resettable} equal to {@code YES}?.</P>

		@return  <CODE>this == {@link #YES}</CODE>

		@see  #isNo()
	 **/
	public final boolean isYes()  {
		return  this == YES;
	}
	/**
		<P>Is this {@code Resettable} equal to {@code NO}?.</P>

		@return  <CODE>this == {@link #NO}</CODE>
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
		<P>Get a {@code Resettable} from an actual boolean.</P>

		@return  <CODE>(b ? {@link #YES} : {@link #NO})</CODE>
	 **/
	public static final Resettable getForBoolean(boolean b)  {
		return  (b ? YES : NO);
	}
};