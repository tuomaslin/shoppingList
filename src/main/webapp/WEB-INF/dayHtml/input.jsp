<!doctype html>
<html>
	<head>
		<title>How many days till given date?</title>
		<link rel="stylesheet" href="https://unpkg.com/sakura.css/css/sakura-ink.css" type="text/css">
	</head>
<body>
	<h2>Calculate how many dates there is between today and given date</h2>
		<div>
			<form action="/date" method="post">
				<label>Day: <input type="number" name="day" min="1" max="31"></label>
				<label>Month: <input type="number" name="month" min="1" max="12"></label>
				<label>Year: <input type="number" name="year"></label>
				<input type="submit" value="Calculate">
			</form>
		</div>
</body>
</html>