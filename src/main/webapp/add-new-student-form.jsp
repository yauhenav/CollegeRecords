<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Show add new student form</title>
</head>
<body>

<form name="Form3"
      method="get"
      action="http://localhost:8080/CollegeRecords/modify">
    <table>
        <tr>
            <td><B>Enter student attributes to add new student into DB</B></td>
        </tr>
        <tr>
            <td><B>Enter student ID</B></td>
            <td><input type=number name="new_student_ID" size="25" value=""></td>
        </tr>
        <tr>
            <td><B>Enter student name</B></td>
            <td><input type=text name="new_student_name" size="25" value=""></td>
        </tr>
        <tr>
            <td><B>Enter student surname</B></td>
            <td><input type=text name="new_student_surname" size="25" value=""></td>
        </tr>
    </table>
    <input type=submit name="action" value="Add new student">
</form>

<a href="index.jsp">Go to Home Page</a>

</body>
</html>