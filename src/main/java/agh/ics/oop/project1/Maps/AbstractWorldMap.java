package agh.ics.oop.project1.Maps;

import agh.ics.oop.project1.Elements.Animal;
import agh.ics.oop.project1.Elements.Grass;
import agh.ics.oop.project1.Elements.Vector2d;
import agh.ics.oop.project1.Enum.MapDirection;
import agh.ics.oop.project1.Interfaces.IPositionChangeObserver;
import agh.ics.oop.project1.Random.RandomNumber;

import java.util.*;

import static java.lang.System.out;

//MAP
public abstract class AbstractWorldMap implements  IPositionChangeObserver {

    //Map Dimensions
    protected final int width;
    protected final int height;

    //Grass Info
    private int EnergyFromOneGrass;
    private int NumberOfGrassPerDay;
    private int StartingNumberOfGrass;
    private AbstractGrassGenerator GrassGrowthVariant;

    //Animal Info
    private int StartingNumberOfAnimals;
    private int StartingAnimalEnergy;
    private int EnergyNeededForCopulation;
    private int EnergyWastedOnNewAnimal;
    private Vector2d MinMaxMutations;
    private int AnimalGenomeLength;
    private String AnimalsBehaviorVariant;
    private String MutationVariant;
    private int EnergyLostPerDay;
    private int NumberOfGrassOnMap=0;
    private int sumDaysOfDeathAnimals=0;
    private int numberOfDeathAnimals=0;
    private int numberOfDays=0;


    //MAP DATA
    protected Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private List<Animal> listOfAnimals= new ArrayList<>();
    protected Map<Vector2d, Grass> grassH = new HashMap<>();
    protected Map<Vector2d,Integer[]> deathAnimals;
    private Map<String,Integer> genomeHashMap = new HashMap<>();
    private Map<Vector2d,Integer> occupiedFields = new HashMap<>();


    //Constructor

    protected AbstractWorldMap(int width, int height,int EnergyFromOneGrass,int NumberOfGrassPerDay,int StartingNumberOfGrass,String GrassGrowthVariant,int StartingNumberOfAnimals,
                               int StartingAnimalEnergy,int EnergyNeededForCopulation,int EnergyWastedOnNewAnimal,Vector2d MinMaxMutations,int AnimalGenomeLength,String AnimalsBehaviorVariant
                               ,String MutationVariant,int EnergyLostPerDay){
        this.width=width;
        this.height=height;
        this.EnergyFromOneGrass=EnergyFromOneGrass;
        this.NumberOfGrassPerDay=NumberOfGrassPerDay;
        this.StartingNumberOfGrass=StartingNumberOfGrass;
        this.StartingNumberOfAnimals=StartingNumberOfAnimals;
        this.StartingAnimalEnergy=StartingAnimalEnergy;
        this.EnergyNeededForCopulation=EnergyNeededForCopulation;
        this.EnergyWastedOnNewAnimal=EnergyWastedOnNewAnimal;
        this.MinMaxMutations=MinMaxMutations;
        this.AnimalGenomeLength=AnimalGenomeLength;
        this.AnimalsBehaviorVariant=AnimalsBehaviorVariant;
        this.MutationVariant=MutationVariant;
        this.EnergyLostPerDay=EnergyLostPerDay;
        this.GrassGrowthVariant=AbstractGrassGeneratorFactory.getAbstractGrassGenerator(GrassGrowthVariant,width,height,this);

        //SET GRASS AND ANIMALS INITIALLY

        this.GrassGrowthVariant.generateGrass(this.StartingNumberOfGrass);
        this.generateAnimals();

    }
    public boolean isOutOfMap(Vector2d position){
        return position.x<0 || position.x>=this.width || position.y<0 || position.y>=this.height;
    }

    //ABSTRACT FOR MAP VARIANTS
    abstract public Vector2d getCorrectPosition(Vector2d newPosition);
    abstract public MapDirection getCorrectDirection(MapDirection direction);
    abstract public boolean isLoseEnergy();


    //ADD REMOVE ANIMALS
    private void addAnimal(Animal animal){
        if(this.animals.get(animal.getPosition())==null){
            this.animals.put(animal.getPosition(),new ArrayList<>());
        }
        this.animals.get(animal.getPosition()).add(animal);
        this.checkOccupiedFields(animal.getPosition());
    }
    private void addAnimal(Animal animal, Vector2d pos){
        if(this.animals.get(pos)==null){
            this.animals.put(pos,new ArrayList<>());
        }
        this.animals.get(pos).add(animal);
        this.checkOccupiedFields(pos);
    }

    private void removeAnimal(Animal animal){
        if(this.animals.get(animal.getPosition())==null){
            return;
        }
        this.animals.get(animal.getPosition()).remove(animal);
        this.checkOccupiedFields(animal.getPosition());
    }
    private void removeAnimal(Animal animal, Vector2d pos){
        if(this.animals.get(pos)==null){
            return;
        }
        this.animals.get(pos).remove(animal);
        this.checkOccupiedFields(pos);
    }

    //PLACE FIRST TIME
    private boolean place(Animal animal) {
        if(!this.isOutOfMap(animal.getPosition())){
            this.addAnimal(animal);
            this.listOfAnimals.add(animal);
            this.addGenome(animal.genes.getStrGenes());
            animal.addObserver((IPositionChangeObserver) this);
            this.checkOccupiedFields(animal.getPosition());
            return true;
        }
        throw new IllegalArgumentException("The field " + animal.getPosition() + " is wrong");
    }
    //OBJECT ON POSITION
    public Object objectAt(Vector2d position) {
        if(this.animals.get(position)!=null && this.animals.get(position).size()>0){
            return this.chooseAnimalToEat(this.animals.get(position));
        }
        else if(this.grassH.get(position)!=null){
            return this.grassH.get(position);
        }
        return null;
    }


    //OBSERVER
    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition){
         removeAnimal(animal,oldPosition);
         addAnimal(animal,newPosition);
    }


    //SIMULATION CONTROL
    public void moveAllAnimals(){

            for(Animal animal: this.listOfAnimals){
                animal.move(animal.genes.getActiveGen());
            }

    }
    public void newDayLoseAll(){
        //FOR EACH ANIMAL INCREASE DAYS DECREASE ENERGY
        for(List<Animal> listAnimal: this.animals.values()){
            if(listAnimal==null){continue;}
            for(Animal animal: listAnimal){
                animal.increaseDays();
                animal.decreaseEnergy();
            }
        }
        this.numberOfDays+=1;
    }

    public void deleteDeathAnimals(){
        List<Animal> deathsTmp=new ArrayList<>();
            for(Animal animal: this.listOfAnimals){
                //ENERGY LESS THAN ZERO
                if(!animal.checkEnergy()){
                    deathsTmp.add(animal);
                    this.deathAnimalsStatistic(animal.getDaysOfLive());
                }
            }
        for(Animal animal:deathsTmp){
            removeAnimal(animal);
            this.listOfAnimals.remove(animal);
            this.removeGenome(animal.genes.getStrGenes());
            animal.setDeathDay();

            //MAP OPTION
            if(this.deathAnimals!=null){

                //ON POSITION INCREASE NUMBER OF DEATH ANIMALS
                this.deathAnimals.get(animal.getPosition())[2] += 1;
            }
        }
    }
    public void grassEating(){
        List <Vector2d> toRemoveGrass=new ArrayList<>();

        //FOR EACH GRASS CHOOSE ANIMAL IN THE SAME POSITION
        for(Vector2d pos: this.grassH.keySet()){
            List <Animal> listAnimals=this.animals.get(pos);
            if(listAnimals==null || listAnimals.size()==0){
                continue;
            }
            if(listAnimals.size()==1){
                listAnimals.get(0).increaseEnergy();
                toRemoveGrass.add(pos);
            }
            else{
                Animal chosenAnimal=this.chooseAnimalToEat(listAnimals);
                chosenAnimal.increaseEnergy();
                toRemoveGrass.add(pos);
            }
        }

        //REMOVE EATEN GRASS
        for(Vector2d grass: toRemoveGrass){
            this.grassH.remove(grass);
            this.changeNumberOfGrass(-1);
            this.checkOccupiedFields(grass);
        }
    }

    public void copulation(){
        for(List<Animal> listAnimal: this.animals.values()){
            if(listAnimal==null || listAnimal.size()<2){
                continue;
            }
            //CHOOSE ANIMALS
            Animal[] fatherMother=this.chooseAnimalToCop(listAnimal);
            Animal father=fatherMother[0];
            Animal mother=fatherMother[1];

            //IF THEY MEET THE CONDITIONS
            if(mother.getAnimalEnergy()>=this.EnergyNeededForCopulation && father.getAnimalEnergy()>=this.EnergyNeededForCopulation){

                //COPULATION
                Animal newAnimal=father.copulation(mother);
                this.place(newAnimal);
            }
        }
    }

    private void generateAnimals(){
        for(int i=0;i<this.StartingNumberOfAnimals;i++){
            Vector2d randPos=new Vector2d(RandomNumber.RandomNum(0,this.width-1),RandomNumber.RandomNum(0,this.height-1));
            this.place(new Animal(this,randPos,this.StartingAnimalEnergy));
        }
    }
    public void generateGrassOnMap(){
        this.GrassGrowthVariant.generateGrass(this.getNumberOfGrassPerDay());
    }


    //CHOOSE THE BEST ANIMAL/ANIMALS
    private Animal chooseAnimalToEat(List <Animal> animalList){
        animalList.sort(new Comparator<Animal>() {
            @Override
            public int compare(Animal o1, Animal o2) {
                if(o1.getAnimalEnergy()!=o2.getAnimalEnergy()){
                    return o2.getAnimalEnergy()-o1.getAnimalEnergy();
                }
                else if(o1.getDaysOfLive()!=o2.getDaysOfLive()){
                    return o2.getDaysOfLive()-o1.getDaysOfLive();
                }
                return o2.getNumberOfChild()-o1.getNumberOfChild();
            }
        });
        return animalList.get(0);
    }
    private Animal[] chooseAnimalToCop(List <Animal> animalList){
        animalList.sort(new Comparator<Animal>() {
            @Override
            public int compare(Animal o1, Animal o2) {
                if(o1.getAnimalEnergy()!=o2.getAnimalEnergy()){
                    return o2.getAnimalEnergy()-o1.getAnimalEnergy();
                }
                else if(o1.getDaysOfLive()!=o2.getDaysOfLive()){
                    return o2.getDaysOfLive()-o1.getDaysOfLive();
                }
                return o2.getNumberOfChild()-o1.getNumberOfChild();
            }
        });
        Animal[] animalTab= {animalList.get(0),animalList.get(1)};

        return animalTab;
    }

    //OTHERS
    protected void changeNumberOfGrass(int value){
        this.NumberOfGrassOnMap+=value;
    }


    //UPDATE FOR STATISTICS

          //CHECK OBJECTS IN FIELD
    protected void checkOccupiedFields(Vector2d position){
        if((this.animals.get(position)!=null && this.animals.get(position).size()>0) || this.grassH.get(position)!=null){
            if(this.occupiedFields.get(position)==null) {
                this.occupiedFields.put(position, 1);
            }
        }
        else{
            if(this.occupiedFields.get(position)!=null) {
                this.occupiedFields.remove(position);
            }
        }
    }
    private void deathAnimalsStatistic(int days){
        this.numberOfDeathAnimals+=1;
        this.sumDaysOfDeathAnimals+=days;
    }

    //ADD REMOVE GENOME
    private void addGenome(String strTab){
        if(this.genomeHashMap.get(strTab)==null){
            this.genomeHashMap.put(strTab,0);
        }
        this.genomeHashMap.put(strTab,this.genomeHashMap.get(strTab)+1);
    }
    private void removeGenome(String strTab){
        if(this.genomeHashMap.get(strTab)==null){
            return;
        }
        this.genomeHashMap.put(strTab,this.genomeHashMap.get(strTab)-1);
    }


    //GETTERS
    public String getMostPopularGenotype(){
        int count=-1;
        String mostPopularGenome=null;
        for(Map.Entry<String,Integer> entry: this.genomeHashMap.entrySet()){
            if(entry.getValue()>count){
                count=entry.getValue();
                mostPopularGenome= entry.getKey();
            }
        }
        return mostPopularGenome;
    }
    public int getNumberOfAnimalsOnMap(){
        return this.listOfAnimals.size();
    }
    public int getNumberOfGrassOnMap(){
        return this.NumberOfGrassOnMap;
    }
    public int getNumberOfFreeFieldsOnMap(){
        return this.width*this.height-this.occupiedFields.size();
    }
    public double getAverageEnergyOfLivingAnimals(){
        if(this.listOfAnimals.size()==0){return 0;}
        int sum=0;
        for(Animal animal: listOfAnimals){
            sum+=animal.getAnimalEnergy();
        }
        return sum/this.listOfAnimals.size();
    }
    public int getAverageLifespanOfDeathAnimals(){
        if(this.numberOfDeathAnimals==0){return 0;}
        return this.sumDaysOfDeathAnimals/this.numberOfDeathAnimals;
    }
    public int getDay(){
        return this.numberOfDays;
    }
    public int getMapWidth(){
        return this.width;
    }
    public int getMapHeight(){
        return this.height;
    }
    public int getEnergyFromOneGrass(){
        return this.EnergyFromOneGrass;
    }
    public int getNumberOfGrassPerDay(){
        return this.NumberOfGrassPerDay;
    }
    public int getStartingAnimalEnergy(){
        return this.StartingAnimalEnergy;
    }
    public int getEnergyWastedOnNewAnimal(){
        return this.EnergyWastedOnNewAnimal;
    }
    public Vector2d getMinMaxMutations(){
        return this.MinMaxMutations;
    }
    public int getAnimalGenomeLength(){
        return this.AnimalGenomeLength;
    }
    public String getAnimalsBehaviorVariant(){
        return this.AnimalsBehaviorVariant;
    }
    public String getMutationVariant(){
        return this.MutationVariant;
    }
    public int getEnergyLostPerDay(){
        return this.EnergyLostPerDay;
    }
}
