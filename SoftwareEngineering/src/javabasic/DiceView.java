package javabasic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiceView extends Frame {
    private TextField mDiceTf1;
    private TextField mDiceTf2;
    private Label mCell1;
    private Label mCell2;
    private DiceGame mDicegame;
    private Panel mPanel1;
    private Panel mPanel2;

    public DiceView(final String _playerName, final DiceGame _dicegame) {
        super("DiceView");
        setLayout(new FlowLayout());

        mDicegame = _dicegame;
        mDiceTf1 = new TextField(5);
        mDiceTf2 = new TextField(5);
        mCell1 = new Label();
        mCell2 = new Label();
        mPanel1 = new Panel();
        mPanel2 = new Panel();
        final Button rollBtn = new Button("Roll");
        Button cancelBtn = new Button("Exit");

        rollBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WinningStatus ws = mDicegame.roll();
                mDiceTf1.setText(String.valueOf(mDicegame.getFaceValue1()));
                mDiceTf2.setText(String.valueOf(mDicegame.getFaceValue2()));
                if (ws == WinningStatus.NotYet) {
                    mCell1.setText(String.valueOf(mDicegame.getCurCellPos1()));
                    mCell2.setText(String.valueOf(mDicegame.getCurCellPos2()));
                } else {
                    String message;
                    Scores scores = mDicegame.load();
                    Entry
                }
            }
        });

        mPanel1.add(rollBtn);
        mPanel1.add(new Label(_playerName + " face value"));
        mPanel1.add(mDiceTf1);
        mPanel1.add(new Label("AlphaDice face value"));
        mPanel1.add(mDiceTf2);
        mPanel1.add(rollBtn);
        mPanel1.add(cancelBtn);

        mPanel2.setLayout(new GridLayout(2, 2));
        mPanel2.add(new Label(_playerName));
        mPanel2.add(mCell1);
        mPanel2.add(new Label("AlphaDice"));
        mPanel2.add(mCell2);
        add(mPanel2, BorderLayout.WEST);
    }
}
