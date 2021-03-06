package permlab.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import permlib.Permutation;
import permlab.utilities.ExportInterface;
import permlib.utilities.RestrictedPermutation;
import permlib.utilities.IOUtilities;
import permlab.utilities.PaintUtilities;

/**
 * Display single permutations (and their extensions)
 *
 * @author M Albert
 */
public class StaticPermFrame extends javax.swing.JFrame {

    private ArrayList<RestrictedPermutation> history = new ArrayList<RestrictedPermutation>();
    private int currentPosition = -1;
    private ArrayList<DisplayElement> highlights = new ArrayList<DisplayElement>();
    private DisplayElement location;

    /**
     * Creates new form StaticPermFrame
     */
    public StaticPermFrame() {
        initComponents();
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new HelpDispatcher("Permutation display", "StaticPermHelp.html", this));
        avoidedPermsText.setText("");
        permText.setText("");
        setMenus();
        addPermState(new RestrictedPermutation(avoidedPermsText.getText(), permText.getText(), false, false));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pointPopupMenu = new javax.swing.JPopupMenu();
        delete = new javax.swing.JMenuItem();
        replacePointWithPerm = new javax.swing.JMenuItem();
        spacePopupMenu = new javax.swing.JPopupMenu();
        insertPermInSpace = new javax.swing.JMenuItem();
        insertPoint = new javax.swing.JMenuItem();
        markForbidden = new javax.swing.JMenuItem();
        markPermitted = new javax.swing.JMenuItem();
        displayPanel = new DisplayPanel();
        textPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        avoidedPermsText = new javax.swing.JTextArea();
        permTextPanel = new javax.swing.JPanel();
        permText = new javax.swing.JTextField();
        nextButton = new javax.swing.plaf.basic.BasicArrowButton(SwingConstants.EAST);
        previousButton = new javax.swing.plaf.basic.BasicArrowButton(SwingConstants.WEST);
        simpleCheckBox = new javax.swing.JCheckBox();
        exportButton = new javax.swing.JButton();
        monotoneConstraintsBox = new javax.swing.JCheckBox();
        involutionCheckBox = new javax.swing.JCheckBox();

        delete.setText("jMenuItem1");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        pointPopupMenu.add(delete);

        replacePointWithPerm.setText("jMenuItem1");
        replacePointWithPerm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replacePointWithPermActionPerformed(evt);
            }
        });
        pointPopupMenu.add(replacePointWithPerm);

        insertPermInSpace.setText("jMenuItem1");
        insertPermInSpace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertPermInSpaceActionPerformed(evt);
            }
        });
        spacePopupMenu.add(insertPermInSpace);

        insertPoint.setText("jMenuItem1");
        insertPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertPointActionPerformed(evt);
            }
        });
        spacePopupMenu.add(insertPoint);

        markForbidden.setText("jMenuItem1");
        markForbidden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markForbiddenActionPerformed(evt);
            }
        });
        spacePopupMenu.add(markForbidden);

        markPermitted.setText("jMenuItem1");
        markPermitted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markPermittedActionPerformed(evt);
            }
        });
        spacePopupMenu.add(markPermitted);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        displayPanel.setPreferredSize(new java.awt.Dimension(700, 700));
        displayPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                displayPanelMouseAction(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                displayPanelMouseAction(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                displayPanelMouseAction(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                displayPanelMouseExited(evt);
            }
        });
        displayPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                displayPanelMouseMoved(evt);
            }
        });

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 506, Short.MAX_VALUE)
        );

        textPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Basis"));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(216, 125));

        avoidedPermsText.setColumns(20);
        avoidedPermsText.setRows(5);
        avoidedPermsText.addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyCode() == ke.VK_ENTER) {
                    update();
                }
            }
        });
        jScrollPane2.setViewportView(avoidedPermsText);

        javax.swing.GroupLayout textPanelLayout = new javax.swing.GroupLayout(textPanel);
        textPanel.setLayout(textPanelLayout);
        textPanelLayout.setHorizontalGroup(
            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                .addContainerGap())
        );
        textPanelLayout.setVerticalGroup(
            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        permTextPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Permutation"));

        permText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                permTextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout permTextPanelLayout = new javax.swing.GroupLayout(permTextPanel);
        permTextPanel.setLayout(permTextPanelLayout);
        permTextPanelLayout.setHorizontalGroup(
            permTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(permText)
        );
        permTextPanelLayout.setVerticalGroup(
            permTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(permTextPanelLayout.createSequentialGroup()
                .addComponent(permText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        previousButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousButtonActionPerformed(evt);
            }
        });

        simpleCheckBox.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        simpleCheckBox.setText("Simple");
        simpleCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpleCheckBoxActionPerformed(evt);
            }
        });

        exportButton.setText("Export");
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        monotoneConstraintsBox.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        monotoneConstraintsBox.setText("Show monotone constraints");
        monotoneConstraintsBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monotoneConstraintsBoxActionPerformed(evt);
            }
        });

        involutionCheckBox.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        involutionCheckBox.setText("Involution");
        involutionCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                involutionCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(monotoneConstraintsBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exportButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(involutionCheckBox)
                            .addComponent(simpleCheckBox))
                        .addGap(18, 18, 18)
                        .addComponent(previousButton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(permTextPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 8, Short.MAX_VALUE))
            .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(permTextPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(involutionCheckBox)
                                .addComponent(previousButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(simpleCheckBox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(exportButton)
                            .addComponent(monotoneConstraintsBox)))
                    .addComponent(textPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Updates the display when a change is made in the permutation field.
     * 
     * @param evt responds to the permutation text field
     */
    private void permTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_permTextActionPerformed
        update();
    }//GEN-LAST:event_permTextActionPerformed

    /**
     * Moves back one frame in the collection.
     * 
     * @param evt previous button being pushed
     */
    private void previousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousButtonActionPerformed
        if (currentPosition > 0) {
            currentPosition--;
        }
        redisplay();
    }//GEN-LAST:event_previousButtonActionPerformed

    /**
     * Moves forward one frame in the collection.
     * 
     * @param evt next button being pushed
     */
    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        if (currentPosition < history.size() - 1) {
            currentPosition++;
        }
        redisplay();
    }//GEN-LAST:event_nextButtonActionPerformed

    /**
     * Tracks the mouse in the display panel to update highlighting locations.
     * 
     * @param evt the mouse being moved in the display panel area
     */
    private void displayPanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_displayPanelMouseMoved
        clearHighlights();
        if (displayPanel.contains(evt.getPoint())) {
            // highlighted = new DisplayElement(evt.getX(), evt.getY());
            updateHighlights(evt.getX(), evt.getY());
        }
        repaint();
    }//GEN-LAST:event_displayPanelMouseMoved

    /**
     * Removes the highlight when the mouse leaves the display panel area.
     */
    private void displayPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_displayPanelMouseExited
        clearHighlights();
        repaint();
    }//GEN-LAST:event_displayPanelMouseExited

    /**
     * Updates the display from the permutation and basis fields. This also checks
     * that there is valid input in these fields.
     */
    private void update() {
        if (IOUtilities.isPermString(permText.getText())
                && IOUtilities.isPermsString(avoidedPermsText.getText())) {

            RestrictedPermutation temp = currentState().updateBasis(avoidedPermsText.getText());
            temp = temp.changePerm(permText.getText());
            addPermState(temp);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Improperly formatted permutation.",
                "Format error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Updates the state when the option to restrict to simple permutations is toggled.
     * 
     * @param evt the simple check box being clicked
     */
    private void simpleCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpleCheckBoxActionPerformed
        addPermState(currentState().setSimpleState(simpleCheckBox.isSelected()));
    }//GEN-LAST:event_simpleCheckBoxActionPerformed

    /**
     * Handles mouse clicks in the display panel. Left-click attempts to add a point,
     * right-clicks trigger a pop-up menu.
     * 
     * @param evt mouse clicks in the display panel
     */
    private void displayPanelMouseAction(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_displayPanelMouseAction
        if (evt.isPopupTrigger()) {
            popupMenuHandler(evt);
            return;
        }
        if (evt.getID() == MouseEvent.MOUSE_CLICKED
                && evt.getButton() == MouseEvent.BUTTON1
                && !((evt.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK)) {
            location = new DisplayElement(evt.getX(), evt.getY());
            if (!location.isPoint() && !location.isForbidden()) {
                addPointAt(location);
            }
        }
    }//GEN-LAST:event_displayPanelMouseAction

    /**
     * Deletes a point from the permutation at the current location.
     * 
     * @param evt selecting delete point from menu
     */
    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        deletePointAt(location);
    }//GEN-LAST:event_deleteActionPerformed

    /**
     * Replaces a point with a permutation specified by the user.
     * 
     * @param evt selecting the replace with permutation option
     */
    private void replacePointWithPermActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replacePointWithPermActionPerformed
        String s = (String) JOptionPane.showInputDialog(
                this,
                "Replace point with permutation:",
                "Replace point",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "1");
        if (IOUtilities.isPermString(s)) {
            replacePointAt(location, new Permutation(s));
        }
    }//GEN-LAST:event_replacePointWithPermActionPerformed

    /**
     * Marks a space as forbidden for insertion.
     * 
     * @param evt selecting the mark forbidden option
     */
    private void markForbiddenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_markForbiddenActionPerformed
        markForbiddenRegionAt(location);
    }//GEN-LAST:event_markForbiddenActionPerformed

    /**
     * Removes a user defined restriction on the selected space.
     * 
     * @param evt selecting the permit insertion option
     */
    private void markPermittedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_markPermittedActionPerformed
        markPermittedRegionAt(location);
    }//GEN-LAST:event_markPermittedActionPerformed

    /**
     * Adds a point at the current location.
     * 
     * @param evt selecting the add a point option
     */
    private void insertPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertPointActionPerformed
        addPointAt(location);
    }//GEN-LAST:event_insertPointActionPerformed

    /**
     * Replaces a space with a permutation specified by the user.
     * 
     * @param evt selecting the replace with permutation option
     */
    private void insertPermInSpaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertPermInSpaceActionPerformed
        String s = (String) JOptionPane.showInputDialog(
                this,
                "Replace space with permutation:",
                "Replace space",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "1");
        if (IOUtilities.isPermString(s)) {
            replaceSpaceAt(location, new Permutation(s));
        }
    }//GEN-LAST:event_insertPermInSpaceActionPerformed

    /**
     * Triggers an export dialogue box to output current display.
     * 
     * @param evt export option selected
     */
    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
        ExportDialog ex = new ExportDialog(this, true);
        ex.setVisible(true);
        ExportInterface e = ex.getExportInterface();
        if (e != null) {
            e.export(this, currentState(), this.getWidth(), this.getHeight(), monotoneConstraintsBox.isSelected(), true);
        }
    }//GEN-LAST:event_exportButtonActionPerformed

    /**
     * Toggles the option for displaying monotone constraints.
     * 
     * @param evt selecting the monotone constraints checkbox
     */
    private void monotoneConstraintsBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monotoneConstraintsBoxActionPerformed
        repaint();
    }//GEN-LAST:event_monotoneConstraintsBoxActionPerformed

    /**
     * Updates the state to toggle between restricting to involutions.
     * 
     * @param evt selecting the involutions checkbox
     */
    private void involutionCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_involutionCheckBoxActionPerformed
        addPermState(currentState().setInvolutionState(involutionCheckBox.isSelected()));
    }//GEN-LAST:event_involutionCheckBoxActionPerformed

    /**
     * Decides whether to display the point menu or the space menu based on the location.
     * 
     * @param evt triggering a pop-up
     */
    private void popupMenuHandler(java.awt.event.MouseEvent evt) {
        if (evt.isPopupTrigger()) {
            location = new DisplayElement(evt.getX(), evt.getY());
            if (!location.isPoint()) {
                spacePopupMenu.show(evt.getComponent(),
                        evt.getX(), evt.getY());
            } else {
                pointPopupMenu.show(evt.getComponent(),
                        evt.getX(), evt.getY());
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;


                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StaticPermFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StaticPermFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StaticPermFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StaticPermFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StaticPermFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea avoidedPermsText;
    private javax.swing.JMenuItem delete;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JButton exportButton;
    private javax.swing.JMenuItem insertPermInSpace;
    private javax.swing.JMenuItem insertPoint;
    private javax.swing.JCheckBox involutionCheckBox;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem markForbidden;
    private javax.swing.JMenuItem markPermitted;
    private javax.swing.JCheckBox monotoneConstraintsBox;
    private javax.swing.JButton nextButton;
    private javax.swing.JTextField permText;
    private javax.swing.JPanel permTextPanel;
    private javax.swing.JPopupMenu pointPopupMenu;
    private javax.swing.JButton previousButton;
    private javax.swing.JMenuItem replacePointWithPerm;
    private javax.swing.JCheckBox simpleCheckBox;
    private javax.swing.JPopupMenu spacePopupMenu;
    private javax.swing.JPanel textPanel;
    // End of variables declaration//GEN-END:variables

    /**
     * Adds a new state to the history.
     * 
     * @param newState a new state to add to the history
     */
    private void addPermState(RestrictedPermutation newState) {
        while (history.size() > currentPosition + 1) {
            history.remove(history.size() - 1);
        }
        currentPosition++;
        history.add(newState);
        redisplay();
    }

    /**
     * Redisplays the display panel to be current with selections.
     */
    private void redisplay() {
        avoidedPermsText.setText(currentState().getBasisString());
        permText.setText(currentState().getPermString());
        simpleCheckBox.setSelected(currentState().isSimple());
        involutionCheckBox.setSelected(currentState().isInvolution());
        repaint();
    }

    /**
     * Returns a Restricted Permutation that contains information about the
     * current state.
     * 
     * @return the current state
     */
    private RestrictedPermutation currentState() {
        return history.get(currentPosition);
    }

    /**
     * Adds a point at the given location.
     * 
     * @param location location to add point
     */
    private void addPointAt(DisplayElement location) {
        addPermState(currentState().addPoint(location.i, location.j));
    }

    /**
     * Sets the pop-up menu options
     */
    private void setMenus() {
        delete.setText("Delete point");
        replacePointWithPerm.setText("Replace with permutation");
        insertPermInSpace.setText("Insert Permutation");
        insertPoint.setText("Insert point");
        markForbidden.setText("Forbid insertion");
        markPermitted.setText("Permit insertion");
    }

    /**
     * Delete point at given location.
     * 
     * @param location location to delete point at
     */
    private void deletePointAt(DisplayElement location) {
        addPermState(currentState().deletePointAt(location.i));
    }

    /**
     * Replace a point at a given location with specified permutation.
     * 
     * @param location location of point to replace
     * @param rep permutation to insert
     */
    private void replacePointAt(DisplayElement location, Permutation rep) {
        addPermState(currentState().replacePoint(location.i, rep));
    }

    /**
     * Marks a region as forbidden.
     * 
     * @param location location to forbid insertion
     */
    private void markForbiddenRegionAt(DisplayElement location) {
        addPermState(currentState().addUserForbiddenPair(location.i, location.j));
    }

    /**
     * Marks a region to permit insertion.
     * 
     * @param location location to permit insertion
     */
    private void markPermittedRegionAt(DisplayElement location) {
        addPermState(currentState().removeUserForbiddenPair(location.i, location.j));
    }

    /**
     * Replace a space at a given location with specified permutation.
     * 
     * @param location location of space to replace
     * @param rep permutation to insert
     */
    private void replaceSpaceAt(DisplayElement location, Permutation rep) {
        addPermState(currentState().replaceSpace(location.i, location.j, rep));
    }

    /**
     * Returns true if simplicity is required.
     * 
     * @return true if simplicity is required
     */
    public boolean requiresSimple() {
        return simpleCheckBox.isSelected();
    }

    private void clearHighlights() {
        highlights.clear();
    }

    private void updateHighlights(int x, int y) {
       clearHighlights();
       DisplayElement here = new DisplayElement(x,y);
       highlights.add(here);
       if (involutionCheckBox.isSelected()) {
           highlights.add(here.involutionMate());
       }
    }

    /**
     * Panel that displays the permutation being worked with. 
     */
    private class DisplayPanel extends JPanel {

        public DisplayPanel() {
        }

        @Override
        public void paint(Graphics g) {
            PaintUtilities.paint(g, currentState(), getWidth(), getHeight(), monotoneConstraintsBox.isSelected(), true);
            if (currentState().getPerm() != null) {
                int dx = getWidth() / (currentState().getPerm().length() + 1);
                int dy = getHeight() / (currentState().getPerm().length() + 1);
                int r = Math.min(Math.min(dx, dy) / 4, PaintUtilities.DEFAULT_POINT_SIZE);
                for (DisplayElement highlighted : highlights) {
                    if (highlighted.isPoint()) { 
                        g.setColor(Color.YELLOW);
                        int x = (highlighted.i + 1) * dx;
                        int y = getHeight() - (currentState().getPerm().elements[highlighted.i] + 1) * dy;
                        g.fillOval(x - r, y - r, 2 * r, 2 * r);
                    } else {
                        g.setColor(Color.BLUE);
                        g.drawRect(highlighted.i * dx + r, getHeight() - (highlighted.j + 1) * dy + r, dx - 2 * r, dy - 2 * r);
                    }
                }
            }
        }
    }

    /**
     * Represents an element being displayed in the display panel. 
     */
    private class DisplayElement {

        private int x;
        private int y;
        private int dx;
        private int dy;
        private int r;
        private int i;
        private int j;
        private boolean isPoint;

        /**
         * Creates new display element.
         * 
         * @param screenX x location on screen
         * @param screenY y location on screen
         */
        DisplayElement(int screenX, int screenY) {
            this.x = screenX;
            this.y = displayPanel.getHeight() - screenY;
            dx = displayPanel.getWidth() / (currentState().getPerm().length() + 1);
            dy = displayPanel.getHeight() / (currentState().getPerm().length() + 1);
            r = Math.min(Math.min(dx, dy) / 4, PaintUtilities.DEFAULT_POINT_SIZE);
            i = this.x / dx;
            j = this.y / dy;
            determineType();
        }
        
        DisplayElement() {};
        
        DisplayElement involutionMate() {
            if (i == j) return this;
            DisplayElement result = new DisplayElement();
            result.i = this.j;
            result.j = this.i;
            result.isPoint = this.isPoint;
            return result;
        }

        /**
         * Returns true if it is a point.
         * 
         * @return true if it is a point
         */
        private boolean isPoint() {
//            int tx = (x + r) / dx;
//            int ty = (y + r) / dy;
//            if (x - tx * dx > r || y - ty * dy > r) {
//                return false;
//            }
//            tx--;
//            ty--;
//            if (tx < 0 || tx >= currentState().getPerm().length()) {
//                return false;
//            }
//            if (currentState().getPerm().elements[tx] != ty) {
//                return false;
//            }
//            i = tx;
//            j = ty;
//            return true;
            return isPoint;
        }
        
        private void determineType() {
            int tx = (x + r) / dx;
            int ty = (y + r) / dy;
            if (x - tx * dx > r || y - ty * dy > r) {
                isPoint = false;
                return;
            }
            tx--;
            ty--;
            if (tx < 0 || tx >= currentState().getPerm().length()) {
                isPoint = false;
                return;
            }
            if (currentState().getPerm().elements[tx] != ty) {
                isPoint = false;
                return;
            }
            i = tx;
            j = ty;
            isPoint = true;
        }

        /**
         * Returns true if it is a forbidden area.
         * 
         * @return true if it is a forbidden area
         */
        private boolean isForbidden() {
            return (currentState().forbids(i, j));
        }
    }
}