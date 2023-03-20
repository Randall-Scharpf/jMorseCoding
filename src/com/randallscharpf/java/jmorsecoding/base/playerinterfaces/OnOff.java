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
package com.randallscharpf.java.jmorsecoding.base.playerinterfaces;

/**
 * Something that can be turned on and off. Expected to have a visible effect resulting
 * in state transition between two predetermined and distinguishable states. Used
 * to produce Morse Code by setting the state to "on" when and only when a dot or
 * a dash is being played.
 * @since 1.0
 */
@FunctionalInterface
public interface OnOff {
    /**
     * Sets the state of the device. The active state is the state referred to as
     * "on" and the inactive state is the state referred to as "off".
     * @since 1.0
     * @param active true if and only if the device should be in the "on" state
     * @throws Exception if the change of state is unable to be enacted
     */
    public void setActive(boolean active) throws Exception;
}
