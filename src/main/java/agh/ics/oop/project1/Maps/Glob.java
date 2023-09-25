package agh.ics.oop.project1.Maps;

import agh.ics.oop.project1.Elements.Vector2d;
import agh.ics.oop.project1.Enum.MapDirection;

public class Glob extends AbstractWorldMap{


    public Glob(int width, int height, int EnergyFromOneGrass, int NumberOfGrassPerDay, int StartingNumberOfGrass, String GrassGrowthVariant, int StartingNumberOfAnimals, int StartingAnimalEnergy, int EnergyNeededForCopulation, int EnergyWastedOnNewAnimal, Vector2d MinMaxMutations, int AnimalGenomeLength, String AnimalsBehaviorVariant, String MutationVariant, int EnergyLostPerDay) {
        super(width, height, EnergyFromOneGrass, NumberOfGrassPerDay, StartingNumberOfGrass, GrassGrowthVariant, StartingNumberOfAnimals, StartingAnimalEnergy, EnergyNeededForCopulation, EnergyWastedOnNewAnimal, MinMaxMutations, AnimalGenomeLength, AnimalsBehaviorVariant, MutationVariant, EnergyLostPerDay);
    }

    @Override
    public Vector2d getCorrectPosition(Vector2d newPosition) {
        //if vector beyond top or bottom return null
        if((isOutOfLeftOrRight(newPosition) && isOutOfLowOrHigh(newPosition)) || isOutOfLowOrHigh(newPosition)){
            return null;
        }
        //else looping of the right and left edges
        return new Vector2d(width-Math.abs(newPosition.x),newPosition.y);
    }

    //vector beyond top or bottom, change direction to opposite
    @Override
    public MapDirection getCorrectDirection(MapDirection direction) {
        return direction.rotate(4);
    }

    //does not lose energy
    @Override
    public boolean isLoseEnergy() {
        return false;
    }

    boolean isOutOfLowOrHigh(Vector2d position){
        return position.y>=this.height || position.y<0;
    }
    boolean isOutOfLeftOrRight(Vector2d position){
        return position.x>=this.width || position.y<0;
    }
}
