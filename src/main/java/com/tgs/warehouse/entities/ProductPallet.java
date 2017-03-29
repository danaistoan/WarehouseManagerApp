package com.tgs.warehouse.entities;

import java.util.ArrayList;
import java.util.List;

public class ProductPallet {

	private Long id;
	private String description;
	private List<ProductPackage> packages = new ArrayList<ProductPackage>();
	
	public ProductPallet(){
	}
	
	public ProductPallet(Long id, String description){
		this.id = id;
		this.description = description;
	}
	
	public ProductPallet(String description, List<ProductPackage> packages){
		this.description = description;
		this.packages = packages;
	}

	public void setId(Long id) {
		this.id = id;		
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ProductPackage> getPackages() {
		return packages;
	}

	public void setPackages(List<ProductPackage> packages) {
		this.packages = packages;
	}	
	
	
}
