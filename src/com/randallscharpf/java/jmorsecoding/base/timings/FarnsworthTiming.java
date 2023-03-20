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
 * A two-parameter timing used for beginners learning Morse Code. Based on the
 * recommendations in <a href="https://www.arrl.org/files/file/Technology/x9004008.pdf">
 * https://www.arrl.org/files/file/Technology/x9004008.pdf</a>. This
 * timing uses a certain WPM for the rate at which symbols are sent, giving them
 * the characteristic rhythm they have under similarly-rated professional timings,
 * while adding in extra spacing between symbols to slow the overall rate down to
 * a different WPM. It sets the gap between two dots, two dashes, or a dot and a
 * dash to be the same length as a dot. It sets a dash to be three times this length.
 * It also independently sets the ratio between the gap between two characters within
 * a word and the gap between two words to be 3:7.
 * @version 1.0
 * @since 1.0
 */
public final class FarnsworthTiming implements MorseTiming {

    /**
     * Creates a <code>FarnsworthTiming</code> an overall rate of 8 WPM with
     * characters sent at an 18 WPM rhythm. These values are calculated using the
     * standard word PARIS. They can be changed by interacting with the settings
     * object of this timing, as in <code>this.settings.setWpm(10, 20);</code>.
     * The ratios between symbol elements (element gap : dot : dash) is fixed at
     * 1:1:3 and the ratio between non-symbol elements (letter gap : word gap) is
     * fixed at 3:7.
     * @version 1.0
     * @since 1.0
     */
    public FarnsworthTiming() {}
    
    /**
     * Creates a <code>FarnsworthTiming</code> with the specified overall rate. The
     * character rhythm is 18 WPM if the overall WPM is slower than this, otherwise
     * the character rhythm and overall rate are defined by the same WPM values.
     * @version 1.0
     * @since 1.0
     * @param wpm the overall WPM rate with which to setup the timing specification
     */
    public FarnsworthTiming(double wpm) {
        this();
        settings.setWpm(wpm);
    }
    
    /**
     * Creates a <code>FarnsworthTiming</code> with the specified overall rate and
     * character rhythm. The character rhythm must be specified by a WPM value no
     * slower than the overall rate.
     * @version 1.0
     * @since 1.0
     * @param overallWpm the overall WPM rate with which to setup the timing specification
     * @param charWpm the WPM rate to use to determine the rhythm of characters
     */
    public FarnsworthTiming(double overallWpm, double charWpm) {
        this();
        settings.setWpm(overallWpm, charWpm);
    }
    
    /**
     * The settings for a <code>FarnsworthTiming</code>. Two settings are exposed
     * via this object: the overall WPM of the timing specification and the WPM
     * rate at which individual symbols are played, which determines their rhythm.
     * On change, the instance recomputes the lengths of the elements so that they
     * continue to satisfy the requirements of a <code>FarnsworthTiming</code>.
     * @version 1.0
     * @since 1.0
     */
    public final class Settings {

        private Settings() {
            setWpm(8);
        }
        
        private long nanosMorse1Unit;
        private long nanosMorse3Unit;
        private long nanosMorse3gapUnit;
        private long nanosMorse7gapUnit;
        
        /**
         * Sets the overall WPM and assumes an appropriate character rhythm. The
         * character rhythm is given by 18 WPM if the overall WPM specified is less
         * than 18, and otherwise the character rhythm is assume to be specified by
         * the same value as the overall rate. Internally, this method uses
         * {@link #setWpm(double, double)} to calculate the new timing parameters.
         * @param wpm the overall WPM, using standard word PARIS, for the timing specification
         */
        public void setWpm(double wpm) {
            if (wpm < 18) {
                setWpm(wpm, 18);
            } else {
                setWpm(wpm, wpm);
            }
        }
        
        /**
         * Sets the overall WPM and character rhythm WPM. WPM is calculated assuming
         * every word is the standard word PARIS. The object sets up the timing such
         * that the actual length of a dash is within the range from three dots to three
         * dots plus two nanoseconds, inclusive. It also sets the length of a gap
         * between words to be at least seven thirds the length of a gap between
         * letters and no more than two nanoseconds longer than this minimum.
         * If it is possible to exactly achieve the input WPMs while satisfying this,
         * then this is done, otherwise the lengths of the units are set to obtain
         * the maximum character rhythm that does not exceed that parameter and the
         * maximum transmission rate that does not exceed that parameter.
         * @version 1.0
         * @since 1.0
         * @param overallWpm the WPM of transmission, with standard PARIS, to aim to achieve
         * @param charWpm the rhythm of characters as determined by a standard timing
         */
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
                return Duration.ofNanos(settings.nanosMorse3gapUnit);
            case WORD_GAP:
                return Duration.ofNanos(settings.nanosMorse7gapUnit);
            default:
                throw new UnsupportedOperationException("Not implemented for element type " + type);
        }
    }
    
}
