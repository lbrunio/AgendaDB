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
	            SELECT id_dep, nombre
	            FROM Departamento
	            """;
	    
	    try {
	        StringBuilder sb = new StringBuilder();
	        ResultSet rs = conn.createStatement().executeQuery(sql);
	        while (rs.next()) {
	            int idDepartamento = rs.getInt("id_dep");
	            String nombreDepartamento = rs.getString("nombre");
	            String nombreJefe = obtenerNombreJefe(idDepartamento);

	            sb.append("ID: ").append(idDepartamento)
	              .append(", Nombre Departamento: ").append(nombreDepartamento);
	              
	            if (nombreJefe != null) {
	                sb.append(", Jefe: ").append(nombreJefe);
	            }
	            
	            sb.append("\n");
	        }
	        return sb.toString();
	    } catch (SQLException e) {
	        // Manejar la excepción adecuadamente
	        e.printStackTrace();
	        // También podrías devolver un mensaje de error o lanzar una excepción para manejarla en otro lugar
	    }
	    return "";
	}

	private String obtenerNombreJefe(int idDepartamento) {
	    String sql = "SELECT E.nombre FROM Empleado E WHERE E.id_emple = (SELECT jefe FROM Departamento WHERE id_dep = ?)";
	    try {
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, idDepartamento);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return rs.getString("nombre");
	        }
	    } catch (SQLException e) {
	        // Manejar la excepción adecuadamente
	        e.printStackTrace();
	    }
	    return null;
	}


	
	public String showEmpleado() {
	    String sql = """
	            SELECT id_emple, nombre, salario, departamento
	            FROM Empleado
	            """;
	    
	    try {
	        StringBuilder sb = new StringBuilder();
	        ResultSet rs = conn.createStatement().executeQuery(sql);
	        while (rs.next()) {
	            int idEmpleado = rs.getInt("id_emple");
	            String nombre = rs.getString("nombre");
	            double salario = rs.getDouble("salario");
	            int idDepartamento = rs.getInt("departamento");

	            // Obtener el nombre del departamento a través de una función separada
	            String nombreDepartamento = obtenerNombreDepartamento(idDepartamento);

	            sb.append("ID: ").append(idEmpleado)
	              .append(", Nombre: ").append(nombre)
	              .append(", Salario: ").append(salario)
	              .append(", Departamento: ").append(nombreDepartamento)
	              .append("\n");
	        }
	        return sb.toString();
	    } catch (SQLException e) {
	        // Manejar la excepción adecuadamente, por ejemplo, registrándola o notificándola
	        e.printStackTrace();
	        // También podrías devolver un mensaje de error o lanzar una excepción para manejarla en otro lugar
	    }
	    return "";
	}
	
	private String obtenerNombreDepartamento(int idDepartamento) {
	    String sql = "SELECT nombre FROM Departamento WHERE id_dep = ?";
	    try {
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, idDepartamento);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	        	return rs.getString("nombre");
	        }
	    } catch (SQLException e) {
	        // Manejar la excepción adecuadamente
	        e.printStackTrace();
	    }
	    return "Departamento no encontrado";
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
	
	
	public boolean add(Empleado empleado) {
	    String sql = """
	            INSERT INTO Empleado (nombre, salario)
	            VALUES (?, ?)
	            """;
	    try {
	        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, empleado.getNombre());
	        ps.setDouble(2, empleado.getSalario());
	        
	        if (ps.executeUpdate() > 0) {
	            ResultSet generatedKeys = ps.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                empleado.setId(generatedKeys.getInt(1));
	            }
	            return true;
	        }
	    } catch (SQLException e) {
	     
	    }
	    return false;
	}
	
	public boolean add(Empleado empleado, Integer idDepartamento) {
	    String sql = """
	            INSERT INTO Empleado (nombre, salario, departamento)
	            VALUES (?, ?, ?)
	            """;
	    try {
	        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        ps.setString(1, empleado.getNombre());
	        ps.setDouble(2, empleado.getSalario());
	        ps.setInt(3, idDepartamento);
	        if (ps.executeUpdate() > 0) {
	            ResultSet generatedKeys = ps.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                empleado.setId(generatedKeys.getInt(1));
	            }
	            return true;
	        }
	    } catch (SQLException e) {
	       
	    }
	    return false;
	}


	
	public boolean add(Departamento departamento) {
	    String sql = """
	            INSERT INTO Departamento (nombre)
	            VALUES (?)
	            """;
	    try {
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, departamento.getNombre());
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
	
	public Empleado buscarPorCodigoEmple(Integer id) {
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
	        ps.setNull(3, d.getId());
	        ps.setInt(4, empleado.getId());
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
	        int idDepartamento = rs.getInt("id_departamento");
	        String nombre = rs.getString("nombre");
	        int idJefe = rs.getInt("id_jefe"); // Asumiendo que el campo en la tabla es id_jefe
	        Empleado jefe = new Empleado(); // Deberás recuperar el jefe de la base de datos
	        return new Departamento(idDepartamento, nombre, jefe);
	    } catch (SQLException e) {
	        // Maneja la excepción adecuadamente
	    }
	    return null;
	}

	private Empleado readEmpleado(ResultSet rs) {
	    try {
	        int idEmpleado = rs.getInt("id_emple");
	        String nombre = rs.getString("nombre");
	        Double salario = rs.getDouble("salario");
	        int idDepartamento = rs.getInt("departamento");
	        // Puedes agregar más atributos según la estructura de tu tabla Empleado
	        Departamento departamento = new Departamento(); // Deberás recuperar el departamento de la base de datos
	        return new Empleado(idEmpleado, nombre, salario, departamento);
	    } catch (SQLException e) {
	        // Maneja la excepción adecuadamente
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
