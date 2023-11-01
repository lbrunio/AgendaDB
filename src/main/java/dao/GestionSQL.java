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

	public boolean add(Empleado empleado, Departamento d) {
		String sql = """
				INSERT INTO Empleado (nombre, salario, departamento)
				VALUES (?, ?, ?)
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, empleado.getNombre());
			ps.setDouble(2, empleado.getSalario());
			if (d != null) {
				ps.setInt(3, d.getId());
			} else {
				ps.setNull(3, Types.INTEGER);
			}

			if (ps.executeUpdate() > 0) {
				ResultSet generatedKeys = ps.getGeneratedKeys();
				if (generatedKeys.next()) {
					empleado.setId(generatedKeys.getInt(1));
				}
				return ps.executeUpdate() > 0;
			}
		} catch (SQLException e) {

		}
		return false;
	}


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

	public boolean update(Empleado empleado, Departamento d) {
		String sql = """
				UPDATE Empleado
				SET nombre = ?, salario = ?, departamento = ?
				WHERE id = ?
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, empleado.getNombre());
			ps.setDouble(2, empleado.getSalario());
			ps.setInt(3, d.getId());
			ps.setInt(4, empleado.getId());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
		}
		return false;
	}

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

	public Departamento buscarPorCodigoDep(Integer id) {
		String sql = """
				SELECT id_dep
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

	public void dropDepartamento() {
		String sql = """
				DROP TABLE Departamento;
				""";
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
		}
	}

	public void dropEmpleado() {
		String sql = """
				DROP TABLE Empleado;
				""";
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
		}
	}

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
