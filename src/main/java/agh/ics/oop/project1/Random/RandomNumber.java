package agh.ics.oop.project1.Random;

import java.util.Random;

public class RandomNumber {

    public static int RandomNum(int min, int max) {
        return new Random().nextInt(max - min+1) + min;
    }
}
