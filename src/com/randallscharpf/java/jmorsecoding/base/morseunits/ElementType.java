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
package com.randallscharpf.java.jmorsecoding.base.morseunits;

/**
 * A state and a relative duration within a specification for timing.
 * @version 1.0
 * @since 1.0
 */
public enum ElementType {
    /**
     * A minimum-length unit of Morse Code with "on" state.
     * @version 1.0
     * @since 1.0
     */
    DOT(true),
    /**
     * A medium-length unit of Morse Code with "on" state.
     * @version 1.0
     * @since 1.0
     */
    DASH(true),
    /**
     * A minimum-length unit of Morse Code with "off" state.
     * @version 1.0
     * @since 1.0
     */
    ELEMENT_GAP(false),
    /**
     * A medium-length unit of Morse Code with "off" state.
     * @version 1.0
     * @since 1.0
     */
    LETTER_GAP(false),
    /**
     * A maximum-length unit of Morse Code with "off" state.
     * @version 1.0
     * @since 1.0
     */
    WORD_GAP(false);
    /**
     * The state of the instance. A <code>true</code> is present here if an element
     * type of this instance is played in the "on" state and a <code>false</code>
     * is present here if an element type of this instance is played in the "off"
     * state.
     * @version 1.0
     * @since 1.0
     */
    public final boolean activeDuringPlay;
    private ElementType(boolean on) {
        this.activeDuringPlay = on;
    }
}
