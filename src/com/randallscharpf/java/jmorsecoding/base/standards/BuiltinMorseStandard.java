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
import com.randallscharpf.java.jmorsecoding.base.symbolsets.ExtendedInternationalSymbolSet;
import com.randallscharpf.java.jmorsecoding.base.symbolsets.InternationalSymbolSet;
import com.randallscharpf.java.jmorsecoding.base.timings.MorseTiming;
import com.randallscharpf.java.jmorsecoding.base.timings.StandardTiming;

/**
 * A common set of rules for creating Morse Code. Each standard uses some
 * symbol set and timing that is likely to be widely usable, based on consensus,
 * practice, or standard, and is provided as such. Instances of this class are
 * immutable.
 * @version 1.0
 * @since 1.0
 */
public enum BuiltinMorseStandard implements MorseStandard {
    ITU_R_M1677_1_2009(new InternationalSymbolSet(), new StandardTiming()),
    EXTENDED_INTERNATIONAL(new ExtendedInternationalSymbolSet(), new StandardTiming()),
    ;

    private final MorseTiming timingSpec;
    private final MorseSymbolSet symbolSet;
    
    private BuiltinMorseStandard(MorseSymbolSet symbolSet, MorseTiming timingSpec) {
        this.timingSpec = timingSpec;
        this.symbolSet = symbolSet;
    }

    /**
     * {@inheritDoc}
     * @version 1.0
     * @since 1.0
     */
    @Override
    public MorseTiming getTimingSpecification() {
        return timingSpec;
    }

    /**
     * {@inheritDoc}
     * @version 1.0
     * @since 1.0
     */
    @Override
    public MorseSymbolSet getSymbolSpecification() {
        return symbolSet;
    }   
}
