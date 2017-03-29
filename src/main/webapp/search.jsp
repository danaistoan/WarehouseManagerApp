<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search the warehouse</title>
<script>
function validateForm() {
    var x = document.forms["searchForm"]["searchParameter"].value;
    if (x == "") {
        alert("Search field must be filled out");
        return false;
    }
}
</script>
</head>
<body>
	<div align="center">
	<h1>Search the Warehouse</h1>
	<form name="searchForm" action="warehouseOperations" onsubmit="return validateForm()" method="GET">
		Insert description parameter: <input type="text" name="searchParameter"> 
			<br> <br>
			<input type="hidden" name="action" value="search"/>
			<input type="submit" value="Submit" />
	</form>
	</div>
</body>
</html>