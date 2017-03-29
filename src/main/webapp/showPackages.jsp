<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Packages</title>
</head>
<body>
<div align="center">
        <table border="1" cellpadding="5">
            <caption><h1>List of Packages for Pallet <c:out value="${requestScope.palletDescription}"/></h1></caption>
            <tr>
                <th>Id</th>
                <th>Description</th>
                <th>Type</th>
            </tr>
            <c:forEach var="productPackage" items="${packageList}">
                <tr>
                    <td><c:out value="${productPackage.id}" /></td>
                    <td><c:out value="${productPackage.description}" /></td>
                    <td><c:out value="${productPackage.type}" /></td>
                </tr>
            </c:forEach>
        </table>
        	<br>
        	<button onclick="window.history.back()">Back to pallets <br>
			<!-- <form action="warehouseOperations" method="GET">
			<input type="submit" name="showAllPallets" value="Back to pallets" />
			</form> -->
    </div>
</body>
</html>