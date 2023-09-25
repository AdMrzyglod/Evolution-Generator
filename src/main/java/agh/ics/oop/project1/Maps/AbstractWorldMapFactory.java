package agh.ics.oop.project1.Maps;

import agh.ics.oop.project1.Elements.Vector2d;

public class AbstractWorldMapFactory {

    public static AbstractWorldMap getAbstractWorldMap(String type, int width, int height, int EnergyFromOneGrass, int NumberOfGrassPerDay, int StartingNumberOfGrass, String GrassGrowthVariant, int StartingNumberOfAnimals,
                                                       int StartingAnimalEnergy, int EnergyNeededForCopulation, int EnergyWastedOnNewAnimal, Vector2d MinMaxMutations, int AnimalGenomeLength, String AnimalsBehaviorVariant,
                                                       String MutationVariant, int EnergyLostPerDay){
        if("Glob".equals(type)){
            return new Glob(width,height, EnergyFromOneGrass,NumberOfGrassPerDay,StartingNumberOfGrass,GrassGrowthVariant,
                    StartingNumberOfAnimals,StartingAnimalEnergy,EnergyNeededForCopulation,EnergyWastedOnNewAnimal,MinMaxMutations
                    ,AnimalGenomeLength,AnimalsBehaviorVariant,MutationVariant,EnergyLostPerDay);
        }
        if("HellPortal".equals(type)){
            return new HellPortal(width,height, EnergyFromOneGrass,NumberOfGrassPerDay,StartingNumberOfGrass,GrassGrowthVariant,
                    StartingNumberOfAnimals,StartingAnimalEnergy,EnergyNeededForCopulation,EnergyWastedOnNewAnimal,MinMaxMutations
                    ,AnimalGenomeLength,AnimalsBehaviorVariant,MutationVariant,EnergyLostPerDay);
        }
        return null;
    }
}
