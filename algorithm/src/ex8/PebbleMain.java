package ex8;

import javax.swing.*;
import java.awt.*;

class PebbleNode {
    private int value;
    private boolean isCheck;
    PebbleNode(final int _value) {
        value = _value;
        isCheck = false;
    }

    public void check() {
        isCheck = true;
    }

    public int getValue() {
        return value;
    }

    public boolean isCheck() {
        return isCheck;
    }
}

class Pebble {
    private final int LIMIT = 15;
    private PebbleNode[][] nodes;
    private int pebbleWidth;
    private int pebbleHeight;

    Pebble(final int _width, final int _height) {
        nodes = new PebbleNode[_height][_width];
        pebbleWidth = _width;
        pebbleHeight = _height;

        int plusMinus;
        for(int i = 0; i < _height; i++) {
            for(int j = 0; j < _width; j++) {
                plusMinus = (int)(Math.random() * 2);
                if(plusMinus == 0)
                    nodes[i][j] = new PebbleNode((int)(Math.random() * LIMIT) + 1);
                else
                    nodes[i][j] = new PebbleNode(((int)(Math.random() * LIMIT) + 1) * -1);
            }
        }
    }

    public PebbleNode getPebbleNodeData(final int _height, final int _width) {
        return nodes[_height][_width];
    }

    public void pebbleRockIt() {

    }

    public void printPebble() {
        System.out.print("┌");
        for(int i = 0; i < pebbleWidth * 4 - 1; i++)
            System.out.print("─");
        System.out.println("┐");

        for(int i = 0; i < pebbleHeight; i++) {
            System.out.print("│");
            for(int j = 0; j < pebbleWidth; j++) {
                if(nodes[i][j].getValue() > 0) {
                    if(nodes[i][j].getValue() < 10)
                        System.out.print("  " + nodes[i][j].getValue() + "│");
                    else
                        System.out.print(" " + nodes[i][j].getValue() + "│");
                } else {
                    if(nodes[i][j].getValue() > -10)
                        System.out.print(" " + nodes[i][j].getValue() + "│");
                    else
                        System.out.print(nodes[i][j].getValue() + "│");
                }

            }
            System.out.println();
        }

        System.out.print("└");
        for(int i = 0; i < pebbleWidth * 4 - 1; i++)
            System.out.print("─");
        System.out.println("┘");
        System.out.println();
    }
}

class PebbleSetWindow extends JFrame {
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
    private JLabel[][] txtRock;
    private Pebble pebbleData;
    PebbleMainWindow(final String _pebbleWidth, final String _pebbleHeight) {
        super("조약돌 놓기 1.0v");
        final int ROCK_WIDTH = 40;
        final int ROCK_HEIGHT = 40;
        setLayout(new BorderLayout());

        peddleWidth = Integer.parseInt(_pebbleWidth);
        peddleHeight = Integer.parseInt(_pebbleHeight);

        JPanel rockLayout = new JPanel();
        rockLayout.setLayout(new GridLayout(peddleHeight, peddleWidth));
        txtRock = new JLabel[peddleHeight][peddleWidth];
        for(int i = 0; i < peddleHeight; i++) {
            for(int j = 0; j < peddleWidth; j++) {
                txtRock[i][j] = new JLabel();
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
        for(int i = 0; i < peddleHeight; i++) {
            for(int j = 0; j < peddleWidth; j++) {
                if(pebbleData.getPebbleNodeData(i, j).isCheck())
                    txtRock[i][j].setForeground(Color.RED);
            }
        }
    }
}

public class PebbleMain {
    public static void main(String[] args) {
        new PebbleSetWindow();
    }
}
