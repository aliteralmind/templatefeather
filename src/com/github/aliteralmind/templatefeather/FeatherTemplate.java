/*license*\
   XBN-Java: Template Featherweight

   Copyright (C) 2014, Jeff Epstein (aliteralmind __DASH__ github __AT__ yahoo __DOT__ com)

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
   import  com.github.xbn.array.z.XbnIndexOutOfBoundsException_Cfg;
   import  com.github.xbn.io.RTIOException;
   import  com.github.xbn.io.SimpleDebuggable;
   import  com.github.xbn.io.TextAppenter;
   import  com.github.xbn.lang.Copyable;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.regexutil.RegexTokenizer;
   import  com.github.xbn.regexutil.TokenizerElement;
   import  com.github.xbn.text.padchop.NewVzblPadChopFor;
   import  com.github.xbn.text.padchop.VzblPadChop;
   import  java.io.IOException;
   import  java.util.ArrayList;
   import  java.util.Collections;
   import  java.util.List;
/**
   <P>A template.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://templatefeather.aliteralmind.com">{@code http://templatefeather.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/templatefeather">{@code https://github.com/aliteralmind/templatefeather}</A>
 **/
public class FeatherTemplate extends SimpleDebuggable implements Copyable  {
   /**
      <P>The default capacity to initialize the internal array-list of template-pieces to--Equal to {@code 100}.</P>

      @see  #FeatherTemplate(String, Appendable)
    **/
   public static final int DEFAULT_INIT_PIECE_LIST_CAPACITY = 100;
   private final List<TemplatePiece> immutablePieceList;
   private final GapMap              gapMap    			 ;
   private final GapCharConfig       charConfig			 ;
   private Appendable autoRenderDest;
   private int autoRenderPieceIdx;
   private static final VzblPadChop VPC_DBG = NewVzblPadChopFor.trimEscChopWithDDD(true, null, 50);

   /**
      <P>Create a new instance with defaults.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link #FeatherTemplate(String, GapCharConfig, int, Resettable, Appendable) this}(original_text, (new GapCharConfig()), {@link com.github.aliteralmind.templatefeather.Resettable Resettable}.{@link com.github.aliteralmind.templatefeather.Resettable#NO NO})</CODE></P>
    **/
   public FeatherTemplate(String original_text, Appendable debugDest_ifNonNull)  {
      this(original_text, (new GapCharConfig()), Resettable.NO, debugDest_ifNonNull);
   }
   /**
      <P>Create a new instance.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; <CODE>{@link #FeatherTemplate(String, GapCharConfig, int, Resettable, Appendable) this}(original_text, char_config, {@link #DEFAULT_INIT_PIECE_LIST_CAPACITY}, resettable)</CODE></P>
    **/
   public FeatherTemplate(String original_text, GapCharConfig char_config, Resettable resettable, Appendable debugDest_ifNonNull)  {
      this(original_text, char_config, DEFAULT_INIT_PIECE_LIST_CAPACITY, resettable, debugDest_ifNonNull);
   }
   /**
      <P>Create a new instance.</P>

      <P>This {@linkplain com.github.aliteralmind.templatefeather.GapCharConfig#newTemplateTokenizer(String, Appendable) parses} original text into its {@linkplain #getPieceList() parts}.</P>

      @param  original_text  May not be {@code null} and must be a validly-formatted template (containing at least one gap).
      @param  char_config  Defines the characters that surround gap names, and their literal counterparts. May not be {@code null}. Get with {@link #getCharConfig() getCharConfig}{@code ()}.
      @param  initialPieceList_capacity  The {@linkplain java.util.ArrayList#ArrayList(int) capacity} to initialize the internal {@linkplain java.util.ArrayList array list} to, in preparation for parsing the original text. Ideally this should be the number of (non-unique) gaps, doubled, plus one.
      @param  resettable  May not be {@code null}. If {@link com.github.aliteralmind.templatefeather.Resettable#YES YES}, then then it is possible to {@linkplain #unfill} the template, which also means that gaps may be {@linkplain #unfill(String) unfilled}. Get with {@link #isResettable() isResettable}{@code ()}. <I>If {@linkplain #setAutoRendererX(Appendable) auto-rendering}, this should be set to {@link com.github.aliteralmind.templatefeather.Resettable#NO NO}. Unfilling a gap does not affect <B>already output</B> gaps.</I>
      @see  #FeatherTemplate(String, Appendable)
      @see  #FeatherTemplate(String, GapCharConfig, Resettable, Appendable)
      @see  #FeatherTemplate(FeatherTemplate, Appendable)
    **/
   public FeatherTemplate(String original_text, GapCharConfig char_config, int initialPieceList_capacity, Resettable resettable, Appendable debugDest_ifNonNull)  {
      setDebug(debugDest_ifNonNull, (debugDest_ifNonNull != null));
      gapMap = new GapMap(char_config, resettable, debugDest_ifNonNull);
      ArrayList<TemplatePiece> pieceList = null;
      try  {
         pieceList = new ArrayList<TemplatePiece>(initialPieceList_capacity);
      }  catch(IllegalArgumentException iax)  {
         throw  new IllegalArgumentException("initialPieceList_capacity=" + initialPieceList_capacity, iax);
      }
      RegexTokenizer tknzr = null;
      try  {
         tknzr = char_config.newTemplateTokenizer(original_text, debugDest_ifNonNull);
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(char_config, "char_config", null, rx);
      }

      charConfig = char_config;

      boolean isBetween = true;
      while(tknzr.hasNext())  {
         TokenizerElement element = tknzr.next();

         if(isBetween)  {
            assert  element.isBetween()  :  "Not a between: " + element;
            String text = element.getText();

            int idxStartEnd = charConfig.indexOfStartOrEndChar(text);
            if(idxStartEnd != -1)  {
               boolean isStart =(text.charAt(idxStartEnd) == charConfig.getStart());
               throw  new IllegalArgumentException("Unescaped " +
                  (isStart ? "start" : "end") +
                  " character ('" + text.charAt(idxStartEnd) + "') in original_text, at index " + idxStartEnd +
                  ". Relevant between token: \"" + text + "\". Use getCharConfig().getLiteral" +
                  (isStart ? "Start" : "End") + "() (\"" +
                  (isStart ? charConfig.getLiteralStart() : charConfig.getLiteralEnd()) + "\")");
            }
            pieceList.add(new BetweenPiece(text, gapMap, charConfig));
         }  else  {
            assert  element.isSeparator()  :  "Not a separator: " + element;
            String gapWithChars = element.getText();
            String gapName = GapCharConfig.getGapNameWithNoChars(gapWithChars);

            if(!gapMap.contains(gapName))  {
               gapMap.add(gapName);
            }
            pieceList.add(new GapPiece(gapName, gapMap, charConfig));
         }
         isBetween = !isBetween;
      }
      immutablePieceList = Collections.unmodifiableList(pieceList);

      gapMap.lock();
      setAutoRenderer(null);
   }
   /**
      <P>Create a new template with the same original-text as an existing template. The new template is {@linkplain com.github.aliteralmind.templatefeather.GapMap#isFilled() unfilled}, {@linkplain #setAutoRenderer(Appendable) auto-rendering} is disabled, and {@linkplain com.github.xbn.io.Debuggable#isDebugOn() debugging} is off.</P>

      @param  to_copy  May not be {@code null}.
      @see  #getObjectCopy()
      @see  #getObjectCopy(Appendable)
      @see  #FeatherTemplate(String, GapCharConfig, int, Resettable, Appendable)
    **/
   public FeatherTemplate(FeatherTemplate to_copy, Appendable debugDest_ifNonNull)  {
      ArrayList<TemplatePiece> pieceList = null;
      try  {
         pieceList = new ArrayList<TemplatePiece>(to_copy.immutablePieceList.size());
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(to_copy, "to_copy", null, rx);
      }

      autoRenderPieceIdx = 0;
      gapMap = to_copy.getGapMap().getObjectCopy();
      charConfig = to_copy.getCharConfig().getObjectCopy();

      for(TemplatePiece piece : to_copy.immutablePieceList)  {
         pieceList.add(piece.getPieceCopy(gapMap, charConfig));
      }
      immutablePieceList = Collections.unmodifiableList(pieceList);

      setDebug(debugDest_ifNonNull, (debugDest_ifNonNull != null));
      setAutoRenderer(null);
   }
   /**
      <P>An immutable list of all template pieces.</P>

      @return  An {@linkplain java.util.Collections#unmodifiableList(List) immutable list} containing all gaps and betweens (the text between each gap). The first and last elements are {@linkplain com.github.aliteralmind.templatefeather.BetweenPiece betweens}. Each between is next to a {@linkplain com.github.aliteralmind.templatefeather.GapPiece gap}, and each gap is next to a between. If there are two gaps immediately next to each other, they are separated by an empty-string between. The number of <I>non-unique</I> gaps can be determined by
      <BR> &nbsp; &nbsp; <CODE>((getPieceList().size() - 1) / 2)</CODE>
      @see  #getGapMap()
      @see  #getBetweenPiece(int)
      @see  #getGapPiece(int)
    **/
   public List<TemplatePiece> getPieceList()  {
      return  immutablePieceList;
   }
   /**
      <P>Get a between piece.</P>

      @param  index  Must refer to a between element.
      @exception  XbnIndexOutOfBoundsException  If {@code index} is invalid, given <CODE>{@link #getPieceList() getPieceList}().size()</CODE>.
      @exception  ClassCastException  If the element is a gap.
      @see  #getGapPiece(int)
    **/
   public BetweenPiece getBetweenPiece(int index)  {
      try  {
         return  (BetweenPiece)getPieceList().get(index);
      }  catch(IndexOutOfBoundsException ibx)  {
         throw  new XbnIndexOutOfBoundsException_Cfg().
            badIndex(index, "index").
            absCollectionSize(getPieceList(), "getPieceList()").
            buildWithCause(ibx);
      }  catch(ClassCastException ccx)  {
         throw  new ClassCastException("Piece index " + index + " is a gap: [" + getPieceList().get(index) + "]. Original exception: " + ccx);
      }
   }
   /**
      <P>Get a gap piece.</P>

      @param  index  Must refer to a gap element.
      @exception  XbnIndexOutOfBoundsException  If {@code index} is invalid, given <CODE>{@link #getPieceList() getPieceList}().size()</CODE>.
      @exception  ClassCastException  If the element is a between.
      @see  #getGapPiece(int)
    **/
   public GapPiece getGapPiece(int index)  {
      try  {
         return  (GapPiece)getPieceList().get(index);
      }  catch(IndexOutOfBoundsException ibx)  {
         throw  new XbnIndexOutOfBoundsException_Cfg().
            badIndex(index, "index").
            absCollectionSize(getPieceList(), "getPieceList()").
            buildWithCause(ibx);
      }  catch(ClassCastException ccx)  {
         throw  new ClassCastException("Piece index " + index + " is a between: [" + getPieceList().get(index) + "]. Original exception: " + ccx);
      }
   }
   /**
      <P>Get the fully-rendered text.</P>

      @return  <CODE>{@link #appendFilled(Appendable) appendFilled}(new StringBuilder()).toString()</CODE>
    **/
   public String getFilled()  {
      return  appendFilled(new StringBuilder()).toString();
   }
   /**
      <P>Append the fully-rendered text, with runtime exceptions only.</P>

      @return  <CODE>{@link #appendFilledX(Appendable) appendFilledX}(new StringBuilder()).toString()</CODE>
      @exception  RTIOException  If a {@code java.io.IOException} is thrown for any reason.
    **/
   public Appendable appendFilled(Appendable to_appendTo)  {
      try  {
         return  appendFilledX(to_appendTo);
      }  catch(IOException iox)  {
         throw  new RTIOException(iox);
      }
   }
   /**
      <P>Append the fully-rendered text, with checked exceptions (<A HREF="{@docRoot}/overview-summary.html#xmpl_fully">example</A>). This is unaffected by--and does not adversely affect--any {@linkplain #setAutoRendererX(Appendable) auto-render} currently in process.</P>

      <P>For {@linkplain #getPieceList() each piece} in order, this appends their {@linkplain com.github.aliteralmind.templatefeather.TemplatePiece#getRendered() rendered texts}.</P>

      @param  to_appendTo  May not be {@code null}.
      @return  {@code to_appendTo}
      @exception  GapUnfilledException  If <CODE>{@link #getGapMap() getGapMap}().{@link com.github.aliteralmind.templatefeather.GapMap#isFilled() isFilled}()</CODE> is {@code false}.
      @exception  IOException  If appending fails.
      @see  #appendFilled(Appendable)
      @see  #getFilled()
      @see  #appendPartiallyFilledX(Appendable)
      @see  #appendOriginalX(Appendable)
    **/
   public Appendable appendFilledX(Appendable to_appendTo) throws IOException  {
      if(!getGapMap().isFilled())  {
         throw  new GapUnfilledException(getGapMap().toString());
      }
      for(TemplatePiece piece : immutablePieceList)  {
         try  {
            to_appendTo.append(piece.getRendered());
         }  catch(RuntimeException rx)  {
            throw  CrashIfObject.nullOrReturnCause(to_appendTo, "to_appendTo", null, rx);
         }
      }
      return  to_appendTo;
   }
   /**
      <P>Get the rendered text with any unfilled gaps replaced by their original text.</P>

      @return  <CODE>{@link #appendPartiallyFilled(Appendable) appendPartiallyFilled}(new StringBuilder()).toString()</CODE>
    **/
   public String getPartiallyFilled()  {
      return  appendPartiallyFilled(new StringBuilder()).toString();
   }
   /**
      <P>Get the rendered text with any unfilled gaps replaced by their original text.</P>

      @return  <CODE>{@link #appendPartiallyFilledX(Appendable) appendPartiallyFilledX}(new StringBuilder()).toString()</CODE>
      @exception  RTIOException  If a {@code java.io.IOException} is thrown for any reason.
    **/
   public Appendable appendPartiallyFilled(Appendable to_appendTo)  {
      try  {
         return  appendPartiallyFilledX(to_appendTo);
      }  catch(IOException iox)  {
         throw  new RTIOException(iox);
      }
   }
   /**
      <P>Get the rendered text with any unfilled gaps replaced by their {@linkplain com.github.aliteralmind.codelet.GapCharConfig#getGapNameWithChars(String) original text}. This is unaffected by--and does not adversely affect--any {@linkplain #setAutoRendererX(Appendable) auto-render} currently in process.</P>

      <P>For {@linkplain #getPieceList() each piece} in order, this appends their {@linkplain com.github.aliteralmind.templatefeather.TemplatePiece#getRenderedOrOriginalIfNot() rendered-unless-not-ready} texts.</P>

      @param  to_appendTo  May not be {@code null}.
      @return  {@code to_appendTo}
      @exception  IOException  If appending fails.
      @see  #appendPartiallyFilled(Appendable)
      @see  #getPartiallyFilled()
      @see  #appendFilledX(Appendable)
    **/
   public Appendable appendPartiallyFilledX(Appendable to_appendTo) throws IOException  {
      for(TemplatePiece piece : immutablePieceList)  {
         try  {
            to_appendTo.append(piece.getRenderedOrOriginalIfNot());
         }  catch(RuntimeException rx)  {
            throw  CrashIfObject.nullOrReturnCause(to_appendTo, "to_appendTo", null, rx);
         }
      }
      return  to_appendTo;
   }
   /**
      <P>Get the original text.</P>

      @return  <CODE>{@link #appendOriginal(Appendable) appendOriginal}(new StringBuilder()).toString()</CODE>
    **/
   public String getOriginal()  {
      return  appendOriginal(new StringBuilder()).toString();
   }
   /**
      <P>Get the original text.</P>

      @return  <CODE>{@link #appendOriginalX(Appendable) appendOriginalX}(new StringBuilder()).toString()</CODE>
      @exception  RTIOException  If a {@code java.io.IOException} is thrown for any reason.
    **/
   public Appendable appendOriginal(Appendable to_appendTo)  {
      try  {
         return  appendOriginalX(to_appendTo);
      }  catch(IOException iox)  {
         throw  new RTIOException(iox);
      }
   }
   /**
      <P>Get the original text. This is unaffected by--and does not adversely affect--any {@linkplain #setAutoRendererX(Appendable) auto-render} currently in process.</P>

      <P>For {@linkplain #getPieceList() each piece} in order, this appends their {@linkplain com.github.aliteralmind.templatefeather.TemplatePiece#getOriginal() original texts}.</P>

      @param  to_appendTo  May not be {@code null}.
      @return  {@code to_appendTo}
      @exception  IOException  If appending fails.
      @see  #appendOriginal(Appendable)
      @see  #getOriginal()
      @see  #appendFilledX(Appendable)
    **/
   public Appendable appendOriginalX(Appendable to_appendTo) throws IOException  {
      for(TemplatePiece piece : immutablePieceList)  {
         try  {
            to_appendTo.append(piece.getOriginal());
         }  catch(RuntimeException rx)  {
            throw  CrashIfObject.nullOrReturnCause(to_appendTo, "to_appendTo", null, rx);
         }
      }
      return  to_appendTo;
   }
   /**
      <P>Get the fully-rendered text.</P>

      @return  <CODE>{@link #appendFilledAndReset(Appendable) appendFilledAndReset}(new StringBuilder()).toString()</CODE>
    **/
   public String getFilledAndReset()  {
      return  appendFilledAndReset(new StringBuilder()).toString();
   }
   /**
      <P>Append the fully-rendered text, with runtime exceptions only.</P>

      @return  <CODE>{@link #appendFilledAndResetX(Appendable) appendFilledAndResetX}(new StringBuilder()).toString()</CODE>
      @exception  RTIOException  If a {@code java.io.IOException} is thrown for any reason.
    **/
   public Appendable appendFilledAndReset(Appendable to_appendTo)  {
      try  {
         return  appendFilledAndResetX(to_appendTo);
      }  catch(IOException iox)  {
         throw  new RTIOException(iox);
      }
   }
   /**
      <P>Get the fully-rendered text and reset the template.</P>

      <P>This calls {@link #appendFilledX(Appendable) appendFilledX}{@code (to_appendTo)} then {@link #unfill() unfill}{@code ()}.</P>

      @return  {@code to_appendTo}
      @exception  TemplateResettableException  If {@link #isResettable() isResettable}{@code ()} is {@code false}.
      @see  #appendFilledAndReset(Appendable)
      @see  #getFilledAndReset()
    **/
   public Appendable appendFilledAndResetX(Appendable to_appendTo) throws IOException  {
      appendFilledX(to_appendTo);
      unfill();
      return  to_appendTo;
   }
   /**
      <P>Set the auto-rendering output destination.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; {@link #setAutoRendererX(Appendable) setAutoRendererX}{@code (render_dest)}</P>

      @exception  RTIOException  If a {@code java.io.IOException} is thrown for any reason.
    **/
   public void setAutoRenderer(Appendable render_dest)  {
      try  {
         setAutoRendererX(render_dest);
      }  catch(IOException iox)  {
         throw  new RTIOException(iox);
      }
   }
   /**
      <P>Set the auto-rendering output destination, and start rendering.</P>

      <P>Auto-rendering may be safely started and ended at any time, regardless how many gaps are or are not filled. In addition, the {@linkplain #appendFilledX(Appendable) fully rendered} (or {@linkplain #appendPartiallyFilledX(Appendable) partial} or {@linkplain #appendOriginalX(Appendable) original}) output may be retrieved at any time without adversely affecting auto-rendering.</P>

      <P>Steps<OL>
         <LI>Sets {@link #getAutoRenderIndex() getAutoRenderIndex}{@code ()} to zero</LI>
         <LI>Outputs all text up to, but not including, the first unfilled gap.</LI>
         <LI>Each time a gap is {@linkplain #fill(String, Object) filled}, this repeats: All text to just before the next unfilled gap is output.</LI>
         <LI>When the final gap is filled, all remaining text is output, and auto-rendering is deactivated:<OL>
            <LI>{@link #getAutoRenderer() getAutoRenderer}{@code ()} is set to {@code null}</LI>
            <LI>{@code getAutoRenderIndex()} is set to {@code -1}.</LI>
         </OL>.</LI>
      </OL></P>

      <H3>Example</H3>

{@.codelet.and.out com.github.aliteralmind.templatefeather.examples.FeatherAutoRenderDemo:eliminateCmtBlocksPkgLineAndPkgReferences(true, true, false)}

      <P><B>Warning:</B> If the template is {@linkplain #isResettable() resettable}, this means that gaps can be {@linkplain #unfill(String) unfilled}, even though they may have already been output.</P>

      @param  render_dest  If {@code null}, auto-rendering is deactivated. Otherwise, this immediately outputs up rendered text up-to-but-not-including the first <I>unfilled</I> gap. Get with {@link #getAutoRenderer() getAutoRenderer}{@code ()}
      @exception  IllegalStateException  If <CODE>{@link #getGapMap() getGapMap}().{@link com.github.aliteralmind.templatefeather.GapMap#isFilled() isFilled}()</CODE> is {@code true}.
      @see  #isAutoRendering()
    **/
   public void setAutoRendererX(Appendable render_dest) throws IOException  {

      if(render_dest == null)  {
         autoRenderDest = null;
         autoRenderPieceIdx = -1;
      }  else  {
         if(getGapMap().isFilled())  {
            throw  new IllegalStateException("getGapMap().isFilled() is true.");
         }
         autoRenderDest = render_dest;
         autoRenderPieceIdx = 0;
         continueAutoRenderX();
      }
   }
      private void continueAutoRender()  {
         try  {
            continueAutoRenderX();
         }  catch(IOException iox)  {
            throw  new RTIOException(iox);
         }
      }
      private void continueAutoRenderX() throws IOException  {
         if(autoRenderPieceIdx == -1)  {
            return;
         }
         for(; autoRenderPieceIdx < immutablePieceList.size(); autoRenderPieceIdx++)  {
            TemplatePiece piece = immutablePieceList.get(autoRenderPieceIdx);
            if(!piece.isRendered())  {
               return;
            }
            autoRenderDest.append(piece.getRendered());
         }

         autoRenderPieceIdx = -1;
         autoRenderDest = null;
      }
   /**
      <P>The auto-render destination.</P>

      @return  If {@link #isAutoRendering() isAutoRendering}{@code ()} is<UL>
         <LI>{@code true}: The {@linkplain #setAutoRendererX(Appendable) auto-render} outputter.</LI>
         <LI>{@code false}: {@code null}</LI>
      </UL>
    **/
   public Appendable getAutoRenderer()  {
      return  autoRenderDest;
   }
   /**
      <P>The next piece-index to be auto-rendered. This is the index of the first non-unique {@linkplain com.github.aliteralmind.templatefeather.GapPiece gap} in the template <I>not yet filled</I> and, therefore, not yet output.</P>

      @see  #setAutoRendererX(Appendable)
    **/
   public int getAutoRenderIndex()  {
      return  autoRenderPieceIdx;
   }
   /**
      <P>Is an auto-render underway?.</P>

      @return  <CODE>({@link #getAutoRenderIndex() getAutoRenderIndex}() != -1)</CODE>
      @see  #setAutoRendererX(Appendable)
    **/
   public boolean isAutoRendering()  {
      return  (getAutoRenderIndex() != -1);
   }

   /**
      <P>Configuration related to the gap-start and end characters.</P>

      @see  #FeatherTemplate(String, GapCharConfig, int, Resettable, Appendable)
    **/
   public GapCharConfig getCharConfig()  {
      return  charConfig;
   }
   /**
      <P>Associates gap names and their fill texts.</P>

      @see  #FeatherTemplate(String, GapCharConfig, int, Resettable, Appendable)
    **/
   public GapMap getGapMap()  {
      return  gapMap;
   }
   /**
      <P>Fill a gap.</P>

      @param  name  May not be {@code null}, and must {@linkplain com.github.aliteralmind.templatefeather.GapMap#contains(String) exist} and, when {@code text} is {@code null}, must be {@linkplain com.github.aliteralmind.templatefeather.GapMap#isFilled(String) unfilled}.
      @param  text  If non-{@code null}, the gap is filled, and when active, {@linkplain #setAutoRendererX(Appendable) auto-rendering} proceeds. If {@code null}, the gap is {@linkplain #unfill(String) unfilled}.
      @exception  GapFilledException  If {@code text} is non-{@code null} and the gap is already filled.
      @exception  TemplateResettableException  If {@code text} is {@code null} and {@link #isResettable() isResettable}{@code ()} is {@code false}
    **/
   public FeatherTemplate fill(String name, Object text)  {
      if(isDebugOn()) { getDebugAptr().appent("FeatherTemplate.fill(\"" + name + "\", \"" + VPC_DBG.get(text) + "\")"); }
      getGapMap().fill(name, text);
      continueAutoRender();
      return  this;
   }
   /**
      <P>Unfill a gap.</P>

      <P>Equal to
      <BR> &nbsp; &nbsp; {@link #fill(String, Object) fill}{@code (name, null)}</P>
    **/
   public void unfill(String name)  {
      fill(name, null);
   }
   /**
      <P>Unfill all gaps. This {@linkplain #fill(String name, Object text) fills} all gaps with {@code null}.</P>

      @exception  TemplateResettableException  If {@link #isResettable() isResettable}{@code ()} is {@code false}.
    **/
   public void unfill()  {
      getGapMap().unfill();
   }
   /**
      <P>Get a duplicate of this <CODE>FeatherTemplate</CODE>.</P>

      @return {@link #getObjectCopy(Appendable)}{@code (null)}
    **/
   public FeatherTemplate getObjectCopy()  {
      return  getObjectCopy(null);
   }
   /**
      <P>Get a duplicate of this <CODE>FeatherTemplate</CODE>.</P>

      @return <CODE>({@link #FeatherTemplate(FeatherTemplate, Appendable) FeatherTemplate}(this, debugDest_ifNonNull))</CODE>
      @see  #getObjectCopy()
    **/
   public FeatherTemplate getObjectCopy(Appendable debugDest_ifNonNull)  {
      return  (new FeatherTemplate(this, debugDest_ifNonNull));
   }
   /**
      <P>Can all gaps be unfilled, so the template can be reused?.</P>

      @return  <CODE>{@link #getGapMap() getGapMap}().{@link com.github.aliteralmind.templatefeather.GapMap#isUnfillOk() isUnfillOk}()</CODE>
      @see  #unfill()
    **/
   public boolean isResettable()  {
      return  getGapMap().isUnfillOk();
   }
   /**
    	@return  <CODE>true</CODE> If {@code to_compareTo} is non-{@code null}, a {@code FeatherTemplate}, and all its fields {@linkplain #areFieldsEqual(FeatherTemplate) are equal}. This is implemented as suggested by Joshua Bloch in &quot;Effective Java&quot; (2nd ed, item 8, page 46).
    **/
   @Override
   public boolean equals(Object to_compareTo)  {
      //Check for object equality first, since it's faster than instanceof.
      if(this == to_compareTo)  {
         return  true;
      }
      if(!(to_compareTo instanceof FeatherTemplate))  {
         //to_compareTo is either null or not an FeatherTemplate.
         //java.lang.Object.object(o):
         // "For any non-null reference value x, x.equals(null) should return false."
         //See the bottom of this class for a counter-argument (which I'm not going with).
         return  false;
      }

      //Safe to cast
      FeatherTemplate o = (FeatherTemplate)to_compareTo;

      //Finish with field-by-field comparison.
      return  areFieldsEqual(o);
   }
   /**
      <P>Are all relevant fields equal?.</P>

      @param  to_compareTo  May not be {@code null}.
      @return  {@code true}  If all pieces are equal. Specifically:
      <BR> &nbsp; &nbsp; <CODE>to_compareTo.{@link #getPieceList() getPieceList}().equals(getPieceList())</CODE>
      @see  <CODE><A HREF="http://stackoverflow.com/questions/2889858/compare-two-lists">http://stackoverflow.com/questions/2889858/compare-two-lists</A></CODE>
    **/
   public boolean areFieldsEqual(FeatherTemplate to_compareTo)  {
      try  {
         return  to_compareTo.getPieceList().equals(getPieceList());
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(to_compareTo, "to_compareTo", null, rx);
      }
   }
   public String toString()  {
      return  getCharConfig() + ", " + getGapMap();
   }
}
