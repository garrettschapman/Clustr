package com.teamclustr.clustrapp.gui;

import com.teamclustr.clustrapp.Server;
import com.teamclustr.clustrapp.communication.Post;
import com.teamclustr.clustrapp.representation.Group;
import com.teamclustr.clustrapp.representation.User;
import com.teamclustr.clustrapp.tables.DynamoDBClient;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * This class handles all functionality having to do with the user interface
 *
 * @author Team Clustr
 * @version 1.0 File: GUIFrameMain.java Created: 10/17/2017 Copyright (c) 2017,
 * Team Clustr, All rights reserved. Summary of Modifications: N/A
 */
@SuppressWarnings({ "serial", "unused" })
public class GUIFrameMain extends javax.swing.JFrame {

    // MEMBER DATA.
    // The system of this session.
    public static Server sessionServer;

    // Active session user status constants.
    public static final String USER_STATUS_OUT_TEXT = "Not Logged In";
    public static final Color USER_STATUS_IN_COLOR = Color.BLACK;
    public static final Color USER_STATUS_OUT_COLOR = Color.GRAY;
    public static final Color USER_STATUS_HOVER_COLOR = Color.LIGHT_GRAY;
	
	// Account relationship constants.
	public static final String BUTTON_ADD_FRIEND_TEXT = "Add Friend";
	public static final String BUTTON_REMOVE_FRIEND_TEXT = "Remove Friend";
	public static final String BUTTON_ADD_BLOCK_TEXT = "Block User";
	public static final String BUTTON_REMOVE_BLOCK_TEXT = "Unblock User";
    
    // colors for the gradient theme
    public static final Color GRADIENT_BOTTOM_COLOR = Color.decode("#90E0FF");
    public static final Color GRADIENT_TOP_COLOR = Color.WHITE;
    
    // Misc component theme constants.
    public static final Color GUI_THEME_COLOR = new Color(240, 240, 240);
    
    // Login/Signup dialog return value.
    private User lsDialogResult;
    
    private static final DynamoDBClient client = new DynamoDBClient();

    /**
     * This method creates a translucent JScrollPane so that you can see the 
     * gradient behind the ScrollPane. 
     * @return JScrollPane with a translucent background
     */
    public JScrollPane getTranslucentScrollPane(){
        JScrollPane pane = new JScrollPane();
        pane.getViewport().setOpaque(false); // set the viewport to translucent
        return pane;
    }
    
    /**
     * This method is called by the NetBeans GUI BUILDER by changing the "custom code"
     * option from default to "custom code" in the properties. This method 
     * creates a new JPanel that has a gradient background.
     * @return 
     */
    private JPanel getGradientPanel() {
        class GradientPanel extends JPanel{
            
            
            public GradientPanel(){
                
            }
            
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                Color col1 = GRADIENT_TOP_COLOR;
                
                Color col2 = GRADIENT_BOTTOM_COLOR;
                
                GradientPaint gp = new GradientPaint( 0, 0, col1, 0, h, col2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
                
            }
        }
        GradientPanel panel = new GradientPanel();
        return panel;
    }
    
    private void dumpSession(String filepath) {
	
		// Try to open file.
		try (FileOutputStream fileOutStream = new FileOutputStream(filepath)) {
			
			// Write session server to file.
			ObjectOutputStream objOutStream = new ObjectOutputStream(fileOutStream);
			objOutStream.writeObject(sessionServer);
		}
		catch (IOException ex) {
			
			JOptionPane.showMessageDialog(this, 
					"Could not write server image dump.",
					"Dump Failed", JOptionPane.ERROR_MESSAGE);
		}
	}
    
	private void restoreSession(String filepath) {
	
		// Try to open file.
		try (FileInputStream fileInStream = new FileInputStream(filepath)) {
			
			// Read session server from file.
			ObjectInputStream objInStream = new ObjectInputStream(fileInStream);
			// Use buffer in case dump is malformed.
			Server buffServer = (Server)objInStream.readObject();
			sessionServer = buffServer;
		}
		catch (Exception ex) {
			
			JOptionPane.showMessageDialog(this, 
					"Could not read server image dump.",
					"Restore Failed", JOptionPane.ERROR_MESSAGE);
		}
	}

    private void refreshGroupsTab() {
        
        
        try {
            // refresh posts view

            // set the label text
            Post post = sessionServer.getActivePost();
            viewPostTitleLabel.setText(post.getTitle());
            viewPostNumOfPoints.setText(Integer.toString(post.getPoints()));
            viewPostBodyArea.setText(post.getBody());

            // set the active post
            sessionServer.setActivePost(post);
            
            // initialize the comments
            updateCommentTable(commentTable, sessionServer.getActivePost().getCommentList());


            // show the card
            CardLayout layout = (CardLayout) jPanelGroups.getLayout();
            layout.show(jPanelGroups, "postCard");
            
            
            if (!sessionServer.getActiveGroup().isMember(sessionServer.getActiveUser())) {
                // hide the upvote and downvote buttons
                PostUpvoteButton.setVisible(false);
                PostDownvoteButton.setVisible(false);
            }
            

            // refresh groups view
            
            Group group = sessionServer.getActiveGroup();
            // GET THE GROUP VIEW SET UP -> INITIALIZE
            // set the labels 
            groupNameLabel.setText(group.getName());
            groupCategoriesLabel.setText(group.getCategories());
            groupTagsLabel.setText(group.getTags());

            // set the selection model for the list on the right of the screen
            groupMemberList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            @SuppressWarnings({ "rawtypes", "unused" })
			DefaultListModel listModel = new DefaultListModel();

            // set the active group
            sessionServer.setActiveGroup(group);

            updatePostTable(group);

            // populate the members
            @SuppressWarnings({ "unchecked", "rawtypes" })
			Vector<String> users = new Vector();

            for (User user : group.getMembers()) {
                System.out.printf("Username: %s", user.getUsername());
                users.add(user.getUsername());
            }

            // show the members of the group
            groupMemberList.setListData(users);

            // check to see if the current user is a member, and hide the create post button
            if (sessionServer == null || !group.isMember(sessionServer.getActiveUser())) {
                showCreatePostDialogButton.setVisible(false); // not a member, hide the button
                leaveGroupButton.setVisible(false);
            } else {
                showCreatePostDialogButton.setVisible(true); // is a member, dont hide button
                createPostButton.setVisible(true);
                leaveGroupButton.setVisible(true);

                
            }

            // check to see if the current user is a member of the group
            if (sessionServer == null || group.isMember(sessionServer.getActiveUser())) {
                groupWindowJoinGroupButton.setVisible(false); // is a member, hide the join button
            } else {
                groupWindowJoinGroupButton.setVisible(true); // not a member, show join button
            }
           
        } catch (NullPointerException e) {
            // Ignore It
        }
        
    }
	
    /**
     * This inner class defines a TableModel in which the cells are not editable
     */
    private class TableModel extends DefaultTableModel {

        public TableModel(String[] titles, int rows) {
            super(titles, rows);
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }

        @Override
        public void setValueAt(Object val, int row, int col) {
            // NO!
        }
    }

    // MEMBER METHODS.
    /**
     * Creates new form GUIFrameMain
     */
    public GUIFrameMain() {

	// Initialize the session server.
        sessionServer = new Server();
	    
        // Initialize GUI components.
        initComponents();
        
        
        
        // Initialize session user status.
        jLabelMainSessionUserStatus.setText(USER_STATUS_OUT_TEXT);
        jLabelMainSessionUserStatus.setForeground(USER_STATUS_OUT_COLOR);
        //jButtonAccountMustLogIn.setIcon(new ImageIcon(getClass().getResource("https://github.com/garrettschapman/Clustr/blob/master/src/com/teamclustr/clustrapp/gui/logo.png?raw=true")));
    }

    /**
     * Main program method.
     *
     * @param args command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUIFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIFrameMain().setVisible(true);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jDialogLoginSignup = new javax.swing.JDialog();
        jPanelLoginSignup = getGradientPanel();
        jPanelLogin = new javax.swing.JPanel();
        jLabelLogin = new javax.swing.JLabel();
        jLabelLoginUsername = new javax.swing.JLabel();
        jTextFieldLoginUsername = new javax.swing.JTextField();
        jLabelLoginPassword = new javax.swing.JLabel();
        jPasswordFieldLoginPassword = new javax.swing.JPasswordField();
        jButtonLogin = new javax.swing.JButton();
        jButtonSwitchSignup = new javax.swing.JButton();
        jButtonLoginCancel = new javax.swing.JButton();
        jPanelSignup = new javax.swing.JPanel();
        jLabelSignup = new javax.swing.JLabel();
        jLabelSignupUsername = new javax.swing.JLabel();
        jTextFieldSignupUsername = new javax.swing.JTextField();
        jLabelSignupPassword = new javax.swing.JLabel();
        jPasswordFieldSignupPassword = new javax.swing.JPasswordField();
        jLabelSignupPasswordConfirm = new javax.swing.JLabel();
        jPasswordFieldSignupPasswordConfirm = new javax.swing.JPasswordField();
        jButtonSignup = new javax.swing.JButton();
        jButtonSwitchLogin = new javax.swing.JButton();
        jButtonSignupCancel = new javax.swing.JButton();
        createPostDialog = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        createPostButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        postTitleField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        postBodyField = new javax.swing.JTextArea();
        jTabbedPaneMain = new javax.swing.JTabbedPane();
        jPanelAccount = new javax.swing.JPanel();
        jPanelAccountNull = getGradientPanel();
        jButtonAccountMustLogIn = new javax.swing.JButton();
        jTabbedPaneAcountValid = new javax.swing.JTabbedPane();
        jPanelAccountDetails = getGradientPanel();
        jLabelAccountEmail = new javax.swing.JLabel();
        jTextFieldAccountEmail = new javax.swing.JTextField();
        jTextFieldAccountEmailEdit = new javax.swing.JTextField();
        jLabelAccountPhoneNumber = new javax.swing.JLabel();
        jTextFieldAccountPhoneNumber = new javax.swing.JTextField();
        jTextFieldAccountPhoneNumberEdit = new javax.swing.JTextField();
        jLabelAccountPassword = new javax.swing.JLabel();
        jLabelAccountBio = new javax.swing.JLabel();
        jButtonAccountUpdate = new javax.swing.JButton();
        jPasswordFieldAccountPassword = new javax.swing.JPasswordField();
        jPasswordFieldAccountPasswordEdit = new javax.swing.JPasswordField();
        jScrollPaneAccountBio = new javax.swing.JScrollPane();
        jTextAreaAccountBio = new javax.swing.JTextArea();
        jScrollPaneAccountBioEdit = new javax.swing.JScrollPane();
        jTextAreaAccountBioEdit = new javax.swing.JTextArea();
        fillerAccount = new javax.swing.Box.Filler(new java.awt.Dimension(150, 25), new java.awt.Dimension(150, 25), new java.awt.Dimension(150, 25));
        jTextField1 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextAreaAccountAge = new javax.swing.JTextField();
        jTextAreaAccountAgeEdit = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextAreaAccountGenderEdit = new javax.swing.JComboBox<>();
        jTextAreaAccountGender = new javax.swing.JTextField();
        jTextAreaAccountMajor = new javax.swing.JTextField();
        jTextAreaAccountYear = new javax.swing.JTextField();
        jTextAreaAccountLocation = new javax.swing.JTextField();
        jTextAreaAccountEthnicityEdit = new javax.swing.JComboBox<>();
        jTextAreaAccountYearEdit = new javax.swing.JComboBox<>();
        jTextAreaAccountMajorEdit = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jInterest0Text = new javax.swing.JTextField();
        jInterest1Text = new javax.swing.JTextField();
        jInterest2Text = new javax.swing.JTextField();
        jComboBoxInterest0 = new javax.swing.JComboBox<>();
        jComboBoxInterest1 = new javax.swing.JComboBox<>();
        jTextAreaAccountMarital = new javax.swing.JTextField();
        jTextAreaAccountEthnicity = new javax.swing.JTextField();
        jComboBoxInterest2 = new javax.swing.JComboBox<>();
        jTextAreaAccountMaritalEdit = new javax.swing.JComboBox<>();
        jTextAreaAccountLocationEdit = new javax.swing.JComboBox<>();
        jPanelAccountActivity = getGradientPanel();
        jScrollPaneAccountActivity = getTranslucentScrollPane();
        jTableAccountActivity = new javax.swing.JTable();
        jPanelGroups = new javax.swing.JPanel();
        jPanelAllGroups = getGradientPanel();
        groupSearchField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        groupSearchButton = new javax.swing.JButton();
        browseGroupsPane = getTranslucentScrollPane();
        browseTable = new javax.swing.JTable();
        createAGroupCardButton = new javax.swing.JButton();
        yourGroupsCardButton = new javax.swing.JButton();
        jPanelCreateGroup = getGradientPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        createGroupButton = new javax.swing.JButton();
        createGroupCancelButton = new javax.swing.JButton();
        groupTagsField = new javax.swing.JTextField();
        groupCategoriesField = new javax.swing.JTextField();
        groupNameField = new javax.swing.JTextField();
        jPanelFeedGroups = getGradientPanel();
        jScrollPane2 = getTranslucentScrollPane();
        feedTable = new javax.swing.JTable();
        backToBrowseCardButton = new javax.swing.JButton();
        createAGroupCardButtonFeed = new javax.swing.JButton();
        groupWindowPanel = getGradientPanel();
        groupNameLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        groupCategoriesLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        groupTagsLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        groupMemberList = new javax.swing.JList<String>();
        scrollPane = new javax.swing.JScrollPane();
        groupPostTable = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        showCreatePostDialogButton = new javax.swing.JButton();
        groupWindowJoinGroupButton = new javax.swing.JButton();
        browseGroupsButtonGroupPage = new javax.swing.JButton();
        feedButtonGroupPage = new javax.swing.JButton();
        leaveGroupButton = new javax.swing.JButton();
        viewPostWindowPanel = getGradientPanel();
        jLabel11 = new javax.swing.JLabel();
        viewPostTitleLabel = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        PostUpvoteButton = new javax.swing.JButton();
        PostDownvoteButton = new javax.swing.JButton();
        viewPostNumOfPoints = new javax.swing.JLabel();
        postCardBackButton = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        viewPostBodyArea = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentTable = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        commentTextArea = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        commentTitleField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        deletePostButton = new javax.swing.JButton();
        jLabelMainSessionUserStatus = new javax.swing.JLabel();
        jButtonDump = new javax.swing.JButton();
        jButtonRestore = new javax.swing.JButton();

        jDialogLoginSignup.setTitle("Login/Signup");
        jDialogLoginSignup.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jDialogLoginSignup.setIconImage(null);
        jDialogLoginSignup.setMinimumSize(new java.awt.Dimension(300, 325));
        jDialogLoginSignup.setModal(true);
        jDialogLoginSignup.setResizable(false);

        jPanelLoginSignup.setLayout(new java.awt.CardLayout());

        jPanelLogin.setOpaque(false);
        java.awt.GridBagLayout jPanelLoginLayout = new java.awt.GridBagLayout();
        jPanelLoginLayout.columnWidths = new int[] {0};
        jPanelLoginLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanelLogin.setLayout(jPanelLoginLayout);

        jLabelLogin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelLogin.setText("Login");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanelLogin.add(jLabelLogin, gridBagConstraints);

        jLabelLoginUsername.setText("Username:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        jPanelLogin.add(jLabelLoginUsername, gridBagConstraints);

        jTextFieldLoginUsername.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanelLogin.add(jTextFieldLoginUsername, gridBagConstraints);

        jLabelLoginPassword.setText("Password:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        jPanelLogin.add(jLabelLoginPassword, gridBagConstraints);

        jPasswordFieldLoginPassword.setText("password");
        jPasswordFieldLoginPassword.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jPanelLogin.add(jPasswordFieldLoginPassword, gridBagConstraints);

        jButtonLogin.setText("Login");
        jButtonLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonLoginMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanelLogin.add(jButtonLogin, gridBagConstraints);

        jButtonSwitchSignup.setText("Signup");
        jButtonSwitchSignup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSwitchSignupMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanelLogin.add(jButtonSwitchSignup, gridBagConstraints);

        jButtonLoginCancel.setText("Cancel");
        jButtonLoginCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonLoginCancelMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanelLogin.add(jButtonLoginCancel, gridBagConstraints);

        jPanelLoginSignup.add(jPanelLogin, "card1");

        jPanelSignup.setOpaque(false);
        java.awt.GridBagLayout jPanelSignupLayout = new java.awt.GridBagLayout();
        jPanelSignupLayout.columnWidths = new int[] {0};
        jPanelSignupLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanelSignup.setLayout(jPanelSignupLayout);

        jLabelSignup.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelSignup.setText("Signup");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanelSignup.add(jLabelSignup, gridBagConstraints);

        jLabelSignupUsername.setText("Username:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        jPanelSignup.add(jLabelSignupUsername, gridBagConstraints);

        jTextFieldSignupUsername.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanelSignup.add(jTextFieldSignupUsername, gridBagConstraints);

        jLabelSignupPassword.setText("Password:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        jPanelSignup.add(jLabelSignupPassword, gridBagConstraints);

        jPasswordFieldSignupPassword.setText("password");
        jPasswordFieldSignupPassword.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jPanelSignup.add(jPasswordFieldSignupPassword, gridBagConstraints);

        jLabelSignupPasswordConfirm.setText("Confirm Password:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        jPanelSignup.add(jLabelSignupPasswordConfirm, gridBagConstraints);

        jPasswordFieldSignupPasswordConfirm.setText("password");
        jPasswordFieldSignupPasswordConfirm.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        jPanelSignup.add(jPasswordFieldSignupPasswordConfirm, gridBagConstraints);

        jButtonSignup.setText("Signup");
        jButtonSignup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSignupMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanelSignup.add(jButtonSignup, gridBagConstraints);

        jButtonSwitchLogin.setText("Login");
        jButtonSwitchLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSwitchLoginMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanelSignup.add(jButtonSwitchLogin, gridBagConstraints);

        jButtonSignupCancel.setText("Cancel");
        jButtonSignupCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSignupCancelMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanelSignup.add(jButtonSignupCancel, gridBagConstraints);

        jPanelLoginSignup.add(jPanelSignup, "card2");

        javax.swing.GroupLayout jDialogLoginSignupLayout = new javax.swing.GroupLayout(jDialogLoginSignup.getContentPane());
        jDialogLoginSignup.getContentPane().setLayout(jDialogLoginSignupLayout);
        jDialogLoginSignupLayout.setHorizontalGroup(
            jDialogLoginSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelLoginSignup, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        jDialogLoginSignupLayout.setVerticalGroup(
            jDialogLoginSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelLoginSignup, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
        );

        createPostDialog.setTitle("Clustr");
        createPostDialog.setMinimumSize(new java.awt.Dimension(500, 500));

        createPostButton.setText("Create Post");
        createPostButton.setMaximumSize(new java.awt.Dimension(500, 500));
        createPostButton.setMinimumSize(new java.awt.Dimension(500, 500));
        createPostButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createPostButtonMouseClicked(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Post Title");

        postTitleField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Post Content");

        postBodyField.setColumns(20);
        postBodyField.setRows(5);
        jScrollPane4.setViewportView(postBodyField);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(createPostButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jButton2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(postTitleField, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 59, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel9)
                    .addComponent(postTitleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createPostButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        javax.swing.GroupLayout createPostDialogLayout = new javax.swing.GroupLayout(createPostDialog.getContentPane());
        createPostDialog.getContentPane().setLayout(createPostDialogLayout);
        createPostDialogLayout.setHorizontalGroup(
            createPostDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        createPostDialogLayout.setVerticalGroup(
            createPostDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Clustr");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImages(null);
        setMaximumSize(new java.awt.Dimension(1600, 1200));
        setMinimumSize(new java.awt.Dimension(800, 500));
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jTabbedPaneMain.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPaneMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPaneMainMouseClicked(evt);
            }
        });

        jPanelAccount.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanelAccountComponentShown(evt);
            }
        });
        jPanelAccount.setLayout(new java.awt.CardLayout());

        jPanelAccountNull.setBackground(new java.awt.Color(255, 255, 255));
        jPanelAccountNull.setLayout(new java.awt.GridBagLayout());

        jButtonAccountMustLogIn.setBackground(new java.awt.Color(255, 255, 255));
        jButtonAccountMustLogIn.setIcon(new javax.swing.ImageIcon(Paths.get("/resources/logo.png").toAbsolutePath().toString())); // NOI18N
        jButtonAccountMustLogIn.setText("Login/Signup");
        jButtonAccountMustLogIn.setToolTipText("");
        jButtonAccountMustLogIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonAccountMustLogInMouseClicked(evt);
            }
        });
        jButtonAccountMustLogIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAccountMustLogInActionPerformed(evt);
            }
        });
        jPanelAccountNull.add(jButtonAccountMustLogIn, new java.awt.GridBagConstraints());

        jPanelAccount.add(jPanelAccountNull, "null");

        java.awt.GridBagLayout jPanelAccountDetailsValidLayout = new java.awt.GridBagLayout();
        jPanelAccountDetailsValidLayout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanelAccountDetailsValidLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanelAccountDetails.setLayout(jPanelAccountDetailsValidLayout);

        jLabelAccountEmail.setText("Email Address");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanelAccountDetails.add(jLabelAccountEmail, gridBagConstraints);

        jTextFieldAccountEmail.setEditable(false);
        jTextFieldAccountEmail.setMinimumSize(new java.awt.Dimension(250, 25));
        jTextFieldAccountEmail.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanelAccountDetails.add(jTextFieldAccountEmail, gridBagConstraints);

        jTextFieldAccountEmailEdit.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        jPanelAccountDetails.add(jTextFieldAccountEmailEdit, gridBagConstraints);

        jLabelAccountPhoneNumber.setText("Phone Number");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanelAccountDetails.add(jLabelAccountPhoneNumber, gridBagConstraints);

        jTextFieldAccountPhoneNumber.setEditable(false);
        jTextFieldAccountPhoneNumber.setMinimumSize(new java.awt.Dimension(250, 25));
        jTextFieldAccountPhoneNumber.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        jPanelAccountDetails.add(jTextFieldAccountPhoneNumber, gridBagConstraints);

        jTextFieldAccountPhoneNumberEdit.setMinimumSize(new java.awt.Dimension(250, 25));
        jTextFieldAccountPhoneNumberEdit.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        jPanelAccountDetails.add(jTextFieldAccountPhoneNumberEdit, gridBagConstraints);

        jLabelAccountPassword.setText("Password");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jPanelAccountDetails.add(jLabelAccountPassword, gridBagConstraints);

        jLabelAccountBio.setText("Biography");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        jPanelAccountDetails.add(jLabelAccountBio, gridBagConstraints);

        jButtonAccountUpdate.setText("Update");
        jButtonAccountUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonAccountUpdateMouseClicked(evt);
            }
        });
        /*jButtonAccountUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAccountUpdateActionPerformed(evt);
            }
        });*/
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        jPanelAccountDetails.add(jButtonAccountUpdate, gridBagConstraints);

        jPasswordFieldAccountPassword.setEditable(false);
        jPasswordFieldAccountPassword.setMinimumSize(new java.awt.Dimension(250, 25));
        jPasswordFieldAccountPassword.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        jPanelAccountDetails.add(jPasswordFieldAccountPassword, gridBagConstraints);

        jPasswordFieldAccountPasswordEdit.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        jPanelAccountDetails.add(jPasswordFieldAccountPasswordEdit, gridBagConstraints);

        jTextAreaAccountBio.setEditable(false);
        jTextAreaAccountBio.setColumns(20);
        jTextAreaAccountBio.setLineWrap(true);
        jTextAreaAccountBio.setRows(5);
        jTextAreaAccountBio.setPreferredSize(new java.awt.Dimension(100, 50));
        jScrollPaneAccountBio.setViewportView(jTextAreaAccountBio);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelAccountDetails.add(jScrollPaneAccountBio, gridBagConstraints);

        jTextAreaAccountBioEdit.setColumns(20);
        jTextAreaAccountBioEdit.setLineWrap(true);
        jTextAreaAccountBioEdit.setRows(5);
        jTextAreaAccountBioEdit.setPreferredSize(new java.awt.Dimension(100, 50));
        jScrollPaneAccountBioEdit.setViewportView(jTextAreaAccountBioEdit);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelAccountDetails.add(jScrollPaneAccountBioEdit, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanelAccountDetails.add(fillerAccount, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        jPanelAccountDetails.add(jTextField1, gridBagConstraints);

        jLabel17.setText("Age");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        jPanelAccountDetails.add(jLabel17, gridBagConstraints);

        jTextAreaAccountAge.setMinimumSize(new java.awt.Dimension(250, 25));
        jTextAreaAccountAge.setPreferredSize(new java.awt.Dimension(250, 25));
        jTextAreaAccountAge.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        jPanelAccountDetails.add(jTextAreaAccountAge, gridBagConstraints);

        jTextAreaAccountAgeEdit.setMinimumSize(new java.awt.Dimension(250, 25));
        jTextAreaAccountAgeEdit.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 18;
        jPanelAccountDetails.add(jTextAreaAccountAgeEdit, gridBagConstraints);

        jLabel18.setText("Gender");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        jPanelAccountDetails.add(jLabel18, gridBagConstraints);

        jLabel19.setText("Major");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 32;
        jPanelAccountDetails.add(jLabel19, gridBagConstraints);

        jLabel20.setText("Year");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 36;
        jPanelAccountDetails.add(jLabel20, gridBagConstraints);

        jLabel21.setText("Housing Location");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 40;
        jPanelAccountDetails.add(jLabel21, gridBagConstraints);

        jTextAreaAccountGenderEdit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female", "Other", "Prefer not to Answer" }));
        jTextAreaAccountGenderEdit.setMinimumSize(new java.awt.Dimension(250, 25));
        jTextAreaAccountGenderEdit.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 22;
        jPanelAccountDetails.add(jTextAreaAccountGenderEdit, gridBagConstraints);

        jTextAreaAccountGender.setMinimumSize(new java.awt.Dimension(250, 25));
        jTextAreaAccountGender.setPreferredSize(new java.awt.Dimension(250, 25));
        jTextAreaAccountGender.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 22;
        jPanelAccountDetails.add(jTextAreaAccountGender, gridBagConstraints);

        jTextAreaAccountMajor.setMinimumSize(new java.awt.Dimension(250, 25));
        jTextAreaAccountMajor.setPreferredSize(new java.awt.Dimension(250, 25));
        jTextAreaAccountMajor.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 34;
        jPanelAccountDetails.add(jTextAreaAccountMajor, gridBagConstraints);

        jTextAreaAccountYear.setToolTipText("");
        jTextAreaAccountYear.setMinimumSize(new java.awt.Dimension(250, 25));
        jTextAreaAccountYear.setPreferredSize(new java.awt.Dimension(250, 25));
        jTextAreaAccountYear.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 38;
        jPanelAccountDetails.add(jTextAreaAccountYear, gridBagConstraints);

        jTextAreaAccountLocation.setPreferredSize(new java.awt.Dimension(250, 25));
        jTextAreaAccountLocation.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 42;
        jPanelAccountDetails.add(jTextAreaAccountLocation, gridBagConstraints);

        jTextAreaAccountEthnicityEdit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Caucasian", "African American", "Hispanic/Latino", "Asian/Pacific Islander", "Other" }));
        jTextAreaAccountEthnicityEdit.setMinimumSize(new java.awt.Dimension(250, 25));
        jTextAreaAccountEthnicityEdit.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 26;
        jPanelAccountDetails.add(jTextAreaAccountEthnicityEdit, gridBagConstraints);

        jTextAreaAccountYearEdit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Freshman", "Sophomore", "Junior", "Senior" }));
        jTextAreaAccountYearEdit.setMinimumSize(new java.awt.Dimension(250, 25));
        jTextAreaAccountYearEdit.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 38;
        jPanelAccountDetails.add(jTextAreaAccountYearEdit, gridBagConstraints);

        jTextAreaAccountMajorEdit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Accounting", "Anthropology", "Art", "Athletic Training", "Biochemistry", "Bioengineering", "Biology", "Biology-Accelerated", "Biotechnology", "Chemistry", "Child and Youth Studies", "Civil Engineering", "Clinical Laboratory Science", "Communication", "Community Health", "Computer Information Systems", "Criminal Justice", "Early Childhood Education", "Economics", "Elementary Education", "English", "Environmental Engineering", "Environmental Studies", "Exercise Science", "Finance", "Forensic Science", "Forensic Studies", "Health Science", "History", "Integrated Studies", "Interdisciplinary Entrepreneurship Studies", "Journalism", "Legal Studies", "Management", "Marine Science", "Marketing", "Mathematics", "Music - Performance", "Music Education", "Music Therapy", "Nursing", "PGA Golf Management", "Philosophy", "Political Science", "Psychology", "Public Health", "Resort & Hospitality Management", "Secondary Biology Education", "Secondary Mathematics Education", "Secondary Social Science Education", "Social Work", "Sociology", "Software Engineering", "Special Education", "Theatre" }));
        jTextAreaAccountMajorEdit.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 34;
        jPanelAccountDetails.add(jTextAreaAccountMajorEdit, gridBagConstraints);

        jLabel22.setText("Interests");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 44;
        jPanelAccountDetails.add(jLabel22, gridBagConstraints);

        jLabel23.setText("Marital Status");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 28;
        jPanelAccountDetails.add(jLabel23, gridBagConstraints);

        jLabel25.setText("Ethnicity");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 24;
        jPanelAccountDetails.add(jLabel25, gridBagConstraints);

        jInterest0Text.setPreferredSize(new java.awt.Dimension(250, 25));
        jInterest0Text.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 46;
        jPanelAccountDetails.add(jInterest0Text, gridBagConstraints);

        jInterest1Text.setPreferredSize(new java.awt.Dimension(250, 25));
        jInterest1Text.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 48;
        jPanelAccountDetails.add(jInterest1Text, gridBagConstraints);

        jInterest2Text.setPreferredSize(new java.awt.Dimension(250, 25));
        jInterest2Text.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 50;
        jPanelAccountDetails.add(jInterest2Text, gridBagConstraints);

        jComboBoxInterest0.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Baseball", "Basketball", "Football", "Soccer" }));
        jComboBoxInterest0.setMinimumSize(new java.awt.Dimension(250, 25));
        jComboBoxInterest0.setName(""); // NOI18N
        jComboBoxInterest0.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 50;
        jPanelAccountDetails.add(jComboBoxInterest0, gridBagConstraints);

        jComboBoxInterest1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Drawing", "Painting", "Photography", "Writing" }));
        jComboBoxInterest1.setMinimumSize(new java.awt.Dimension(250, 25));
        jComboBoxInterest1.setName(""); // NOI18N
        jComboBoxInterest1.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 46;
        jPanelAccountDetails.add(jComboBoxInterest1, gridBagConstraints);

        jTextAreaAccountMarital.setMinimumSize(new java.awt.Dimension(250, 25));
        jTextAreaAccountMarital.setPreferredSize(new java.awt.Dimension(250, 25));
        jTextAreaAccountMarital.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 30;
        jPanelAccountDetails.add(jTextAreaAccountMarital, gridBagConstraints);

        jTextAreaAccountEthnicity.setMinimumSize(new java.awt.Dimension(250, 25));
        jTextAreaAccountEthnicity.setPreferredSize(new java.awt.Dimension(250, 25));
        jTextAreaAccountEthnicity.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 26;
        jPanelAccountDetails.add(jTextAreaAccountEthnicity, gridBagConstraints);

        jComboBoxInterest2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Video Games", "Television", "Movies", "Music" }));
        jComboBoxInterest2.setMinimumSize(new java.awt.Dimension(250, 25));
        jComboBoxInterest2.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 48;
        jPanelAccountDetails.add(jComboBoxInterest2, gridBagConstraints);

        jTextAreaAccountMaritalEdit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Unmarried", "Married" }));
        jTextAreaAccountMaritalEdit.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 30;
        jPanelAccountDetails.add(jTextAreaAccountMaritalEdit, gridBagConstraints);

        jTextAreaAccountLocationEdit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SoVi", "North Lake Village", "West Lake Village", "Coastal Village", "Off-Campus" }));
        jTextAreaAccountLocationEdit.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 42;
        jPanelAccountDetails.add(jTextAreaAccountLocationEdit, gridBagConstraints);

        jTabbedPaneAcountValid.addTab("Details", jPanelAccountDetails);

        jTableAccountActivity.setAutoCreateRowSorter(true);
        jTableAccountActivity.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        jTableAccountActivity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableAccountActivityMouseClicked(evt);
            }
        });
        jScrollPaneAccountActivity.setViewportView(jTableAccountActivity);

        javax.swing.GroupLayout jPanelAccountActivityLayout = new javax.swing.GroupLayout(jPanelAccountActivity);
        jPanelAccountActivity.setLayout(jPanelAccountActivityLayout);
        jPanelAccountActivityLayout.setHorizontalGroup(
            jPanelAccountActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneAccountActivity, javax.swing.GroupLayout.DEFAULT_SIZE, 964, Short.MAX_VALUE)
        );
        jPanelAccountActivityLayout.setVerticalGroup(
            jPanelAccountActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneAccountActivity, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
        );

        jTabbedPaneAcountValid.addTab("Activity", jPanelAccountActivity);

        jPanelAccount.add(jTabbedPaneAcountValid, "valid");

        jTabbedPaneMain.addTab("Account", jPanelAccount);

        jPanelGroups.setLayout(new java.awt.CardLayout());

        jPanelAllGroups.setOpaque(false);

        groupSearchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupSearchFieldActionPerformed(evt);
            }
        });

        jLabel1.setText("Search Groups");

        groupSearchButton.setText("Search");
        groupSearchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                groupSearchButtonMouseClicked(evt);
            }
        });

        browseGroupsPane.setBackground(new java.awt.Color(255, 255, 255));
        browseGroupsPane.setOpaque(false);

        browseTable.setAutoCreateRowSorter(true);
        browseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "Group Name", "Number Of Members", "Tags", "Categories", "Join"
            }
        ));
        browseTable.setOpaque(false);
        browseTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                browseTableMouseClicked(evt);
            }
        });
        browseGroupsPane.setViewportView(browseTable);

        createAGroupCardButton.setText("Create A Group");
        createAGroupCardButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createAGroupCardButtonMouseClicked(evt);
            }
        });

        yourGroupsCardButton.setText("Your Groups");
        yourGroupsCardButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                yourGroupsCardButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelAllGroupsLayout = new javax.swing.GroupLayout(jPanelAllGroups);
        jPanelAllGroups.setLayout(jPanelAllGroupsLayout);
        jPanelAllGroupsLayout.setHorizontalGroup(
            jPanelAllGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAllGroupsLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groupSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groupSearchButton)
                .addGap(38, 38, 38)
                .addComponent(createAGroupCardButton)
                .addGap(29, 29, 29)
                .addComponent(yourGroupsCardButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelAllGroupsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(browseGroupsPane, javax.swing.GroupLayout.DEFAULT_SIZE, 949, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelAllGroupsLayout.setVerticalGroup(
            jPanelAllGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAllGroupsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAllGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(groupSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(groupSearchButton)
                    .addComponent(createAGroupCardButton)
                    .addComponent(yourGroupsCardButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(browseGroupsPane, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelGroups.add(jPanelAllGroups, "card3");
        jPanelGroups.add(jPanelAllGroups, "browseAllCard");

        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 600));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Group Name");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("Categories");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("Tags");

        createGroupButton.setText("Create Group");
        createGroupButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createGroupButtonMouseClicked(evt);
            }
        });

        createGroupCancelButton.setText("Cancel");
        createGroupCancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createGroupCancelButtonMouseClicked(evt);
            }
        });

        groupTagsField.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        groupCategoriesField.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        groupNameField.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(157, 157, 157)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(groupCategoriesField)
                                    .addComponent(groupTagsField)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 165, Short.MAX_VALUE)
                                .addComponent(groupNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(createGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(createGroupCancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(212, 212, 212))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(groupNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(groupCategoriesField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(groupTagsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 272, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(createGroupCancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelCreateGroupLayout = new javax.swing.GroupLayout(jPanelCreateGroup);
        jPanelCreateGroup.setLayout(jPanelCreateGroupLayout);
        jPanelCreateGroupLayout.setHorizontalGroup(
            jPanelCreateGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCreateGroupLayout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 727, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(133, Short.MAX_VALUE))
        );
        jPanelCreateGroupLayout.setVerticalGroup(
            jPanelCreateGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCreateGroupLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(283, Short.MAX_VALUE))
        );

        jPanelGroups.add(jPanelCreateGroup, "card4");
        jPanelGroups.add(jPanelCreateGroup, "createGroupCard");

        feedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        feedTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                feedTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(feedTable);

        backToBrowseCardButton.setText("Back To Browse");
        backToBrowseCardButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backToBrowseCardButtonMouseClicked(evt);
            }
        });

        createAGroupCardButtonFeed.setText("Create A Group");
        createAGroupCardButtonFeed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createAGroupCardButtonFeedMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelFeedGroupsLayout = new javax.swing.GroupLayout(jPanelFeedGroups);
        jPanelFeedGroups.setLayout(jPanelFeedGroupsLayout);
        jPanelFeedGroupsLayout.setHorizontalGroup(
            jPanelFeedGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelFeedGroupsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 949, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelFeedGroupsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backToBrowseCardButton)
                .addGap(42, 42, 42)
                .addComponent(createAGroupCardButtonFeed)
                .addGap(152, 152, 152))
        );
        jPanelFeedGroupsLayout.setVerticalGroup(
            jPanelFeedGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelFeedGroupsLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanelFeedGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backToBrowseCardButton)
                    .addComponent(createAGroupCardButtonFeed))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 832, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelGroups.add(jPanelFeedGroups, "card5");
        jPanelGroups.add(jPanelFeedGroups, "feedCard");

        groupNameLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        groupNameLabel.setText("jLabel3");

        jLabel3.setText("Categories");

        groupCategoriesLabel.setText("jLabel6");

        jLabel6.setText("Tags");

        groupTagsLabel.setText("jLabel7");

        groupMemberList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane3.setViewportView(groupMemberList);

        groupPostTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        groupPostTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                groupPostTableMouseClicked(evt);
            }
        });
        scrollPane.setViewportView(groupPostTable);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Posts");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Members");

        showCreatePostDialogButton.setText("Create Post");
        showCreatePostDialogButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showCreatePostDialogButtonMouseClicked(evt);
            }
        });

        groupWindowJoinGroupButton.setText("Join Group");
        groupWindowJoinGroupButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                groupWindowJoinGroupButtonMouseClicked(evt);
            }
        });

        browseGroupsButtonGroupPage.setText("Browse Groups");
        browseGroupsButtonGroupPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseGroupsButtonGroupPageActionPerformed(evt);
            }
        });

        feedButtonGroupPage.setText("Feed");
        feedButtonGroupPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                feedButtonGroupPageActionPerformed(evt);
            }
        });

        leaveGroupButton.setText("Leave Group");
        leaveGroupButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                leaveGroupButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout groupWindowPanelLayout = new javax.swing.GroupLayout(groupWindowPanel);
        groupWindowPanel.setLayout(groupWindowPanelLayout);
        groupWindowPanelLayout.setHorizontalGroup(
            groupWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(groupWindowPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(groupWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(groupWindowPanelLayout.createSequentialGroup()
                        .addGroup(groupWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(groupWindowPanelLayout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(showCreatePostDialogButton)
                                .addGap(18, 18, 18)
                                .addComponent(groupWindowJoinGroupButton)
                                .addGap(18, 18, 18)
                                .addComponent(browseGroupsButtonGroupPage))
                            .addGroup(groupWindowPanelLayout.createSequentialGroup()
                                .addComponent(groupNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(groupWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(groupWindowPanelLayout.createSequentialGroup()
                                        .addGap(55, 55, 55)
                                        .addComponent(feedButtonGroupPage)
                                        .addGap(28, 28, 28)
                                        .addComponent(leaveGroupButton))
                                    .addGroup(groupWindowPanelLayout.createSequentialGroup()
                                        .addGroup(groupWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel6))
                                        .addGap(104, 104, 104)
                                        .addGroup(groupWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(groupTagsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                            .addComponent(groupCategoriesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, groupWindowPanelLayout.createSequentialGroup()
                        .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 843, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(groupWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(groupWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(groupWindowPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSeparator1)
                    .addContainerGap()))
        );
        groupWindowPanelLayout.setVerticalGroup(
            groupWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(groupWindowPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(groupWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(groupNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(groupWindowPanelLayout.createSequentialGroup()
                        .addGroup(groupWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(groupCategoriesLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(groupWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(groupTagsLabel))))
                .addGap(18, 18, 18)
                .addGroup(groupWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7)
                    .addComponent(showCreatePostDialogButton)
                    .addComponent(groupWindowJoinGroupButton)
                    .addComponent(browseGroupsButtonGroupPage)
                    .addComponent(feedButtonGroupPage)
                    .addComponent(jLabel8)
                    .addComponent(leaveGroupButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(groupWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(groupWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(groupWindowPanelLayout.createSequentialGroup()
                    .addGap(51, 51, 51)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(816, Short.MAX_VALUE)))
        );

        jPanelGroups.add(groupWindowPanel, "card5");
        jPanelGroups.add(groupWindowPanel, "groupPageCard");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Post Title");

        viewPostTitleLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        viewPostTitleLabel.setText("jLabel12");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Post Content");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Points");

        PostUpvoteButton.setText("Upvote");
        PostUpvoteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PostUpvoteButtonMouseClicked(evt);
            }
        });

        PostDownvoteButton.setText("Downvote");
        PostDownvoteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PostDownvoteButtonMouseClicked(evt);
            }
        });

        viewPostNumOfPoints.setText("jLabel14");

        postCardBackButton.setText("Back");
        postCardBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postCardBackButtonActionPerformed(evt);
            }
        });

        jScrollPane5.setEnabled(false);

        viewPostBodyArea.setEditable(false);
        viewPostBodyArea.setColumns(20);
        viewPostBodyArea.setRows(5);
        jScrollPane5.setViewportView(viewPostBodyArea);

        jScrollPane1.setViewportView(commentTable);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Comments");

        commentTextArea.setColumns(20);
        commentTextArea.setRows(5);
        jScrollPane6.setViewportView(commentTextArea);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("Leave A Comment");

        jButton1.setText("Leave Comment");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Title");

        deletePostButton.setText("Delete Post");
        deletePostButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deletePostButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout viewPostWindowPanelLayout = new javax.swing.GroupLayout(viewPostWindowPanel);
        viewPostWindowPanel.setLayout(viewPostWindowPanelLayout);
        viewPostWindowPanelLayout.setHorizontalGroup(
            viewPostWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewPostWindowPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(viewPostWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(viewPostWindowPanelLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(59, 59, 59)
                        .addComponent(postCardBackButton)
                        .addContainerGap(743, Short.MAX_VALUE))
                    .addGroup(viewPostWindowPanelLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(72, 72, 72)
                        .addComponent(viewPostTitleLabel)
                        .addGap(155, 155, 155)
                        .addComponent(deletePostButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(PostUpvoteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(viewPostNumOfPoints)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PostDownvoteButton)
                        .addGap(92, 92, 92))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewPostWindowPanelLayout.createSequentialGroup()
                        .addGroup(viewPostWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addComponent(jScrollPane5))
                        .addContainerGap())
                    .addGroup(viewPostWindowPanelLayout.createSequentialGroup()
                        .addGroup(viewPostWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jButton1)
                            .addGroup(viewPostWindowPanelLayout.createSequentialGroup()
                                .addGroup(viewPostWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, viewPostWindowPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel16)
                                        .addGap(35, 35, 35)
                                        .addComponent(commentTitleField, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE))
                                .addGap(459, 459, 459))
                            .addComponent(jSeparator2))
                        .addContainerGap())))
        );
        viewPostWindowPanelLayout.setVerticalGroup(
            viewPostWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewPostWindowPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(viewPostWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel11)
                    .addComponent(viewPostTitleLabel)
                    .addComponent(jLabel13)
                    .addComponent(PostUpvoteButton)
                    .addComponent(viewPostNumOfPoints)
                    .addComponent(PostDownvoteButton)
                    .addComponent(deletePostButton))
                .addGap(68, 68, 68)
                .addGroup(viewPostWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(postCardBackButton))
                .addGap(3, 3, 3)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addGap(37, 37, 37)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(viewPostWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(commentTitleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        jPanelGroups.add(viewPostWindowPanel, "card6");
        jPanelGroups.add(viewPostWindowPanel, "postCard");

        jTabbedPaneMain.addTab("Groups", jPanelGroups);

        jLabelMainSessionUserStatus.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelMainSessionUserStatus.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelMainSessionUserStatus.setText("$USER_LOGIN_STATUS");
        jLabelMainSessionUserStatus.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabelMainSessionUserStatus.setMaximumSize(new java.awt.Dimension(200, 15));
        jLabelMainSessionUserStatus.setMinimumSize(new java.awt.Dimension(0, 15));
        jLabelMainSessionUserStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelMainSessionUserStatusMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelMainSessionUserStatusMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelMainSessionUserStatusMouseExited(evt);
            }
        });

        jButtonDump.setText("Save");
        jButtonDump.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonDumpMouseClicked(evt);
            }
        });

        jButtonRestore.setText("Load");
        jButtonRestore.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonRestoreMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneMain)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonDump)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonRestore)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelMainSessionUserStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelMainSessionUserStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonDump)
                        .addComponent(jButtonRestore)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPaneMain))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

        private void jLabelMainSessionUserStatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMainSessionUserStatusMouseClicked

		// Log user in.
		this.logUserIn(this.promptUserForLogin());
        }//GEN-LAST:event_jLabelMainSessionUserStatusMouseClicked

        private void jLabelMainSessionUserStatusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMainSessionUserStatusMouseEntered

		// Update display.
		this.refreshUserStatusLabel();
        }//GEN-LAST:event_jLabelMainSessionUserStatusMouseEntered

        private void jLabelMainSessionUserStatusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMainSessionUserStatusMouseExited

		// Update display.
		this.refreshUserStatusLabel();
        }//GEN-LAST:event_jLabelMainSessionUserStatusMouseExited

        private void jButtonSwitchSignupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSwitchSignupMouseClicked

            // Switch to login panel.
            LayoutManager dialogLayout = jPanelLoginSignup.getLayout();
            if (dialogLayout instanceof CardLayout) {

                ((CardLayout) dialogLayout).next(jPanelLoginSignup);
            }
        }//GEN-LAST:event_jButtonSwitchSignupMouseClicked

        private void jButtonSwitchLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSwitchLoginMouseClicked

            // Switch to signup panel.
            LayoutManager dialogLayout = jPanelLoginSignup.getLayout();
            if (dialogLayout instanceof CardLayout) {

                ((CardLayout) dialogLayout).next(jPanelLoginSignup);
            }
        }//GEN-LAST:event_jButtonSwitchLoginMouseClicked

    private void groupSearchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupSearchFieldActionPerformed
        
    }//GEN-LAST:event_groupSearchFieldActionPerformed

    private String getGroupNameFromTable(JTable tbl, java.awt.event.MouseEvent evt) {
        int row = tbl.rowAtPoint(evt.getPoint());
        return (String) tbl.getModel().getValueAt(row, 0);
    }

    private String getPostNameFromTable(JTable tbl, java.awt.event.MouseEvent evt, int postNameColumn) {
        int row = tbl.rowAtPoint(evt.getPoint());
        return (String) tbl.getModel().getValueAt(row, postNameColumn);
    }

    /**
     * This method is called when a table is initialized, and populates and 
     * updates the content of the table. 
     * 
     * @param tbl The table in question
     * @param tableName Either 'browse' or 'feed' depending on the table structure you
     * want
     */
    private void updateGroupTable(JTable tbl, String tableName, ArrayList<Group> groupList) throws InvalidParameterException {
        String col[] = {"Group Name", "Number of Members", "Tags", "Categories", "Posts"};
        TableModel tableModel = new TableModel(col, 0);
        try {

            tbl.setModel(tableModel);

            // for each group in groupList, add it as a row in the table
            for (Group group : groupList) {
                //System.out.println(group.getName());
                Object obj[] = new Object[5];

                obj[0] = group.getName();
                obj[1] = group.getMembers().size();
                obj[2] = group.getTags();
                obj[3] = group.getCategories();
                obj[4] = group.getPosts().size();

                if (tableName.equals("browse")) {

                    // add the group info to the table
                    tableModel.addRow(obj);

                } else if (tableName.equals("feed")) {

                    /*  only show the group if the user is a member of the group
                        because this is the section where the user can see which groups they 
                        belong to
                    */
                    if (group.getMembers().contains(sessionServer.getActiveUser())) {
                        tableModel.addRow(obj);
                    }

                } else {
                    // invalid table name -> do nothing
                }

            }
        } catch (NullPointerException n) {
            // alert about null pointer
            System.err.println(n.getMessage());
        }
    }

    
    private void updateCommentTable(JTable tbl, ArrayList<Post> commentList) throws InvalidParameterException {
        String[] col = {"Comment ID", "Title", "Body"};
        TableModel tableModel = new TableModel(col, 0);
        try {

            tbl.setModel(tableModel);

            // for each group in groupList, add it as a row in the table
            for (Post comment : commentList) {

                // table headers
                Object obj[] = new Object[5];

                obj[0] = comment.hashCode() % 256; // comment ID
                obj[1] = comment.getTitle();
                obj[2] = comment.getBody();
                
                // there is only one place where comments go. 
                // no need for if statement
                tableModel.addRow(obj);


            }
        } catch (NullPointerException n) {
            // alert about null pointer
            System.err.println(n.getMessage());
        }
    }


	private void jTabbedPaneGroupsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPaneGroupsMouseClicked
        
        // list all groups that exist into the table
        updateGroupTable(browseTable, "browse", sessionServer.getGroupList());
        updateGroupTable(feedTable, "feed", sessionServer.getGroupList());
    }//GEN-LAST:event_jTabbedPaneGroupsMouseClicked

    private void groupSearchButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_groupSearchButtonMouseClicked
        // Grab the text in the search field

        if (!groupSearchField.getText().equals("")) {
            // get the search text
            String searchText = groupSearchField.getText();
            
            // get the searched array and add the elements to the table
            updateGroupTable(browseTable, "browse", sessionServer.searchedGroups(searchText));
            
        } else {
            updateGroupTable(browseTable, "browse", sessionServer.getGroupList());
        }

    }//GEN-LAST:event_groupSearchButtonMouseClicked

    private void createGroupCancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createGroupCancelButtonMouseClicked
        // Canceled; Go back to the other view
        CardLayout layout = (CardLayout) jPanelGroups.getLayout();
        layout.show(jPanelGroups, "browseAllCard");

        // clear the fields
        groupCategoriesField.setText("");
        groupNameField.setText("");
        groupTagsField.setText("");

    }//GEN-LAST:event_createGroupCancelButtonMouseClicked

    private void createGroupButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createGroupButtonMouseClicked
        // Create a new group
        
        // get the active user
        if(sessionServer.getActiveUser() != null){
            // check whether the group already exists with the same name
            if (!sessionServer.groupExists(groupNameField.getText())) {
                
                // if the group does not exist, create one
                if (!groupNameField.getText().isEmpty()
                        && !groupCategoriesField.getText().isEmpty()
                        && !groupTagsField.getText().isEmpty()) {

                    String name = groupNameField.getText();
                    String categories = groupCategoriesField.getText();
                    String tags = groupTagsField.getText();

                    // create the group in the array in the server
                    sessionServer.createGroup(name, categories, tags);
                    System.out.println("CREATING GROUP");
                    
                    // alert success
                    JOptionPane.showMessageDialog(rootPane,
                            "Group " + groupNameField.getText() + " Created.",
                            "Success", 1);

                    
                    // now, take the user to their groups page
                    goToGroupPage(sessionServer.getActiveGroup());
                } else {
                    
                    // error message to indicate invalid entries
                    JOptionPane.showMessageDialog(rootPane,
                            "Please enter a group name, "
                            + "a list of group categories "
                            + "seperated by commas, and group "
                            + "tags seperated by commas.",
                            "Fields Cannot Be Blank", 1);
                }
            } else {
                
                // error message
                JOptionPane.showMessageDialog(rootPane,
                        "This name is already taken.", "Error", 0);
            }
        } else {
            
            // error message
            JOptionPane.showMessageDialog(rootPane,
                    "Please Sign In.", "Error", 0);
        }

    }//GEN-LAST:event_createGroupButtonMouseClicked


    private void jTabbedPaneMainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPaneMainMouseClicked
        
        updateGroupTable(feedTable, "feed", sessionServer.getGroupList());
    }//GEN-LAST:event_jTabbedPaneMainMouseClicked

    /**
     * When the browse groups table is clicked, go to that group's page
     *
     * @param evt
     */
    private void browseTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browseTableMouseClicked
        try {
            // get the group row clicked
            String groupName = getGroupNameFromTable(browseTable, evt);
            goToGroupPage(sessionServer.getGroupByName(groupName));
        } catch (Exception e) {
            // do something
        }


    }//GEN-LAST:event_browseTableMouseClicked

    private void showCreatePostDialogButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showCreatePostDialogButtonMouseClicked
        // show a dialog box where you can create a new post
        createPostDialog.setAutoRequestFocus(true);
        createPostDialog.setVisible(true); // show the create post dialog window


    }//GEN-LAST:event_showCreatePostDialogButtonMouseClicked

    private void createPostButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createPostButtonMouseClicked
        

        if (!postTitleField.getText().isEmpty()
                && !postBodyField.getText().isEmpty()
                && !groupNameLabel.getText().isEmpty()) {
            try {

                Group grp = sessionServer.getGroupByName(groupNameLabel.getText());

                // add a new post to the group that is currently active in the 
                // group window
                grp.leavePost(new Post(
                        sessionServer.getActiveUser(),
                        postBodyField.getText(),
                        postTitleField.getText()
                )
                );

                // clear the fields and close the window. 
                postBodyField.setText("");
                postTitleField.setText("");
                createPostDialog.setVisible(false);

                // then update the group window
                updatePostTable(grp);
                JOptionPane.showMessageDialog(null, "Post Created Successfully");
            } catch (NullPointerException n) {
                
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a post title & body.");
        }

    }//GEN-LAST:event_createPostButtonMouseClicked

    private void updatePostTable(Group grp) {
        String col[] = {"Post ID", "Title", "Points"};
        TableModel tableModel = new TableModel(col, 0);
        groupPostTable.setModel(tableModel);

        // populate the posts
        for (Post post : grp.getPosts()) {

            Object[] obj = new Object[3];
            obj[0] = post.hashCode() % 256;
            obj[1] = post.getTitle();
            obj[2] = post.getPoints();

            tableModel.addRow(obj);
        }
    }

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // close the create post dialog box and pretend nothing happened
        createPostDialog.setVisible(false); // nothing to see here folks
        postBodyField.setText("");
        postTitleField.setText("");
    }//GEN-LAST:event_jButton2MouseClicked

        @SuppressWarnings("deprecation")
		private void jButtonLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonLoginMouseClicked
                
		// Get user.
		User curUser = sessionServer.getUserFromUsername(this.jTextFieldLoginUsername.getText().trim());
		
		// Check if user was found and password matches.
		if (curUser != null && 
			curUser.getPassword().equals(this.jPasswordFieldLoginPassword.getText().trim())) {
		
			// Set result.
			this.lsDialogResult = curUser;
			
			this.jDialogLoginSignup.setVisible(false);
		}
		// Could not log in.
		else {
		
			JOptionPane.showMessageDialog(this.jDialogLoginSignup, 
				"Could not log in.\nEither user does not exist or password is incorrect.",
				"Login Failed", JOptionPane.ERROR_MESSAGE);
		}
        }//GEN-LAST:event_jButtonLoginMouseClicked

        private void jButtonLoginCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonLoginCancelMouseClicked

		// Hide login/signup dialog.
		this.jDialogLoginSignup.setVisible(false);
        }//GEN-LAST:event_jButtonLoginCancelMouseClicked

        private void jButtonSignupCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSignupCancelMouseClicked

		// Hide login/signup dialog.
		this.jDialogLoginSignup.setVisible(false);
        }//GEN-LAST:event_jButtonSignupCancelMouseClicked

    private void groupPostTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_groupPostTableMouseClicked

        String postName = getPostNameFromTable(groupPostTable, evt, 1);
        Post post = sessionServer.getActiveGroup().getPostByTitle(postName);
        
        if(sessionServer.getActiveGroup().isMember(sessionServer.getActiveUser())){
            goToPostPage(post);
        }
        
    }//GEN-LAST:event_groupPostTableMouseClicked
 
   /* boolean postupClicked = false;
    boolean postdownClicked = false;*/

   // int countVote = 0;
    
    private void PostUpvoteButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PostUpvoteButtonMouseClicked
        // get the active post
        Post pst = sessionServer.getActivePost();
        // get the active user
        User user = sessionServer.getActiveUser();
       
		// increment the points
		pst.incrementPoints(user.getUsername());   
		pst.addVotedUsers(user.getUsername()); //add user to voted list

		// refresh the page
        goToPostPage(pst);
        
  
    }//GEN-LAST:event_PostUpvoteButtonMouseClicked

    private void PostDownvoteButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PostDownvoteButtonMouseClicked
        // get the active post
        Post pst = sessionServer.getActivePost();
        // get the active user
        User user = sessionServer.getActiveUser();
       
        // decrement the points
        pst.decrementPoints(user.getUsername());
        pst.addVotedUsers(user.getUsername()); //add user to voted list
     
        // refresh the page
        goToPostPage(pst);
    }//GEN-LAST:event_PostDownvoteButtonMouseClicked

        @SuppressWarnings("deprecation")
		private void jButtonSignupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSignupMouseClicked
                
		// Get text field data.
		String username = this.jTextFieldSignupUsername.getText().trim();
		String password = this.jPasswordFieldSignupPassword.getText().trim();
		String passwordConf = this.jPasswordFieldSignupPasswordConfirm.getText().trim();
		
		// Get user.
		User curUser = sessionServer.getUserFromUsername(username);
		
		// Check if user already exists.
		if (curUser != null) {
		
			JOptionPane.showMessageDialog(this.jDialogLoginSignup, 
				"Could not sign up.\nUsername already exists.",
				"Signup Failed", JOptionPane.ERROR_MESSAGE);
			
			return;
		}
		// Check if passwords do not match.
		else if (!password.equals(passwordConf)) {
		
			JOptionPane.showMessageDialog(this.jDialogLoginSignup, 
				"Could not sign up.\nPasswords do not match.",
				"Signup Failed", JOptionPane.ERROR_MESSAGE);
			
			return;
		}
		// Signup allowed.
		else {
			
			// Create user.
			curUser = sessionServer.createUser(username, password);
			
			client.PutUser(curUser);
			
			// Set result.
			this.lsDialogResult = curUser;
			
			this.jDialogLoginSignup.setVisible(false);
		}
        }//GEN-LAST:event_jButtonSignupMouseClicked

    private void groupWindowJoinGroupButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_groupWindowJoinGroupButtonMouseClicked

        if (sessionServer.getActiveUser() != null) {
            sessionServer.getActiveGroup().addMember(sessionServer.getActiveUser());

            // refresh the page
            goToGroupPage(sessionServer.getActiveGroup());
        } else {
            JOptionPane.showMessageDialog(null, "Please Sign In", "Could Not Join Group", 0);
        }
    }//GEN-LAST:event_groupWindowJoinGroupButtonMouseClicked

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // UPDATE WINDOW
        updateGroupTable(feedTable, "feed", sessionServer.getGroupList());
        updateGroupTable(browseTable, "browse", sessionServer.getGroupList());
    }//GEN-LAST:event_formWindowGainedFocus

        private void jTableAccountActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAccountActivityMouseClicked
                
		// Fetch appropriate post and display it.
		String postName = getPostNameFromTable(this.jTableAccountActivity, evt, 1);
		goToPostPage(sessionServer.getActiveUser().getPostByTitle(postName));
        }//GEN-LAST:event_jTableAccountActivityMouseClicked

        @SuppressWarnings("deprecation")
		private void jButtonAccountUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAccountUpdateMouseClicked
                
			// Get active session user.
			User curUser = sessionServer.getActiveUser();

			// Check for valid user.
			if (curUser != null) {

				// Update account details.
				curUser.setEmail(this.jTextFieldAccountEmailEdit.getText().trim());
				curUser.setPhoneNum(this.jTextFieldAccountPhoneNumberEdit.getText().trim());
				curUser.setPassword(this.jPasswordFieldAccountPasswordEdit.getText().trim());
				curUser.setBio(this.jTextAreaAccountBioEdit.getText());
				curUser.setAge(Integer.parseInt(this.jTextAreaAccountAgeEdit.getText()));
				curUser.setMajor(this.jTextAreaAccountMajorEdit.getSelectedItem().toString());
				curUser.setYear(this.jTextAreaAccountYearEdit.getSelectedItem().toString());
				curUser.setLocation(this.jTextAreaAccountLocationEdit.getSelectedItem().toString());
				curUser.setInterest(this.jComboBoxInterest0.getSelectedItem().toString(), 0);
				curUser.setInterest(this.jComboBoxInterest1.getSelectedItem().toString(), 1);
				curUser.setInterest(this.jComboBoxInterest2.getSelectedItem().toString(), 2);
				curUser.setEthnicity(this.jTextAreaAccountEthnicityEdit.getSelectedItem().toString());
				curUser.setGender(this.jTextAreaAccountGenderEdit.getSelectedItem().toString());
				if(this.jTextAreaAccountGenderEdit.getSelectedIndex() == 0) {
					curUser.setMaritalStatus(false);
				}else {
					curUser.setMaritalStatus(true);
				}

				// Refresh display.
				this.refreshAccountTab();
				client.UpdateUser(curUser);
			}
        }//GEN-LAST:event_jButtonAccountUpdateMouseClicked

        private void jButtonAccountMustLogInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAccountMustLogInMouseClicked
                
		// Log user in.
		this.logUserIn(this.promptUserForLogin());
        }//GEN-LAST:event_jButtonAccountMustLogInMouseClicked

        private void jPanelAccountComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanelAccountComponentShown
                
		// Refresh display.
		this.refreshAccountTab();
        }//GEN-LAST:event_jPanelAccountComponentShown

    private void jButtonAccountMustLogInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAccountMustLogInActionPerformed
        
    }//GEN-LAST:event_jButtonAccountMustLogInActionPerformed

    private void feedTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_feedTableMouseClicked
        try {
            // get the group row clicked
            String groupName = getGroupNameFromTable(feedTable, evt);
            goToGroupPage(sessionServer.getGroupByName(groupName));

        } catch (Exception e) {
            
        }
    }//GEN-LAST:event_feedTableMouseClicked

    private void createAGroupCardButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createAGroupCardButtonMouseClicked
        // go to create a group page
        CardLayout layout = (CardLayout) jPanelGroups.getLayout();
        layout.show(jPanelGroups, "createGroupCard");// I got this card # from the properties. I cant change it
    }//GEN-LAST:event_createAGroupCardButtonMouseClicked

    private void yourGroupsCardButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_yourGroupsCardButtonMouseClicked
        // view your "feed"
        CardLayout layout = (CardLayout) jPanelGroups.getLayout();
        layout.show(jPanelGroups, "feedCard");// I got this card # from the properties. I cant change it
    }//GEN-LAST:event_yourGroupsCardButtonMouseClicked

    private void backToBrowseCardButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backToBrowseCardButtonMouseClicked
        // go back to "browse groups"
        CardLayout layout = (CardLayout) jPanelGroups.getLayout();
        layout.show(jPanelGroups, "browseAllCard");// I got this card # from the properties. I cant change it
    }//GEN-LAST:event_backToBrowseCardButtonMouseClicked

    private void createAGroupCardButtonFeedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createAGroupCardButtonFeedMouseClicked
        // go back to "Create A Group"
        CardLayout layout = (CardLayout) jPanelGroups.getLayout();
        layout.show(jPanelGroups, "createGroupCard");// I got this card # from the properties. I cant change it
    }//GEN-LAST:event_createAGroupCardButtonFeedMouseClicked

    private void browseGroupsButtonGroupPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseGroupsButtonGroupPageActionPerformed
        // go back to browse
        CardLayout layout = (CardLayout) jPanelGroups.getLayout();
        layout.show(jPanelGroups, "browseAllCard");// I got this card # from the properties. I cant change it
    }//GEN-LAST:event_browseGroupsButtonGroupPageActionPerformed

    private void feedButtonGroupPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_feedButtonGroupPageActionPerformed
        // go back to your feed
        CardLayout layout = (CardLayout) jPanelGroups.getLayout();
        layout.show(jPanelGroups, "feedCard");
    }//GEN-LAST:event_feedButtonGroupPageActionPerformed

    private void postCardBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postCardBackButtonActionPerformed
        
        // go back to the active group from the post
        goToGroupPage(sessionServer.getActiveGroup());
    }//GEN-LAST:event_postCardBackButtonActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        
        // leave a comment
        Post post = sessionServer.getActivePost();
        post.addComment(sessionServer.getActiveUser(), commentTextArea.getText(), commentTitleField.getText());
        
        goToPostPage(post);
        
    }//GEN-LAST:event_jButton1MouseClicked

    private void jComboBoxRelationsContextItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxRelationsContextItemStateChanged
        
		this.refreshAccountTab();
    }//GEN-LAST:event_jComboBoxRelationsContextItemStateChanged

    private void jButtonDumpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonDumpMouseClicked
        /*
		// Prompt user for dump location.
//		String filepath = JOptionPane.showInputDialog(this, 
//				"Select location to dump server image to.", 
//				"Dump Image", 
//				JOptionPane.QUESTION_MESSAGE);
        String filepath = null;
        JFileChooser chooser = new JFileChooser();
        if(chooser.showSaveDialog(jPanel1) == JFileChooser.APPROVE_OPTION){
            filepath = chooser.getSelectedFile().getAbsolutePath();
        }
		// Dump server image.
		if (filepath != null) {
			
			this.dumpSession(filepath);
		}*/
    	
    	client.PutUser(sessionServer.getActiveUser());
    }//GEN-LAST:event_jButtonDumpMouseClicked

    private void jButtonRestoreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonRestoreMouseClicked
        /*
		// Prompt user for dump location.
//		String filepath = JOptionPane.showInputDialog(this, 
//				"Select location to read server image from.", 
//				"Restore Image", 
//				JOptionPane.QUESTION_MESSAGE);
                String filepath = null;
		
                JFileChooser chooser = new JFileChooser();
                if(chooser.showOpenDialog(jPanel1) == JFileChooser.APPROVE_OPTION){
                    File file = chooser.getSelectedFile();
                    filepath = file.getAbsolutePath();
                }
                
		// Restore server image.
		if (filepath != null) {
			
			this.restoreSession(filepath);
		} */
    	
    	//client.listTables();

		// Refresh displays.
		this.refreshAccountTab();
		this.refreshUserStatusLabel();
                this.refreshGroupsTab();
    }//GEN-LAST:event_jButtonRestoreMouseClicked

    private void leaveGroupButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_leaveGroupButtonMouseClicked
        // have the active user leave the active group
        sessionServer.getActiveGroup().leaveGroup(sessionServer.getActiveUser());
        
        // refresh the page
        goToGroupPage(sessionServer.getActiveGroup());
    }//GEN-LAST:event_leaveGroupButtonMouseClicked

    private void deletePostButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deletePostButtonMouseClicked
        // remove the post
        if(sessionServer.getActivePost().getOwner().equals(sessionServer.getActiveUser())){
            System.out.println(sessionServer.getActiveGroup().removePost(sessionServer.getActivePost()));
        } else {
            JOptionPane.showMessageDialog(null, "You don't own this post");
        }
        
        goToGroupPage(sessionServer.getActiveGroup());
    }//GEN-LAST:event_deletePostButtonMouseClicked
    
    public void goToPostPage(Post post) {
        // CONFIGURE THE VIEW POST PAGE
        // THEN SHOW THE PAGE

        try {

            // set the label text
            viewPostTitleLabel.setText(post.getTitle());
            viewPostNumOfPoints.setText(Integer.toString(post.getPoints()));
            viewPostBodyArea.setText(post.getBody());

            // set the active post
            sessionServer.setActivePost(post);
            
            // initialize the comments
            updateCommentTable(commentTable, sessionServer.getActivePost().getCommentList());


            // show the card
            CardLayout layout = (CardLayout) jPanelGroups.getLayout();
            layout.show(jPanelGroups, "postCard");
            
            // now you have to switch over to the other tab
            jTabbedPaneMain.setSelectedIndex(1);

            
            if (!sessionServer.getActiveGroup().isMember(sessionServer.getActiveUser())) {
                // hide the upvote and downvote buttons
                PostUpvoteButton.setVisible(false);
                PostDownvoteButton.setVisible(false);
            }
            

        } catch (Exception e) {
            // DO SOMETHING
        }

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void goToGroupPage(Group group) {
        try {
            // GET THE GROUP VIEW SET UP -> INITIALIZE
            // set the labels 
            groupNameLabel.setText(group.getName());
            groupCategoriesLabel.setText(group.getCategories());
            groupTagsLabel.setText(group.getTags());

            // set the selection model for the list on the right of the screen
            groupMemberList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            DefaultListModel listModel = new DefaultListModel();

            // set the active group
            sessionServer.setActiveGroup(group);

            updatePostTable(group);

            // populate the members
            Vector<String> users = new Vector();

            for (User user : group.getMembers()) {
                System.out.printf("Username: %s", user.getUsername());
                users.add(user.getUsername());
            }

            // show the members of the group
            groupMemberList.setListData(users);

            // check to see if the current user is a member, and hide the create post button
            if (sessionServer == null || !group.isMember(sessionServer.getActiveUser())) {
                showCreatePostDialogButton.setVisible(false); // not a member, hide the button
                leaveGroupButton.setVisible(false);
            } else {
                showCreatePostDialogButton.setVisible(true); // is a member, dont hide button
                createPostButton.setVisible(true);
                leaveGroupButton.setVisible(true);

                
            }

            // check to see if the current user is a member of the group
            if (sessionServer == null || group.isMember(sessionServer.getActiveUser())) {
                groupWindowJoinGroupButton.setVisible(false); // is a member, hide the join button
            } else {
                groupWindowJoinGroupButton.setVisible(true); // not a member, show join button
            }

            // show group page
            CardLayout layout = (CardLayout) jPanelGroups.getLayout();
            layout.show(jPanelGroups, "groupPageCard");// I got this card # from the properties. I cant change it

        } catch (Exception e) {
            // do something
        }
    }
	
	private void refreshAccountTab() {

		// Get session user.
		User curUser = sessionServer.getActiveUser();
		
		// Check for active session user.
		if (curUser == null) {

			// Update shown account panel.
			LayoutManager panelLayout = this.jPanelAccount.getLayout();
			if (panelLayout instanceof CardLayout) {

				((CardLayout)panelLayout).show(this.jPanelAccount, "null");
			}
		}
		else {
			
			// Updated account details display.
			
			// Update account details component colors.
			this.jTextFieldAccountEmail.setBackground(GUI_THEME_COLOR);
			this.jTextFieldAccountPhoneNumber.setBackground(GUI_THEME_COLOR);
			this.jPasswordFieldAccountPassword.setBackground(GUI_THEME_COLOR);
			this.jTextAreaAccountBio.setBackground(GUI_THEME_COLOR);
			
			// Update account details component information.
			this.jTextFieldAccountEmail.setText(curUser.getEmail());
			this.jTextFieldAccountEmailEdit.setText(curUser.getEmail());
			this.jTextFieldAccountPhoneNumber.setText(curUser.getPhoneNum());
			this.jTextFieldAccountPhoneNumberEdit.setText(curUser.getPhoneNum());
			this.jPasswordFieldAccountPassword.setText(curUser.getPassword());
			this.jPasswordFieldAccountPasswordEdit.setText(curUser.getPassword());
			this.jTextAreaAccountBio.setText(curUser.getBio());
			this.jTextAreaAccountBioEdit.setText(curUser.getBio());
			this.jTextAreaAccountAge.setText(Integer.toString(curUser.getAge()));
			this.jTextAreaAccountMajor.setText(curUser.getMajor());
			this.jTextAreaAccountYear.setText(curUser.getYear());
			this.jTextAreaAccountLocation.setText(curUser.getLocation());
			this.jInterest0Text.setText(curUser.getInterest(0));
			this.jInterest1Text.setText(curUser.getInterest(1));
			this.jInterest2Text.setText(curUser.getInterest(2));
			this.jTextAreaAccountEthnicity.setText(curUser.getEthnicity());
			this.jTextAreaAccountGender.setText(curUser.getGender());
			if(curUser.getMaritalStatus()) {
				this.jTextAreaAccountMarital.setText("Married");
			}
			else {
				this.jTextAreaAccountMarital.setText("Unmarried");
			}
			
			// Update account activity display.
			
			// Create new TableModel for account activity table.
			TableModel tableModelActivity = new TableModel(new String[]{"Post ID", "Title", "Date"}, 0);
			this.jTableAccountActivity.setModel(tableModelActivity);

			// Populate account activity table with user's posts.
			for (Post userPost : curUser.getPosts()) {

				tableModelActivity.addRow(new String[]{
					Integer.toString(userPost.hashCode() % 256), 
					userPost.getTitle(), 
					userPost.getDate().format(DateTimeFormatter.ISO_DATE)});
			}
			
			// Update account relations display.
			
			// Update shown account panel.
			LayoutManager panelLayout = this.jPanelAccount.getLayout();
			if (panelLayout instanceof CardLayout) {

				((CardLayout)panelLayout).show(this.jPanelAccount, "valid");
			}
		}
	}
	
	private void refreshUserStatusLabel() {
	
		// Update displayed text and base color.
		if (sessionServer.getActiveUser() == null) {
		
			this.jLabelMainSessionUserStatus.setText(USER_STATUS_OUT_TEXT);
			this.jLabelMainSessionUserStatus.setForeground(USER_STATUS_OUT_COLOR);
		}
		else {
		
			this.jLabelMainSessionUserStatus.setText(sessionServer.getActiveUser().getUsername());
			this.jLabelMainSessionUserStatus.setForeground(USER_STATUS_IN_COLOR);
		}
		
		// Check if mouse is on label.
		Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
		Point relativeMouseLocation = new Point(
			mouseLocation.x - this.jLabelMainSessionUserStatus.getLocationOnScreen().x, 
			mouseLocation.y - this.jLabelMainSessionUserStatus.getLocationOnScreen().y);
		if (this.jLabelMainSessionUserStatus.contains(relativeMouseLocation)) {
		
			this.jLabelMainSessionUserStatus.setForeground(USER_STATUS_HOVER_COLOR);
		}
	}
	
	private void logUserIn(User newSessionUser) {
	
		// Log user in (or out if null).
		sessionServer.setSessionUser(newSessionUser);
		
		// Update displays.
		refreshAccountTab();
		refreshUserStatusLabel();
	}
	
	private User promptUserForLogin() {
	
		// User is not logged in.
		if (sessionServer.getActiveUser() == null) {

			// Initialize return value.
			this.lsDialogResult = null;
			
			// Show the login/signup dialog.
			this.jDialogLoginSignup.setVisible(true);
			
			return this.lsDialogResult;
		}
		// User is logged in.
		else {

			// Prompt user to log out.
			int choice = JOptionPane.showConfirmDialog(
				this.jDialogLoginSignup,
				"Are you sure you want to log out?",
				"Log Out",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

			// If user chose to log out.
			if (choice == JOptionPane.YES_OPTION) {

				return null;
			}
			else {
			
				return sessionServer.getActiveUser();
			}
		}
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton PostDownvoteButton;
    private javax.swing.JButton PostUpvoteButton;
    private javax.swing.JButton backToBrowseCardButton;
    private javax.swing.JButton browseGroupsButtonGroupPage;
    private javax.swing.JScrollPane browseGroupsPane;
    private javax.swing.JTable browseTable;
    private javax.swing.JTable commentTable;
    private javax.swing.JTextArea commentTextArea;
    private javax.swing.JTextField commentTitleField;
    private javax.swing.JButton createAGroupCardButton;
    private javax.swing.JButton createAGroupCardButtonFeed;
    private javax.swing.JButton createGroupButton;
    private javax.swing.JButton createGroupCancelButton;
    private javax.swing.JButton createPostButton;
    private javax.swing.JFrame createPostDialog;
    private javax.swing.JButton deletePostButton;
    private javax.swing.JButton feedButtonGroupPage;
    private javax.swing.JTable feedTable;
    private javax.swing.Box.Filler fillerAccount;
    private javax.swing.JTextField groupCategoriesField;
    private javax.swing.JLabel groupCategoriesLabel;
    private javax.swing.JList<String> groupMemberList;
    private javax.swing.JTextField groupNameField;
    private javax.swing.JLabel groupNameLabel;
    private javax.swing.JTable groupPostTable;
    private javax.swing.JButton groupSearchButton;
    private javax.swing.JTextField groupSearchField;
    private javax.swing.JTextField groupTagsField;
    private javax.swing.JLabel groupTagsLabel;
    private javax.swing.JButton groupWindowJoinGroupButton;
    private javax.swing.JPanel groupWindowPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonAccountMustLogIn;
    private javax.swing.JButton jButtonAccountUpdate;
    private javax.swing.JButton jButtonDump;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonLoginCancel;
    private javax.swing.JButton jButtonRestore;
    private javax.swing.JButton jButtonSignup;
    private javax.swing.JButton jButtonSignupCancel;
    private javax.swing.JButton jButtonSwitchLogin;
    private javax.swing.JButton jButtonSwitchSignup;
    private javax.swing.JComboBox<String> jComboBoxInterest0;
    private javax.swing.JComboBox<String> jComboBoxInterest1;
    private javax.swing.JComboBox<String> jComboBoxInterest2;
    private javax.swing.JDialog jDialogLoginSignup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAccountBio;
    private javax.swing.JLabel jLabelAccountEmail;
    private javax.swing.JLabel jLabelAccountPassword;
    private javax.swing.JLabel jLabelAccountPhoneNumber;
    private javax.swing.JLabel jLabelLogin;
    private javax.swing.JLabel jLabelLoginPassword;
    private javax.swing.JLabel jLabelLoginUsername;
    private javax.swing.JLabel jLabelMainSessionUserStatus;
    private javax.swing.JLabel jLabelSignup;
    private javax.swing.JLabel jLabelSignupPassword;
    private javax.swing.JLabel jLabelSignupPasswordConfirm;
    private javax.swing.JLabel jLabelSignupUsername;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelAccount;
    private javax.swing.JPanel jPanelAccountActivity;
    private javax.swing.JPanel jPanelAccountDetails;
    private javax.swing.JPanel jPanelAccountNull;
    private javax.swing.JPanel jPanelAllGroups;
    private javax.swing.JPanel jPanelCreateGroup;
    private javax.swing.JPanel jPanelFeedGroups;
    private javax.swing.JPanel jPanelGroups;
    private javax.swing.JPanel jPanelLogin;
    private javax.swing.JPanel jPanelLoginSignup;
    private javax.swing.JPanel jPanelSignup;
    private javax.swing.JPasswordField jPasswordFieldAccountPassword;
    private javax.swing.JPasswordField jPasswordFieldAccountPasswordEdit;
    private javax.swing.JPasswordField jPasswordFieldLoginPassword;
    private javax.swing.JPasswordField jPasswordFieldSignupPassword;
    private javax.swing.JPasswordField jPasswordFieldSignupPasswordConfirm;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPaneAccountActivity;
    private javax.swing.JScrollPane jScrollPaneAccountBio;
    private javax.swing.JScrollPane jScrollPaneAccountBioEdit;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPaneAcountValid;
    private javax.swing.JTabbedPane jTabbedPaneMain;
    private javax.swing.JTable jTableAccountActivity;
    private javax.swing.JTextField jTextAreaAccountAge;
    private javax.swing.JTextField jTextAreaAccountAgeEdit;
    private javax.swing.JTextArea jTextAreaAccountBio;
    private javax.swing.JTextArea jTextAreaAccountBioEdit;
    private javax.swing.JTextField jTextAreaAccountEthnicity;
    private javax.swing.JComboBox<String> jTextAreaAccountEthnicityEdit;
    private javax.swing.JTextField jTextAreaAccountGender;
    private javax.swing.JComboBox<String> jTextAreaAccountGenderEdit;
    private javax.swing.JTextField jTextAreaAccountLocation;
    private javax.swing.JComboBox<String> jTextAreaAccountLocationEdit;
    private javax.swing.JTextField jTextAreaAccountMajor;
    private javax.swing.JComboBox<String> jTextAreaAccountMajorEdit;
    private javax.swing.JTextField jTextAreaAccountMarital;
    private javax.swing.JComboBox<String> jTextAreaAccountMaritalEdit;
    private javax.swing.JTextField jTextAreaAccountYear;
    private javax.swing.JComboBox<String> jTextAreaAccountYearEdit;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jInterest2Text;
    private javax.swing.JTextField jInterest0Text;
    private javax.swing.JTextField jInterest1Text;
    private javax.swing.JTextField jTextFieldAccountEmail;
    private javax.swing.JTextField jTextFieldAccountEmailEdit;
    private javax.swing.JTextField jTextFieldAccountPhoneNumber;
    private javax.swing.JTextField jTextFieldAccountPhoneNumberEdit;
    private javax.swing.JTextField jTextFieldLoginUsername;
    private javax.swing.JTextField jTextFieldSignupUsername;
    private javax.swing.JButton leaveGroupButton;
    private javax.swing.JTextArea postBodyField;
    private javax.swing.JButton postCardBackButton;
    private javax.swing.JTextField postTitleField;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton showCreatePostDialogButton;
    private javax.swing.JTextArea viewPostBodyArea;
    private javax.swing.JLabel viewPostNumOfPoints;
    private javax.swing.JLabel viewPostTitleLabel;
    private javax.swing.JPanel viewPostWindowPanel;
    private javax.swing.JButton yourGroupsCardButton;
    // End of variables declaration//GEN-END:variables
}