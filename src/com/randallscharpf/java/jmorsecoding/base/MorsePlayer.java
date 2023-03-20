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

import com.randallscharpf.java.jmorsecoding.base.standards.BuiltinMorseStandard;
import com.randallscharpf.java.jmorsecoding.base.playerinterfaces.Delayer;
import com.randallscharpf.java.jmorsecoding.base.standards.MorseStandard;
import com.randallscharpf.java.jmorsecoding.base.playerinterfaces.OnOff;

public class MorsePlayer {

    public final MorseStandard morseStandard;
    public final OnOff stateSetter;
    public final Delayer delayer;

    public MorsePlayer(OnOff stateSetter, Delayer delayer) {
        this(stateSetter, delayer, BuiltinMorseStandard.ITU_R_M1677_1_2009);
    }
    
    public MorsePlayer(OnOff stateSetter, Delayer delayer, MorseStandard morseStandard) {
        this.morseStandard = morseStandard;
        this.stateSetter = stateSetter;
        this.delayer = delayer;
    }
    
    // Be aware that this method could misinterpret your string if you type it in a way that violates the convention
    // of the currently set morse standard. If perfect results are of the utmost essence, the message readout must
    // be checked. Alternatively, directly calling the characters and playing them individually is far less prone
    // to errors in morse convention (albeit at the cost of more potential for programming errors).
    public void playMorseFromString(String message) throws Exception {
        morseStandard.getSymbolSpecification().getPlayableForString(message).play(stateSetter, delayer, morseStandard.getTimingSpecification());
    }
    
    public void playProsign(String prosign) throws Exception {
        morseStandard.getSymbolSpecification().getPlayableForProsign(prosign).play(stateSetter, delayer, morseStandard.getTimingSpecification());
    }
}
