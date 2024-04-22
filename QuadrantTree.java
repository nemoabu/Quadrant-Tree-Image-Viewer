public class QuadrantTree {
    // The root of the quadrant tree
    private QTreeNode root; 
    
    // Constructor
    public QuadrantTree(int[][] thePixels) {
        int size = thePixels.length;
        this.root = buildTree(thePixels, 0, 0, size);
    }
    
    // Method to build the quadrant tree recursively
    private QTreeNode buildTree(int[][] pixels, int x, int y, int size) {
        QTreeNode node = new QTreeNode();
        node.setx(x);
        node.sety(y);
        node.setSize(size);
        node.setColor(Gui.averageColor(pixels, x, y, size));
        
        if (size == 1) { 
            // Leaf node
            return node;
        } else {
            // Calculate coordinates for the child nodes
            int topLeftX = x;
            int topLeftY = y;
            int topRightX = x + size / 2;
            int topRightY = y;
            int bottomLeftX = x;
            int bottomLeftY = y + size / 2;
            int bottomRightX = x + size / 2;
            int bottomRightY = y + size / 2;
        
            // Create children nodes
            // R1
            node.setChild(buildTree(pixels, topLeftX, topLeftY, size / 2), 0); 
            // R2
            node.setChild(buildTree(pixels, topRightX, topRightY, size / 2), 1); 
            // R3
            node.setChild(buildTree(pixels, bottomLeftX, bottomLeftY, size / 2), 2); 
            // R4
            node.setChild(buildTree(pixels, bottomRightX, bottomRightY, size / 2), 3); 
        
            return node;
        }
        
        
        
    }
     
    // Method to get the root of the quadrant tree
    public QTreeNode getRoot() {
        return this.root;
    }

    // Method to get all nodes at a specified level
    public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel) {
        ListNode<QTreeNode> list = new ListNode<>(null);
        //if the list is emppty return null;
        if (r == null) return list;
        //Otherwise call the helper method
        getPixelsHelper(r, theLevel, 0, list);
        //after helper methods finish return the list
        return list;
    }

    // Recursive helper method for getPixels
    private void getPixelsHelper(QTreeNode r, int targetLevel, int currentLevel, ListNode<QTreeNode> nodeList) {
        // If the current node is at the target level or it is a leaf node
        if (currentLevel == targetLevel || r.isLeaf()) {
            // Append the node to the result list
            ListNode<QTreeNode> newNode = new ListNode<>(r);
            if (nodeList.getData() == null) {
                nodeList.setData(r);
            } else {
                ListNode<QTreeNode> lastNode = findLastNode(nodeList);
                lastNode.setNext(newNode);
            }
            return;
        }
        
        // Recursively call the method for each child node(sub quadrants)
        for (int quadrantIndex = 0; quadrantIndex < 4; quadrantIndex++) {
            QTreeNode childNode = r.getChild(quadrantIndex);
            getPixelsHelper(childNode, targetLevel, currentLevel + 1, nodeList);
        }
    }

    // Method to find the last node 
    private ListNode<QTreeNode> findLastNode(ListNode<QTreeNode> node) {
        ListNode<QTreeNode> current = node;
        //use while loop to itereate through tree
        while (current.getNext() != null) {
            current = current.getNext();
        }
        return current;
    }
    
    // Method to find a node at a specified level and coordinates
    public QTreeNode findNode(QTreeNode node, int targetLevel, int targetX, int targetY) {
        //if node is null return itself
        if (node == null) {
            return null;
        }
        //otherwise call the helper method
        else {
            return findNodeHelper(node, targetLevel, targetX, targetY);
        }
    }

    
    // Method for findNode
    private QTreeNode findNodeHelper(QTreeNode node, int targetLevel, int targetX, int targetY) {
        //if the node does not contain the target cords return null
        if (!node.contains(targetX, targetY)) {
            return null;
        }
        if (targetLevel == 0 || node.isLeaf()) {
            return node;
        }
    
        // Search in the child nodes
        for (int quadrantIndex = 0; quadrantIndex < 4; quadrantIndex++) {
            QTreeNode result = findNodeHelper(node.getChild(quadrantIndex), targetLevel - 1, targetX, targetY);
            if (result != null) {
                return result;
            }
        }
    
        return null;
    }



    public Duple findMatching(QTreeNode r, int theColor, int theLevel) {
        // Get all nodes at the specified level
        ListNode<QTreeNode> nodesAtSpecificLevel = getPixels(r, theLevel);
        // Filter out the nodes with colors similar to theColor
        ListNode<QTreeNode> filteredNodes = sameColor(nodesAtSpecificLevel, theColor);
        // Calculate the count of filtered nodes
        int count =0;
        count= countNodes(filteredNodes);
        // Return the filtered nodes and their count
        return new Duple(filteredNodes, count);
    }

    // Method to filter out nodes with colors 
    private ListNode<QTreeNode> sameColor(ListNode<QTreeNode> nodesAtSpecificLevel, int theColor) {
        ListNode<QTreeNode> current = nodesAtSpecificLevel;
        ListNode<QTreeNode> filteredList = new ListNode<>(null); 
        // Iterate through the nodes and filter out
        while (current != null) {
            if (Gui.similarColor(current.getData().getColor(), theColor)) {
                // Append the node to the filtered list
                filteredList = appendFilteredNode(filteredList, current.getData());
            }
            current = current.getNext();
        }
        // Return the filtered list 
        return filteredList.getNext();
    }

    // Method to append the filtered node to a linked list
    private ListNode<QTreeNode> appendFilteredNode(ListNode<QTreeNode> list, QTreeNode node) {
        ListNode<QTreeNode> current = list;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        current.setNext(new ListNode<>(node));
        return list;
    }

    // Counting number of nodes using a while loop
    private int countNodes(ListNode<QTreeNode> list) {
        int count = 0;
        ListNode<QTreeNode> currentNode = list;
        while (currentNode != null) {
            count++;
            currentNode = currentNode.getNext();
        }
        return count;
    }

}
    
    





