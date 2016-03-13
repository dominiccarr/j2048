import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Grid implements Iterable<Square> {

	private final int columns;
	private final int rows;
	private Square[][] arr;
	private Square[][] prev;
	private GamePanel ui;
	private static final int WINNING_NUMBER = 2048;
	private static final int INITIAL_PIECES = 2;

	public Grid(int rows, int columns, GamePanel gamePanel) {
		this.rows = rows;
		this.columns = columns;
		this.arr = new Square[rows][columns];
		this.prev = new Square[rows][columns];

		this.ui = gamePanel;
		setUp();
	}

	private void setUp() {
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				arr[row][column] = new Square(row, column);
			}
		}
		for (int i = 0; i < INITIAL_PIECES; i++) {
			addPiece();
		}
	}

	public Square getFreeSquare() {

		Square selected = null;
		do {
			int row = (int) (Math.random() * rows);
			int column = (int) (Math.random() * columns);
			selected = arr[row][column];
		} while (selected.isUsed());

		return selected;
	}

	public Square[][] getBoard() {
		return arr;
	}

	public void handle(KeyEvent e) {
		int code = e.getKeyCode();
		if (move(code)){
			saveBoard();
		}
		if (code == KeyEvent.VK_U){
			undo();
		}
		if (code == KeyEvent.VK_LEFT) {
			handleLeft();
		} else if (code == KeyEvent.VK_RIGHT) {
			handleRight();
		} else if (code == KeyEvent.VK_UP) {
			handleUp();
		} else if (code == KeyEvent.VK_DOWN) {
			handleDown();
		}
		if (move(code)) {
			statusCheck();
			addPiece();
			for (Square square : this) {
				square.unset();
			}
		}

	}
	
	private void undo() {
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				arr[row][column] = prev[row][column];
			}
		}		
	}

	private void saveBoard() {
		// TODO Auto-generated method stub
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				prev[row][column] = new Square(arr[row][column]);
			}
		}
	}

	private boolean move(int code){
		return code == KeyEvent.VK_DOWN || code == KeyEvent.VK_UP
				|| code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_LEFT;
	}

	private void statusCheck() {
		if (isLost()) {
			System.out.println("You Lose");
		} else if (isWon()) {
			setUp();
			System.out.println("You Win");
		}
	}

	private boolean isLost() {
		return usedCount() == rows * columns;
	}

	private boolean isWon() {
		Square square = getHighest();
		return square.getValue() == WINNING_NUMBER;
	}

	private Square getHighest() {
		Square highest = arr[0][0];
		for (Square current : this) {
			if (current.getValue() > highest.getValue())
				highest = current;
		}
		return highest;
	}

	private int usedCount() {
		int count = 0;
		for (Square current : this) {
			if (current.isUsed())
				count++;
		}
		return count;
	}

	private void addPiece() {
		getFreeSquare().setValue(2);
	}

	private void handleUp() {
		for (int column = 0; column < columns; column++) {
			for (int row = 0; row < rows; row++) {
				Square current = arr[row][column];
				if (current.isUsed())
					moveLeft(columnToRow(column), row);
				mergeRowL(columnToRow(column));
			}
		}
	}



	private Square[] columnToRow(int column) {
		Square[] rowArr = new Square[rows];
		for (int row = 0; row < rows; row++) {
			Square current = arr[row][column];
			rowArr[row] = current;
		}
		return rowArr;
	}

	private void handleLeft() {
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				Square current = arr[row][column];
				if (current.isUsed())
					moveLeft(arr[row], column);
				mergeRowL(arr[row]);
			}
		}
	}

	private void mergeRowL(Square[] row) {
		for (int j = 0; j < columns - 1; j++) {
			Square current = row[j];
			Square next = row[j + 1];
			if (current.isMoveable() && next.isMoveable())
				merge(current, next);
		}
	}

	private void moveLeft(Square[] row, int column) {
		for (int i = 0; i < column; i++) {
			Square current = row[i];
			if (current.isFree())
				swap(row[column], current);
		}
	}

	private void handleRight() {
		for (int row = 0; row < rows; row++) {
			for (int column = columns - 1; column >= 0; column--) {
				Square current = arr[row][column];
				if (current.isUsed())
					moveRight(arr[row], column);
				mergeRow(arr[row]);
			}
		}
	}
	
	private void handleDown() {
		for (int column = 0; column < columns; column++) {
			for (int row = rows-1; row >= 0; row--) {
				Square current = arr[row][column];
				Square[] col = columnToRow(column);
				if (current.isUsed())
					moveRight(col, row);
				mergeRow(col);
			}
		}
	}

	private void moveRight(Square[] row, int column) {
		for (int i = columns - 1; i > column; i--) {
			Square s = row[i];
			if (s.isFree())
				swap(row[column], s);
		}
	}

	private void mergeRow(Square[] row) {
		for (int j = columns - 1; j >= 1; j--) {
			Square current = row[j];
			Square next = row[j - 1];
			if (current.isMoveable() && next.isMoveable()) {
				merge(current, next);
			}
		}
	}

	private void merge(Square current, Square next) {
		if (current.getValue() == next.getValue()) {
			current.add(next.getValue());
			next.free();
		}
		paint();
	}

	private void swap(Square first, Square second) {
		if (second == null)
			return;
		second.setValue(first.getValue());
		first.free();
		paint();
	}

	private void paint() {
		ui.repaint();
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Iterator<Square> iterator() {
		return new GridIterator(arr);
	}

}