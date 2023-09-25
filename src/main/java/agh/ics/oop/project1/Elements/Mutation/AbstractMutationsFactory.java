package agh.ics.oop.project1.Elements.Mutation;


import agh.ics.oop.project1.Elements.Vector2d;

public class AbstractMutationsFactory{

    public static AbstractMutations getAbstractMutations(String type, Vector2d mutations){
        if("FullRandGen".equals(type)){
            return new FullRandGen(mutations);
        }
        if("LightCorrectionGen".equals(type)){
            return new LightCorrectionGen(mutations);
        }
        return null;
    }
}
