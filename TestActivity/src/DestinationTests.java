import com.example.team11xtremexpensetracker.Destination;

import junit.framework.TestCase;


public class DestinationTests extends TestCase {
	public void testDestName(){
		Destination dest1 = new Destination("Maui");
		Destination dest2 = new Destination("Florida");
		
		assertTrue("Dest Name", dest1.getDest().equals("Maui"));
		assertFalse("Dest Name", dest1.getDest().equals("Mall"));
		
		assertTrue("Dest Name", dest2.getDest().equals("Florida"));
		
		
	}
	
	public void testReason(){
		Destination dest1 = new Destination("Maui");
		Destination dest2 = new Destination("Florida");
		
		dest1.setReason("Work");
		
		assertTrue("Dest Reason", dest1.getReason().equals("Work"));
		assertTrue("Dest Reason Default", dest2.getReason().equals(""));
	}
}
