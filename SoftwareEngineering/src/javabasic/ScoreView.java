package javabasic;

import java.awt.*;

public class ScoreView extends Frame {
    private ScrollPane mScroll;
    private DiceGame mDicegame;
    private Panel mPanel1;
    private Panel mPanel2;
    private Button mExitBtn;

    public ScoreView() {
        super("Score View");
        setLayout(new BorderLayout());

        mDicegame = new DiceGame();
        mPanel1 = new Panel();
        mPanel2 = new Panel();
        mScroll = new ScrollPane();
        mExitBtn = new Button("Exit");
        Scores scores = mDicegame.load();
        final java.awt.List list = scores.getList();

        mScroll.setSize(250, 100);
        mExitBtn.addActionListener((e)->{
            setVisible(false);
            dispose();
        });

        mScroll.add(list);
        mPanel1.add(mScroll);
        mPanel2.add(mExitBtn);
        add(mPanel1, BorderLayout.NORTH);
        add(mPanel2, BorderLayout.CENTER);

        setSize(300, 200);
        setVisible(true);
    }
}
