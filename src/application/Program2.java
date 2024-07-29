package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {

		DepartmentDao deps = DaoFactory.createDepartmentDao();
		System.out.println("--- TEST 1: department findAll ---");
		List<Department> list = deps.findAll();
		list.forEach(System.out::println);

		System.out.println("\n--- TEST 2: department findById ---");
		System.out.println(deps.findById(2));

		System.out.println("\n--- TEST 3: department insert ---");
		Department dep = new Department(null, "inova");
		deps.insert(dep);
		System.out.println("Inserted! new id = " + dep.getId());
		
		System.out.println("\n--- TEST 4: department delete ---");
		deps.deleteById(50);
		System.out.println("Delete completed");
		
		System.out.println("\n--- TEST 4: department update ---");
		dep.setName("inovatech");
		deps.update(dep);
		System.out.println("update completed");
	}
}
