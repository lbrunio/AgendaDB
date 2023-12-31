package view;



import dao.GestionSQL;
import io.IO;
import model.Departamento;
import model.Empleado;

public class Menu {
	public static void main(String[] args) {
		GestionSQL gestion = new GestionSQL();
		int opcion, opEmple, opDep;
		
		boolean loop = true;

//		gestion.dropEmpleado();
//		gestion.dropDepartamento();
//		System.exit(0);
		

	

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
					mostrarEmpleado(gestion);
					break;

				case 3:
					updateEmpleado(gestion);
					break;
					
				case 4:
					deleteEmpleado(gestion);
					break;
					
				case 5:
					break;
					
				default:
					System.err.println("Opcion invalida");
					break;
				}
				break;
				
				
			case 2:
				opDep = menuDep();
				switch(opDep) {
				case 1:
					addDepartamento(gestion);
					break;
				case 2:
					mostrarDepartamentos(gestion);
					break;
					
				case 3:
					updateDepartamento(gestion);
					break;
					
				case 4:
					deleteDepartamento(gestion);
					break;
				
				case 5:
					break;
				
				default:
					System.err.println("Opcion invalida");
					break;
				}
				break;
				
				

			default:
				System.err.println("Opcion invalida");
				break;
			}
		}

	}

	/**
	 * 1: Add departamento 2: Mostrar departamentos 3: Modificar departamento 4:
	 * Borrar departamento 5: Salir
	 * 
	 */
	// -------------------------- Departamento ---------------------------------
	private static void deleteDepartamento(GestionSQL gestion) {
		System.out.println("Introduce el id del departamento que quiere borrar: ");
		Integer idDep = IO.readInt();

		Departamento d = gestion.buscarDepartamento(idDep);

		System.out.println("Seguro que quiere borrar el departamento " + d.toString());
		String yn = IO.readString();

		if (yn.equalsIgnoreCase("y")) {
			boolean deleted = gestion.deleteDepartamento(idDep);
			System.out.println(deleted ? "Departamento deleted" : "Departamento not deleted");
		}
	}

	private static void updateDepartamento(GestionSQL gestion) {
		boolean updated = false;
		System.out.println("Introduce el id del departmanto que quiere actualizar: ");
		Integer idDep = IO.readInt();

		Departamento d = gestion.buscarDepartamento(idDep);

		System.out.println("Cambiar el nombre " + d.getNombre() + " a ?: ");
		String nombreDepUpdate = IO.readString();

		if (!nombreDepUpdate.isBlank()) {
			d.setNombreDep(nombreDepUpdate);
		}

		System.out.println("Update jefe? Y/N");
		String yn = IO.readString();

		Integer idEmple = d.getJefe().getId();

		Empleado e = gestion.buscarEmpleado(idEmple);

		if (yn.equalsIgnoreCase("y")) {

			System.out.println("Cambiar el jefe " + e.getNombre() + " a ?: ");
			System.out.println("Nombre empleado: ");
			String nombreEmpleUpdate = IO.readString();

			if (!nombreEmpleUpdate.isBlank()) {
				e.setNombreEmple(nombreEmpleUpdate);
			}

			System.out.println("Salario: ");
			Double salarioUpdate = IO.readDouble();

			if (!salarioUpdate.isNaN()) {
				e.setSalario(salarioUpdate);
			}

			updated = gestion.update(d, e);
		} else {
			updated = gestion.update(d, e);
		}

		System.out.println(updated ? "Departamento updated" : "Departamento not updated");

	}

	private static void addDepartamento(GestionSQL gestion) {
		System.out.println("Introduce el nombre del departamento: ");
		String nombreDep = IO.readString();

		System.out.println("Add existing Empleado as Jefe? Y/N");
		String yn = IO.readString();

		if (yn.equalsIgnoreCase("y")) {
			System.out.println("Introduce el id del empleado: ");
			Integer idEmple = IO.readInt();

			Empleado e = gestion.buscarEmpleado(idEmple);
			boolean added = gestion.add(new Departamento(nombreDep, e), e);

			System.out.println(added ? "Departamento added" : "Departamento not added");
		} else {
			boolean added = gestion.add(new Departamento(nombreDep, null), null);
			System.out.println(added ? "Departamento added" : "Departamento not added");
		}
	}

	private static void mostrarDepartamentos(GestionSQL gestion) {
		System.out.println(gestion.showDepartamento());
	}

	// -------------------------- Departamento ---------------------------------

	// -------------------------- Empleado -------------------------------------
	private static void deleteEmpleado(GestionSQL gestion) {
		System.out.println("Introduce el ID del empleado que quiere borrar: ");
		Integer idEmple = IO.readInt();

		boolean deleted = gestion.deleteEmpleado(idEmple);

		System.out.println(deleted ? "Empleado deleted" : "Empleado not deleted");
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

				e.setDepartamento(d);
				boolean added = gestion.add(d, e);

				System.out.println(added ? "departamento added" : "not added");

				

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

	
	/** Add Empleado 
	 * 
	 * @param gestion database
	 */
	private static void addEmpleado(GestionSQL gestion) {
		Empleado e;
		Departamento d = null;
		Double salario;
		
		System.out.println("Introduce nombre empleado: ");
		String nombre = IO.readString();

		System.out.println("Salario del empleado: ");
		salario = IO.readDouble();

		System.out.println("Add to existing Departamento ? Y/N");
		String yn = IO.readString();

		if (yn.equalsIgnoreCase("Y")) {
			System.out.println("Introduce el id del departamento");
			Integer idDep = IO.readInt();
			d = gestion.buscarDepartamento(idDep);

			e = new Empleado(nombre, salario, d);
			boolean added = gestion.add(e, d);

			System.out.println(added ? "Empleado added" : "Empleado not added");
		} else {
			e = new Empleado(nombre, salario, d);

			boolean added = gestion.add(e, d);
			System.out.println(added ? "Empleado added" : "Empleado not added");
		}
	}

	/** Mostrar todos los empleados
	 * 
	 * @param gestion database
	 */
	private static void mostrarEmpleado(GestionSQL gestion) {
		System.out.println(gestion.showEmpleado());
	}

	// -------------------------- Empleado -------------------------------------

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
