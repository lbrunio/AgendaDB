package model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Departamento {
	
	private Integer id;
	private String nombre;
	private Empleado jefe;
	
	
	public Departamento(String nombre, Empleado jefe) {
		this.id = id;
		this.nombre = nombre;
		this.jefe = jefe;
	}


	public Integer getId() {
		return id;
	}


	public String getNombre() {
		return nombre;
	}


	public Empleado getJefe() {
		return jefe;
	}


	@Override
	public String toString() {
		return "Departamento [id=" + id + ", nombre=" + nombre + ", jefe=" + jefe + "]";
	}
	
	
	
}
