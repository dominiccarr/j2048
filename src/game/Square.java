package game;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class Square {

	private int value;
	private int width = 100;
	private int height = 100;
	public int row;
	public int column;
	private boolean updated = false;
	
	public static final int UNUSED = -1;
	private static final Map<Integer, Color> map = new HashMap<>();

	static {
		genColors();
	}

	public static void genColors() {
		map.put(UNUSED, new Color(192, 179, 163));
		map.put(2, new Color(233, 222, 208));
		map.put(4, new Color(232, 218, 184));
		map.put(8, new Color(236, 163, 92));
		map.put(16, new Color(239, 131, 71));
		map.put(32, new Color(238, 105, 70));
		map.put(64, new Color(239, 72, 35));
		map.put(128, new Color(231, 200, 77));
	}

	public Color getColor() {
		int value = getValue();
		if (value > 128) 
			value = 128;
		return map.get(value);
	}

	public int getValue() {
		return value;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Square(int value, int row, int column) {
		this.value = value;
		this.row = row;
		this.column = column;
	}

	public Square(int row, int column) {
		this(UNUSED, row, column);
	}

	public Square(Square square) {
		this(square.getValue(), square.row, square.column);
		this.updated = square.isUpdated();
	}

	public boolean isUsed() {
		return value != UNUSED;
	}

	public boolean isMoveable() {
		return isUsed() && !isUpdated();
	}

	public void add(int val) {
		this.value += val;
		this.updated = true;
	}

	public boolean isFree() {
		return !isUsed();
	}

	public void free() {
		this.setValue(UNUSED);
	}

	public boolean isUpdated() {
		return updated;
	}

	public void unset() {
		updated = false;
	}

	public void setValue(int i) {
		value = i;
	}

	@Override
	public String toString() {
		return "Square [val=" + value + ", r=" + row + ", c=" + column + "]";
	}

}
