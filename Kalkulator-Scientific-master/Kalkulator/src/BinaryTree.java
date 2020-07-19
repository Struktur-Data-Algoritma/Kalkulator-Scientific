import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

/*
 * Represents a binary tree which is composed of nodes that can hold any object.
 * The implementation is not complete but is sufficient for this calculator.
 *
 * @author Luka Kralj
 * @author Sabar Muhamad Itikap
 * @version 19 07 2020
 */

public class BinaryTree {
    /*ATRIBUT*/
    private int size;
    private Node root;

    public BinaryTree(Node root) { //KONSTRUKTOR DENGAN PARAMETER NODE, YANG AKAN DIJADIKAN ROOT DARI SUATU BINARY TREE
        size = 0;
        if (root != null) {
            size++;
        }
        this.root = root;
    }

/*KONSTRUKTOR TANPA PARAMETER SEHINGGA ROOT BINARY TREE MASIH NULL*/
    public BinaryTree() {
        this(null);
    }

/*RETURN TRUE JIKA V MEMILIKI LEFT/RIGHT CHILD*/
    public boolean isInternal(Node v) {
        return hasLeft(v) || hasRight(v);
    }

/*RETURN TRUE JIKA V TIDAK MEMILIKI LEFT DAN RIGHT CHILD*/
    public boolean isExternal(Node v) {
        return !hasLeft(v) && !hasRight(v);
    }

/*RETURN TRUE JIKA V MEMILIKI LEFT CHILD DAN TIDAK NULL*/
    public boolean hasLeft(Node v) {
        return v.getLeft() != null;
    }

/*RETURN TRUE JIKA V MEMILIKI RIGHT CHILD DAN TIDAK NULL*/
    public boolean hasRight(Node v) {
        return v.getRight() != null;
    }

/*RETURN TRUE JIKA V ADALAH ROOT DARI TREE*/
    public boolean isRoot(Node v) {
        return v == root;
    }

/*RETURN ROOT NODE DARI TREE*/
    public Node root() {
        return root;
    }

    /**
     *
     * @return Number of nodes in the tree.
     */
/*RETURN JUMLAH NODE DI DALAM TREE*/
    public int size(){
        return size;
    }

/*UPDATE BINARY TREE, ROOT LAMA DIISI DENGAN ROOT BARU, ROOT SEBELUMNYA MENJADI ANAK KIRI DARI ROOT BARU (ANAK KANAN OPSIONAL)*/
    public void updateTree(@NotNull Node newRoot, @Nullable Node rightChild) {
        if (root == null) {
            root = newRoot;
        }
        else {
            root.setParent(newRoot);
            newRoot.setLeft(root);
            root = newRoot;
        }
        size++;

        if (rightChild != null) {
           root.setRight(rightChild);
           size++;
        }

    }
    
    /***TRAVERSAL***/
//    public void preOrder(Node node){
//        if (node != null) {
//            System.out.print(" " + node.getValue());
//            preOrder(node.getLeftChild());
//            preOrder(node.getRightChild());
//        }
//    }
//    
//    public void inOrder(Node node){
//        if (node != null) {
//            inOrder(node.getLeftChild());
//            System.out.print(" " + node.getValue());
//            inOrder(node.getRightChild());
//        }
//    }
//    
//    public void postOrder(Node node){
//        if (node != null) {
//            postOrder(node.getLeftChild());
//            postOrder(node.getRightChild());
//            System.out.print(" " + node.getValue());
//        }
//    }
}
