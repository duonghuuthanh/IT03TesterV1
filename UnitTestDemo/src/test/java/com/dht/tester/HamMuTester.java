package com.dht.tester;


import com.dht.services.HamMuService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author admin
 */
public class HamMuTester {
    @Test
    public void testMuDuong1() {
        double x = 2;
        int n = 0;
        double expected = 1;
        double actual = HamMuService.Power(x, n);
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    public void testMuDuong2() {
        double x = -2;
        int n = 2;
        double expected = 4;
        double actual = HamMuService.Power(x, n);
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    public void testMuDuong3() {
        double x = -2;
        int n = 3;
        double expected = -8;
        double actual = HamMuService.Power(x, n);
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    public void testMuDuong4() {
        double x = 2;
        int n = -1;
        double expected = 0.5;
        double actual = HamMuService.Power(x, n);
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    public void testMuDuong5() {
        double x = -2;
        int n = -1;
        double expected = -0.5;
        double actual = HamMuService.Power(x, n);
        Assertions.assertEquals(expected, actual);
    }
    
    @Test
    public void testMuDuong6() {
        double x = 99999;
        int n = 9999999;
        double expected = Math.pow(x, n);
        double actual = HamMuService.Power(x, n);
        Assertions.assertEquals(expected, actual);
    }
}
