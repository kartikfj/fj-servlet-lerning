<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.studentdatapack.jdbc.Student" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student List</title>
    <style>
        table {
            width: 80%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h2>Student List</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Actions</th>
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
            <td>
                <!-- Edit Button (Optional) -->
                <form action="StudentServlet" method="get" style="display:inline;">
                    <input type="hidden" name="command" value="LOAD">
                    <input type="hidden" name="studentId" value="<%= tempStudent.getId() %>">
                    <input type="submit" value="Edit">
                </form>

                <!-- Delete Button -->
                <form action="StudentServlet" method="post" style="display:inline;">
                    <input type="hidden" name="command" value="DELETE">
                    <input type="hidden" name="studentId" value="<%= tempStudent.getId() %>">
                    <input type="submit" value="Delete" onclick="return confirm('Are you sure you want to delete this student?');">
                </form>
            </td>
        </tr>
        <% } %>
    </table>
    <br>
    <a href="add-student-form.jsp">Add New Student</a>
</body>
</html>
