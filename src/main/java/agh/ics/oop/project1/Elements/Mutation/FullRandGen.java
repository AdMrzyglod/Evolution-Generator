package agh.ics.oop.project1.Elements.Mutation;

import agh.ics.oop.project1.Elements.Mutation.AbstractMutations;
import agh.ics.oop.project1.Elements.Vector2d;
import agh.ics.oop.project1.Random.RandomNumber;

public class FullRandGen extends AbstractMutations {
    public FullRandGen(Vector2d mutations) {
        super(mutations);
    }

    @Override
    protected int randGen(int actualGen) {
        int rGen= RandomNumber.RandomNum(0,7);
        while(actualGen==rGen){
            rGen=RandomNumber.RandomNum(0,7);
        }
        return rGen;
    }
}
