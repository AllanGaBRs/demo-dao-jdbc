package application;

import java.util.Date;
import java.util.List;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("--- TEST 1: seller findById ---");
		System.out.println(sellerDao.findById(4));
		
		System.out.println("\n--- TEST 2: seller findByDepartment ---");
		Department dep = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartmentId(dep);
		list.forEach(System.out::println);
		
		System.out.println("\n--- TEST 3: seller findAll ---");
		List<Seller> list2 = sellerDao.findAll();
		list2.forEach(System.out::println);
		
		System.out.println("\n--- TEST 4: seller insert ---");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, dep);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! new id = " + newSeller.getId());
		
		System.out.println("\n--- TEST 5: seller update ---");
		newSeller = sellerDao.findById(1);
		newSeller.setName("Martha Waine");
		sellerDao.update(newSeller);
		System.out.println("Update Complete");
		
		System.out.println("\n--- TEST 5: seller delete ---");
		sellerDao.deleteById(2);
		System.out.println("Delete completed");
	
		DB.closeConnection();
	}

}
