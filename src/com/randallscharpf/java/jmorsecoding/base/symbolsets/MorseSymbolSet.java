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
package com.randallscharpf.java.jmorsecoding.base.symbolsets;

import com.randallscharpf.java.jmorsecoding.base.morseunits.PlayableMorseUnit;

/**
 * A translator from written characters and prosigns to dashes and dots. Each character
 * or prosign is a "symbol" which, in Morse Code form, is a minimum meaningful unit
 * that can be played.
 * @since 1.0
 */
public interface MorseSymbolSet {
    /**
     * Returns a unit that plays the each of the characters in the given string in
     * order, with proper separation. The string is terminated with a word gap so
     * that it becomes a discrete message, unless the end of the provided string
     * is whitespace.
     * @since 1.0
     * @param str the set of characters to compose the returned unit
     * @return a gap-terminated <code>PlayableMorseUnit</code> for the input string
     */
    public PlayableMorseUnit getPlayableForString(String str);
    /**
     * Returns a unit that plays a prosign. The prosign is followed by a word gap.
     * The input string and prosign name (as listed in {@link #getAvailableProsigns()}
     * must be approximately the same, but the choice of comparison function is left
     * open to implementers. A good choice for implementers is to compare the strings
     * in lowercase, and users of this function should prefer to provide as parameter
     * the exact string that appears in the list of available prosigns.
     * @since 1.0
     * @param prosignName a string giving the action represented by the prosign
     * @return a gap-terminated <code>PlayableMorseUnit</code> for the requested prosign
     */
    public PlayableMorseUnit getPlayableForProsign(String prosignName);
    /**
     * Gives the names of the prosigns registered as symbols in this set. The name
     * of a prosign is typically related to the action it performs in communication
     * or the written symbol used in transcripts of conversations to indicate that
     * the prosign was sent. The names in this array must be such that calling
     * {@link #getPlayableForProsign(java.lang.String)} on any <code>str</code> that
     * is an element of this array will give a valid <code>PlayableMorseUnit</code>
     * and not instead throw an error.
     * @since 1.0
     * @return an array of the names of all prosigns supported by this symbol set
     */
    public String[] getAvailableProsigns();
}
