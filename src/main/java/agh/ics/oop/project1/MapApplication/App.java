package agh.ics.oop.project1.MapApplication;


import agh.ics.oop.project1.Elements.Animal;
import agh.ics.oop.project1.Elements.Grass;
import agh.ics.oop.project1.Elements.Vector2d;
import agh.ics.oop.project1.MapSimulationEngine.SimulationEngine;
import agh.ics.oop.project1.Maps.AbstractWorldMap;
import agh.ics.oop.project1.fileclasses.DataCSV;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import static java.lang.System.out;

//APPLICATIONS WINDOWS
public class App{

    private AbstractWorldMap map;
    private SimulationEngine appEngine;
    private GridPane gridPane = new GridPane();
    private Thread threadRun;
    private StatisticsChart statChart;
    private Animal observerAnimal=null;
    private AnimalStatisticsChart chartForObserverAnimal=null;
    private VBox boxStat;


    //Constructor

    public App(AbstractWorldMap map,int RefreshTime) throws IOException {
        this.map=map;
        this.appEngine=new SimulationEngine(map,RefreshTime,this);
        this.statChart = new StatisticsChart(this.map);
    }

    //SAVE DATA
    protected void setDataCsv(){
        try {
            this.appEngine.setDataCSV();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //DRAW MAP
    public void drawMap(){

        this.gridPane.getChildren().clear();
        this.gridPane.getRowConstraints().clear();
        this.gridPane.getColumnConstraints().clear();
        this.gridPane.setGridLinesVisible(false);

        //MOST POPULAR GEN
        String gen = null;

        //MAP WAIT
        if(this.appEngine.isWait){
            gen= this.map.getMostPopularGenotype();

            //clear variables for observer animal
            this.observerAnimal=null;
            if(this.chartForObserverAnimal!=null) {
                this.boxStat.getChildren().remove(2);
            }
            this.chartForObserverAnimal= null;
        }

        //draw map
        for(int i=0;i<this.map.getMapWidth();i++){
            for(int j=0;j<this.map.getMapHeight();j++){

                //RECTANGLE IS FIELD
                Rectangle box = new Rectangle(20,20);

                Object object = this.map.objectAt(new Vector2d(i,j));

                //FIELD NOT EMPTY
                if(object!=null){
                    if(object instanceof Grass){
                        box.setFill(((Grass) object).toPaint());
                    }
                    else{
                        box.setFill(((Animal) object).toPaint());

                        //OBSERVER ANIMAL IS ALIVE AND IN CURRENT POSITION
                        if(this.observerAnimal!=null && this.observerAnimal.getDeathDay()==-1 && this.observerAnimal.getPosition().equals(((Animal) object).getPosition())){

                            //HIGHLIGHT IT
                            box.setStrokeType(StrokeType.INSIDE);
                            box.setStrokeWidth(4);
                            box.setStroke(Color.BROWN);
                        }

                        //MAP WAIT
                        if(gen!=null){

                            //HIGHLIGHT ANIMALS WITH THE MOST POPULAR GENOME
                            if(((Animal) object).genes.getStrGenes().equals(gen)){
                                box.setStrokeType(StrokeType.INSIDE);
                                box.setStrokeWidth(3);
                                box.setStroke(Color.BLUE);
                            }

                            //CLICK ON RECTANGLE WITH ANIMAL TO OBSERVE IT
                            box.setOnMouseClicked(event -> {
                                this.observerAnimal=(Animal) object;
                            });
                        }
                    }
                }

                //EMPTY FIELD
                else{
                    box.setFill(Color.rgb(153,255,153));
                }

                gridPane.add(box,i,j,1,1);
                gridPane.setHalignment(box, HPos.CENTER);
                gridPane.setValignment(box,VPos.CENTER);
            }
        }
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        gridPane.setGridLinesVisible(true);

        //SET CHART FOR OBSERVER ANIMAL
        if(this.observerAnimal!=null && this.chartForObserverAnimal==null){
            this.chartForObserverAnimal= new AnimalStatisticsChart(this.observerAnimal,this.map.getDay());
            this.boxStat.getChildren().add(this.chartForObserverAnimal.getChart());
        }

        //UPDATE OBSERVER ANIMAL CHART
        if(this.chartForObserverAnimal!=null){
            this.chartForObserverAnimal.addStat(this.map.getDay());
        }
    }

    //MAP STATISTIC CHART UPDATE
    public void addDayToStat(){
         this.statChart.addStat();
    }

    public void start(){

        Stage primaryStage = new Stage();

        //DRAW
        this.drawMap();
        this.addDayToStat();

        if(this.chartForObserverAnimal!=null){
            this.chartForObserverAnimal.addStat(this.map.getDay());
        }


        Label Title = new Label("Simulation engine map");
        Title.setFont(new Font(35));
        HBox boxTitle=new HBox(50,Title);

        int rectangleSize=20;
        Rectangle freeField = new Rectangle(rectangleSize,rectangleSize);
        freeField.setFill(Color.rgb(153,255,153));
        Label freeFieldLabel = new Label("<- Empty field");
        HBox freeBox = new HBox(10,freeField,freeFieldLabel);
        freeBox.setAlignment(Pos.CENTER);

        Rectangle grass = new Rectangle(rectangleSize,rectangleSize);
        grass.setFill(Color.GREEN);
        Label grassLabel = new Label("<- Grass");
        HBox grassBox = new HBox(10,grass,grassLabel);
        grassBox.setAlignment(Pos.CENTER);

        Rectangle animal = new Rectangle(rectangleSize,rectangleSize);
        animal.setFill(Color.rgb(0,0,0));
        Label animalLabel = new Label("<- Animal");
        HBox animalBox = new HBox(10,animal,animalLabel);
        animalBox.setAlignment(Pos.CENTER);

        Rectangle popularGen = new Rectangle(rectangleSize,rectangleSize);
        popularGen.setFill(Color.rgb(0,0,0));
        popularGen.setStrokeType(StrokeType.INSIDE);
        popularGen.setStrokeWidth(3);
        popularGen.setStroke(Color.BLUE);
        Label popularGenLabel = new Label("<- Animal with most popular genotype");
        HBox popularGenBox = new HBox(10,popularGen,popularGenLabel);
        popularGenBox.setAlignment(Pos.CENTER);

        Rectangle observedAnimal = new Rectangle(rectangleSize,rectangleSize);
        observedAnimal.setFill(Color.rgb(0,0,0));
        observedAnimal.setStrokeType(StrokeType.INSIDE);
        observedAnimal.setStrokeWidth(4);
        observedAnimal.setStroke(Color.BROWN);
        Label observedAnimalLabel = new Label("<- Observed animal");
        HBox observedAnimalBox = new HBox(10,observedAnimal,observedAnimalLabel);
        observedAnimalBox.setAlignment(Pos.CENTER);

        Label legend = new Label("Legend");
        legend.setFont(new Font(20));
        legend.setAlignment(Pos.CENTER);

        HBox box1= new HBox(15,freeBox,animalBox,grassBox);
        box1.setAlignment(Pos.CENTER);
        HBox box2 = new HBox(15,popularGenBox,observedAnimalBox);
        box2.setAlignment(Pos.CENTER);

        VBox legendBox = new VBox(10,legend,box1,box2);
        legendBox.setAlignment(Pos.CENTER);


        Label statLabel = new Label("Statistics");
        statLabel.setFont(new Font(30));
        HBox boxStatLabel = new HBox(40,statLabel);

        ToggleButton controlThread= new ToggleButton("Stop simulation");
        HBox controlThreadBox = new HBox(controlThread);
        controlThreadBox.setAlignment(Pos.CENTER);


        //THREAD CONTROL TOGGLE BUTTON
        controlThread.setOnAction((click)->{
            this.appEngine.isWait=controlThread.isSelected();
            if(!controlThread.isSelected()){
                controlThread.setText("Stop simulation");
            }
            else{
                controlThread.setText("Resume simulation");
            }

            //IF THE ENGINE IS NOT WAITING RESUME IT
            if(!this.appEngine.isWait){
                try {
                    this.appEngine.threadResume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        threadRun= new Thread(this.appEngine);
        threadRun.start();

        this.gridPane.setAlignment(Pos.CENTER);
        VBox boxTable=new VBox(25,boxTitle,legendBox,this.gridPane,controlThreadBox);
        this.boxStat = new VBox(30,boxStatLabel,this.statChart.getChart());
        HBox appBox = new HBox(70,this.boxStat,boxTable);
        boxTitle.setAlignment(Pos.CENTER);
        appBox.setAlignment(Pos.CENTER);
        boxStatLabel.setAlignment(Pos.CENTER);



        Scene scene = new Scene(appBox,1700,1000);
        primaryStage.setScene(scene);
        primaryStage.show();

        //WHEN CLOSE THE WINDOW, CLOSE WRITE TO FILE AND END THREAD
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                if(appEngine.dataCSV!=null) {
                    appEngine.dataCSV.closeWriter();
                }
                threadRun.stop();
            }
        });
    }
}
