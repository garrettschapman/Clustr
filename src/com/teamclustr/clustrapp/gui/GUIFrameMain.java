package com.teamclustr.clustrapp.gui;

import com.teamclustr.clustrapp.Server;
import com.teamclustr.clustrapp.communication.Post;
import com.teamclustr.clustrapp.representation.Group;
import com.teamclustr.clustrapp.representation.User;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.nio.file.Path;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * BRIEF CLASS DESCRIPTION.
 *
 * @author Team Clustr
 * @version 1.0 File: GUIFrameMain.java Created: 10/17/2017 Copyright (c) 2017,
 * Team Clustr, All rights reserved. Summary of Modifications: N/A
 */
public class GUIFrameMain extends javax.swing.JFrame {

    // MEMBER DATA.
    // The system of this session.
    public static Server sessionServer;

    // Active session user status constants.
    public static final String USER_STATUS_OUT_STRING = "Not Logged In";
    public static final Color USER_STATUS_IN_COLOR = Color.DARK_GRAY;
    public static final Color USER_STATUS_OUT_COLOR = Color.GRAY;
    public static final Color USER_STATUS_HOVER_COLOR = Color.LIGHT_GRAY;
    
    // colors for the gradient theme
    public static final Color GRADIENT_BOTTOM_COLOR = Color.decode("#90E0FF");
    public static final Color GRADIENT_TOP_COLOR = Color.WHITE;
    
    // Misc component theme constants.
    public static final Color GUI_THEME_COLOR = new Color(240, 240, 240);
    
    // Login/Signup dialog return value.
    private User lsDialogResult;

    /**
     * This method creates a translucent JScrollPane so that you can see the 
     * gradient behind the ScrollPane. 
     * @return 
     */
    public JScrollPane getTranslucentScrollPane(){
        JScrollPane pane = new JScrollPane();
        pane.getViewport().setOpaque(false); // set the viewport to translucent
        return pane;
    }
    
    /**
     * This method is called by the netbeans GUI BUILDER by changing the "custom code"
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
        jLabelMainSessionUserStatus.setText(USER_STATUS_OUT_STRING);
        jLabelMainSessionUserStatus.setForeground(USER_STATUS_OUT_COLOR);
        
        // Add the logo to the login button
        Path currentRelativePath = Paths.get("resources/");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
        File file = new File(s);
        for(String f : file.list()){
             System.out.println(f);
        }
        
        
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jDialogLoginSignup = new javax.swing.JDialog();
        jPanelLoginSignup = new javax.swing.JPanel();
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
        groupWindow = new javax.swing.JFrame();
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
        createPostDialog = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        createPostButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        postTitleField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        postBodyField = new javax.swing.JTextArea();
        viewPostWindow = new javax.swing.JFrame();
        viewPostWindowPanel = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        viewPostTitleLabel = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        PostUpvoteButton = new javax.swing.JButton();
        PostDownvoteButton = new javax.swing.JButton();
        viewPostNumOfPoints = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        viewPostBodyArea = new javax.swing.JTextArea();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
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
        jPanelAccountActivity = getGradientPanel();
        jScrollPaneAccountActivity = new javax.swing.JScrollPane();
        jTableAccountActivity = new javax.swing.JTable();
        jPanelGroups = new javax.swing.JPanel();
        jTabbedPaneGroups = new javax.swing.JTabbedPane();
        jPanelAllGroups = getGradientPanel();
        groupSearchField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        groupSearchButton = new javax.swing.JButton();
        browseGroupsPane = getTranslucentScrollPane();
        browseTable = new javax.swing.JTable();
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
        jPanelBrowseGroups = getGradientPanel();
        jScrollPane2 = getTranslucentScrollPane();
        feedTable = new javax.swing.JTable();
        jLabelMainSessionUserStatus = new javax.swing.JLabel();

        jDialogLoginSignup.setTitle("Login/Signup");
        jDialogLoginSignup.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jDialogLoginSignup.setIconImage(null);
        jDialogLoginSignup.setMinimumSize(new java.awt.Dimension(300, 325));
        jDialogLoginSignup.setModal(true);
        jDialogLoginSignup.setResizable(false);

        jPanelLoginSignup.setLayout(new java.awt.CardLayout());

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

        groupWindow.setTitle("Clustr");
        groupWindow.setMinimumSize(new java.awt.Dimension(800, 500));
        groupWindow.setName("groupWindow"); // NOI18N
        groupWindow.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                groupWindowWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

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

        javax.swing.GroupLayout groupWindowLayout = new javax.swing.GroupLayout(groupWindow.getContentPane());
        groupWindow.getContentPane().setLayout(groupWindowLayout);
        groupWindowLayout.setHorizontalGroup(
            groupWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(groupWindowLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(groupWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, groupWindowLayout.createSequentialGroup()
                        .addGroup(groupWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                            .addGroup(groupWindowLayout.createSequentialGroup()
                                .addGroup(groupWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(groupWindowLayout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(61, 61, 61)
                                        .addComponent(showCreatePostDialogButton)
                                        .addGap(43, 43, 43)
                                        .addComponent(groupWindowJoinGroupButton))
                                    .addGroup(groupWindowLayout.createSequentialGroup()
                                        .addComponent(groupNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(groupWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel6))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(groupWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(groupWindowLayout.createSequentialGroup()
                                .addGroup(groupWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, groupWindowLayout.createSequentialGroup()
                                .addGroup(groupWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(groupTagsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                                    .addComponent(groupCategoriesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(13, 13, 13)))))
                .addContainerGap())
        );
        groupWindowLayout.setVerticalGroup(
            groupWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(groupWindowLayout.createSequentialGroup()
                .addGroup(groupWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, groupWindowLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(groupCategoriesLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(groupTagsLabel))
                    .addGroup(groupWindowLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(groupWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(groupNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(groupWindowLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(groupWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(groupWindowLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(groupWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, groupWindowLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(groupWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(showCreatePostDialogButton)
                            .addComponent(groupWindowJoinGroupButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(groupWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(scrollPane))
                .addContainerGap())
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

        viewPostWindow.setMinimumSize(new java.awt.Dimension(400, 500));
        viewPostWindow.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                viewPostWindowWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

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

        jScrollPane5.setEnabled(false);

        viewPostBodyArea.setEditable(false);
        viewPostBodyArea.setColumns(20);
        viewPostBodyArea.setRows(5);
        jScrollPane5.setViewportView(viewPostBodyArea);

        javax.swing.GroupLayout viewPostWindowPanelLayout = new javax.swing.GroupLayout(viewPostWindowPanel);
        viewPostWindowPanel.setLayout(viewPostWindowPanelLayout);
        viewPostWindowPanelLayout.setHorizontalGroup(
            viewPostWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewPostWindowPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(viewPostWindowPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(viewPostWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(viewPostWindowPanelLayout.createSequentialGroup()
                        .addGroup(viewPostWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, viewPostWindowPanelLayout.createSequentialGroup()
                                .addGroup(viewPostWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel11))
                                .addGap(1, 1, 1)
                                .addComponent(PostUpvoteButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(viewPostWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(viewPostTitleLabel)
                                    .addGroup(viewPostWindowPanelLayout.createSequentialGroup()
                                        .addComponent(viewPostNumOfPoints)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(PostDownvoteButton)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator3))
                .addContainerGap())
        );
        viewPostWindowPanelLayout.setVerticalGroup(
            viewPostWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewPostWindowPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(viewPostWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(viewPostTitleLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(viewPostWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(PostDownvoteButton)
                    .addComponent(viewPostNumOfPoints)
                    .addComponent(PostUpvoteButton))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout viewPostWindowLayout = new javax.swing.GroupLayout(viewPostWindow.getContentPane());
        viewPostWindow.getContentPane().setLayout(viewPostWindowLayout);
        viewPostWindowLayout.setHorizontalGroup(
            viewPostWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewPostWindowPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        viewPostWindowLayout.setVerticalGroup(
            viewPostWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewPostWindowPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Clustr");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImages(null);
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
        jButtonAccountMustLogIn.setIcon(new javax.swing.ImageIcon(Paths.get("./resources/logo.png").toAbsolutePath().toString())); // NOI18N
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
        jPanelAccountDetailsValidLayout.columnWidths = new int[] {0, 5, 0, 5, 0, 5, 0};
        jPanelAccountDetailsValidLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanelAccountDetails.setLayout(jPanelAccountDetailsValidLayout);

        jLabelAccountEmail.setText("Email Address");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanelAccountDetails.add(jLabelAccountEmail, gridBagConstraints);

        jTextFieldAccountEmail.setEditable(false);
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
        jTextFieldAccountPhoneNumber.setPreferredSize(new java.awt.Dimension(250, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        jPanelAccountDetails.add(jTextFieldAccountPhoneNumber, gridBagConstraints);

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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        jPanelAccountDetails.add(jButtonAccountUpdate, gridBagConstraints);

        jPasswordFieldAccountPassword.setEditable(false);
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

        jTabbedPaneAcountValid.addTab("Details", jPanelAccountDetails);

        jTableAccountActivity.setAutoCreateRowSorter(true);
        jTableAccountActivity.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

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
            .addComponent(jScrollPaneAccountActivity, javax.swing.GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
        );
        jPanelAccountActivityLayout.setVerticalGroup(
            jPanelAccountActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneAccountActivity, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
        );

        jTabbedPaneAcountValid.addTab("Activity", jPanelAccountActivity);

        jPanelAccount.add(jTabbedPaneAcountValid, "valid");

        jTabbedPaneMain.addTab("Account", jPanelAccount);

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelAllGroupsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(browseGroupsPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelAllGroupsLayout.setVerticalGroup(
            jPanelAllGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAllGroupsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAllGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(groupSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(groupSearchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(browseGroupsPane, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPaneGroups.addTab("Browse All Groups", jPanelAllGroups);

        jPanel2.setOpaque(false);

        jLabel2.setText("Group Name");

        jLabel4.setText("Categories");

        jLabel5.setText("Tags");

        createGroupButton.setText("Create Group");
        createGroupButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createGroupButtonMouseClicked(evt);
            }
        });
        createGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createGroupButtonActionPerformed(evt);
            }
        });

        createGroupCancelButton.setText("Cancel");
        createGroupCancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createGroupCancelButtonMouseClicked(evt);
            }
        });

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
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(groupTagsField, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(groupNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(groupCategoriesField, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(createGroupButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(createGroupCancelButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(groupNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(groupCategoriesField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(groupTagsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createGroupButton)
                    .addComponent(createGroupCancelButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelCreateGroupLayout = new javax.swing.GroupLayout(jPanelCreateGroup);
        jPanelCreateGroup.setLayout(jPanelCreateGroupLayout);
        jPanelCreateGroupLayout.setHorizontalGroup(
            jPanelCreateGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCreateGroupLayout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(428, Short.MAX_VALUE))
        );
        jPanelCreateGroupLayout.setVerticalGroup(
            jPanelCreateGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCreateGroupLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(150, 150, 150))
        );

        jTabbedPaneGroups.addTab("Create Group", jPanelCreateGroup);

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

        javax.swing.GroupLayout jPanelBrowseGroupsLayout = new javax.swing.GroupLayout(jPanelBrowseGroups);
        jPanelBrowseGroups.setLayout(jPanelBrowseGroupsLayout);
        jPanelBrowseGroupsLayout.setHorizontalGroup(
            jPanelBrowseGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBrowseGroupsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelBrowseGroupsLayout.setVerticalGroup(
            jPanelBrowseGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBrowseGroupsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPaneGroups.addTab("Your Groups", jPanelBrowseGroups);

        javax.swing.GroupLayout jPanelGroupsLayout = new javax.swing.GroupLayout(jPanelGroups);
        jPanelGroups.setLayout(jPanelGroupsLayout);
        jPanelGroupsLayout.setHorizontalGroup(
            jPanelGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneGroups)
        );
        jPanelGroupsLayout.setVerticalGroup(
            jPanelGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneGroups)
        );

        jTabbedPaneMain.addTab("Groups", jPanelGroups);

        jLabelMainSessionUserStatus.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabelMainSessionUserStatus.setText("USER LOGIN STATUS");
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneMain)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelMainSessionUserStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelMainSessionUserStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        // TODO add your handling code here:
    }//GEN-LAST:event_groupSearchFieldActionPerformed

    private void createGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createGroupButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_createGroupButtonActionPerformed

    private String getGroupNameFromTable(JTable tbl, java.awt.event.MouseEvent evt) {
        int row = tbl.rowAtPoint(evt.getPoint());
        return (String) tbl.getModel().getValueAt(row, 0);
    }

    private String getPostNameFromTable(JTable tbl, java.awt.event.MouseEvent evt, int postNameColumn) {
        int row = tbl.rowAtPoint(evt.getPoint());
        return (String) tbl.getModel().getValueAt(row, postNameColumn);
    }

    /**
     *
     * @param tbl The table in question
     * @param tableName Either 'browse' or 'feed' depending on the table structure you
     * want
     */
    private void updateGroupTable(JTable tbl, String tableName) throws InvalidParameterException {
        String col[] = {"Group Name", "Number of Members", "Tags", "Categories", "Posts"};
        TableModel tableModel = new TableModel(col, 0);
        try {
            ArrayList<Group> groupList = sessionServer.getGroupList();

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

                    tableModel.addRow(obj);

                } else if (tableName.equals("feed")) {

                    if (group.getMembers().contains(sessionServer.getActiveUser())) {
                        tableModel.addRow(obj);
                    }

                } else {
                    throw new InvalidParameterException("Arg Type Not Recognized.");
                }

            }
        } catch (NullPointerException n) {
            // DO NOTHING
        }
    }


    private void jTabbedPaneGroupsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPaneGroupsMouseClicked
        // TODO add your handling code here:
        // list all groups that exist into the table
        updateGroupTable(browseTable, "browse");
        updateGroupTable(feedTable, "feed");
    }//GEN-LAST:event_jTabbedPaneGroupsMouseClicked

    private void groupSearchButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_groupSearchButtonMouseClicked
        // Grab the text in the search field

        if (!groupSearchField.getText().equals("")) {
            String searchText = groupSearchField.getText();

            TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();
            sorter.setRowFilter(RowFilter.regexFilter(searchText));
            browseTable.setRowSorter(sorter);
        }

    }//GEN-LAST:event_groupSearchButtonMouseClicked

    private void createGroupCancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createGroupCancelButtonMouseClicked
        // Canceled; Go to profile tab
        jTabbedPaneGroups.setSelectedIndex(0);

        // clear the fields
        groupCategoriesField.setText("");
        groupNameField.setText("");
        groupTagsField.setText("");

    }//GEN-LAST:event_createGroupCancelButtonMouseClicked

    private void createGroupButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createGroupButtonMouseClicked
        // TODO Create a new group
        if(sessionServer.getActiveUser() != null){
            if (!sessionServer.groupExists(groupNameField.getText())) {
                if (!groupNameField.getText().isEmpty()
                        && !groupCategoriesField.getText().isEmpty()
                        && !groupTagsField.getText().isEmpty()) {

                    String name = groupNameField.getText();
                    String categories = groupCategoriesField.getText();
                    String tags = groupTagsField.getText();

                    sessionServer.createGroup(name, categories, tags);
                    JOptionPane.showMessageDialog(rootPane,
                            "Group " + groupNameField.getText() + " Created.",
                            "Success", 1);

                } else {
                    JOptionPane.showMessageDialog(rootPane,
                            "Please enter a group name, "
                            + "a list of group categories "
                            + "seperated by commas, and group "
                            + "tags seperated by commas.",
                            "Fields Cannot Be Blank", 1);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane,
                        "This name is already taken.", "Error", 0);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane,
                    "Please Sign In.", "Error", 0);
        }

    }//GEN-LAST:event_createGroupButtonMouseClicked


    private void jTabbedPaneMainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPaneMainMouseClicked
        // TODO add your handling code here:
        updateGroupTable(feedTable, "feed");
    }//GEN-LAST:event_jTabbedPaneMainMouseClicked

    /**
     * When the browse groups table is clicked, go to that group's page
     *
     * @param evt
     */
    private void browseTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_browseTableMouseClicked
        try {
            // get the group row clicked
            //System.out.println("CLICKED!!");
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
        // TODO CREATE THE POST

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
                // TODO DO SOMETHING
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
        createPostDialog.setVisible(false);
        postBodyField.setText("");
        postTitleField.setText("");
    }//GEN-LAST:event_jButton2MouseClicked

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
        goToPostPage(sessionServer.getActiveGroup().getPostByTitle(postName));
    }//GEN-LAST:event_groupPostTableMouseClicked

    private void PostUpvoteButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PostUpvoteButtonMouseClicked
        // get the active post
        Post pst = sessionServer.getActivePost();

        // increment the points
        pst.incrementPoints();

        // refresh the page
        goToPostPage(pst);
    }//GEN-LAST:event_PostUpvoteButtonMouseClicked

    private void PostDownvoteButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PostDownvoteButtonMouseClicked
        // get the active post
        Post pst = sessionServer.getActivePost();

        // decrement the points
        pst.decrementPoints();

        // refresh the page
        goToPostPage(pst);
    }//GEN-LAST:event_PostDownvoteButtonMouseClicked

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

    private void groupWindowWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_groupWindowWindowGainedFocus
        // UPDATE WINDOW
        goToGroupPage(sessionServer.getActiveGroup());
    }//GEN-LAST:event_groupWindowWindowGainedFocus

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // UPDATE WINDOW
        updateGroupTable(feedTable, "feed");
        updateGroupTable(browseTable, "browse");
    }//GEN-LAST:event_formWindowGainedFocus

    private void viewPostWindowWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_viewPostWindowWindowGainedFocus
        viewPostWindowPanel.validate();
        viewPostWindow.repaint();
    }//GEN-LAST:event_viewPostWindowWindowGainedFocus

        private void jTableAccountActivityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAccountActivityMouseClicked
                
		// Fetch appropriate post and display it.
		String postName = getPostNameFromTable(this.jTableAccountActivity, evt, 1);
		goToPostPage(sessionServer.getActiveUser().getPostByTitle(postName));
        }//GEN-LAST:event_jTableAccountActivityMouseClicked

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
			
			// Refresh display.
			this.refreshAccountTab();
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
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAccountMustLogInActionPerformed

    private void feedTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_feedTableMouseClicked
        try {
            // get the group row clicked
            String groupName = getGroupNameFromTable(feedTable, evt);
            goToGroupPage(sessionServer.getGroupByName(groupName));

        } catch (Exception e) {
            // TODO do something
        }
    }//GEN-LAST:event_feedTableMouseClicked

    public void goToPostPage(Post post) {
        // TODO: CONFIGURE THE VIEW POST PAGE
        // THEN SHOW THE PAGE

        try {

            // set the label text
            viewPostTitleLabel.setText(post.getTitle());
            viewPostNumOfPoints.setText(Integer.toString(post.getPoints()));
            viewPostBodyArea.setText(post.getBody());

            // set the active post
            sessionServer.setActivePost(post);

            // repaint the screen and show
            viewPostWindow.validate();
            viewPostWindow.setVisible(true);
            
            if (!sessionServer.getActiveGroup().isMember(sessionServer.getActiveUser())) {
                // hide the upvote and downvote buttons
                PostUpvoteButton.setVisible(false);
                PostDownvoteButton.setVisible(false);
            }
            

        } catch (Exception e) {
            // DO SOMETHING
        }

    }

    private void goToGroupPage(Group group) {
        try {

            // set the labels 
            groupNameLabel.setText(group.getName());
            groupCategoriesLabel.setText(group.getCategories());
            groupTagsLabel.setText(group.getTags());

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

            groupMemberList.setListData(users);

            if (sessionServer == null || !group.isMember(sessionServer.getActiveUser())) {
                showCreatePostDialogButton.setVisible(false);
            } else {
                showCreatePostDialogButton.setVisible(true);
                createPostButton.setVisible(true);
                groupWindow.repaint();
                
            }

            if (sessionServer == null || group.isMember(sessionServer.getActiveUser())) {
                groupWindowJoinGroupButton.setVisible(false);
            } else {
                groupWindowJoinGroupButton.setVisible(true);
            }

            groupWindow.setAutoRequestFocus(true);
            groupWindow.setVisible(true);

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
			
			// Update account activity display.
			
			// Create new TableModel for account activity table.
			TableModel tableModel = new TableModel(new String[]{"Post ID", "Title", "Date"}, 0);
			this.jTableAccountActivity.setModel(tableModel);

			// Populate account activity table with user's posts.
			for (Post userPost : curUser.getPosts()) {

				tableModel.addRow(new String[]{
					Integer.toString(userPost.hashCode() % 256), 
					userPost.getTitle(), 
					userPost.getDate().format(DateTimeFormatter.ISO_DATE)});
			}
			
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
		
			this.jLabelMainSessionUserStatus.setText(USER_STATUS_OUT_STRING);
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
    private javax.swing.JScrollPane browseGroupsPane;
    private javax.swing.JTable browseTable;
    private javax.swing.JButton createGroupButton;
    private javax.swing.JButton createGroupCancelButton;
    private javax.swing.JButton createPostButton;
    private javax.swing.JFrame createPostDialog;
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
    private javax.swing.JFrame groupWindow;
    private javax.swing.JButton groupWindowJoinGroupButton;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonAccountMustLogIn;
    private javax.swing.JButton jButtonAccountUpdate;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonLoginCancel;
    private javax.swing.JButton jButtonSignup;
    private javax.swing.JButton jButtonSignupCancel;
    private javax.swing.JButton jButtonSwitchLogin;
    private javax.swing.JButton jButtonSwitchSignup;
    private javax.swing.JDialog jDialogLoginSignup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPanel jPanelBrowseGroups;
    private javax.swing.JPanel jPanelCreateGroup;
    private javax.swing.JPanel jPanelGroups;
    private javax.swing.JPanel jPanelLogin;
    private javax.swing.JPanel jPanelLoginSignup;
    private javax.swing.JPanel jPanelSignup;
    private javax.swing.JPasswordField jPasswordFieldAccountPassword;
    private javax.swing.JPasswordField jPasswordFieldAccountPasswordEdit;
    private javax.swing.JPasswordField jPasswordFieldLoginPassword;
    private javax.swing.JPasswordField jPasswordFieldSignupPassword;
    private javax.swing.JPasswordField jPasswordFieldSignupPasswordConfirm;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPaneAccountActivity;
    private javax.swing.JScrollPane jScrollPaneAccountBio;
    private javax.swing.JScrollPane jScrollPaneAccountBioEdit;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPaneAcountValid;
    private javax.swing.JTabbedPane jTabbedPaneGroups;
    private javax.swing.JTabbedPane jTabbedPaneMain;
    private javax.swing.JTable jTableAccountActivity;
    private javax.swing.JTextArea jTextAreaAccountBio;
    private javax.swing.JTextArea jTextAreaAccountBioEdit;
    private javax.swing.JTextField jTextFieldAccountEmail;
    private javax.swing.JTextField jTextFieldAccountEmailEdit;
    private javax.swing.JTextField jTextFieldAccountPhoneNumber;
    private javax.swing.JTextField jTextFieldAccountPhoneNumberEdit;
    private javax.swing.JTextField jTextFieldLoginUsername;
    private javax.swing.JTextField jTextFieldSignupUsername;
    private javax.swing.JTextArea postBodyField;
    private javax.swing.JTextField postTitleField;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton showCreatePostDialogButton;
    private javax.swing.JTextArea viewPostBodyArea;
    private javax.swing.JLabel viewPostNumOfPoints;
    private javax.swing.JLabel viewPostTitleLabel;
    private javax.swing.JFrame viewPostWindow;
    private javax.swing.JPanel viewPostWindowPanel;
    // End of variables declaration//GEN-END:variables
}
