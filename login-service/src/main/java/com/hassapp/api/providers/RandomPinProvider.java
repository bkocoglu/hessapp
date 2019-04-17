package com.hassapp.api.providers;

import com.hassapp.api.enums.RandomType;

import java.util.Random;

public class RandomPinProvider {
    private static final String[] KEYS =
            {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o",
                    "p","r","s","t","u","v","y","z","0","1","2","3","4","5","6","7","8","9"};

    private static Random rnd = new Random();

    public static String createRandomPin(RandomType randomType, int length){
        String pin = "";

        if (randomType == RandomType.NICNAME)
            pin = "@hs-";
        else if (randomType == RandomType.PASSWORD || randomType == RandomType.ACTIVATIONCODE)
            pin = "";

        for(int i = 0; i<length; i++){
            int index = rnd.nextInt(KEYS.length);

            pin += KEYS[index];
        }

        return pin;
    }

}
