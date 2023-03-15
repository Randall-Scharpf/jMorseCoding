package com.randallscharpf.java.jmorsecoding.base;

import com.randallscharpf.java.jmorsecoding.base.codesets.InternationalSymbolSet;
import com.randallscharpf.java.jmorsecoding.base.morseunits.ElementType;
import com.randallscharpf.java.jmorsecoding.base.standards.DefaultMorseStandard;
import com.randallscharpf.java.jmorsecoding.base.timings.FarnsworthTiming;
import com.randallscharpf.java.jmorsecoding.base.timings.StandardTiming;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Randall
 */
public class sandbox {
    static int framesMade = 0;
    public static void main(String[] args) throws LineUnavailableException {
        StandardTiming timing = new StandardTiming(1.21e+8);
        System.out.println(timing.timeForElementType(ElementType.ELEMENT_GAP));
        System.exit(0);
        AudioFormat format = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                48000,
                8,
                1,
                1,
                48000,
                true
        );
        SourceDataLine line = AudioSystem.getSourceDataLine(format);
        byte[] waveform = new byte[24000];
        int period = 109;//960;
        for (int i = 0; i < waveform.length; i++) {
            double in = (i * 2.0 * Math.PI) / (period);
            double sin = Math.sin(in);
//            double scaled = sin;
            double scaled = 2 * Math.asin(sin) / Math.PI; // triangle
//            double scaled = Math.signum(sin);
            waveform[i] = (byte) (127 * scaled);
        }
        line.write(waveform, 0, waveform.length);
        framesMade += 24000;
        line.open(format);
        MorsePlayer morsePlayer = new MorsePlayer(
                (active) -> {
                    if (active) {
                        line.start();
                        new Thread(() -> {
                            if (line.getLongFramePosition() - 24000 < framesMade) {
                                line.write(waveform, 0, waveform.length);
                                framesMade += 24000;
                            }
                        }).start();
                    } else {
                        line.stop();
                    }
                },
                (time) -> {
                    Thread.sleep(time.toMillis());
                },
                new DefaultMorseStandard(timing, new InternationalSymbolSet())
        );
        try {
            morsePlayer.playMorseFromString("this is pretty cool");
        } catch (Exception ex) {
            System.err.println("Exception during Morse playback!");
            ex.printStackTrace(System.err);
        }
    }
}
