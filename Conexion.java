import java.sql.*;

/**
 * 
 * @author Grupo 1: Raydelto Hernandez, Raul Aybar y Rafael Vasquez
 * @version 1.1
 * 
 * 
 */
public class Conexion {

  /**
   * Instancia de la conexion la cual es utilizada por todos los metodos de esta
   * clase.
   */
  public static Connection con;

  /**
   * Tiene el valor true si esta conectado a la base de datos, y false si no lo
   * est�.
   */
  public static boolean conectado = false;

  /**
   * Aqui se almacenan todos los registros solicitados a la base de datos.
   */

  public static ResultSet RS;

  /**
   * Este es el objeto query el cual es utilizado para solicitar la informacion a
   * la base de datos.
   */
  public static Statement query;

  /**
   * Metodo utilizado para conectarse a la base de datos de map.do, devuelve true
   * si se ha conextado exitosamente
   * 
   */
  public static boolean conectar() throws SQLException

  {

    String URL = "jdbc:mysql://pegasus.s4wsbox.com:3306/mapDot?user=&password=";

    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      con = DriverManager.getConnection(URL);
      query = con.createStatement();
      conectado = true;

    }

    catch (Exception e) {
      System.err.println("Error al cargar Driver!.");
      e.printStackTrace();
    }

    return conectado;
  }

  /**
   * Devuelve un ResultSet a partir de un query
   */

  public static ResultSet getQuery(String consulta) throws SQLException {

    RS = query.executeQuery(consulta);
    return RS;
  }

  /**
   * 
   * 
   * /**
   * Inserta valores a una tabla.
   */

  public static boolean insert(String fields[], String table) throws SQLException {
    boolean exito = false;
    StringBuffer consult = new StringBuffer();
    try {

      consult.append("insert into " + table + " values(");

      for (int i = 0; i < fields.length; i++) {
        consult.append("\'" + fields[i] + "\'");
        if (i != fields.length - 1)
          consult.append(",");
      }

      consult.append(")");

      query.execute(consult.toString());
      exito = true;
    }

    catch (Exception e) {
      System.err.print("Error al insertar");
    }

    return exito;

  }

  /**
   * Borra registros de la tabla.
   */

  public static boolean delete(String values[], String field, String table) throws SQLException {
    boolean exito = false;
    StringBuffer consult = new StringBuffer();
    try {

      consult.append("delete from " + table + " where ");

      for (int i = 0; i < values.length; i++) {
        consult.append(table + "= \'" + values[i] + "\'");
        if (i != values.length - 1)
          consult.append(" or ");
      }

      query.execute(consult.toString());
      exito = true;
    }

    catch (Exception e) {
      System.err.print("Error al borrar");
    }

    return exito;

  }

  /**
   * 
   * 
   * 
   * Ejecuta un query en la base de datos, pero NO retorna nada (para DDL) insert,
   * delete, update , etc.
   */

  public static void execSql(String consulta) throws SQLException {
    query.execute(consulta);
  }

  /**
   * Cuenta cuantos Records hay en la tabla especificada
   */

  public static int countRecords(String table) throws SQLException {
    RS = query.executeQuery("select count(*) from " + table);
    return RS.getInt(0);
  }

  /**
   * Retorna los campos de las tablas especificadas
   */

  public static ResultSet getRecords(String[] fields, String[] table) throws SQLException {
    StringBuffer consult = new StringBuffer();
    consult.append("select ");
    for (int i = 0; i < fields.length; i++) {
      consult.append(table[i] + "." + fields[i]);
      if (i != fields.length - 1)
        consult.append(",");
    }

    consult.append(" from ");

    for (int i = 0; i < fields.length; i++)

    {
      consult.append(table[i]);
      if (i != fields.length - 1)
        consult.append(",");
    }

    RS = query.executeQuery(consult.toString());
    return RS;
  }

}
