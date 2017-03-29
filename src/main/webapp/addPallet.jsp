<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Pallet</title>
<script>
function validateForm() {
	for(var i=0; i<document.forms["addForm"].length; i++){
		var x = document.forms["addForm"][i].value;
	    if (x == "") {
	        alert("Field " + document.forms["addForm"][i].name + " must be filled out");
	        return false;
	    }
	}
}
</script>
</head>
<body>
	<div align="center">
	<form name="addForm" action="warehouseOperations" onsubmit="return validateForm()" method="POST">
		<h1>Add new Pallet</h1>
			Pallet description: <input type="text" name="description"><br>
			<br>
  			Package1 type: <input type="text" name="package1Type"><br>
  			Package1 description: <input type="text" name="package1Description"><br>
  			<br>
  			Package2 type: <input type="text" name="package2Type"><br>
  			Package2 description: <input type="text" name="package2Description"><br>
			<br>
			<input type="hidden" name="action" value="addPallet"/>
			<input type="submit" value="Submit">
			
	</form>
	</div>
</body>
</html>