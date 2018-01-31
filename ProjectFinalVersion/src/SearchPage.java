import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import BetacriticEasyDatabase.Book;
import BetacriticEasyDatabase.DatabaseManager;
import BetacriticEasyDatabase.Game;
import BetacriticEasyDatabase.Movie;

// @author Petroula

public class SearchPage extends javax.swing.JPanel {

    
    public SearchPage(final JFrame Projectframe) {
        initComponents(Projectframe);
        //calle lazy code
        if (BetaCritic.loginPanel.jCheckBox3.isSelected()){
     
        BetaCritic.manager.PopulateJTabel(jTable1, BetaCritic.manager.search(loginPanel1.jTextField2.getText(), "book", BetaCritic.loginPanel.Rating));
        jTable1.addMouseListener(new MouseAdapter() {
        	  public void mouseClicked(MouseEvent e) {
        		  if (jTable1.getSelectedRow() != -1) {
        			  if(jTable1.getColumnCount() == 0){	 
        	     int selectedRow = jTable1.getSelectedRow();
        	     
        	     Book book = new Book(jTable1.getValueAt(selectedRow, 0).toString(), (DatabaseManager)BetaCritic.manager);
        	     ReadMoreDialog dialog = new ReadMoreDialog((JFrame)jTable1.getTopLevelAncestor(), true, book);
        	     dialog.show();
        			  }
  				}
        	   }
        	});
        }else if (BetaCritic.loginPanel.jCheckBox2.isSelected()){
            BetaCritic.manager.PopulateJTabel(jTable1, BetaCritic.manager.search(loginPanel1.jTextField2.getText(), "movie", BetaCritic.loginPanel.Rating));
            jTable1.addMouseListener(new MouseAdapter() {
            	  public void mouseClicked(MouseEvent e) {
            		  if (jTable1.getSelectedRow() != -1) {
            		 if(jTable1.getColumnCount() == 0){	  
            	     int selectedRow = jTable1.getSelectedRow();
            	     
            	     Movie movie = new Movie(jTable1.getValueAt(selectedRow, 0).toString(), (DatabaseManager)BetaCritic.manager);
            	     ReadMoreDialog dialog = new ReadMoreDialog((JFrame)jTable1.getTopLevelAncestor(), true, movie);
            	     dialog.show();
            		 }
      				}
            	   }
            	});
        }else if (BetaCritic.loginPanel.jCheckBox1.isSelected()){
            BetaCritic.manager.PopulateJTabel(jTable1, BetaCritic.manager.search(loginPanel1.jTextField2.getText(), "game", BetaCritic.loginPanel.Rating));
            jTable1.addMouseListener(new MouseAdapter() {
            	  public void mouseClicked(MouseEvent e) {
            		  if (jTable1.getSelectedRow() != -1) {
            			  if(jTable1.getColumnCount() == 0){  
            	     int selectedRow = jTable1.getSelectedRow();
            	     
            	     Game game = new Game(jTable1.getValueAt(selectedRow, 0).toString(), (DatabaseManager)BetaCritic.manager);
            	     ReadMoreDialog dialog = new ReadMoreDialog((JFrame)jTable1.getTopLevelAncestor(), true, game);
            	     dialog.show();
            	     }
      				}
            	   }
            	});
        }
        //end calle

    }

                      
    private void initComponents(final JFrame Projectframe) {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        loginPanel1 = BetaCritic.loginPanel;
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(240, 240, 240));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 600));

        jLabel1.setFont(new java.awt.Font("Edwardian Script ITC", 1, 48));
        jLabel1.setText("BetaCritic");
        jLabel1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        jLabel1.addMouseListener( new MouseListener() {     // return to the main page when clicking on the Betacritic label

			public void mouseClicked(MouseEvent e) {}

			public void mouseEntered(MouseEvent e) {}
			
			public void mouseExited(MouseEvent e) {}
			
			public void mousePressed(MouseEvent e) {
				Projectframe.getContentPane().setVisible(false);
		    	Projectframe.setContentPane(new MainPage(Projectframe));
			}

			public void mouseReleased(MouseEvent e) {}
    		
    	});


        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel41.setText("    Search results");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

       
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(181, 181, 181)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addComponent(loginPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(loginPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(113, 113, 113)
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }                     

                    
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable1;
    private LoginPanel loginPanel1;
                     
}
