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
        
        private double secsMorseUnitChar;
        private double secsMorseUnitWord;
        
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
            secsMorseUnitChar = 1.2 / charWpm;
            secsMorseUnitWord = (60/overallWpm - 31*secsMorseUnitChar) / 19;
        }
    }
    
    public final Settings settings = new Settings();

    @Override
    public Duration timeForElementType(ElementType type) {
        switch(type) {
            case DASH:
                return Duration.ofNanos((long) (3*settings.secsMorseUnitChar * 1.0e9));
            case DOT:
                return Duration.ofNanos((long) (settings.secsMorseUnitChar * 1.0e9));
            case ELEMENT_GAP:
                return Duration.ofNanos((long) (settings.secsMorseUnitChar * 1.0e9));
            case LETTER_GAP:
                return Duration.ofNanos((long) (3*settings.secsMorseUnitWord * 1.0e9));
            case WORD_GAP:
                return Duration.ofNanos((long) (7*settings.secsMorseUnitWord * 1.0e9));
            default:
                throw new UnsupportedOperationException("Not implemented for element type " + type);
        }
    }
    
}
