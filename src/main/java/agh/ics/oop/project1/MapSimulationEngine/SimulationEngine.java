package agh.ics.oop.project1.MapSimulationEngine;

import agh.ics.oop.project1.MapApplication.App;
import agh.ics.oop.project1.Maps.AbstractWorldMap;
import agh.ics.oop.project1.fileclasses.DataCSV;
import javafx.application.Platform;

import java.io.IOException;

import static java.lang.System.out;

public class SimulationEngine implements Runnable {
    private AbstractWorldMap map;
    private int moveDelay=300;
    private App application;
    public boolean isWait=false;
    public DataCSV dataCSV=null;


    //Constructor
    public SimulationEngine(AbstractWorldMap map,int moveDelay,App application){
        this.map=map;
        this.moveDelay=moveDelay;
        this.application=application;
    }

    //SAVE DATA
    public void setDataCSV() throws IOException {
        try {
            this.dataCSV = new DataCSV(this.map);
        }catch(IOException exception){
            out.println("Nie można stworzyć pliku");
        }
    }

    //THREAD SLEEP ON MOVE DELAY TIME
    private void threadSleep(){
        try {
            Thread.sleep(this.moveDelay);

        } catch (InterruptedException exception) {
            out.print("Application is not running " + exception);
        }
    }

    //RESUME THREAD
    synchronized public void threadResume() throws InterruptedException {
        notify();
    }

    //MAP CONTROL FUNCTION
    @Override
    synchronized public void run() {

        while(this.map.getNumberOfAnimalsOnMap()>0) {

            //IF isWait TRUE THEN WAIT THREAD
            if(this.isWait){
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            //MAP OPERATIONS

            threadSleep();
            this.map.deleteDeathAnimals();
            this.map.moveAllAnimals();
            this.map.grassEating();
            this.map.copulation();
            this.map.generateGrassOnMap();
            this.map.newDayLoseAll();


            //DRAW
            Platform.runLater(() -> {
                this.application.drawMap();
                this.application.addDayToStat();
            });

            //WRITE NEW LINE TO FILE
            if(this.dataCSV!=null) {
                this.dataCSV.writeToCSV();
            }
        }
    }
}
