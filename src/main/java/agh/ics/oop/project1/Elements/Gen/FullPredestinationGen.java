package agh.ics.oop.project1.Elements.Gen;

import agh.ics.oop.project1.Elements.Vector2d;

public class FullPredestinationGen extends AbstractGenes {


    public FullPredestinationGen(int len, String typeM, Vector2d minMaxMutation){
        super(len,typeM,minMaxMutation);
    }
    public FullPredestinationGen(Integer[] mGenTab,Integer[] fGenTab,int mEnergy,int fEnergy,String typeM, Vector2d minMaxMutation){
        super(mGenTab,fGenTab,mEnergy,fEnergy,typeM,minMaxMutation);
    }

    @Override
    public int getActiveGen(){
        int gen= super.Genes[super.activeGen%super.len];
        super.activeGen+=1;
        return gen;
    }
}
