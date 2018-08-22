package kestone.com.kestone.MODEL.Manager;

import java.io.Serializable;

/**
 * Created by karan on 7/22/2017.
 */

public class CompareLeftRight implements Serializable {
    int left;
    int right;

    int hallLeft;
    int hallRight;

    public CompareLeftRight() {
    }

    public CompareLeftRight(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }


    public int getHallLeft() {
        return hallLeft;
    }

    public void setHallLeft(int hallLeft) {
        this.hallLeft = hallLeft;
    }

    public int getHallRight() {
        return hallRight;
    }

    public void setHallRight(int hallRight) {
        this.hallRight = hallRight;
    }

    public CompareLeftRight(int left, int right, int hallLeft, int hallRight) {
        this.left = left;
        this.right = right;
        this.hallLeft = hallLeft;
        this.hallRight = hallRight;
    }
}
