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
				default:
					throw new IllegalArgumentException("Unexpected value: " + opEmple);
				}

			case 2:
				opDep = menuDep();
				break;

			default:
				throw new IllegalArgumentException("Unexpected value: " + opcion);
			}
		}

	}





	private static void addEmpleado(GestionSQL gestion) {
		
		boolean added = false;
		System.out.println("Introduce nombre empleado: ");
		String nombre = IO.readString();

		System.out.println("Salario del empleado: ");
		Double salario = IO.readDouble();

		added = gestion.add(new Empleado(nombre, salario, null));
		
		System.out.println(added ? "added" : "Not added");
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
