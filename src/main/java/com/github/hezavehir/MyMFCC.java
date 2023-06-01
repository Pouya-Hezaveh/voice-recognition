package com.github.hezavehir;

import org.jtransforms.dct.FloatDCT_1D;
import org.jtransforms.fft.FloatFFT_1D;

/*
 * author: @lichard49
 * repository: lichard49/MFCCTest.java
 * url: https://gist.github.com/lichard49/69ab2a2ddd3c11394974c404ca102a55
 *
 * Based on the MFCC implementation from pyAudioAnalysis (https://github.com/tyiannak/pyAudioAnalysis)
 */

public class MyMFCC {

    // instance variables to be precomputed before extracting MFCC's
    private float[][] fbank = null;
    private float[] freqs = null;

    public MyMFCC() {

    }

    private void initFilterBanks(float frequency, float nFFT) {
        // filter bank params
        float lowfreq = 133.33f;
        float linsc = 200/3.f;
        float logsc = 1.0711703f;
        int numLinFiltTotal = 13;
        int numLogFilt = 27;

        // total number of filters
        int nFiltTotal = numLinFiltTotal + numLogFilt;

        // compute frequency points of the triangle
        freqs = new float[nFiltTotal+2];
        for (int i = 0; i < numLinFiltTotal; i++) {
            freqs[i] = lowfreq + i * linsc;
        }
        for (int i = numLinFiltTotal; i < freqs.length; i++) {
            freqs[i] = freqs[numLinFiltTotal - 1] * (float)Math.pow(logsc, (i - numLinFiltTotal + 1));
        }
        float[] heights = new float[freqs.length-2];
        for (int i = 0; i < heights.length; i++) {
            heights[i] = 2.0f/(freqs[i+2] - freqs[i]);
        }

        // compute filter bank coefficients (in FFT domain)
        fbank = new float[nFiltTotal][(int)nFFT];
        float[] nfreqs = new float[(int)nFFT];
        for (int i = 0; i < nFFT; i++) {
            nfreqs[i] = i / (1.0f * nFFT) * frequency;
        }

        for (int i = 0; i < nFiltTotal; i++) {
            float lowTrFreq = freqs[i];
            float cenTrFreq = freqs[i+1];
            float highTrFreq = freqs[i+2];

            int lid_start = (int)(Math.floor(lowTrFreq * nFFT / frequency) + 1);
            int lid_end = (int)(Math.ceil(cenTrFreq * nFFT / frequency) + 1);
            float lslope = heights[i] / (cenTrFreq - lowTrFreq);

            int rid_start = (int)(Math.floor(cenTrFreq * nFFT / frequency) + 1);
            int rid_end = (int)(Math.ceil(highTrFreq * nFFT / frequency) + 1);
            float rslope = heights[i] / (highTrFreq - cenTrFreq);

            for (int j = lid_start; j < lid_end; j++) {
                fbank[i][j] = lslope * (nfreqs[j] - lowTrFreq);
            }
            for (int j = rid_start; j < rid_end; j++) {
                fbank[i][j] = rslope * (highTrFreq - nfreqs[j]);
            }
        }
    }

    public float[] getMFCC(float[] x, float Fs, int nceps) {
        if (fbank == null) {
            initFilterBanks(Fs, x.length);
        }

        // preprocess signal
        float[] X = preprocessSignal(x, Fs);

        // dot product of signal and banks
        float[] mspec = new float[fbank.length];
        for (int j = 0; j < mspec.length; j++) {
            for (int i = 0; i < X.length; i++) {
                mspec[j] += fbank[j][i] * X[i];
            }
            mspec[j] = (float) Math.log10(mspec[j] + 0.0000000001);
        }

        // find cepstrum
        FloatDCT_1D dct = new FloatDCT_1D(mspec.length);
        dct.forward(mspec, true);
        float[] ceps = new float[nceps];
        System.arraycopy(mspec, 0, ceps, 0, ceps.length);

        return ceps;
    }

    private float[] preprocessSignal(float[] x, float sampleRate) {
        /*
        // !!! not needed because normalization is already taken care of by audio processor
        // signal normalization
        float DC = 0;
        float MAX = 0;
        for (int i = 0; i < x.length; i++) {
            x[i] = x[i] / (float)Math.pow(2, 15);
            DC += x[i];
            if (x[i] > MAX) {
                MAX = x[i];
            }
        }

        DC /= x.length;
        for (int i = 0; i < x.length; i++) {
            x[i] = (x[i] - DC) / (MAX + 0000000001);
        }
        */

        // audio parameters
        int N = x.length;
        float Fs = sampleRate;
        float nFFT = N/2;

        // get magnitude FFT
        float[] X = new float[N*2];
        for (int i = 0; i < N; i++) {
            X[i] = x[i];
        }
        FloatFFT_1D fft = new FloatFFT_1D(N);
        fft.realForwardFull(X);
        float[] X_mag = new float[(int)nFFT];
        for (int i = 0; i < nFFT; i++) {
            X_mag[i] = (float) Math.sqrt(Math.pow(X[i*2], 2) + Math.pow(X[i*2+1], 2)) / nFFT;
        }

        this.initFilterBanks(Fs, nFFT);

        return X_mag;
    }
}
