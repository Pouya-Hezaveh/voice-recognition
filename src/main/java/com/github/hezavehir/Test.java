package com.github.hezavehir;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.github.hezavehir.models.UtilityMethods;

public class Test {

    private Test() {
        throw new IllegalStateException("Utility class");
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
        File[] trainingFiles = UtilityMethods.getFilesOfFolder(new File("./aud/samples"));
        for (int i = 0; i < trainingFiles.length; i++) {
            System.out.print(trainingFiles[i].getName()+": ");
            System.out.println(UtilityMethods.compareMFCC(trainingFiles[i], new File("./aud/test/example5(panj).wav")));
        }
    }

}
