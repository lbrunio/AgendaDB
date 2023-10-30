package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public String showDepartamento() {
	    String sql = """
	            SELECT id, nombre, jefe
	            FROM Departamento
	            """;
	    try {
	        StringBuffer sb = new StringBuffer();
	        ResultSet rs = conn.createStatement().executeQuery(sql);
	        while (rs.next()) {
	            Departamento departamento = readDepartamento(rs);
	            sb.append(departamento.toString());
	            sb.append("\n");
	        }
	        return sb.toString();
	    } catch (SQLException e) {
	        
	    }
	    return "";
	}

	
	public String showEmpleado() {
	    String sql = """
	            SELECT id, nombre, salario, departamento
	            FROM Empleado
	            """;
	    try {
	        StringBuffer sb = new StringBuffer();
	        ResultSet rs = conn.createStatement().executeQuery(sql);
	        while (rs.next()) {
	            Empleado empleado = readEmpleado(rs);
	            sb.append(empleado.toString());
	            sb.append("\n");
	        }
	        return sb.toString();
	    } catch (SQLException e) {
	       
	    }
	    return "";
	}

	
	public Departamento buscarDepartamento(Integer id) {
	    String sql = """
	            SELECT id, nombre, jefe
	            FROM Departamento
	            WHERE id = ?
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

	
	
	public Empleado buscarEmpleado(Integer id) {
	    String sql = """
	            SELECT id, nombre, salario, departamento
	            FROM Empleado
	            WHERE id = ?
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
	
	public boolean add(Departamento departamento) {
	    String sql = """
	            INSERT INTO Departamento (nombre)
	            VALUES (?)
	            """;
	    try {
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, departamento.getNombre());
	        return ps.executeUpdate() > 0;
	        
	     
	    } catch (SQLException e) {
	      
	    }
	    return false;
	}
	
	public Departamento buscarPorCodigo(Integer id) {
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



	public boolean add(Empleado empleado) {
	    String sql = """
	            INSERT INTO Empleado (nombre, salario)
	            VALUES (?, ?)
	            """;
	    try {
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, empleado.getNombre());
	        ps.setDouble(2, empleado.getSalario());

	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	       
	    }
	    return false;
	}

	
//	public boolean add(Empleado empleado) {
//	    String sql = """
//	            INSERT INTO Empleado (nombre, salario)
//	            VALUES (?, ?)
//	            """;
//	    try {
//	        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//	        ps.setString(1, empleado.getNombre());
//	        ps.setDouble(2, empleado.getSalario());
//	        
//	        return ps.executeUpdate() > 0;
//	    } catch (SQLException e) {
//	       
//	    }
//	    return false;
//	}

//	public boolean add(Departamento departamento) {
//	    String sql = """
//	            INSERT INTO Departamento (nombre)
//	            VALUES (?)
//	            """;
//	    try {
//	        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//	        ps.setString(1, departamento.getNombre());
//	        
//	        return ps.executeUpdate() > 0;
//	       
//	    } catch (SQLException e) {
//	        
//	    }
//	    return false;
//	}

	
	
	
//	public boolean add(Empleado empleado) {
//	    String sql = """
//	            INSERT INTO Empleado (nombre, salario)
//	            VALUES (?, ?)
//	            """;
//	    try {
//	        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//	        ps.setString(1, empleado.getNombre());
//	        ps.setDouble(2, empleado.getSalario());
//	        return ps.executeUpdate() > 0;
//	        
//	    } catch (SQLException e) {
//	    }
//	    return false;
//	}


	
	public boolean update(Departamento departamento) {
	    String sql = """
	            UPDATE Departamento
	            SET nombre = ?
	            WHERE id = ?
	            """;
	    try {
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, departamento.getNombre());
	        ps.setInt(2, departamento.getId());
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        
	    }
	    return false;
	}

	
	
	public boolean update(Empleado empleado) {
	    String sql = """
	            UPDATE Empleado
	            SET nombre = ?, salario = ?
	            WHERE id = ?
	            """;
	    try {
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, empleado.getNombre());
	        ps.setDouble(2, empleado.getSalario());
	        ps.setInt(3, empleado.getId());
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

	
	private Departamento readDepartamento(ResultSet rs) {
	    try {
	        Integer id = rs.getInt("id");
	        String nombre = rs.getString("nombre");
	        Integer jefeId = rs.getInt("jefe");

	        Empleado jefe = new Empleado();
	        jefe.setId(jefeId);

	        Departamento departamento = new Departamento(nombre, jefe);
	        return departamento;
	    } catch (SQLException e) {
	        
	    }
	    return null;
	}

	
	
	private Empleado readEmpleado(ResultSet rs) {
	    try {
	        Integer id = rs.getInt("id");
	        String nombre = rs.getString("nombre");
	        Double salario = rs.getDouble("salario");
	        Integer departamentoId = rs.getInt("departamento");

	        Departamento departamento = new Departamento();
	        departamento.setId(departamentoId);

	        Empleado empleado = new Empleado(nombre, salario, departamento);
	        return empleado;
	    } catch (SQLException e) {
	        
	    }
	    return null;
	}

	
	

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
						jefe INT,
						FOREIGN KEY (jefe) REFERENCES Empleado(id_emple) ON DELETE SET NULL
						);

					CREATE TABLE IF NOT EXISTS Empleado (
						id_emple INT AUTO_INCREMENT PRIMARY KEY,
						nombre VARCHAR(255),
						salario DECIMAL(10, 2),
						departamento INT,
						FOREIGN KEY (departamento) REFERENCES Departamento(id_dep) ON DELETE SET NULL
						);
					               """;
		}
		try {
			conn.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
		}
	}
	
	public void insertEmpleValues() {
		String sql = """
	            INSERT INTO Empleado (nombre, salario, departamento)
	            VALUES (?, ?, ?)
	            """;
	}

}
