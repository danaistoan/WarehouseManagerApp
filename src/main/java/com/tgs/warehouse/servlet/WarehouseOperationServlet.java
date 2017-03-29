package com.tgs.warehouse.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tgs.warehouse.dao.LogisticUnitDAO;
import com.tgs.warehouse.entities.*;
import com.tgs.warehouse.services.WarehouseService;

/**
 * Servlet implementation class WarehouseOperationServlet
 */
@WebServlet("/warehouseOperations")
public class WarehouseOperationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WarehouseOperationServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		
		if(action != null){
			
			LogisticUnitDAO logisticUnit = new LogisticUnitDAO();

			switch (action) {
			
				case "showSearch":
					response.sendRedirect("search.jsp");
					break;
	
				case "search":
					searchPalletByDescription(request, response, logisticUnit);
					break;
					
				case "all":
					showAllPallets(request, response, logisticUnit);
					break;
					
				case "showPackages":
					showPackagesForPallet(request, response, logisticUnit);
					break;
					
				case "addPallet":
					response.sendRedirect("addPallet.jsp");
					break;
					
				default:
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action param!");
					break;
			}
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action param!");
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		LogisticUnitDAO logisticUnit = new LogisticUnitDAO();
		
		switch(action) {
		
			case "deletePallet":
				deletePallet(request, response, logisticUnit);
				break;
				
			case "addPallet":
				addPallet(request, response, logisticUnit);
				break;
				
			default:
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action param!");
				break;
		}		
	}	
	
	private void searchPalletByDescription(HttpServletRequest request, HttpServletResponse response, LogisticUnitDAO logisticUnit) 
			throws ServletException, IOException{
		
		String description = request.getParameter("searchParameter");
		
		if (description != null) {
			List<ProductPallet> foundPalletList = logisticUnit.search(description);
			System.out.println("There were found " + foundPalletList.size() + " pallets");
			
			request.setAttribute("palletList", foundPalletList);
			request.setAttribute("searchParameter", description);
			request.getRequestDispatcher("/showAllPallets.jsp").forward(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing searchParameter keyword!");
		}
	}	
	
	private void showAllPallets(HttpServletRequest request, HttpServletResponse response, LogisticUnitDAO logisticUnit) 
			throws ServletException, IOException{
		
		List<ProductPallet> palletList = logisticUnit.getAllPallets();
		System.out.println("There are a total of " + palletList.size() + " pallets");
		
		request.setAttribute("palletList", palletList);
		request.getRequestDispatcher("/showAllPallets.jsp").forward(request, response);
	}	
	
	private void showPackagesForPallet(HttpServletRequest request, HttpServletResponse response, LogisticUnitDAO logisticUnit) 
			throws ServletException, IOException{
		
		Long palletId = Long.parseLong(request.getParameter("palletId"));
		List<ProductPackage> packageList = logisticUnit.loadPackagesByPalletId(palletId);
		ProductPallet pallet = logisticUnit.loadPalletById(palletId);
		String palletDescription = pallet.getDescription();
		
		request.setAttribute("palletDescription", palletDescription);
		request.setAttribute("packageList", packageList);
		request.getRequestDispatcher("/showPackages.jsp").forward(request, response);
	}
	
	private void deletePallet(HttpServletRequest request, HttpServletResponse response, LogisticUnitDAO logisticUnit) 
			throws ServletException, IOException{
		
		Long palletId = Long.parseLong(request.getParameter("palletId"));
		boolean palletDeleted = logisticUnit.deleteProductPallet(palletId);
		
		if (palletDeleted) {
			System.out.println("The pallet with id " + palletId + " was deleted from the warehouse");
			List<ProductPallet> newPalletList;
			
			if (request.getParameter("searchParameter") != null){
				newPalletList = logisticUnit.search(request.getParameter("searchParameter"));
			} else {
				newPalletList = logisticUnit.getAllPallets();	
			}
			
			System.out.println("There are a total of " + newPalletList.size() + " pallets");
			request.setAttribute("palletList", newPalletList);
			request.getRequestDispatcher("/showAllPallets.jsp").forward(request, response);
		} else {
			System.out.println("The pallet could not be deleted");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/deletePalletFailure.jsp");
			dispatcher.forward(request, response);
		} 
	}
	
	private void addPallet(HttpServletRequest request, HttpServletResponse response, LogisticUnitDAO logisticUnit) 
			throws ServletException, IOException{
		
		String palletDesc = request.getParameter("description");
		String package1Type = request.getParameter("package1Type");
		String package1Desc = request.getParameter("package1Description");
		String package2Type = request.getParameter("package2Type");
		String package2Desc = request.getParameter("package2Description");

		WarehouseService wService = new WarehouseService();
		ProductPallet palletSaved = wService.saveProductPallet(palletDesc, package1Type, package1Desc, package2Type, package2Desc);
		System.out.println("Am introdus un palet nou");
		
		if (palletSaved != null) {
			List<ProductPallet> newPalletList = logisticUnit.getAllPallets();
			System.out.println("There are a total of " + newPalletList.size() + " pallets");
			request.setAttribute("palletList", newPalletList);
			request.getRequestDispatcher("/showAllPallets.jsp").forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/addPalletFailure.jsp");
			dispatcher.forward(request, response);
		}
	}
}
