/*
 * The MIT License
 *
 * Copyright (c) 2023 Randall Scharpf
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.randallscharpf.java.jmorsecoding.base.symbolsets;

import com.randallscharpf.java.jmorsecoding.base.playerinterfaces.Delayer;
import com.randallscharpf.java.jmorsecoding.base.morseunits.Element;
import com.randallscharpf.java.jmorsecoding.base.morseunits.ElementType;
import com.randallscharpf.java.jmorsecoding.base.playerinterfaces.OnOff;
import com.randallscharpf.java.jmorsecoding.base.morseunits.PlayableMorseUnit;
import com.randallscharpf.java.jmorsecoding.base.timings.MorseTiming;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A concrete implementation of a {@link MorseSymbolSet} implemented using an
 * <code>enum</code>. Subclasses can, by linking to the appropriate <code>enum</code>
 * class, use the sequencing and lookup implementations provided by this class.
 * <br>
 * This class is most efficient when the linked <code>enum</code> class never changes.
 * Each time an implemented method is called on a new class, a cache is internally
 * built in O(S) time, where S is the number of elements in the <code>enum</code>
 * linked. The total size of stored caches is also O(S). Subsequent operations are
 * much faster and at most linear in the size of the input to the function.
 * @version 1.0
 * @since 1.0
 */
public abstract class SymbolSetBase implements MorseSymbolSet {
    
    /**
     * A character or prosign that can be played as Morse Code. A symbol set that
     * uses the utilities in <code>SymbolSetBase</code> is designed to contain a
     * set of instances of this type.
     */
    protected static interface MorseSymbol extends PlayableMorseUnit {
        /**
         * Gives the character or prosign represented by this symbol. That is, when
         * this unit is played, the translation referencing it should interpret those
         * dits and dahs as the symbol given here.
         * @return the written translation of the symbol
         */
        public CharOrProsign getCharOrProsign();
    }
    
    /**
     * Either a character or a prosign. No instance of this class may be both a
     * character and a prosign. Instances of this class are immutable.
     */
    public static class CharOrProsign {
        
        private final boolean isChar;
        private final char myChar;
        private final String myProsign;
        
        /**
         * Makes an instance that contains a character and no prosign.
         * @param myChar the character to be contained
         */
        public CharOrProsign(char myChar) {
            this.isChar = true;
            this.myChar = myChar;
            this.myProsign = null;
        }
        
        /**
         * Makes an instance that contains a prosign and no character.
         * @param myProsign the name of the prosign to be contained
         */
        public CharOrProsign(String myProsign) {
            this.isChar = false;
            this.myChar = '\0';
            this.myProsign = myProsign;
        }

        /**
         * Tells the type of data held by this instance.
         * @return true if and only if the data held is a character
         */
        public boolean isChar() {
            return isChar;
        }
        
        /**
         * Gives the character held by this instance. If this instance holds no
         * character and instead holds a prosign, <code>null</code> is returned.
         * @return the character held by this instance, or null if it is a prosign
         */
        public Character getChar() {
            if (isChar) {
                return myChar;
            } else {
                return null;
            }
        }
        
        /**
         * Gives the name of the prosign held by this instance. If this instance holds no
         * prosign and instead holds a character, <code>null</code> is returned.
         * @return the name of the prosign held by this instance, or null if it is a character
         */
        public String getProsign() {
            if (isChar) {
                return null;
            } else {
                return myProsign;
            }
        }
    }
    
    /**
     * Gives the class of an enumeration containing all the symbols that can be
     * translated by this set. Includes all translatable prosigns and characters.
     * The enumeration class must implement the {@link MorseSymbol} interface.
     * <br>
     * Implementing this method associates the implementations in this class
     * (<code>SymbolSetBase</code>) with the data in the subclass while allowing
     * subclasses to package their data in the form of an enumeration: the implicit
     * superclass of Java's enumerations prevents the subclass itself from holding
     * the data in this way. This method is not intended to be used outside of this
     * class (<code>SymbolSetBase</code>).
     * @since 1.0
     * @return the <code>enum</code> of symbols that this set supports translating
     */
    protected abstract Class<? extends Enum<? extends MorseSymbol>> getSymbolEnumClass();

    private Class<? extends Enum<? extends MorseSymbol>> cacheEnumClass;
    private String[] cachedResult1 = null;
    private Map<String, PlayableMorseUnit> cachedResult2 = null;
    private Map<Character, PlayableMorseUnit> cachedResult3 = null;

    /**
     * {@inheritDoc}
     * @version 1.0
     * @since 1.0
     */
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

    /**
     * {@inheritDoc}
     * @version 1.0
     * @since 1.0
     */
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

    /**
     * {@inheritDoc}
     * @version 1.0
     * @since 1.0
     */
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
    
    /**
     * Maps characters that should be considered the same to some common character.
     * For example, Morse Code does not distinguish between capital and lowercase
     * letters, so the characters <code>'a'</code> and <code>'A'</code> should be
     * mapped to some character X which is the same for each of them. In addition,
     * the symbol set must be configured to map the character X to the appropriate
     * sets of dots and dashes for both <code>'a'</code> and <code>'A'</code>.
     * The default implementation of this function simply converts the character
     * to lowercase, but subclasses should override the function if the translation
     * they use implies a different equivalence relation among characters (where
     * two characters are equivalent in the relation if they are represented by the
     * same set of dots and dashes).
     * @param c a character for which to determine an equivalence class
     * @return the character from the equivalence class of the input in the symbol set
     */
    protected char standardizeChar(char c) {
        c = Character.toLowerCase(c);
        return c;
    }
    
    /**
     * Maps prosign names that should be considered the same to some common string.
     * For example, the prosigns <code>"STARTING SIGNAL"</code> and <code>
     * "starting signal"</code> are probably the same, so they should be mapped to
     * some string X which is the same for each of them. In addition, the symbol
     * set must be configured to map the string X to the appropriate sets of dots
     * and dashes for that starting signal prosign. The default implementation of
     * this function simply converts the string to lowercase, but subclasses should
     * override the function if the translation they use implies a different equivalence
     * relation among strings (where two strings are equivalent in the relation if
     * they name the same prosign).
     * @param str a string for which to determine an equivalence class
     * @return the string from the equivalence class of the input in the symbol set
     */
    protected String standardizeString(String str) {
        str = str.toLowerCase();
        return str;
    }
}
