package  com.github.aliteralmind.templatefeather.test;
	import  com.github.aliteralmind.templatefeather.GapMap;
	import  com.github.aliteralmind.templatefeather.GapUnfilledException;
	import  java.util.NoSuchElementException;
	import  org.junit.Test;
	import  static org.junit.Assert.*;

/**
	<p>One-class unit: {@code java com.github.aliteralmind.templatefeather.test.GapMap_Unit}</p>
 **/
public class GapMap_Unit  {
	public static final void main(String[] ignored)  {
		GapMap_Unit unit = new GapMap_Unit();
		unit.test();
	}
	@Test
	public void test()  {
		GapMap map = new GapMap(null);
		assertFalse(map.isUnfillOk());
		assertEquals(0, map.size());
		map.add("name");
		map.add("percent_num");
		map.lock();

		assertEquals(0, map.getFilledCount());
		assertEquals(2, map.getUnfilledCount());
		assertEquals(2, map.newNameSet().size());
		assertEquals(0, map.filledNameSet().size());
		assertEquals(2, map.unfilledNameSet().size());
		assertFalse(map.isFilled());

		assertFalse(map.contains("x"));
		try  {
			map.isFilled("x");
			assertFalse(true);
		}  catch(NoSuchElementException x)  {
			assertTrue(true);
		}
		try  {
			map.getFillText("name");
			assertFalse(true);
		}  catch(GapUnfilledException x)  {
			assertTrue(true);
		}
		assertTrue(map.contains("name"));
	}
}
