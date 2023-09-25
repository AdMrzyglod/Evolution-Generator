package agh.ics.oop.project1.fileclasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.System.out;

public class DataLoader {

    private Properties prop;

    //Constructor

    public DataLoader(String configFilePath) throws IOException {
        InputStream propInputFile = new FileInputStream(new File("src/main/resources/agh/ics/oop/project1/fileclasses/variants").getAbsolutePath()+"/"+configFilePath);
        this.prop= new Properties();
        this.prop.load(propInputFile);
    }


    //DATA GETTERS
    public String getMapWidth(){
        return this.prop.getProperty("MapWidth");
    }
    public String getMapHeight(){
        return this.prop.getProperty("MapHeight");
    }
    public String getMapVariant(){
        return this.prop.getProperty("MapVariant");
    }
    public String getStartingNumberOfAnimals(){
        return this.prop.getProperty("StartingNumberOfAnimals");
    }
    public String getStatingAnimalEnergy(){
        return this.prop.getProperty("StatingAnimalEnergy");
    }
    public String getEnergyLostPerDay(){
        return this.prop.getProperty("EnergyLostPerDay");
    }
    public String getEnergyNeededForCopulation(){
        return this.prop.getProperty("EnergyNeededForCopulation");
    }
    public String getEnergyWastedOnNewAnimal(){
        return this.prop.getProperty("EnergyWastedOnNewAnimal");
    }
    public String getAnimalGenomeLength(){
        return this.prop.getProperty("AnimalGenomeLength");
    }
    public String getMutationVariant(){
        return this.prop.getProperty("MutationVariant");
    }
    public String getMinNumberOfMutationsInNewAnimal(){
        return this.prop.getProperty("MinNumberOfMutationsInNewAnimal");
    }
    public String getMaxNumberOfMutationsInNewAnimal(){
        return this.prop.getProperty("MaxNumberOfMutationsInNewAnimal");
    }
    public String getAnimalsBehaviorVariant(){
        return this.prop.getProperty("AnimalsBehaviorVariant");
    }
    public String getStartingNumberOfGrass(){
        return this.prop.getProperty("StartingNumberOfGrass");
    }
    public String getEnergyFromOneGrass(){
        return this.prop.getProperty("EnergyFromOneGrass");
    }
    public String getNumberOfGrassPerDay(){
        return this.prop.getProperty("NumberOfGrassPerDay");
    }
    public String getGrassGrowthVariant(){
        return this.prop.getProperty("GrassGrowthVariant");
    }
    public String getRefreshTime() {
        return this.prop.getProperty("RefreshTime");
    }

}
