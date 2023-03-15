/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randallscharpf.java.jmorsecoding.base.morseunits;

import com.randallscharpf.java.jmorsecoding.base.standards.Delayer;
import com.randallscharpf.java.jmorsecoding.base.timings.MorseTiming;
import com.randallscharpf.java.jmorsecoding.base.standards.OnOff;

/**
 * A set of dits and dahs that can be played back. Callers must supply a timing specification
 * and action interfaces that create the dits and dahs themselves.
 * @version 1.0
 * @since 1.0
 */
public interface PlayableMorseUnit {
    public void play(OnOff stateSetter, Delayer delayer, MorseTiming standard) throws Exception;
}
