package javabasic.ex9;

abstract class Number {
    private int[] numbers = new int[10];
    abstract void play();
    void setNumbers(final int _value, final int _idx) {
        int length = numbers.length;
        if(length - 1 >= _idx) {
            numbers[_idx] = _value;
        } else {
            System.out.println("배열 범위를 벗어남");
        }
    }
    void printNumbers() {
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println();

    }
}

class NumberInteger extends Number {
    @Override
    void play() {
        for (int i = 0; i < 10; i++) {
            setNumbers((int)(Math.random() * 10), i);
        }
        System.out.println("배열 생성 완료");
    }
}

public class Test {
    public static void main(String[] args) {
        NumberInteger ii = new NumberInteger();
        ii.play();
        ii.printNumbers();
    }
}
