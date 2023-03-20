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
package com.randallscharpf.java.jmorsecoding.base.timings;

import com.randallscharpf.java.jmorsecoding.base.morseunits.ElementType;
import java.time.Duration;

/**
 * A single-parameter timing based on current standard convention. The timing sets
 * a dash as three times as long as a dot. It sets the gap between two dots, two
 * dashes, or a dot and a dash to be the length of a dot. It sets the total gap
 * between two characters in the same word to be the length of a dash and the total
 * gap between two words to be the same as seven dots. The single parameter is the
 * words per minute of text send, assuming that the standard word PARIS is the only
 * word sent.
 * @version 1.0
 * @since 1.0
 */
public final class StandardTiming implements MorseTiming {

    /**
     * Creates a <code>StandardTiming</code> at 24 WPM. This WPM value is calculated
     * using the standard word PARIS. It can be changed by interacting with the
     * settings object of this timing, as in <code>this.settings.setWpm(30)</code>.
     * The ratios between elements (element gap/dot : letter gap/dash : word gap)
     * is fixed at 1:3:7.
     * @version 1.0
     * @since 1.0
     */
    public StandardTiming() {}
    
    /**
     * Creates a <code>StandardTiming</code> at some WPM. This WPM value is calculated
     * using the standard word PARIS. It can be changed by interacting with the
     * settings object of this timing, as in <code>this.settings.setWpm(30);</code>.
     * The ratios between elements (element gap/dot : letter gap/dash : word gap)
     * is fixed at 1:3:7.
     * @version 1.0
     * @since 1.0
     * @param wpm the initial WPM value with which to setup the timing specification
     */
    public StandardTiming(double wpm) {
        this();
        settings.setWpm(wpm);
    }
    
    /**
     * The settings for a <code>StandardTiming</code>. A single setting is exposed
     * via this object: the WPM of the timing specification. On change, the instance
     * recomputes the lengths of the elements so that they continue to satisfy the
     * requirements of a <code>StandardTiming</code>.
     * @version 1.0
     * @since 1.0
     */
    public final class Settings {
        private Settings() {
            setWpm(24);
        }
        
        private long nanosMorse1Unit;
        private long nanosMorse3Unit;
        private long nanosMorse7Unit;

        /**
         * Sets the playback WPM of this instance. WPM is calculated assuming every
         * word is the standard word PARIS. The object sets up the timing such that
         * the actual length of a unit with a length of three dots is within the range
         * from three dots to three dots plus two nanoseconds, inclusive, and so
         * that the actual length of a unit with a length of seven dots is within
         * the range from seven dots to seven dots plus six nanoseconds, inclusive.
         * If it is possible to exactly achieve the input WPM while satisfying this,
         * then this is done, otherwise the lengths of the units are set to obtain
         * the maximum possible transmission rate that does not exceed the parameter.
         * @version 1.0
         * @since 1.0
         * @param wpm the WPM, with standard PARIS, to aim to achieve
         */
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
    
    /**
     * The settings for this instance. To modify the settings of this timing, users
     * must call methods on this member.
     * @version 1.0
     * @since 1.0
     */
    public final Settings settings = new Settings();

    /**
     * {@inheritDoc}
     * @version 1.0
     * @since 1.0
     */
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
