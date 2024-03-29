/*
 * The MIT License
 *
 * Copyright (c) 2023 Randall Scharpf
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.randallscharpf.java.jmorsecoding.gui;

import java.awt.Component;
import java.io.File;
import java.util.function.Consumer;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Interactively chooses and returns a file from the user's drive. First, a frame
 * must be instantiated, preparing it for use. Then, {@link #getFile(java.util.function.Consumer)}
 * can be called, utilizing the created selection interface.
 * @version 1.0
 * @since 1.0
 */
public class FileSelectFrame extends javax.swing.JFrame {

    /**
     * Creates a new <code>FileSelectFrame</code> centered around the supplied parent.
     * The frame is built and prepared, but not made visible.
     * @version 1.0
     * @since 1.0
     * @param parent a component with respect to which this component should begin centered
     */
    public FileSelectFrame(Component parent) {
        initComponents();
        setLocationRelativeTo(parent);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Find an Extension to Sample");

        jFileChooser1.setDialogTitle("");
        jFileChooser1.setFileFilter(new FileNameExtensionFilter("JAR Files Only", "jar"));
        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        dispose();
        callback.accept(evt.getActionCommand().equals("ApproveSelection") ? jFileChooser1.getSelectedFile() : null);
    }//GEN-LAST:event_jFileChooser1ActionPerformed

    /**
     * Show the frame, and set the callback to be used when a file is selected. If
     * selection is cancelled or the window is closed prematurely, the callback is
     * answered with <code>null</code>. When the callback is called (either with a
     * valid <code>File</code> or <code>null</code>), this frame is hidden and
     * disposed of, so it cannot be reused. This method may not be called more than
     * once per instance.
     * @version 1.0
     * @since 1.0
     * @param callback a function to be called when a file is selected
     * @throws IllegalStateException if the method has already been called
     */
    public void getFile(Consumer<File> callback) {
        boolean b;
        synchronized (getFileLock) {
            b = getFileCalled;
            getFileCalled = true;
        }
        if (b) {
            throw new IllegalStateException("getFile has already been called on this frame!");
        }
        setVisible(true);
        this.callback = callback;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    // End of variables declaration//GEN-END:variables
    private Consumer<File> callback;
    private final Object getFileLock = new Object();
    private boolean getFileCalled = false;
}
