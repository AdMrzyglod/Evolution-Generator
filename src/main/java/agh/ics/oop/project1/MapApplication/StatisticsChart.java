package agh.ics.oop.project1.MapApplication;

import agh.ics.oop.project1.Maps.AbstractWorldMap;
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

import static java.lang.System.out;

//CHART FOR MAP
public class StatisticsChart{

    private AbstractWorldMap map;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private LineChart<String,Number> lineChart;
    private XYChart.Series numAnimals;
    private XYChart.Series numGrass;
    private XYChart.Series numFreeFields;
    private GridPane statisticPane = new GridPane();
    private VBox Chart;

    //Chart constructor
    public StatisticsChart(AbstractWorldMap map){
        this.map=map;
        this.xAxis = new CategoryAxis();
        this.yAxis = new NumberAxis();
        this.xAxis.setLabel("Day");
        this.yAxis.setLabel("Number");
        this.xAxis.setAnimated(false);
        this.yAxis.setAnimated(false);
        this.lineChart = new LineChart<String,Number>(xAxis,yAxis);
        this.lineChart.setAnimated(false);
        this.numAnimals = new XYChart.Series<>();
        this.numGrass = new XYChart.Series<>();
        this.numFreeFields = new XYChart.Series<>();
        this.numAnimals.setName("Animals");
        this.numGrass.setName("Grass");
        this.numFreeFields.setName("Free fields");
        this.lineChart.getData().addAll(this.numAnimals,this.numGrass,this.numFreeFields);
        this.lineChart.setTitle("Map statistics");
        this.Chart = new VBox(20,this.lineChart,this.statisticPane);
        this.statisticPane.setAlignment(Pos.CENTER);
        this.Chart.setAlignment(Pos.CENTER);
    }

    //Add day to stat
    public void addStat(){

        // max 20 days on chart for readability
        if(this.numAnimals.getData().size()>20){
            this.numAnimals.getData().remove(0);
            this.numGrass.getData().remove(0);
            this.numFreeFields.getData().remove(0);
        }

        //add day to statistics series
        this.numAnimals.getData().add(new XYChart.Data(this.map.getDay()+"",this.map.getNumberOfAnimalsOnMap()));
        this.numGrass.getData().add(new XYChart.Data(this.map.getDay()+"",this.map.getNumberOfGrassOnMap()));
        this.numFreeFields.getData().add(new XYChart.Data(this.map.getDay()+"",this.map.getNumberOfFreeFieldsOnMap()));

        this.drawStatistic();
    }

    //table with other statistics
    private void drawStatistic(){
        this.statisticPane.getChildren().clear();
        this.statisticPane.getRowConstraints().clear();
        this.statisticPane.getColumnConstraints().clear();
        this.statisticPane.setGridLinesVisible(false);

        String[] statName= {"Day", "Most popular genotype", "Average energy"
                , "Average lifespan death"};
        String[] stat = {this.map.getDay()+"",this.map.getMostPopularGenotype()+"",this.map.getAverageEnergyOfLivingAnimals()+"",
                this.map.getAverageLifespanOfDeathAnimals()+""};

        for(int i=0;i< stat.length;i++){
            Label NameLabel = new Label(statName[i]);
            Label label = new Label(stat[i]);
            this.statisticPane.add(NameLabel,i,0,1,1);
            this.statisticPane.add(label,i,1,1,1);
            NameLabel.setPadding(new Insets(5,5,5,5));
            label.setPadding(new Insets(5,5,5,5));
            statisticPane.setValignment(label, VPos.CENTER);
            statisticPane.setHalignment(label, HPos.CENTER);
            statisticPane.setValignment(NameLabel,VPos.CENTER);
            statisticPane.setHalignment(NameLabel, HPos.CENTER);
            label.setFont(new Font(15));
            NameLabel.setFont(new Font(15));
        }
        this.statisticPane.setGridLinesVisible(true);
    }

    //BOX GETTER
    public VBox getChart(){
         return this.Chart;
    }
}
