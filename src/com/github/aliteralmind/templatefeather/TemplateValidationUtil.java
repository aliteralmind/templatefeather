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
   import  java.util.Arrays;
   import  java.util.Set;
   import  com.github.xbn.lang.CrashIfObject;
/**
   <p>Utilities for validating {@code FeatherTemplates}.</p>

 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://templatefeather.aliteralmind.com">{@code http://templatefeather.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/templatefeather">{@code https://github.com/aliteralmind/templatefeather}</a>
 **/
public class TemplateValidationUtil  {
   /**
      <p>If any required gaps are missing, crash. Otherwise, do nothing.</p>

    * @param  tmpl_varName  Descriptive name of template. <i>Should</i> not be {@code null} or empty.
    * @exception  IncorrectGapsException  If
      <br> &nbsp; &nbsp; {@link #hasRequiredGaps(FeatherTemplate, String[]) hasRequiredGaps}{@code (template, required_names)}
      <br>is {@code false}.
    * @see  #crashIfContainsUnexpectedGaps(FeatherTemplate, String, String[])
    */
   public static final void crashIfMissingRequiredGaps(FeatherTemplate template, String tmpl_varName, String[] required_names)  {
      if(!hasRequiredGaps(template, required_names))  {
         throw  new IncorrectGapsException(tmpl_varName + " missing required gaps. Required=" + Arrays.toString(required_names) + ". " + tmpl_varName + "=" + template);
      }
   }
   /**
      <p>If any required gaps are missing, crash. Otherwise, do nothing.</p>

    * @param  tmpl_varName  Descriptive name of template. <i>Should</i> not be {@code null} or empty.
    * @exception  IncorrectGapsException  If
      <br> &nbsp; &nbsp; {@link #hasNoUnexpectedGaps(FeatherTemplate, String[]) hasNoUnexpectedGaps}{@code (template, required_names)}
      <br>is {@code false}.
    * @see  #crashIfMissingRequiredGaps(FeatherTemplate, String, String[])
    */
   public static final void crashIfContainsUnexpectedGaps(FeatherTemplate template, String tmpl_varName, String[] expectedButNotRequired_names)  {
      if(!hasNoUnexpectedGaps(template, expectedButNotRequired_names))  {
         throw  new IncorrectGapsException(tmpl_varName + " has unexpected gaps. expectedButNotRequired_names=" + Arrays.toString(expectedButNotRequired_names) + ". " + tmpl_varName + "=" + template);
      }
   }
   /**
      <p>Does the template have all required gaps?.</p>

    * @param  template  May not be {@code null}.
    * @param  required_names  May not be {@code null}.
    * @see  #crashIfMissingRequiredGaps(FeatherTemplate, String, String[]) crashIfMissingRequiredGaps(ft,s,s[])
    * @see  #hasNoUnexpectedGaps(FeatherTemplate, String[]) crashIfMissingRequiredGaps(ft,s[])
    */
   public static final boolean hasRequiredGaps(FeatherTemplate template, String[] required_names)  {
      try  {
         GapMap map = template.getGapMap();
         for(String name : required_names)  {
            if(!map.contains(name))  {
               return  false;
            }
         }
      }  catch(RuntimeException rx)  {
         CrashIfObject.nnull(template, "template", null);
         throw  CrashIfObject.nullOrReturnCause(required_names, "required_names", null, rx);
      }
      return  true;
   }
   /**
      <p>Does a template have only the expected gaps (or a subset)?.</p>

    * @param  template  May not be {@code null}.
    * @param  expectedButNotRequired_names  May not be {@code null}.
    * @return  {@code true}  If the <i>unique set</i> of gaps in the template has exactly, or a subset of, the gaps in {@code expectedButNotRequired_names}.
    * @see  #crashIfContainsUnexpectedGaps(FeatherTemplate, String, String[]) crashIfContainsUnexpectedGaps(ft,s,s[])
    * @see  #hasRequiredGaps(FeatherTemplate, String[]) hasRequiredGaps(ft,s[])
    */
   public static final boolean hasNoUnexpectedGaps(FeatherTemplate template, String[] expectedButNotRequired_names)  {
      Set<String> actualGapNameSet = null;
      try  {
         actualGapNameSet = template.getGapMap().newNameSet();
         for(String name : expectedButNotRequired_names)  {
            actualGapNameSet.remove(name);
         }
      }  catch(RuntimeException rx)  {
         CrashIfObject.nnull(template, "template", null);
         throw  CrashIfObject.nullOrReturnCause(expectedButNotRequired_names, "expectedButNotRequired_names", null, rx);
      }
      return  (actualGapNameSet.size() == 0);
   }
   /**
      <p>If a template is not {@linkplain com.github.aliteralmind.templatefeather.FeatherTemplate#isResettable() resettable}, crash. Otherwise, do nothing.</p>

    * @param  template  May not be {@code null}.
    * @param  tmpl_varName  Descriptive name of the template. <i>Should</i> not be {@code null} or empty.
    * @exception  TemplateResettableException  If the template is not resettable.
    */
   public static final void crashIfNotResettable(FeatherTemplate template, String tmpl_varName)  {
      try  {
         if(!template.isResettable())  {
            throw  new TemplateResettableException(tmpl_varName + ".isResettable() is false.");
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(template, "template", null, rx);
      }
   }
   private TemplateValidationUtil()  {
      throw  new IllegalStateException("Do not instantiate.");
   }
}
