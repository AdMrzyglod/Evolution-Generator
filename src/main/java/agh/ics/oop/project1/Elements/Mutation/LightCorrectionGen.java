package agh.ics.oop.project1.Elements.Mutation;

import agh.ics.oop.project1.Elements.Mutation.AbstractMutations;
import agh.ics.oop.project1.Elements.Vector2d;
import agh.ics.oop.project1.Random.RandomNumber;

public class LightCorrectionGen extends AbstractMutations {

    public LightCorrectionGen(Vector2d mutations) {
        super(mutations);
    }

    @Override
    protected int randGen(int actualGen) {
        int rGen= RandomNumber.RandomNum(0,1);
        if(rGen==0){
            rGen=-1;
        }
        actualGen+=rGen;
        if(actualGen<0 || actualGen>7){
            actualGen=(8-Math.abs(actualGen));
        }
        return actualGen;
    }
}
