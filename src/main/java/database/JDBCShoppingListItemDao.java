package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.ShoppingListItem;


	public class JDBCShoppingListItemDao implements ShoppingListItemDao {
		
		public static final String JDBC_URL = System.getenv("JDBC_DATABASE_URL");

	    public Connection connect() throws SQLException {
	        return DriverManager.getConnection(JDBC_URL);
	    }

	    @Override
	    public List<ShoppingListItem> listAllItems() {
			
	    	List <ShoppingListItem> items = new ArrayList<>();
	    	
	    	 try (Connection connection = connect();
	    	    PreparedStatement statement = connection.prepareStatement("SELECT * FROM ShoppingListItem");
	    	    ResultSet results = statement.executeQuery()) {
	    	
	    	while (results.next()) {
	    		long id = results.getLong("id");
	    		String title = results.getString("title");
	    		ShoppingListItem newItem = new ShoppingListItem(id, title);
	    		items.add(newItem);
	    	}
	    	 
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    return items;
	    }

	    @Override
		public ShoppingListItem getItem(long id) {
	    
			ShoppingListItem searchResult = new ShoppingListItem(id, null);

			try (Connection connection = connect();
					PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM ShoppingListItem WHERE id = ?");
					ResultSet results = findStatement.executeQuery()) {

				findStatement.setLong(1, id);
				findStatement.executeQuery();

				if (results.next()) {
					long resultId = results.getLong("id");
					String title = results.getString("title");
					searchResult = new ShoppingListItem(resultId, title);
				} else {
					return null;
				}
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();

			}
			return searchResult;

		}

	    @Override
	    public boolean addItem(ShoppingListItem newItem) {
	   
	    String i = newItem.getTitle();
	    boolean b = false;
	   	 
	   	 try (Connection connection = connect();
	   		 PreparedStatement addStatement = connection.prepareStatement("INSERT INTO ShoppingListItem (title) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
	   		 PreparedStatement checkStatement= connection.prepareStatement ("SELECT * FROM ShoppingListItem WHERE title = ?");
	   		 ResultSet r = checkStatement.executeQuery();
	   		 ResultSet rs = addStatement.getGeneratedKeys()) {
	   		 
	   		checkStatement.setString (1, i);
			checkStatement.executeQuery();
	   		 
	   		 if (r.next()) {
	   			 b = false;
	   		 } else {
	   			 addStatement.setString(1, i);
	   			 addStatement.executeUpdate();
	   			 b = true;
	   		 }
	   		 
	   		 
	   		 addStatement.getGeneratedKeys();				/**En ymm�rr�*/
	   		 rs.next();										/**n�iden tarkoitusta*/
	   		 int automaticallyGeneratedId = rs.getInt(1);	/**teht�v�ss� */
	   		
	   	 } catch (SQLException e) {
	   		 e.printStackTrace();
	   	 }
	   	 	return b;
	    }
	

	    @Override
	    public boolean removeItem(ShoppingListItem item) {
	    	
	    	String rem = item.getTitle();
	    	boolean b = false;
	 
		 
		try (Connection connection = connect();
				PreparedStatement checkStatement= connection.prepareStatement ("SELECT * FROM ShoppingListItem WHERE title = ?");
				PreparedStatement removeStatement = connection.prepareStatement("DELETE FROM ShoppingListItem WHERE title = ?");
				ResultSet r = checkStatement.executeQuery()) {
			
			checkStatement.setString (1, rem);
			checkStatement.executeQuery();
			 
			 
			if (r.next()) {
				removeStatement.setString(1, rem);
				removeStatement.executeUpdate();	
				b = true;
			} else {
				b = false;
			}
		 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}


}

