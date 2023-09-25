package agh.ics.oop.project1.fileclasses;



import agh.ics.oop.project1.Maps.AbstractWorldMap;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvWriter;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvWriterSettings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DataCSV {

    private File CSVFile;
    private CsvWriter writer;
    private AbstractWorldMap map;

    //Constructor

    public DataCSV(AbstractWorldMap map) throws IOException {
        String[] header = this.getStatisticsNames();
        this.map=map;
        this.CSVFile = new File(new File("src/main/resources/agh/ics/oop/project1/fileclasses/dataCSV").getAbsolutePath()+"/"+getDateTime()+".csv");
        this.CSVFile.createNewFile();

        FileWriter outputfile = new FileWriter(this.CSVFile);
        this.writer = new CsvWriter(outputfile,new CsvWriterSettings());
        this.writer.writeHeaders(header);
    }

    //map stats
    private String[] getStatisticsNames(){
        return new String[]{"Day","Number of animals", "Number of grass", "Number of free fields", "Most popular genotype", "Average energy of living animals"
                , "Average lifespan of deaths animals"};
    }
    private String[] getStatistics(){
        String[] stat= {this.map.getDay()+"",this.map.getNumberOfAnimalsOnMap()+"",this.map.getNumberOfGrassOnMap()+"",this.map.getNumberOfFreeFieldsOnMap()+"",
                this.map.getMostPopularGenotype(),this.map.getAverageEnergyOfLivingAnimals()+"",this.map.getAverageLifespanOfDeathAnimals()+""};
        return stat;
    }

    //CSV OPTIONS
    public void writeToCSV(){
        this.writer.writeRow(this.getStatistics());
    }
    public void closeWriter(){
        this.writer.flush();
    }

    //OTHER
    private String getDateTime(){
        String tm = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss"));
        return tm;
    }
}
