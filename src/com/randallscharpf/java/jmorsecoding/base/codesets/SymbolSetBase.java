/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randallscharpf.java.jmorsecoding.base.codesets;

import com.randallscharpf.java.jmorsecoding.base.standards.Delayer;
import com.randallscharpf.java.jmorsecoding.base.morseunits.Element;
import com.randallscharpf.java.jmorsecoding.base.morseunits.ElementType;
import com.randallscharpf.java.jmorsecoding.base.standards.OnOff;
import com.randallscharpf.java.jmorsecoding.base.morseunits.PlayableMorseUnit;
import com.randallscharpf.java.jmorsecoding.base.timings.MorseTiming;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SymbolSetBase implements MorseSymbolSet {
    
    public static interface MorseSymbol extends PlayableMorseUnit {
        public CharOrProsign getCharOrProsign();
    }
    
    public static class CharOrProsign {
        
        private final boolean isChar;
        private final char myChar;
        private final String myProsign;
        
        public CharOrProsign(char myChar) {
            this.isChar = true;
            this.myChar = myChar;
            this.myProsign = null;
        }
        
        public CharOrProsign(String myProsign) {
            this.isChar = false;
            this.myChar = '\0';
            this.myProsign = myProsign;
        }

        public boolean isChar() {
            return isChar;
        }
        
        public Character getChar() {
            if (isChar) {
                return myChar;
            } else {
                return null;
            }
        }
        
        public String getProsign() {
            if (isChar) {
                return null;
            } else {
                return myProsign;
            }
        }
    }
    public abstract Class<? extends Enum<? extends MorseSymbol>> getSymbolEnumClass();

    private Class<? extends Enum<? extends MorseSymbol>> cacheEnumClass;
    private String[] cachedResult1 = null;
    private Map<String, PlayableMorseUnit> cachedResult2 = null;
    private Map<Character, PlayableMorseUnit> cachedResult3 = null;

    @Override
    public String[] getAvailableProsigns() {
        Class<? extends Enum<? extends MorseSymbol>> c = getSymbolEnumClass();
        if (c == cacheEnumClass && cachedResult1 != null) {
            return cachedResult1;
        }
        MorseSymbol[] symbols = c.getEnumConstants()[0].getDeclaringClass().getEnumConstants();
        int count = 0;
        for (MorseSymbol symbol : symbols) {
            if (!symbol.getCharOrProsign().isChar()) {
                count++;
            }
        }
        cachedResult1 = new String[count];
        int i = 0;
        for (MorseSymbol symbol : symbols) {
            if (!symbol.getCharOrProsign().isChar()) {
                cachedResult1[i] = symbol.getCharOrProsign().getProsign();
                i++;
            }
        }
        if (c != cacheEnumClass) {
            cacheEnumClass = c;
            cachedResult2 = null;
            cachedResult3 = null;
        }
        return cachedResult1;
    }

    @Override
    public PlayableMorseUnit getPlayableForProsign(String prosignName) {
        Class<? extends Enum<? extends MorseSymbol>> c = getSymbolEnumClass();
        if (c == cacheEnumClass && cachedResult2 != null) {
            return cachedResult2.get(standardizeString(prosignName));
        }
        cachedResult2 = new HashMap<>();
        MorseSymbol[] symbols = c.getEnumConstants()[0].getDeclaringClass().getEnumConstants();
        for (MorseSymbol symbol : symbols) {
            if (!symbol.getCharOrProsign().isChar()) {
                cachedResult2.put(standardizeString(symbol.getCharOrProsign().getProsign()), symbol);
            }
        }
        if (c != cacheEnumClass) {
            cacheEnumClass = c;
            cachedResult1 = null;
            cachedResult3 = null;
        }
        PlayableMorseUnit p = cachedResult2.get(standardizeString(prosignName));
        if (p == null) {
            throw new IllegalArgumentException("No morse encoding for the prosign "
                    + prosignName + " can be found in the current character set!");
        }
        return p;
    }

    @Override
    public PlayableMorseUnit getPlayableForString(String str) {
        Class<? extends Enum<? extends MorseSymbol>> c = getSymbolEnumClass();
        if (c == cacheEnumClass && cachedResult3 != null) {
            return buildStringFromChars(cachedResult3, str);
        }
        cachedResult3 = new HashMap<>();
        MorseSymbol[] symbols = c.getEnumConstants()[0].getDeclaringClass().getEnumConstants();
        for (MorseSymbol symbol : symbols) {
            if (symbol.getCharOrProsign().isChar()) {
                cachedResult3.put(standardizeChar(symbol.getCharOrProsign().getChar()), symbol);
            }
        }
        if (c != cacheEnumClass) {
            cacheEnumClass = c;
            cachedResult1 = null;
            cachedResult2 = null;
        }
        return buildStringFromChars(cachedResult3, str);
    }
    
    private PlayableMorseUnit buildStringFromChars(Map<Character, PlayableMorseUnit> lookup, String str) {
        List<PlayableMorseUnit> playables = new ArrayList<>(str.length());
        boolean needsGap = false;
        for (Character c : str.toCharArray()) {
            if (Character.isWhitespace(c)) {
                playables.add(new Element(ElementType.WORD_GAP));
                needsGap = false;
            } else {
                if (needsGap) {
                    playables.add(new Element(ElementType.LETTER_GAP));
                }
                PlayableMorseUnit p = lookup.get(standardizeChar(c));
                if (p == null) {
                    throw new IllegalArgumentException("No morse encoding for the character "
                            + c + " can be found in the current character set!");
                }
                playables.add(p);
                needsGap = true;
            }
        }
        if (needsGap) {
            playables.add(new Element(ElementType.WORD_GAP));
        }
        return (OnOff stateSetter, Delayer delayer, MorseTiming standard) -> {
            for (PlayableMorseUnit p : playables) {
                p.play(stateSetter, delayer, standard);
            }
        };
    }
    
    protected char standardizeChar(char c) {
        c = Character.toLowerCase(c);
        return c;
    }
    
    protected String standardizeString(String str) {
        str = str.toLowerCase();
        return str;
    }
}
