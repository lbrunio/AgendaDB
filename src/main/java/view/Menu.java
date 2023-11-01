package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.GestionSQL;
import io.IO;
import model.Departamento;
import model.Empleado;

public class Menu {
	public static void main(String[] args) {
		GestionSQL gestion = new GestionSQL();
		int opcion, opEmple, opDep;
		String yn;
		Integer id;
		boolean loop = true;

//		gestion.dropEmpleado();
//		gestion.dropDepartamento();
//		System.exit(0);

		// Lista de los empleados y departamentos
		List<Departamento> departamentos = new ArrayList<Departamento>();
		List<Empleado> empleados = new ArrayList<Empleado>();

		// Departamentos por defecto sin jefes
		Departamento marketing = new Departamento("Marketing", null);
		Departamento humanos = new Departamento("Humanos", null);
		Departamento ventas = new Departamento("Ventas", null);

		// Empleados por defecto sin departamentos
		Empleado e1 = new Empleado("A", 10, marketing);
		Empleado e2 = new Empleado("B", 11, humanos);
		Empleado e3 = new Empleado("C", 12, ventas);

		departamentos.add(ventas);
		departamentos.add(humanos);
		departamentos.add(marketing);

		// Add empleado y departamento en las listas

		// Departamentos con jefes
		ventas = new Departamento("Ventas", e3);

		while (loop) {
			opcion = menu();
			switch (opcion) {
			case 1:
				opEmple = menuEmple();
				switch (opEmple) {
				case 1:
					addEmpleado(gestion);
					break;

				case 2:
					mostrar(gestion);
					break;

				case 3:
					updateEmpleado(gestion);
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + opEmple);
				}

			case 2:
				opDep = menuDep();
				switch(opDep) {
				case 2:
					mostrarDepartamentos(gestion);
					break;
				}
				break;
				
				

			default:
				throw new IllegalArgumentException("Unexpected value: " + opcion);
			}
		}

	}
	
	
	private static void mostrarDepartamentos(GestionSQL gestion) {
		System.out.println(gestion.showDepartamento());
	}

	private static void updateEmpleado(GestionSQL gestion) {
		System.out.println("Introduce el id del empleado: ");
		Integer idEmpleado = IO.readInt();
		Empleado e = gestion.buscarEmpleado(idEmpleado);

		System.out.println("Cambiar el nombre " + e.getNombre() + " por? ");
		String updateNombre = IO.readString();
		if (!updateNombre.isBlank()) {
			e.setNombreEmple(updateNombre);
		}

		System.out.println("Cambiar el salario por? ");
		Double updateSalario = IO.readDouble();

		if (!updateSalario.isNaN()) {
			e.setSalario(updateSalario);
		}
	

		System.out.println("Asignar a un nuevo departamento ? Y/N ");
		String yn = IO.readString();

		if (yn.equalsIgnoreCase("y")) {
			System.out.println("Introduce el nombre del departamento: ");
			String nombreDep = IO.readString();
			
			System.out.println("Asignarlo como jefe? Y/N ");
			yn = IO.readString();
			if (yn.equalsIgnoreCase("y")) {
				Departamento d = new Departamento(nombreDep, e);
				
				boolean added = gestion.add(d, e);
				
				System.out.println(added ? "departamento added" : "not added");
				
				
				e.setDepartamento(d);
				
				added = gestion.update(e, d);
				
				System.out.println(added ? "empleado updated" : "not updated");
			
			}
		} else {
			System.out.println("Introduce el id del departamento: ");
			Integer idDep = IO.readInt();
			Departamento d1 = gestion.buscarDepartamento(idDep);
			
			e.setDepartamento(d1);
			
			
		}
	}

	private static void addEmpleado(GestionSQL gestion) {
		Empleado e;
		Departamento d = null;
		System.out.println("Introduce nombre empleado: ");
		String nombre = IO.readString();

		System.out.println("Salario del empleado: ");
		Double salario = IO.readDouble();

		System.out.println("Add to existing Departamento ? ");
		String yn = IO.readString();

		if (yn.equalsIgnoreCase("Y")) {
			System.out.println("Introduce el id del departamento");
			Integer idDep = IO.readInt();
			d = gestion.buscarDepartamento(idDep);
			
			e = new Empleado(nombre, salario, d);
			boolean added = gestion.add(e, d);
			
			System.out.println(added ? "Empleado added" : "Empleado not added");
		} else {
			e = new Empleado(nombre, salario, null);
			
			boolean added = gestion.add(e, d);
			System.out.println(added ? "Empleado added" : "Empleado not added");
		}
	}
			

	private static void mostrar(GestionSQL gestion) {
		System.out.println(gestion.showEmpleado());
	}

	private static int menuDep() {
		int opcion;
		System.out.println("""
				1: Add departamento
				2: Mostrar departamentos
				3: Modificar departamento
				4: Borrar departamento
				5: Salir
				""");
		opcion = IO.readInt();
		return opcion;
	}

	private static int menuEmple() {
		int opcion;
		System.out.println("""
				1: Add empleado
				2: Mostrar empleados
				3: Modificar empleado
				4: Borrar empleado
				5: Salir
				""");
		opcion = IO.readInt();
		return opcion;
	}

	private static int menu() {
		int opcion;
		System.out.println("Introduce opcion");
		System.out.println("""
				1: Empleado
				2: Departamento
				""");
		opcion = IO.readInt();
		return opcion;
	}

}
