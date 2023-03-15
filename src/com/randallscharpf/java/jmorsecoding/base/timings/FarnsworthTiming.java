/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randallscharpf.java.jmorsecoding.base.timings;

import com.randallscharpf.java.jmorsecoding.base.morseunits.ElementType;
import java.time.Duration;

/**
 * Based on the recommendations in
 * https://www.arrl.org/files/file/Technology/x9004008.pdf
 */
public final class FarnsworthTiming implements MorseTiming {

    public FarnsworthTiming() {}
    
    public FarnsworthTiming(double wpm) {
        this();
        settings.setWpm(wpm);
    }
    
    public FarnsworthTiming(double overallWpm, double charWpm) {
        this();
        settings.setWpm(overallWpm, charWpm);
    }
    
    public final class Settings {

        private Settings() {
            setWpm(8);
        }
        
        private long nanosMorse1Unit;
        private long nanosMorse3Unit;
        private long nanosMorse3gapUnit;
        private long nanosMorse7gapUnit;
        
        public void setWpm(double wpm) {
            if (wpm < 18) {
                setWpm(wpm, 18);
            } else {
                setWpm(wpm, wpm);
            }
        }
        
        public void setWpm(double overallWpm, double charWpm) {
            if (overallWpm <= 0) {
                throw new IllegalArgumentException("WPM values must be positive.");
            }
            if (charWpm <= 0) {
                throw new IllegalArgumentException("WPM values must be positive.");
            }
            if (overallWpm > charWpm) {
                throw new IllegalArgumentException(
                        "Overall WPM must not exceed the WPM for one character."
                );
            }
            if (overallWpm > 1.2e+9 || charWpm > 1.2e+9) {
                throw new IllegalArgumentException(Math.max(overallWpm, charWpm) + " WPM not supported:"
                        + "subnanosecond timing precision required!");
            }
            long nanosMorseUnits = Math.round(60e+9 / charWpm);
            nanosMorse1Unit = nanosMorseUnits / 50;
            nanosMorse3Unit = 3 * nanosMorse1Unit;
            int plus3 = 0;
            int plus7 = 0;
            while ((nanosMorseUnits > nanosMorse1Unit*38 + nanosMorse3Unit*4) && (plus3 < 2)) {
                plus3++;
                nanosMorse3Unit++;
            }
            if (Math.round(1.2e+9 / charWpm) > Math.round((nanosMorse1Unit*38 + nanosMorse3Unit*4)/50.0)) {
                nanosMorse1Unit++;
                nanosMorse3Unit = 3 * nanosMorse1Unit;
            }
            long gapUnits = Math.round(60e+9 / overallWpm) - (nanosMorse1Unit*19 + nanosMorse3Unit*4);
            nanosMorse3gapUnit = gapUnits / 19 * 3;
            nanosMorse7gapUnit = gapUnits / 19 * 7;
            plus3 = 0;
            plus7 = 0;
            while ((gapUnits > nanosMorse3gapUnit * 4 + nanosMorse7gapUnit) && (plus7 < 6)) {
                if (plus7 < 2*plus3 + 2) {
                    plus7++;
                    nanosMorse7gapUnit++;
                } else {
                    plus3++;
                    nanosMorse3gapUnit++;
                }
            }
            if (Math.round(1.2e+9 / overallWpm) > Math.round(
                    (nanosMorse1Unit*19 + nanosMorse3Unit*4 + nanosMorse3gapUnit*4 + nanosMorse7gapUnit)/50.0)) {
                nanosMorse3gapUnit = (gapUnits / 19 + 1) * 3;
                nanosMorse7gapUnit = (gapUnits / 19 + 1) * 7;
            }
        }
    }
    
    public final Settings settings = new Settings();

    @Override
    public Duration timeForElementType(ElementType type) {
        switch(type) {
            case DASH:
                return Duration.ofNanos(settings.nanosMorse3Unit);
            case DOT:
                return Duration.ofNanos(settings.nanosMorse1Unit);
            case ELEMENT_GAP:
                return Duration.ofNanos(settings.nanosMorse1Unit);
            case LETTER_GAP:
                return Duration.ofNanos(settings.nanosMorse3gapUnit);
            case WORD_GAP:
                return Duration.ofNanos(settings.nanosMorse7gapUnit);
            default:
                throw new UnsupportedOperationException("Not implemented for element type " + type);
        }
    }
    
}
