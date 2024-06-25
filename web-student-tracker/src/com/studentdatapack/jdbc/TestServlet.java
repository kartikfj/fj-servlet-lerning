package com.studentdatapack.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Define DataSource/Connection Pool for Resource Injection
    @Resource(name = "jdbc/mysqlfjtcolocal")
    private DataSource dataSource;

    public TestServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set the response content type
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            // Get a connection from the connection pool
            myConn = dataSource.getConnection();

            // Create a SQL statement
            String sql = "SELECT * FROM student";
            myStmt = myConn.createStatement();

            // Execute the SQL statement
            myRs = myStmt.executeQuery(sql);

            // Process the result set
            while (myRs.next()) {
//                int id = myRs.getInt("id");
                String email = myRs.getString("email");
//                int age = myRs.getInt("age");
//                String grade = myRs.getString("grade");

                out.println("email: " + email); //+ ", Name: " + name + ", Age: " + age + ", Grade: " + grade);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            out.println("Error: " + exc.getMessage());
        } finally {
            // Close the resources
            try {
                if (myRs != null) myRs.close();
                if (myStmt != null) myStmt.close();
                if (myConn != null) myConn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Delegate POST requests to the doGet method
        doGet(request, response);
    }
}
