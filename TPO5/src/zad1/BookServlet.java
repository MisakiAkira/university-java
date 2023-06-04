package zad1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class BookServlet extends HttpServlet {
    String url = "jdbc:mysql://localhost/booksdb";
    String uid = "root";
    String pwd = "Jr6#k+S7";
    Connection con;

    public void init() throws ServletException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, uid, pwd);
        } catch(Exception exc){
            throw new ServletException("No database connection", exc);
        }
    }

    public void serviceRequest(HttpServletRequest req,
                               HttpServletResponse resp)
        throws ServletException, IOException{
        resp.setContentType("text/html; charset=windows-1250");
        PrintWriter out = resp.getWriter();
        out.println("<h2>List of books</h2>");
        String sel = "SELECT * FROM books";
        out.println("<ol>");
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sel);
            while(rs.next()){
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year");
                out.println("<li>" + id + " " + title + ", author: " + author
                + ", year: " + year + "</li>");
            }
            rs.close();
            stmt.close();
        } catch (SQLException exc){
            out.println(exc.getMessage());
        }
        out.close();
    }

    public void destroy(){
        try{
            con.close();
        } catch (Exception ignored){

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serviceRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        serviceRequest(req, resp);
    }
    //
}
