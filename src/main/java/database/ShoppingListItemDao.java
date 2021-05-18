package database;

import java.util.List;
import model.ShoppingListItem;

public interface ShoppingListItemDao {

	public List<ShoppingListItem> listAllItems();

	public ShoppingListItem getItem(long id);

	public boolean addItem(ShoppingListItem newItem);

	public boolean removeItem(ShoppingListItem item);

}
