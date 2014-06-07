package  com.github.aliteralmind.templatefeather.test;
   import  java.util.List;
   import  com.github.aliteralmind.templatefeather.GapFilledException;
   import  com.github.aliteralmind.templatefeather.GapUnfilledException;
   import  com.github.aliteralmind.templatefeather.TemplatePiece;
   import  org.junit.Test;
   import  static org.junit.Assert.*;
   import  com.github.aliteralmind.templatefeather.FeatherTemplate;
   import  com.github.aliteralmind.templatefeather.GapPiece;

/**
   <P>One-class unit: {@code java com.github.aliteralmind.templatefeather.test.TemplatePiece_Unit}</P>
 **/
public class TemplatePiece_Unit  {
   public static final void main(String[] ignored)  {
      TemplatePiece_Unit unit = new TemplatePiece_Unit();
      unit.test();
   }
   @Test
   public void test()  {
      String orig = "Hello %name%! I like you %percent_num%__PCT__.";
      FeatherTemplate tmpl = new FeatherTemplate(orig, null);

      assertEquals("Hello ", tmpl.getPieceList().get(0).getOriginal());
      assertEquals("%name%", tmpl.getPieceList().get(1).getOriginal());
      assertEquals("! I like you ", tmpl.getPieceList().get(2).getOriginal());
      assertEquals("%percent_num%", tmpl.getPieceList().get(3).getOriginal());
      assertEquals("__PCT__.", tmpl.getPieceList().get(4).getOriginal());

      assertEquals("Hello ", tmpl.getPieceList().get(0).getRenderedOrOriginalIfNot());
      assertEquals("%name%", tmpl.getPieceList().get(1).getRenderedOrOriginalIfNot());
      assertEquals("! I like you ", tmpl.getPieceList().get(2).getRenderedOrOriginalIfNot());
      assertEquals("%percent_num%", tmpl.getPieceList().get(3).getRenderedOrOriginalIfNot());
      assertEquals("%.", tmpl.getPieceList().get(4).getRenderedOrOriginalIfNot());

      assertEquals("Hello ", tmpl.getPieceList().get(0).getRendered());
      try  {
         tmpl.getPieceList().get(1).getRendered();
         assertFalse(true);
      }  catch(GapUnfilledException x)  {
         assertTrue(true);
      }
      assertEquals("! I like you ", tmpl.getPieceList().get(2).getRendered());
      try  {
         //percent_num
         tmpl.getPieceList().get(3).getRendered();
         assertFalse(true);
      }  catch(GapUnfilledException x)  {
         assertTrue(true);
      }
      assertEquals("%.", tmpl.getPieceList().get(4).getRendered());

      tmpl.fill("name", "Kermit");

      assertEquals("Hello ", tmpl.getPieceList().get(0).getOriginal());
      assertEquals("%name%", tmpl.getPieceList().get(1).getOriginal());
      assertEquals("! I like you ", tmpl.getPieceList().get(2).getOriginal());
      assertEquals("%percent_num%", tmpl.getPieceList().get(3).getOriginal());
      assertEquals("__PCT__.", tmpl.getPieceList().get(4).getOriginal());

      assertEquals("Hello ", tmpl.getPieceList().get(0).getRenderedOrOriginalIfNot());
      assertEquals("Kermit", tmpl.getPieceList().get(1).getRenderedOrOriginalIfNot());
      assertEquals("! I like you ", tmpl.getPieceList().get(2).getRenderedOrOriginalIfNot());
      assertEquals("%percent_num%", tmpl.getPieceList().get(3).getRenderedOrOriginalIfNot());
      assertEquals("%.", tmpl.getPieceList().get(4).getRenderedOrOriginalIfNot());

      assertEquals("Hello ", tmpl.getPieceList().get(0).getRendered());
      assertEquals("Kermit", tmpl.getPieceList().get(1).getRendered());
      assertEquals("! I like you ", tmpl.getPieceList().get(2).getRendered());
      try  {
         //percent_num
         tmpl.getPieceList().get(3).getRendered();
         assertFalse(true);
      }  catch(GapUnfilledException x)  {
         assertTrue(true);
      }
      assertEquals("%.", tmpl.getPieceList().get(4).getRendered());

      tmpl.fill("percent_num", "110");

      assertEquals("Hello ", tmpl.getPieceList().get(0).getOriginal());
      assertEquals("%name%", tmpl.getPieceList().get(1).getOriginal());
      assertEquals("! I like you ", tmpl.getPieceList().get(2).getOriginal());
      assertEquals("%percent_num%", tmpl.getPieceList().get(3).getOriginal());
      assertEquals("__PCT__.", tmpl.getPieceList().get(4).getOriginal());

      assertEquals("Hello ", tmpl.getPieceList().get(0).getRenderedOrOriginalIfNot());
      assertEquals("Kermit", tmpl.getPieceList().get(1).getRenderedOrOriginalIfNot());
      assertEquals("! I like you ", tmpl.getPieceList().get(2).getRenderedOrOriginalIfNot());
      assertEquals("110", tmpl.getPieceList().get(3).getRenderedOrOriginalIfNot());
      assertEquals("%.", tmpl.getPieceList().get(4).getRenderedOrOriginalIfNot());

      assertEquals("Hello ", tmpl.getPieceList().get(0).getRendered());
      assertEquals("Kermit", tmpl.getPieceList().get(1).getRendered());
      assertEquals("! I like you ", tmpl.getPieceList().get(2).getRendered());
      assertEquals("110", tmpl.getPieceList().get(3).getRendered());
      assertEquals("%.", tmpl.getPieceList().get(4).getRendered());
   }
}
