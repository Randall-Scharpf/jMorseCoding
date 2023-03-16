/*
 * The MIT License
 *
 * Copyright 2023 Randall.
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
package com.randallscharpf.java.jmorsecoding.base;

import com.randallscharpf.java.jmorsecoding.base.codesets.ExtendedInternationalSymbolSet;
import com.randallscharpf.java.jmorsecoding.base.codesets.InternationalSymbolSet;
import com.randallscharpf.java.jmorsecoding.base.codesets.MorseSymbolSet;
import com.randallscharpf.java.jmorsecoding.base.codesets.SymbolSetBase;
import com.randallscharpf.java.jmorsecoding.base.timings.FarnsworthTiming;
import junit.framework.TestCase;

/**
 *
 * @author Randall
 */
public class SymbolSetTest extends TestCase {

    public SymbolSetTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testCharOrProsign() {
        SymbolSetBase.CharOrProsign c = new SymbolSetBase.CharOrProsign('x');
        assertEquals(Character.valueOf('x'), c.getChar());
        assertEquals(null, c.getProsign());
        assertEquals(true, c.isChar());
        SymbolSetBase.CharOrProsign s = new SymbolSetBase.CharOrProsign("x");
        assertEquals(null, s.getChar());
        assertEquals("x", s.getProsign());
        assertEquals(false, s.isChar());
    }
    
    // For symbol sets, high unit test coverage is not required, because the majority of the
    // information in the classes is enumerative rather than procedural. Tests simply
    // spot-check the methods exposed by the symbol sets.
    
    public void testExtendedIntlSymbolSet() {
        ExtendedInternationalSymbolSet s = new ExtendedInternationalSymbolSet();
        checkWords(s);
        checkStartingSignal(s);
        for (String str : s.getAvailableProsigns()) {
            if (str.equals("end")) {
                return;
            }
        }
        fail("Set does not contain an end of work prosign!");
    }
    
    public void testItuR2009SymbolSet() {
        InternationalSymbolSet s = new InternationalSymbolSet();
        checkWords(s);
        checkStartingSignal(s);
        for (String str : s.getAvailableProsigns()) {
            if (str.equals("end")) {
                return;
            }
        }
        fail("Set does not contain an end of work prosign!");
    }
    
    private void checkWords(MorseSymbolSet set) {
        try {
            StringBuilder b = new StringBuilder();
            set.getPlayableForString("PARIS").play((active) -> {
                b.append(active + "\n");
            }, ((time) -> {
                b.append("Wait " + time + "\n");
            }), new FarnsworthTiming(20, 40));
            assertEquals(
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.09S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.09S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.326842105S\n" +
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.09S\n" +
                    "false\nWait PT0.326842105S\n" +
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.09S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.326842105S\n" +
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.326842105S\n" +
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.76263158S\n",
            b.toString());
            b.delete(0, Integer.MAX_VALUE);
            set.getPlayableForString("CODEX").play((active) -> {
                b.append(active + "\n");
            }, ((time) -> {
                b.append("Wait " + time + "\n");
            }), new FarnsworthTiming(20, 40));
            assertEquals(
                    "true\nWait PT0.09S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.09S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.326842105S\n" +
                    "true\nWait PT0.09S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.09S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.09S\n" +
                    "false\nWait PT0.326842105S\n" +
                    "true\nWait PT0.09S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.326842105S\n" +
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.326842105S\n" +
                    "true\nWait PT0.09S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.09S\n" +
                    "false\nWait PT0.76263158S\n",
            b.toString());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    private void checkStartingSignal(MorseSymbolSet set) {
        try {
            StringBuilder b = new StringBuilder();
            set.getPlayableForProsign("starting signal").play((active) -> {
                b.append(active + "\n");
            }, ((time) -> {
                b.append("Wait " + time + "\n");
            }), new FarnsworthTiming(20, 40));
            assertEquals(
                    "true\nWait PT0.09S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.09S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.03S\n" +
                    "false\nWait PT0.03S\n" +
                    "true\nWait PT0.09S\n" +
                    "false\nWait PT0.76263158S\n",
            b.toString());
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
}
