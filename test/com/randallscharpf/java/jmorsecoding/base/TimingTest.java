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
package com.randallscharpf.java.jmorsecoding.base;

import com.randallscharpf.java.jmorsecoding.base.timings.*;
import com.randallscharpf.java.jmorsecoding.base.morseunits.ElementType;
import java.time.Duration;

import junit.framework.TestCase;

// Tests WPM values and element duration ratios on built-in timings
// Uses PARIS as standard word, since it is intended for natural language, while
// the alternative CODEX is intended for random language.
public class TimingTest extends TestCase {
    
    public TimingTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testStandardTiming() {
        System.out.println("=== Testing StandardTiming ===");
        System.out.println();
        StandardTiming timing1 = new StandardTiming();
        StandardTiming timing2 = new StandardTiming();
        double[] wpms = new double[]{10, 20, 30, 40, Math.PI, Math.E, 1.23456789e-6, 1.23456e+8};
        for (double wpm : wpms) {
            timing1 = new StandardTiming(wpm);
            testStandardTiming(timing1, wpm);
            timing2.settings.setWpm(wpm);
            testStandardTiming(timing2, wpm);
        }
        try {
            timing1 = new StandardTiming(4.92032930e+20);
            fail("No exception thrown!");
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("WPM"));
        }
        try {
            timing1.settings.setWpm(4.92032930e+20);
            fail("No exception thrown!");
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("WPM"));
        }
        System.out.println("=== Finished Testing StandardTiming ===");
        System.out.println();
    }
    
    private void testStandardTiming(StandardTiming timing, double targetWpm) {
        System.out.println("Testing standard timing with " + targetWpm + " wpm...");
        System.out.println("\tTesting words per minute using PARIS...");
        checkOverallWpm(timing, targetWpm);
        // debug printing is internal to element ratio checking
        checkElementRatios(timing, new int[]{1, 3, 7});
        System.out.println();
    }
    
    public void testFarnsworthTiming() {
        System.out.println("=== Testing FarnsworthTiming ===");
        System.out.println();
        FarnsworthTiming timing1 = new FarnsworthTiming();
        FarnsworthTiming timing2 = new FarnsworthTiming();
        double[] wpms = new double[]{10, 20, 30, 40, Math.PI, Math.E, 1.23456789e-6, 1.23456e+8};
        IllegalArgumentException ex;
        for (double wpm1 : wpms) {
            timing1 = new FarnsworthTiming(wpm1);
            testFarnsworthTiming(timing1, wpm1, Math.max(wpm1, 18));
            timing2.settings.setWpm(wpm1);
            testFarnsworthTiming(timing2, wpm1, Math.max(wpm1, 18));
            for (double wpm2 : wpms) {
                ex = null;
                try {
                    timing1 = new FarnsworthTiming(wpm1, wpm2);
                    testFarnsworthTiming(timing1, wpm1, wpm2);
                } catch (IllegalArgumentException exi) {
                    ex = exi;
                }
                assertTrue((wpm2 >= wpm1) ^ (ex != null && ex.getMessage().contains("WPM")));
                ex = null;
                try {
                    timing2.settings.setWpm(wpm1, wpm2);
                    testFarnsworthTiming(timing2, wpm1, wpm2);
                } catch (IllegalArgumentException exi) {
                    ex = exi;
                }
                assertTrue((wpm2 >= wpm1) ^ (ex != null && ex.getMessage().contains("WPM")));
            }
        }
        try {
            timing1 = new FarnsworthTiming(4.92032930e+20);
            fail("No exception thrown!");
        } catch (IllegalArgumentException exi) {
            assertTrue(exi.getMessage().contains("WPM"));
        }
        try {
            timing1.settings.setWpm(4.92032930e+20);
            fail("No exception thrown!");
        } catch (IllegalArgumentException exi) {
            assertTrue(exi.getMessage().contains("WPM"));
        }
        try {
            timing1 = new FarnsworthTiming(10, 4.92032930e+20);
            fail("No exception thrown!");
        } catch (IllegalArgumentException exi) {
            assertTrue(exi.getMessage().contains("WPM"));
        }
        try {
            timing1.settings.setWpm(10, 4.92032930e+20);
            fail("No exception thrown!");
        } catch (IllegalArgumentException exi) {
            assertTrue(exi.getMessage().contains("WPM"));
        }
        System.out.println("=== Finished Testing FarnsworthTiming ===");
        System.out.println();
    }
    
    private void testFarnsworthTiming(FarnsworthTiming timing, double totalWpm, double letterWpm) {
        System.out.println("Testing Farnsworth timing with " + totalWpm + " total wpm and " + letterWpm + " letter wpm...");
        System.out.println("\tTesting words per minute using PARIS...");
        checkOverallWpm(timing, totalWpm);
        checkLetterWpm(timing, letterWpm);
        // debug printing is internal to element ratio checking
        checkElementRatios(timing, new int[]{1, 1, 3}, new int[]{3, 7});
        System.out.println();
    }
    
        // Standard word is PARIS which is .--./.-/.-./../...//
        // which has 10 .'s; 4 -'s, 4 /'s, 1 //, and 9 element gaps
        // this is 50 total units

    private void checkOverallWpm(MorseTiming timing, double targetWpm) {
        // use divide by here instead of multiply by because quantization of
        // durations to nanosecond precision with a required ratio means that the
        // time for one word must be a multiple of 50, so the length specified by
        // the WPM parameter cannot be achieved to more precision than 50 nanoseconds,
        // and we should only check the expected and actual value correspond to the
        // same multiple of 50 nanoseconds
        assertEquals(
                Math.round(1.2e+9 / targetWpm), Math.round(Duration.ZERO.plus(
                timing.timeForElementType(ElementType.DOT).multipliedBy(10)).plus(
                timing.timeForElementType(ElementType.DASH).multipliedBy(4)).plus(
                timing.timeForElementType(ElementType.LETTER_GAP).multipliedBy(4)).plus(
                timing.timeForElementType(ElementType.WORD_GAP).multipliedBy(1)).plus(
                timing.timeForElementType(ElementType.ELEMENT_GAP).multipliedBy(9)).toNanos() / 50.0)
        );
    }
    
    private void checkLetterWpm(MorseTiming timing, double targetWpm) {
        // using divided by here instead of multiplied by because quantization of
        // durations to nanosecond precision with a required ratio means that the
        // time for one word must be a multiple of 50, so the length specified by
        // the WPM parameter cannot be achieved to more precision than 50 nanoseconds,
        // and we should only check the expected and actual value correspond to the
        // same multiple of 50 nanoseconds
        assertEquals(
                Math.round(1.2e+9 / targetWpm), Math.round(Duration.ZERO.plus(
                timing.timeForElementType(ElementType.DOT).multipliedBy(10)).plus(
                timing.timeForElementType(ElementType.DASH).multipliedBy(4)).plus(
                timing.timeForElementType(ElementType.ELEMENT_GAP).multipliedBy(/*4 letter gaps*/ 12)).plus(
                timing.timeForElementType(ElementType.ELEMENT_GAP).multipliedBy(/*1 word gap*/ 7)).plus(
                timing.timeForElementType(ElementType.ELEMENT_GAP).multipliedBy(9)).toNanos() / 50.0)
        );
    }

    /**
     * Checks ratios between element durations and element gap durations. The ratio between gap durations is
     * specified by the first parameter array: for example, parameters {1, 1, 3} imply that the ratio between element
     * gaps, dots, and dashes is 1:1:3, with tolerance for non-integer ratios. The ratio between element
     * durations is given by the second parameter array: for example, parameters {2, 5} imply that the ratio between
     * dot lengths and dash lengths is 2:5, with tolerance for non-integer ratios. Tolerance on a given duration is
     * such that the durations provided by the implementation must be some set of Xi which, for specified ratios Yi,
     * satisfy Yi&lt;Xi/a&lt;Yi+1 for some value a and all i. If no second parameter array is given, it is implied
     * that the ratio between elements follows that between element gaps in such a way that the duration of a dot is
     * the same as the duration of an intra-letter element gap and the duration of a dash is the same as the duration
     * of a letter gap.
     * 
     * @param timing the timing object to test
     * @param parameters the ratios of timings within that object
     */
    private void checkElementRatios(MorseTiming timing, int[]... parameters) {
        // using divided here instead of multiplies allows implementations to, within reason, optimize
        // the distribution of their element type durations to acheive to maximum precision the ratios
        // between element type durations or to achieve to maximum precision the length of a word,
        // since it is impossible to do both, and no answer along that spectrum is most correct
        if (parameters.length == 1) {
            System.out.println("\tTesting the ratio between a letter gap and an element gap...");
            assertEquals(
                    timing.timeForElementType(ElementType.LETTER_GAP).dividedBy(parameters[0][1]),
                    timing.timeForElementType(ElementType.ELEMENT_GAP).dividedBy(parameters[0][0])
            );
        }
        System.out.println("\tTesting the ratio between the length of an element gap and a dot...");
        assertEquals(
                timing.timeForElementType(ElementType.ELEMENT_GAP).dividedBy(parameters.length == 1 ? parameters[0][0] : parameters[0][0]),
                timing.timeForElementType(ElementType.DOT).dividedBy(parameters.length == 1 ? parameters[0][0] : parameters[0][1])
        );
        System.out.println("\tTesting the ratio between the length of a dash and a dot...");
        assertEquals(
                timing.timeForElementType(ElementType.DASH).dividedBy(parameters.length == 1 ? parameters[0][1] : parameters[0][2]),
                timing.timeForElementType(ElementType.DOT).dividedBy(parameters.length == 1 ? parameters[0][0] : parameters[0][1])
        );
        System.out.println("\tTesting the ratio between the length of a word gap and a letter gap...");
        assertEquals(
                timing.timeForElementType(ElementType.WORD_GAP).dividedBy(parameters.length == 1 ? parameters[0][2] : parameters[1][1]),
                timing.timeForElementType(ElementType.LETTER_GAP).dividedBy(parameters.length == 1 ? parameters[0][1] : parameters[1][0])
        );
        System.out.println("Timing passed!");
    }
}
