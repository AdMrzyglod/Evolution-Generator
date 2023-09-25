package agh.ics.oop.project1.Elements;
import agh.ics.oop.project1.Elements.Gen.AbstractGenesFactory;
import agh.ics.oop.project1.Elements.Gen.FullPredestinationGen;
import agh.ics.oop.project1.Elements.Gen.AbstractGenes;
import agh.ics.oop.project1.Elements.Gen.RandomGen;
import agh.ics.oop.project1.Enum.MapDirection;
import agh.ics.oop.project1.Maps.AbstractWorldMap;
import agh.ics.oop.project1.Interfaces.IPositionChangeObserver;
import agh.ics.oop.project1.Random.RandomNumber;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Animal {

     private int animalEnergy;
     private int daysOfLive=0;
     private int numberOfChild=0;
     private int numberOfGrassEaten=0;
     private int deathDay=-1;
     private MapDirection direction;
     private Vector2d position;
     private AbstractWorldMap map;
     private List <IPositionChangeObserver> observers= new ArrayList<>();
     public AbstractGenes genes;

     //Constructors

    public Animal(AbstractWorldMap map, Vector2d initialPosition,int animalEnergy){
        this.map=map;
        this.direction=MapDirection.NORTH.rotate(RandomNumber.RandomNum(1,8));
        this.position=initialPosition;
        this.animalEnergy=animalEnergy;
        this.genes= AbstractGenesFactory.getAbstractGenes(this.map.getAnimalsBehaviorVariant(),this.map.getAnimalGenomeLength(),this.map.getMutationVariant(),this.map.getMinMaxMutations());
    }
    public Animal(AbstractWorldMap map, Vector2d initialPosition,int animalEnergy,
                  Integer[] mGenTab,Integer[] fGenTab,int mEnergy,int fEnergy){
        this.map=map;
        this.direction=MapDirection.NORTH.rotate(RandomNumber.RandomNum(1,8));
        this.position=initialPosition;
        this.animalEnergy=animalEnergy;
        this.genes=AbstractGenesFactory.getAbstractGenes(this.map.getAnimalsBehaviorVariant(),mGenTab,fGenTab,mEnergy,fEnergy,this.map.getMutationVariant(),this.map.getMinMaxMutations());
    }

    public MapDirection getDirection(){
        return this.direction;
    }
    public Vector2d getPosition(){
         return this.position;
    }
    private void setDirection(MapDirection direct){
         this.direction=direct;
    }
    private void setPosition(Vector2d pos){
         this.position=pos;
    }


    public boolean isAt(Vector2d position){
        return this.getPosition().equals(position);
    }

    //Move
    public void move(int rotateNum){
        //change direction
        this.setDirection(this.getDirection().rotate(rotateNum));

        Vector2d newPosition= this.getPosition().add(this.getDirection().toUnitVector());

        //Ok
        if(!this.map.isOutOfMap(newPosition)){
            this.positionChanged(this.getPosition(),newPosition);
            this.setPosition(newPosition);
            return;
        }

        //get correct position
        Vector2d correctPosition=this.map.getCorrectPosition(newPosition);
        if(correctPosition!=null){
            this.positionChanged(this.getPosition(),correctPosition);
            this.setPosition(correctPosition);
            if(this.map.isLoseEnergy()){
                this.animalEnergy-=this.map.getEnergyLostPerDay();
            }
        }
        //else change direction
        else if(this.map.getCorrectDirection(this.getDirection())!=null){
                this.setDirection(this.map.getCorrectDirection(this.getDirection()));
            }
    }

    //Observer
    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }
    public void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }
    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){
         for(IPositionChangeObserver observer: this.observers){
              observer.positionChanged(this,oldPosition,newPosition);
         }
    }

    //Setters
    private void decreaseEnergy(int Energy){
        this.animalEnergy-=Energy;
        if(this.animalEnergy<0){
            this.animalEnergy=0;
        }
    }
    public void decreaseEnergy(){
        this.animalEnergy-=this.map.getEnergyLostPerDay();
        if(this.animalEnergy<0){
            this.animalEnergy=0;
        }
    }
    public void increaseEnergy(){
        this.animalEnergy+=this.map.getEnergyFromOneGrass();
        this.increaseGrass();
    }
    private void increaseChild(){
        this.numberOfChild+=1;
    }
    public void increaseDays(){
        this.daysOfLive+=1;
    }
    public boolean checkEnergy(){
        return this.animalEnergy>0;
    }
    private void increaseGrass(){
        this.numberOfGrassEaten+=1;
    }
    public void setDeathDay(){
        this.deathDay=this.map.getDay();
    }

    //Copulation
    public Animal copulation(Animal mother){

        Animal child= new Animal(this.map,this.position,this.map.getEnergyWastedOnNewAnimal(),mother.genes.getGenes(),this.genes.getGenes(),mother.animalEnergy,this.animalEnergy);

        int loseEnergy=(int)Math.floor(this.map.getEnergyWastedOnNewAnimal()*0.5);
        mother.decreaseEnergy(loseEnergy);
        this.decreaseEnergy(this.map.getEnergyWastedOnNewAnimal()-loseEnergy);
        this.increaseChild();
        mother.increaseChild();
        return child;
    }

    //Paint
    public Color toPaint(){
        int startEnergy= this.map.getStartingAnimalEnergy();
        if(this.animalEnergy<=0){return Color.rgb(224,224,224);}
        if(this.animalEnergy<0.25*startEnergy){return Color.rgb(192,192,192);}
        if(this.animalEnergy<0.5*startEnergy){return Color.rgb(160,160,160);}
        if(this.animalEnergy<0.75*startEnergy){return Color.rgb(128,128,128);}
        if(this.animalEnergy<startEnergy){return Color.rgb(96,96,96);}
        if(this.animalEnergy<2*startEnergy){return Color.rgb(64,64,64);}
        if(this.animalEnergy<5*startEnergy){return Color.rgb(32,32,32);}
        if(this.animalEnergy>=5*startEnergy){return Color.rgb(0,0,0);}
        return null;
    }

    //Getters
    public int getDaysOfLive(){
        return this.daysOfLive;
    }
    public int getAnimalEnergy(){
        return this.animalEnergy;
    }
    public int getNumberOfChild(){
        return this.numberOfChild;
    }
    public int getNumberOfGrassEaten(){
        return this.numberOfGrassEaten;
    }
    public int getDeathDay(){
        return this.deathDay;
    }
}
