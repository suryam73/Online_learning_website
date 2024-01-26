package website;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Validate
 */
@WebServlet("/Validate")
public class Validate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int flag = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/samplelogin", "root", "12341234");
            java.sql.Statement ps = con.createStatement();
            ResultSet rs = ps.executeQuery("select * from login");

            String name = request.getParameter("user"); // Replace "username" with the actual parameter name
            String mail = request.getParameter("mail"); // Replace "email" with the actual parameter name
            String pass = request.getParameter("pass"); // Replace "password" with the actual parameter name

            while (rs.next()) {
                if (rs.getString(1).equals(name) && rs.getString(2).equals(mail) && rs.getString(3).equals(pass)) {
                    flag = 1;
                    break;
                }
            }

            if (flag == 1) {
                RequestDispatcher rd = request.getRequestDispatcher("index.html");
                rd.forward(request, response);
            } else {
                response.sendRedirect("login.html");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}