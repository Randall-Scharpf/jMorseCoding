/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randallscharpf.java.jmorsecoding.base.timings;

import com.randallscharpf.java.jmorsecoding.base.morseunits.ElementType;
import java.time.Duration;

@FunctionalInterface
public interface MorseTiming {
    public Duration timeForElementType(ElementType type);
}
