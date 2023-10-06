<%@ page import="monitorizare.MonitorThread" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Meniu principal</title>
    <meta charset="utf-8"/>
</head>
<body>
<h1>Meniu principal</h1>
<hr/>
<h3>Gestiune cont bancar</h3>
<form action="./process-bank-operation" method="post">
    <fieldset label="operatiuni">
        <legend>Alegeti operatiunea dorita:</legend>
        <select name="operation">
            <option value="deposit">Depunere numerar</option>
            <option value="withdraw">Retragere numerar</option>
            <option value="balance">Interogare sold</option>
        </select>
        <br/>
        <br/>
        Introduceti suma: <input type="number" name="amount"/>
        <br/>
        <br/>
        <button type="submit">Efectuare</button>
    </fieldset>
</form>
<hr/>
<h3>Baza de date cu studenti</h3>
<form action="./set-parameters" method="post">
    <label for="minVarsta">Varsta minima pentru student:</label>
    <input type="number" id="minVarsta" name="minVarsta"><br><br>
    <label for="maxVarsta">Varsta maxima pentru student:</label>
    <input type="number" id="maxVarsta" name="maxVarsta"><br><br>
    <label for="minMedie">Media minima pentru promovare:</label>
    <input type="number" id="minMedie" name="minMedie"><br><br>
    <label for="maxMedie">Media maxima pentru promovare:</label>
    <input type="number" id="maxMedie" name="maxMedie"><br><br>
    <button type="submit" name="submit">Seteaza</button>
</form>
<% Thread t = new Thread(monitorizare.MonitorThread.getInstance());
    t.start();
    if (MonitorThread.getInstance().shouldStop) {
        MonitorThread.getInstance().shouldStop = false;
        String url = "./error.jsp";
        response.sendRedirect(url);
        t.interrupt();
        MonitorThread.getInstance().state = 0;
    }
%>

<hr/>
<h3>Baza de date cu studenti</h3>
<a href="./formular.jsp">Adaugare student</a>
<br/>
<a href="./fetch-student-list">Afisare lista studenti</a>
<br/>
<a href="./find-student.jsp">Cautare studenti pentru actualizare/stergere</a>
</body>
</html>