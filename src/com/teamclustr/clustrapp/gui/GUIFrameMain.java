package com.teamclustr.clustrapp.gui;

import com.teamclustr.clustrapp.System;
import java.awt.Color;
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
	
	// MEMBER METHODS.
	
	/**
	 * Creates new form GUIFrameMain
	 */
	public GUIFrameMain() {
		
		initComponents();
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

                jTabbedPaneMain = new javax.swing.JTabbedPane();
                jTabbedPaneAcount = new javax.swing.JTabbedPane();
                jPanelAccountDetails = new javax.swing.JPanel();
                jPanelAccountActivity = new javax.swing.JPanel();
                jPanelDirectMessage = new javax.swing.JPanel();
                jTabbedPaneGroups = new javax.swing.JTabbedPane();
                jPanelBrowseGroups = new javax.swing.JPanel();
                jPanelFocusGroup = new javax.swing.JPanel();
                jLabelMainAccountStatus = new javax.swing.JLabel();

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
                        .addGap(0, 245, Short.MAX_VALUE)
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
                        .addGap(0, 245, Short.MAX_VALUE)
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
                        .addGap(0, 245, Short.MAX_VALUE)
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
                        .addGap(0, 245, Short.MAX_VALUE)
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
                        .addGap(0, 245, Short.MAX_VALUE)
                );

                jTabbedPaneGroups.addTab("Focused", jPanelFocusGroup);

                jTabbedPaneMain.addTab("Groups", jTabbedPaneGroups);

                jLabelMainAccountStatus.setForeground(new java.awt.Color(255, 0, 0));
                jLabelMainAccountStatus.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
                jLabelMainAccountStatus.setText("Not Signed In");
                jLabelMainAccountStatus.setVerticalAlignment(javax.swing.SwingConstants.TOP);
                jLabelMainAccountStatus.setPreferredSize(new java.awt.Dimension(0, 15));
                jLabelMainAccountStatus.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jLabelMainAccountStatusMouseClicked(evt);
                        }
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                                jLabelMainAccountStatusMouseEntered(evt);
                        }
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                                jLabelMainAccountStatusMouseExited(evt);
                        }
                });

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPaneMain)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabelMainAccountStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabelMainAccountStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTabbedPaneMain))
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents
	
        private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
                
		// Prompt the user for a system to load.
		JOptionPane.showMessageDialog(
			this, 
			"In the future, this pop-up will allow the user to selected a system to load.", 
			"Load System", 
			JOptionPane.PLAIN_MESSAGE);
		
		// Initialize the session system.
		GUIFrameMain.sessionSystem = new System();
        }//GEN-LAST:event_formWindowOpened

        private void jLabelMainAccountStatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMainAccountStatusMouseClicked
                
		// Check if the user is not signed in.
		if (GUIFrameMain.sessionSystem.getActiveAccount() == null) {
		
			// Prompt the user for login information.
			JOptionPane.showMessageDialog(
				this, 
				"In the future, this pop-up will allow the user to login to an account.", 
				"Login", 
				JOptionPane.PLAIN_MESSAGE);
		}
		// User is signed in.
		else {
			
			
		}
        }//GEN-LAST:event_jLabelMainAccountStatusMouseClicked

        private void jLabelMainAccountStatusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMainAccountStatusMouseEntered
                
		// Change label color.
		jLabelMainAccountStatus.setForeground(Color.YELLOW);
        }//GEN-LAST:event_jLabelMainAccountStatusMouseEntered

        private void jLabelMainAccountStatusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMainAccountStatusMouseExited
                
		// Change label color.
		jLabelMainAccountStatus.setForeground(Color.RED);
        }//GEN-LAST:event_jLabelMainAccountStatusMouseExited

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel jLabelMainAccountStatus;
        private javax.swing.JPanel jPanelAccountActivity;
        private javax.swing.JPanel jPanelAccountDetails;
        private javax.swing.JPanel jPanelBrowseGroups;
        private javax.swing.JPanel jPanelDirectMessage;
        private javax.swing.JPanel jPanelFocusGroup;
        private javax.swing.JTabbedPane jTabbedPaneAcount;
        private javax.swing.JTabbedPane jTabbedPaneGroups;
        private javax.swing.JTabbedPane jTabbedPaneMain;
        // End of variables declaration//GEN-END:variables
}
