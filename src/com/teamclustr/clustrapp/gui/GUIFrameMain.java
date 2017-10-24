package com.teamclustr.clustrapp.gui;

import com.teamclustr.clustrapp.Server;
import com.teamclustr.clustrapp.representation.Group;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * BRIEF CLASS DESCRIPTION.
 * 
 * @author Team Clustr
 * @version 1.0
 * File: GUIFrameMain.java
 * Created: 10/17/2017
 * Copyright (c) 2017, Team Clustr, All rights reserved.
 * Summary of Modifications:
 *  N/A
 */
public class GUIFrameMain extends javax.swing.JFrame {

	// MEMBER DATA.
	
	// The system of this session.
	public static Server sessionSystem;
	
	// Active session user status constants.
	public static String USER_STATUS_OUT_STRING = "Not Logged In";
	public static Color USER_STATUS_IN_COLOR = Color.BLUE;
	public static Color USER_STATUS_OUT_COLOR = Color.RED;
	public static Color USER_STATUS_HOVER_COLOR = Color.YELLOW;
	
	// MEMBER METHODS.
	
	/**
	 * Creates new form GUIFrameMain
	 */
	public GUIFrameMain() {
		
		// Initialize GUI components.
		initComponents();
		
		// Initialize session user status.
		jLabelMainSessionUserStatus.setText(USER_STATUS_OUT_STRING);
		jLabelMainSessionUserStatus.setForeground(USER_STATUS_OUT_COLOR);
                
	}
        
        
	/**
	 * Main program method.
	 * @param args command line arguments
	 */
	public static void main(String args[]) {
		
		// Call logic debug methods.
		garrettDebug(false);
		jiraDebug(false);
		tjDebug(false);
		
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
	
	// HELPER METHODS.
	
	private static void garrettDebug(boolean execute) {
	
		if (execute) {
		
			// Garrett's debug code.
		}
	}
	
	private static void jiraDebug(boolean execute) {
	
		if (execute) {
		
			// Jira's debug code.
		}
	}
	
	private static void tjDebug(boolean execute) {
	
		if (execute) {
		
			// TJ's debug code.
		}
	}
	
	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
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
        jButtonSIgnup = new javax.swing.JButton();
        jButtonSwitchLogin = new javax.swing.JButton();
        jButtonSignupCancel = new javax.swing.JButton();
        jTabbedPaneMain = new javax.swing.JTabbedPane();
        jTabbedPaneAcount = new javax.swing.JTabbedPane();
        jPanelAccountDetails = new javax.swing.JPanel();
        jPanelAccountActivity = new javax.swing.JPanel();
        jPanelDirectMessage = new javax.swing.JPanel();
        jTabbedPaneGroups = new javax.swing.JTabbedPane();
        jPanelBrowseGroups = new javax.swing.JPanel();
        jPanelCreateGroup = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        groupNameField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        groupCategoriesField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        groupTagsField = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jPanelAllGroups = new javax.swing.JPanel();
        groupSearchField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        groupSearchButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabelMainSessionUserStatus = new javax.swing.JLabel();

        jDialogLoginSignup.setTitle("Login/Signup");
        jDialogLoginSignup.setMinimumSize(new java.awt.Dimension(300, 300));
        jDialogLoginSignup.setModal(true);
        jDialogLoginSignup.setResizable(false);

        jPanelLoginSignup.setLayout(new java.awt.CardLayout());

        java.awt.GridBagLayout jPanelLoginLayout = new java.awt.GridBagLayout();
        jPanelLoginLayout.columnWidths = new int[] {0};
        jPanelLoginLayout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        jPanelLogin.setLayout(jPanelLoginLayout);

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

        jTextFieldLoginUsername.setPreferredSize(new java.awt.Dimension(250, 19));
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
        jPasswordFieldLoginPassword.setPreferredSize(new java.awt.Dimension(250, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        jPanelLogin.add(jPasswordFieldLoginPassword, gridBagConstraints);

        jButtonLogin.setText("Login");
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

        jTextFieldSignupUsername.setPreferredSize(new java.awt.Dimension(250, 19));
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
        jPasswordFieldSignupPassword.setPreferredSize(new java.awt.Dimension(250, 19));
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
        jPasswordFieldSignupPasswordConfirm.setPreferredSize(new java.awt.Dimension(250, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        jPanelSignup.add(jPasswordFieldSignupPasswordConfirm, gridBagConstraints);

        jButtonSIgnup.setText("Signup");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanelSignup.add(jButtonSIgnup, gridBagConstraints);

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
            .addComponent(jPanelLoginSignup, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Clustr");
        setIconImages(null);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        javax.swing.GroupLayout jPanelAccountDetailsLayout = new javax.swing.GroupLayout(jPanelAccountDetails);
        jPanelAccountDetails.setLayout(jPanelAccountDetailsLayout);
        jPanelAccountDetailsLayout.setHorizontalGroup(
            jPanelAccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
        );
        jPanelAccountDetailsLayout.setVerticalGroup(
            jPanelAccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );

        jTabbedPaneAcount.addTab("Details", jPanelAccountDetails);

        javax.swing.GroupLayout jPanelAccountActivityLayout = new javax.swing.GroupLayout(jPanelAccountActivity);
        jPanelAccountActivity.setLayout(jPanelAccountActivityLayout);
        jPanelAccountActivityLayout.setHorizontalGroup(
            jPanelAccountActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
        );
        jPanelAccountActivityLayout.setVerticalGroup(
            jPanelAccountActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );

        jTabbedPaneAcount.addTab("Activity", jPanelAccountActivity);

        javax.swing.GroupLayout jPanelDirectMessageLayout = new javax.swing.GroupLayout(jPanelDirectMessage);
        jPanelDirectMessage.setLayout(jPanelDirectMessageLayout);
        jPanelDirectMessageLayout.setHorizontalGroup(
            jPanelDirectMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
        );
        jPanelDirectMessageLayout.setVerticalGroup(
            jPanelDirectMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );

        jTabbedPaneAcount.addTab("Messages", jPanelDirectMessage);

        jTabbedPaneMain.addTab("Account", jTabbedPaneAcount);

        jTabbedPaneGroups.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPaneGroupsMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelBrowseGroupsLayout = new javax.swing.GroupLayout(jPanelBrowseGroups);
        jPanelBrowseGroups.setLayout(jPanelBrowseGroupsLayout);
        jPanelBrowseGroupsLayout.setHorizontalGroup(
            jPanelBrowseGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
        );
        jPanelBrowseGroupsLayout.setVerticalGroup(
            jPanelBrowseGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );

        jTabbedPaneGroups.addTab("Your Feed", jPanelBrowseGroups);

        jButton2.setText("Create Group");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Group Name");

        jLabel4.setText("Categories");

        jLabel5.setText("Tags");

        jButton3.setText("Cancel");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelCreateGroupLayout = new javax.swing.GroupLayout(jPanelCreateGroup);
        jPanelCreateGroup.setLayout(jPanelCreateGroupLayout);
        jPanelCreateGroupLayout.setHorizontalGroup(
            jPanelCreateGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCreateGroupLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanelCreateGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanelCreateGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(groupNameField)
                    .addComponent(groupCategoriesField)
                    .addComponent(groupTagsField, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCreateGroupLayout.createSequentialGroup()
                .addContainerGap(324, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap())
        );
        jPanelCreateGroupLayout.setVerticalGroup(
            jPanelCreateGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCreateGroupLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanelCreateGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(groupNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(jPanelCreateGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(groupCategoriesField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelCreateGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(groupTagsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65)
                .addGroup(jPanelCreateGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        jTabbedPaneGroups.addTab("Create Group", jPanelCreateGroup);

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

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Group Name", "Number Of Members", "Tags", "Categories", "Join"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPaneGroups.addTab("Browse All Groups", jPanelAllGroups);

        jTabbedPaneMain.addTab("Groups", jTabbedPaneGroups);

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
	
        private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
                
		// Prompt the user for a system to load.
		JOptionPane.showMessageDialog(
			this, 
			"<TODO: ALLOW USER TO LOAD SYSTEM INFORMATION HERE>", 
			"Load System Information", 
			JOptionPane.PLAIN_MESSAGE);
		
		// Initialize the session system.
		sessionSystem = new Server();
        }//GEN-LAST:event_formWindowOpened

        private void jLabelMainSessionUserStatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMainSessionUserStatusMouseClicked
                
		// User is not logged in.
		if (sessionSystem.getSessionUser() == null) {
		
			// Prompt the user for login information.
			jDialogLoginSignup.setVisible(true);
		}
		// User is logged in.
		else {
			
			
		}
        }//GEN-LAST:event_jLabelMainSessionUserStatusMouseClicked

        private void jLabelMainSessionUserStatusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMainSessionUserStatusMouseEntered
                
		// Change label color.
		jLabelMainSessionUserStatus.setForeground(USER_STATUS_HOVER_COLOR);
        }//GEN-LAST:event_jLabelMainSessionUserStatusMouseEntered

        private void jLabelMainSessionUserStatusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMainSessionUserStatusMouseExited
                
		// Change label color.
		if (sessionSystem.getSessionUser() == null) {
		
			jLabelMainSessionUserStatus.setForeground(USER_STATUS_OUT_COLOR);
		}
		else {
		
			jLabelMainSessionUserStatus.setForeground(USER_STATUS_IN_COLOR);
		}
        }//GEN-LAST:event_jLabelMainSessionUserStatusMouseExited

        private void jButtonSwitchSignupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSwitchSignupMouseClicked
                
		// Switch to login panel.
		LayoutManager dialogLayout = jPanelLoginSignup.getLayout();
		if (dialogLayout instanceof CardLayout) {
		
			((CardLayout)dialogLayout).next(jPanelLoginSignup);
		}
        }//GEN-LAST:event_jButtonSwitchSignupMouseClicked

        private void jButtonSwitchLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSwitchLoginMouseClicked
                
		// Switch to signup panel.
		LayoutManager dialogLayout = jPanelLoginSignup.getLayout();
		if (dialogLayout instanceof CardLayout) {
		
			((CardLayout)dialogLayout).next(jPanelLoginSignup);
		}
        }//GEN-LAST:event_jButtonSwitchLoginMouseClicked

    private void groupSearchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupSearchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_groupSearchFieldActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTabbedPaneGroupsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPaneGroupsMouseClicked
        // TODO add your handling code here:
        // list all groups that exist into the table
        String col[] = {"Group Name", "Number of Members", "Tags", "Categories", ""};
        DefaultTableModel tabelModel = new DefaultTableModel(col, 0);
        ArrayList<Group> groupList = sessionSystem.getGroupList();
        jTable1.setModel(tabelModel);
        // for each group in groupList, add it as a row in the table
        for(Group group : groupList){
            System.out.println(group.getName());
            Object obj[] = new Object[5];
            
            obj[0] = group.getName();
            obj[1] = group.getMembers().size();
            obj[2] = group.getTags();
            obj[3] = group.getCategories();
            obj[4] = "Join";
            
            tabelModel.addRow(obj);
        }
    }//GEN-LAST:event_jTabbedPaneGroupsMouseClicked

    private void groupSearchButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_groupSearchButtonMouseClicked
        // Grab the text in the search field
        String searchText = groupSearchField.getText();

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(); 
        sorter.setRowFilter(RowFilter.regexFilter(searchText));
        jTable1.setRowSorter(sorter);

        // TODO: search group functionality
    }//GEN-LAST:event_groupSearchButtonMouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // Canceled; Go to profile tab
        jTabbedPaneGroups.setSelectedIndex(0);
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO Create a new group
        
    }//GEN-LAST:event_jButton2MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField groupCategoriesField;
    private javax.swing.JTextField groupNameField;
    private javax.swing.JButton groupSearchButton;
    private javax.swing.JTextField groupSearchField;
    private javax.swing.JTextField groupTagsField;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonLoginCancel;
    private javax.swing.JButton jButtonSIgnup;
    private javax.swing.JButton jButtonSignupCancel;
    private javax.swing.JButton jButtonSwitchLogin;
    private javax.swing.JButton jButtonSwitchSignup;
    private javax.swing.JDialog jDialogLoginSignup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelLogin;
    private javax.swing.JLabel jLabelLoginPassword;
    private javax.swing.JLabel jLabelLoginUsername;
    private javax.swing.JLabel jLabelMainSessionUserStatus;
    private javax.swing.JLabel jLabelSignup;
    private javax.swing.JLabel jLabelSignupPassword;
    private javax.swing.JLabel jLabelSignupPasswordConfirm;
    private javax.swing.JLabel jLabelSignupUsername;
    private javax.swing.JPanel jPanelAccountActivity;
    private javax.swing.JPanel jPanelAccountDetails;
    private javax.swing.JPanel jPanelAllGroups;
    private javax.swing.JPanel jPanelBrowseGroups;
    private javax.swing.JPanel jPanelCreateGroup;
    private javax.swing.JPanel jPanelDirectMessage;
    private javax.swing.JPanel jPanelLogin;
    private javax.swing.JPanel jPanelLoginSignup;
    private javax.swing.JPanel jPanelSignup;
    private javax.swing.JPasswordField jPasswordFieldLoginPassword;
    private javax.swing.JPasswordField jPasswordFieldSignupPassword;
    private javax.swing.JPasswordField jPasswordFieldSignupPasswordConfirm;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPaneAcount;
    private javax.swing.JTabbedPane jTabbedPaneGroups;
    private javax.swing.JTabbedPane jTabbedPaneMain;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldLoginUsername;
    private javax.swing.JTextField jTextFieldSignupUsername;
    // End of variables declaration//GEN-END:variables
}
