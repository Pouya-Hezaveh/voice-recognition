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
            result += Algorithms.calculateDTW(firstArray, secondArray);
        }

        return result;
    }
}
