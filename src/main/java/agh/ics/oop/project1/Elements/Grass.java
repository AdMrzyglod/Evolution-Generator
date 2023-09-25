package agh.ics.oop.project1.Elements;


import javafx.scene.paint.Color;


public class Grass {
    private Vector2d grassTuft;
    private Color grassColor;

    public Grass(Vector2d grassTuft){
        this.grassTuft=grassTuft;
        this.grassColor= Color.GREEN;
    }
    public Vector2d getPosition(){
        return this.grassTuft;
    }

    //Paint
    public Color toPaint(){
        return this.grassColor;
    }
}
