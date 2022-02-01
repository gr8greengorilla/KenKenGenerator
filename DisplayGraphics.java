import java.awt.*;

public class DisplayGraphics extends Canvas{  
      
    private static final int BOX_SIZES = 50;
    private static final int MARGINS = 50;
    private static final int DIVIDER_THICKNESS = 6;
    private static final int DISPLAY_MARGINS = 5;
    private Grid grid;

    public DisplayGraphics(Grid grid) {
        this.grid = grid;
    }

    public void paint(Graphics g) { 
        g.drawString("Hello",40,40);
        setBackground(Color.WHITE);  
        setForeground(Color.BLACK);
        
        for (int i = 0; i < grid.getSize(); i ++) { //Make a grid of the right size
            for (int j = 0; j < grid.getSize(); j++) {
                g.drawRect(MARGINS + i * BOX_SIZES, MARGINS + j * BOX_SIZES, BOX_SIZES, BOX_SIZES);
            }
        }

        for (int x = 0; x < grid.getSize(); x ++) { //Add dividers between all groups
            for (int y = 0; y < grid.getSize(); y++) {
                if (x < grid.getSize() - 1 && grid.get(x, y).getId() != grid.get(x + 1,y).getId()) {
                    g.fillRect(MARGINS + x * BOX_SIZES + BOX_SIZES - DIVIDER_THICKNESS/2, MARGINS + y * BOX_SIZES, DIVIDER_THICKNESS, BOX_SIZES);
                }
                if (y < grid.getSize() - 1 && grid.get(x, y).getId() != grid.get(x,y + 1).getId()) {
                    g.fillRect(MARGINS + x * BOX_SIZES, MARGINS + y * BOX_SIZES + BOX_SIZES - DIVIDER_THICKNESS/2, BOX_SIZES, DIVIDER_THICKNESS);
                }
            }
        }

        int[][] groups = grid.getGroupValues();
        for (int i = 0; i < groups.length; i++) {
            for (int x = 0; x < grid.getSize(); x ++) {
                for (int y = 0; y < grid.getSize(); y++) {
                    if (grid.get(x, y).getId() == i) {
                        if (grid.getGroupOperation(i).equals("N")) {
                            g.drawString(Integer.toString(grid.getGroupDisplay(i)), MARGINS + x * BOX_SIZES + BOX_SIZES/2 - 5, MARGINS + y * BOX_SIZES + BOX_SIZES/2 + 5) ;
                        } else {
                            g.drawString(Integer.toString(grid.getGroupDisplay(i)) + "" + grid.getGroupOperation(i), MARGINS + x * BOX_SIZES + DISPLAY_MARGINS, MARGINS + y * BOX_SIZES + DISPLAY_MARGINS + 10);
                        }
                        x = grid.getSize();
                        y = grid.getSize();
                    }
                }
            }
        }
        //TODO Print thicker boundaries between grouops, label groups with operations and sum/totals, and fill the empty boxes with whole numbers
    }


    public void setGrid(Grid x) {
        this.grid = x;
    }
}  
