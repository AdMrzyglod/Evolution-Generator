package agh.ics.oop.project1.Maps;

import agh.ics.oop.project1.Elements.Vector2d;
import agh.ics.oop.project1.Enum.MapDirection;
import agh.ics.oop.project1.Random.RandomNumber;

public class HellPortal extends AbstractWorldMap{


    public HellPortal(int width, int height, int EnergyFromOneGrass, int NumberOfGrassPerDay, int StartingNumberOfGrass, String GrassGrowthVariant, int StartingNumberOfAnimals, int StartingAnimalEnergy, int EnergyNeededForCopulation, int EnergyWastedOnNewAnimal, Vector2d MinMaxMutations, int AnimalGenomeLength, String AnimalsBehaviorVariant, String MutationVariant, int EnergyLostPerDay) {
        super(width, height, EnergyFromOneGrass, NumberOfGrassPerDay, StartingNumberOfGrass, GrassGrowthVariant, StartingNumberOfAnimals, StartingAnimalEnergy, EnergyNeededForCopulation, EnergyWastedOnNewAnimal, MinMaxMutations, AnimalGenomeLength, AnimalsBehaviorVariant, MutationVariant, EnergyLostPerDay);
    }

    //IF ANIMAL BEYOND MAP , NEW POSITION IS RANDOM
    @Override
    public Vector2d getCorrectPosition(Vector2d newPosition) {
        return new Vector2d(RandomNumber.RandomNum(0,this.width-1),RandomNumber.RandomNum(0,this.height-1));
    }

    //NO CHANGE OF DIRECTION
    @Override
    public MapDirection getCorrectDirection(MapDirection direction) {
        return null;
    }

    //LOSSES ENERGY
    @Override
    public boolean isLoseEnergy() {
        return true;
    }

}
