
import org.junit.Test;
import student.TestCase;

/**
 * This class tests the methods of Rectangle class,
 * ensuring that they work as they should.
 * 
 * @author <Harleen Kaur>
 * @version <version_no>
 */
public class RectangleTest extends TestCase {

    /**
     * Initializes a rectangle object to be used for the tests.
     */
    private Rectangle rect = new Rectangle(1, 0, 2, 4);

    /**
     * initial setup method
     */
    public void setUp() {
    }


    /**
     * getter test case for X coordinate
     */
    @Test
    public void testGetXCoordinate() {
        assertEquals(1, rect.getxCoordinate());
    }


    /**
     * getter test case for Y coordinate
     */
    @Test
    public void testGetYCoordinate() {
        assertEquals(0, rect.getyCoordinate());
    }


    /**
     * getter test case for width
     */
    @Test
    public void testGetWidth() {
        assertEquals(2, rect.getWidth());
    }


    /**
     * getter test case for height
     */
    @Test
    public void testGetHeight() {
        assertEquals(4, rect.getHeight());
    }


    /**
     * test case for intersection method
     */
    @Test
    public void testIntersect() {
        Rectangle test = new Rectangle(2, 0, 2, 4);
        assertTrue(rect.intersect(test));
        assertFalse(rect.intersect(new Rectangle(4, 5, 2, 3)));
    }


    /**
     * test case for equals
     */
    @Test
    public void testEquals() {
        Rectangle baseRect = new Rectangle(100, 100, 50, 50);
        Rectangle sameRect = new Rectangle(100, 100, 50, 50);
        assertTrue("Identical rectangles should be equal", baseRect.equals(
            sameRect));
        assertTrue("Equals should be symmetric", sameRect.equals(baseRect));
        Rectangle differentX = new Rectangle(101, 100, 50, 50);
        assertFalse("Different x coordinate should not be equal", baseRect
            .equals(differentX));
        Rectangle differentY = new Rectangle(100, 101, 50, 50);
        assertFalse("Different y coordinate should not be equal", baseRect
            .equals(differentY));
        Rectangle differentWidth = new Rectangle(100, 100, 51, 50);
        assertFalse("Different width should not be equal", baseRect.equals(
            differentWidth));
        Rectangle differentHeight = new Rectangle(100, 100, 50, 51);
        assertFalse("Different height should not be equal", baseRect.equals(
            differentHeight));
        assertTrue("Rectangle should equal itself", baseRect.equals(baseRect));

    }


    /**
     * test case for to string
     */
    @Test
    public void testToString() {
        String actual = "1, 0, 2, 4";
        assertEquals(actual, rect.toString());

    }


    /**
     * test case for is invalid method
     */
    @Test
    public void testIsInvalid() {
        Rectangle test1 = new Rectangle(1025, 0, 2, 4);
        Rectangle test2 = new Rectangle(0, 0, 0, 0);
        Rectangle test3 = new Rectangle(0, 1022, 0, 10);
        assertTrue(test1.isInvalid());
        assertTrue(test2.isInvalid());
        assertTrue(test3.isInvalid());
        assertFalse(rect.isInvalid());

        // Width and Height tests
        Rectangle zeroWidth = new Rectangle(10, 10, 0, 5);
        Rectangle negativeWidth = new Rectangle(10, 10, -1, 5);
        Rectangle zeroHeight = new Rectangle(10, 10, 5, 0);
        Rectangle negativeHeight = new Rectangle(10, 10, 5, -1);
        assertTrue(zeroWidth.isInvalid());
        assertTrue(negativeWidth.isInvalid());
        assertTrue(zeroHeight.isInvalid());
        assertTrue(negativeHeight.isInvalid());

        // X coordinate tests
        Rectangle negativeX = new Rectangle(-1, 100, 50, 50);
        Rectangle xTooLarge = new Rectangle(1025, 100, 50, 50);
        Rectangle xPlusWidthTooLarge = new Rectangle(1000, 100, 50, 50);
        assertTrue("Negative x should be invalid", negativeX.isInvalid());
        assertTrue("X > 1024 should be invalid", xTooLarge.isInvalid());
        assertTrue("X + width > 1024 should be invalid", xPlusWidthTooLarge
            .isInvalid());

        // Y coordinate tests
        Rectangle negativeY = new Rectangle(100, -1, 50, 50);
        Rectangle yTooLarge = new Rectangle(100, 1025, 50, 50);
        Rectangle yPlusHeightTooLarge = new Rectangle(100, 1000, 50, 50);
        assertTrue("Negative y should be invalid", negativeY.isInvalid());
        assertTrue("Y > 1024 should be invalid", yTooLarge.isInvalid());
        assertTrue("Y + height > 1024 should be invalid", yPlusHeightTooLarge
            .isInvalid());

        // Boundary tests
        Rectangle atOrigin = new Rectangle(0, 0, 50, 50);
        Rectangle atMaxBoundary = new Rectangle(1000, 1000, 24, 24);
        assertFalse("Rectangle at origin should be valid", atOrigin
            .isInvalid());
        assertFalse("Rectangle at max boundary should be valid", atMaxBoundary
            .isInvalid());

        // Additional edge cases
        // Exact boundary tests
        Rectangle exactMaxX = new Rectangle(1024, 0, 0, 1);
        Rectangle exactMaxY = new Rectangle(0, 1024, 1, 0);
        assertTrue("X at 1024 should be invalid", exactMaxX.isInvalid());
        assertTrue("Y at 1024 should be invalid", exactMaxY.isInvalid());

        // Maximum boundary tests
        Rectangle maxBoundaryX = new Rectangle(1023, 0, 1, 1);
        Rectangle maxBoundaryY = new Rectangle(0, 1023, 1, 1);
        assertFalse("X at 1023 with width 1 should be valid", maxBoundaryX
            .isInvalid());
        assertFalse("Y at 1023 with height 1 should be valid", maxBoundaryY
            .isInvalid());

        // Corner cases
        Rectangle maxValues = new Rectangle(1023, 1023, 1, 1);
        assertFalse("Maximum valid values should be valid", maxValues
            .isInvalid());

        // Extreme width/height tests
        Rectangle maxWidth = new Rectangle(0, 0, 1024, 1);
        Rectangle maxHeight = new Rectangle(0, 0, 1, 1024);
        assertFalse("Width of 1024 should be invalid when x is 0", maxWidth
            .isInvalid());
        assertFalse("Height of 1024 should be invalid when y is 0", maxHeight
            .isInvalid());

        // Combined boundary tests
        Rectangle nearBoundary = new Rectangle(1020, 1020, 4, 4);
        Rectangle atBoundary = new Rectangle(1020, 1020, 5, 5);
        assertFalse("Near boundary should be valid", nearBoundary.isInvalid());
        assertTrue("At boundary should be invalid", atBoundary.isInvalid());

        // Overflow tests
        Rectangle overflowX = new Rectangle(1000, 0, 1026, 1);
        Rectangle overflowY = new Rectangle(0, 1000, 1, 1026);
        assertTrue("Integer overflow in width should be invalid", overflowX
            .isInvalid());
        assertTrue("Integer overflow in height should be invalid", overflowY
            .isInvalid());

    }


    /**
     * test case for intersection edge cases
     */

    @Test
    public void testIntersectionCases() {
        Rectangle baseRect = new Rectangle(10, 10, 5, 5);

        Rectangle r1 = new Rectangle(11, 11, 3, 3);
        assertTrue(baseRect.intersect(r1));
        assertTrue(r1.intersect(baseRect));

        Rectangle r2 = new Rectangle(16, 10, 5, 5);
        assertFalse(baseRect.intersect(r2));
        assertFalse(r2.intersect(baseRect));

        Rectangle r3 = new Rectangle(4, 10, 5, 5);
        assertFalse(baseRect.intersect(r3));
        assertFalse(r3.intersect(baseRect));

        Rectangle r4 = new Rectangle(10, 4, 5, 5);
        assertFalse(baseRect.intersect(r4));
        assertFalse(r4.intersect(baseRect));

        Rectangle r5 = new Rectangle(10, 16, 5, 5);
        assertFalse(baseRect.intersect(r5));
        assertFalse(r5.intersect(baseRect));

        Rectangle r6 = new Rectangle(14, 10, 5, 5);
        assertTrue(baseRect.intersect(r6));
        assertTrue(r6.intersect(baseRect));

        Rectangle r7 = new Rectangle(8, 10, 5, 5);
        assertTrue(baseRect.intersect(r7));
        assertTrue(r7.intersect(baseRect));

        Rectangle r8 = new Rectangle(10, 8, 5, 5);
        assertTrue(baseRect.intersect(r8));
        assertTrue(r8.intersect(baseRect));

        Rectangle r9 = new Rectangle(10, 14, 5, 5);
        assertTrue(baseRect.intersect(r9));
        assertTrue(r9.intersect(baseRect));

        Rectangle r10 = new Rectangle(15, 15, 5, 5);
        assertFalse(baseRect.intersect(r10));
        assertFalse(r10.intersect(baseRect));

        Rectangle r11 = new Rectangle(5, 5, 15, 15);
        assertTrue(baseRect.intersect(r11));
        assertTrue(r11.intersect(baseRect));

        Rectangle r12 = new Rectangle(11, 11, 5, 5);
        assertTrue(baseRect.intersect(r12));
        assertTrue(r12.intersect(baseRect));

        Rectangle r13 = new Rectangle(16, 16, 5, 5);
        assertFalse(baseRect.intersect(r13));
        assertFalse(r13.intersect(baseRect));

        Rectangle r14 = new Rectangle(15, 10, 5, 5);
        assertFalse(baseRect.intersect(r14));
        assertFalse(r14.intersect(baseRect));
        Rectangle r15 = new Rectangle(10, 15, 5, 5);
        assertFalse(baseRect.intersect(r15));
        assertFalse(r15.intersect(baseRect));
    }


    /**
     * test case for special cases intersections
     */

    @Test
    public void testSpecialCases() {
        Rectangle r20 = new Rectangle(10, 10, 5, 5);
        assertTrue(r20.intersect(r20));

        Rectangle r21a = new Rectangle(10, 10, 5, 5);
        Rectangle r21b = new Rectangle(10, 10, 5, 5);
        assertTrue(r21a.intersect(r21b));
        assertTrue(r21b.intersect(r21a));
    }

}
