<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Show add new student form</title>
</head>
<body>

<form name="Form10"
      method="get"
      action="http://localhost:8080/CollegeRecords/modify">
    <table>
        <tr>
            <td><B>To delete existing subject, enter subject's ID</B></td>
            <td><input type=number name="delete_subject_ID" size="25" value=""></td>
        </tr>
    </table>
    <input type=submit name="action" value="Delete subject">
</form>

<a href="index.jsp">Go to Home Page</a>

</body>
</html>