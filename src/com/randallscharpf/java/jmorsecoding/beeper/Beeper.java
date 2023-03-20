/*
 * The MIT License
 *
 * Copyright (c) 2023 Randall Scharpf
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.randallscharpf.java.jmorsecoding.beeper;

import com.randallscharpf.java.jmorsecoding.base.playerinterfaces.Delayer;
import com.randallscharpf.java.jmorsecoding.base.playerinterfaces.OnOff;
import com.randallscharpf.java.jmorsecoding.base.playerinterfaces.Openable;
import java.time.Duration;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * The state setter and delayer for a single Morse Code physical layer. The "on"
 * state represents that noise-making is occurring, and the "off" state represents
 * silence. The delayer causes the audio output to continue producing the sound (or
 * lack thereof) corresponding to the most recently set state for the specified
 * amount of time.
 * @version 1.0
 * @since 1.0
 */
public class Beeper implements OnOff, Delayer, Openable {
    
    private final DoubleSupplier pitch;
    private final DoubleSupplier volume;
    private final Supplier<WaveType> waveform;

    /**
     * Creates a beeper at full volume that uses the pitch A4 (440Hz). The tone of
     * the waveform can be set and adjusted in real-time as each beep is queued.
     * @version 1.0
     * @since 1.0
     * @param waveform the shape of the wave, which determines its tone
     * @throws LineUnavailableException if the audio device cannot be opened
     */
    public Beeper(Supplier<WaveType> waveform) throws LineUnavailableException {
        this(() -> 440, waveform);
    }
    
    /**
     * Creates a beeper at full volume. The pitch of the waveform can be set and
     * adjusted in real-time as audio is queued, and the tone of the waveform can
     * be set and adjusted in real-time as each beep is queued.
     * @version 1.0
     * @since 1.0
     * @param pitch the frequency of the wave, in Hz
     * @param waveform the shape of the wave, which determines its tone
     * @throws LineUnavailableException if the audio device cannot be opened
     */
    public Beeper(DoubleSupplier pitch, Supplier<WaveType> waveform) throws LineUnavailableException {
        this(pitch, () -> 100, waveform);
    }
    
    /**
     * Creates a beeper at full volume that uses a triangle wave. The pitch of the
     * waveform can be set and adjusted in real-time as audio is queued.
     * @version 1.0
     * @since 1.0
     * @param pitch the frequency of the wave, in Hz
     * @throws LineUnavailableException if the audio device cannot be opened
     */
    public Beeper(DoubleSupplier pitch) throws LineUnavailableException {
        this(pitch, () -> WaveType.TRIANGLE);
    }
    
    /**
     * Creates a beeper at full volume that uses an A4 (440 Hz) triangle wave.
     * @version 1.0
     * @since 1.0
     * @throws LineUnavailableException if the audio device cannot be opened
     */
    public Beeper() throws LineUnavailableException {
        this(() -> WaveType.TRIANGLE);
    }
    
    /**
     * Creates a beeper. The pitch and volume of the waveform can be set and adjusted
     * in real-time as audio is queued, and the tone of the waveform can be set and
     * adjusted in real-time as each beep is queued.
     * @version 1.0
     * @since 1.0
     * @param pitch the frequency of the wave, in Hz
     * @param volume the volume percent at which to do playback
     * @param waveform the shape of the wave, which determines its tone
     * @throws LineUnavailableException if the audio device cannot be opened
     */
    public Beeper(DoubleSupplier pitch, DoubleSupplier volume, Supplier<WaveType> waveform) throws LineUnavailableException {
        line = AudioSystem.getSourceDataLine(format);
        this.pitch = pitch;
        this.volume = volume;
        this.waveform = waveform;
    }
    
    /**
     * Supported common waveform shapes giving tonal control.
     * @version 1.0
     * @since 1.0
     */
    public static enum WaveType {
        /**
         * A pure frequency without overtones.
         * @version 1.0
         * @since 1.0
         */
        SINE,
        /**
         * A tone with odd-indexed harmonics in alternating decreasing amplitude.
         * The magnitudes of the amplitudes are inversely proportional to the square
         * of the harmonic number.
         * @version 1.0
         * @since 1.0
         */
        TRIANGLE,
        /**
         * A tone with even-indexed harmonics in alternating decreasing amplitude.
         * The magnitudes of the amplitudes are inversely proportional to the harmonic
         * number.
         * @version 1.0
         * @since 1.0
         */
        SAWTOOTH,
        /**
         * A tone with odd-indexed harmonics in non-alternating decreasing amplitude.
         * The magnitudes of the amplitudes are inversely proportional to the harmonic
         * number.
         * @version 1.0
         * @since 1.0
         */
        SQUARE;
        private static String[] names;
        static {
            WaveType[] w = WaveType.values();
            names = new String[w.length];
            for (int i = 0; i < w.length; i++) {
                names[i] = w[i].toString();
            }
        }
        /**
         * A list of the names of all the supported wave types. Returns a pre-computed
         * comprehension of <code>.toString()</code> across <code>WaveType.values()</code>.
         * @version 1.0
         * @since 1.0
         * @return the names of all supported wave types
         */
        public static String[] names() {
            return names;
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
    
    /**
     * {@inheritDoc}
     * @version 1.0
     * @since 1.0
     */
    @Override
    public synchronized void open() throws LineUnavailableException {
        if (audioManagerMayRun) return;
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
                double freq = pitch.getAsDouble();
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
                            Short.MAX_VALUE * volume.getAsDouble()/100.0 *
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

    /**
     * {@inheritDoc}
     * @version 1.0
     * @since 1.0
     */
    @Override
    public void setActive(boolean active) {
        playingWaveType = active ? waveform.get() : null;
    }

    /**
     * {@inheritDoc}
     * @version 1.0
     * @since 1.0
     */
    @Override
    public void wait(Duration time) throws Exception {
        Thread.sleep(time.toMillis(), time.getNano() % 1_000_000);
    }
    
    /**
     * {@inheritDoc}
     * @version 1.0
     * @since 1.0
     */
    @Override
    public synchronized void close() {
        if (!audioManagerMayRun) return;
        audioManagerMayRun = false;
        line.stop();
        line.close();
    }
}
