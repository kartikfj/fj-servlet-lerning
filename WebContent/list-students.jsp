<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.studentdatapack.jdbc.Student" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student List</title>
</head>
<body>
    <h2>Student List</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
        </tr>
        <%
            List<Student> students = (List<Student>) request.getAttribute("STUDENT_LIST");
            for (Student tempStudent : students) {
        %>
        <tr>
            <td><%= tempStudent.getId() %></td>
            <td><%= tempStudent.getFirstName() %></td>
            <td><%= tempStudent.getLastName() %></td>
            <td><%= tempStudent.getEmail() %></td>
        </tr>
        <% } %>
    </table>
    <br>
    <a href="add-student-form.jsp">Add New Student</a>
</body>
</html>
