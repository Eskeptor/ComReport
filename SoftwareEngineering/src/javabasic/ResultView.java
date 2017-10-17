package javabasic;

import java.awt.*;

public class ResultView extends Frame {
    private Label mMessage;

    public ResultView(final String _message, final Entry _entry) {
        super("Result View");

        setLayout(new FlowLayout());

        mMessage = new Label(_message);
        Button scoreBtn = new Button("Score");
        Button cancelBtn = new Button("Exit");

        scoreBtn.addActionListener((e)->{
            new ScoreView();
        });

        cancelBtn.addActionListener((e)->{
            setVisible(false);
            dispose();
        });

        add(mMessage);
        add(scoreBtn);
        add(cancelBtn);

        setSize(500, 100);
        setVisible(true);
    }
}
