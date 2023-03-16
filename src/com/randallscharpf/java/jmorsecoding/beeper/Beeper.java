/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.randallscharpf.java.jmorsecoding.beeper;

import com.randallscharpf.java.jmorsecoding.base.standards.Delayer;
import com.randallscharpf.java.jmorsecoding.base.standards.OnOff;
import java.io.Closeable;
import java.time.Duration;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author Randall
 */
public class Beeper implements OnOff, Delayer, Closeable {
    
    private final IntSupplier pitch;
    private final IntSupplier volume;
    private final Supplier<WaveType> waveform;

    public Beeper(IntSupplier pitch, Supplier<WaveType> waveform) throws LineUnavailableException {
        this(pitch, () -> 100, waveform);
    }
    
    public Beeper(Supplier<WaveType> waveform) throws LineUnavailableException {
        this(() -> 440, () -> 100, waveform);
    }
    
    public Beeper(IntSupplier pitch) throws LineUnavailableException {
        this(pitch, () -> 100, () -> WaveType.TRIANGLE);
    }
    
    public Beeper(IntSupplier pitch, IntSupplier volume, Supplier<WaveType> waveform) throws LineUnavailableException {
        line = AudioSystem.getSourceDataLine(format);
        this.pitch = pitch;
        this.volume = volume;
        this.waveform = waveform;
    }
    
    public static enum WaveType {
        SINE,
        TRIANGLE,
        SAWTOOTH,
        SQUARE;
        public static String[] names() {
            WaveType[] w = WaveType.values();
            String[] out = new String[w.length];
            for (int i = 0; i < w.length; i++) {
                out[i] = w[i].toString();
            }
            return out;
        }
    }
    
    private static double calcSample(WaveType waveType, double p) {
        if (waveType == null) return 0;
        switch (waveType) {
            case SINE:
                return Math.sin(p);
            case TRIANGLE:
                return 2/Math.PI*Math.asin(Math.sin(p));
            case SAWTOOTH:
                return (p % (2*Math.PI))/Math.PI-1;
            case SQUARE:
                return Math.signum(Math.sin(p));
            default:
                throw new AssertionError();
        }
    }
    
    private final SourceDataLine line;
    private final AudioFormat format = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED,
            44100,
            16,
            1,
            2,
            44100,
            true
    );
    private Thread audioManager;
    private volatile WaveType playingWaveType = null;
    private volatile boolean audioManagerMayRun;
    
    public void open() throws LineUnavailableException {
        line.open(format,2200); // 25ms of buffer
        line.start();
        audioManagerMayRun = true;
        audioManager = new Thread(() -> {
            long framesWritten = 0;
            double sineInput = 0;
            WaveType lastWaveType = null;
            WaveType storedWaveType = null;
            long transitionFrame = 0;
            while(audioManagerMayRun) {
                int freq = pitch.getAsInt();
                double fpc = 44100/freq;
                if (storedWaveType != playingWaveType) {
                    transitionFrame = framesWritten;
                    lastWaveType = storedWaveType;
                    storedWaveType = playingWaveType;
                }
                byte[] moreframes = new byte[440]; // 5ms of data
                for (int i = 0; i < moreframes.length/2; i++) {
                    double sample = calcSample(storedWaveType, sineInput);
                    double sampleOld = calcSample(lastWaveType, sineInput);
                    double x = (framesWritten+i-transitionFrame)/44100.0;
                    x *= freq;
                    double factor = Math.exp(-2*x*x);
                    short v = (short) (
                            Short.MAX_VALUE * volume.getAsInt()/100.0 *
                            (sampleOld * factor + sample *(1 - factor))
                    );
                    moreframes[2*i] = (byte) (v >> 8);
                    moreframes[2*i+1] = (byte) v;
                    sineInput += 2*Math.PI / fpc;
                }
                framesWritten += line.write(moreframes, 0, moreframes.length)/2;
            }
        });
        audioManager.start();
    }

    @Override
    public void setActive(boolean active) {
        playingWaveType = active ? waveform.get() : null;
    }

    @Override
    public void wait(Duration time) throws Exception {
        Thread.sleep(time.toMillis(), time.getNano() % 1_000_000);
    }
    
    @Override
    public void close() {
        audioManagerMayRun = false;
        line.stop();
        line.close();
    }
}
