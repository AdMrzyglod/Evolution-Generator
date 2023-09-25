package agh.ics.oop.project1.Maps;

import agh.ics.oop.project1.Elements.Grass;
import agh.ics.oop.project1.Elements.Vector2d;
import agh.ics.oop.project1.Random.RandomNumber;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ToxicCorpses extends AbstractGrassGenerator {

    private List<Integer[]> fieldsList= new ArrayList<>();
    private int preferRange;


    //Constructor

    public ToxicCorpses(int width,int height,AbstractWorldMap map){
        super(width, height, map);

        //INITIALIZE deathAnimals HASHMAP
        this.map.deathAnimals= new HashMap<>();

        //PREFERRED FIELD RANGE
        this.preferRange=(int)Math.floor(width*height*0.2);

        //FOR EACH FIELD
        for(int i=0;i<this.width;i++){
            for(int j=0;j<this.height;j++){

                //created table with x and y coordinates and number of dead animals on field k {x,y,k}
                Integer[] field = {i,j,0};
                //add to hashmap and list
                this.map.deathAnimals.put(new Vector2d(i,j),field);
                this.fieldsList.add(field);
            }
        }
    }

    @Override
    public void generateGrass(int numberOfGrass) {

        //SORT, THEN FIRST preferRange fields are preferred
        this.fieldsList.sort(new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return o1[2]-o2[2];
            }
        });

        //FOR NUMBER OF GRASS TIMES
        for (int i = 0; i < numberOfGrass; i++) {

            //RAND NUMBER
            int randInt = RandomNumber.RandomNum(1, 100);

            int counter = 0;
            if(randInt <= 80) {

                //OPTION FOR PREFERRED FIELDS

                //AFTER preferRange TIMES, IF WE DO NOT FIND A FREE FIELD, IT IS ALMOST FULL
                while (counter < this.preferRange*2) {
                    int indexOfField = RandomNumber.RandomNum(0,preferRange);
                    Vector2d grass = new Vector2d(this.fieldsList.get(indexOfField)[0],this.fieldsList.get(indexOfField)[1]);
                    if (this.map.grassH.get(grass) == null) {
                        this.map.grassH.put(grass,new Grass(grass));
                        this.map.changeNumberOfGrass(1);
                        this.map.checkOccupiedFields(grass);
                        break;
                    }
                    counter+=1;
                }
            }
            else{
                //OPTION FOR NOT PREFERRED FIELDS

                //AFTER preferRange TIMES * 4, IF WE DO NOT FIND A FREE FIELD, IT IS ALMOST FULL
                while (counter < preferRange*4*2) {
                    int indexOfField = RandomNumber.RandomNum(preferRange+1,this.fieldsList.size()-1);
                    Vector2d grass = new Vector2d(this.fieldsList.get(indexOfField)[0],this.fieldsList.get(indexOfField)[1]);
                    if (this.map.grassH.get(grass) == null){
                        this.map.grassH.put(grass,new Grass(grass));
                        this.map.changeNumberOfGrass(1);
                        this.map.checkOccupiedFields(grass);
                        break;
                    }
                    counter+=1;
                }
            }
        }
    }
}
