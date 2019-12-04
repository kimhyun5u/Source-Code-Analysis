package pt.technic.apps.minesfinder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.*;

/**
 *
 * @author Gabriel Massadas
 */
public class GameWindow extends javax.swing.JFrame {

	JMenuBar jmb = new JMenuBar();
	JMenu jm = new JMenu();
    private ButtonMinefield[][] buttons;
    private Minefield minefield;
    private RecordTable record;
    private boolean isLeftPressed;
    private boolean isRightPressed;
    private SoundEffect clickE;	//BGM
    //목숨-----------------
    public static int moksum = 1;
    //-----------------
    /**
     * Creates new form GameWindow
     */
   
    public static int getMoksum() {
    	return moksum;
    }
    public static void setMoksum(int setmoksum) {
    	moksum = setmoksum;
    }
    public GameWindow() {
        initComponents();
    }
    
    public GameWindow(Minefield minefield) { //사용자설정게임
        initComponents();
        isLeftPressed = false;
        isRightPressed = false;
        
        this.minefield = minefield;

        buttons = new ButtonMinefield[minefield.getWidth()][minefield.getHeight()];

        getContentPane().setLayout(new GridLayout(minefield.getWidth(),
                minefield.getHeight()));

        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonMinefield button = (ButtonMinefield) e.getSource();
                int x = button.getCol();
                int y = button.getLine();               
                minefield.revealGrid(x, y);               
                //BGM
                clickE.startClip();
                //BGM                
                updateButtonsStates(); 
              
                if (minefield.isGameFinished()) {
                    if (minefield.isPlayerDefeated()) {
                        JOptionPane.showMessageDialog(null, "Oh, a mine broke",
                                "Lost!", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Congratulations. You managed to discover all the mines in "
                                + (minefield.getGameDuration() / 1000) + " seconds",
                                "victory", JOptionPane.INFORMATION_MESSAGE                                
                        );                                      
                      //--------------------------------------------------------
                        if(MinesFinder.getChallenge()) {
                        	MinesFinder.setChallengeMinesNum(MinesFinder.getChallengeMinesNum()+4);
                        	MinesFinder.setChallengeWidth(MinesFinder.getChallengeWidth()+3);
                        	MinesFinder.setChallegneHeight(MinesFinder.getChallegneHeight()+3);
                        	setMoksum(1);
                        	GameWindow gameWindow = new GameWindow(new Minefield(MinesFinder.getChallengeWidth(),MinesFinder.getChallegneHeight(),MinesFinder.getChallengeMinesNum()));
                    		gameWindow.setVisible(true);
                        }
                        //-------------------------------------------------------
                    }
                    setVisible(false);
                }
            }
        };

        MouseListener mouseListener = new MouseListener() {
        	 @Override
             public void mousePressed(MouseEvent e) {
                //양클릭--------------------------------------------------------------------------------
                ButtonMinefield botao = (ButtonMinefield) e.getSource();
                int x = botao.getCol();
                int y = botao.getLine();
                if (minefield.getGridState(x, y) >= 1 && minefield.getGridState(x, y) <= 8) {
                   if (SwingUtilities.isLeftMouseButton(e)) {
                      isLeftPressed = true;
                   } else if (SwingUtilities.isRightMouseButton(e)) {
                      isRightPressed = true;
                   }

                   if (isLeftPressed && isRightPressed) {

                      minefield.doubleCliked(x, y);
                   }
                }         
                //-------------------------------------------------------------------------------
                if (e.getButton() == MouseEvent.BUTTON3) {                    
                     if (minefield.getGridState(x, y) == minefield.COVERED) {
                         minefield.setMineMarked(x, y);
                     }
                     else if (minefield.getGridState(x, y) == minefield.MARKED) {
                         minefield.setMineQuestion(x, y);
                     } 
                     else if (minefield.getGridState(x, y) == minefield.QUESTION) {
                         minefield.setMineCovered(x, y);
                     }
                     updateButtonsStates();
                 }
            }

            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            	 if (SwingUtilities.isLeftMouseButton (e))
                 {
                     isLeftPressed = false;
                 }
                 else if (SwingUtilities.isRightMouseButton (e))
                 {
                     isRightPressed = false;
                 }
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        };

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                ButtonMinefield botao = (ButtonMinefield) e.getSource();
                int x = botao.getWidth();
                int y = botao.getHeight();
                if (e.getKeyCode() == KeyEvent.VK_UP && y > 0) {
                    buttons[x][y - 1].requestFocus();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT && x > 0) {
                    buttons[x - 1][y].requestFocus();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && y
                        < minefield.getHeight() - 1) {
                    buttons[x][y + 1].requestFocus();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && x
                        < minefield.getWidth() - 1) {
                    buttons[x + 1][y].requestFocus();
                } else if (e.getKeyCode() == KeyEvent.VK_M) {
                    if (minefield.getGridState(x, y) == minefield.COVERED) {
                        minefield.setMineMarked(x, y);
                    } else if (minefield.getGridState(x,
                            y) == minefield.MARKED) {
                        minefield.setMineQuestion(x, y);
                    } else if (minefield.getGridState(x,
                            y) == minefield.QUESTION) {
                        minefield.setMineCovered(x, y);
                    }
                    updateButtonsStates();
                }
            }

            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyReleased(KeyEvent ke) {
            }
        };
        
        // Create buttons for the player
        for (int x = 0; x < minefield.getWidth(); x++) {
            for (int y = 0; y < minefield.getHeight(); y++) {
                buttons[x][y] = new ButtonMinefield(x, y);
                buttons[x][y].addActionListener(action);
                buttons[x][y].addMouseListener(mouseListener);
                buttons[x][y].addKeyListener(keyListener);
                getContentPane().add(buttons[x][y]);
            }
        }
    }
    
    public GameWindow(Minefield minefield, RecordTable record) {
        initComponents();

        this.minefield = minefield;
        this.record = record;

        buttons = new ButtonMinefield[minefield.getWidth()][minefield.getHeight()];

        getContentPane().setLayout(new GridLayout(minefield.getWidth(),
                minefield.getHeight()));        
        
        
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonMinefield button = (ButtonMinefield) e.getSource();
                int x = button.getCol();
                int y = button.getLine();
                minefield.revealGrid(x, y);
                //-----------------------------------------
                jm.setText("남은 목숨 : " + Integer.toString(getMoksum()));
                //---------------------------------------
                //BGM
                clickE.startClip();
                //BGM
                updateButtonsStates();
                if (minefield.isGameFinished()) {
                    if (minefield.isPlayerDefeated()) {
                        JOptionPane.showMessageDialog(null, "Oh, a mine broke",
                                "Lost!", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Congratulations. You managed to discover all the mines in "
                                + (minefield.getGameDuration() / 1000) + " seconds",
                                "victory", JOptionPane.INFORMATION_MESSAGE
                        );
                        long a = minefield.getGameDuration();
                        long b = record.getScore();
                        boolean newRecord = minefield.getGameDuration() < record.getScore();

                        if (newRecord) {
                            String name = JOptionPane.showInputDialog("Enter your name");
                            if(name != "")
                                record.setRecord(name, minefield.getGameDuration());
                        }
                    }
                    setVisible(false);
                }
            }
        };

        MouseListener mouseListener = new MouseListener() {
        	 @Override
             public void mousePressed(MouseEvent e) {
                //양클릭--------------------------------------------------------------------------------
                ButtonMinefield botao = (ButtonMinefield) e.getSource();
                int x = botao.getCol();
                int y = botao.getLine();
                if (minefield.getGridState(x, y) >= 1 && minefield.getGridState(x, y) <= 8) {
                   if (SwingUtilities.isLeftMouseButton(e)) {
                      isLeftPressed = true;
                   } else if (SwingUtilities.isRightMouseButton(e)) {
                      isRightPressed = true;
                   }

                   if (isLeftPressed && isRightPressed) {

                      minefield.doubleCliked(x, y);
                   }
                }         
                //-------------------------------------------------------------------------------
                if (e.getButton() == MouseEvent.BUTTON3) {                    
                     if (minefield.getGridState(x, y) == minefield.COVERED) {
                         minefield.setMineMarked(x, y);
                     }
                     else if (minefield.getGridState(x, y) == minefield.MARKED) {
                         minefield.setMineQuestion(x, y);
                     } 
                     else if (minefield.getGridState(x, y) == minefield.QUESTION) {
                         minefield.setMineCovered(x, y);
                     }
                     updateButtonsStates();
                 }
             }

            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {          	
	           	if (SwingUtilities.isLeftMouseButton (e))
	            {
	                isLeftPressed = false;
	            }
	            else if (SwingUtilities.isRightMouseButton (e))
	            {
	                isRightPressed = false;
	            }
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        };

//        KeyListener keyListener = new KeyListener() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                ButtonMinefield botao = (ButtonMinefield) e.getSource();
//                int x = botao.getWidth();
//                int y = botao.getHeight();
//                if (e.getKeyCode() == KeyEvent.VK_UP && y > 0) {
//                    buttons[x][y - 1].requestFocus();
//                } else if (e.getKeyCode() == KeyEvent.VK_LEFT && x > 0) {
//                    buttons[x - 1][y].requestFocus();
//                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && y
//                        < minefield.getHeight() - 1) {
//                    buttons[x][y + 1].requestFocus();
//                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && x
//                        < minefield.getWidth() - 1) {
//                    buttons[x + 1][y].requestFocus();
//                } else if (e.getKeyCode() == KeyEvent.VK_M) {
//                    if (minefield.getGridState(x, y) == minefield.COVERED) {
//                        minefield.setMineMarked(x, y);
//                    } else if (minefield.getGridState(x,
//                            y) == minefield.MARKED) {
//                        minefield.setMineQuestion(x, y);
//                    } else if (minefield.getGridState(x,
//                            y) == minefield.QUESTION) {
//                        minefield.setMineCovered(x, y);
//                    }
//                    updateButtonsStates();
//                }
//            }
//
//            @Override
//            public void keyTyped(KeyEvent ke) {
//            }
//
//            @Override
//            public void keyReleased(KeyEvent ke) {
//            }
//        };
        
        // Create buttons for the player
        for (int x = 0; x < minefield.getWidth(); x++) {
            for (int y = 0; y < minefield.getHeight(); y++) {
                buttons[x][y] = new ButtonMinefield(x, y);
                buttons[x][y].addActionListener(action);
                buttons[x][y].addMouseListener(mouseListener);
//                buttons[x][y].addKeyListener(keyListener);
                getContentPane().add(buttons[x][y]);
            }
        }
    }

    private void updateButtonsStates() {
        for (int x = 0; x < minefield.getWidth(); x++) {
            for (int y = 0; y < minefield.getHeight(); y++) {
                buttons[x][y].setEstado(minefield.getGridState(x, y));
            }
        }
    }

     /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    	clickE = new SoundEffect("src/pt/technic/apps/minesfinder/music/버튼.wav"); 
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Game");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
         //-------------------------------------------------------------------              
        jm.setText("남은 목숨 : " + Integer.toString(getMoksum()));
        jmb.add(jm);
        setJMenuBar(jmb); 
        //-------------------------------------------------------------------
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout); 
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1094, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 553, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
