package ex8;

import java.awt.*;

public class StartView extends Frame {
    private TextField mNameTf;
    private String mName;
    private D6DiceGame mDicegame;
    private Panel mPanel1;
    private Panel mPanel2;

    public StartView(final String _title) {
        super(_title);
        setLayout(new FlowLayout());

        Button configBtn = new Button("Configure");
        Button playBtn = new Button("Play");
        Button exitBtn = new Button("Exit");

        mPanel1 = new Panel();
        mPanel2 = new Panel();
        mNameTf = new TextField(20);

        mPanel1.add(mNameTf);
        mPanel1.add(playBtn);

        add(mPanel1);

        mPanel2.add(configBtn);
        mPanel2.add(exitBtn);

        add(mPanel2);

        playBtn.addActionListener((e)->{
            if(mDicegame == null)
                mDicegame = new D6DiceGame();
            mName = mNameTf.getText();
            new DiceView(mName, mDicegame);
            setVisible(false);
            dispose();
        });

        exitBtn.addActionListener((e)->{
            System.exit(0);
        });

        setSize(300, 150);
        setVisible(true);
    }
}
