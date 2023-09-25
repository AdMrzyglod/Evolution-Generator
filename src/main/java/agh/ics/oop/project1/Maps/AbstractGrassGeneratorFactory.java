package agh.ics.oop.project1.Maps;

import agh.ics.oop.project1.Elements.Vector2d;

public class AbstractGrassGeneratorFactory {

    public static AbstractGrassGenerator getAbstractGrassGenerator(String type, int width, int height, AbstractWorldMap map){
        if("ForestedEquators".equals(type)){
            return new ForestedEquators(width,height,map);
        }
        if("ToxicCorpses".equals(type)){
            return new ToxicCorpses(width,height,map);
        }
        return null;
    }
}
