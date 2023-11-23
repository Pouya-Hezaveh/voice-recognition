package com.github.hezavehir.models;

public class Algorithms {
    private Algorithms() {
        throw new IllegalStateException("Utility class");
    }

    public static float calculateDTW(float[] firstArray, float[] secondArray) {
        int n = firstArray.length;
        int m = secondArray.length;
        float[][] dp = new float[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            dp[i][0] = 0;
        }

        for (int i = 0; i <= m; i++) {
            dp[0][m] = 0;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // avery unit gap has more important than the previous one so,
                // to make the gap impacts exponential: the gap = (arr1 - arr2)^2
                float gap = firstArray[i - 1] - secondArray[j - 1];
                gap = gap * gap;
                dp[i][j] = gap + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));

                // System.out.println("==========");
                // System.out.println("#" + gap);
                // System.out.println(i + " " + j);
                // System.out.println(dp[i - 1][j - 1]);
                // System.out.println(dp[i - 1][j]);
                // System.out.println(dp[i][j - 1]);
                // System.out.println(dp[i][j]);
            }
        }

        return dp[n][m];
    }
}
