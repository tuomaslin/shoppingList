package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.JDBCShoppingListItemDao;
import database.ShoppingListItemDao;
import model.ShoppingListItem;

@WebServlet("/shop")
public class ShoppingListServlet extends HttpServlet {
	
	private final ShoppingListItemDao dao = new JDBCShoppingListItemDao();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<ShoppingListItem> allItems = dao.listAllItems();
		
		req.setAttribute("items", allItems);
		req.getRequestDispatcher("/WEB-INF/shoppingList/list.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ShoppingListItem newItem = new ShoppingListItem(req.getParameter("title"));
		dao.addItem(newItem);
		resp.sendRedirect("/shop");
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		long idd = Long.parseLong(req.getParameter("id"));
		ShoppingListItem toDelete = dao.getItem(idd);

		dao.removeItem(toDelete);
	}
}


