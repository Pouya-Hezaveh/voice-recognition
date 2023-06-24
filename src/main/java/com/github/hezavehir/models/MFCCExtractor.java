package com.github.hezavehir.models;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.UnsupportedAudioFileException;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.mfcc.MFCC;
import javafx.util.Pair;

public class MFCCExtractor {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
        File file = new File("/home/pouya/Desktop/voice-recognition/aud/Persian/numbers/2(do).wav");
        // AudioDispatcher dispatcher = AudioDispatcherFactory.fromFile(file, 2048,
        // 1024);
        // int numCoefficients = 13;

        // MFCC mfcc = new MFCC(2048, 44100, numCoefficients, 20, 300, 22000);
        // dispatcher.addAudioProcessor(mfcc);
        // dispatcher.run();
        // float[] mfccValues = mfcc.getMFCC();
        // System.out.println(Arrays.toString(mfccValues));

        List<Pair<Float, float[]>> mfccList = new ArrayList<>(300);
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromFile(file, 2048, 512);
        MFCC mfcc = new MFCC(2048, 44100, 13, 20, 300, 22000);
        dispatcher.addAudioProcessor(mfcc);
        dispatcher.addAudioProcessor(new AudioProcessor() {

            @Override
            public void processingFinished() {
                for (int i = 0; i < mfccList.size(); i++) {
                    System.out.print("\n$$$" + mfccList.get(i).getKey() + ": ");
                    for (int j = 0; j < mfccList.get(i).getValue().length; j++) {
                        System.out.print(mfccList.get(i).getValue()[j] + " ");
                    }
                }
            }

            @Override
            public boolean process(AudioEvent audioEvent) {
                mfccList.add(new Pair(audioEvent.getTimeStamp(), mfcc.getMFCC()));
                return true;
            }
        });

        dispatcher.run();
    }

}
