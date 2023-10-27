package view;

import java.util.Scanner;

import model.Departamento;
import model.Empleado;

public class Menu {
	public static void main(String[] args) {

//		Scanner sc = new Scanner(System.in);
//		int opcion;
//		int opcionEmple;
		
		
		
//		Empleado e = new Empleado("aaa", 12.1, new Departamento("aaa", null));
		
		

//		while (true) {
//			opcion = menu(sc);
//
//			switch (opcion) {
//			case 2:
//				opcionEmple = menuEmpleado(sc);
//				switch(opcionEmple) {
//				case 1:
//					break;
//				}
//				break;
//			}
//		}
//
	}
	
	private static int menuEmpleado(Scanner sc) {
		int opcion;
		System.out.println("1. Add empleado");
		System.out.println("2. Borrar empleado");
		System.out.println("3. Buscar empleado");
		System.out.println("4. Salir");
		opcion = Integer.parseInt(sc.nextLine());
		return opcion;
	}

	private static int menu(Scanner sc) {
		int opcion;
		System.out.println("Introduce opcion");
		System.out.println("1. Departamento");
		System.out.println("2. Empleados");
		opcion = Integer.parseInt(sc.nextLine());
		
		return opcion;
	}
}
