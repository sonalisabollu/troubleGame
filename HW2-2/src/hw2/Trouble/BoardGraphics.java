package hw2.Trouble;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class BoardGraphics {
    /*
        These hashtables are used to map Integer indexing values to 2d coordinate points.
     */
    HashMap<Integer, Point> boardCircularCoords;
    HashMap<Integer, Point>[] boardHomeCoords;
    HashMap<Integer, Point>[] boardFinishCoords;

    // For storing map into the memory
    ArrayList<String> grid;

    final int HORIZONTAL_DISPLACEMENT = 7;
    final int VERTICAL_DISPLACEMENT = 3;
    final int NUM_CELLS = 7;
    final String TEMPLATE_FILE_PATH = "./boardFile.txt";
    final int NUM_TEAMS = 4;

    // used to access different peg groups
    public static final int RED_INDEX = 0;
    public static final int BLUE_INDEX = 1;
    public static final int YELLOW_INDEX = 2;
    public static final int GREEN_INDEX = 3;


    public BoardGraphics() {
        loadBoardTemplate();
        bindIndexToCoordnate();
    }


    public void draw() {
        for(int i = 0; i < grid.size(); i++) {
            System.out.println(grid.get(i));
        }
    }

    /*
        References boardCircularCoords hashmap
        Inserts the string at cell p provided by hashmap
     */
    public void insertInCircularCell(int index, String str) {
        Point p = boardCircularCoords.get(index);
        insertInCell(p, str);
    }

    /*
        Similar to insertToCircularCell
        Uses boardHomeCoords to reference Home cells
     */
    public void insertInHomeCell(int homeIndex, int cellIndex, String str) {
        Point p = boardHomeCoords[homeIndex].get(cellIndex);
        insertInCell(p, str);
    }

    /*
        Uses boardFinishCoords has map to access finish cells.
     */
    public void insertInFinishCell(int homeIndex, int cellIndex, String str) {
        Point p = boardFinishCoords[homeIndex].get(cellIndex);
        insertInCell(p, str);
    }

    // ** PRIVATE HELPER FUNCITONS **

    private void loadBoardTemplate() {
        grid = new ArrayList<String>();
        BufferedReader objReader = null;
        try {
            String strCurrentLine;
            objReader = new BufferedReader(new FileReader(TEMPLATE_FILE_PATH));
            while ((strCurrentLine = objReader.readLine()) != null) {
                grid.add(strCurrentLine);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (objReader != null)
                    objReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void bindIndexToCoordnate() {
        boardCircularCoords = new HashMap<>();
        boardHomeCoords = new HashMap[4];
        boardFinishCoords = new HashMap[4];

        for(int i = 0; i < NUM_TEAMS; i++)
            boardHomeCoords[i] = new HashMap<>();
        for(int i = 0; i < NUM_TEAMS; i++)
            boardFinishCoords[i] = new HashMap<>();



        // ** Circular Cell Coordinates Point **
        // coordinates for first cell of upper cell list
        int y = 9;
        int x = 33;
        int cellIndex = 0;
        for(int i = cellIndex; cellIndex < i+ NUM_CELLS; cellIndex++) {
            boardCircularCoords.put(cellIndex, new Point(x, y));
            x += HORIZONTAL_DISPLACEMENT;
        }

        // coordinates for first cell of right vertical cell list
        y = 14;
        x = 90;
        for(int i = cellIndex; cellIndex < i+ NUM_CELLS; cellIndex++) {
            boardCircularCoords.put(cellIndex, new Point(x, y));
            y += VERTICAL_DISPLACEMENT;
        }

        // coordinates for last cell of bottom horizontal cell list
        y = 37;
        x = 74;
        for(int i = cellIndex; cellIndex < i+ NUM_CELLS; cellIndex++) {
            boardCircularCoords.put(cellIndex, new Point(x, y));
            x -= HORIZONTAL_DISPLACEMENT;
        }

        // coordinates for last cell of left vertical cell list
        y = 32;
        x = 20;
        for(int i = cellIndex; cellIndex < i+ NUM_CELLS; cellIndex++) {
            boardCircularCoords.put(cellIndex, new Point(x, y));
            y -= VERTICAL_DISPLACEMENT;
        }


        // ** Home Cell Coordinate Points **
        // RED HOME cells
        boardHomeCoords[0].put(0, new Point(20, 4));
        boardHomeCoords[0].put(1, new Point(24, 4));
        boardHomeCoords[0].put(2, new Point(20, 5));
        boardHomeCoords[0].put(3, new Point(24, 5));

        // RED HOME cells
        boardHomeCoords[1].put(0, new Point(102, 11));
        boardHomeCoords[1].put(1, new Point(106, 11));
        boardHomeCoords[1].put(2, new Point(102, 12));
        boardHomeCoords[1].put(3, new Point(106, 12));

        // RED HOME cells
        boardHomeCoords[2].put(0, new Point(81, 41));
        boardHomeCoords[2].put(1, new Point(85, 41));
        boardHomeCoords[2].put(2, new Point(81, 42));
        boardHomeCoords[2].put(3, new Point(85, 42));

        // RED HOME cells
        boardHomeCoords[3].put(0, new Point(7, 32));
        boardHomeCoords[3].put(1, new Point(11, 32));
        boardHomeCoords[3].put(2, new Point(7, 33));
        boardHomeCoords[3].put(3, new Point(11, 33));

        // ** FOR FINISH CELLS **
        // RED FINISH cells
        boardFinishCoords[0].put(0, new Point(29, 13));
        boardFinishCoords[0].put(1, new Point(33, 15));
        boardFinishCoords[0].put(2, new Point(37, 17));
        boardFinishCoords[0].put(3, new Point(41, 19));

        // BLUE FINISH cells
        boardFinishCoords[1].put(0, new Point(80, 13));
        boardFinishCoords[1].put(1, new Point(78, 15));
        boardFinishCoords[1].put(2, new Point(76, 17));
        boardFinishCoords[1].put(3, new Point(74, 19));

        // YELLOW FINISH cells
        boardFinishCoords[3].put(0, new Point(29, 33));
        boardFinishCoords[3].put(1, new Point(31, 31));
        boardFinishCoords[3].put(2, new Point(33, 29));
        boardFinishCoords[3].put(3, new Point(35, 27));

        // GREEB FINISH cells
        boardFinishCoords[2].put(0, new Point(80, 33));
        boardFinishCoords[2].put(1, new Point(78, 31));
        boardFinishCoords[2].put(2, new Point(76, 29));
        boardFinishCoords[2].put(3, new Point(74, 27));
    }

    // helper function to insert string at a particular point in 2d grid.
    private void insertInCell(Point p, String str) {
        char[] ch = str.toCharArray();
        char[] row = grid.get(p.y).toCharArray();
        row[p.x] = ch[0];
        row[p.x+1] = ch[1];
        grid.set(p.y,String.valueOf(row));
    }
}
