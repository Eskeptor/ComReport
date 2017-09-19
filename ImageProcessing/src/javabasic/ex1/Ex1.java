package javabasic.ex1;

import javax.swing.JOptionPane;

public class Ex1 {
    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog(null, "학번을 입력하세요.");
        int hakbun = Integer.parseInt(input);
        if(hakbun % 2 == 0) {
            JOptionPane.showConfirmDialog(null, "짝수 학번 입니다.", "알림", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showConfirmDialog(null, "홀수 학번 입니다.", "알림", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    }
}