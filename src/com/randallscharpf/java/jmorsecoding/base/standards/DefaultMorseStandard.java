/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randallscharpf.java.jmorsecoding.base.standards;

import com.randallscharpf.java.jmorsecoding.base.codesets.MorseSymbolSet;
import com.randallscharpf.java.jmorsecoding.base.timings.MorseTiming;

/**
 *
 * @author Randall
 */
public class DefaultMorseStandard implements MorseStandard {
    
    public MorseTiming timing;
    private final MorseSymbolSet symbols;
    
    public DefaultMorseStandard(MorseTiming timing, MorseSymbolSet symbols) {
        this.timing = timing;
        this.symbols = symbols;
    }

    @Override
    public MorseTiming getTimingSpecification() {
        return timing;
    }

    @Override
    public MorseSymbolSet getSymbolSpecification() {
        return symbols;
    }
}
