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

@WebServlet("/edit")
public class EditRecordServlet extends HttpServlet {
	private final static String query = "update ticket set stdid=?,name=?,module=?,sem=?,comment=?,date=? where id=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		PrintWriter pw = res.getWriter();

		res.setContentType("text/html");
	
		pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
	
		int id = Integer.parseInt(req.getParameter("id"));
		
		
		String stdid = req.getParameter("stdid");
		String name = req.getParameter("name");
		String module = req.getParameter("module");
		String sem = req.getParameter("sem");
		String comment = req.getParameter("comment");
		String date = req.getParameter("date");
		
	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Ticket_Management", "root", "root");
					PreparedStatement ps = con.prepareStatement(query);){
		
			ps.setString(1, stdid);
			ps.setString(2, name);
			ps.setString(3, module);
			ps.setString(4, sem);
			ps.setString(5, comment);
			ps.setString(6, date);
			ps.setInt(7, id);
		
			int count = ps.executeUpdate();
			pw.println("<div class='card' style='margin:auto;width:300px;margin-top:100px'>");
			if(count==1) {
				pw.println("<h2 class='bg-danger text-light text-center'>Ticket Edited Successfully</h2>");
			}else {
				pw.println("<h2 class='bg-danger text-light text-center'>Ticket Not Edited</h2>");
			}
		}catch(SQLException se) {
			pw.println("<h2 class='bg-danger text-light text-center'>"+se.getMessage()+"</h2>");
			se.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		pw.println("<a href='home.html'><button class='btn btn-outline-success'>Home</button></a>");
		pw.println("&nbsp; &nbsp;");
		pw.println("<a href='showdata'><button class='btn btn-outline-success'>Show Ticket</button></a>");
		pw.println("</div>");

		pw.close();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}
}
