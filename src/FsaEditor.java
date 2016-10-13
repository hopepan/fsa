import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FsaEditor extends JFrame {
    
    private JMenuItem deleteMI;
    private JTextField eventTF;
    private JMenu jMenu1;
    private JMenu jMenu2;
    private JMenuBar jMenuBar1;
    private JPanel jPanel2;
    private JMenuItem newStateMI;
    private JMenuItem newTransitionMI;
    private JFileChooser openFC;
    private JMenuItem openMI;
    private JMenuItem quitMI;
    private JButton resetButton;
    private JMenuItem saveMI;
    private JMenuItem setFinalMI;
    private JMenuItem setInitialMI;
    private JButton stepButton;
    private JMenuItem unsetFinalMI;
    private JMenuItem unsetInitialMI;
    private FsaPanel jPanel1;
    
    private FsaIo fsaIo;
    private Fsa fsa;
    
    public FsaEditor() {
        initComponents();
        init();
    }
    
    private void init() {
    	this.fsaIo = new FsaReaderWriter();
    	this.fsa = new FsaImpl();
    	this.fsa.addListener(jPanel1);
    	this.jPanel1.setFsa(this.fsa);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new FsaPanel();
        jPanel2 = new JPanel();
        resetButton = new JButton();
        stepButton = new JButton();
        eventTF = new JTextField();
        openFC = new JFileChooser();
        jMenuBar1 = new JMenuBar();
        jMenu1 = new JMenu();
        openMI = new JMenuItem();
        saveMI = new JMenuItem();
        quitMI = new JMenuItem();
        jMenu2 = new JMenu();
        newStateMI = new JMenuItem();
        newTransitionMI = new JMenuItem();
        setInitialMI = new JMenuItem();
        unsetInitialMI = new JMenuItem();
        setFinalMI = new JMenuItem();
        unsetFinalMI = new JMenuItem();
        deleteMI = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setResizable(false);

        jPanel1.setBackground(Color.white);
        jPanel1.setPreferredSize(new Dimension(800, 600));
        jPanel1.setLayout(null);

        jPanel2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        jPanel2.setPreferredSize(new Dimension(0, 30));

        resetButton.setFont(new Font("Arial", 0, 12)); // NOI18N
        resetButton.setText("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(final ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        stepButton.setFont(new Font("Arial", 0, 12)); // NOI18N
        stepButton.setText("Step");

        eventTF.setFont(new Font("Arial", 0, 12)); // NOI18N

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(eventTF, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE)
                .addGap(232, 232, 232)
                .addComponent(resetButton)
                .addGap(44, 44, 44)
                .addComponent(stepButton)
                .addGap(32, 32, 32))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(resetButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(stepButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(eventTF, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        );
        
        openFC.setFont(new Font("Arial", 0, 12)); // NOI18N
        openFC.setFileSelectionMode(JFileChooser.FILES_ONLY);
        openFC.setFileFilter(new FileNameExtensionFilter("FSA File","fsa"));

        jMenuBar1.setFont(new Font("Arial", 0, 12)); // NOI18N
        jMenuBar1.setPreferredSize(new Dimension(58, 30));

        jMenu1.setText("File");
        jMenu1.setFont(new Font("Arial", 0, 12)); // NOI18N
        jMenu1.setName(""); // NOI18N
        jMenu1.setPreferredSize(new Dimension(50, 19));

        openMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        openMI.setFont(new Font("Arial", 0, 12)); // NOI18N
        openMI.setText("Open...");
        openMI.setPreferredSize(new Dimension(155, 30));
        openMI.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(final ActionEvent evt) {
                openMIActionPerformed(evt);
            }
        });
        jMenu1.add(openMI);

        saveMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        saveMI.setFont(new Font("Arial", 0, 12)); // NOI18N
        saveMI.setText("Save as...");
        saveMI.setPreferredSize(new Dimension(155, 30));
        saveMI.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(final ActionEvent evt) {
        		saveMIActionPerformed(evt);
        	}
        });
        jMenu1.add(saveMI);

        quitMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        quitMI.setFont(new Font("Arial", 0, 12)); // NOI18N
        quitMI.setText("Quit");
        quitMI.setPreferredSize(new Dimension(155, 30));
        quitMI.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(final ActionEvent evt) {
            	quitMIActionPerformed(evt);
            }
        });
        jMenu1.add(quitMI);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenu2.setFont(new Font("Arial", 0, 12)); // NOI18N
        jMenu2.setPreferredSize(new Dimension(50, 19));

        newStateMI.setFont(new Font("Arial", 0, 12)); // NOI18N
        newStateMI.setText("New state");
        newStateMI.setPreferredSize(new Dimension(127, 30));
        newStateMI.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(final ActionEvent evt) {
            	newStateMIActionPerformed(evt);
            }
        });
        jMenu2.add(newStateMI);

        newTransitionMI.setFont(new Font("Arial", 0, 12)); // NOI18N
        newTransitionMI.setText("New transition");
        newTransitionMI.setPreferredSize(new Dimension(127, 30));
        newTransitionMI.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(final ActionEvent evt) {
            	newTransitionMIActionPerformed(evt);
            }
        });
        jMenu2.add(newTransitionMI);

        setInitialMI.setFont(new Font("Arial", 0, 12)); // NOI18N
        setInitialMI.setText("Set initial");
        setInitialMI.setPreferredSize(new Dimension(127, 30));
        setInitialMI.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(final ActionEvent evt) {
            	setInitialMIActionPerformed(evt);
            }
        });
        jMenu2.add(setInitialMI);

        unsetInitialMI.setFont(new Font("Arial", 0, 12)); // NOI18N
        unsetInitialMI.setText("Unset initial");
        unsetInitialMI.setPreferredSize(new Dimension(127, 30));
        unsetInitialMI.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(final ActionEvent evt) {
            	unsetInitialMIActionPerformed(evt);
            }
        });
        jMenu2.add(unsetInitialMI);

        setFinalMI.setFont(new Font("Arial", 0, 12)); // NOI18N
        setFinalMI.setText("Set final");
        setFinalMI.setPreferredSize(new Dimension(127, 30));
        setFinalMI.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(final ActionEvent evt) {
            	setFinalMIActionPerformed(evt);
            }
        });
        jMenu2.add(setFinalMI);

        unsetFinalMI.setFont(new Font("Arial", 0, 12)); // NOI18N
        unsetFinalMI.setText("Unset final");
        unsetFinalMI.setPreferredSize(new Dimension(127, 30));
        unsetFinalMI.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(final ActionEvent evt) {
            	unsetFinalMIActionPerformed(evt);
            }
        });
        jMenu2.add(unsetFinalMI);

        deleteMI.setFont(new Font("Arial", 0, 12)); // NOI18N
        deleteMI.setText("Delete");
        deleteMI.setPreferredSize(new Dimension(127, 30));
        deleteMI.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(final ActionEvent evt) {
            	deleteMIActionPerformed(evt);
            }
        });
        jMenu2.add(deleteMI);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        
        pack();
    }                        

    private void resetButtonActionPerformed(final ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void openMIActionPerformed(final ActionEvent evt) {                                       
    	int returnValue = this.openFC.showOpenDialog(this);
    	if(returnValue == JFileChooser.APPROVE_OPTION) {
    		Reader r = null;
			try {
				r = new FileReader(this.openFC.getSelectedFile());
				this.jPanel1.resetPanel();
				this.fsa = new FsaImpl();
				this.fsa.addListener(jPanel1);
				this.jPanel1.setFsa(this.fsa);
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

    private void saveMIActionPerformed(final ActionEvent evt) {                                       
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

    private void quitMIActionPerformed(final ActionEvent evt) {    
    	System.exit(0);
    }
    
    private void newStateMIActionPerformed(final ActionEvent evt) {                                       
    }                                      
    
    private void newTransitionMIActionPerformed(final ActionEvent evt) {                                       
    }                                      
    
    private void setInitialMIActionPerformed(final ActionEvent evt) {                                       
    }                                      
    
    private void unsetInitialMIActionPerformed(final ActionEvent evt) {                                       
    }                                      
    
    private void setFinalMIActionPerformed(final ActionEvent evt) {                                       
    }                                      
    
    private void unsetFinalMIActionPerformed(final ActionEvent evt) {                                       
    }                                      
    
    private void deleteMIActionPerformed(final ActionEvent evt) {                                       
    }                                      

    public static void main(final String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FsaEditor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(FsaEditor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FsaEditor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(FsaEditor.class.getName()).log(Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(new Runnable() {
            @Override
			public void run() {
                new FsaEditor().setVisible(true);
            }
        });
    }

}
