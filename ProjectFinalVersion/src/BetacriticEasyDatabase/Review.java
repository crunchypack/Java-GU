package BetacriticEasyDatabase;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
//calle
public class Review {
	private String text; 
	private String referencedID;
	private String rating;
	private String type;
	private String userID;
	private String ID;
	private String type1;
	
	public Review(String referencedID, String userID, DatabaseManager manager){
		ArrayList<ArrayList<String>> arrList = DatabaseManager.toArrayList(manager.query("SELECT * FROM review WHERE ReferencedID="+referencedID+" AND ReviewerID="+userID+";"));
		ArrayList<String> list = arrList.get(0);
		this.text = list.get(manager.getIndexOfCollumn("review", "text"));
		this.rating = list.get(manager.getIndexOfCollumn("review", "Rating"));
		this.type = "review";
		this.userID = userID;
		this.referencedID = referencedID;
		this.ID = list.get(manager.getIndexOfCollumn("review", "ID"));
		this.type1 = list.get(manager.getIndexOfCollumn("review", "Type"));
		
	}
	
	
	
	public void Refresh(){
		
		
	}
	
	public static ArrayList<Review> GetReviewsOfBaseMedia(BaseMedia media, DatabaseManager manager)
	{
		
		ArrayList<Review> reviewArrList = new ArrayList<Review>();
		ArrayList<ArrayList<String>> arrList = DatabaseManager.toArrayList(manager.query("SELECT * FROM review WHERE type='"+media.type+"' AND ReferencedID="+media.getID()+";"));
		for (ArrayList<String> arrayList : arrList) {
			Review review = new Review(arrayList.get(manager.getIndexOfCollumn("review", "ReferencedID")),
					arrayList.get(manager.getIndexOfCollumn("review", "ReviewerID")), manager);
			reviewArrList.add(review);
		}
	return reviewArrList;
		

	}
	
	//Simon
	public static ArrayList<Review> GetUserReview(User user, DatabaseManager manager)
	{
		
		ArrayList<Review> reviewArrList = new ArrayList<Review>();
		ArrayList<ArrayList<String>> arrList = DatabaseManager.toArrayList(manager.query("SELECT * FROM review WHERE ReviewerID="+user.getUserID()+";"));
		for (ArrayList<String> arrayList : arrList) {
			Review review = new Review(arrayList.get(manager.getIndexOfCollumn("review", "ReferencedID")),
					arrayList.get(manager.getIndexOfCollumn("review", "ReviewerID")), manager);
			reviewArrList.add(review);
		}
	return reviewArrList;
		

	}
	//Simon end
	
	
	
	public String getType()
	{
		return type;
	}
	public String getRating()
	{
		return rating;
	}
	public String getText()
	{
		return text;
	}
	
	
	public String getUserName(DatabaseManager manager)
	{
		ArrayList<ArrayList<String>> arrList = DatabaseManager.toArrayList(manager.query("SELECT Username FROM user WHERE UserID='"+userID+"';"));
		if (arrList.size() != 0) {
			return arrList.get(0).get(0);
		}
		else {
			return "NoUser";
		}
	
	}
	
	//Simon
	public String getTitle(DatabaseManager manager)
	{
		ArrayList<ArrayList<String>> arrList = DatabaseManager.toArrayList(manager.query("SELECT Title FROM "+type1+" WHERE ID='"+Integer.parseInt(referencedID)+"';"));
		return arrList.get(0).get(0);
	}
	//Simon end
	
	
	
	public int getID(){
		return Integer.parseInt(ID);
		
	}
	
	public void ShowReviewDialog(JFrame frame)
	{
		JOptionPane.showMessageDialog(frame,
				 "The only way to close this dialog is by\n"
						    + "pressing one of the following buttons.\n"
						    + "Do you understand?");
	}
	
	
	
}
