/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randallscharpf.java.jmorsecoding.base.morseunits;

import com.randallscharpf.java.jmorsecoding.base.standards.Delayer;
import com.randallscharpf.java.jmorsecoding.base.standards.OnOff;
import com.randallscharpf.java.jmorsecoding.base.timings.MorseTiming;

public class Element implements PlayableMorseUnit {
    
    private final ElementType type;
    
    public Element(ElementType type) {
        this.type = type;
    }

    @Override
    public void play(OnOff stateSetter, Delayer delayer, MorseTiming morseStandard) throws Exception {
        stateSetter.setActive(type.activeDuringPlay);
        delayer.wait(
                morseStandard.timeForElementType(type)
        );
    }
    
}
