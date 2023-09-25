package agh.ics.oop.project1.Elements.Gen;

import agh.ics.oop.project1.Elements.Mutation.AbstractMutations;
import agh.ics.oop.project1.Elements.Mutation.AbstractMutationsFactory;
import agh.ics.oop.project1.Elements.Mutation.FullRandGen;
import agh.ics.oop.project1.Elements.Mutation.LightCorrectionGen;
import agh.ics.oop.project1.Elements.Vector2d;
import agh.ics.oop.project1.Random.RandomNumber;

abstract public class AbstractGenes {
    protected Integer[] Genes;
    private final String strGenes;
    protected int activeGen=0;
    protected final int len;
    private AbstractMutations mutation;

    //Constructors

    //For Starting Animals
    public AbstractGenes(int len, String typeM, Vector2d minMaxMutation){
        this.len=len;
        this.Genes= new Integer[len];
        this.activeGen=RandomNumber.RandomNum(0,len-1);
        this.generateRandomGenes();
        this.mutation= AbstractMutationsFactory.getAbstractMutations(typeM,minMaxMutation);
        this.strGenes=tabToString(this.Genes);
    }

    //For New Animals
    public AbstractGenes(Integer[] mGenTab, Integer[] fGenTab, int mEnergy, int fEnergy, String typeM, Vector2d minMaxMutation){

        this.mutation= AbstractMutationsFactory.getAbstractMutations(typeM,minMaxMutation);
        this.len=mGenTab.length;
        this.Genes= new Integer[this.len];
        this.activeGen=RandomNumber.RandomNum(0,len-1);


        double percentM=mEnergy/(mEnergy+fEnergy);

        int partOfGenM=(int)Math.floor(this.len*percentM);
        int partOfGenF=this.len-partOfGenM;

        //left or right
        int side=RandomNumber.RandomNum(0,1);

        //rewrite to child gen
        if(side==0){
            for(int i=0;i<partOfGenF;i++){
                this.Genes[i]=fGenTab[i];
            }
            for(int i=partOfGenF;i<this.len;i++){
                this.Genes[i]=mGenTab[i];
            }
        }
        else{
            for(int i=0;i<partOfGenM;i++){
                this.Genes[i]=mGenTab[i];
            }
            for(int i=partOfGenM;i<this.len;i++){
                this.Genes[i]=fGenTab[i];
            }
        }

        //mutations
        this.mutation.mutationsOfGenome(this.Genes);
        this.strGenes=tabToString(this.Genes);
    }
    private String tabToString(Integer[] tab){
        String str="";
        for(Integer val:tab){
            str+=""+val;
        }
        return str;
    }

    //abstract for options
    abstract public int getActiveGen();

    private void generateRandomGenes(){
        for(int i=0;i<this.len;i++){
            this.Genes[i]=RandomNumber.RandomNum(0,7);
        }
    }

    //Getters
    public Integer[] getGenes(){
        return this.Genes;
    }
    public String getStrGenes(){
         return this.strGenes;
    }
    public int getCurrentGene(){
        return this.Genes[this.activeGen%this.len];
    }
}
