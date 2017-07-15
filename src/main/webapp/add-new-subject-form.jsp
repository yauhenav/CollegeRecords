<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Show add new student form</title>
</head>
<body>

<form name="Form8"
      method="get"
      action="http://localhost:8080/CollegeRecords/modify">
    <table>
        <tr>
            <td><B>Enter subject attributes to add new subject into DB</B></td>
        </tr>
        <tr>
            <td><B>Enter subject ID</B></td>
            <td><input type=number name="new_subject_ID" size="25" value=""></td>
        </tr>
        <tr>
            <td><B>Enter subject description</B></td>
            <td><input type=text name="new_subject_description" size="25" value=""></td>
        </tr>
    </table>
    <input type=submit name="action" value="Add new subject">
</form>

<a href="index.jsp">Go to Home Page</a>

</body>
</html>