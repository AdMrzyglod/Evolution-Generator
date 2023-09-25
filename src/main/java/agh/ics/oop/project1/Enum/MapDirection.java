package agh.ics.oop.project1.Enum;
import agh.ics.oop.project1.Elements.Vector2d;

import static java.lang.System.out;

public enum MapDirection {

    //eight directions
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    //direction number
    public int getRotateNumber(){
        int rotateNumber = switch (this) {
            case NORTH -> 0 ;
            case NORTH_EAST -> 1 ;
            case EAST -> 2;
            case SOUTH_EAST->3;
            case SOUTH->4;
            case SOUTH_WEST-> 5;
            case WEST-> 6;
            case NORTH_WEST -> 7;
        };
        return rotateNumber;
    }

    //Rotation of directions
    public MapDirection rotate(int rotation){
        int newNumDirect=(this.getRotateNumber()+rotation)%8; 
        MapDirection direct = switch (newNumDirect) {
            case 0 -> MapDirection.NORTH; 
            case 1-> MapDirection.NORTH_EAST;
            case 2-> MapDirection.EAST;
            case 3-> MapDirection.SOUTH_EAST;
            case 4-> MapDirection.SOUTH;
            case 5-> MapDirection.SOUTH_WEST;
            case 6-> MapDirection.WEST;
            case 7-> MapDirection.NORTH_WEST;
            default -> throw new IllegalStateException("Unexpected value: " + newNumDirect);
        };
        return direct;
    }

    //from direction to vector
    public Vector2d toUnitVector(){
        Vector2d vector = switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTH_EAST -> new Vector2d(1,1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH_EAST ->  new Vector2d(1,-1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTH_WEST -> new Vector2d(-1,-1);
            case WEST -> new Vector2d(-1, 0);
            case NORTH_WEST -> new Vector2d(-1,1);
        };
        return vector;
    }

}
