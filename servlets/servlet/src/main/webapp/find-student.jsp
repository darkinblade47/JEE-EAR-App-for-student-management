<html xmlns:jsp="http://java.sun.com/JSP/Page">
<head>
    <title>Actualizare student</title>
    <meta charset="UTF-8"/>
</head>
<body>
<h3>Formular cautare studenti</h3>
Introduceti datele de cautare:
<form action="./find-student" method="get">
    Criteriu:
    <select name="criteriu" id="criteriu">
        <option value="nume">Nume</option>
        <option value="prenume">Prenume</option>
    </select>
    <input type="text" id="text" name="text"/>
    <br/>
    <br/>
    <button type="submit" name="submit">Cauta</button>
</form>
</body>
</html>