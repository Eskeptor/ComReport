package ex8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


class PebbleSetWindow extends JFrame {
    PebbleSetWindow(final int _width, final int _height) {
        new PebbleMainWindow(_width, _height);
        dispose();
    }

    PebbleSetWindow() {
        super("조약돌 놓기 1.0v");
        setLayout(new BorderLayout());

        JPanel setPane = new JPanel();
        setPane.setLayout(new GridLayout(2, 2));
        JLabel lblWidth = new JLabel("가로 길이");
        JTextField txtWidth = new JTextField();
        JLabel lblHeight = new JLabel("세로 길이");
        JTextField txtHeight = new JTextField();
        setPane.add(lblWidth);
        setPane.add(txtWidth);
        setPane.add(lblHeight);
        setPane.add(txtHeight);

        JPanel setButtonPane = new JPanel();
        setButtonPane.setLayout(new FlowLayout());
        JButton btnSet = new JButton("확인");
        JButton btnExit = new JButton("종료");
        btnSet.addActionListener((e) -> {
            if(txtWidth.getText().isEmpty()) {
                JOptionPane.showMessageDialog(PebbleSetWindow.this, "값이 입력되어 있지 않습니다.");
                txtWidth.requestFocus();
            } else if(txtHeight.getText().isEmpty()) {
                JOptionPane.showMessageDialog(PebbleSetWindow.this, "값이 입력되어 있지 않습니다.");
                txtHeight.requestFocus();
            } else {
                new PebbleMainWindow(txtWidth.getText(), txtHeight.getText());
                dispose();
            }
        });
        btnExit.addActionListener((e) -> {
            System.exit(0);
        });
        setButtonPane.add(btnSet);
        setButtonPane.add(btnExit);

        add(setPane, BorderLayout.CENTER);
        add(setButtonPane, BorderLayout.SOUTH);

        txtWidth.requestFocus();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300,150);
        setResizable(false);
        setVisible(true);
    }
}

class PebbleMainWindow extends JFrame {
    enum RockFlag {
        RESET, CREATE
    }

    private int peddleWidth;
    private int peddleHeight;
    private PebbleLabel[][] txtRock;
    private Pebble pebbleData;
    private final int ROCK_WIDTH = 40;
    private final int ROCK_HEIGHT = 40;
    private int isCheck;
    private PebbleLabel checkedLabel;

    PebbleMainWindow(final String _pebbleWidth, final String _pebbleHeight) {
        super("조약돌 놓기 1.0v");
        setLayout(new BorderLayout());
        peddleWidth = Integer.parseInt(_pebbleWidth);
        peddleHeight = Integer.parseInt(_pebbleHeight);
        isCheck = 0;
        initGraphicComponent();
    }

    PebbleMainWindow(final int _pebbleWidth, final int _pebbleHeight) {
        super("조약돌 놓기 1.0v");
        setLayout(new BorderLayout());
        peddleWidth = _pebbleWidth;
        peddleHeight = _pebbleHeight;

        initGraphicComponent();
    }

    private void initGraphicComponent() {
        JPanel rockLayout = new JPanel();
        rockLayout.setLayout(new GridLayout(peddleHeight, peddleWidth));
        txtRock = new PebbleLabel[peddleHeight][peddleWidth];
        for(int i = 0; i < peddleHeight; i++) {
            for(int j = 0; j < peddleWidth; j++) {
                txtRock[i][j] = new PebbleLabel("", j, i);
                Font font = txtRock[i][j].getFont();
                txtRock[i][j].setFont(font.deriveFont(font.getStyle() & ~Font.BOLD, 20));
                txtRock[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JLabel label = (JLabel)e.getComponent();
                        if(label.getForeground() == Color.RED) {
                            label.setForeground(Color.BLACK);
                            Font font = label.getFont();
                            label.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD, 20));
                            isCheck--;
                            if (isCheck == 0) {
                                checkedLabel = null;
                            }
                        }
                        else {
                            label.setForeground(Color.RED);
                            Font font = label.getFont();
                            label.setFont(font.deriveFont(font.getStyle() | Font.BOLD, 30));
                            isCheck++;
                            if (isCheck == 1)
                                checkedLabel = (PebbleLabel) e.getComponent();
                        }
                        System.out.println("isCheck: " + isCheck);
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseExited(MouseEvent e) {}
                });
                rockLayout.add(txtRock[i][j]);
            }
        }

        JPanel buttonLayout = new JPanel();
        buttonLayout.setLayout(new FlowLayout());
        JButton createRandomBtn = new JButton("번호 생성");
        JButton startBtn = new JButton("조약돌 놓기");
        JButton resetBtn = new JButton("초기화");
        JButton exitBtn = new JButton("종료");
        startBtn.setEnabled(false);
        createRandomBtn.addActionListener((e) -> {
            startBtn.setEnabled(true);
            setRocks(RockFlag.CREATE);
        });
        startBtn.addActionListener((e) -> {
            if(isCheck <= 0)
                JOptionPane.showMessageDialog(PebbleMainWindow.this, "조약돌이 선택되지 않았습니다.");
            else if (isCheck > 1)
                JOptionPane.showMessageDialog(PebbleMainWindow.this, "조약돌이 2개이상 선택되었습니다.");
            else
                startRockDown();
        });
        resetBtn.addActionListener((e) -> {
            startBtn.setEnabled(false);
            setRocks(RockFlag.RESET);
        });
        exitBtn.addActionListener((e) -> {
            System.exit(0);
        });
        buttonLayout.add(createRandomBtn);
        buttonLayout.add(startBtn);
        buttonLayout.add(resetBtn);
        buttonLayout.add(exitBtn);

        add(rockLayout, BorderLayout.CENTER);
        add(buttonLayout, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(ROCK_WIDTH * peddleWidth,ROCK_HEIGHT * peddleHeight + 60);
        setVisible(true);
    }

    private void setRocks(final RockFlag _flag) {
        switch (_flag) {
            case RESET: {
                for(int i = 0; i < peddleHeight; i++) {
                    for(int j = 0; j < peddleWidth; j++) {
                        txtRock[i][j].setText("");
                        txtRock[i][j].setForeground(Color.BLACK);
                        Font font = txtRock[i][j].getFont();
                        txtRock[i][j].setFont(font.deriveFont(font.getStyle() & ~Font.BOLD, 20));
                        isCheck = 0;
                    }
                }
                break;
            }
            case CREATE: {
                pebbleData = new Pebble(peddleWidth, peddleHeight);
                for(int i = 0; i < peddleHeight; i++) {
                    for(int j = 0; j < peddleWidth; j++) {
                        txtRock[i][j].setText(Integer.toString(pebbleData.getPebbleNodeData(i, j).getValue()));
                    }
                }
                break;
            }
        }
    }

    private void startRockDown() {
        int max = pebbleData.pebbleRockItThreeRow(checkedLabel);
        for(int i = 0; i < peddleHeight; i++) {
            for(int j = 0; j < peddleWidth; j++) {
                if(pebbleData.getPebbleNodeData(i, j).isCheck()) {
                    txtRock[i][j].setForeground(Color.RED);
                    Font font = txtRock[i][j].getFont();
                    txtRock[i][j].setFont(font.deriveFont(font.getStyle() | Font.BOLD, 30));
                }
            }
        }
        JOptionPane.showMessageDialog(PebbleMainWindow.this, "최대값: " + max);
    }
}

public class PebbleMain {
    public static void main(String[] args) {
//        new PebbleSetWindow();
        new PebbleSetWindow(8,3);
    }
}
