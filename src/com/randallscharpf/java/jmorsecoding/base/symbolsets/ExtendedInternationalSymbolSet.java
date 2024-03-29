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

import com.randallscharpf.java.jmorsecoding.base.morseunits.Element;
import com.randallscharpf.java.jmorsecoding.base.morseunits.ElementType;
import static com.randallscharpf.java.jmorsecoding.base.morseunits.ElementType.DASH;
import static com.randallscharpf.java.jmorsecoding.base.morseunits.ElementType.DOT;
import com.randallscharpf.java.jmorsecoding.base.playerinterfaces.Delayer;
import com.randallscharpf.java.jmorsecoding.base.timings.MorseTiming;
import com.randallscharpf.java.jmorsecoding.base.playerinterfaces.OnOff;
import java.util.ArrayList;
import java.util.List;

/**
 * A symbol set that attempts to be able to convert any valid Unicode string to the
 * Morse code sequence that corresponds to that string according to general consensus.
 * General consensus is not a formal standard, but it can be a goal nonetheless, and
 * so it is the goal of this implementation. Some characters and prosigns, therefore,
 * may be misinterpreted by some in their Morse translations, but this is considered
 * a superior choice to the choice of no translation at all.
 * @version 1.0
 * @since 1.0
 */
public final class ExtendedInternationalSymbolSet extends SymbolSetBase {

    /**
     * Creates a symbol set using international Morse Code with non-standard extensions.
     * @version 1.0
     * @since 1.0
     */
    public ExtendedInternationalSymbolSet() {
        // no functionality required, simply for documentation
    }

    /**
     * {@inheritDoc}
     * @version 1.0
     * @since 1.0
     */
    @Override
    protected Class<? extends Enum<? extends MorseSymbol>> getSymbolEnumClass() {
        return ExtendedInternationalSymbol.class;
    }
    
    /* package-private */ static enum ExtendedInternationalSymbol implements MorseSymbol {
        LETTER_A('a', DOT, DASH),
        SPECIAL_LETTER_A_GRAVE('\u00E0', DOT, DASH, DASH, DOT, DASH),
        SPECIAL_LETTER_A_DOTS('\u00E4', DOT, DASH, DOT, DASH),
        SPECIAL_LETTER_A_CIRCLE('\u00E5', DOT, DASH, DASH, DOT, DASH),
        SPECIAL_LETTER_A_HOOK('\u0105', DOT, DASH, DOT, DASH),
        SPECIAL_LETTER_AE('\u00E6', DOT, DASH, DOT, DASH),
        LETTER_B('b', DASH, DOT, DOT, DOT),
        LETTER_C('c', DASH, DOT, DASH, DOT),
        SPECIAL_LETTER_C_ACUTE('\u0107', DASH, DOT, DASH, DOT, DOT),
        SPECIAL_LETTER_C_HAT('\u0109', DASH, DOT, DASH, DOT, DOT),
        SPECIAL_LETTER_C_HOOK('\u00E7', DASH, DOT, DASH, DOT, DOT),
        SPECIAL_LETTER_CH('\u010D', DASH, DASH, DASH, DASH),
        LETTER_D('d', DASH, DOT, DOT),
        SPECIAL_LETTER_D_STROKED('\u0111', DOT, DOT, DASH, DOT, DOT),
        SPECIAL_LETTER_ETH('\u00F0', DOT, DOT, DASH, DASH, DOT),
        LETTER_E('e', DOT),
        SPECIAL_LETTER_E_ACUTE('\u00E9', DOT, DOT, DASH, DOT, DOT),
        SPECIAL_LETTER_E_GRAVE('\u00E8', DOT, DASH, DOT, DOT, DASH),
        SPECIAL_LETTER_E_HOOK('\u0119', DOT, DOT, DASH, DOT, DOT),
        LETTER_F('f', DOT, DOT, DASH, DOT),
        LETTER_G('g', DASH, DASH, DOT),
        SPECIAL_LETTER_G_HAT('\u011D', DASH, DASH, DOT, DASH, DOT),
        LETTER_H('h', DOT, DOT, DOT, DOT),
        SPECIAL_LETTER_H_HAT('\u0125', DASH, DASH, DASH, DASH),
        LETTER_I('i', DOT, DOT),
        LETTER_J('j', DOT, DASH, DASH, DASH),
        SPECIAL_LETTER_J_HAT('\u0135', DOT, DASH, DASH, DASH, DOT),
        LETTER_K('k', DASH, DOT, DASH),
        LETTER_L('l', DOT, DASH, DOT, DOT),
        SPECIAL_LETTER_L_STROKED('\u0142', DOT, DASH, DOT, DOT, DASH),
        LETTER_M('m', DASH, DASH),
        LETTER_N('n', DASH, DOT),
        SPECIAL_LETTER_N_ACUTE('\u0144', DASH, DASH, DOT, DASH, DASH),
        SPECIAL_LETTER_N_TILDE('\u00F1', DASH, DASH, DOT, DASH, DASH),
        LETTER_O('o', DASH, DASH, DASH),
        SPECIAL_LETTER_O_ACUTE('\u00F3', DASH, DASH, DASH, DOT),
        SPECIAL_LETTER_O_DOTS('\u00F6', DASH, DASH, DASH, DOT),
        SPECIAL_LETTER_O_STROKED('\u00F8', DASH, DASH, DASH, DOT),
        LETTER_P('p', DOT, DASH, DASH, DOT),
        LETTER_Q('q', DASH, DASH, DOT, DASH),
        LETTER_R('r', DOT, DASH, DOT),
        LETTER_S('s', DOT, DOT, DOT),
        SPECIAL_LETTER_S_ACUTE('\u015B', DOT, DOT, DOT, DASH, DOT, DOT, DOT),
        SPECIAL_LETTER_S_HAT('\u015D', DOT, DOT, DOT, DASH, DOT),
        SPECIAL_LETTER_SH('\u0161', DASH, DASH, DASH, DASH),
        LETTER_T('t', DASH),
        SPECIAL_LETTER_THORN('\u00FE', DOT, DASH, DASH, DOT, DOT),
        LETTER_U('u', DOT, DOT, DASH),
        SPECIAL_LETTER_U_DOTS('\u00FC', DOT, DOT, DASH, DASH),
        SPECIAL_LETTER_U_BREVE('\u016D', DOT, DOT, DASH, DASH),
        LETTER_V('v', DOT, DOT, DOT, DASH),
        LETTER_W('w', DOT, DASH, DASH),
        LETTER_X('x', DASH, DOT, DOT, DASH),
        LETTER_Y('y', DASH, DOT, DASH, DASH),
        LETTER_Z('z', DASH, DASH, DOT, DOT),
        SPECIAL_LETTER_Z_ACUTE('\u017A', DASH, DASH, DOT, DOT, DASH, DOT),
        SPECIAL_LETTER_Z_DOT('\u017C', DASH, DASH, DOT, DOT, DASH),
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
        PUNCTUATION_EXCLAMATIONPOINT('!', DASH, DOT, DASH, DOT, DASH, DASH),
        PUNCTUATION_FORWARDSLASH('/', DASH, DOT, DOT, DASH, DOT),
        PUNCTUATION_OPENPARENTHESIS('(', DASH, DOT, DASH, DASH, DOT),
        PUNCTUATION_CLOSEPARENTHESIS(')', DASH, DOT, DASH, DASH, DOT, DASH),
        PUNCTUATION_AMPERSAND('&', DOT, DASH, DOT, DOT, DOT),
        PUNCTUATION_COLON(':', DASH, DASH, DASH, DOT, DOT, DOT),
        PUNCTUATION_SEMICOLON(';', DASH, DOT, DASH, DOT, DASH, DOT),
        PUNCTUATION_DOUBLEHYPHEN('\u2E40', DASH, DOT, DOT, DOT, DASH),
        PUNCTUATION_PLUSSIGN('+', DOT, DASH, DOT, DASH, DOT),
        PUNCTUATION_HYPHEN('-', DASH, DOT, DOT, DOT, DOT, DASH),
        PUNCTUATION_MINUSSIGN('\u2212', DASH, DOT, DOT, DOT, DOT, DASH),
        PUNCTUATION_UNDERSCORE('_', DOT, DOT, DASH, DASH, DOT, DASH),
        PUNCTUATION_QUOTATIONMARK('"', DOT, DASH, DOT, DOT, DASH, DOT),
        PUNCTUATION_DOLLARSIGN('$', DOT, DOT, DOT, DASH, DOT, DOT, DASH),
        PUNCTUATION_ATSIGN('@', DOT, DASH, DASH, DOT, DASH, DOT),
        PROSIGN_DOUBLEHYPHEN("double hyphen", DASH, DOT, DOT, DOT, DASH),
        PROSIGN_END("end", DOT, DOT, DOT, DASH, DOT, DASH),
        PROSIGN_ERROR("error", DOT, DOT, DOT, DOT, DOT, DOT, DOT, DOT),
        PROSIGN_CROSS("cross", DOT, DASH, DOT, DASH, DOT),
        PROSIGN_INVITATIONTOTRANSMIT("invitation to transmit", DASH, DOT, DASH),
        PROSIGN_STARTINGSIGNAL("starting signal", DASH, DOT, DASH, DOT, DASH),
        PROSIGN_NEWPAGE("new page", DOT, DASH, DOT, DASH, DOT),
        PROSIGN_UNDERSTOOD("understood", DOT, DOT, DOT, DASH, DOT),
        PROSIGN_WAIT("wait", DOT, DASH, DOT, DOT, DOT);
        
        private final CharOrProsign cop;
        private final Element[] elements;
        
        private ExtendedInternationalSymbol(char myChar, ElementType... elementTypes) {
            this(new CharOrProsign(myChar), elementTypes);
        }
        
        private ExtendedInternationalSymbol(String myProsign, ElementType... elementTypes) {
            this(new CharOrProsign(myProsign), elementTypes);
        }
        
        private ExtendedInternationalSymbol(CharOrProsign cop, ElementType... elementTypes) {
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
