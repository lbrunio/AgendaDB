package model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
	private Integer idEmple;
	public String nombreEmple;
	private Double salario;
	private Departamento departamento;
	
	
	public Empleado(String nombre, double salario, Departamento departamento) {
		this.nombreEmple = nombre;
		this.salario = salario;
		this.departamento = departamento;
	}
	
	


	public Empleado(Integer idEmpleado, String nombre, Double salario, Departamento departamento) {
		this.idEmple = idEmpleado;
		this.nombreEmple = nombre;
		this.salario = salario;
		this.departamento = departamento;
	}




	public Empleado() {
	}




	public Integer getId() {
		return idEmple;
	}


	public String getNombre() {
		return nombreEmple;
	}


	public Double getSalario() {
		return salario;
	}

	public Departamento getDepartamento() {
		return departamento;
	}



	@Override
	public String toString() {
		return "Empleado [id=" + idEmple + ", nombre=" + nombreEmple + ", salario=" + salario + ", departamento=" + departamento
				+ "]";
	}
	
	
	

	public String getNombreEmple() {
		return nombreEmple;
	}




	public void setNombreEmple(String nombreEmple) {
		this.nombreEmple = nombreEmple;
	}




	public void setSalario(Double salario) {
		this.salario = salario;
	}




	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}




	public void setId(Integer id) {
		this.idEmple = id;
		
	}
	

	
}
