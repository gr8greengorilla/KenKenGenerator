import java.util.Random;
public class Grid {
    private Random randomizer = new Random();

    private int size;
    private Square[][] board;
    private int maxGroupSize;

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

        //Now we must split the squares up into groups
        splitSquares();

    }

    public void splitSquares() {
        int idIndex = 0;
        double spread;
        int i;
        int x;
        int y;
        boolean spreading;
        while (!allGrouped()) {
            for (int a = 0; a < size; a ++) {
                for (int b = 0; b < size; b++) {
                    i = 0;
                    if (get(a,b).getId() == -1) {
                        idIndex++;
                    }
                    x = a;
                    y = b;
                    while (get(x,y).getId() == -1 && randomizer.nextDouble() < (maxGroupSize - i)/(double)maxGroupSize) {
                        get(x,y).setId(idIndex);

                        spreading = true;
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
                        }
                        i++;
                    }
                }
            }
        }
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
                //output += (squareSet(i, j) ? "X " : "- ");
                //output += (get(i,j).getValue());
                output += (get(i,j).getId()) + "  ";
            }
            output += "\n";
        }
        return output;
    }

    public boolean squareSet(int x, int y) {
        return board[x][y].isSet();
    }

}
