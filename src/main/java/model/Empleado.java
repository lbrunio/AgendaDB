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
	private Integer id;
	public String nombre;
	private Double salario;
	private Departamento departamento;
	
	
	public Empleado(String nombre, double salario, Departamento departamento) {
		this.id = id;
		this.nombre = nombre;
		this.salario = salario;
		this.departamento = departamento;
	}


	public Integer getId() {
		return id;
	}


	public String getNombre() {
		return nombre;
	}


	public Double getSalario() {
		return salario;
	}


	public Departamento getDepartamento() {
		return departamento;
	}


	@Override
	public String toString() {
		return "Empleado [id=" + id + ", nombre=" + nombre + ", salario=" + salario + ", departamento=" + departamento
				+ "]";
	}
	
	
	
	
	

	
}
