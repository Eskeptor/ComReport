package javabasic.ex14;

import javabasic.ex14.original.PPMEncoder;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ConverterWindow extends JFrame {
    private File mOriginalFile;
    private BufferedImage mConvertedImage;
    private JFileChooser mFileChooser;

    ConverterWindow() {
        super("JPG to PGM(P2)");
        setLayout(new BorderLayout());
        mFileChooser = new JFileChooser();

        JPanel selectPane = new JPanel();
        JButton btnSelect = new JButton("Select");
        JLabel lblFileName = new JLabel();
        selectPane.setLayout(new FlowLayout());
        selectPane.add(btnSelect);
        selectPane.add(lblFileName);

        JPanel buttonPane = new JPanel();
        JButton btnConvert = new JButton("Convert");
        JButton btnSave = new JButton("Save");
        JButton btnExit = new JButton("Exit");
        btnSave.setEnabled(false);
        buttonPane.setLayout(new FlowLayout());
        buttonPane.add(btnConvert);
        buttonPane.add(btnSave);
        buttonPane.add(btnExit);

        btnSelect.addActionListener((e) -> {
            mFileChooser.setDialogTitle("Load Image");
            if (mFileChooser.showOpenDialog(ConverterWindow.this) == JFileChooser.APPROVE_OPTION) {
                mOriginalFile = mFileChooser.getSelectedFile();
                lblFileName.setText(mOriginalFile.getName());
                btnConvert.setEnabled(true);
                btnSave.setEnabled(false);
            }
        });
        btnConvert.addActionListener((e) -> {
            btnConvert.setEnabled(false);
            btnSave.setEnabled(true);
            mConvertedImage = PGMConverterP2.convert(mOriginalFile);
        });
        btnSave.addActionListener((e) -> {
            if (mConvertedImage != null) {
                try {
                    mFileChooser.setDialogTitle("Save Image");
                    mFileChooser.setFileFilter(new FileNameExtensionFilter("PGM", "pgm"));
                    String savePath;
                    if (mFileChooser.showSaveDialog(ConverterWindow.this) == JFileChooser.APPROVE_OPTION) {
                        savePath = mFileChooser.getSelectedFile().getPath();
                        PPMEncoder encoder = new PPMEncoder(savePath + ".pgm");
                        encoder.encode(mConvertedImage);
                        encoder.close();
                        JOptionPane.showConfirmDialog(null, "Save Complete", "성공", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnExit.addActionListener((e) -> System.exit(0));


        add(selectPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.SOUTH);
        setSize(400, 150);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        ConverterWindow window = new ConverterWindow();
    }
}
