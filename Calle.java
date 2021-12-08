import java.awt.*;
import java.awt.geom.*;
import java.sql.*;

public class Calle {
	private static Connection con;
	private static Statement query;
	private static ResultSet RS;
	private static double[] x1 = new double[5];
	private static double[] y1 = new double[5];
	private static double[] x2 = new double[5];
	private static double[] y2 = new double[5];
	private static double[] intersect = new double[5];
	private String nombreCalle;
	private int size = 0;
	public static double mas, menos;

	public double slope(double xx1, double yy1, double xx2, double yy2) {
		return ((yy2 - yy1) / (xx2 - xx1));
	}

	public double getExtra(double m, double xx, double yy, double e) {
		return (m * e) - (m * xx) + yy;
	}

	public double getInt(double b1, double b2, double m1, double m2) {
		return (b2 - b1) / (m1 - m2);
	}

	public void getPoints(String nombreCalle) {
		this.nombreCalle = nombreCalle;
		try {
			Conexion.conectar();
			RS = Conexion.getQuery("select * from Calles where Street = '" + nombreCalle + "'");

			while (RS.next()) {
				x1[size] = RS.getDouble(1);
				y1[size] = RS.getDouble(2);
				x2[size] = RS.getDouble(3);
				y2[size] = RS.getDouble(4);
				size++;
			}

			for (int i = 0; i < size - 1; i++) {
				intersect[i] = getInt(y1[i], y1[i + 1], slope(x1[i], y1[i], x2[i], y2[i]),
						slope(x1[i + 1], y1[i + 1], x2[i + 1], y2[i + 1]));
				intersect[i + 1] = 320;
			}

		}

		catch (Throwable j) {
			System.err.println(j.getMessage());
			System.err.println("Error Fatal!");
		}

	}

	public void drawStreet(Graphics2D g2) {
		Line2D.Double[] segment = new Line2D.Double[5];

		mas = -320;

		for (int i = 0; i < size; i++) {
			menos = intersect[i];

			y1[i] = getExtra(slope(x1[i], y1[i], x2[i], y2[i]), x1[i], y1[i], mas);
			x1[i] = mas;
			y2[i] = getExtra(slope(x1[i], y1[i], x2[i], y2[i]), x1[i], y1[i], menos);
			x2[i] = menos;

			mas = menos;
			x1[i] += 320;
			y1[i] += (240 - (y1[i] * 2));
			x2[i] += 320;
			y2[i] += 240 - (y2[i] * 2);

			segment[i] = new Line2D.Double(x1[i], y1[i], x2[i], y2[i]);
			g2.draw(segment[i]);

		}
	}
}
