<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Student</title>
</head>
<body>
    <h2>Add New Student</h2>
    <form action="StudentServlet" method="post">
        <input type="hidden" name="command" value="ADD">
        First Name: <input type="text" name="firstName"><br>
        Last Name: <input type="text" name="lastName"><br>
        Email: <input type="text" name="email"><br>
        <input type="submit" value="Add Student">
    </form>
    <br>
    <a href="StudentServlet?command=LIST">Back to Student List</a>
</body>
</html>
