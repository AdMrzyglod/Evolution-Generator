package agh.ics.oop.project1.Elements.Gen;

import agh.ics.oop.project1.Elements.Mutation.AbstractMutations;
import agh.ics.oop.project1.Elements.Mutation.FullRandGen;
import agh.ics.oop.project1.Elements.Mutation.LightCorrectionGen;
import agh.ics.oop.project1.Elements.Vector2d;

public class AbstractGenesFactory{

    public static AbstractGenes getAbstractGenes(String type, int len, String typeM, Vector2d minMaxMutation){
        if("FullPredestinationGen".equals(type)){
            return new FullPredestinationGen(len,typeM,minMaxMutation);
        }
        if("RandomGen".equals(type)){
            return new RandomGen(len,typeM,minMaxMutation);
        }
        return null;
    }
    public static AbstractGenes getAbstractGenes(String type, Integer[] mGenTab, Integer[] fGenTab, int mEnergy, int fEnergy, String typeM, Vector2d minMaxMutation){
        if("FullPredestinationGen".equals(type)){
            return new FullPredestinationGen(mGenTab,fGenTab,mEnergy,fEnergy,typeM,minMaxMutation);
        }
        if("RandomGen".equals(type)){
            return new RandomGen(mGenTab,fGenTab,mEnergy,fEnergy,typeM,minMaxMutation);
        }
        return null;
    }
}
