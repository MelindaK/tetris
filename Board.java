// Board.java
package tetris;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;

	private boolean[][] grid;
    private boolean[][] xGrid;

	private boolean DEBUG = true;
	boolean committed;

	private int[] widths;
    private int[] xWidths;

	private int[] heights;
    private int[] xHeights;

	private int maxHeight;
    private int xMaxHeight = 0;
	// Here a few trivial methods are provided:

	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;

		grid = new boolean[width][height];
        xGrid = new boolean[width][height];

		committed = true;
		maxHeight = 0;

		widths = new int[height];
        xWidths = new int[height];

		heights = new int[width];
        xHeights = new int[width];
	}


	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}


	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}


	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {
		return maxHeight;
	}


	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if (DEBUG) {
			// YOUR CODE HERE
		}
	}

	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.

	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		int yOrigin = 0;

		for (int i = 0; i < piece.getWidth(); i++){
			if ((heights[i + x] - piece.getSkirt()[i + x]) > yOrigin) {
				yOrigin = heights[i + x] - piece.getSkirt()[i + x];
			};
		}

		return yOrigin;
	}


	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		return heights[x];
	}


	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		 return widths[y];
	}


	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		if (x >= width || x < 0 || y >= height || y < 0 ) {
			return true;
		}

		return grid[x][y];
	}


	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;

	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.

	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
        if (!committed) throw new RuntimeException("place commit problem");
        committed = false;

        System.arraycopy(widths, 0, xWidths, 0, widths.length);
        System.arraycopy(heights, 0, xHeights, 0, heights.length);
        for(int i = 0; i < width; i++) {
            System.arraycopy(grid[i], 0, xGrid[i], 0, grid[i].length);
        }
        xMaxHeight = maxHeight;

		// YOUR CODE HERE
		// find point that piece fills
		// Check if piece is out of bounds (return out of bounds)
		// find if any of those points match a filled point on the board
			// if yes return place bad
			// if no return ok
		// Check if row is filled

		TPoint[] pieceBody = piece.getBody();
		TPoint[] currentPoints = new TPoint[pieceBody.length];

		// Find the current points taken up by piece placement
		for (int i = 0; i < pieceBody.length; i++) {
			int currentX = pieceBody[i].x + x;
			int currentY = pieceBody[i].y + y;
			currentPoints[i] = new TPoint(currentX, currentY);
		}

		for (int i = 0; i < currentPoints.length; i++) {
			// Check if piece is out of bounds
			if (currentPoints[i].x > width || currentPoints[i].y > height ){
				return PLACE_OUT_BOUNDS;
			}
			// Check if piece is overlaps a current filled point
			if (grid[currentPoints[i].x][currentPoints[i].y] == true) {
				return PLACE_BAD;
			}
		}

		// Update Grid with new piece
		for (int i = 0; i < currentPoints.length; i++ ) {
			grid[currentPoints[i].x][currentPoints[i].y] = true;

			//Update widths
			widths[currentPoints[i].y] += 1;

			//Update Heights
			if (currentPoints[i].y >= heights[currentPoints[i].x]) {
				heights[currentPoints[i].x] = currentPoints[i].y +1;
			}

			//Update Max Height
			if (currentPoints[i].y >= maxHeight) {
				maxHeight = currentPoints[i].y + 1;
			}
		}


		// Check whole grid if row is filled
		// change r=0 to y placement
		for (int r = 0; r < height; r++) {
			if (widths[r] == width) {
				return PLACE_ROW_FILLED;
			}
		}

		return PLACE_OK;
	}


	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
        if(committed == true){
            System.arraycopy(widths, 0, xWidths, 0, widths.length);
            System.arraycopy(heights, 0, xHeights, 0, heights.length);
            for(int i = 0; i < width; i++)
                System.arraycopy(grid[i], 0, xGrid[i], 0, grid[i].length);
            xMaxHeight = maxHeight;
        }
        committed = false;

		int rowsCleared = 0;

		for (int row = 0; row < height; row++) {
			if (widths[row] == width) {
				rowsCleared++;
				for (int i = row; i < maxHeight; i++) {
					if (i == (height - 1)) widths[i] = 0;
					else widths[i] = widths[i+1];
					
					for (int x = 0; x < width; x++){
						if (i == (height - 1)) grid[x][i] = false;
						else grid[x][i] = grid[x][i+1];
					}
				}
				row -= 1;
			}
		}
		
		for (int i = 0; i < width; i++) {
			heights[i] -= rowsCleared;
		}
		maxHeight -= rowsCleared;

		// Update widths, heights, maxHeight
		sanityCheck();
		return rowsCleared;
	}



	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
        if(committed) return;

        committed = true;

        int[] temp = widths;
        widths = xWidths;
        xWidths = temp;

        temp = heights;
        heights = xHeights;
        xHeights = temp;

        boolean[][] temp2 = grid;
        grid = xGrid;
        xGrid = temp2;

        int temp3 = maxHeight;
        maxHeight = xMaxHeight;
        xMaxHeight = temp3;
	}


	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		committed = true;
	}



	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility)
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


