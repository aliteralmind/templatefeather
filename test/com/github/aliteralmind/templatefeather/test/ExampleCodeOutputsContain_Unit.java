package  com.github.aliteralmind.templatefeather.test;
   import  com.github.xbn.testdev.DisplayOutputToConsole;
   import  com.github.xbn.testdev.VerifyApplicationOutput;
   import  org.junit.Test;
/**
   <p>Example-code units: {@code com.github.aliteralmind.templatefeather.test.ExampleCodeOutputsContain_Unit}</p>
 **/
public class ExampleCodeOutputsContain_Unit  {
   public static final void main(String[] ignored)  {
      ExampleCodeOutputsContain_Unit test = new ExampleCodeOutputsContain_Unit();
   	test.FeatherAutoRenderDemo();
   	test.HelloFeather();
   }
   @Test
   public void FeatherAutoRenderDemo()  {
      VerifyApplicationOutput.assertWithNoParameters(DisplayOutputToConsole.NO, null,
         com.github.aliteralmind.templatefeather.examples.FeatherAutoRenderDemo.class,
         "Hello ", "Ralph. I like you, Ralph, ", "45% guaranteed.");
   }
   @Test
   public void HelloFeather()  {
      VerifyApplicationOutput.assertWithNoParameters(DisplayOutputToConsole.NO, null,
         com.github.aliteralmind.templatefeather.examples.HelloFeather.class,
         "Hello Ralph. I like you, Ralph, 45% guaranteed.");
   }
}
