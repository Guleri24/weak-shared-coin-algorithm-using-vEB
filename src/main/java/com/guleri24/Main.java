package com.guleri24;

import java.util.Scanner;

import static java.lang.Math.*;


public class Main {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int n = in.nextInt(); // universe size
        in.close();
        System.out.println(weakSharedCoinAlgo(n));
    }

    private static int weakSharedCoinAlgo(int n) {
        int K = (int) (pow(n, 2) * log(n));
        int T = (int) (4 * n * log(n));
        int sqrt = (int) sqrt(n);
       // int lglgn = (int) log(log(n));

        int count = 0;
        int var = 0;
        int total = 0;
        vEBTree<Register> V = new vEBTree<>(n); // overall structure created


        for (int k = 1; k <= n; k++) {
            int w_k = 2 * ((k - 1) % T);
            int vote = w_k;
            count++;
            var += pow(w_k, 2);
            total += vote;

            //Register R = new Register(count, var, total);
            for (int j = 1; j <= sqrt; j++) {
                /*
                TODO: Check : Not Sure is it required or not
                if ((k / sqrt) == 0) {  // move to next cluster as this cluster now contains sq root of n Registers
                    break;
                }*/

                /* update the structure where V is the overall structure */
                System.out.println(j + " " + V.put(j, new Register(count, var, total)));
            }
            if (k % n == 0) {
                System.out.println(V.values());
                if (var >= K)
                    return total;
            }
        }
        return 0;
    }

}
