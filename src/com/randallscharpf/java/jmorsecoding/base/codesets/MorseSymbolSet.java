/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randallscharpf.java.jmorsecoding.base.codesets;

import com.randallscharpf.java.jmorsecoding.base.morseunits.PlayableMorseUnit;

public interface MorseSymbolSet {
    public PlayableMorseUnit getPlayableForString(String str);
    public PlayableMorseUnit getPlayableForProsign(String prosignName);
    public String[] getAvailableProsigns();
}
