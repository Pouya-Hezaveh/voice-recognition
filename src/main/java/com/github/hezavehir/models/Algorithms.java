package com.github.hezavehir.models;

public class Algorithms {
    float calculateDTW(float[] firstArray, float[] secondArray) {
        int n = firstArray.length;
        int m = secondArray.length;
        float[][] dp = new float[n + 1][m + 1];

        for (int i = 0; i < n; i++) {
            dp[i][0] = 0;
        }

        for (int i = 0; i < m; i++) {
            dp[0][m] = 0;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // avery unit gap has more important than the previous one so,
                // to make the gap exponential: the gap = (arr1 - arr2)^2
                float gap = firstArray[i] - secondArray[j];
                gap = gap * gap;

                dp[i][j] = gap + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[j][i - 1]));
            }
        }

        return dp[n][m];
    }
}
