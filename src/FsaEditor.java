import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FsaEditor extends javax.swing.JFrame {

    public FsaEditor() {
        initComponents();
        init();
    }
    
    private void init() {
    	this.fsaIo = new FsaReaderWriter();
    	this.fsa = new FsaImpl();
    	this.fsa.addListener(jPanel1);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new FsaPanel();
        jPanel2 = new javax.swing.JPanel();
        resetButton = new javax.swing.JButton();
        stepButton = new javax.swing.JButton();
        eventTF = new javax.swing.JTextField();
        openFC = new javax.swing.JFileChooser();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        openMI = new javax.swing.JMenuItem();
        saveMI = new javax.swing.JMenuItem();
        quitMI = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        newStateMI = new javax.swing.JMenuItem();
        newTransitionMI = new javax.swing.JMenuItem();
        setInitialMI = new javax.swing.JMenuItem();
        unsetInitialMI = new javax.swing.JMenuItem();
        setFinalMI = new javax.swing.JMenuItem();
        unsetFinalMI = new javax.swing.JMenuItem();
        deleteMI = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel2.setPreferredSize(new java.awt.Dimension(0, 30));

        resetButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        stepButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        stepButton.setText("Step");

        eventTF.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(eventTF, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(232, 232, 232)
                .addComponent(resetButton)
                .addGap(44, 44, 44)
                .addComponent(stepButton)
                .addGap(32, 32, 32))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(resetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(stepButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(eventTF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        openFC.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        openFC.setFileSelectionMode(JFileChooser.FILES_ONLY);
        openFC.setFileFilter(new FileNameExtensionFilter("FSA File","fsa"));

        jMenuBar1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jMenuBar1.setPreferredSize(new java.awt.Dimension(58, 30));

        jMenu1.setText("File");
        jMenu1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jMenu1.setName(""); // NOI18N
        jMenu1.setPreferredSize(new java.awt.Dimension(50, 19));

        openMI.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMI.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        openMI.setText("Open...");
        openMI.setPreferredSize(new java.awt.Dimension(155, 30));
        openMI.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
                openMIActionPerformed(evt);
            }
        });
        jMenu1.add(openMI);

        saveMI.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMI.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        saveMI.setText("Save as...");
        saveMI.setPreferredSize(new java.awt.Dimension(155, 30));
        saveMI.addActionListener(new java.awt.event.ActionListener() {
        	@Override
        	public void actionPerformed(final java.awt.event.ActionEvent evt) {
        		saveMIActionPerformed(evt);
        	}
        });
        jMenu1.add(saveMI);

        quitMI.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        quitMI.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        quitMI.setText("Quit");
        quitMI.setPreferredSize(new java.awt.Dimension(155, 30));
        quitMI.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
            	quitMIActionPerformed(evt);
            }
        });
        jMenu1.add(quitMI);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenu2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jMenu2.setPreferredSize(new java.awt.Dimension(50, 19));

        newStateMI.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        newStateMI.setText("New state");
        newStateMI.setPreferredSize(new java.awt.Dimension(127, 30));
        newStateMI.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
            	newStateMIActionPerformed(evt);
            }
        });
        jMenu2.add(newStateMI);

        newTransitionMI.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        newTransitionMI.setText("New transition");
        newTransitionMI.setPreferredSize(new java.awt.Dimension(127, 30));
        newTransitionMI.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
            	newTransitionMIActionPerformed(evt);
            }
        });
        jMenu2.add(newTransitionMI);

        setInitialMI.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        setInitialMI.setText("Set initial");
        setInitialMI.setPreferredSize(new java.awt.Dimension(127, 30));
        setInitialMI.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
            	setInitialMIActionPerformed(evt);
            }
        });
        jMenu2.add(setInitialMI);

        unsetInitialMI.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        unsetInitialMI.setText("Unset initial");
        unsetInitialMI.setPreferredSize(new java.awt.Dimension(127, 30));
        unsetInitialMI.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
            	unsetInitialMIActionPerformed(evt);
            }
        });
        jMenu2.add(unsetInitialMI);

        setFinalMI.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        setFinalMI.setText("Set final");
        setFinalMI.setPreferredSize(new java.awt.Dimension(127, 30));
        setFinalMI.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
            	setFinalMIActionPerformed(evt);
            }
        });
        jMenu2.add(setFinalMI);

        unsetFinalMI.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        unsetFinalMI.setText("Unset final");
        unsetFinalMI.setPreferredSize(new java.awt.Dimension(127, 30));
        unsetFinalMI.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
            	unsetFinalMIActionPerformed(evt);
            }
        });
        jMenu2.add(unsetFinalMI);

        deleteMI.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        deleteMI.setText("Delete");
        deleteMI.setPreferredSize(new java.awt.Dimension(127, 30));
        deleteMI.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
            	deleteMIActionPerformed(evt);
            }
        });
        jMenu2.add(deleteMI);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        
        pack();
    }                        

    private void resetButtonActionPerformed(final java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void openMIActionPerformed(final java.awt.event.ActionEvent evt) {                                       
    	int returnValue = this.openFC.showOpenDialog(this);
    	if(returnValue == JFileChooser.APPROVE_OPTION) {
    		Reader r = null;
			try {
				r = new FileReader(this.openFC.getSelectedFile());
				this.fsa = new FsaImpl();
				this.fsa.addListener(jPanel1);
				this.fsaIo.read(r, this.fsa);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "No file is selected or found.", "Open file",JOptionPane.WARNING_MESSAGE);  
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "The file can't be read.", "Open file",JOptionPane.WARNING_MESSAGE);  
			} catch (FsaFormatException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, e.getMessage(), "Open file",JOptionPane.WARNING_MESSAGE);  
			} finally {
				if(r != null) {
					try {
						r.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
//				this.fsa.toString();
			}
    	}
    }                                      

    private void saveMIActionPerformed(final java.awt.event.ActionEvent evt) {                                       
    	int returnValue = this.openFC.showSaveDialog(this);
    	if(returnValue == JFileChooser.APPROVE_OPTION) {
    		Writer w = null;
			try {
				w = new FileWriter(this.openFC.getSelectedFile());
				this.fsaIo.write(w, this.fsa);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "The file can't be writen", "Save file",JOptionPane.WARNING_MESSAGE);  
			} finally {
				if(w != null) {
					try {
						w.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
    	}
    }                                      

    private void quitMIActionPerformed(final java.awt.event.ActionEvent evt) {    
    	System.exit(0);
    }
    
    private void newStateMIActionPerformed(final java.awt.event.ActionEvent evt) {                                       
    }                                      
    
    private void newTransitionMIActionPerformed(final java.awt.event.ActionEvent evt) {                                       
    }                                      
    
    private void setInitialMIActionPerformed(final java.awt.event.ActionEvent evt) {                                       
    }                                      
    
    private void unsetInitialMIActionPerformed(final java.awt.event.ActionEvent evt) {                                       
    }                                      
    
    private void setFinalMIActionPerformed(final java.awt.event.ActionEvent evt) {                                       
    }                                      
    
    private void unsetFinalMIActionPerformed(final java.awt.event.ActionEvent evt) {                                       
    }                                      
    
    private void deleteMIActionPerformed(final java.awt.event.ActionEvent evt) {                                       
    }                                      

    public static void main(final String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FsaEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FsaEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FsaEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FsaEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
			public void run() {
                new FsaEditor().setVisible(true);
            }
        });
    }

    private FsaIo fsaIo;
    private Fsa fsa;
    
    private javax.swing.JMenuItem deleteMI;
    private javax.swing.JTextField eventTF;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private FsaPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JMenuItem newStateMI;
    private javax.swing.JMenuItem newTransitionMI;
    private javax.swing.JFileChooser openFC;
    private javax.swing.JMenuItem openMI;
    private javax.swing.JMenuItem quitMI;
    private javax.swing.JButton resetButton;
    private javax.swing.JMenuItem saveMI;
    private javax.swing.JMenuItem setFinalMI;
    private javax.swing.JMenuItem setInitialMI;
    private javax.swing.JButton stepButton;
    private javax.swing.JMenuItem unsetFinalMI;
    private javax.swing.JMenuItem unsetInitialMI;
}
