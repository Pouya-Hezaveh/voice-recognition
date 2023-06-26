package com.github.hezavehir;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.github.hezavehir.models.UtilityMethods;

public class Test {
    public static void main(String[] args) throws InterruptedException, UnsupportedAudioFileException, IOException {
        testSamples();
    }

    public static void testSamples() throws UnsupportedAudioFileException, IOException {

        File[] trainingFiles = UtilityMethods.getFilesOfFolder(new File("./aud/samples"));

        File[] testingFiles = UtilityMethods.getFilesOfFolder(new File("./aud/test"));

        for (int t = 0; t < testingFiles.length; t++) {
            System.out.println("----------------------------\n");
            System.out.println("#" + testingFiles[t].getName());
            System.out.println();
            for (int i = 0; i < trainingFiles.length; i++) {
                System.out.print(trainingFiles[i].getName() + ": ");
                System.out.println(UtilityMethods.compareMFCC(trainingFiles[i], testingFiles[t]));
            }
        }
    }
}

class JavaSoundRecorder {
    // record duration, in milliseconds
    static final long RECORD_TIME = 2000; // 1 minute

    // path of the wav file
    File wavFile = new File("./aud/records/recorded-voice.wav");

    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    // the line from which audio data is captured
    TargetDataLine line;

    /**
     * Defines an audio format
     */
    AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }

    /**
     * Captures the sound and record into a WAV file
     */
    void start() {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start(); // start capturing

            System.out.println("Start capturing...");

            AudioInputStream ais = new AudioInputStream(line);

            System.out.println("Start recording...");

            // start recording
            AudioSystem.write(ais, fileType, wavFile);

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Closes the target data line to finish capturing and recording
     */
    void finish() {
        line.stop();
        line.close();
        System.out.println("Finished");
    }

    /**
     * Entry to run the program
     */
    static void recordSound() {
        final JavaSoundRecorder recorder = new JavaSoundRecorder();

        // creates a new thread that waits for a specified
        // of time before stopping
        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(RECORD_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                recorder.finish();
            }
        });

        stopper.start();

        // start recording
        recorder.start();
    }
}