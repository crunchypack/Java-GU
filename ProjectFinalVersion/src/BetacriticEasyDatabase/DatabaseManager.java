package BetacriticEasyDatabase;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//Author Carl-Oscar Persson AKA Calle
public class DatabaseManager {
	
	
	Connection connection;
	public Statement statement;
	
	ArrayList<Book> books;
	ArrayList<Game> games;
	ArrayList<Movie> movies;
	
	/**
	 * This class handles functions such as searching the database, updating, inserting etc
	 * @param  DBLocation  the location of the database example: jdbc:mysql://localhost/betacritic
	 * @param  password the username to access the database with
	 * @param  username the password associated with given username
	 */
	public DatabaseManager(String DBLocation, String password, String username)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");	 
			this.connection = DriverManager.getConnection(DBLocation, username, password);
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructs the object with a settings file instead the file needs to be formatted as following:
	 * <br/>jdbc:mysql://localhost/betacritic (location of the database)
	 * <br/>password
	 * <br/>username
	 * @param  filePath  the location of the file with the settings if null it will search in the local folder for the file "settings"
	 */
	public DatabaseManager(String filePath)
	{
		try {
			if (filePath == null) {
				filePath = "settings.txt";
			}
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			Class.forName("com.mysql.jdbc.Driver");	 
			
			String databaseLocation = reader.readLine();
			String password = reader.readLine();
			String username = reader.readLine();

			this.connection = DriverManager.getConnection(databaseLocation, username, password);
			statement = connection.createStatement();
	        reader.close();
	        
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Database connnection could not be established.");
			e.printStackTrace();
			System.exit(0);
		}
		
	
		

	}
	
	/**
	 * Executed given query and returns the resulting resultSet if one is recieved
	 * @param  query  Mysql query to be executed 
	 */
	public ResultSet query(String query)
	{
		ResultSet set = null; 
		try {
			  set = statement.executeQuery(query);
		} catch (SQLException e) {
			System.out.println(e);
		}
		return set;
		
	}
	
	public boolean manipulativeQuery(String query)
	{
		try {
			  statement.execute(query);
			  return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	/**
	 * Searches the database for rows matching given input
	 * @param  word	 The word to search for. will return rows with titles that contains the word
	 * @param  table	 The table that should be searched
	 * @param  minRating  the minimum rating that should be allowed
	 */
	public ArrayList<BaseMedia> search(String word, String table, int minRating)
	{
		String query = "SELECT * FROM "+table+" WHERE Title LIKE '%"+word.trim()+"%'";
		ArrayList<ArrayList<String>> mdList = DatabaseManager.toArrayList(this.query(query));
	
		ArrayList<BaseMedia> BMList = new ArrayList<BaseMedia>();
		for (ArrayList<String> list : mdList) {
			if (table.equals("book")) {
				BMList.add(new Book(list.get(getIndexOfCollumn("book", "ID")), this));
			}
			else if (table.equals("game")) {
				BMList.add(new Game(list.get(getIndexOfCollumn("game", "ID")), this));
			}
			else if (table.equals("movie")) {
				BMList.add(new Movie(list.get(getIndexOfCollumn("movie", "ID")), this));
			}
		}

		

		
		ArrayList<BaseMedia> finalList = new ArrayList<BaseMedia>();
		for (ArrayList<String> arrayList : mdList) {
			if (getCriticPoints(table, arrayList.get(getIndexOfCollumn(table, "ID"))) <= minRating) {
				if (table.equals("book")) {
					finalList.add(new Book(arrayList.get(getIndexOfCollumn("book", "ID")), this));
				}
				else if (table.equals("game")) {
					finalList.add(new Game(arrayList.get(getIndexOfCollumn("game", "ID")), this));
				}
				else if (table.equals("movie")) {
					finalList.add(new Movie(arrayList.get(getIndexOfCollumn("movie", "ID")), this));
				}
				
			};
		}

	
		
		return finalList;
		
	}

	/**
	 * Gets the criticpoints the total product of all the reviews on the given ID (Gets all the review ratings and combines them into one score divided by the amount of reviews)
	 * @param  table	 The word to search for. will return rows with titles that contains the word
	 * @param  ID	 	 The ID of the row that will get its total 
	 */
	public double getCriticPoints(String table, String ID)
	{
		String query = "SELECT * FROM review WHERE ReferencedID='"+ID+"' AND Type='"+table+"'";
		ArrayList<ArrayList<String>> mdList = DatabaseManager.toArrayList(this.query(query));
		
		int indexOfRating = getIndexOfCollumn("review", "Rating");
		int count = 0;
		int total = 0;
		
		for (ArrayList<String> arrayList : mdList) {
			total = total + Integer.parseInt(arrayList.get(indexOfRating));
			count++;
		}
		if (count == 0) {
			return 0;
		}
		else if (total == 0) {
			return 0;
		}
		double totalrating = (double)total/(double)count;
		return totalrating;
		
	}
	
	/**
	 * Gets an arraylist containing the top ranking BaseMedias
	 * @param  amount	 how many of the top scoring BaseMedias that should be returned
	 */
	public ArrayList<ArrayList<String>> GetTopRated(int amount)
	{
		
		ArrayList<ArrayList<String>> resultList = new ArrayList<ArrayList<String>>();
		
		ArrayList<Double> list = new ArrayList<Double>();
		String query = "SELECT * FROM book";
		for (ArrayList<String> string : DatabaseManager.toArrayList(this.query(query))) {
			ArrayList<String> arr = new ArrayList<String>(string); 
			arr.add(Double.toString(getCriticPoints("book", string.get(getIndexOfCollumn("book", "ID")))));
			resultList.add(arr);
		}
		
		Collections.sort(list);
		Collections.reverse(list);
		for (int i = list.size(); i > amount; i--) {
			list.remove(i-1);
		}

		return resultList;

	}
	
	public int getIndexOfCollumn(String table, String collumnName)
	{
		int count = 0;
		for (String name : getCollumnNames(table)) {
			if (name.equals(collumnName)) {
				return count;
			}
			count++;
		}
		if (count == 0) {
			count = -1;
		}
		return count;
	}
	
	public ArrayList<String> getCollumnNames(String table)
	{
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			ResultSetMetaData set = this.query("SELECT * FROM "+table+" LIMIT 1").getMetaData();
			for (int i = 0; i < set.getColumnCount(); i++) {
				list.add(set.getColumnName(i+1));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return list;
		
	}
	
	/**
	 * Converts a ResultSet object into a 2D arraylist object
	 * @param  ResultSet  The ResultSet to be converted
	 */
	public static ArrayList<ArrayList<String>> toArrayList(ResultSet set)
	{
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		
		try {

			while (set.next()) {
				ArrayList<String> list2 = new ArrayList<String>();
				for (int i = 0; i < set.getMetaData().getColumnCount(); i++) {
					list2.add(set.getString(i+1));
				}
				list.add(list2);
				
			}

		} catch (SQLException e) {
		
			e.printStackTrace();
		}

		return list;
		
	}
	
	
	private ArrayList<BaseMedia> constructBaseMedia(String type)
	{
		ArrayList<BaseMedia> mediaList = new ArrayList<BaseMedia>();
		
		ArrayList<ArrayList<String>> list = DatabaseManager.toArrayList(query("SELECT * FROM "+type+""));
		for (ArrayList<String> arrayList : list) {
			mediaList.add(new BaseMedia((arrayList.get(getIndexOfCollumn(type, "ID"))), type, this));
		}
		return mediaList;
	}
	
	public ArrayList<Book> getBooks()
	{
		ArrayList<BaseMedia> list = constructBaseMedia("book");
		ArrayList<Book> bookList  = new ArrayList<Book>();
		for (BaseMedia book : list) {
			bookList.add(new Book(book.getID(), this));
		}
		return bookList;
	}
	
	public ArrayList<Movie> getMovies()
	{
		ArrayList<BaseMedia> list = constructBaseMedia("movie");
		ArrayList<Movie> movieList  = new ArrayList<Movie>();
		for (BaseMedia movie : list) {
			movieList.add(new Movie(movie.getID(), this));
		}
		return movieList;
	}
	
	public ArrayList<Game> getGames()
	{
		ArrayList<BaseMedia> list = constructBaseMedia("game");
		ArrayList<Game> gameList  = new ArrayList<Game>();
		for (BaseMedia movie : list) {
			gameList.add(new Game(movie.getID(), this));
		}
		return gameList;
	}
	
	
	public ArrayList<Book> getTopListBooks(int amount)
	{
		ArrayList<Book> bookList = new ArrayList<Book>(getBooks());
		Collections.sort(bookList);
		
		for (int i = bookList.size(); i > amount; i--) {
			bookList.remove(i-1);
		}
		return bookList;
		
	}
	
	public ArrayList<Game> getTopListGames(int amount)
	{
		ArrayList<Game> gameList = new ArrayList<Game>(getGames());
		Collections.sort(gameList);
		
		for (int i = gameList.size(); i > amount; i--) {
			gameList.remove(i-1);
		}
		return gameList;
		
	}
	
	public void PopulateJTabel(JTable table , ArrayList<BaseMedia> BMList)
	{
		if (BMList.size() != 0) {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setRowCount(0);
			model.setColumnCount(0);
			
			for (String baseMedia : getCollumnNames(BMList.get(0).getType())) {
				model.addColumn(baseMedia);
			}
			
			
			for (BaseMedia media : BMList) {
				model.addRow(media.getAsArr());
			}
			table.setModel(model);
		}
		else {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setRowCount(0);
			model.setColumnCount(1);
			String[] word = new String[]{"No Results"};
			model.addRow(word);
		}

	}
	
	public ArrayList<Movie> getTopListMovies(int amount)
	{
		ArrayList<Movie> movieList = new ArrayList<Movie>(getMovies());
		Collections.sort(movieList);
		
		for (int i = movieList.size(); i > amount; i--) {
			movieList.remove(i-1);
		}
		return movieList;
		
		
	}
	

	
	

	

}