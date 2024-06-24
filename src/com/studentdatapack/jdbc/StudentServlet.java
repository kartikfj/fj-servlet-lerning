package com.studentdatapack.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private StudentDbUtil studentDbUtil;

    @Resource(name = "jdbc/mysqlfjtcolocal")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();

        // Create our student db util and pass in the connection pool / datasource
        try {
            studentDbUtil = new StudentDbUtil(dataSource);
        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Read the command parameter
            String theCommand = request.getParameter("command");

            // If the command is missing, default to listing students
            if (theCommand == null) {
                theCommand = "LIST";
            }

            // Route to the appropriate method
            switch (theCommand) {
                case "LIST":
                    listStudents(request, response);
                    break;
                case "ADD":
                    addStudent(request, response);
                    break;
                default:
                    listStudents(request, response);
            }

        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Get students from db util
        List<Student> students = studentDbUtil.getStudents();

        // Add students to the request
        request.setAttribute("STUDENT_LIST", students);

        // Send to JSP page (view)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
        dispatcher.forward(request, response);
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Read student info from form data
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        // Create a new student object
        Student theStudent = new Student(firstName, lastName, email);

        // Add the student to the database
        studentDbUtil.addStudent(theStudent);

        // Send back to main page (student list)
        listStudents(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Delegate POST requests to the doGet method
        doGet(request, response);
    }
}
