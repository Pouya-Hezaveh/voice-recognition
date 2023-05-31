package com.github.hezavehir;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.TarsosDSPAudioInputStream;
import be.tarsos.dsp.io.UniversalAudioInputStream;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.mfcc.MFCC;

public class MFCCExtractor {

    public static double[][] computeMFCC(String audioFilePath) {
        try {
            // Open audio file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(audioFilePath));
            AudioFormat audioFormat = audioInputStream.getFormat();

            // Convert audio format to desired format for TarsosDSP
            AudioFormat tarsosDSPAudioFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    audioFormat.getSampleRate(),
                    16,
                    audioFormat.getChannels(),
                    audioFormat.getChannels() * 2,
                    audioFormat.getSampleRate(),
                    false);

            // Read audio data into a byte array
            byte[] audioData = new byte[(int) (audioInputStream.getFrameLength() * audioFormat.getFrameSize())];
            audioInputStream.read(audioData);

            // Create a ByteArrayInputStream from the audio data
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(audioData);

            // Create a TarsosDSP audio input stream from the ByteArrayInputStream
            TarsosDSPAudioInputStream tarsosDSPAudioInputStream = new UniversalAudioInputStream(byteArrayInputStream, audioFormat, AudioSystem.NOT_SPECIFIED);

            // Create an AudioDispatcher to read the audio stream
            AudioDispatcher audioDispatcher = AudioDispatcherFactory.fromPipe(tarsosDSPAudioInputStream, 1024, 0);

            // Create an MFCC instance
            MFCC mfcc = new MFCC(tarsosDSPAudioFormat.getSampleRate(), 1024, 13, 40, 300, 3000);

            // Create an array to store the MFCC coefficients
            double[][] mfccCoefficients = new double[0][];

            // Process audio frames and compute MFCC
            audioDispatcher.addAudioProcessor(mfcc);
            audioDispatcher.run();

            // Get the computed MFCC coefficients
            mfccCoefficients = mfcc.getMFCC();

            return mfccCoefficients;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        // Example usage

        String audioFile = "path/to/audio/file.wav";

        double[][] mfccCoefficients = computeMFCC(audioFile);

        if (mfccCoefficients != null) {
            // Print the MFCC coefficients
            for (double[] frame : mfccCoefficients) {
                System.out.println(Arrays.toString(frame));
            }
        }
    }
}
