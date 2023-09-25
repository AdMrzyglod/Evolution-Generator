package agh.ics.oop.project1.Maps;

abstract public class AbstractGrassGenerator {
    int width;
    int height;
    AbstractWorldMap map;

    //Constructor
    public AbstractGrassGenerator(int width, int height, AbstractWorldMap map){
        this.width=width;
        this.height=height;
        this.map=map;
    }

    abstract public void generateGrass(int numberOfGrass);
}
