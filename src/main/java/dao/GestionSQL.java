package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import model.Departamento;
import model.Empleado;

public class GestionSQL {
	private Connection conn = null;

	public GestionSQL() {
		conn = Database.getConnection();
		createTables();
	}

	public void close() {
		Database.close();
	}

	// -------------------------- Empleado -------------------------------------

	/** Mostrar empleados
	 * 
	 * @return "" or empleado.toString
	 */
	public String showEmpleado() {
		String sql = """
				SELECT id_emple, nombre, salario, departamento
				FROM Empleado
				""";

		try {
			StringBuilder sb = new StringBuilder();
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				Empleado e = readEmpleado(rs);
				sb.append(e.toString());
				sb.append("\n");
			}
			return sb.toString();
		} catch (SQLException e) {

			e.printStackTrace();

		}
		return "";
	}

	/** Buscar el empleado por id
	 * 
	 * @param id del empleado
	 * @return null or Empleado
	 */
	public Empleado buscarEmpleado(Integer id) {
		String sql = """
				SELECT id_emple, nombre, salario, departamento
				FROM Empleado
				WHERE id_emple = ?
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return readEmpleado(rs);
			}
		} catch (SQLException e) {

		}
		return null;
	}

	/** Add empleado
	 * 
	 * @param empleado empleado	
	 * @param departamento departamento
	 * @return false if ps.executeUpdate < 0 else true
	 */
	public boolean add(Empleado empleado, Departamento departamento) {
		String sql = """
				INSERT INTO Empleado (nombre, salario, departamento)
				VALUES (?, ?, ?)
				""";
		try {
			
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, empleado.getNombre());
			ps.setDouble(2, empleado.getSalario());
			if (departamento != null) {
				ps.setInt(3, departamento.getId());
			} else {
				ps.setNull(3, Types.INTEGER);
			}

			if (ps.executeUpdate() > 0) {
				ResultSet generatedKeys = ps.getGeneratedKeys();
				if (generatedKeys.next()) {
					departamento.setId(generatedKeys.getInt(1));
				}
			}
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {

		}
		return false;
	}


	/** Borrar empleado
	 * 
	 * @param id empleado
	 * @return false if ps.executeUpdate < 0 else true
	 */
	public boolean deleteEmpleado(Integer id) {
		String sql = """
				DELETE FROM Empleado
				WHERE id_emple = ?;
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
		}
		return false;
	}

	/** Actualizar empleado
	 * 
	 * @param empleado empleado
	 * @param departamento departamento
	 * @return false if empleado not updated else true
	 */
	public boolean update(Empleado empleado, Departamento departamento) {
		String sql = """
				UPDATE Empleado
				SET nombre = ?, salario = ?, departamento = ?
				WHERE id = ?
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, empleado.getNombre());
			ps.setDouble(2, empleado.getSalario());
			ps.setInt(3, departamento.getId());
			ps.setInt(4, empleado.getId());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
		}
		return false;
	}

	/** Leer empleado
	 * 
	 * @param rs ResultSet
	 * @return null or new Empleado
	 */
	private Empleado readEmpleado(ResultSet rs) {
		try {
			int idEmpleado = rs.getInt("id_emple");
			String nombre = rs.getString("nombre");
			Double salario = rs.getDouble("salario");
			int idDepartamento = rs.getInt("departamento");

			Departamento d = buscarDepartamento(idDepartamento);

			return new Empleado(idEmpleado, nombre, salario, d);
		} catch (SQLException e) {

		}
		return null;
	}

	// ---------------------Empleado--------------------------------------

	// ---------------------Departamento----------------------------------
	
	/** Mostrar todos los departamentos
	 * 
	 * @return "" or departamento.toString
	 */
	public String showDepartamento() {
		String sql = """
				SELECT id_dep, nombre, jefe
				FROM Departamento
				""";

		try {
			StringBuilder sb = new StringBuilder();
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				Departamento e = readDepartamento(rs);
				sb.append(e.toString());
				sb.append("\n");
			}
			return sb.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	/** Buscar departamento por id
	 * 
	 * @param id departamento
	 * @return false si no lo encuentra else true
	 */
	public Departamento buscarDepartamento(Integer id) {
		String sql = """
				SELECT id_dep, nombre, jefe
				FROM Departamento
				WHERE id_dep = ?
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return readDepartamento(rs);
			}
		} catch (SQLException e) {

		}
		return null;
	}

	/** Add departamento 
	 * 
	 * @param departamento departamento
	 * @param jefe empleado
	 * @return false si no ha sido added else true
	 */
	public boolean add(Departamento departamento, Empleado jefe) {
		String sql = """
				INSERT INTO Departamento (nombre, jefe)
				VALUES (?, ?)
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, departamento.getNombre());
			if (jefe != null) {
				ps.setInt(2, jefe.getId());
			} else {
				ps.setNull(2, Types.INTEGER);
			}

			if (ps.executeUpdate() > 0) {
				ResultSet generatedKeys = ps.getGeneratedKeys();
				if (generatedKeys.next()) {
					jefe.setId(generatedKeys.getInt(1));
				}
			}
		} catch (SQLException e) {

		}
		return false;
	}

	/** Actualizar empleado
	 * 
	 * @param departamento departamento
	 * @param empleado empleado
	 * @return false si no ha sido actualizado else true
	 */
	public boolean update(Departamento departamento, Empleado empleado) {
		String sql = """
				UPDATE Departamento
				SET nombre = ?,
				WHERE id = ?
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, departamento.getNombre());
			ps.setInt(2, empleado.getId());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {

		}
		return false;
	}

	/**
	 * 
	 * @param id departamento
	 * @return false si no ha borrado else true
	 */
	public boolean deleteDepartamento(Integer id) {
		String sql = """
				DELETE FROM Departamento
				WHERE id_dep = ?
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
		}
		return false;
	}

	/**
	 *  Eliminar todos los valores de la tabla departamento
	 */
	public void dropDepartamento() {
		String sql = """
				DELETE FROM Departamento
				""";
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
		}
	}

	/**
	 * Eliminar todos los valores de la tabla empleado
	 */
	public void dropEmpleado() {
		String sql = """
				DELETE FROM Empleado
				""";
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
		}
	}

	/**
	 * 
	 * @param rs ResultSet
	 * @return null si no hay departamento else return new Departamento
	 */
	private Departamento readDepartamento(ResultSet rs) {
		try {
			int idDepartamento = rs.getInt("id_dep");
			String nombre = rs.getString("nombre");
			int idJefe = rs.getInt("jefe");
			Empleado jefe = buscarEmpleado(idJefe);

			return new Departamento(idDepartamento, nombre, jefe);
		} catch (SQLException e) {

		}
		return null;
	}

	// ---------------------Departamento-------------------------------
	/**
	 *  Crear las tablas
	 */
	private void createTables() {
		String sql = null;

		if (Database.typeDB.equals("sqlite")) {
			sql = """
					CREATE TABLE IF NOT EXISTS Departamento (
					id_dep INTEGER PRIMARY KEY AUTOINCREMENT,
					nombre TEXT,
					jefe INTEGER,
					FOREIGN KEY (jefe) REFERENCES Empleado(id_emple) ON DELETE SET NULL
					);

					CREATE TABLE IF NOT EXISTS Empleado (
					id_emple INTEGER PRIMARY KEY AUTOINCREMENT,
					nombre TEXT,
					salario REAL,
					departamento INTEGER,
					FOREIGN KEY (departamento) REFERENCES Departamento(id_dep) ON DELETE SET NULL
					);
					              """;
		}

		if (Database.typeDB.equals("mariadb")) {
			sql = """
					               CREATE TABLE IF NOT EXISTS Departamento (
						id_dep INT AUTO_INCREMENT PRIMARY KEY,
						nombre VARCHAR(255),
						jefe INT NULL,
						FOREIGN KEY (jefe) REFERENCES Empleado(id_emple) ON DELETE SET NULL
						);

					CREATE TABLE IF NOT EXISTS Empleado (
						id_emple INT AUTO_INCREMENT PRIMARY KEY,
						nombre VARCHAR(255),
						salario DECIMAL(10, 2),
						departamento INT NULL,
						FOREIGN KEY (departamento) REFERENCES Departamento(id_dep) ON DELETE SET NULL
						);
					               """;
		}
		try {
			conn.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
		}
	}

}
