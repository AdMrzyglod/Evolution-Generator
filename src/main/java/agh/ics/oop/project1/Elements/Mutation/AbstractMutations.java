package agh.ics.oop.project1.Elements.Mutation;

import agh.ics.oop.project1.Elements.Vector2d;
import agh.ics.oop.project1.Random.RandomNumber;

abstract public class AbstractMutations {
    private Vector2d MinMaxMutations;

    //Constructor
    public AbstractMutations(Vector2d mutations){
        this.MinMaxMutations=mutations;
    }

    //abstract for options
    abstract protected int randGen(int actualGen);

    //mutation
    public void mutationsOfGenome(Integer[] genome){
        int numberOfMutations= RandomNumber.RandomNum(this.MinMaxMutations.x,this.MinMaxMutations.y);
        boolean[] choosePlace=new boolean[genome.length];

        int counter=0;
        while(counter!=numberOfMutations){
            int randNum=RandomNumber.RandomNum(0,genome.length-1);
            if(!choosePlace[randNum]){
                choosePlace[randNum]=true;
                counter+=1;
            }
        }

        for(int i=0;i<genome.length;i++){
            if(choosePlace[i]){
                genome[i]=this.randGen(genome[i]);
            }
        }

    }
}
