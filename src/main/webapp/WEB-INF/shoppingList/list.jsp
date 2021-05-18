<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html>
<head>
<title>Shopping list</title>
<link rel="stylesheet"
	href="https://unpkg.com/sakura.css/css/sakura-ink.css" type="text/css">
</head>
<body>
	<h2>Shopping list eli ostoslista</h2>
	
	<form name="testi" action="/shop" method="post">
    	<input name="title" type="text" required placeholder="type item here..." autofocus /> <br />
    	<input type="submit" value="Add item to list" />
	</form>
	
	<table>
		<thead>
			<tr>
				<th>Product(s)</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ items }" var="s">
				<tr id="product-${s.getId()}">
					<td class="title"><c:out value="${ s.getId() } ${ s.getTitle() }"></c:out></td>
					<td><button onclick="removeProduct(${s.getId()})">× Remove</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<script>

	async function removeProduct(id) {

		await fetch(`/shop?id=` + id, { method: 'DELETE'});
		
		let deleteRow = document.getElementById("product-" + id);
		deleteRow.remove();
	}
	
</script>
</body>
</html>
