
/*
 * AnimatedPermFrame.java
 *
 * Created on Apr 2, 2012, 2:08:31 PM
 */
package permlab.ui;

import permlab.utilities.ExportInterface;
import permlab.utilities.FileUtilities;
import permlab.utilities.PaintUtilities;
import permlib.Permutation;
import permlib.property.PermProperty;
import permlib.property.Universal;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static permlab.utilities.ExportInterface.fileChooser;

/**
 * The GUI class that represents the frame for displaying an animation of a
 * series of permutations.
 *
 * @author Michael Albert, M Belton
 */
public class AnimatedPermFrame extends JFrame {

    private Permutation[] perms = new Permutation[0];
    private int permIndex;
    private Timer timer;
    private static final int INITIAL_DELAY = 100; //ms
    private int delay = INITIAL_DELAY; // ms
    private AnimationTask parentTask;

    /**
     * Creates new AnimatedPermFrame with an AnimationTask.
     *
     * @param parentTask thread generating permutations to display
     */
    public AnimatedPermFrame(AnimationTask parentTask) {
        this.parentTask = parentTask;
        this.timer = new Timer(delay, new TimeListener());
        this.perms = new Permutation[0];
        this.permIndex = -1;
        initComponents();
        totalFramesLabel.setText("of " + this.perms.length);
        addMenu();
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new HelpDispatcher("Animation window", "AnimatedPermFrameHelp.html", this));
    }

    /**
     * Creates new AnimatedPermFrame.
     */
    public AnimatedPermFrame() {
        this.parentTask = null;
        this.timer = new Timer(delay, new TimeListener());
        this.perms = new Permutation[0];
        this.permIndex = -1;
        initComponents();
        totalFramesLabel.setText("of " + this.perms.length);
        addMenu();
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new HelpDispatcher("Animation window", "AnimatedPermFrameHelp.html", this));
    }

    /**
     * Creates new AnimatedPermFrame from a collection of perms.
     *
     * @param perms collection to animate
     */
    public AnimatedPermFrame(Collection<Permutation> perms) {
        initComponents();

        this.timer = new Timer(delay, new TimeListener());
        addPerms(perms);
        this.permIndex = -1;

        totalFramesLabel.setText("of " + this.perms.length);
        addMenu();
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new HelpDispatcher("Animation window", "AnimatedPermFrameHelp.html", this));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenu = new JPopupMenu();
        controlPanel = new JPanel();
        displayPanel = new DisplayPanel();
        optionsPanel = new JPanel();
        stepButton = new JButton();
        startButton = new JButton();
        stopButton = new JButton();
        resetButton = new JButton();
        speedSlider = new JSlider();
        slowLabel = new JLabel();
        fastLabel = new JLabel();
        frameLabel = new JLabel();
        frameNumberTextField = new JTextField();
        totalFramesLabel = new JLabel();
        sortButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        displayPanel.setBorder(BorderFactory.createEtchedBorder());
        displayPanel.setPreferredSize(new Dimension(500, 300));
        displayPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                displayPanelpopupMenuHandler(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                displayPanelpopupMenuHandler(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                displayPanelpopupMenuHandler(evt);
            }
        });

        GroupLayout displayPanelLayout = new GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 321, Short.MAX_VALUE)
        );

        optionsPanel.setPreferredSize(new Dimension(500, 300));

        stepButton.setText("Step");
        stepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepButtonActionPerformed(evt);
            }
        });

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Stop");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        speedSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                speedSliderStateChanged(evt);
            }
        });

        slowLabel.setText("Slow");

        fastLabel.setText("Fast");

        frameLabel.setText("Frame Number");

        frameNumberTextField.setText("0");
        frameNumberTextField.setPreferredSize(new Dimension(110, 28));
        frameNumberTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                frameNumberTextFieldMouseClicked(evt);
            }
        });
        frameNumberTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frameNumberTextFieldActionPerformed(evt);
            }
        });

        totalFramesLabel.setText("of ???");

        sortButton.setText("Sort");
        sortButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortButtonActionPerformed(evt);
            }
        });

        GroupLayout optionsPanelLayout = new GroupLayout(optionsPanel);
        optionsPanel.setLayout(optionsPanelLayout);
        optionsPanelLayout.setHorizontalGroup(
            optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(optionsPanelLayout.createSequentialGroup()
                .addGroup(optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(optionsPanelLayout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(sortButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(frameNumberTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalFramesLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(GroupLayout.Alignment.TRAILING, optionsPanelLayout.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(GroupLayout.Alignment.TRAILING, optionsPanelLayout.createSequentialGroup()
                                .addComponent(slowLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(speedSlider, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fastLabel))
                            .addGroup(GroupLayout.Alignment.TRAILING, optionsPanelLayout.createSequentialGroup()
                                .addComponent(stepButton)
                                .addGap(18, 18, 18)
                                .addComponent(startButton)
                                .addGap(18, 18, 18)
                                .addComponent(stopButton)
                                .addGap(18, 18, 18)
                                .addComponent(resetButton)
                                .addGap(12, 12, 12)))))
                .addContainerGap())
            .addGroup(GroupLayout.Alignment.TRAILING, optionsPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(frameLabel)
                .addGap(150, 150, 150))
        );
        optionsPanelLayout.setVerticalGroup(
            optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(optionsPanelLayout.createSequentialGroup()
                .addGroup(optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(optionsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(fastLabel)
                            .addComponent(slowLabel)))
                    .addGroup(optionsPanelLayout.createSequentialGroup()
                        .addComponent(speedSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(resetButton)
                            .addComponent(stopButton)
                            .addComponent(startButton)
                            .addComponent(stepButton))))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(frameLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(optionsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(frameNumberTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalFramesLabel)
                    .addComponent(sortButton))
                .addGap(78, 78, 78))
        );

        GroupLayout controlPanelLayout = new GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addComponent(optionsPanel, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(displayPanel, GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addComponent(displayPanel, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(optionsPanel, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(controlPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(controlPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Sorts the collection of permutation being displayed in lexicographical
     * order.
     *
     * @param evt event of button being pressed
     */
    private void sortButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortButtonActionPerformed
        timer.stop();
        Arrays.sort(perms);
        resetButtonActionPerformed(evt);
    }//GEN-LAST:event_sortButtonActionPerformed

    /**
     * Action responding to user specifying a permutation at a frame number to
     * display.
     *
     * @param evt event in the text field displaying the frame number
     */
    private void frameNumberTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_frameNumberTextFieldActionPerformed
        try {
            permIndex = Integer.parseInt(frameNumberTextField.getText()) - 1;
            refresh();
        } catch (NumberFormatException ignored) {
        }
    }//GEN-LAST:event_frameNumberTextFieldActionPerformed

    /**
     * Stops the animation when the frame number text field is clicked.
     *
     * @param evt event when mouse clicks the frame number text field.
     */
    private void frameNumberTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_frameNumberTextFieldMouseClicked
        timer.stop();
    }//GEN-LAST:event_frameNumberTextFieldMouseClicked

    /**
     * Adjusts the spend that the animation is shown depending on the slider
     * value.
     *
     * @param evt event of slider value being altered
     */
    private void speedSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_speedSliderStateChanged
        if (!speedSlider.getValueIsAdjusting()) {
            int x = speedSlider.getValue();
            delay = x == 0 ? 10000 : (10000 / x - 100);
            timer.setDelay(delay);
        }
    }//GEN-LAST:event_speedSliderStateChanged

    /**
     * Resets the animation by going back to the first frame.
     *
     * @param evt event of reset button being pressed
     */
    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        timer.stop();
        permIndex = -1;
        frameNumberTextField.setText("" + (permIndex + 1));
        displayPanel.repaint();
    }//GEN-LAST:event_resetButtonActionPerformed

    /**
     * Stops the animation being displayed.
     *
     * @param evt event of the stop button being pressed
     */
    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        timer.stop();
    }//GEN-LAST:event_stopButtonActionPerformed

    /**
     * Starts the animation.
     *
     * @param evt event of the start button being pressed
     */
    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        timer.start();
    }//GEN-LAST:event_startButtonActionPerformed

    /**
     * Steps through the animation by one frame.
     *
     * @param evt event of the stop button being pressed
     */
    private void stepButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepButtonActionPerformed
        timer.stop();
        step();
    }//GEN-LAST:event_stepButtonActionPerformed

    /**
     * If the pop-up menu is triggered then stop the animation and show the menu
     * at the spot clicked.
     *
     * @param evt the mouse being clicked
     */
    private void displayPanelpopupMenuHandler(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_displayPanelpopupMenuHandler
        if (evt.isPopupTrigger()) {
            timer.stop();
            popupMenu.show(evt.getComponent(),
                    evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_displayPanelpopupMenuHandler

    /**
     * As the window is closed stop the animation and the threads traversing the
     * collection and calculating the rest of the collection.
     *
     * @param evt the window being closed
     */
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        timer.stop();
        parentTask.cancel(true);
    }//GEN-LAST:event_formWindowClosed

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
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnimatedPermFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AnimatedPermFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel controlPanel;
    private JPanel displayPanel;
    private JLabel fastLabel;
    private JLabel frameLabel;
    private JTextField frameNumberTextField;
    private JPanel optionsPanel;
    private JPopupMenu popupMenu;
    private JButton resetButton;
    private JLabel slowLabel;
    private JButton sortButton;
    private JSlider speedSlider;
    private JButton startButton;
    private JButton stepButton;
    private JButton stopButton;
    private JLabel totalFramesLabel;
    // End of variables declaration//GEN-END:variables
    private JMenuItem exportOption = new JMenuItem("Export current");
    private JMenuItem exportAllAsTextOption = new JMenuItem("Export all in textbox");
    private JMenuItem exportAllAsGIF = new JMenuItem("Export all as a GIF");

    /**
     * Creates the pop-up menu.
     */
    private void addMenu() {
        popupMenu.add(exportOption);
        exportOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (permIndex >= 0) {
                    exportCurrent();
                }
            }
        });
        popupMenu.add(exportAllAsTextOption);
        exportAllAsTextOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextFrame textDisplay = new TextFrame();
                textDisplay.addText(perms);
                textDisplay.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                textDisplay.setLocation(
                        displayPanel.getX() + displayPanel.getWidth() + 10,
                        displayPanel.getY());
                textDisplay.pack();
                textDisplay.setVisible(true);
            }
        });
        popupMenu.add(exportAllAsGIF);
        exportAllAsGIF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportPermCollectionAsGIF(perms, displayPanel.getWidth(), displayPanel.getHeight());
            }
        });
    }

    /**
     * Exports the entire collection as a GIF.
     * NOTE: This GIF is set up so that it will display all permutations once
     * without looping.
     *
     * @param perms permutations to be exported
     * @param width width of the GIF
     * @param height height of the GIF
     */
    public void exportPermCollectionAsGIF(Permutation[] perms, int width, int height) {
        BufferedImage image;
        Graphics2D g;
        File savingFile = new File("GIF.gif");
        fileChooser.setSelectedFile(savingFile);
        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            savingFile = fileChooser.getSelectedFile();
            if (FileUtilities.canSaveFileHere(this, savingFile)) {
                try {
                    Permutation p = perms[0];
                    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    g = image.createGraphics();
                    PaintUtilities.paint(g, p, width, height, false);

                    ImageTypeSpecifier spec = new ImageTypeSpecifier(image);
                    ImageWriter wr = ImageIO.getImageWriters(spec, "GIF").next();
                    wr.setOutput(ImageIO.createImageOutputStream(savingFile));
                    ImageWriteParam param = wr.getDefaultWriteParam();
                    IIOMetadata meta = wr.getDefaultImageMetadata(spec, param);
                    wr.prepareWriteSequence(meta);

                    for (Permutation per : perms) {
                        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                        g = image.createGraphics();
                        PaintUtilities.paint(g, per, width, height, false);

                        wr.writeToSequence(new IIOImage(image, null, null), param);
                    }
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * Export the current permutation being displayed.
     */
    private void exportCurrent() {
        ExportDialog ex = new ExportDialog(this, true);
        ex.setVisible(true);
        ExportInterface e = ex.getExportInterface();
        if (e != null) {
            e.export(this, perms[permIndex], this.getWidth(), this.getHeight(), false);
        }
    }

    /**
     * Add permutations to the current collection being displayed.
     *
     * @param extraPerms additional permutations to add
     * @param prop property pertaining to permutation collection
     */
    void addPerms(Collection<Permutation> extraPerms, PermProperty prop) {
        if (prop instanceof Universal) {
            Permutation[] newPerms = new Permutation[this.perms.length + extraPerms.size()];
            extraPerms.toArray(newPerms);
            System.arraycopy(perms, 0, newPerms, extraPerms.size(), perms.length);
            this.perms = newPerms;
        } else {
            ArrayList<Permutation> temp = new ArrayList<>();
            for (Permutation p : extraPerms) {
                if (prop.isSatisfiedBy(p)) {
                    temp.add(p);
                }
            }
            Permutation[] newPerms = new Permutation[this.perms.length + temp.size()];
            temp.toArray(newPerms);
            System.arraycopy(perms, 0, newPerms, temp.size(), perms.length);
            this.perms = newPerms;
        }
        totalFramesLabel.setText("of " + perms.length);
        repaint();
    }

    /**
     * Add permutations to the current collection being displayed.
     *
     * @param extraPerms additional permutations to add
     */
    void addPerms(Collection<Permutation> extraPerms) {
        addPerms(extraPerms, new Universal());
    }

    /**
     * Inner class acts as a timer to step through the frames and display the
     * collection as an animation.
     */
    private class TimeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            step();
        }
    }

    /**
     * Move to next frame.
     */
    public void step() {
        permIndex++;
        refresh();
    }

    /**
     * Repaint the display to match the current frame number.
     */
    private void refresh() {
        permIndex = Math.max(0, Math.min(permIndex, perms.length - 1));
        frameNumberTextField.setText("" + (permIndex + 1));
        displayPanel.repaint();
    }

    /**
     * Panel to display the permutation at the current frame number.
     */
    private class DisplayPanel extends JPanel {

        public DisplayPanel() {
        }

        @Override
        public void paint(Graphics g) {
            Permutation p = null;
            if (0 <= permIndex && permIndex < perms.length) {
                p = perms[permIndex];
            }
            // NOTE: This paint method was moved to a utility class to make use of it when exporting to image formats.
            PaintUtilities.paint(g, p, getWidth(), getHeight(), false);
        }
    }
}