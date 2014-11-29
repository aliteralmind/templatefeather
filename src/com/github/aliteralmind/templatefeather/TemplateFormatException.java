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
	<p>Indicates an error in the original template text, such as no gaps, or gap {@linkplain com.github.aliteralmind.codelet.GapCharConfig#getStart() start} or {@linkplain com.github.aliteralmind.codelet.GapCharConfig#getEnd() end} characters existing between the gaps.</p>

	@since  0.1.0
	@author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://templatefeather.aliteralmind.com">{@code http://templatefeather.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/templatefeather">{@code https://github.com/aliteralmind/templatefeather}</a>

 **/
public class TemplateFormatException extends IllegalArgumentException  {
	/**
	 *
	 */
	private static final long serialVersionUID = 3100963757019972384L;
	public TemplateFormatException(String message)  {
		super(message);
	}
	public TemplateFormatException()  {
	}
	public TemplateFormatException(String message, Throwable cause)  {
		super(message, cause);
	}
	public TemplateFormatException(Throwable cause)  {
		super(cause);
	}
}
