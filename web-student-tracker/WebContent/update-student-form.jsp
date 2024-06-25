<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Student</title>
</head>
<body>
    <h2>Update Student</h2>
    <form action="StudentServlet" method="post">
        <input type="hidden" name="command" value="UPDATE">
        StudentId<input type="text" name="studentId" value="${THE_STUDENT.id}">
        First Name: <input type="text" name="firstName" value="${THE_STUDENT.firstName}"><br>
        Last Name: <input type="text" name="lastName" value="${THE_STUDENT.lastName}"><br>
        Email: <input type="text" name="email" value="${THE_STUDENT.email}"><br>
        <input type="submit" value="Update Student">
    </form>
    <br>
    <a href="StudentServlet?command=LIST">Back to Student List</a>
</body>
</html>
