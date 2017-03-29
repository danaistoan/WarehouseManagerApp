package com.tgs.warehouse.main;

import java.util.ArrayList;
import java.util.List;

import com.tgs.warehouse.dao.LogisticUnitDAO;
import com.tgs.warehouse.entities.*;


public class WarehouseMain {
	
	static LogisticUnitDAO logisticUnitDAO = new LogisticUnitDAO();

	public static void main(String[] args) {
		
		loadProductPallets();
		deleteProductPalletFromDB();
		findProductPallet();
		
	}
	
	
	public static void loadProductPallets(){
		
		ProductPackage package1 = new ProductPackage("Food for children", "Food");
		ProductPackage package2 = new ProductPackage("Cereals", "Food");
		
		ProductPackage package3 = new ProductPackage("Milk for babies", "Food");
		ProductPackage package4 = new ProductPackage("Cereals for babies", "Food");
		
		List<ProductPackage> packagesList1 = new ArrayList<ProductPackage>();
		packagesList1.add(package1);
		packagesList1.add(package2);
		
		List<ProductPackage> packagesList2 = new ArrayList<ProductPackage>();
		packagesList2.add(package3);
		packagesList2.add(package4);
		
		ProductPallet pallet1 = new ProductPallet("Food", packagesList1);
		ProductPallet pallet2 = new ProductPallet("Baby-Food", packagesList2);
		
		ProductPallet registeredPallet1 = logisticUnitDAO.insertProductPallet(pallet1);
		System.out.println("The following product pallet was inserted in DB: " + registeredPallet1.getId() + " "
				+ registeredPallet1.getDescription());	
		
		ProductPallet registeredPallet2 = logisticUnitDAO.insertProductPallet(pallet2);
		System.out.println("The following product pallet was inserted in DB: " + registeredPallet2.getId() + " "
				+ registeredPallet2.getDescription());	
	}
	
	public static void deleteProductPalletFromDB(){
		
		boolean deletedSucces = false; 
		deletedSucces = logisticUnitDAO.deleteProductPallet((long) 72);
		
		if(deletedSucces){
			System.out.println("The pallet with id 72 was deleted from warehouse");
		}
	}
	
	public static void findProductPallet(){
		
		List<ProductPallet> searchedPallet = logisticUnitDAO.search("Food");
		System.out.println("The following pallets were found with the required description: ");
		for(ProductPallet pp : searchedPallet){
			System.out.println("---" + pp.getId() + "---" + pp.getDescription());
		}
	}
	
}
