package api;

import java.io.*;
import java.util.ArrayList;

/**
 * A class for holding sequences of coordinates including methods for getting data about these coordinates.
 * <p>
 * Imported from the original Java Autonomous GUI repository
 * @see <a href="https://github.com/BxSciborgs/JavaAutonomousGUI">Original Repository</a>
 *
 * @author Alejandro Ramos
 */
public class Path implements Serializable {
    private static final long serialVersionUID = 1;

    private ArrayList<Integer> x = new ArrayList<>();
    private ArrayList<Integer> y = new ArrayList<>();

    /**
     * Creates a new, empty Path.
     */
    public Path() {
    }

    /**
     * Sets this Path to the given Path.
     *
     * <p>
     * If the given Path is <code>null</code>, this constructor creates an empty Path.
     *
     * @param path the Path to set this Path to
     */
    public Path(Path path) {
        set(path);
    }

    /**
     * Creates a new Path with the given coordinate.
     *
     * @param x the first x coordinate
     * @param y the first y coordinate
     */
    public Path(int x, int y) {
        add(x, y);
    }

    /**
     * Creates a new Path with the given coordinate array.
     *
     * @param xy a coordinate array in which xy[0] has the x coordinates and xy[1] has the y coordinates
     */
    public Path(int[][] xy) {
        add(xy);
    }

    /**
     * Creates a new Path from a Path file.
     *
     * @param file Path file
     * @throws IOException if the Path could not be loaded from the file
     */
    public Path(File file) throws IOException {
        if (!this.load(file)) {
            throw new IOException();
        }
    }

    /**
     * Returns a {@link String} representation of this Path in an easily readable way.
     * The String is split into two lines to easily compare the x and y values of each coordinate.
     *
     * @return String representation of this Path
     */
    @Override
    public String toString() {
        StringBuilder xString = new StringBuilder("Path{x=[");
        StringBuilder yString = new StringBuilder("y=[");
        for (int i = 0; i < this.size(); i++) {
            xString.append(x.get(i));
            yString.append(y.get(i));

            // don't need commas and stuff if this is the last coordinate
            if (i < this.size() - 1) {
                xString.append(", ");
                yString.append(", ");

                int xLength = x.get(i).toString().length();
                int yLength = y.get(i).toString().length();

                // align the string so that each new coordinate starts at the same point for x and y
                if (xLength > yLength) {
                    for (int j = 0; j < xLength - yLength; j++) {
                        yString.append(" ");
                    }
                }
                if (yLength > xLength) {
                    for (int j = 0; j < yLength - xLength; j++) {
                        xString.append(" ");
                    }
                }
            }
        }
        return xString + "],\n" + "     " + yString + "]}";
    }

    /**
     * Adds the given coordinate to this Path.
     *
     * @param x the x coordinate value
     * @param y the y coordinate value
     */
    public void add(int x, int y) {
        this.x.add(x);
        this.y.add(y);
    }

    /**
     * Adds the given coordinates to this Path.
     *
     * @param xy a coordinate array in which xy[0] has the x coordinates and xy[1] has the y coordinates
     */
    public void add(int[][] xy) {
        for (int i = 0; i < xy.length; i++) {
            this.add(xy[i][0], xy[i][1]);
        }
    }

    /**
     * Gets the coordinate at the given index.
     *
     * <p>
     * If the index is out of the bounds of this Path, this method returns <code>null</code>.
     *
     * @param index the index of the coordinate to get
     * @return a <code>int[]</code> coordinate value in which [0] is the x value and [1] is the y value
     */
    public int[] get(int index) {
        if (index < x.size() && index >= 0) {
            return new int[]{x.get(index), y.get(index)};
        } else {
            return null;
        }
    }

    /**
     * Returns the last coordinate in this Path.
     *
     * <p>
     * The coordinate is given in a <code>int[]</code> where the 0th index is the x value, and the 1st index is the y value.
     *
     * <p>
     * If there are no coordinates in this Path, returns <code>null</code>.
     *
     * @return the last coordinate in this Path
     */
    public int[] getLast() {
        if (this.size() > 0) {
            return get(this.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * Removes the coordinate at the specified index.
     *
     * <p>
     * If the given index is out of the bounds of the current Path, this command does nothing.
     *
     * @param index the index of the coordinate to remove
     */
    public void remove(int index) {
        if (index >= 0 && x.size() > 0) {
            x.remove(index);
            y.remove(index);
        }
    }

    /**
     * Sets this Path to the given Path.
     *
     * <p>
     * If the given Path is <code>null</code>, this method does nothing.
     *
     * @param path the Path to set this Path to
     */
    public void set(Path path) {
        if (path != null) {
            this.clear();
            for (int i = 0; i < path.size(); i++) {
                this.add(path.get(i)[0], path.get(i)[1]);
            }
        }
    }

    /**
     * Saves this Path to the specified {@link File}.
     *
     * @param file the file to save this Path to
     * @return if the operation was successful or not
     */
    public boolean save(File file) {
        try {
            new ObjectOutputStream(new FileOutputStream(file)).writeObject(this);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Sets this Path to the Path at the specified {@link File}.
     *
     * @param file the Path file
     * @return if the operation was successful or not
     */
    public boolean load(File file) {
        if (file != null) {
            Path path;
            try {
                path = (Path)new ObjectInputStream(new FileInputStream(file)).readObject();
                this.set(path);
            } catch (Exception e) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Clears this Path of all its coordinates.
     */
    public void clear() {
        x.clear();
        y.clear();
    }

    /**
     * Gets the size of this Path.
     *
     * @return the size of this Path
     */
    public int size() {
        return x.size();
    }

    /**
     * Gets the length of this Path.
     *
     * @return the length of this Path
     */
    public double getLength() {
        double length = 0;
        for (int i = 0; i < this.size() - 1; i++) {
            length += getDistance(i, i + 1);
        }
        return length;
    }

    /**
     * Gets the distance from one coordinate to another.
     *
     * @param start the first coordinate
     * @param end the second coordinate
     * @return the distance between the first and second coordinates
     */
    public double getDistance(int start, int end) {
        return Math.sqrt(Math.pow(x.get(end) - x.get(start), 2) + Math.pow(y.get(end) - y.get(start), 2));
    }

    /**
     * Gets the angle between three coordinates at the point of the second coordinate.
     *
     * @param start the first coordinate
     * @param connector the second coordinate
     * @param end the third coordinate
     * @return the angle between the three coordinates at the point of the second coordinate
     */
    public double getAngle(int start, int connector, int end) {
        return Math.atan((getSlope(start, connector) - getSlope(connector, end)) / (1 + (getSlope(start, connector) * getSlope(connector, end))));
    }

    /**
     * Gets the slope of a line constructed from two given coordinates.
     *
     * @param start the first coordinate
     * @param end the second coordinate
     * @return the slope of a line constructed from two given coordinates
     */
    public double getSlope(int start, int end) {
        return (y.get(end) - y.get(start)) / (x.get(end) - x.get(start));
    }
}
