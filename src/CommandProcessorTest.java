import org.junit.Test;
import student.TestCase;
import student.TestableRandom;

/**
 * This class tests the CommandProcessor class.
 * Test each possible command on its bounds,
 * if applicable to ensure they work properly.
 * Also test passing improper command to ensure
 * all class functionalities work as intended.
 * 
 * @author <Harleen Kaur>
 * @version <version_no>
 */
public class CommandProcessorTest extends TestCase {

    /**
     * The setUp() method will be called automatically before
     * each test and reset whatever the test modified. For this
     * test class, only a new database object is needed, so
     * creat a database here for use in each test case.
     */
    private CommandProcessor cmdProc;

    /**
     * Sets up the test fixture.
     * This method is called before each test case.
     */
    public void setUp() {
        cmdProc = new CommandProcessor();
    }

// @Test
// public void testInsertValid() {
// cmdProc.processor("insert R1 11 11 5 5");
// String output = systemOut().getHistory();
// systemOut().clearHistory();
// String[] lines = output.split("\n");
// String lastLine = lines[lines.length - 1].trim();
//
// assertEquals("Rectangle inserted: (R1, (11, 11, 5, 5))", lastLine);
// }


    /**
     * Tests inserting a rectangle with an invalid name.
     */
    @Test
    public void testInsertInvalidName() {
        try {
            cmdProc.processor("insert 1R 10 10 5 5");
            fail("Should have thrown IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Invalid name format", e.getMessage());
        }
    }


    /**
     * Tests the main method of the CommandProcessor class.
     */
    public void testMain() {
        String[] emptyInput = { "" };
        SkipListProject.main(emptyInput);
        verifyResult("Invalid file");

        String[] nullInput = {};
        SkipListProject.main(nullInput);
        verifyResult("Invalid file. No filename in command line arguments");

        String[] validInput = { "P1test1.txt" };
        SkipListProject.main(validInput);
        String output = systemOut().getHistory();

        assertTrue(output.contains("SkipList dump:"));
        assertTrue(output.contains("r4") && output.contains("20") && output
            .contains("25") && output.contains("7") && output.contains("9"));

        resetOutput();
    }


    /**
     * Tests the search functionality of the CommandProcessor.
     */

    public void testSearch() {
        TestableRandom.setNextBooleans(true, false);
        processCmd("search rect1");
        String output = systemOut().getHistory();
        systemOut().clearHistory();

        processCmd("insert rect1 0 0 5 5");
        systemOut().clearHistory();

        processCmd("search rect1");
        output = systemOut().getHistory();
        systemOut().clearHistory();
        assertEquals("Rectangles found:\nrect1 0, 0, 5, 5\n", output.trim()
            + "\n");

        processCmd("search nonexistent");
        output = systemOut().getHistory();
        systemOut().clearHistory();
    }


    /**
     * Tests the insert functionality of the CommandProcessor.
     */
    @Test
    public void testInsertCases() {
        TestableRandom.setNextBooleans(true, false, true, false, true, false,
            true, false, true, false, true, false, true, false, true, false,
            true, false, true, false, true, false, true, false); // For
                                                                 // predictable
                                                                 // level
                                                                 // generation

        // Test 1: Valid rectangle insertion
        processCmd("insert r1 10 10 5 5");
        verifyResult("Rectangle inserted: (r1, 10, 10, 5, 5)");

        // Test 2: Invalid dimensions (negative width)
        processCmd("insert r2 15 15 -5 5");
        verifyResult("Rectangle rejected: (r2, 15, 15, -5, 5)");

        // Test 3: Invalid dimensions (negative height)
        processCmd("insert r3 15 15 5 -5");
        verifyResult("Rectangle rejected: (r3, 15, 15, 5, -5)");

        // Test 4: Invalid dimensions (zero width)
        processCmd("insert r4 15 15 0 5");
        verifyResult("Rectangle rejected: (r4, 15, 15, 0, 5)");

        // Test 6: Invalid coordinates (non-numeric)
        processCmd("insert r5 abc 15 5 5");
        verifyResult("Rectangle rejected:");

        // Test 7: Missing parameters
        processCmd("insert r6 15 15 5");
        verifyResult("Rectangle rejected:");

        // Test 8: Extra parameters
        processCmd("insert r7 15 15 5 5 10");
        verifyResult("Rectangle inserted: (r7, 15, 15, 5, 5)");

// // Test 9: Multiple insertions and dump verification
// processCmd("insert r8 20 20 10 10");
// processCmd("dump");
// String output = systemOut().getHistory();
// assertFalse(output.isEmpty());

        // Test 11: Max values
        processCmd("insert r9 1024 1024 1024 1024");
        verifyResult("Rectangle rejected: (r9, 1024, 1024, 1024, 1024)");

        // Test 12: Edge case - all zeros except valid width/height
        processCmd("insert r10 0 0 5 5");
        verifyResult("Rectangle inserted: (r10, 0, 0, 5, 5)");
    }


    /**
     * Tests the insert functionality of the CommandProcessor special names
     */
    @Test
    public void testSpecialNameCases() {
        // Test valid name formats
        processCmd("insert A_1 10 10 5 5");
        verifyResult("Rectangle inserted: (A_1, 10, 10, 5, 5)");

        processCmd("insert Z_9 10 10 5 5");
        verifyResult("Rectangle inserted: (Z_9, 10, 10, 5, 5)");

    }


    /**
     * Tests the insert functionality of the CommandProcessor boundary values
     */

    @Test
    public void testBoundaryValues() {
        // Test boundary values for coordinates and dimensions
        processCmd("insert r1 " + Integer.MAX_VALUE + " 10 5 5");
        processCmd("insert r2 10 " + Integer.MAX_VALUE + " 5 5");
        processCmd("insert r3 10 10 " + Integer.MAX_VALUE + " 5");
        processCmd("insert r4 10 10 5 " + Integer.MAX_VALUE);

        // Verify all these insertions in the dump
        processCmd("dump");
        String output = systemOut().getHistory();
        assertFalse(output.contains("Integer.MAX_VALUE"));
    }


    /**
     * Tests the remove functionality of the CommandProcessor.
     */
    public void testRemove() {
        TestableRandom.setNextBooleans(true, false, true, false);

        processCmd("insert rect1 15 15 5 5");
        resetOutput();
        //
        // processCmd("remove rect1");
        // verifyResult("Rectangle removed: (rect1, 15, 15, 5, 5)");

        // processCmd("remove rect1");
        // verifyResult("Rectangle not removed: rect1");

        processCmd("insert rect1 15 15 5 5");
        resetOutput();
        processCmd("remove 15 15 5 5");
        verifyResult("Rectangle removed: (rect1, 15, 15, 5, 5)");

        processCmd("remove 15 15 5 5");
        verifyResult("Rectangle removed: (rect1, 15, 15, 5, 5)");
    }


    /**
     * Tests the intersect functionality of the CommandProcessor.
     */
    public void testIntersect() {

        TestableRandom.setNextBooleans(false, false);
        processCmd("intersections");
        verifyResult("Intersection pairs:");

        processCmd("insert rect1 15 15 5 5");
        resetOutput();
        processCmd("intersections");
        verifyResult("Intersection pairs:");

        processCmd("insert rect2 15 15 5 5");
        resetOutput();
// processCmd("intersections");
// verifyResult(
// "Intersection pairs: \r\n" +
// "(rect1, 15, 15, 5, 5) | (rect2, 15, 15, 5, 5)");
    }


    private void processCmd(String cmd) {
        cmdProc.processor(cmd);
    }


    private void resetOutput() {
        systemOut().clearHistory();
    }


    private void verifyResult(String expected) {
        String output = systemOut().getHistory();
        systemOut().clearHistory();

        if (output == null || output.trim().isEmpty()) {
            if (expected == null || expected.trim().isEmpty()) {
                return;
            }
            assertEquals(expected, output);
            return;
        }

        String[] lines = output.split("\n");
        StringBuilder actual = new StringBuilder();

        for (String line : lines) {
            if (line != null && !line.contains("SkipNode@")) {
                if (line.contains("Node with depth")) {
                    line = line.replaceAll("\\d+", "X");
                }
                actual.append(line).append("\n");
            }
        }

        if (expected.contains("Node with depth")) {
            expected = expected.replaceAll("\\d+", "X");
        }

        assertEquals(expected, actual.toString().trim());

    }


    /**
     * Tests the regionsearch functionality of the CommandProcessor.
     * Specifically tests:
     * - Invalid width and height rejection
     * - Proper output format for rejected regions
     * - Proper error messages
     */
    public void testRegionSearch() {
        TestableRandom.setNextBooleans(false, false);

        // Insert some rectangles to search within
        processCmd("insert r1 10 10 5 5");
        resetOutput();

        // Test Case 1: Zero width
        processCmd("regionsearch 5 5 0 10");
        verifyResult("Rectangle rejected: (5, 5, 0, 10)");

        // Test Case 2: Zero height
        processCmd("regionsearch 5 5 10 0");
        verifyResult("Rectangle rejected: (5, 5, 10, 0)");

        // Test Case 3: Negative width
        processCmd("regionsearch 5 5 -5 10");
        verifyResult("Rectangle rejected: (5, 5, -5, 10)");

        // Test Case 4: Negative height
        processCmd("regionsearch 5 5 10 -5");
        verifyResult("Rectangle rejected: (5, 5, 10, -5)");

        // Test Case 5: Both negative dimensions
        processCmd("regionsearch 5 5 -5 -5");
        verifyResult("Rectangle rejected: (5, 5, -5, -5)");

        // Test Case 6: Both zero dimensions
        processCmd("regionsearch 5 5 0 0");
        verifyResult("Rectangle rejected: (5, 5, 0, 0)");
    }
}
