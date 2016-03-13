import java.util.Iterator;

public class GridIterator implements Iterable<Square>, Iterator<Square> {

	private Square[][] array;
	private int row;
	private int column;
	private int numRows;
	private int numColumns;

	public GridIterator(Square[][] array) {
		this.array = array;
		this.row = 0;
		this.column = 0;
		this.numRows = array.length;
		this.numColumns = array[0].length;
	}

	public boolean hasNext() {
		return ((column <= numColumns - 1) && (row <= numRows - 1));
	}

	public Square next() {
		Square next = array[row][column];
		if (column == numColumns - 1) {
			row++;
			column = 0;
		} else {
			column++;
		}
		return next;
	}

	public void remove() {
		// TODO Auto-generated method stub

	}

	public Iterator<Square> iterator() {
		return this;
	}

}
