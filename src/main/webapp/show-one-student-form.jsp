<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Show one student form</title>
</head>
<body>

<form name="Form1"
      method="get"
      action="http://localhost:8080/CollegeRecords/modify">
    <table>
        <tr>
            <td><B>Enter student ID</B></td>
            <td><input type=number name="show_student_ID" size="25" value=""></td>
        </tr>
    </table>
    <input type=submit name="action" value="Show student">
</form>

<a href="index.jsp">Go to Home Page</a>

</body>
</html>