package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/date")
public class DaysUntilServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.getRequestDispatcher("/WEB-INF/dayHtml/input.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		LocalDate today = LocalDate.now();
		int day = Integer.parseInt(req.getParameter("day"));
		int month = Integer.parseInt(req.getParameter("month"));
		int year = Integer.parseInt(req.getParameter("year"));
		
		long daysBetween = ChronoUnit.DAYS.between(today, LocalDate.of(year, month, day));
		
		if (daysBetween < 0) {
			long positive = Math.abs(daysBetween);
			req.setAttribute("positive", positive);
			req.getRequestDispatcher("/WEB-INF/dayHtml/negative.jsp").forward(req, resp);
		} else {
		req.setAttribute("daysBetween", daysBetween);
		req.getRequestDispatcher("/WEB-INF/dayHtml/result.jsp").forward(req, resp);
		}
	}
	
}