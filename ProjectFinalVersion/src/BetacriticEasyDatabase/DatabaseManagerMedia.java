package BetacriticEasyDatabase;
import java.sql.SQLException;

//@author Petroula

public class DatabaseManagerMedia extends DatabaseManager {
	public DatabaseManagerMedia(String location){
		super("settings.txt");
	}
	
	public void addBook(String bookTitle, String Author, String bookDescription, String ISBN, String Pages ) {
		
		try {
			
			statement.executeUpdate("INSERT INTO book values " + 
			"(null,'"+bookTitle+"','"+Author+"','"+bookDescription+"','"+ISBN+"','"+Pages+"');");
			
		} catch (SQLException e) {	
			//e.printStackTrace();
		}
	}
	
	public void addGame (String gameTitle, String gameDescription) {
		
        try {
			
			statement.executeUpdate("INSERT INTO game values " +
			"(null,'"+gameTitle+"','"+gameDescription+"');");
			
		} catch (SQLException e) {	
			//e.printStackTrace();
		}       
	}
	
     public void addMovie (String movieTitle, String movieDescription) {
    		
         try {
    			
    		 statement.executeUpdate("INSERT INTO movie values " +
    		 "(null,'"+movieTitle+"','"+movieDescription+"');");
    			
    	 } catch (SQLException e) {	
    		 //e.printStackTrace();
    	 }	
	 }
     
     //-------------------------------------------------Simon Code-------------------------------
 	/**
 	 * Adds Pictures 
 	 * @param  fileName	 Name of your choosing 
 	 * @param  Caption	 short caption of the picture
 	 * @param  filePath  the filepath of the picture
 	 * @param  Type		The type of the media: Book, Movie or Game
 	 * @author Simon 
 	 */
     public void addPicture (int ID, String fileName, String Caption, String filePath, String Type){
    	 try{
    	 statement.executeUpdate("INSERT INTO Photos values " + "(null,'"+ID+"',' "+fileName+"','"+Caption+"',LOAD_FILE('"+filePath+"'),'"+Type+"');");
     }catch (SQLException e){
    	 
     }

     }
     //-------------------------------------------------Simon Code----------------------------------

}
