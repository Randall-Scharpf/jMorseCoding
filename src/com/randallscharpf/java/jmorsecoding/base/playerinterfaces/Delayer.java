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

import java.time.Duration;

/**
 * A means of waiting. In some contexts, spinning or sleeping the current thread
 * is an appropriate choice; in other contexts, it makes more sense to merely indicate
 * to a scheduler or device the required timing. Implementations may choose to use
 * the most situationally efficient code, the most situationally accurate code, or
 * some tradeoff between these potential extremes.
 * @since 1.0
 */
@FunctionalInterface
public interface Delayer {
    /**
     * Waits for some amount of time. This method may block until the waiting is
     * completed or indicate to some other thread or some device that the waiting
     * should occur and then return immediately.
     * @since 1.0
     * @param time a <code>Duration</code> giving the amount of time to wait
     * @throws Exception if the waiting is unable to be demanded or completed
     */
    public void wait(Duration time) throws Exception;
}
