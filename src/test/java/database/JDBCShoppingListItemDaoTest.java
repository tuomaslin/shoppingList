package database;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ShoppingListItem;

class JDBCShoppingListItemDaoTest {
	
	private JDBCShoppingListItemDao dao = new JDBCShoppingListItemDao();
	
	@BeforeEach
	public void setUp() throws Exception {
        Connection connection = dao.connect();
        connection.prepareStatement("DELETE FROM ShoppingListItem").executeUpdate();
        connection.prepareStatement("INSERT INTO ShoppingListItem (id, title) values (1, 'Milk'), (2, 'Eggs')").executeUpdate();
        connection.close();
	}

	// Removing items from list
	@Test
	public void removingItemsFromList() {
		
		ShoppingListItem testRemove = new ShoppingListItem(1, "Milk");
		boolean result = dao.removeItem(testRemove);

		assertEquals(true, result);
	}
	
	// Removing wrong items from list
	@Test
	public void removingWrongItemsFromList() {
			
		ShoppingListItem testRemove = new ShoppingListItem(3, "Cocoa");
		boolean result = dao.removeItem(testRemove);

		assertEquals(false, result);
	}

	// Adding items to list
	@Test
	public void addItemToList() {
		
		ShoppingListItem testAdd = new ShoppingListItem(3, "Cocoa");
		
		boolean result = dao.addItem(testAdd);
		 
	assertEquals(true, result);
	}

	// Searching an item that exists in the list
	@Test
	public void searchItem() {
		
    	long id = 1;
    	
    	ShoppingListItem testSearch = new ShoppingListItem(1, "Milk");
    	
		ShoppingListItem result = dao.getItem(id);
		 
		assertEquals(testSearch, result);
	}

	// Searching an item that doesn't exist in the list
	@Test
	public void searchWrongItem() {
		
		long id = 3;
		
		ShoppingListItem result = dao.getItem(id);
		 
		assertEquals(null, result);
	}

	// List all items in the list
	@Test
	public void listAllItems() {
		
		List <ShoppingListItem> result = dao.listAllItems();
		 
		assertEquals(new ArrayList<>(List.of(new ShoppingListItem(1, "Milk"), new ShoppingListItem(2, "Eggs"))), result);
	}

}
