import java.util.Random;
public class Grid {
    private Random randomizer = new Random();

    private int size;
    private Square[][] board;

    public Grid(int size) {
        this.size = size;

        this.board = new Square[size][size];

        for (int x = 0; x < size; x++)  { //put a square object in every grid place
            for (int y = 0; y < size; y++) {
                this.board[x][y] = new Square();
            }
        }

        //next we have to assign all of the slots random values
        fillValues();

    }

    public void fillValues() {
        int randomVal = -1;
        boolean newnum;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y ++) {
                while (!squareSet(x, y)) {
                    randomVal = (int)(randomizer.nextDouble() * size) + 1;
                    newnum = true;
                    for (int i = 0; i < y; i++) {
                        if (get(x,i).getValue() == randomVal) {
                            newnum = false;
                        }
                    }
                    for (int i = 0; i < x; i++) {
                        if (get(i,y).getValue() == randomVal) {
                            newnum = false;
                        }
                    }
                    if (newnum) {
                        get(x,y).setValue(randomVal);
                    }
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
                output += (get(i,j).getValue());
            }
            output += "\n";
        }
        return output;
    }

    public boolean squareSet(int x, int y) {
        return board[x][y].isSet();
    }

}
