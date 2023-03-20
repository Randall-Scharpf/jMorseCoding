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

import com.randallscharpf.java.jmorsecoding.base.playerinterfaces.Delayer;
import com.randallscharpf.java.jmorsecoding.base.playerinterfaces.OnOff;
import com.randallscharpf.java.jmorsecoding.base.timings.MorseTiming;

/**
 * A single dot, dash, or gap. Each element is immutable and is of exactly one of the
 * types defined in {@link ElementType}.
 * @version 1.0
 * @since 1.0
 */
public class Element implements PlayableMorseUnit {
    
    private final ElementType type;
    
    /**
     * Creates an element with the specified type. This determines both the length
     * of the element relative to other elements played under the same standard and
     * the state with which the element is perceived (either "on" or "off").
     * @param type the type of element to create
     */
    public Element(ElementType type) {
        this.type = type;
    }

    /**
     * {@inheritDoc}
     * @version 1.0
     * @since 1.0
     */
    @Override
    public void play(OnOff stateSetter, Delayer delayer, MorseTiming morseStandard) throws Exception {
        stateSetter.setActive(type.activeDuringPlay);
        delayer.wait(
                morseStandard.timeForElementType(type)
        );
    }
    
}
