/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randallscharpf.java.jmorsecoding.base.morseunits;

import com.randallscharpf.java.jmorsecoding.base.standards.Delayer;
import com.randallscharpf.java.jmorsecoding.base.standards.MorseStandard;
import com.randallscharpf.java.jmorsecoding.base.standards.OnOff;

public class Element implements PlayableMorseUnit {
    
    private final ElementType type;
    
    public Element(ElementType type) {
        this.type = type;
    }

    @Override
    public void play(OnOff stateSetter, Delayer delayer, MorseStandard morseStandard) throws Exception {
        stateSetter.setActive(type.activeDuringPlay);
        delayer.wait(
                morseStandard.getTimingSpecification().timeForElementType(type)
        );
    }
    
}
