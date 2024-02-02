package opgave1;

import java.util.List;

public class mergeThread extends Thread {
    private List<Integer> list;
    private int l, h;
    public mergeThread(List list, int l, int h) {
        this.list = list;
        this.l = l;
        this.h = h;
    }
    public void run() {
        FletteSortering.mergesort(list, l, h);
    }
}
