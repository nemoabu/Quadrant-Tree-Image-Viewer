public class QTreeNode {
    private int x, y; 
    private int size; 
    private int color; 
    private QTreeNode parent; 
    private QTreeNode[] children; 
    
    // Constructor
    public QTreeNode() {
        
        this.x = 0;
        this.y = 0;
        this.size = 0;
        this.color = 0;
        this.parent = null;
        this.children = new QTreeNode[4];
    }
    
    // Constructor 2
    public QTreeNode(QTreeNode[] theChildren, int xcoord, int ycoord, int theSize, int theColor) {
        this.children = theChildren;
        this.x = xcoord;
        this.y = ycoord;
        this.size = theSize;
        this.color = theColor;
    }
    
    // Check point contained inside qadrant
    public boolean contains(int xcoord, int ycoord) {
        if (xcoord < this.x || xcoord > this.x + this.size - 1) {
            return false;
        }
        
        if (ycoord < this.y || ycoord > this.y + this.size - 1) {
            return false;
        }
        
        return true;
    }
    
    
    // Getter methods
    public int getx() {
        return this.x;
    }
    
    public int gety() {
        return this.y;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public QTreeNode getParent() {
        return this.parent;
    }
    
    // Method to get child node at given index
    public QTreeNode getChild(int index) throws QTreeException {
        if (this.children == null || index < 0 || index > 3) {
            throw new QTreeException("Invalid child index or children array is null");
        }
        return this.children[index];
    }
    
    // Setter methods
    public void setx(int newx) {
        this.x = newx;
    }
    
    public void sety(int newy) {
        this.y = newy;
    }
    
    public void setSize(int newSize) {
        this.size = newSize;
    }
    
    public void setColor(int newColor) {
        this.color = newColor;
    }
    
    public void setParent(QTreeNode newParent) {
        this.parent = newParent;
    }
    
    // set child note to index
    public void setChild(QTreeNode newChild, int index) throws QTreeException {
        if (this.children == null || index < 0 || index > 3) {
            throw new QTreeException("Invalid index or array null");
        }
        this.children[index] = newChild;
    }
    
    // Check if node is leaf
    public boolean isLeaf() {
        if (this.children == null) {
            return true;
        }
        for (QTreeNode child : this.children) {
            if (child != null) {
                return false;
            }
        }
        return true;
    }
}
