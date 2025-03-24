
import student.TestCase;

/**
 * This class tests the KVPair class so that the member methods work properly
 * and that the expected behavior occurs.
 * 
 * @author Harleen Kaur
 * 
 * @version 2024.1
 */
public class KVPairTest extends TestCase {
    /**
     * private KVPair class object
     */
    private KVPair<String, Integer> pair;

    /**
     * Sets up the test fixture.
     * This method is called before each test case.
     */

    public void setUp() {
        pair = new KVPair<String, Integer>("ABCD", 1011112);
    }


    /**
     * getter test case for key
     */
    public void testGetKey() {
        assertEquals(pair.getKey(), "ABCD");
    }


    /**
     * getter test case for value
     */
    public void testGetValue() {
        assertEquals(pair.getValue().intValue(), 1011112);
    }


    /**
     * test case for toString method
     */
    public void testToString() {
        assertEquals(pair.toString(), "ABCD, 1011112");
    }

}
