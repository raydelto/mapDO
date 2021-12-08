import java.applet.*;
import java.awt.*;
import java.awt.geom.*;
import java.sql.*;

public class Two extends Applet {

	public static Statement query;
	public static ResultSet RS;
	public Calle a = new Calle();
	public Calle b = new Calle();
	public Calle c = new Calle();
	public Calle d = new Calle();
	public Calle e = new Calle();
	public int i = 0;

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// for(int j=0;j<i;j++)
		// {
		// a[j].drawStreet(g);
		//
		// }
		a.getPoints("AV. ROMULO BETANCOURT");
		a.drawStreet(g2);
		b.getPoints("AV.CAONABO");
		b.drawStreet(g2);
		// c.getPoints("C-B");
		// c.drawStreet(g2);
		// d.getPoints("SAN JUAN BAUTISTA DE SALLE");
		// d.drawStreet(g2);
		// e.getPoints("ROSARIO");
		// e.drawStreet(g2);
	}

	public void init() {
		try {

			Conexion.conectar();
			RS = Conexion.getQuery("select distinct Street from Calles");

		} catch (Throwable j) {
			// System.out.println(j.getMessage());
		}

	}
}
