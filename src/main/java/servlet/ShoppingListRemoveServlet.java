package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.JDBCShoppingListItemDao;
import database.ShoppingListItemDao;
import model.ShoppingListItem;

@WebServlet("/remove")
public class ShoppingListRemoveServlet extends HttpServlet {

	private final ShoppingListItemDao dao = new JDBCShoppingListItemDao();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ShoppingListItem newItem = new ShoppingListItem(req.getParameter("title"));
		dao.removeItem(newItem);
		resp.sendRedirect("/shop");
	}
	
}