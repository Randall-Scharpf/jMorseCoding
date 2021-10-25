/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randallscharpf.java.jmorsecoding.base.standards;

import com.randallscharpf.java.jmorsecoding.base.codesets.MorseSymbolSet;
import com.randallscharpf.java.jmorsecoding.base.timings.MorseTiming;

public interface MorseStandard {
    public MorseTiming getTimingSpecification();
    public MorseSymbolSet getSymbolSpecification();
}
