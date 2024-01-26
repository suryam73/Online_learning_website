package website;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Deleteid
 */
@WebServlet("/Deleteid")
public class Deleteid extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/samplelogin";
        String username = "root";
        String password = "12341234";

        // SQL query for deletion
        String deleteQuery = "DELETE FROM login WHERE pass = ?";

        // Retrieve parameters from the request
        String passToDelete = request.getParameter("num");

        // Set the response type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            
            // Create a prepared statement for the DELETE query
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        ) {
            // Set the parameter in the prepared statement
            preparedStatement.setString(1, passToDelete);

            // Execute the DELETE query
            int rowsAffected = preparedStatement.executeUpdate();

            // Display the results
            out.println("<html><body>");
            out.println("<h2>" + rowsAffected + " row(s) deleted.</h2>");
            out.println("</body></html>");

        } catch (SQLException e) {
            // Handle database-related exceptions
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h2>Error occurred while deleting the record.</h2>");
            out.println("</body></html>");
        }
    }
}