package  com.github.aliteralmind.templatefeather.test;
   import  com.github.aliteralmind.templatefeather.GapUnfilledException;
   import  com.github.aliteralmind.templatefeather.TemplateResettableException;
   import  java.util.List;
   import  com.github.aliteralmind.templatefeather.TemplatePiece;
   import  org.junit.Test;
   import  static org.junit.Assert.*;
   import  com.github.aliteralmind.templatefeather.FeatherTemplate;

/**
   <P>One-class unit: {@code java com.github.aliteralmind.templatefeather.test.FeatherTemplate_Unit}</P>
 **/
public class FeatherTemplate_Unit  {
   public static final void main(String[] ignored)  {
      FeatherTemplate_Unit unit = new FeatherTemplate_Unit();
      unit.test();
   }
   @Test
   public void test()  {
      String orig = "Hello %name%! I like you %percent_num%__PCT__.";
      FeatherTemplate tmpl = new FeatherTemplate(orig, null);

      List<TemplatePiece> pieceList = tmpl.getPieceList();

      assertEquals(5, pieceList.size());

      assertEquals("Hello ", tmpl.getBetweenPiece(0).getOriginal());
      assertEquals("%name%", tmpl.getGapPiece(1).getOriginal());
      assertEquals("! I like you ", tmpl.getBetweenPiece(2).getOriginal());
      assertEquals("%percent_num%", tmpl.getGapPiece(3).getOriginal());
      assertEquals("__PCT__.", tmpl.getBetweenPiece(4).getOriginal());

      try  {
         tmpl.getBetweenPiece(1);
         assertFalse(true);
      }  catch(ClassCastException x)  {
         assertTrue(true);
      }

      assertEquals(orig, tmpl.getOriginal());
      assertEquals("Hello %name%! I like you %percent_num%%.", tmpl.getPartiallyFilled());
      try  {
         tmpl.getFilled();
         assertFalse(true);
      }  catch(GapUnfilledException x)  {
         assertTrue(true);
      }

      try  {
         tmpl.fill("name", null);
         assertFalse(true);
      }  catch(TemplateResettableException x)  {
         assertTrue(true);
      }

      tmpl.fill("name", "Kermit");

      try  {
         tmpl.fill("name", null);
         assertFalse(true);
      }  catch(TemplateResettableException x)  {
         assertTrue(true);
      }

      assertEquals(orig, tmpl.getOriginal());
      assertEquals("Hello Kermit! I like you %percent_num%%.", tmpl.getPartiallyFilled());
      try  {
         tmpl.getFilled();
         assertFalse(true);
      }  catch(GapUnfilledException x)  {
         assertTrue(true);
      }

      tmpl.fill("percent_num", 110);

      assertEquals(orig, tmpl.getOriginal());
      assertEquals("Hello Kermit! I like you 110%.", tmpl.getPartiallyFilled());
      assertEquals("Hello Kermit! I like you 110%.", tmpl.getFilled());

      FeatherTemplate tmpl2 = new FeatherTemplate(tmpl, null);

      assertFalse(tmpl.equals(tmpl2));

      tmpl2.fill("name", "Kermit");
      tmpl2.fill("percent_num", 110);
   }
}
