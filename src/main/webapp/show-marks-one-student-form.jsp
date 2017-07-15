<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Show add new student form</title>
</head>
<body>

<form name="Form13"
      method="get"
      action="http://localhost:8080/CollegeRecords/modify">
    <table>
        <tr>
            <td><B>To see all marks of one student, enter student ID</B></td>
            <td><input type=number name="show_all_marks_1stud" size="25" value=""></td>
        </tr>
    </table>
    <input type=submit name="action" value="Show student's marks">
</form>

<a href="index.jsp">Go to Home Page</a>

</body>
</html>