/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randallscharpf.java.jmorsecoding.base;

import com.randallscharpf.java.jmorsecoding.base.standards.BuiltinMorseStandard;
import com.randallscharpf.java.jmorsecoding.base.standards.Delayer;
import com.randallscharpf.java.jmorsecoding.base.standards.MorseStandard;
import com.randallscharpf.java.jmorsecoding.base.standards.OnOff;

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
        morseStandard.getSymbolSpecification().getPlayableForString(message).play(stateSetter, delayer, morseStandard);
    }
    
    public void playProsign(String prosign) throws Exception {
        morseStandard.getSymbolSpecification().getPlayableForProsign(prosign).play(stateSetter, delayer, morseStandard);
    }
}
