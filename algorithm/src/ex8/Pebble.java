package ex8;

public class Pebble {
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
                    nodes[i][j] = new PebbleNode((int)(Math.random() * LIMIT) + 1, j, i);
                else
                    nodes[i][j] = new PebbleNode(((int)(Math.random() * LIMIT) + 1) * -1, j, i);
            }
        }
    }

    public PebbleNode getPebbleNodeData(final int _height, final int _width) {
        return nodes[_height][_width];
    }

    /*
        패턴
        0. 1    1. 0    2. 0    3. 1
           0       1       0       0
           0       0       1       1
     */
    public int pebbleRockItThreeRow(final PebbleLabel _rock) {
        System.out.println("x: " + _rock.getXpos() + ", y: " + _rock.getYpos());
        int selectedRockX = _rock.getXpos();
        int selectedRockY = _rock.getYpos();

        int curCase = selectedRockY;
        int total = nodes[selectedRockY][selectedRockX].getValue();

        for(int x = selectedRockX + 1; x < pebbleWidth; x++) {
            switch (curCase) {
                case 0: {
                    if(nodes[1][x].getValue() > nodes[2][x].getValue()) {
                        nodes[1][x].check();
                        total += nodes[1][x].getValue();
                        curCase = 1;
                    } else {
                        nodes[2][x].check();
                        total += nodes[2][x].getValue();
                        curCase = 2;
                    }
                    break;
                }
                case 1: {
                    if(nodes[0][x].getValue() > nodes[2][x].getValue()) {
                        if(nodes[0][x].getValue() > nodes[0][x].getValue() + nodes[2][x].getValue()) {
                            nodes[0][x].check();
                            total += nodes[0][x].getValue();
                            curCase = 0;
                        } else {
                            nodes[0][x].check();
                            nodes[2][x].check();
                            total += nodes[0][x].getValue() + nodes[2][x].getValue();
                            curCase = 3;
                        }
                    } else {
                        if(nodes[2][x].getValue() > nodes[0][x].getValue() + nodes[2][x].getValue()) {
                            nodes[2][x].check();
                            total += nodes[2][x].getValue();
                            curCase = 2;
                        } else {
                            nodes[0][x].check();
                            nodes[2][x].check();
                            total += nodes[0][x].getValue() + nodes[2][x].getValue();
                            curCase = 3;
                        }
                    }
                    break;
                }
                case 2: {
                    if(nodes[1][x].getValue() > nodes[0][x].getValue()) {
                        nodes[1][x].check();
                        total += nodes[1][x].getValue();
                        curCase = 1;
                    } else {
                        nodes[0][x].check();
                        total += nodes[0][x].getValue();
                        curCase = 0;
                    }
                    break;
                }
                case 3: {
                    nodes[1][x].check();
                    total += nodes[1][x].getValue();
                    curCase = 1;
                    break;
                }
            }
        }

        curCase = selectedRockY;
        for(int x = selectedRockX - 1; x >= 0; x--) {
            switch (curCase) {
                case 0: {
                    if(nodes[1][x].getValue() > nodes[2][x].getValue()) {
                        nodes[1][x].check();
                        total += nodes[1][x].getValue();
                        curCase = 1;
                    } else {
                        nodes[2][x].check();
                        total += nodes[2][x].getValue();
                        curCase = 2;
                    }
                    break;
                }
                case 1: {
                    if(nodes[0][x].getValue() > nodes[2][x].getValue()) {
                        if(nodes[0][x].getValue() > nodes[0][x].getValue() + nodes[2][x].getValue()) {
                            nodes[0][x].check();
                            total += nodes[0][x].getValue();
                            curCase = 0;
                        } else {
                            nodes[0][x].check();
                            nodes[2][x].check();
                            total += nodes[0][x].getValue() + nodes[2][x].getValue();
                            curCase = 3;
                        }
                    } else {
                        if(nodes[2][x].getValue() > nodes[0][x].getValue() + nodes[2][x].getValue()) {
                            nodes[2][x].check();
                            total += nodes[2][x].getValue();
                            curCase = 2;
                        } else {
                            nodes[0][x].check();
                            nodes[2][x].check();
                            total += nodes[0][x].getValue() + nodes[2][x].getValue();
                            curCase = 3;
                        }
                    }
                    break;
                }
                case 2: {
                    if(nodes[1][x].getValue() > nodes[0][x].getValue()) {
                        nodes[1][x].check();
                        total += nodes[1][x].getValue();
                        curCase = 1;
                    } else {
                        nodes[0][x].check();
                        total += nodes[0][x].getValue();
                        curCase = 0;
                    }
                    break;
                }
                case 3: {
                    nodes[1][x].check();
                    total += nodes[1][x].getValue();
                    curCase = 1;
                    break;
                }
            }
        }
        return total;
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
