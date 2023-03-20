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
 * A <code>MorseStandard</code> implementation that returns the same timing and symbol
 * set specifications whenever asked through its lifetime. Instances of this class
 * are immutable.
 * @version 1.0
 * @since 1.0
 */
public class DefaultMorseStandard implements MorseStandard {
    
    private final MorseTiming timing;
    private final MorseSymbolSet symbols;
    
    /**
     * Creates a <code>MorseStandard</code> with the supplied timing and symbol set
     * specifications. These specifications cannot be changed throughout the lifetime
     * of the instance, and the results of <code>getTimingSpecification()</code>
     * and <code>getSymbolSpecification</code> are exactly the parameters to this
     * method.
     * @param timing an object giving the timing parameters of the Morse Code protocol
     * @param symbols an object giving the symbol translation parameters of the Morse Code protocol
     * @version 1.0
     * @since 1.0
     */
    public DefaultMorseStandard(MorseTiming timing, MorseSymbolSet symbols) {
        this.timing = timing;
        this.symbols = symbols;
    }

    /**
     * {@inheritDoc}
     * @version 1.0
     * @since 1.0
     */
    @Override
    public MorseTiming getTimingSpecification() {
        return timing;
    }

    /**
     * {@inheritDoc}
     * @version 1.0
     * @since 1.0
     */
    @Override
    public MorseSymbolSet getSymbolSpecification() {
        return symbols;
    }
}
