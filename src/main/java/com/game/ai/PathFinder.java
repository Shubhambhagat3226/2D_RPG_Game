package com.game.ai;

import com.game.GamePanel;
import com.game.constants.CommonConstant;

import java.util.ArrayList;

public class PathFinder {
    final GamePanel gp;
    Node[][] nodes;
    Node startNode, goalNode, currentNode;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    int step            = 0;
    boolean goalReached = false;

    public PathFinder(GamePanel gp) {
        this.gp = gp;
        instantiateNode();
    }
    public void instantiateNode() {
        nodes = new Node[CommonConstant.MAX_WORLD_COL][CommonConstant.MAX_WORLD_ROW];

        int col = 0, row = 0;
        while (col < CommonConstant.MAX_WORLD_COL && row < CommonConstant.MAX_WORLD_ROW) {
            nodes[col][row] = new Node(col, row);

            col++;
            if (col == CommonConstant.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }
    }
    public void resetNodes() {

        int col = 0, row = 0;
        while (col < CommonConstant.MAX_WORLD_COL && row < CommonConstant.MAX_WORLD_ROW) {
            // RESET OPEN, CHECKED AND SOLID STATE
            nodes[col][row].open    = false;
            nodes[col][row].checked = false;
            nodes[col][row].solid   = false;

            col++;
            if (col == CommonConstant.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }
        // RESET OTHER SETTING
        openList.clear();
        pathList.clear();
        goalReached = false;
        step        = 0;
    }
    public void setNode(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();

        // SET START AND GOAL NODE
        startNode   = nodes[startCol][startRow];
        currentNode = startNode;
        goalNode    = nodes[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0, row = 0;
        while (col < CommonConstant.MAX_WORLD_COL && row < CommonConstant.MAX_WORLD_ROW) {

            // SET SOLID NODE
            // CHECK TILES
            int tileNum = gp.getTileM().getMap()[gp.getCurrentMap()][row][col];
            if (gp.getTileM().getTiles()[tileNum].isCollision()) {
                nodes[col][row].solid = true;
            }
            // CHECK INTERACTIVE TILE
            for (int i = 0; i < gp.getiTile().length; i++) {
                if (gp.getiTile()[gp.getCurrentMap()][i] != null && gp.getiTile()[gp.getCurrentMap()][i].isDestructible()) {
                    int itCol = gp.getiTile()[gp.getCurrentMap()][i].getWorldX()/CommonConstant.TILE_SIZE;
                    int itRow = gp.getiTile()[gp.getCurrentMap()][i].getWorldY()/CommonConstant.TILE_SIZE;
                    nodes[itCol][itRow].solid = true;
                }
            }
            // SET COST
            getCost(nodes[col][row]);

            col++;
            if (col == CommonConstant.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }

    }
    public void getCost(Node node) {
        // GET G-COST (DISTANCE FROM START NODE)
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost    = xDistance + yDistance;

        // GET H-COST (DISTANCE FROM GOAL NODE)
        xDistance  = Math.abs(node.col - goalNode.col);
        yDistance  = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // GET F-COST (DISTANCE FROM START NODE)
        node.fCost = node.gCost + node.hCost;

    }
    public boolean search() {

        while (!goalReached && step < 1000) {
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.checked = true;
            openList.remove(currentNode);

            // OPEN THE UP NODE
            if (row-1 >= 0) {
                openNode(nodes[col][row-1]);
            }
            // OPEN THE LEFT NODE
            if (col-1 >= 0) {
                openNode(nodes[col-1][row]);
            }
            // OPEN THE DOWN NODE
            if (row+1 < CommonConstant.MAX_WORLD_ROW) {
                openNode(nodes[col][row+1]);
            }
            // OPEN THE RIGHT NODE
            if (col+1 < CommonConstant.MAX_WORLD_COL) {
                openNode(nodes[col+1][row]);
            }

            // FIND THE BEST NODE
            int bestNodeIndex = 0;
            int bestNode_FCost = 999;

            for (int i = 0; i < openList.size(); i++) {

                // CHECKED IF THIS NODE'S F-COST IS BETTER
                if (openList.get(i).fCost < bestNode_FCost) {
                    bestNodeIndex  = i;
                    bestNode_FCost = openList.get(i).fCost;
                }
                // F-COST IS EQUAL, CHECK THE G-COST
                else if (openList.get(i).fCost == bestNode_FCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }
            // IF THERE IS NO NODE IN THE OPEN-LIST, END THE LOOP
            if (openList.isEmpty()) {
                break;
            }
            // AFTER THE LOOP, WE GET THE BEST NODE WHICH IS OUR NEXT STEP
            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackPath();
            }
            step++;
        }
        return goalReached;
    }
    public void openNode(Node node) {

        if (!node.open && !node.checked && !node.solid) {
            // IF NODE IS NOT OPENED YET, ADD IT TO OPEN LIST
            node.open   = true;
            node.parent = currentNode;
            openList.add(node);

        }
    }
    public void trackPath() {
        Node current = goalNode;
        while (current != startNode) {
            pathList.add(0, current);
            current = current.parent;
        }
    }
}
