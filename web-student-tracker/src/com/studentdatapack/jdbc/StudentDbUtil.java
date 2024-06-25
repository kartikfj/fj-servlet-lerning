package com.studentdatapack.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {
    private DataSource dataSource;

    public StudentDbUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

    // Method to get the list of students
    public List<Student> getStudents() throws Exception {
        List<Student> students = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            // Get a connection
            myConn = dataSource.getConnection();

            // Create SQL statement
            String sql = "SELECT * FROM student ORDER BY last_name";
            myStmt = myConn.createStatement();

            // Execute query
            myRs = myStmt.executeQuery(sql);

            // Process result set
            while (myRs.next()) {
                int id = myRs.getInt("id");
                String firstName = myRs.getString("first_name");
                String lastName = myRs.getString("last_name");
                String email = myRs.getString("email");

                Student tempStudent = new Student(id, firstName, lastName, email);

                students.add(tempStudent);
            }

            return students;
        } finally {
            // Close JDBC objects
            close(myConn, myStmt, myRs);
        }
    }

    // Method to add a new student
    public void addStudent(Student theStudent) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // Get db connection
            myConn = dataSource.getConnection();

            // Create SQL for insert
            String sql = "INSERT INTO student (first_name, last_name, email) VALUES (?, ?, ?)";
            myStmt = myConn.prepareStatement(sql);

            // Set the param values for the student
            myStmt.setString(1, theStudent.getFirstName());
            myStmt.setString(2, theStudent.getLastName());
            myStmt.setString(3, theStudent.getEmail());

            // Execute SQL insert
            myStmt.execute();
        } finally {
            // Clean up JDBC objects
            close(myConn, myStmt, null);
        }
    }

    public String updateStudent(Student theStudent) throws Exception{
    	Connection myConn=null;
    	PreparedStatement myStmt=null;
    	
    	
    	try {
    		myConn=dataSource.getConnection();
    		String sql="UPDATE student  SET first_name=?,last_name=?,email=? WHERE id=?";
    		
    		myStmt=myConn.prepareStatement(sql);
    		
    		myStmt.setString(1, theStudent.getFirstName());
    		myStmt.setString(2, theStudent.getLastName());
    		myStmt.setString(3, theStudent.getEmail());
    		 myStmt.setInt(4, theStudent.getId());
//    		myStmt.setString(4,theStudent.getId());
    		myStmt.executeUpdate();
    		
    		
    	}
    	finally {
    		close(myConn,myStmt,null);
    	}
    	return "updated";
    }
   
    public void deleteStudent(String id) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // Convert student id to int
            int studentId = Integer.parseInt(id);

            // Get connection to database
            myConn = dataSource.getConnection();

            // Create SQL to delete student
            String sql = "DELETE FROM student WHERE id=?";

            // Prepare statement
            myStmt = myConn.prepareStatement(sql);

            // Set the parameter
            myStmt.setInt(1, studentId);

            // Execute SQL statement
            myStmt.execute();
        } finally {
            // Clean up JDBC objects
            close(myConn, myStmt, null);
        }
    }
    public Student getStudent(String id) throws Exception {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        Student theStudent = null;

        try {
            // Convert student id to int
            int studentId = Integer.parseInt(id);

            // Get connection to database
            myConn = dataSource.getConnection();

            // Create SQL to select student by id
            String sql = "SELECT * FROM student WHERE id=?";

            // Prepare statement
            myStmt = myConn.prepareStatement(sql);

            // Set the parameter
            myStmt.setInt(1, studentId);

            // Execute the query
            myRs = myStmt.executeQuery();

            // Process the result set
            if (myRs.next()) {
                String firstName = myRs.getString("first_name");
                String lastName = myRs.getString("last_name");
                String email = myRs.getString("email");

                // Create a new Student object
                theStudent = new Student(studentId, firstName, lastName, email);
            } else {
                throw new Exception("Could not find student id: " + studentId);
            }

            return theStudent;
        } finally {
            // Clean up JDBC objects
            close(myConn, myStmt, myRs);
        }
    }

    
       // Utility method to close JDBC objects
    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try {
            if (myRs != null) {
                myRs.close();
            }
            if (myStmt != null) {
                myStmt.close();
            }
            if (myConn != null) {
                myConn.close(); // Return to connection pool
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
