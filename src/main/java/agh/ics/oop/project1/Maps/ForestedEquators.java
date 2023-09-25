package agh.ics.oop.project1.Maps;

import agh.ics.oop.project1.Elements.Grass;
import agh.ics.oop.project1.Elements.Vector2d;
import agh.ics.oop.project1.Random.RandomNumber;

public class ForestedEquators extends AbstractGrassGenerator {

    //UPPER RIGHT AND LOWER LEFT LAND STRIP
    Vector2d ur;
    Vector2d ll;


    //Constructor

    public ForestedEquators(int width,int height,AbstractWorldMap map){
        super(width, height, map);

        int xHigh=width-1;
        int yHigh=height-1;
        int xLow=0;
        int yLow=0;

        //SIZE OF LAND STRIP IS 20 OF THE MAP SIZE
        double area = (xHigh-xLow+1)*(yHigh-yLow+1)*0.2;

        //SEARCHING LAND STRIP
        while(true){
            if(area>=(xHigh-xLow+1)*(yHigh-yLow+1)){
                break;
            }
            xHigh-=1;
            if(area>=(xHigh-xLow+1)*(yHigh-yLow+1)){
                break;
            }
            yHigh-=1;
            if(area>=(xHigh-xLow+1)*(yHigh-yLow+1)){
                break;
            }
            yLow+=1;
            if(area>=(xHigh-xLow+1)*(yHigh-yLow+1)){
                break;
            }
            xLow+=1;
        }
        this.ll=new Vector2d(xLow,yLow);
        this.ur=new Vector2d(xHigh,yHigh);
    }

    @Override
    public void generateGrass(int numberOfGrass){

        //FOR NUMBER OF GRASS TIMES
        for (int i = 0; i < numberOfGrass; i++) {

            //RAND NUMBER
            int randInt = RandomNumber.RandomNum(1, 100);

            int counter = 0;
            if(randInt <= 80) {

                //OPTION FOR LAND STRIP

                //AFTER LAND STRIP SIZE TIMES, IF WE DO NOT FIND A FREE FIELD, IT IS ALMOST FULL
                while (counter < (ur.x-ll.x+1) * (ur.y-ll.y+1)*2) {
                    Vector2d grass = new Vector2d(this.ll.x + RandomNumber.RandomNum(0, ur.x-ll.x  ), this.ll.y + RandomNumber.RandomNum(0, ur.y-ll.y));
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
                //OPTION FOR REST MAP

                //AFTER REST MAP SIZE TIMES, IF WE DO NOT FIND A FREE FIELD, IT IS ALMOST FULL
                while (counter < (ur.x-ll.x+1) * (ur.y-ll.y+1)*4*2) {
                    Vector2d grass = new Vector2d(RandomNumber.RandomNum(0, this.width-1), RandomNumber.RandomNum(0, this.height-1));
                    if ( !(grass.follows(ll) && grass.precedes(ur)) && this.map.grassH.get(grass) == null){
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
