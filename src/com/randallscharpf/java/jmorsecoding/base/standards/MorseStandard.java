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
package com.randallscharpf.java.jmorsecoding.base.standards;

import com.randallscharpf.java.jmorsecoding.base.symbolsets.MorseSymbolSet;
import com.randallscharpf.java.jmorsecoding.base.timings.MorseTiming;

/**
 * A set of rules defining a Morse Code messaging protocol. Includes the translation
 * from symbols to dashes and dots as well as the rules regarding timing of dots,
 * dashes, and gaps between them.
 * @since 1.0
 */
public interface MorseStandard {
    /**
     * Gives the timing parameters of this Morse Code protocol. The object returned
     * provides the lengths of dots, dashes, and gaps between them.
     * @return the <code>MorseTiming</code> specification for this protocol
     */
    public MorseTiming getTimingSpecification();
    /**
     * Gives the symbol translation parameters of this Morse Code protocol. The object returned
     * provides the set of dots and dashes for each written character in a word and
     * each prosign.
     * @return the <code>MorseSymbolSet</code> specification for this protocol
     */
    public MorseSymbolSet getSymbolSpecification();
}
