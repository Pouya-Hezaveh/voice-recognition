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

// @author: Pouya Hezaveh

public class MFCCExtractor {
    private int numberOfEvents = 0;
    private float[][] mfccMatrix;
    private float[] timeVector;
    int audioBufferSize = 2048;
    int bufferOverlap = 1024;
    int samplesPerFrame = 2048;
    int sampleRate = 44100;
    int amountOfCepstrumCoef = 1;
    int amountOfMelFilters = 20;
    int lowerFilterFreq = 150;
    int upperFilterFreq = 20000;

    public MFCCExtractor(File file) throws UnsupportedAudioFileException, IOException {
        List<Float> timeList = new ArrayList<>(300);
        List<float[]> mfccList = new ArrayList<>(300);
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromFile(file, audioBufferSize, bufferOverlap);
        MFCC mfcc = new MFCC(samplesPerFrame, sampleRate, amountOfCepstrumCoef, amountOfMelFilters, lowerFilterFreq,
                upperFilterFreq);
        dispatcher.addAudioProcessor(mfcc);

        dispatcher.addAudioProcessor(new AudioProcessor() {

            @Override
            public void processingFinished() {
                timeVector = new float[numberOfEvents];
                mfccMatrix = new float[numberOfEvents][amountOfCepstrumCoef];
                for (int i = 0; i < numberOfEvents; i++) {
                    timeVector[i] = timeList.get(i);
                    mfccMatrix[i] = mfccList.get(i);
                }
            }

            @Override
            public boolean process(AudioEvent audioEvent) {
                timeList.add((float) audioEvent.getTimeStamp());
                mfccList.add(mfcc.getMFCC());
                numberOfEvents++;
                return true;
            }
        });

        dispatcher.run();
    }

    public int getLength() {
        return numberOfEvents;
    }

    public float[] getTimeVector() {
        return timeVector;
    }

    public float[][] getMFCCMatrix() {
        return this.mfccMatrix;
    }
}
