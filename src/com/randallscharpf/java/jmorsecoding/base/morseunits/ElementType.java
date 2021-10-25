/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randallscharpf.java.jmorsecoding.base.morseunits;

public enum ElementType {
    DOT(true),
    DASH(true),
    ELEMENT_GAP(false),
    LETTER_GAP(false),
    WORD_GAP(false);
    public final boolean activeDuringPlay;
    private ElementType(boolean on) {
        this.activeDuringPlay = on;
    }
}
