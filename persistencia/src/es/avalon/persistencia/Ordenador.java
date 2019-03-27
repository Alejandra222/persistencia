package es.avalon.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Ordenador {

	private String modelo;
	private String marca;
	private double precio;

	public Ordenador() {
		super();
	}

	public Ordenador(String modelo) {
		super();
		this.modelo = modelo;
	}

	public Ordenador(String modelo, String marca, double precio) {
		super();
		this.modelo = modelo;
		this.marca = marca;
		this.precio = precio;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public static List<Ordenador> buscarTodos() {

		List<Ordenador> ordenadores = new ArrayList<Ordenador>();
		String sql = "select * from Ordenador";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);
				ResultSet rs = sentencia.executeQuery(sql)) {

			while (rs.next()) {
				String modelo = rs.getString("modelo");
				String marca = rs.getString("marca");
				double precio = rs.getDouble("precio");
				Ordenador o = new Ordenador(modelo, marca, precio);
				ordenadores.add(o);
			}

		} catch (Exception e) {
			System.out.println("Error: " + e);
			// e.printStackTrace();
		}

		return ordenadores;
	}

	public void insertarOrdenador() throws Exception {

		String sql = " insert into ordenador (modelo, marca, precio) values(?,?,?)";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, getModelo());
			sentencia.setString(2, getMarca());
			sentencia.setDouble(3, getPrecio());
			sentencia.executeUpdate();

			System.out.println(this.getModelo() + " fue insertado");

		} catch (Exception e) {
			System.err.println("Error desde la clase Ordenador: " + e.getMessage());
			throw e;
		}
	}

	public void deleteOrdenador() throws Exception {

		String sql = "delete from ordenador where modelo = ?";
		// String sql = "delete from ordenador where modelo = '"+this.getModelo()+"'";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, this.getModelo());
			sentencia.executeUpdate();
			System.out.println(modelo + " fue eliminado");

		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw e;
		}

	}

	public void updateOrdenado() {

		String sql = "update ordenador set marca = ?, precio = ? where modelo = ?";
		// String sql = "update ordenador set marca ='"+this.getMarca()+"',precio
		// ="+this.getPrecio()+" where modelo = '"+this.getModelo()+"'";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, this.getMarca());
			sentencia.setDouble(2, this.getPrecio());
			sentencia.setString(3, this.getModelo());
			sentencia.executeUpdate();

			System.out.println(this.getModelo() + " se actualizo correctamente");

		} catch (Exception e) {

			System.err.println(e.getMessage());
		}
	}

	public static Ordenador buscarUnOrdenadorPorModelo(String modelo) {

		String sql = "select * from Ordenador where modelo = ? ";
		Ordenador ordenador = null;

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);) {

			sentencia.setString(1, modelo);
			ResultSet rs = sentencia.executeQuery();// No le pasamos la sql
			rs.next();

			ordenador = new Ordenador(rs.getString("modelo"), rs.getString("marca"), rs.getDouble("precio"));
			// System.out.println("************* " + ordenador);

		} catch (Exception e) {
			System.out.println("Error en clase: " + e);
			// e.printStackTrace();
		}
		return ordenador;
	}

	public static List<Ordenador> filtrarPorModelo(String modelo) {

		System.out.println("filtrarPorModelo le llega: " + modelo);
		List<Ordenador> lista = new ArrayList<Ordenador>();
		String sql = "Select * from ordenador where modelo= ?";

		try (Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql)) {

			sentencia.setString(1, modelo);

			ResultSet rs = sentencia.executeQuery();

			while (rs.next()) {
				Ordenador or = new Ordenador(rs.getString("modelo"), rs.getString("marca"), rs.getDouble("precio"));
				lista.add(or);
			}

			System.out.println("filtrarPorModelo libros encontrados: "+lista.size());
		} catch (Exception e) {
			System.out.println("Error en metodo filtrarPorModelo: " + e);
		}
		return lista;

	}
	
	public static List<Ordenador> filtrar(String filtro){
		
		System.out.println("filtrarCampo recibe: "+filtro);
		
		List<Ordenador> lista = new ArrayList<Ordenador>();
		String sql = "Select * from ordenador order by "+filtro;
		
		try(Connection conexion = DBHelper.crearConexion();
				PreparedStatement sentencia = DBHelper.crearPreparedStatement(conexion, sql);
				ResultSet rs = sentencia.executeQuery();) {
			
			while(rs.next()) {
				String titulo =rs.getString("modelo");
				String autor =rs.getString("marca");
				double paginas =rs.getDouble("precio");
				Ordenador ord = new Ordenador(titulo,autor,paginas);
				lista.add(ord);
			}
			
			System.out.println("filtrarCampo libros encontrados: "+lista.size());
				
		} catch (Exception e) {
			System.out.println("Error en filtrarCampo "+e);
		}
		return lista;
		
		
	}
	
	


}
