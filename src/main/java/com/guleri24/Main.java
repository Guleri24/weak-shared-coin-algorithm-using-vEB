package com.guleri24;

import java.util.Scanner;

import static java.lang.Math.*;

public class Main {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int n = in.nextInt(); // universe size
        in.close();
        weakSharedCoinAlgo(n);
    }

    private static int weakSharedCoinAlgo(int n) {
        int K = (int) (pow(n, 2) * log(n));
        int T = (int) (4 * n * log(n));

        int count = 0;
        int var = 0;
        int total = 0;
        vEB<MaxRegister> V = new vEB<>(n);


        for (int k = 1; k <= n; k++) {
            int w_k = 2 * ((k - 1) % T);
            int vote = w_k;
            count++;
            var += pow(w_k, 2);
            total += vote;

            MaxRegister R = new MaxRegister(count, var, total);

            for (int j = 1; j < log(log(n)); j++) {
                if ((k % sqrt(n)) != 0)
                    break;
                /* update the summary array where V is the overall structure */
                V.insert(j, R);
            }
            if (k % n == 0) {
                System.out.println(V.rootMaxRegister());
                if (var >= K)
                    return total;
            }
        }
        return 0;
    }

}
