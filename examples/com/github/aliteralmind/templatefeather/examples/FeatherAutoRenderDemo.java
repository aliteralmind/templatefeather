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
package  com.github.aliteralmind.templatefeather.examples;
   import  com.github.aliteralmind.templatefeather.FeatherTemplate;
/**
   <P>{@linkplain com.github.aliteralmind.templatefeather.FeatherTemplate#setAutoRendererX(Appendable) Auto-render} demonstration.</P>

   <P>{@code java com.github.aliteralmind.templatefeather.examples.FeatherAutoRenderDemo}</P>

   @since 0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://templatefeather.aliteralmind.com">{@code http://templatefeather.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/templatefeather">{@code https://github.com/aliteralmind/templatefeather}</A>
 **/
public class FeatherAutoRenderDemo  {
   public static final void main(String[] ignored)  {
      String origText = "Hello %name%. I like you, %name%, %pct_num%__PCT__ guaranteed.";

      FeatherTemplate tmpl = new FeatherTemplate(origText,
         null);  //debug on=System.out, off=null

      tmpl.setAutoRenderer(System.out);

      System.out.println("<--Auto renderer set.");
      tmpl.fill("name", "Ralph");

      System.out.println("<--Filled first gap");
      tmpl.fill("pct_num", 45);

      System.out.println("<--Filled second-and-final gap");
   }
}
