/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.services;

/**
 *
 * @author admin
 */
public class BMIService {
    public static int tinhBmi(double chieuCao, double canNang) {
        double d = canNang/Math.pow(chieuCao, 2);
        if (d < 18.5)
            return 1;
        else if (d < 25)
            return 2;
        return 3;
    }
}
