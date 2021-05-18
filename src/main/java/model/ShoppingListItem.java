package model;

public class ShoppingListItem {

	private long id;
	private String title;

	public ShoppingListItem(String title) {
		this.title = title;
	}

	public ShoppingListItem(long id, String title) {
		this.id = id;
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof ShoppingListItem && ((ShoppingListItem) other).id == this.id;
	}
}