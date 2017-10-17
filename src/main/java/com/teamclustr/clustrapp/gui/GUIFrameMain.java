/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamclustr.clustrapp.gui;

/**
 *
 * @author garrett
 */
public class GUIFrameMain extends javax.swing.JFrame {

	/**
	 * Creates new form GUIFrameMain
	 */
	public GUIFrameMain() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jLabelMainAccountStatus = new javax.swing.JLabel();
                jTabbedPaneMain = new javax.swing.JTabbedPane();
                jTabbedPaneAcount = new javax.swing.JTabbedPane();
                jPanelAccountDetails = new javax.swing.JPanel();
                jPanelAccountActivity = new javax.swing.JPanel();
                jTabbedPaneGroups = new javax.swing.JTabbedPane();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setTitle("Clustr");

                jLabelMainAccountStatus.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
                jLabelMainAccountStatus.setText("Not Signed In");
                jLabelMainAccountStatus.setVerticalAlignment(javax.swing.SwingConstants.TOP);
                jLabelMainAccountStatus.setPreferredSize(new java.awt.Dimension(200, 15));

                javax.swing.GroupLayout jPanelAccountDetailsLayout = new javax.swing.GroupLayout(jPanelAccountDetails);
                jPanelAccountDetails.setLayout(jPanelAccountDetailsLayout);
                jPanelAccountDetailsLayout.setHorizontalGroup(
                        jPanelAccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 639, Short.MAX_VALUE)
                );
                jPanelAccountDetailsLayout.setVerticalGroup(
                        jPanelAccountDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 266, Short.MAX_VALUE)
                );

                jTabbedPaneAcount.addTab("Details", jPanelAccountDetails);

                javax.swing.GroupLayout jPanelAccountActivityLayout = new javax.swing.GroupLayout(jPanelAccountActivity);
                jPanelAccountActivity.setLayout(jPanelAccountActivityLayout);
                jPanelAccountActivityLayout.setHorizontalGroup(
                        jPanelAccountActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 639, Short.MAX_VALUE)
                );
                jPanelAccountActivityLayout.setVerticalGroup(
                        jPanelAccountActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 266, Short.MAX_VALUE)
                );

                jTabbedPaneAcount.addTab("Activity", jPanelAccountActivity);

                jTabbedPaneMain.addTab("Account", jTabbedPaneAcount);
                jTabbedPaneMain.addTab("Groups", jTabbedPaneGroups);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPaneMain, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabelMainAccountStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPaneMain, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(5, 5, 5)
                                        .addComponent(jLabelMainAccountStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

	/**
	 * @param args the command line arguments
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

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel jLabelMainAccountStatus;
        private javax.swing.JPanel jPanelAccountActivity;
        private javax.swing.JPanel jPanelAccountDetails;
        private javax.swing.JTabbedPane jTabbedPaneAcount;
        private javax.swing.JTabbedPane jTabbedPaneGroups;
        private javax.swing.JTabbedPane jTabbedPaneMain;
        // End of variables declaration//GEN-END:variables
}
