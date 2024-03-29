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
import com.randallscharpf.java.jmorsecoding.base.timings.MorseTiming;
import com.randallscharpf.java.jmorsecoding.base.playerinterfaces.OnOff;
import java.util.ArrayList;
import java.util.List;

import static com.randallscharpf.java.jmorsecoding.base.morseunits.ElementType.DASH;
import static com.randallscharpf.java.jmorsecoding.base.morseunits.ElementType.DOT;

/**
 * A symbol set that follows the conventions established by ITU-R 2009 standards. See
 * <a href="https://www.itu.int/dms_pubrec/itu-r/rec/m/R-REC-M.1677-1-200910-I!!PDF-E.pdf">
 * https://www.itu.int/dms_pubrec/itu-r/rec/m/R-REC-M.1677-1-200910-I!!PDF-E.pdf</a>
 */
public final class InternationalSymbolSet extends SymbolSetBase {
    
    /**
     * Creates a symbol set using standard international Morse Code. Uses the ITU-R
     * 2009 standards document to translate characters and prosigns to dots, dashes,
     * and gaps.
     * @version 1.0
     * @since 1.0
     */
    public InternationalSymbolSet() {
        // no functionality required, simply for documentation
    }
    
    /**
     * {@inheritDoc}
     * @version 1.0
     * @since 1.0
     */
    @Override
    protected Class<? extends Enum<? extends MorseSymbol>> getSymbolEnumClass() {
        return InternationalSymbol.class;
    }
    
    /* package-private */ enum InternationalSymbol implements MorseSymbol {
        LETTER_A('a', DOT, DASH),
        LETTER_B('b', DASH, DOT, DOT, DOT),
        LETTER_C('c', DASH, DOT, DASH, DOT),
        LETTER_D('d', DASH, DOT, DOT),
        LETTER_E('e', DOT),
        SPECIAL_LETTER_E_ACCENT('\u00E9', DOT, DOT, DASH, DOT, DOT),
        LETTER_F('f', DOT, DOT, DASH, DOT),
        LETTER_G('g', DASH, DASH, DOT),
        LETTER_H('h', DOT, DOT, DOT, DOT),
        LETTER_I('i', DOT, DOT),
        LETTER_J('j', DOT, DASH, DASH, DASH),
        LETTER_K('k', DASH, DOT, DASH),
        LETTER_L('l', DOT, DASH, DOT, DOT),
        LETTER_M('m', DASH, DASH),
        LETTER_N('n', DASH, DOT),
        LETTER_O('o', DASH, DASH, DASH),
        LETTER_P('p', DOT, DASH, DASH, DOT),
        LETTER_Q('q', DASH, DASH, DOT, DASH),
        LETTER_R('r', DOT, DASH, DOT),
        LETTER_S('s', DOT, DOT, DOT),
        LETTER_T('t', DASH),
        LETTER_U('u', DOT, DOT, DASH),
        LETTER_V('v', DOT, DOT, DOT, DASH),
        LETTER_W('w', DOT, DASH, DASH),
        LETTER_X('x', DASH, DOT, DOT, DASH),
        LETTER_Y('y', DASH, DOT, DASH, DASH),
        LETTER_Z('z', DASH, DASH, DOT, DOT),
        DIGIT_0('0', DASH, DASH, DASH, DASH, DASH),
        DIGIT_1('1', DOT, DASH, DASH, DASH, DASH),
        DIGIT_2('2', DOT, DOT, DASH, DASH, DASH),
        DIGIT_3('3', DOT, DOT, DOT, DASH, DASH),
        DIGIT_4('4', DOT, DOT, DOT, DOT, DASH),
        DIGIT_5('5', DOT, DOT, DOT, DOT, DOT),
        DIGIT_6('6', DASH, DOT, DOT, DOT, DOT),
        DIGIT_7('7', DASH, DASH, DOT, DOT, DOT),
        DIGIT_8('8', DASH, DASH, DASH, DOT, DOT),
        DIGIT_9('9', DASH, DASH, DASH, DASH, DOT),
        PUNCTUATION_PERIOD('.', DOT, DASH, DOT, DASH, DOT, DASH),
        PUNCTUATION_COMMA(',', DASH, DASH, DOT, DOT, DASH, DASH),
        PUNCTUATION_QUESTIONMARK('?', DOT, DOT, DASH, DASH, DOT, DOT),
        PUNCTUATION_APOSTROPHE('\'', DOT, DASH, DASH, DASH, DASH, DOT),
        PUNCTUATION_FORWARDSLASH('/', DASH, DOT, DOT, DASH, DOT),
        PUNCTUATION_OPENPARENTHESIS('(', DASH, DOT, DASH, DASH, DOT),
        PUNCTUATION_CLOSEPARENTHESIS(')', DASH, DOT, DASH, DASH, DOT, DASH),
        PUNCTUATION_COLON(':', DASH, DASH, DASH, DOT, DOT, DOT),
        PUNCTUATION_PLUSSIGN('+', DOT, DASH, DOT, DASH, DOT),
        PUNCTUATION_HYPHENMINUS('-', DASH, DOT, DOT, DOT, DOT, DASH),
        PUNCTUATION_QUOTATIONMARK('?', DOT, DASH, DOT, DOT, DASH, DOT),
        PUNCTUATION_ATSIGN('@', DOT, DASH, DASH, DOT, DASH, DOT),
        PUNCTUATION_TIMESSIGN('x', DASH, DOT, DOT, DASH),
        PUNCTUATION_PERCENT('%', // better to type "0/0" directly
                DASH, DASH, DASH, DASH, DASH, ElementType.LETTER_GAP,
                DASH, DOT, DOT, DASH, DOT, ElementType.LETTER_GAP,
                DASH, DASH, DASH, DASH, DASH
        ),
        PUNCTUATION_PERMILLE('\u2030', // better to type "0/00" directly
                DASH, DASH, DASH, DASH, DASH, ElementType.LETTER_GAP,
                DASH, DOT, DOT, DASH, DOT, ElementType.LETTER_GAP,
                DASH, DASH, DASH, DASH, DASH, ElementType.LETTER_GAP,
                DASH, DASH, DASH, DASH, DASH
        ),
        PUNCTUATION_MINUTES('\u2032', DOT, DASH, DASH, DASH, DASH, DOT), // better to use one apostrophe directly
        PUNCTUATION_SECONDS('\u2033', // better to use two apostrophes directly (do NOT use the quotation mark)
                DOT, DASH, DASH, DASH, DASH, DOT, ElementType.LETTER_GAP,
                DOT, DASH, DASH, DASH, DASH, DOT
        ),
        PROSIGN_DOUBLEHYPHEN("double hyphen", DASH, DOT, DOT, DOT, DASH),
        PROSIGN_END("end", DOT, DOT, DOT, DASH, DOT, DASH),
        PROSIGN_ERROR("error", DOT, DOT, DOT, DOT, DOT, DOT, DOT, DOT),
        PROSIGN_CROSS("cross", DOT, DASH, DOT, DASH, DOT),
        PROSIGN_INVITATIONTOTRANSMIT("invitation to transmit", DASH, DOT, DASH),
        PROSIGN_STARTINGSIGNAL("starting signal", DASH, DOT, DASH, DOT, DASH),
        PROSIGN_UNDERSTOOD("understood", DOT, DOT, DOT, DASH, DOT),
        PROSIGN_WAIT("wait", DOT, DASH, DOT, DOT, DOT);
        
        private final CharOrProsign cop;
        private final Element[] elements;
        
        private InternationalSymbol(char myChar, ElementType... elementTypes) {
            this(new CharOrProsign(myChar), elementTypes);
        }
        
        private InternationalSymbol(String myProsign, ElementType... elementTypes) {
            this(new CharOrProsign(myProsign), elementTypes);
        }
        
        private InternationalSymbol(CharOrProsign cop, ElementType... elementTypes) {
            this.cop = cop;
            List<Element> elementsTmp = new ArrayList<>(elementTypes.length * 2 - 1);
            elementsTmp.add(new Element(elementTypes[0]));
            for (int i = 1; i < elementTypes.length; i++) {
                if (elementTypes[i].activeDuringPlay && elementTypes[i-1].activeDuringPlay) {
                    elementsTmp.add(new Element(ElementType.ELEMENT_GAP));
                }
                elementsTmp.add(new Element(elementTypes[i]));
            }
            if (!cop.isChar()) {
                elementsTmp.add(new Element(ElementType.WORD_GAP));
            }
            this.elements = elementsTmp.toArray(new Element[0]);
        }
        
        /**
         * {@inheritDoc}
         * @version 1.0
         * @since 1.0
         */
        @Override
        public void play(OnOff stateSetter, Delayer delayer, MorseTiming standard) throws Exception {
            for (Element e : elements) {
                e.play(stateSetter, delayer, standard);
            }
        }

        /**
         * {@inheritDoc}
         * @version 1.0
         * @since 1.0
         */
        @Override
        public CharOrProsign getCharOrProsign() {
            return cop;
        }
    }
}
