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
import com.randallscharpf.java.jmorsecoding.base.timings.MorseTiming;
import com.randallscharpf.java.jmorsecoding.base.playerinterfaces.OnOff;

/**
 * A set of dots and dashes that can be played back. Callers must supply a timing
 * specification and action interfaces that create the dits and dahs themselves.
 * @version 1.0
 * @since 1.0
 */
public interface PlayableMorseUnit {
    /**
     * Play back this set of dots and dashes. Gaps within this set of dots and dashes,
     * in some contexts including trailing spacing, are also played.
     * @version 1.0
     * @since 1.0
     * @param stateSetter switch to control the indicator that creates dits and dahs
     * @param delayer timer that provides the process of waiting between state transitions
     * @param standard timing with which to play back the dits and dahs
     * @throws Exception if <code>stateSetter</code> or <code>delayer</code> causes
     * an exception during operation
     */
    public void play(OnOff stateSetter, Delayer delayer, MorseTiming standard) throws Exception;
}
