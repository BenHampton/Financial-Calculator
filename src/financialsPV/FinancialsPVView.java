/*
 * FinancialsPVView.java
 */

package financialsPV;

import business.Annuity;
import business.Financial;
import business.Loan;
import business.PresentValue;
import java.awt.ScrollPane;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * The application's main frame.
 */
public class FinancialsPVView extends FrameView {
    Financial f;
    public FinancialsPVView(SingleFrameApplication app) {
        super(app);

        initComponents();
        
        
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = FinancialsPVApp.getApplication().getMainFrame();
            aboutBox = new FinancialsPVAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        FinancialsPVApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jlblAmt = new javax.swing.JLabel();
        jtxtAmt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtxtRate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtxtTerm = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jlblResult = new javax.swing.JLabel();
        jtxtResult = new javax.swing.JTextField();
        jradAnnuity = new javax.swing.JRadioButton();
        jradLoan = new javax.swing.JRadioButton();
        jbtnCalc = new javax.swing.JButton();
        jbtnSched = new javax.swing.JButton();
        jbtnClear = new javax.swing.JButton();
        jradPV = new javax.swing.JRadioButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(financialsPV.FinancialsPVApp.class).getContext().getResourceMap(FinancialsPVView.class);
        jlblAmt.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jlblAmt.setText(resourceMap.getString("jlblAmt.text")); // NOI18N
        jlblAmt.setName("jlblAmt"); // NOI18N

        jtxtAmt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtAmt.setText(resourceMap.getString("jtxtAmt.text")); // NOI18N
        jtxtAmt.setToolTipText(resourceMap.getString("jtxtAmt.toolTipText")); // NOI18N
        jtxtAmt.setName("jtxtAmt"); // NOI18N

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jtxtRate.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtRate.setText(resourceMap.getString("jtxtRate.text")); // NOI18N
        jtxtRate.setToolTipText(resourceMap.getString("jtxtRate.toolTipText")); // NOI18N
        jtxtRate.setName("jtxtRate"); // NOI18N

        jLabel3.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jtxtTerm.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtTerm.setText(resourceMap.getString("jtxtTerm.text")); // NOI18N
        jtxtTerm.setToolTipText(resourceMap.getString("jtxtTerm.toolTipText")); // NOI18N
        jtxtTerm.setName("jtxtTerm"); // NOI18N

        jLabel5.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jlblResult.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jlblResult.setText(resourceMap.getString("jlblResult.text")); // NOI18N
        jlblResult.setName("jlblResult"); // NOI18N

        jtxtResult.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtResult.setToolTipText(resourceMap.getString("jtxtResult.toolTipText")); // NOI18N
        jtxtResult.setName("jtxtResult"); // NOI18N

        buttonGroup1.add(jradAnnuity);
        jradAnnuity.setFont(resourceMap.getFont("jradAnnuity.font")); // NOI18N
        jradAnnuity.setText(resourceMap.getString("jradAnnuity.text")); // NOI18N
        jradAnnuity.setName("jradAnnuity"); // NOI18N
        jradAnnuity.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jradAnnuityItemStateChanged(evt);
            }
        });

        buttonGroup1.add(jradLoan);
        jradLoan.setFont(resourceMap.getFont("jradLoan.font")); // NOI18N
        jradLoan.setText(resourceMap.getString("jradLoan.text")); // NOI18N
        jradLoan.setName("jradLoan"); // NOI18N
        jradLoan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jradLoanItemStateChanged(evt);
            }
        });

        jbtnCalc.setText(resourceMap.getString("jbtnCalc.text")); // NOI18N
        jbtnCalc.setName("jbtnCalc"); // NOI18N
        jbtnCalc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCalcActionPerformed(evt);
            }
        });

        jbtnSched.setText(resourceMap.getString("jbtnSched.text")); // NOI18N
        jbtnSched.setEnabled(false);
        jbtnSched.setName("jbtnSched"); // NOI18N
        jbtnSched.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSchedActionPerformed(evt);
            }
        });

        jbtnClear.setText(resourceMap.getString("jbtnClear.text")); // NOI18N
        jbtnClear.setName("jbtnClear"); // NOI18N
        jbtnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnClearActionPerformed(evt);
            }
        });

        buttonGroup1.add(jradPV);
        jradPV.setFont(resourceMap.getFont("jradPV.font")); // NOI18N
        jradPV.setText(resourceMap.getString("jradPV.text")); // NOI18N
        jradPV.setName("jradPV"); // NOI18N
        jradPV.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jradPVItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlblAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jradAnnuity)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                .addComponent(jradLoan, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jradPV))
                            .addComponent(jtxtAmt)
                            .addComponent(jtxtRate)
                            .addComponent(jtxtTerm)))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jlblResult, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtResult)))
                .addContainerGap(69, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(jbtnCalc)
                        .addGap(125, 125, 125))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(jbtnSched)
                        .addGap(126, 126, 126))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(jbtnClear)
                        .addGap(135, 135, 135))))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jradAnnuity)
                    .addComponent(jradLoan)
                    .addComponent(jradPV))
                .addGap(31, 31, 31)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblAmt)
                    .addComponent(jtxtAmt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtxtRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtxtTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jbtnCalc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtResult, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblResult, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jbtnSched)
                .addGap(18, 18, 18)
                .addComponent(jbtnClear)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(financialsPV.FinancialsPVApp.class).getContext().getActionMap(FinancialsPVView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 366, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jradAnnuityItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jradAnnuityItemStateChanged
        if(jradAnnuity.isSelected()){
            jlblAmt.setText(Annuity.AMOUNTDESC);
            jlblResult.setText(Annuity.RESULTDESC);
        }
    }//GEN-LAST:event_jradAnnuityItemStateChanged

    private void jradLoanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jradLoanItemStateChanged
        if(jradLoan.isSelected()){
            jlblAmt.setText(Loan.AMOUNTDESC);
            jlblResult.setText(Loan.RESULTDESC);
        }
    }//GEN-LAST:event_jradLoanItemStateChanged

    private void jbtnCalcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCalcActionPerformed
        double amt, rate;
        int term;
        NumberFormat curr = NumberFormat.getCurrencyInstance();
        statusMessageLabel.setText("");
        try{
            amt = Double.parseDouble(jtxtAmt.getText());
            if(amt <= 0){
                statusMessageLabel.setText("Amount must be positive");
                jtxtAmt.setText("");
                jtxtAmt.requestFocusInWindow();
                return;
            }
        } catch(NumberFormatException e){
            statusMessageLabel.setText("Invaild Input 'Amount': " + e.getMessage());
            jtxtAmt.requestFocusInWindow();
            return;
        }
        try{
            rate = Double.parseDouble(jtxtRate.getText());
            if(rate <= 0){
                statusMessageLabel.setText("Rate must be positive");
                jtxtRate.setText("");
                jtxtRate.requestFocusInWindow();
                return;
            }
        } catch(NumberFormatException e){
            statusMessageLabel.setText("Invaild Input 'Rate': " + e.getMessage());
            jtxtRate.requestFocusInWindow();
            return;
        }
        try{
            term = Integer.parseInt(jtxtTerm.getText());
            if(term <= 0){
                statusMessageLabel.setText("Term must be positive");
                jtxtTerm.setText("");
                jtxtTerm.requestFocusInWindow();
                return;
            }    
        } catch(NumberFormatException e){
            statusMessageLabel.setText("Invaild Input 'Term': " + e.getMessage());
            jtxtTerm.requestFocusInWindow();
            return;
        }
        
        if(jradAnnuity.isSelected()){
            f = new Annuity(amt,rate,term);
        } else if(jradLoan.isSelected()){
            f = new Loan(amt,rate,term);
        } else if(jradPV.isSelected()){
            f = new PresentValue(amt,rate,term);
        }else {
            statusMessageLabel.setText(" unknown Secection ");
            return;
        }
        jtxtResult.setText(curr.format(f.getResult()));
        jbtnSched.setEnabled(true);
        
    }//GEN-LAST:event_jbtnCalcActionPerformed

    private void jbtnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnClearActionPerformed
        jtxtAmt.setText("");
        jtxtRate.setText("");
        jtxtTerm.setText("");
        jtxtResult.setText("");
        buttonGroup1.clearSelection();
        jbtnSched.setEnabled(false);
        jtxtAmt.requestFocusInWindow();
    }//GEN-LAST:event_jbtnClearActionPerformed

    private void jbtnSchedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSchedActionPerformed
        statusMessageLabel.setText("");
        NumberFormat curr = NumberFormat.getCurrencyInstance();
        NumberFormat pct = NumberFormat.getPercentInstance();
        pct.setMaximumFractionDigits(3);
        pct.setMinimumFractionDigits(3);
        
        JTable sched = null;
        DefaultTableModel mod;
        
        DefaultTableCellRenderer rend;
        
        String[] cols = null;
        String[][] t = null;

                if(f instanceof Annuity){
                    cols = new String[] {"Month", Annuity.BEGBALDESC, Annuity.PRINFACTORDESC, 
                                            Annuity.INTRESTDESC, Annuity.ENDBALDESC};
                    t = new String[f.getTerm()][5];
              } else if(f instanceof Loan){
                  cols = new String[] {"Month", Loan.BEGBALDESC, Loan.PAYMENTDESC, 
                                            Loan.INTRESTDESC, Loan.ENDBALDESC, Loan.PRINPAIDDESC};
                  t =  new String[f.getTerm()][6];
                } else if(f instanceof PresentValue){
                  cols = new String[] {"Month", PresentValue.DISCDESC, PresentValue.PVDESC};
                  t = new String[f.getTerm()][3];
                }   
                else {
                  statusMessageLabel.setText("Uknown Fiancial Operation");
                  return;
              }
                mod = new DefaultTableModel(t,cols);
                sched = new JTable(mod);
                rend = (DefaultTableCellRenderer) sched.getDefaultRenderer(Object.class);
                rend.setHorizontalAlignment(JLabel.RIGHT);
                
                for(int i = 0; i < f.getTerm(); i++){
                    sched.setValueAt((i),i, 0);
                    sched.setValueAt(curr.format(f.getPrinFactor(i)),i,1);
                    sched.setValueAt(curr.format(f.getEndBal(i)), i, 2);
                    if( f instanceof Annuity){
                        sched.setValueAt((i+1),i, 0);
                        sched.setValueAt(curr.format(f.getBegBal(i+1)),i,1);
                        sched.setValueAt(curr.format(f.getPrinFactor(i+1)), i, 2);
                        sched.setValueAt(curr.format(f.getIntFactor(i+1)), i, 3);
                        sched.setValueAt(curr.format(f.getEndBal(i+1)), i, 4);
                    }    
                    if(f instanceof Loan){
                        Loan l = (Loan) f;
                        sched.setValueAt((i+1),i, 0);
                        sched.setValueAt(curr.format(f.getBegBal(i+1)),i,1);
                        sched.setValueAt(curr.format(f.getPrinFactor(i+1)), i, 2);
                        sched.setValueAt(curr.format(f.getIntFactor(i+1)), i, 3);
                        sched.setValueAt(curr.format(f.getEndBal(i+1)), i, 4);
                        sched.setValueAt(curr.format(l.getPrinPaid(i+1)), i, 5);
                        }
                    
                }// end of loop
                
                JScrollPane sp = new JScrollPane(sched);
                JDialog dg = new JDialog();
                dg.add(sp);
                f.setTitle();
                dg.setBounds(150,400,600,300);
                dg.setVisible(true);       
    }//GEN-LAST:event_jbtnSchedActionPerformed

    private void jradPVItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jradPVItemStateChanged
        if(jradPV.isSelected()){
            jlblAmt.setText(PresentValue.AMOUNTDESC);
            jlblResult.setText(PresentValue.RESULTDESC);
        }
    }//GEN-LAST:event_jradPVItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JButton jbtnCalc;
    private javax.swing.JButton jbtnClear;
    private javax.swing.JButton jbtnSched;
    private javax.swing.JLabel jlblAmt;
    private javax.swing.JLabel jlblResult;
    private javax.swing.JRadioButton jradAnnuity;
    private javax.swing.JRadioButton jradLoan;
    private javax.swing.JRadioButton jradPV;
    private javax.swing.JTextField jtxtAmt;
    private javax.swing.JTextField jtxtRate;
    private javax.swing.JTextField jtxtResult;
    private javax.swing.JTextField jtxtTerm;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
