package ex4;


import java.util.Scanner;

class Node {
    public static final int NO_DATA = -1;
    private int data;
    private Node left;
    private Node right;
    Node (final int _data) {
        data = _data;
        left = null;
        right = null;
    }

    public Node getLeft () {
        return left;
    }

    public Node getRight () {
        return right;
    }

    public void setLeft (final Node _left) {
        left = _left;
    }

    public void setRight (final Node _right) {
        right = _right;
    }

    public int getData () {
        return data;
    }

    public void setData (final int _data) {
        data = _data;
    }
}

class BST {
    private Node root;
    private int idx;

    BST() {
        root = new Node(Node.NO_DATA);
        idx = 0;
    }

    public void insert (final int _data) {
        Node curNode = root;
        Node parentNode;
        Node newNode = new Node(_data);

        while (curNode != null) {
            parentNode = curNode;
            if (root.getData() == Node.NO_DATA) {
                root.setData(_data);
                break;
            } else if (curNode.getData() > _data) {
                curNode = curNode.getLeft();
                if (curNode == null) {
                    parentNode.setLeft(newNode);
                    break;
                }
            } else {
                curNode = curNode.getRight();
                if (curNode == null) {
                    parentNode.setRight(newNode);
                    break;
                }
            }
        }
    }

    public void delete (final int _data) {
        Node curNode = root;
        Node parentNode = root;

        while (curNode.getData() != _data) {
            parentNode = curNode;
            if (curNode.getData() > _data) {
                curNode = curNode.getLeft();
            } else {
                curNode = curNode.getRight();
            }
            if (curNode == null) {
                System.out.println(_data + "를 찾지 못했습니다.");
                return;
            }
        }

        if (curNode.getLeft() == null && curNode.getRight() == null) {
            if (curNode == root) {
                root = null;
            } else if (curNode == parentNode.getLeft()) {
                parentNode.setLeft(null);
            } else {
                parentNode.setRight(null);
            }
        } else if (curNode.getLeft() == null) {
            if (curNode == root) {
                root = curNode.getRight();
            } else if (curNode == parentNode.getLeft()) {
                parentNode.setLeft(curNode.getRight());
            } else {
                parentNode.setRight(curNode.getRight());
            }
        } else if (curNode.getRight() == null) {
            if (curNode == root) {
                root = curNode.getLeft();
            } else if (curNode == parentNode.getLeft()) {
                parentNode.setLeft(curNode.getLeft());
            } else {
                parentNode.setRight(curNode.getLeft());
            }
        } else {
            Node biggerNode = getBigger(curNode);

            if (curNode == root) {
                root = biggerNode;
            } else if (curNode == parentNode.getLeft()) {
                parentNode.setLeft(biggerNode);
            } else {
                parentNode.setRight(biggerNode);
            }
            biggerNode.setLeft(curNode.getLeft());
        }
        System.out.println(_data + "를 제거하였습니다.");
    }

    private Node getBigger (Node _node) {
        Node currentNode = _node.getRight();
        Node parentNode = null;
        Node biggerNode = null;

        while (currentNode != null) {
            parentNode = biggerNode;
            biggerNode = currentNode;
            currentNode = currentNode.getLeft();
        }

        if (biggerNode != _node.getRight()) {
            parentNode.setLeft(biggerNode.getRight());
            biggerNode.setRight(_node.getRight());
        }
        return biggerNode;
    }

    public int search (final int _data) {
        Node node = root;
        while (node != null) {
            if (node.getData() > _data) {
                node = node.getLeft();
            } else if (node.getData() < _data) {
                node = node.getRight();
            } else {
                return node.getData();
            }
        }
        return Node.NO_DATA;
    }

    private void treeCheck (final Node _root) {
        if (_root != null) {
            treeCheck(_root.getLeft());
            idx++;
            System.out.printf("%4d ", _root.getData());
            if (idx % 10 == 0) {
                System.out.println();
            }
            treeCheck(_root.getRight());
        }
    }

    public void print () {
        treeCheck(root);
        idx = 0;
    }
}

public class BSTFind {
    static final int MAX = 100;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BST bst = new BST();
        for (int i = 0; i < MAX; i++) {
            bst.insert((int) (2000 * Math.random()));
        }

        System.out.println("이진트리");
        bst.print();
        System.out.println();

        System.out.print("탐색할 값 : ");
        int findData = scanner.nextInt();
        int result = bst.search(findData);
        if (result == Node.NO_DATA) {
            System.out.println(findData + "를 찾지 못했습니다.");
        } else {
            System.out.println(findData + "를 찾았습니다.");
        }
        System.out.println();

        System.out.print("제거할 값 : ");
        int deleteData = scanner.nextInt();
        bst.delete(deleteData);
        System.out.println();

        System.out.println("제거한 후의 이진트리");
        bst.print();
        System.out.println();
    }
}
