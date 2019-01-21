package com.alexscode.bdma.hadoop;

import java.util.Random;

public class Gen {
    private static Random rd = new Random();

    public String randomName(int len){
        char[] name = new char[len];
        name[0] = (char) (65 + rd.nextInt(26));
        for(int i = 1; i < name.length; i++) {
            name[i] = (char) ((char) rd.nextInt(26) + 97);
        }
        return new String(name);
    }
}
