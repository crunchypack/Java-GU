package BetacriticEasyDatabase;


//calle
public class Book extends BaseMedia{
	
	protected String ISBN;
	protected String Author;
	protected String Pages;
	
	public Book(String ID, DatabaseManager manager)
	{
		super(ID, "book", manager);
		if (ID.equals("-1")) {
			System.out.println("No row in table "+type+" with inputted id exists");
		}
		

	}
	

	
	public String getISBN()
	{
		refresh();
		return ISBN;
	}
	
	public String getAuthor()
	{
		refresh();
		return Author;
	}
	
	public String getPages()
	{
		refresh();
		return Pages;
	}
	
	public void setISBN(String value)
	{
		setField("ISBN", value);
	}
	public void setAuthor(String value)
	{
		setField("Authors", value);
	}
	public void setPages(String value)
	{
		setField("Pages", value);
	}
	
	public void refresh()
	{
		super.refresh();
		this.ISBN = refreshList.get(DBManager.getIndexOfCollumn(type, "ISBN"));
		this.Author = refreshList.get(DBManager.getIndexOfCollumn(type, "Author"));
		this.Pages = refreshList.get(DBManager.getIndexOfCollumn(type, "Pages"));
	}
	
	public String[] getAsArr()
	{
		String[] arr = new String[]{getID(), getTitle(), getAuthor(),  getDescription(), getISBN(), getPages()};
		return arr;
	}
	
	
	
}
