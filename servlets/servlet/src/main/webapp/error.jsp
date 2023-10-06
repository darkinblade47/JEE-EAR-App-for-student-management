<%@ page isErrorPage="true" %>
<%@ page import="monitorizare.MonitorThread" %>
<html xmlns:jsp="http://java.sun.com/JSP/Page">
<head>
    <title>Formular student</title>
    <meta charset="UTF-8"/>
</head>
<body>
<b>ERROR</b>
<br/>
<% out.println(MonitorThread.getInstance().cauzaEroare);%><br><br>
Cum doriti sa gestionati situatia?<br/>
Stergeti studentul?
<form action="./delete-student" method="post">
    <input type="hidden" name="id" value="<%=MonitorThread.getInstance().studentEronat.getId()%>">
    <button type="submit" name="submit">Stergere</button>
</form>
<br/>
Actualizati datele studentului? <br/>

<form action="./update-student" method="post">
    ID: <input type="number" name="id" value="<%=MonitorThread.getInstance().studentEronat.getId()%>" readonly/>
    <br/>
    Nume: <input type="text" name="nume" value="<%=MonitorThread.getInstance().studentEronat.getNume()%>"/>
    <br/>
    Prenume: <input type="text" name="prenume" value="<%=MonitorThread.getInstance().studentEronat.getPrenume()%>"/>
    <br/>
    Varsta: <input type="number" name="varsta" value="<%=MonitorThread.getInstance().studentEronat.getVarsta()%>"/>
    <br/>
    Medie: <input type="number" name="medie" value="<%=MonitorThread.getInstance().studentEronat.getMedie()%>"/>
    <br/>
    <button type="submit" name="submit">Actualizeaza</button>
</form>
</body>
</html>