/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randallscharpf.java.jmorsecoding.base.standards;

import java.time.Duration;

public interface Delayer {
    public void wait(Duration time) throws Exception;
}
