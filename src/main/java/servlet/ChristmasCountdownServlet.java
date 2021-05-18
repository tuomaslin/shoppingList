package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/christmas")
public class ChristmasCountdownServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		LocalDate today = LocalDate.now();
		LocalDate christmas = LocalDate.of(today.getYear(), Month.DECEMBER, 24);
		
		long daysBetween = ChronoUnit.DAYS.between(today, christmas);

		req.setAttribute("daysBetween", daysBetween);
		
		req.getRequestDispatcher("/WEB-INF/christmas.jsp").forward(req, resp);
	}
}
