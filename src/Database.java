import java.util.ArrayList;
import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * This class is responsible for interfacing between the command processor and
 * the SkipList. The responsibility of this class is to further interpret
 * variations of commands and do some error checking of those commands. This
 * class further interpreting the command means that the two types of remove
 * will be overloaded methods for if we are removing by name or by coordinates.
 * Many of these methods will simply call the appropriate version of the
 * SkipList method after some preparation.
 * 
 * @author Harleen Kaur
 * 
 * @version 2024-01-22
 */
public class Database {

    // this is the SkipList object that we are using
    // a string for the name of the rectangle and then
    // a rectangle object, these are stored in a KVPair,
    // see the KVPair class for more information
    private SkipList<String, Rectangle> list;

    // This is an Iterator object over the SkipList to loop through it from
    // outside the class.
    // You will need to define an extra Iterator for the intersections method.
    private Iterator<KVPair<String, Rectangle>> itr1;

    /**
     * The constructor for this class initializes a SkipList object with String
     * and Rectangle a its parameters.
     */
    public Database() {
        list = new SkipList<String, Rectangle>();
    }


    /**
     * Inserts the KVPair in the SkipList if the rectangle has valid coordinates
     * and dimensions, that is that the coordinates are non-negative and that
     * the rectangle object has some area (not 0, 0, 0, 0). This insert will
     * add the KVPair specified into the sorted SkipList appropriately
     * 
     * @param pair
     *            the KVPair to be inserted
     */
    public void insert(KVPair<String, Rectangle> pair) {

        if (pair.getValue().isInvalid()) {
            System.out.println("Rectangle rejected: (" + pair.getKey() + ", "
                + pair.getValue() + ")");
        }
        else {
            list.insert(pair);
            System.out.println("Rectangle inserted: (" + pair.getKey() + ", "
                + pair.getValue() + ")");
        }

    }


    /**
     * Removes a rectangle with the name "name" if available. If not an error
     * message is printed to the console.
     * 
     * @param name
     *            the name of the rectangle to be removed
     */
    public void remove(String name) {
        KVPair<String, Rectangle> pair = null;
        Iterator<KVPair<String, Rectangle>> iter = list.iterator();

        while (iter.hasNext()) {
            KVPair<String, Rectangle> current = iter.next();
            if (current.getKey().equals(name)) {
                pair = current;
                break;
            }
        }

        if (pair != null) {
            list.remove(name);
            System.out.println("Rectangle removed: (" + name + ", " + pair
                .getValue().toString() + ")");
        }
        else {
            System.out.println("Rectangle not removed: " + name);
        }
    }


    /**
     * Removes a rectangle with the specified coordinates if available. If not
     * an error message is printed to the console.
     * 
     * @param x
     *            x-coordinate of the rectangle to be removed
     * @param y
     *            x-coordinate of the rectangle to be removed
     * @param w
     *            width of the rectangle to be removed
     * @param h
     *            height of the rectangle to be removed
     */
    public void remove(int x, int y, int w, int h) {
        Rectangle rect = new Rectangle(x, y, w, h);
        KVPair<String, Rectangle> pair = list.removeByValue(rect);
        if (pair == null) {
            System.out.println("Rectangle rejected: " + rect.toString());
        }
        else {
            System.out.println("Rectangle removed: (" + pair.getKey() + ", "
                + pair.getValue() + ")");
        }

    }


    /**
     * Displays all the rectangles inside the specified region. The rectangle
     * must have some area inside the area that is created by the region,
     * meaning, Rectangles that only touch a side or corner of the region
     * specified will not be said to be in the region.
     * 
     * @param x
     *            x-Coordinate of the region
     * @param y
     *            y-Coordinate of the region
     * @param w
     *            width of the region
     * @param h
     *            height of the region
     */
    public void regionsearch(int x, int y, int w, int h) {
        if (w <= 0 || h <= 0) {
            System.out.println("Rectangle rejected: (" + x + ", " + y + ", " + w
                + ", " + h + ")");
            return;
        }

        System.out.println("Rectangles intersecting region (" + x + ", " + y
            + ", " + w + ", " + h + "):");

        int count = 0;
        Iterator<KVPair<String, Rectangle>> countIter = list.iterator();
        while (countIter.hasNext()) {
            countIter.next();
            count++;
        }

        @SuppressWarnings("unchecked")
        KVPair<String, Rectangle>[] rect = (KVPair<String, Rectangle>[])Array
            .newInstance(KVPair.class, count);

        Iterator<KVPair<String, Rectangle>> iter = list.iterator();
        int index = 0;
        while (iter.hasNext()) {
            rect[index] = iter.next();
            index++;
        }

        Rectangle region = new Rectangle(x, y, w, h);

        for (int i = 0; i < count; i++) {
            Rectangle rect1 = rect[i].getValue();
            String name1 = rect[i].getKey();

            if (rect1.intersect(region)) {
                System.out.println(name1 + " " + rect1.toString());
            }
        }

    }


    /**
     * Prints out all the rectangles that intersect each other. Note that
     * it is better not to implement an intersections method in the SkipList
     * class
     * as the SkipList needs to be agnostic about the fact that it is storing
     * Rectangles.
     */
    public void intersections() {
        System.out.println("Intersection pairs:");

        int count = 0;
        Iterator<KVPair<String, Rectangle>> countIter = list.iterator();
        while (countIter.hasNext()) {
            countIter.next();
            count++;
        }

        @SuppressWarnings("unchecked")
        KVPair<String, Rectangle>[] rect = (KVPair<String, Rectangle>[])Array
            .newInstance(KVPair.class, count);

        Iterator<KVPair<String, Rectangle>> iter = list.iterator();
        int index = 0;
        while (iter.hasNext()) {
            rect[index] = iter.next();
            index++;
        }

        for (int i = 0; i < rect.length; i++) {
            Rectangle rect1 = rect[i].getValue();
            String name1 = rect[i].getKey();

            for (int j = i + 1; j < rect.length; j++) {
                Rectangle rect2 = rect[j].getValue();
                String name2 = rect[j].getKey();

                if (rect1.intersect(rect2)) {
                    if (name1.compareTo(name2) <= 0) {
                        System.out.println(String.format(
                            "(%s, %d, %d, %d, %d) | (%s, %d, %d, %d, %d)",
                            name1, rect1.getxCoordinate(), rect1
                                .getyCoordinate(), rect1.getWidth(), rect1
                                    .getHeight(), name2, rect2.getxCoordinate(),
                            rect2.getyCoordinate(), rect2.getWidth(), rect2
                                .getHeight()));
                    }
                    else {
                        System.out.println(String.format(
                            "(%s, %d, %d, %d, %d) | (%s, %d, %d, %d, %d)",
                            name2, rect2.getxCoordinate(), rect2
                                .getyCoordinate(), rect2.getWidth(), rect2
                                    .getHeight(), name1, rect1.getxCoordinate(),
                            rect1.getyCoordinate(), rect1.getWidth(), rect1
                                .getHeight()));
                    }
                }
            }
        }
    }


    /**
     * Prints out all the rectangles with the specified name in the SkipList.
     * This method will delegate the searching to the SkipList class completely.
     * 
     * @param name
     *            name of the Rectangle to be searched for
     */
    public void search(String name) {
        ArrayList<KVPair<String, Rectangle>> results = list.search(name);
        if (results.isEmpty()) {
            System.out.println("Rectangle not found: (" + name + ")");
        }
        else {
            System.out.println("Rectangles found:");
            for (KVPair<String, Rectangle> pair : results) {
                System.out.println(pair.getKey() + " " + pair.getValue()
                    .toString());
            }
        }
        /*
         * printing in format
         * Rectangles found:
         * (r2, 15, 15, 5, 5)
         * (r2, 15, 15, 5, 5)
         */
    }


    /**
     * Prints out a dump of the SkipList which includes information about the
     * size of the SkipList and shows all of the contents of the SkipList. This
     * will all be delegated to the SkipList.
     */
    public void dump() {
        list.dump();
    }

}
