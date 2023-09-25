package agh.ics.oop.project1.MapApplication;

import agh.ics.oop.project1.Elements.Animal;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

//CHART FOR OBSERVER ANIMALS
class AnimalStatisticsChart{

    private Animal observeAnimal;
    private CategoryAxis xAxisA;
    private NumberAxis yAxisA;
    private LineChart<String,Number> lineChartA;
    private XYChart.Series animalEnergy;
    private XYChart.Series grassEaten;
    private XYChart.Series numberOfChildren;
    private VBox StatChart;
    private GridPane gridPane;


    //Chart constructor
    public AnimalStatisticsChart(Animal animal,int day){
        this.observeAnimal=animal;
        this.xAxisA = new CategoryAxis();
        this.yAxisA = new NumberAxis();
        this.xAxisA.setLabel("Day");
        this.yAxisA.setLabel("Number");
        this.xAxisA.setAnimated(false);
        this.yAxisA.setAnimated(false);
        this.lineChartA = new LineChart<String,Number>(xAxisA,yAxisA);
        this.lineChartA.setAnimated(false);
        this.animalEnergy = new XYChart.Series<>();
        this.grassEaten = new XYChart.Series<>();
        this.numberOfChildren = new XYChart.Series<>();
        this.animalEnergy.setName("Energy");
        this.grassEaten.setName("Grass eaten");
        this.numberOfChildren.setName("Children number");
        this.lineChartA.getData().addAll(this.animalEnergy,this.grassEaten,this.numberOfChildren);
        this.lineChartA.setTitle("Observed animal statistics");
        this.gridPane = new GridPane();
        this.StatChart = new VBox(20,this.lineChartA,this.gridPane);
        this.gridPane.setAlignment(Pos.CENTER);
        this.StatChart.setAlignment(Pos.CENTER);
        this.addStat(day);
    }

    //Add day to stat
    public void addStat(int day){

        //if death then stop adding
        if(this.observeAnimal.getDeathDay()!=-1){
            this.drawStat(day);
            return;
        }

        // max 20 days on chart for readability

        if(this.animalEnergy.getData().size()>20){
            this.animalEnergy.getData().remove(0);
            this.grassEaten.getData().remove(0);
            this.numberOfChildren.getData().remove(0);
        }

        //add day to statistics series

        this.animalEnergy.getData().add(new XYChart.Data(day+"",this.observeAnimal.getAnimalEnergy()));
        this.grassEaten.getData().add(new XYChart.Data(day+"",this.observeAnimal.getNumberOfGrassEaten()));
        this.numberOfChildren.getData().add(new XYChart.Data(day+"",this.observeAnimal.getNumberOfChild()));

        this.drawStat(day);
    }

    //table with other statistics
    private void drawStat(int day){
        this.gridPane.getChildren().clear();
        this.gridPane.getRowConstraints().clear();
        this.gridPane.getColumnConstraints().clear();
        this.gridPane.setGridLinesVisible(false);

        String[] statName= {"Day", "Genotype", "Active gene", "Length of life"
                , "Death day"};
        String[] stat = {day+"",this.observeAnimal.genes.getStrGenes()+"",this.observeAnimal.genes.getCurrentGene()+"",this.observeAnimal.getDaysOfLive()+"",
                this.observeAnimal.getDeathDay()+""};

        for(int i=0;i< stat.length;i++){
            Label NameLabel = new Label(statName[i]);
            Label label = new Label(stat[i]);
            this.gridPane.add(NameLabel,i,0,1,1);
            this.gridPane.add(label,i,1,1,1);
            NameLabel.setPadding(new Insets(5,5,5,5));
            label.setPadding(new Insets(5,5,5,5));
            this.gridPane.setValignment(label, VPos.CENTER);
            this.gridPane.setHalignment(label, HPos.CENTER);
            this.gridPane.setValignment(NameLabel,VPos.CENTER);
            this.gridPane.setHalignment(NameLabel, HPos.CENTER);
            label.setFont(new Font(15));
            NameLabel.setFont(new Font(15));
        }
        this.gridPane.setGridLinesVisible(true);
    }

    //BOX GETTER
    public VBox getChart(){
        return this.StatChart;
    }
}
