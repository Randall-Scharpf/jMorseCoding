/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randallscharpf.java.jmorsecoding.base.standards;

import com.randallscharpf.java.jmorsecoding.base.codesets.*;
import com.randallscharpf.java.jmorsecoding.base.timings.MorseTiming;
import com.randallscharpf.java.jmorsecoding.base.timings.StandardTiming;

public enum BuiltinMorseStandard implements MorseStandard {
    ITU_R_M1677_1_2009(new InternationalSymbolSet(), new StandardTiming()),
    EXTENDED_INTERNATIONAL(new ExtendedInternationalSymbolSet(), new StandardTiming()),
    ;

    public MorseTiming timingSpec;
    public final MorseSymbolSet symbolSet;
    
    BuiltinMorseStandard(MorseSymbolSet symbolSet, MorseTiming timingSpec) {
        this.timingSpec = timingSpec;
        this.symbolSet = symbolSet;
    }

    @Override
    public MorseTiming getTimingSpecification() {
        return timingSpec;
    }

    @Override
    public MorseSymbolSet getSymbolSpecification() {
        return symbolSet;
    }   
}
