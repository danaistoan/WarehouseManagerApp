package com.tgs.warehouse.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.tgs.warehouse.connection.DBConnection;
import com.tgs.warehouse.entities.ProductPackage;
import com.tgs.warehouse.entities.ProductPallet;

public class LogisticUnitDAO {

	public ProductPallet insertProductPallet(ProductPallet productPallet) {

		String sqlInsert = "INSERT INTO product_pallet (description) VALUES (?)";

		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement prepStmt = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {

			connection.setAutoCommit(false);

			prepStmt.setString(1, productPallet.getDescription()); 
			prepStmt.execute();

			ResultSet rs = prepStmt.getGeneratedKeys();
			long generatedKey = 0;
			if (rs.next()) {
				generatedKey = rs.getLong(1);
				System.out.println("New pallet id is: " + generatedKey);

				productPallet.setId(generatedKey);

				for (ProductPackage productPackage : productPallet.getPackages()) {
					insertProductPackage(productPackage, productPallet.getId(), connection);
				}
			}

			if (generatedKey == 0) {
				connection.rollback();
				throw new RuntimeException("Creating pallet failed, no rows affected!");
			}

			connection.commit();

		} catch (SQLException e) {
			System.out.println("Could not create statemet...");
			e.printStackTrace();
			return null;
		}

		return productPallet;
	}

	public ProductPackage insertProductPackage(ProductPackage productPackage, Long palletId, Connection connection) {

		String sqlInsert = "INSERT INTO product_package (description, type, product_pallet_id) VALUES (?, ?, ?)";

		try (PreparedStatement prepStmt = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {

			prepStmt.setString(1, productPackage.getDescription());
			prepStmt.setString(2, productPackage.getType());
			prepStmt.setLong(3, palletId);
			prepStmt.execute();

			ResultSet rs = prepStmt.getGeneratedKeys();
			long generatedKey = 0;
			if (rs.next()) {
				generatedKey = rs.getLong(1);
				System.out.println("New package id is: " + generatedKey);

				productPackage.setId(generatedKey);
			}

			if (generatedKey == 0) {
				connection.rollback();
				throw new RuntimeException("Creating package failed, no rows affected!");
			}

		} catch (SQLException e) {
			System.out.println("Could not create statemet...");
			e.printStackTrace();
			return null;
		}

		return productPackage;
	}

	public boolean deleteProductPallet(Long productPalletId) {

		String sqlDelete = "DELETE FROM product_pallet WHERE id = ?";

		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement prepStmt = connection.prepareStatement(sqlDelete)) {
			connection.setAutoCommit(false);

			removeProductPackagesFromPallet(productPalletId, connection);

			prepStmt.setLong(1, productPalletId);
			int rowsAffected = prepStmt.executeUpdate();

			if (rowsAffected == 0) {
				System.out.println("Could not find the pallet to be deleted...");
				return false;
			}

			connection.commit();
			System.out.println("Pallet deleted from warehouse");

		} catch (SQLException e) {
			System.out.println("Could not create statemet...");
			e.printStackTrace();
		}

		return true;
	}

	public List<ProductPallet> search(String description) {

		List<ProductPallet> palletList = new ArrayList<ProductPallet>();

		String sqlSearch = "SELECT DISTINCT product_pallet.id, product_pallet.description FROM product_pallet INNER JOIN "
				+ "product_package ON product_pallet.id = product_package.product_pallet_id "
				+ "WHERE LOWER(product_pallet.description) LIKE LOWER(?) "
				+ "OR LOWER(product_package.description) LIKE LOWER(?)";

		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement prepStmt = connection.prepareStatement(sqlSearch)) {

			prepStmt.setString(1, "%" + description +  "%");
			prepStmt.setString(2, "%" + description +  "%");

			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				ProductPallet productPallet = new ProductPallet(rs.getLong("id"), rs.getString("description"));
				palletList.add(productPallet);
			}

			return palletList;

		} catch (SQLException e) {

			throw new RuntimeException("Cannot return pallets from DB", e);
		}

	}

	public List<ProductPallet> getAllPallets() {

		List<ProductPallet> palletList = new ArrayList<ProductPallet>();

		String sqlSearch = "SELECT product_pallet.id, product_pallet.description FROM product_pallet ORDER BY product_pallet.id";

		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement prepStmt = connection.prepareStatement(sqlSearch)) {

			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				ProductPallet productPallet = new ProductPallet(rs.getLong("id"), rs.getString("description"));
				palletList.add(productPallet);
			}

			return palletList;

		} catch (SQLException e) {

			throw new RuntimeException("Cannot return pallets from DB", e);
		}

	}

	public List<ProductPackage> loadPackagesByPalletId(Long palletId) {

		List<ProductPackage> packageList = new ArrayList<ProductPackage>();
		
		String sql = "SELECT product_package.id, product_package.description, product_package.type "
							+ "FROM product_package LEFT OUTER JOIN product_pallet "
							+ "ON product_package.product_pallet_id = product_pallet.id "
							+ "WHERE product_package.product_pallet_id = ?";
		
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement prepStmt = connection.prepareStatement(sql)) {

			prepStmt.setLong(1, palletId);

			ResultSet rs = prepStmt.executeQuery();
			
			while(rs.next()){
				ProductPackage productPackage = new ProductPackage();
				productPackage.setId(rs.getLong("id"));
				productPackage.setDescription(rs.getString("description"));
				productPackage.setType(rs.getString("type"));
				packageList.add(productPackage);
			}
			
		} catch (SQLException e) {
			System.out.println("Could not create statemet...");
			e.printStackTrace();
			return null;
		}

		return packageList;
	}

	public ProductPallet loadPalletById(Long palletId){
		
		ProductPallet pallet = new ProductPallet();
		
		String sqlSearch = "SELECT product_pallet.id, product_pallet.description FROM product_pallet "
							+ "WHERE product_pallet.id = ?";

		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement prepStmt = connection.prepareStatement(sqlSearch)) {

			prepStmt.setLong(1, palletId);
			ResultSet rs = prepStmt.executeQuery();

			if (rs.next()) {
				pallet = new ProductPallet(rs.getLong("id"), rs.getString("description"));
			}

			return pallet;

		} catch (SQLException e) {

			throw new RuntimeException("Cannot return pallets from DB", e);
		}
	}

	public void removeProductPackagesFromPallet(Long productPalletId, Connection connection) {

		String sqlUpdate = "UPDATE product_package SET product_pallet_id = null WHERE product_pallet_id = ?";

		try (PreparedStatement prepStmt = connection.prepareStatement(sqlUpdate)) {

			prepStmt.setLong(1, productPalletId);

			prepStmt.addBatch();

			int[] rowsAffected = prepStmt.executeBatch();

			if (rowsAffected == null) {
				connection.rollback();
				throw new RuntimeException("Updating package failed, no rows affected!");
			}
			connection.commit();
			System.out.println("Updated FK for packages");

		} catch (SQLException e) {
			System.out.println("Could not create statemet...");
			e.printStackTrace();
		}
	}
}
