package javabasic.ex9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class PictureView extends JFrame {
    PictureView(final String _title) {
        super(_title);
        DrawCircle circle = new DrawCircle();
        circle.addMouseListener(new MouseHandler());
        add(circle);
    }

    class MouseHandler implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            JOptionPane.showConfirmDialog(null, "x:" + e.getX() + ", y:" + e.getY(),
                    "알림", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        }
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }

    class DrawCircle extends JPanel {
        private Image image;
        DrawCircle() {
            image = new ImageIcon(getClass().getResource("1.jpg")).getImage();
        }
        @Override
        protected void paintComponent(Graphics g) {
            setOpaque(false);
            g.drawImage(image, 0, 0, this);

            // 눈
            g.setColor(Color.RED);
            g.drawOval(147, 158, 40, 40);
            g.drawOval(210, 158, 40, 40);
            g.drawString("왼쪽 눈", 142, 152);
            g.drawString("오른쪽 눈", 204, 152);

            // 코
            g.setColor(Color.GREEN);
            g.drawOval(178, 185, 40, 40);
            g.drawString("코", 191, 212);

            // 입
            g.setColor(Color.YELLOW);
            g.drawOval(178, 228, 40, 30);
            g.drawString("입", 191, 248);

            // 귀
            g.setColor(Color.magenta);
            g.drawOval(121, 171, 18, 40);
            g.drawOval(256, 171, 18, 40);
            g.drawString("왼쪽 귀", 80, 196);
            g.drawString("오른쪽 귀", 279, 196);

            // 어깨
            g.setColor(Color.WHITE);
            g.drawRect(52, 283, 40, 40);
            g.drawRect(302, 283, 40, 40);
            g.drawString("왼쪽 어깨", 42, 273);
            g.drawString("오른쪽 어깨", 292, 273);

            // 목
            final int polygonx[] = {159, 198, 237, 237, 198, 159};
            final int polygony[] = {263, 287, 263, 302, 318, 302};
            g.setColor(Color.CYAN);
            g.drawPolygon(polygonx, polygony, polygonx.length);
            g.drawString("목", 191, 304);
        }
    }
}

public class Ex9 {
    public static void main(String[] args) {
        PictureView view = new PictureView("과제");
        view.setSize(400, 400);
        view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        view.setResizable(false);
        view.setVisible(true);
    }
}
