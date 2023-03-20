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
 * A set of timing parameters for dots, dashes, and gaps.
 * @since 1.0
 */
@FunctionalInterface
public interface MorseTiming {
    /**
     * Gives the duration for which the unit should be played. For a dot or dash,
     * this time is the amount of time for which the output signal is "on". For a
     * gap, this is the amount of time for which the output signal is "off".
     * @since 1.0
     * @param type the type of Morse Code unit for which to get the duration
     * @return the time that should be spent transmitting a unit of the type, as a <code>Duration</code>
     */
    public Duration timeForElementType(ElementType type);
}
