package agh.ics.oop.project1.Elements.Gen;

import agh.ics.oop.project1.Elements.Vector2d;
import agh.ics.oop.project1.Random.RandomNumber;

public class RandomGen extends AbstractGenes {

    public RandomGen(int len, String typeM, Vector2d minMaxMutation){
        super(len,typeM,minMaxMutation);
    }
    public RandomGen(Integer[] mGenTab,Integer[] fGenTab,int mEnergy,int fEnergy,String typeM, Vector2d minMaxMutation){
        super(mGenTab,fGenTab,mEnergy,fEnergy,typeM,minMaxMutation);
    }
    @Override
    public int getActiveGen(){
        int gen= super.Genes[super.activeGen%super.len];

        if(RandomNumber.RandomNum(1,100)<=80){
            super.activeGen+=1;
        }
        else{
            int randNum=RandomNumber.RandomNum(0,super.len-1);
            while(randNum==super.activeGen+1%super.len && super.len>1){
                randNum=RandomNumber.RandomNum(0,super.len-1);
            }
            super.activeGen=randNum;
        }

        return gen;
    }
}
