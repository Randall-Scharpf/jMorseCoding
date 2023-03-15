/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randallscharpf.java.jmorsecoding.base.timings;

import com.randallscharpf.java.jmorsecoding.base.morseunits.ElementType;
import java.time.Duration;

public final class StandardTiming implements MorseTiming {

    public StandardTiming() {}
    
    /**
     * note that the length of the standard word (PARIS) may be up to 50 nanoseconds
     * shorter than expected due to the integer precision of Durations
     * @param wpm 
     */
    public StandardTiming(double wpm) {
        this();
        settings.setWpm(wpm);
    }
    
    public final class Settings {
        private Settings() {
            setWpm(24);
        }
        
        private long nanosMorse1Unit;
        private long nanosMorse3Unit;
        private long nanosMorse7Unit;

        public void setWpm(double wpm) {
            if (wpm <= 0) {
                throw new IllegalArgumentException("WPM values must be positive.");
            }
            if (wpm > 1.2e+9) {
                throw new IllegalArgumentException(wpm + " WPM not supported:"
                        + "subnanosecond timing precision required!");
            }
            long nanosMorseUnits = Math.round(60e+9 / wpm);
            nanosMorse1Unit = nanosMorseUnits / 50;
            nanosMorse3Unit = 3 * nanosMorse1Unit;
            nanosMorse7Unit = 7 * nanosMorse1Unit;
            int plus3 = 0;
            int plus7 = 0;
            while ((nanosMorseUnits > nanosMorse1Unit*19 + nanosMorse3Unit*8 + nanosMorse7Unit) && (plus7 < 6)) {
                if (plus7 < 2*plus3 + 2) {
                    plus7++;
                    nanosMorse7Unit++;
                } else {
                    plus3++;
                    nanosMorse3Unit++;
                }
            }
            if (Math.round(1.2e+9 / wpm) > Math.round((nanosMorse1Unit*19 + nanosMorse3Unit*8 + nanosMorse7Unit)/50.0)) {
                nanosMorse1Unit++;
                nanosMorse3Unit = 3 * nanosMorse1Unit;
                nanosMorse7Unit = 7 * nanosMorse1Unit;
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
                return Duration.ofNanos(settings.nanosMorse3Unit);
            case WORD_GAP:
                return Duration.ofNanos(settings.nanosMorse7Unit);
            default:
                throw new UnsupportedOperationException("Not implemented for element type " + type);
        }
    }
    
}
