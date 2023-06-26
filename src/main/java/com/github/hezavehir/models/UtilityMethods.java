package com.github.hezavehir.models;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.UnsupportedAudioFileException;

public class UtilityMethods {
    private UtilityMethods() {
        throw new IllegalStateException("Utility class");
    }

    public static File[] getFilesOfFolder(final File folder) {
        List<File> temp = new ArrayList<>(10);
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                File[] filesInThere = getFilesOfFolder(fileEntry);
                temp.addAll(Arrays.asList(filesInThere));
            } else {
                temp.add(fileEntry);
            }
        }
        return temp.toArray(new File[0]);
    }

    public static float compareMFCC(File firstAud, File secondAud)
            throws UnsupportedAudioFileException, IOException {
        MFCCExtractor firstExt = new MFCCExtractor(firstAud);
        MFCCExtractor secondExt = new MFCCExtractor(secondAud);

        float[][] firstMFCC = firstExt.getMFCCMatrix();
        float[][] secondMFCC = secondExt.getMFCCMatrix();

        float result = 0;
        for (int k = 0; k < firstMFCC[0].length; k++) {
            float[] firstArray = new float[firstExt.getLength()];
            float[] secondArray = new float[secondExt.getLength()];
            for (int i = 0; i < firstArray.length; i++) {
                firstArray[i] = firstMFCC[i][k];
            }
            for (int i = 0; i < secondArray.length; i++) {
                secondArray[i] = secondMFCC[i][k];
            }
            // for (int i = 0; i < firstArray.length-1; i++) {
            // firstArray[i] =
            // (firstMFCC[i+1][k]-firstMFCC[i][k])/(firstExt.getTimeVector()[i+1]-firstExt.getTimeVector()[i]);
            // }
            // for (int i = 0; i < secondArray.length-1; i++) {
            // secondArray[i] =
            // (secondMFCC[i+1][k]-secondMFCC[i][k])/(secondExt.getTimeVector()[i+1]-secondExt.getTimeVector()[i]);
            // }

            // fitArray(firstArray);
            // fitArray(secondArray);

            result += Algorithms.calculateDTW(firstArray, secondArray);
        }

        return result;
    }
    
    static void fitArray(float[] arr) {
        // float average = 0;
        // for (int i = 0; i < arr.length; i++) {
        //         average += arr[i];
        // }
        // average/=arr.length;

        // for (int i = 0; i < arr.length; i++) {
        //     arr[i] /= average;
        // }
    }

}
