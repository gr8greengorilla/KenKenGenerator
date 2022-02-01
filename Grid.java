import javax.swing.JFrame;
import java.util.Random;
public class Grid {
    private Random randomizer = new Random();

    private int size;
    private Square[][] board;
    private int maxGroupSize;
    private int[][] groupValues;
    private String[] groupOperations;
    private static final int BOX_SIZES = 50;
    private static final int MARGINS = 50;

    public void Show() {
        DisplayGraphics m=new DisplayGraphics(this);  
        JFrame f=new JFrame();
        f.add(m);  
        f.setSize(getSize() * BOX_SIZES + MARGINS * 2, getSize() * BOX_SIZES + MARGINS * 2);   
        f.setVisible(true);
    }


    public Grid(int size, int maxGroupSize) {
        this.maxGroupSize = maxGroupSize;
        this.size = size;

        this.board = new Square[size][size];

        for (int x = 0; x < size; x++)  { //put a square object in every grid place
            for (int y = 0; y < size; y++) {
                this.board[x][y] = new Square();
            }
        }

        //next we have to assign all of the slots random values
        fillValues();

        //Now we must split the squares up into groups. This also returns the total amount of groups formed.
        int numGroups = splitSquares();

        //put all of the numbers in all of the groups into an array in the format Array[groupNum][Values]
        this.groupValues = new int[numGroups + 1][maxGroupSize];
        for (int i = 0; i <= numGroups; i++) {
            int j = 0;
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    if (get(x,y).getId() == i) {
                        groupValues[i][j] = get(x, y).getValue(); 
                        j++;
                    }
                }
            }
        }

        
        //Now we need to assign an operation to each group if it has more than one value
        groupOperations = new String[numGroups + 1];
        for (int i = 0; i <= numGroups; i++) {
            if (numValuesInGroup(groupValues[i]) > 1) {
                while (groupOperations[i] == null) {
                    double random = randomizer.nextDouble();
                    if (random < .25) {
                        groupOperations[i] = "+";
                    } else if (random < .5 && numValuesInGroup(groupValues[i]) == 2) {
                        groupOperations[i] = "-";
                    } else if (random < .75 && numValuesInGroup(groupValues[i]) == 2) {
                        groupOperations[i] = "/";
                    } else if (random < 1.0) {
                        groupOperations[i] = "x";
                   }
                }
            } else {
                groupOperations[i] = "N";
            }
        }


    }

    public int numValuesInGroup(int[] x) {
        int count = 0;
        for (int a : x) {
            if (a != 0) {count++;}
        }
        return count;
    }

    public void printGroupValues() {
        for (int i = 0; i < groupValues.length; i++) {
            System.out.print("group " + i + " has the values: ");
            for (int j = 0; j < groupValues[i].length; j++) {
                System.out.print(groupValues[i][j] + " ");
            }
            System.out.println(" And their operation is: " + groupOperations[i]);
        }
    }

    public int splitSquares() {
        int idIndex = -1;
        double spread;
        int i;
        int x;
        int y;
        boolean spreading;
        while (!allGrouped()) {
            for (int a = 0; a < size; a ++) {
                for (int b = 0; b < size; b++) {
                    
                    i = 0;
                    x = a;
                    y = b;

                    if (get(a,b).getId() == -1) { // will always go up by one at the beginning, so set idIndex to -1 so the first group's id is 0
                        idIndex++;
                    }

                    while (get(x,y).getId() == -1 && randomizer.nextDouble() < ( (maxGroupSize - i)/(double)(maxGroupSize))) {
                        get(x,y).setId(idIndex);

                        spreading = true;
                        int retries = 0;
                        while (spreading) {
                            spread = randomizer.nextDouble();
                            if (spread < .25 && x < size - 1) {
                                x++;
                                spreading = false;
                            } else if (spread < .50 && x > 1) {
                                x--;
                                spreading = false;
                            } else if (spread < .75 && y < size - 1) {
                                y++;
                                spreading = false;
                            } else if (y > 1) {
                                y--;
                                spreading = false;
                            }
                            
                            if (get(x,y).getId() != -1 && retries < 50) {
                                spreading = true;
                                x = a;
                                y = b;   
                            }
                            retries++;
                        }

                        i++;
                    }
                }
            }
        }
        return idIndex;
    }

    public boolean allGrouped() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (get(x, y).getId() == -1) {
                    return false;
                }
            }
        }
        return true;
    }


    public void fillValues() {
        int randomVal = -1;
        boolean newnum;
        int retry;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y ++) {
                retry = 0;
                while (!squareSet(x, y)) { //do this until it finds a working value. Could be optimized, but this is fine for me.

                    if (retry > 100) { //if it takes too many tries, its proabably impossible, so reset and try again
                        for (int a = 0; a < size; a++)  { //put a square object in every grid place
                            for (int b = 0; b < size; b++) {
                                this.board[a][b] = new Square();
                            }
                        }
                        x = 0;
                        y = 0;
                    }

                    randomVal = (int)(randomizer.nextDouble() * size) + 1;
                    newnum = true;

                    for (int i = 0; i < y; i++) {
                        if (get(x,i).getValue() == randomVal) { //If there is no same number in col
                            newnum = false;
                        }
                    }
                    for (int i = 0; i < x; i++) {
                        if (get(i,y).getValue() == randomVal) { //If there is no same number in row
                            newnum = false;
                        }
                    }
                    if (newnum) { //set the value
                        get(x,y).setValue(randomVal);
                    }
                    retry++;
                }
            }
        }
    }

    public Square get(int x, int y) {
        return board[x][y];
    }

    public int getSize() {
        return size;
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //output += (get(i,j).getValue());
                //output += (get(i,j).getId()) + "  ";
            }
            output += "\n";
        }
        return output;
    }

    public boolean squareSet(int x, int y) {
        return board[x][y].isSet();
    }

}
