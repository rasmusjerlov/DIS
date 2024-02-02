package opgave1;

import java.util.ArrayList;
import java.util.List;

public class FletteSortering extends Thread{

        // den liste der skal sorteres skal vaere global for de rekursive kald
//den rekursive metode der implementere del-loes og kombiner skabelonen

        public static void mergeSortFlet(List<Integer> list, int l, int h) {
            int m = (l + h) / 2;
            mergeThread mt1 = new mergeThread(list, l, m);
            mergeThread mt2 = new mergeThread(list, m + 1, h);

            mt1.start();
            mt2.start();
            try {
                mt1.join();
                mt2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            merge(list, l, m, h);
        }
        public static void mergesort(List<Integer> list, int l, int h) {
            if (l < h) {
                int m = (l + h) / 2;
                mergesort(list, l, m);
                mergesort(list, m + 1, h);
                merge(list, l, m, h);
            }
        }
        private static void merge(List<Integer> list, int low, int middle, int high) {
            List<Integer> temp = new ArrayList<Integer>();
            int i = low;
            int j = middle + 1;
            while (i <= middle && j <= high) {
                if (list.get(i).compareTo(list.get(j)) <= 0) {
                    temp.add(list.get(i));
                    i++;
                } else {
                    temp.add(list.get(j));
                    j++;
                }
            }
            while (i <= middle) {
                temp.add(list.get(i));
                i++;
            }
            while (j <= high) {
                temp.add(list.get(j));
                j++;
            }
            for (int k = 0; k < temp.size(); k++) {
                list.set(low + k, temp.get(k));
            }
        }
}
