/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randallscharpf.java.jmorsecoding.base.morseunits;

import com.randallscharpf.java.jmorsecoding.base.standards.Delayer;
import com.randallscharpf.java.jmorsecoding.base.standards.MorseStandard;
import com.randallscharpf.java.jmorsecoding.base.standards.OnOff;

public interface PlayableMorseUnit {
    public void play(OnOff stateSetter, Delayer delayer, MorseStandard standard) throws Exception;
}
