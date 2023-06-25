package com.github.hezavehir;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.github.hezavehir.models.Algorithms;

public class Test {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
        test();
    }

    private Test() {
        throw new IllegalStateException("Utility class");
    }

    public static void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getPath());
            }
        }
    }

    public static void test() throws UnsupportedAudioFileException, IOException {

        float[] a1={1,2,3};
        float[] a2 = {1,2,3};
        System.out.println( Algorithms.calculateDTW(a1,a2) );

        // final File folder = new File("./aud/Persian/numbers");
        // listFilesForFolder(folder);

        // File file;
        // file = new File("./aud/Persian/numbers/3(se).wav");
        // MFCCExtractor aud1ext = new MFCCExtractor(file);

        // file = new File("./aud/Persian/numbers/2(do).wav");

        // for (int i = 0; i < aud1ext.getMFCCMatrix().length; i++) {
        //     for (int j = 0; j < aud1ext.getMFCCMatrix()[0].length; j++) {
        //         System.out.print(aud1ext.getMFCCMatrix()[i][j]+" ");
        //     }
        //     System.out.println();
        // }
    }
}
