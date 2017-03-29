<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pallets List</title>
</head>
<body>
	<div align="center">
		<table border="1" cellpadding="5">
			<caption>
				<h1>List of Pallets</h1>
			</caption>
			<tr>
				<th>Id</th>
				<th>Description</th>
			</tr>
			<c:forEach var="pallet" items="${palletList}">
				<tr>
					<td><c:out value="${pallet.id}" /></td>
					<td><c:out value="${pallet.description}" /></td>
					<td>
						<form action="warehouseOperations" method="GET">
							<input type="hidden" name="action" value="showPackages"/>
							<input type="hidden" name="palletId" id="palletId" value="${pallet.id}"/> 
							<input type="submit" value="Show packages"/>
						</form>
					</td>
					<td>
						<form action="warehouseOperations" method="POST">
							<input type="hidden" name="action" value="deletePallet"/>
							<input type="hidden" name="palletId" id="palletId" value="${pallet.id}"/> 
							<input type="hidden" name="searchParameter" value="<c:out value="${requestScope.searchParameter}"/>" />
							<input type="submit" value="Delete"/>
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
		<br>
		<form action="warehouseOperations" method="GET">
			<input type="hidden" name="action" value="addPallet"/>
			<input type="submit" value="Add new pallet" />
		</form>
	</div>
</body>
</html>