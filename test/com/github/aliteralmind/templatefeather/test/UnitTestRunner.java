package  com.github.aliteralmind.templatefeather.test;
   import  org.junit.runner.JUnitCore;
   import  org.junit.runner.Result;
   import  org.junit.runner.notification.Failure;
/**
   <p>Run all units: {@code java com.github.xbn.test.UnitTestRunner}</p>
 **/
public class UnitTestRunner  {
    public static void main(String[] as_cmdLineArgs) {

      Class[] acALL = new Class[] {
/*
 */
         com.github.aliteralmind.templatefeather.test.GapCharConfig_Unit.class,
         com.github.aliteralmind.templatefeather.test.GapMap_Unit.class,
         com.github.aliteralmind.templatefeather.test.TemplatePiece_Unit.class,
         com.github.aliteralmind.templatefeather.test.FeatherTemplate_Unit.class,
         com.github.aliteralmind.templatefeather.test.ExampleCodeOutputsContain_Unit.class
/*
 */
      };

      Result r = JUnitCore.runClasses(                 //Comma-separated list of classes
         acALL
      );

      System.out.println("Test results:");
      System.out.println("  Successful:  " + (r.getRunCount() - r.getFailureCount()));
      System.out.println("  Failures:    " + r.getFailureCount());
      System.out.println("               ----");
      System.out.println("  Total:       " + r.getRunCount() + "\n");

      if(r.getFailureCount() > 0)  {
         System.out.println("Failure descriptions:");
         for(Failure f : r.getFailures())  {
            System.out.println("  " + f.toString());
         }
      }
    }
}

