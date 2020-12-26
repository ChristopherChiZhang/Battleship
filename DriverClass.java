import java.util.ArrayList;
import java.util.List;

public class DriverClass {

	public static void main(String[] args) {
		Carrier ship1 = new Carrier();
		Battleship ship2 = new Battleship();
		Carrier ship3 = new Carrier();
		Carrier ship4 = new Carrier();
		Carrier ship5 = new Carrier();
		Battlefield cpu = new Battlefield();
		cpu.setToField(ship1);
		cpu.setToField(ship2);
		cpu.setToField(ship3);
		cpu.setToField(ship4);
		cpu.setToField(ship5);
		System.out.println();
		for(int i = 0; i < cpu.row; i++) {
			for(int j = 0; j < cpu.col; j++) {
				System.out.print(cpu.field[i][j] +" ");
			}
			System.out.println();
		}
		System.out.println("ship1 start: "+ship1.start.toString() +" ship1 end: "+ship1.end.toString());
		System.out.println("ship2 start: "+ship2.start.toString() +" ship2 end: "+ship2.end.toString());
		System.out.println("ship3 start: "+ship3.start.toString() +" ship3 end: "+ship3.end.toString());
		System.out.println("ship4 start: "+ship4.start.toString() +" ship4 end: "+ship4.end.toString());
		System.out.println("ship5 start: "+ship5.start.toString() +" ship5 end: "+ship5.end.toString());

		List<Point> points = new ArrayList<>();
		Point p = new Point(1,3);
		points.add(new Point(1,1));
		points.add(new Point(1,2));
		points.add(new Point(1,3));
		System.out.println(points.contains(p));
	}
}
