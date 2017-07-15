
<%@ page import="java.util.*"%>

<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>One result page for all queries</title>
</head>
<body>

<%if(request.getAttribute("list") != null) {
    List<Object> lst = (List<Object>)request.getAttribute("list");
    Iterator<Object> iterator = lst.iterator();
    String message = (String) request.getAttribute("message");%>
<B><%=message%></B>
<table border=1>

    <%while (iterator.hasNext()) {%>
    <tr>
        <% Object element = iterator.next();%>
        <td> <%=element%> </td>
    </tr>
    <%}%>
</table>
<a href="index.jsp">Go to Home Page</a>
<%} else if (request.getAttribute("item") != null) {
    Object object = request.getAttribute("item");
    String message = (String) request.getAttribute("message");%>
<B><%=message%></B>
<table border=1>
    <tr>
        <td> <%=object%> </td>
    </tr>

</table>
<a href="index.jsp">Go to Home Page</a>
<%}%>
</body>
</html>