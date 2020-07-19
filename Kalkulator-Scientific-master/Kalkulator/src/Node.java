import com.sun.istack.internal.Nullable;

/*
 * This class represent a node of the binary tree. Each node can have
 * a parent, a left child, and a right child. Each node can hold a value of any object type.
 *
 * @author Luka Kralj
 * @version 19 07 2020
 */
public class Node {
    /*ATRIBUT*/
    private Object value;
    private Node parent;
    private Node left;
    private Node right;

    /**
     * Create a new node.
     *
     * @param value Value that the node holds.
     * @param leftChild Left child of the node, if any.
     * @param rightChild Right child of the node if any.
     */
    /*MEMBUAT NODE BARU, DAN AKAN DI SET ANAK KANAN DAN KIRI NYA JIKA ADA, JIKA TIDAK ADA AKAN DI SET SEBAGAI NULL*/
    public Node(Object value, @Nullable Node leftChild, @Nullable Node rightChild) {
        this.value = value;
        parent = null;
        left = leftChild;
        right = rightChild;
        //SET PARENT DARI KEDUA NODE
        if (leftChild != null) {
            leftChild.setParent(this);
        }
        if (rightChild != null) {
            rightChild.setParent(this);
        }
    }

    /**GETTER METHOD**/
    
    /**
     *
     * @return Parent of the node.
     */
    public Node getParent() {
        return parent;
    }

    /**
     *
     * @return Left child of the node.
     */
    public Node getLeft() {
        return left;
    }

    /**
     *
     * @return Right child of the node.
     */
    public Node getRight() {
        return right;
    }

    /**SETTER METHOD**/
    
    /**
     *
     * @return Value that the node holds.
     */
    public Object element(){
        return value;
    }

    /**
     * Set new right child.
     *
     * @param v New child.
     */
    public void setRight(Node v) {
        right = v;
    }

    /**
     * Set new left child.
     *
     * @param v New child.
     */
    public void setLeft(Node v) {
        left = v;
    }

    /**
     * Set new parent.
     *
     * @param v New parent.
     */
    public void setParent(Node v) {
        parent = v;
    }
}
