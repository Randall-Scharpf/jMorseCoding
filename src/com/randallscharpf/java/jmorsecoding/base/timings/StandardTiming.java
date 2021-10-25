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
    
    public StandardTiming(double wpm) {
        this();
        settings.setWpm(wpm);
    }
    
    public final class Settings {
        private Settings() {
            setWpm(24);
        }
        
        private double secsMorseUnit;

        public void setWpm(double wpm) {
            secsMorseUnit = 1.2 / wpm;
        }
    }
    
    public final Settings settings = new Settings();

    @Override
    public Duration timeForElementType(ElementType type) {
        switch(type) {
            case DASH:
                return Duration.ofNanos((long) (3*settings.secsMorseUnit * 1.0e9));
            case DOT:
                return Duration.ofNanos((long) (settings.secsMorseUnit * 1.0e9));
            case ELEMENT_GAP:
                return Duration.ofNanos((long) (settings.secsMorseUnit * 1.0e9));
            case LETTER_GAP:
                return Duration.ofNanos((long) (3*settings.secsMorseUnit * 1.0e9));
            case WORD_GAP:
                return Duration.ofNanos((long) (7*settings.secsMorseUnit * 1.0e9));
            default:
                throw new UnsupportedOperationException("Not implemented for element type " + type);
        }
    }
    
}
