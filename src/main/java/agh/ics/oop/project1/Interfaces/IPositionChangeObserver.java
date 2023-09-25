package agh.ics.oop.project1.Interfaces;

import agh.ics.oop.project1.Elements.Animal;
import agh.ics.oop.project1.Elements.Vector2d;

public interface IPositionChangeObserver {

     void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition);
}
