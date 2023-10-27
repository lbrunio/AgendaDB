package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mariadb.jdbc.Statement;

import model.Departamento;
import model.Empleado;

public class AgendaSQL {
	private Connection connector = null;
	
	public AgendaSQL() {
		connector = Database.getConnection();
		
	}
	
	public void close() {
		Database.close();
	}
	
	private Empleado readEmpleado(ResultSet rs) {
		try {
			
			String nombre = rs.getString("nombre");
			Double salario = rs.getDouble("salario");
			Departamento departamento = (Departamento) rs.getObject("departamento");
			return new Empleado(nombre, salario, departamento);
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * 
	 * @param rs
	 * @return
	 */
	private Departamento readDepartamento(ResultSet rs) {
		try {
			
			String nombre = rs.getString("nombre");
			Empleado jefe = (Empleado) rs.getObject("jefe");
			return new Departamento(nombre, jefe);
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return null;
	}
	
	
	/** Mostrar los empleados 
	 * 
	 * @return string de empleados
	 */
	private String showEmpleado() {
		String sql = "SELECT id, nombre, salario, departamento FROM Empleado";
		
		try {
			StringBuffer sb = new StringBuffer();
			ResultSet rs = connector.createStatement().executeQuery(sql);
			
			while(rs.next()) {
				Empleado e = readEmpleado(rs);
				sb.append(e.toString());
				sb.append("\n");
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return "";
	}
	
	
	/** Mostrar los departamentos
	 * 
	 * @return string de departamentos
	 */
	public String showDepartamento() {
		String sql = "SELECT id, nombre, jefe FROM Departamento";
		
		try {
			StringBuffer sb = new StringBuffer();
			ResultSet rs = connector.createStatement().executeQuery(sql);
			
			while(rs.next()) {
				Departamento d = readDepartamento(rs);
				sb.append(d.toString());
				sb.append("\n");
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/** Busacar empleado por id o nombre
	 * 
	 * @param id
	 * @return null
	 */
	public Empleado buscarEmpleado(String id) {
		String sql = "SELECT id, nombre, salario, departamento FROM Empeleado WHERE id = ? OR id = ?";
		
		try {
			PreparedStatement ps = connector.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next() ) {
				return readEmpleado(rs);
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/** Buscar departamento por su id
	 * 
	 * @param id
	 * @return null
	 */
	public Departamento buscarDepartamento(Integer id) {
		String sql = "SELECT id, nombre, jefe FROM Departamento WHERE id = ?";
		
		try {
			PreparedStatement ps = connector.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return readDepartamento(rs);
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/** Add empleado
	 * 
	 * @param e Empleado
	 * @param d Departamento
	 * @return false
	 */
	public boolean addEmpleado(Empleado e, Departamento d) {
		String sql = "INSERT INTO Empleado(id, nombre, salario, departamento VALUES (?, ?, ?, ?)";
		
		try {
			PreparedStatement ps = connector.prepareStatement(sql);
			ps.setInt(1, e.getId());
			ps.setString(2, e.getNombre());
			ps.setDouble(3, e.getSalario());
			ps.setInt(4, d.getId());
		} catch (SQLException ex) {
			// TODO: handle exception
		}
	
		return false;
	}
	
	
	/** Add departamento
	 * 
	 * @param d Departamento
	 * @param e Empleado
	 * @return false
	 */
	public boolean addDepartamento(Departamento d, Empleado e) {
		String sql = "INSERT INTO Departamento() VALUES (?, ?, ?)";
		
		try {
			PreparedStatement ps = connector.prepareStatement(sql);
			ps.setInt(1, d.getId());
			ps.setString(2, d.getNombre());
			ps.setInt(3, e.getId());
		} catch (SQLException e2) {
			// TODO: handle exception
		}
		
		return false;
	}
	
	
	/** Actualizar empleado
	 * 
	 * @param e Empleado
	 * @param d Departamento
	 * @return false
	 */
	public boolean updateEmpleado(Empleado e, Departamento d) {
		String sql = "UPDATE Empleado SET nombre = ?, salario = ?, departamento = ?";
		
		try {
			PreparedStatement ps = connector.prepareStatement(sql);
			
			ps.setString(1, e.getNombre());
			ps.setDouble(2, e.getSalario());
			ps.setInt(3, d.getId());
		} catch (SQLException e2) {
			// TODO: handle exception
		}
		
		return false;
	}
	
	
	/** Actualizar departamento
	 * 
	 * @param d Departamento
	 * @param e Empleado
	 * @return false
	 */
	public boolean updateDepartamento(Departamento d, Empleado e) {
		String sql = "UPDATE Departamento SET nombre = ?, jefe = ?";
		
		try {
			PreparedStatement ps = connector.prepareStatement(sql);
			ps.setString(1, d.getNombre());
			ps.setInt(2, e.getId());
		} catch (SQLException e2) {
			// TODO: handle exception
		}
		
		return false;
	}
	
	
	/** Borrar empleado
	 * 
	 * @param id ID empleado
	 * @return false si no ha borrado
	 */
	public boolean deleteEmpleado(Integer id) {
		String sql = "DELETE FROM Empleado WHERE id = ?";
		
		try {
			PreparedStatement ps = connector.prepareStatement(sql);
			ps.setInt(1, id);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO: handle exception
		}
		
		return false;
	}
	
	/** Borrar departamento
	 * 
	 * @param id ID departamento
	 * @return false si no ha borrado
	 */
	public boolean deleteDepartamento(Integer id) {
		String sql = "DELETE FROM Departamento WHERE id = ?";
		
		try {
			PreparedStatement ps = connector.prepareStatement(sql);
			
			ps.setInt(1, id);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO: handle exception
		}
		
		return false;
	}
	
	public void dropEmpleado() {
		String sql = "DELETE FROM Empleado AND Departamento";
		
		try {
			Statement stmt = (Statement) connector.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO: handle exception
		}
	}
	
	public void dropDepartamanto() {
		String sql = "DELETE FROM Empleado AND Departamento";
		
		try {
			Statement stmt = (Statement) connector.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO: handle exception
		}
	}
	
	
}
