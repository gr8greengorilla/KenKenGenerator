import java.awt.*;

public class DisplayGraphics extends Canvas{  
      
    private static final int BOX_SIZES = 50;
    private static final int MARGINS = 50;
    private Grid grid;

    public DisplayGraphics(Grid grid) {
        this.grid = grid;
    }

    public void paint(Graphics g) { 
        g.drawString("Hello",40,40);
        setBackground(Color.WHITE);  
        setForeground(Color.BLACK);
        
        for (int i = 0; i < grid.getSize(); i ++) {
            for (int j = 0; j < grid.getSize(); j++) {
                g.drawRect(MARGINS + i * BOX_SIZES, MARGINS + j * BOX_SIZES, BOX_SIZES, BOX_SIZES);
            }
        }
        //TODO Print thicker boundaries between grouops, label groups with operations and sum/totals, and fill the empty boxes with whole numbers
    }


    public void setGrid(Grid x) {
        this.grid = x;
    }
}  
