package BetacriticEasyDatabase;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;


//calle
public class BaseMedia implements Comparable<BaseMedia>{
	
	protected String ID;
	protected String title;
	protected String type;
	protected String description;
	protected DatabaseManager DBManager;
	protected ArrayList<String> refreshList;
	
	public BaseMedia(String ID, String type, DatabaseManager manager)
	{


			this.ID = ID;
			this.type = type;
			this.DBManager = manager;
			if (DatabaseManager.toArrayList(DBManager.query("SELECT * FROM "+type+" WHERE ID='"+ID+"'")).size() == 0) {
				this.ID = "-1";
			}
			
			refresh();
		

	}

	protected void insertIntoDB(String[] fields, String[] values)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO "+type+" (ID, Title, Description, ");
		for (String string : fields) {
			builder.append(string+",");
		}
		
		builder.delete(builder.length()-1, builder.length());
		builder.append(") VALUES (null, '"+title+"', '"+description+"', ");
		
		for (String string : values) {
			builder.append("'"+string+"',");
		}
		builder.delete(builder.length()-1, builder.length());
		builder.append(")");
		
		if (DBManager.manipulativeQuery(builder.toString())) {
			
		}
		else {
			System.out.println("Failure in adding row");
		}
		
	}

	public void refresh()
	{
		refreshList = DatabaseManager.toArrayList(DBManager.query("SELECT * FROM "+type+" WHERE ID='"+ID+"'")).get(0);
		this.title = refreshList.get(DBManager.getIndexOfCollumn(type, "Title"));
		this.description = refreshList.get(DBManager.getIndexOfCollumn(type, "Description"));
	}
	
	public boolean doIExist()
	{
		if (ID.equals("-1")) {
			return false;
		}
		else {
			return false;
		}
	}
	
	public String getID()
	{
		return ID;
	}
	
	public String getTitle()
	{
		refresh();
		return title;
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getDescription()
	{
		refresh();
		return description;
	}
	
	protected void setField(String collName, String value)
	{
		if (!collName.equals("ID")) {
			String query = "UPDATE "+type+" SET "+collName+"='"+value+"' WHERE ID='"+getID()+"'";
			DBManager.manipulativeQuery(query);
		}
		else {
			System.out.println("No changing ID!");
		}
		refresh();
	}
	
	public void setTitle(String value)
	{
		setField("Title", value);
	}
	
	public void setDescription(String value)
	{
		setField("Title", value);
	}
	
	public Double getReviewScore()
	{
		return DBManager.getCriticPoints(type, getID());
	}
	
	public String[] getAsArr()
	{
		String[] arr = new String[]{getID(), getTitle(), getDescription()};
		return arr;
	}
	
	public String[] getCollumnNames()
	{
		ResultSetMetaData metaData;
		try {
			metaData = DBManager.query("SELECT * FROM "+getType()).getMetaData();
			String[] arr = new String[metaData.getColumnCount()];
			for (int i = 0; i < metaData.getColumnCount(); i++) {
				arr[i] = metaData.getColumnName(i);
			}
			return arr;
		} catch (SQLException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


		return null;
	}
	
	
	
	public int compareTo(BaseMedia compareBaseMedia) {
		 

		return compareBaseMedia.getReviewScore().compareTo(this.getReviewScore());

	}	
	
}
