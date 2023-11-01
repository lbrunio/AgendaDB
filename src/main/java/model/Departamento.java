package model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Departamento {
	
	private Integer idDep;
	private String nombreDep;
	private Empleado jefe;
	
	
	public Departamento(String nombre, Empleado jefe) {
		this.nombreDep = nombre;
		this.jefe = jefe;
	}
	
	public Departamento() {
		
	}


	public Departamento(Integer idDepartamento, String nombre, Empleado jefe) {
		this.idDep = idDepartamento;
		this.nombreDep = nombre;
		this.jefe = jefe;
	}

	public Integer getId() {
		return idDep;
	}


	public String getNombre() {
		return nombreDep;
	}


	public Empleado getJefe() {
		return jefe;
	}
	

	@Override
	public String toString() {
		return "Departamento [id=" + idDep + ", nombre=" + nombreDep + ", jefe=" + jefe + "]";
	}

	public void setId(Integer id) {
		this.idDep = id;
		
	}

	public String getNombreDep() {
		return nombreDep;
	}

	public void setNombreDep(String nombreDep) {
		this.nombreDep = nombreDep;
	}

	public void setJefe(Empleado jefe) {
		this.jefe = jefe;
	}
	
	


}
