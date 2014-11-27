package  com.github.aliteralmind.templatefeather.test;
	import  com.github.aliteralmind.templatefeather.GapCharConfig;
	import  org.junit.Test;
	import  static org.junit.Assert.*;

/**
	<P>One-class unit: {@code java com.github.aliteralmind.templatefeather.test.GapCharConfig_Unit}</P>
 **/
public class GapCharConfig_Unit  {
	public static final void main(String[] ignored)  {
		GapCharConfig_Unit unit = new GapCharConfig_Unit();
		unit.test();
	}
	@Test
	public void test()  {
		GapCharConfig config = new GapCharConfig();

		assertEquals('%', config.getStart());
		assertEquals('%', config.getEnd());
		assertEquals("__PCT__", config.getLiteralStart());
		assertEquals("__PCT__", config.getLiteralEnd());

	}
}
