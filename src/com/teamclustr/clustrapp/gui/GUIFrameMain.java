package com.teamclustr.clustrapp.gui;

import com.teamclustr.clustrapp.System;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.JOptionPane;

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
	public static System sessionSystem;
	
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
                jPanelFocusGroup = new javax.swing.JPanel();
                jLabelMainSessionUserStatus = new javax.swing.JLabel();

                jDialogLoginSignup.setTitle("Login/Signup");
                jDialogLoginSignup.setMinimumSize(new java.awt.Dimension(276, 262));
                jDialogLoginSignup.setModal(true);
                jDialogLoginSignup.setPreferredSize(new java.awt.Dimension(276, 262));
                jDialogLoginSignup.setResizable(false);
                jDialogLoginSignup.getContentPane().setLayout(new java.awt.CardLayout());

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

                jDialogLoginSignup.getContentPane().add(jPanelLogin, "card2");

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

                jDialogLoginSignup.getContentPane().add(jPanelSignup, "card3");

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setTitle("Clustr");
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

                jTabbedPaneGroups.addTab("Browse", jPanelBrowseGroups);

                javax.swing.GroupLayout jPanelFocusGroupLayout = new javax.swing.GroupLayout(jPanelFocusGroup);
                jPanelFocusGroup.setLayout(jPanelFocusGroupLayout);
                jPanelFocusGroupLayout.setHorizontalGroup(
                        jPanelFocusGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 514, Short.MAX_VALUE)
                );
                jPanelFocusGroupLayout.setVerticalGroup(
                        jPanelFocusGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 235, Short.MAX_VALUE)
                );

                jTabbedPaneGroups.addTab("Focused", jPanelFocusGroup);

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
		sessionSystem = new System();
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
		LayoutManager dialogLayout = jDialogLoginSignup.getLayout();
		if (dialogLayout instanceof CardLayout) {
		
			((CardLayout)dialogLayout).next(jDialogLoginSignup);
		}
        }//GEN-LAST:event_jButtonSwitchSignupMouseClicked

        private void jButtonSwitchLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSwitchLoginMouseClicked
                
		// Switch to signup panel.
		LayoutManager dialogLayout = jDialogLoginSignup.getLayout();
		if (dialogLayout instanceof CardLayout) {
		
			((CardLayout)dialogLayout).show(jDialogLoginSignup, jPanelSignup.getName());
		}
        }//GEN-LAST:event_jButtonSwitchLoginMouseClicked

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton jButtonLogin;
        private javax.swing.JButton jButtonLoginCancel;
        private javax.swing.JButton jButtonSIgnup;
        private javax.swing.JButton jButtonSignupCancel;
        private javax.swing.JButton jButtonSwitchLogin;
        private javax.swing.JButton jButtonSwitchSignup;
        private javax.swing.JDialog jDialogLoginSignup;
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
        private javax.swing.JPanel jPanelBrowseGroups;
        private javax.swing.JPanel jPanelDirectMessage;
        private javax.swing.JPanel jPanelFocusGroup;
        private javax.swing.JPanel jPanelLogin;
        private javax.swing.JPanel jPanelSignup;
        private javax.swing.JPasswordField jPasswordFieldLoginPassword;
        private javax.swing.JPasswordField jPasswordFieldSignupPassword;
        private javax.swing.JPasswordField jPasswordFieldSignupPasswordConfirm;
        private javax.swing.JTabbedPane jTabbedPaneAcount;
        private javax.swing.JTabbedPane jTabbedPaneGroups;
        private javax.swing.JTabbedPane jTabbedPaneMain;
        private javax.swing.JTextField jTextFieldLoginUsername;
        private javax.swing.JTextField jTextFieldSignupUsername;
        // End of variables declaration//GEN-END:variables
}
