package agh.ics.oop.project1.MapApplication;


import agh.ics.oop.project1.Elements.Vector2d;
import agh.ics.oop.project1.Maps.AbstractWorldMapFactory;
import agh.ics.oop.project1.fileclasses.DataLoader;
import agh.ics.oop.project1.Maps.AbstractWorldMap;
import agh.ics.oop.project1.Maps.Glob;
import agh.ics.oop.project1.Maps.HellPortal;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.System.out;


//MAIN APPLICATION MENU
public class Menu extends Application{

    private int TitleSize=15;

    //LABELS
    private Label MapDetailsLabel;
    private Label MapWidthLabel;
    private Label MapHeightLabel;
    private Label MapVariantLabel;
    private Label GrassGrowthVariantLabel;
    private Label StartingNumberOfGrassLabel;
    private Label AnimalDetailsLabel;
    private Label EnergyFromOneGrassLabel;
    private Label NumberOfGrassPerDayLabel;
    private Label StartingNumberOfAnimalsLabel;
    private Label StartingAnimalEnergyLabel;
    private Label EnergyNeededForCopulationLabel;
    private Label EnergyWastedOnNewAnimalLabel;
    private Label MinMaxNumberOfMutationsInNewAnimalLabel;
    private Label AnimalGenomeLengthLabel;
    private Label MutationVariantLabel;
    private Label AnimalsBehaviorVariantLabel;
    private Label EnergyLostPerDayLabel;
    private Label GrassDetailsLabel;
    private Label OtherDetailsLabel;
    private Label RefreshTimeLabel;
    private Label chooseVariantLabel;
    private Label verificationMessage;

    //TEXTFIELDS AND COMBOBOX
    private TextField MapWidth;
    private TextField MapHeight;
    private ComboBox MapVariant;
    private ComboBox GrassGrowthVariant;
    private TextField StartingNumberOfGrass;
    private TextField EnergyFromOneGrass;
    private TextField NumberOfGrassPerDay;
    private TextField StartingNumberOfAnimals;
    private TextField StartingAnimalEnergy;
    private TextField EnergyNeededForCopulation;
    private TextField EnergyWastedOnNewAnimal;
    private TextField MinNumberOfMutationsInNewAnimal;
    private TextField MaxNumberOfMutationsInNewAnimal;
    private TextField AnimalGenomeLength;
    private ComboBox MutationVariant;
    private ComboBox AnimalsBehaviorVariant;
    private TextField EnergyLostPerDay;
    private TextField RefreshTime;

    //OTHER
    private CheckBox SaveStat;
    private VBox FinalStage;
    private Button StartButton;
    private ComboBox<String> chooseVariants;

    //INTEGER STATISTICS
    private int mapWidthInt;
    private int mapHeightInt;
    private String mapVariantStr;
    private int energyFromOneGrassInt;
    private int numberOfGrassPerDayInt;
    private int startingNumberOfGrassInt;
    private String grassGrowthVariantStr;
    private int startingNumberOfAnimalInt;
    private int startingAnimalEnergyInt;
    private int energyNeededForCopulationInt;
    private int energyWastedOnNewAnimalInt;
    private Vector2d minMaxMutationsInt;
    private int animalGenomeLengthInt;
    private String animalBehaviorVariantStr;
    private String mutationVariantStr;
    private int energyLostPerDayInt;
    private int RefreshTimeInt;


    //Menu initializer

    public void init() throws IOException {


        //COMBOBOX FOR CONFIGURATIONS

        this.chooseVariantLabel = new Label("Choose a simulation variant: ");
        this.chooseVariants = new ComboBox<>(FXCollections.observableArrayList(this.getListOfNameConfigFiles()));
        VBox chooseVariantVBox = new VBox(20,this.chooseVariantLabel,this.chooseVariants);
        chooseVariantVBox.setAlignment(Pos.CENTER);

        this.SaveStat = new CheckBox("Save stat");
        HBox statBox= new HBox(this.SaveStat);
        statBox.setAlignment(Pos.CENTER);

        //INIT LABELS

        this.MapDetailsLabel= new Label("Map details");//
        this.MapWidthLabel= new Label("Map width: ");//
        this.MapHeightLabel = new Label("Map height: ");//
        this.MapVariantLabel = new Label("Map variant: ");//
        this.GrassGrowthVariantLabel =  new Label("Grass growth variant: ");//
        this.StartingNumberOfGrassLabel = new Label("Starting number of grass: ");//
        this.AnimalDetailsLabel = new Label("Animal details");//
        this.EnergyFromOneGrassLabel = new Label("Energy from one grass: ");//
        this.NumberOfGrassPerDayLabel= new Label("Number of grass per day: ");//
        this.StartingNumberOfAnimalsLabel= new Label("Starting number of animals: ");//
        this.StartingAnimalEnergyLabel = new Label("Starting animal energy" );//
        this.EnergyNeededForCopulationLabel = new Label("Energy needed for copulation: ");//
        this.EnergyWastedOnNewAnimalLabel = new Label("Energy wasted on new animal: ");//
        this.MinMaxNumberOfMutationsInNewAnimalLabel = new Label("Min/Max number of mutations in new animal: ");//
        this.AnimalGenomeLengthLabel = new Label("Animal genome length: ");//
        this.MutationVariantLabel = new Label("Mutation variant: ");//
        this.AnimalsBehaviorVariantLabel = new Label("Animal behavior variant: ");//
        this.GrassDetailsLabel= new Label("Grass details");//
        this.OtherDetailsLabel = new Label("Other");
        this.EnergyLostPerDayLabel= new Label("Energy lost per day: ");
        this.RefreshTimeLabel = new Label("Refresh time: ");

        this.chooseVariantLabel.setFont(new Font(this.TitleSize));
        this.MapDetailsLabel.setFont(new Font(this.TitleSize));
        this.AnimalDetailsLabel.setFont(new Font(this.TitleSize));
        this.GrassDetailsLabel.setFont(new Font(this.TitleSize));
        this.OtherDetailsLabel.setFont(new Font(this.TitleSize));

        //MAP
        this.MapWidth = new TextField();
        this.MapHeight= new TextField();
        this.MapVariant= new ComboBox<>(FXCollections.observableArrayList("Glob","HellPortal",""));

        HBox MapWidthHBox= new HBox(20,this.MapWidthLabel,this.MapWidth);
        HBox MapHeightHBox= new HBox(20,this.MapHeightLabel,this.MapHeight);
        HBox MapVariantHBox = new HBox(20,this.MapVariantLabel,this.MapVariant);
        HBox MapDetailsHBox= new HBox(20,this.MapDetailsLabel);
        VBox MapDetails = new VBox(10,MapDetailsHBox,MapWidthHBox,MapHeightHBox,MapVariantHBox);

        MapWidthHBox.setAlignment(Pos.CENTER);
        MapHeightHBox.setAlignment(Pos.CENTER);
        MapVariantHBox.setAlignment(Pos.CENTER);
        MapDetailsHBox.setAlignment(Pos.CENTER);



        //ANIMAL
        this.EnergyNeededForCopulation= new TextField();
        this.EnergyWastedOnNewAnimal= new TextField();
        this.StartingNumberOfAnimals = new TextField();
        this.StartingAnimalEnergy = new TextField();
        this.MinNumberOfMutationsInNewAnimal=new TextField();
        this.MaxNumberOfMutationsInNewAnimal=new TextField();
        this.AnimalGenomeLength= new TextField();
        this.MutationVariant = new ComboBox<>(FXCollections.observableArrayList("FullRandGen","LightCorrectionGen",""));
        this.AnimalsBehaviorVariant= new ComboBox<>(FXCollections.observableArrayList("FullPredestinationGen","RandomGen",""));
        this.EnergyLostPerDay= new TextField();

        HBox AnimalsDetailsHBox = new HBox(20,this.AnimalDetailsLabel);
        HBox StartingNumberOfAnimalsHBox = new HBox(20, this.StartingNumberOfAnimalsLabel,this.StartingNumberOfAnimals);
        HBox EnergyNeededForCopulationHBox= new HBox(20,this.EnergyNeededForCopulationLabel,this.EnergyNeededForCopulation);
        HBox EnergyWastedOnNewAnimalHBox = new HBox(20,this.EnergyWastedOnNewAnimalLabel,this.EnergyWastedOnNewAnimal);
        HBox StartingAnimalEnergyHBox = new HBox(20,this.StartingAnimalEnergyLabel,this.StartingAnimalEnergy);
        HBox MinMaxNumberOfMutationsInNewAnimalHBox = new HBox(20,this.MinMaxNumberOfMutationsInNewAnimalLabel,this.MinNumberOfMutationsInNewAnimal,
                MaxNumberOfMutationsInNewAnimal);
        HBox AnimalGenomeLengthHBox = new HBox(20,this.AnimalGenomeLengthLabel,this.AnimalGenomeLength);
        HBox MutationVariantHBox = new HBox(20,this.MutationVariantLabel,this.MutationVariant);
        HBox AnimalsBehaviorVariantHBox = new HBox(20,this.AnimalsBehaviorVariantLabel,this.AnimalsBehaviorVariant);
        HBox EnergyLostPerDayHBox = new HBox(20,this.EnergyLostPerDayLabel,this.EnergyLostPerDay);
        VBox AnimalDetails = new VBox(10,AnimalsDetailsHBox,StartingNumberOfAnimalsHBox,StartingAnimalEnergyHBox,EnergyLostPerDayHBox,EnergyNeededForCopulationHBox
                ,EnergyWastedOnNewAnimalHBox, AnimalGenomeLengthHBox,MutationVariantHBox,MinMaxNumberOfMutationsInNewAnimalHBox,AnimalsBehaviorVariantHBox);

        AnimalsDetailsHBox.setAlignment(Pos.CENTER);
        StartingNumberOfAnimalsHBox.setAlignment(Pos.CENTER);
        EnergyNeededForCopulationHBox.setAlignment(Pos.CENTER);
        EnergyWastedOnNewAnimalHBox.setAlignment(Pos.CENTER);
        StartingAnimalEnergyHBox.setAlignment(Pos.CENTER);
        MinMaxNumberOfMutationsInNewAnimalHBox.setAlignment(Pos.CENTER);
        AnimalGenomeLengthHBox.setAlignment(Pos.CENTER);
        MutationVariantHBox.setAlignment(Pos.CENTER);
        AnimalsBehaviorVariantHBox.setAlignment(Pos.CENTER);
        EnergyLostPerDayHBox.setAlignment(Pos.CENTER);


        //GRASS
        this.GrassGrowthVariant= new ComboBox<>(FXCollections.observableArrayList("ForestedEquators","ToxicCorpses",""));
        this.StartingNumberOfGrass= new TextField();
        this.EnergyFromOneGrass = new TextField();
        this.NumberOfGrassPerDay= new TextField();

        HBox GrassDetailsHBox = new HBox(this.GrassDetailsLabel);
        HBox StartingNumberOfGrassHBox = new HBox(20,this.StartingNumberOfGrassLabel,this.StartingNumberOfGrass);
        HBox EnergyFromOneGrassHBox = new HBox(20,this.EnergyFromOneGrassLabel,this.EnergyFromOneGrass);
        HBox NumberOfGrassPerDayHBox = new HBox(20,this.NumberOfGrassPerDayLabel,this.NumberOfGrassPerDay);
        HBox GrassGrowthVariantHBox = new HBox(20,this.GrassGrowthVariantLabel,this.GrassGrowthVariant);
        VBox GrassDetails = new VBox(10,GrassDetailsHBox,StartingNumberOfGrassHBox,EnergyFromOneGrassHBox,NumberOfGrassPerDayHBox,GrassGrowthVariantHBox);

        GrassDetailsHBox.setAlignment(Pos.CENTER);
        StartingNumberOfGrassHBox.setAlignment(Pos.CENTER);
        EnergyFromOneGrassHBox.setAlignment(Pos.CENTER);
        NumberOfGrassPerDayHBox.setAlignment(Pos.CENTER);
        GrassGrowthVariantHBox.setAlignment(Pos.CENTER);

        //OTHERS
        this.RefreshTime = new TextField();

        HBox OtherHBox = new HBox(this.OtherDetailsLabel);
        HBox RefreshTimeHBox = new HBox(20,this.RefreshTimeLabel,this.RefreshTime);
        VBox Other = new VBox(10,OtherHBox,RefreshTimeHBox);

        OtherHBox.setAlignment(Pos.CENTER);
        RefreshTimeHBox.setAlignment(Pos.CENTER);


        this.StartButton=new Button("Start");
        HBox StartButtonHBox = new HBox(this.StartButton);
        StartButtonHBox.setAlignment(Pos.CENTER);

        HBox Buttons = new HBox(80,statBox,StartButtonHBox);
        Buttons.setAlignment(Pos.CENTER);

        //VERIFICATION
        this.verificationMessage = new Label("");
        this.verificationMessage.setTextFill(Color.RED);
        this.verificationMessage.setFont(new Font(this.TitleSize));
        HBox vMessage =  new HBox(20,verificationMessage);
        vMessage.setAlignment(Pos.CENTER);


        this.FinalStage=new VBox(30,chooseVariantVBox,MapDetails,AnimalDetails,GrassDetails,Other,vMessage,Buttons);


        this.chooseVariants.getSelectionModel().selectFirst();
        if(this.chooseVariants.getValue()!="") {
            this.setVariant(chooseVariants.getValue());
        }
    }

    //ADD VARIANT TO TEXTFIELDS
    private void setVariant(String configFilePath) throws IOException {
        DataLoader loadData = new DataLoader(configFilePath);

        this.MapWidth.setText(loadData.getMapWidth());
        this.MapHeight.setText(loadData.getMapHeight());
        if(!loadData.getMapVariant().equals("Glob") && !loadData.getMapVariant().equals("HellPortal")) {
            this.MapVariant.getSelectionModel().select("");
        }
        else{
            this.MapVariant.getSelectionModel().select(loadData.getMapVariant());
        }
        this.EnergyNeededForCopulation.setText(loadData.getEnergyNeededForCopulation());
        this.EnergyWastedOnNewAnimal.setText(loadData.getEnergyWastedOnNewAnimal());
        this.StartingNumberOfAnimals.setText(loadData.getStartingNumberOfAnimals());
        this.StartingAnimalEnergy.setText(loadData.getStatingAnimalEnergy());
        this.MinNumberOfMutationsInNewAnimal.setText(loadData.getMinNumberOfMutationsInNewAnimal());
        this.MaxNumberOfMutationsInNewAnimal.setText(loadData.getMaxNumberOfMutationsInNewAnimal());
        this.AnimalGenomeLength.setText(loadData.getAnimalGenomeLength());
        if(!loadData.getMutationVariant().equals("FullRandGen") && !loadData.getMutationVariant().equals("LightCorrectionGen")){
            this.MutationVariant.getSelectionModel().select("");
        }
        else{
            this.MutationVariant.getSelectionModel().select(loadData.getMutationVariant());
        }
        if(!loadData.getAnimalsBehaviorVariant().equals("FullPredestinationGen") && !loadData.getAnimalsBehaviorVariant().equals("RandomGen")){
            this.AnimalsBehaviorVariant.getSelectionModel().select("");
        }
        else{
            this.AnimalsBehaviorVariant.getSelectionModel().select(loadData.getAnimalsBehaviorVariant());
        }
        this.EnergyLostPerDay.setText(loadData.getEnergyLostPerDay());
        if(!loadData.getGrassGrowthVariant().equals("ForestedEquators") && !loadData.getGrassGrowthVariant().equals("ToxicCorpses")){
            this.GrassGrowthVariant.getSelectionModel().select("");
        }
        else{
            this.GrassGrowthVariant.getSelectionModel().select(loadData.getGrassGrowthVariant());
        }
        this.StartingNumberOfGrass.setText(loadData.getStartingNumberOfGrass());
        this.EnergyFromOneGrass.setText(loadData.getEnergyFromOneGrass());
        this.NumberOfGrassPerDay.setText(loadData.getNumberOfGrassPerDay());

        this.RefreshTime.setText(loadData.getRefreshTime());
    }

    //LIST OF CONFIGURATION FILES
    private String[] getListOfNameConfigFiles(){
        try {
            File file = new File(new File("src/main/resources/agh/ics/oop/project1/fileclasses/variants").getAbsolutePath());

            String[] configFiles =file.list();
            if(configFiles == null){
                out.println("Nie można znaleźć plików konfiguracyjnych");
                return new String[]{""};
            }
            return configFiles;
        } catch (NullPointerException exception){
            out.println(exception);
        }
        return new String[]{""};
    }

    //VERIFICATION
    private void verificationOfArguments(){
        if(this.mapWidthInt<1){throw new IllegalArgumentException("Wrong map width");}
        if(this.mapHeightInt<1){throw new IllegalArgumentException("Wrong map height");}
        if(!this.mapVariantStr.equals("Glob") && !this.mapVariantStr.equals("HellPortal")){throw new IllegalArgumentException("Wrong map variant");}
        if(this.startingNumberOfAnimalInt<1){throw new IllegalArgumentException("Wrong starting number of animals");}
        if(this.startingAnimalEnergyInt<1){throw new IllegalArgumentException("Wrong starting animal energy");}
        if(this.energyLostPerDayInt<1){throw new IllegalArgumentException("Wrong energy lost per day");}
        if(this.energyNeededForCopulationInt<1){throw new IllegalArgumentException("Wrong energy needed for copulation");}
        if(this.energyWastedOnNewAnimalInt<1){throw new IllegalArgumentException("Wrong energy wasted on new animal");}
        if(this.animalGenomeLengthInt<1){throw new IllegalArgumentException("Wrong animal genome length");}
        if(!this.mutationVariantStr.equals("FullRandGen") && !mutationVariantStr.equals("LightCorrectionGen")){throw new IllegalArgumentException("Wrong mutation variant");}
        if(this.minMaxMutationsInt.x<0 || this.minMaxMutationsInt.x>this.animalGenomeLengthInt || this.minMaxMutationsInt.y<0 ||
                this.minMaxMutationsInt.y>this.animalGenomeLengthInt || this.minMaxMutationsInt.x>this.minMaxMutationsInt.y){
            throw new IllegalArgumentException("Wrong min or max mutations number");
        }
        if(!this.animalBehaviorVariantStr.equals("FullPredestinationGen") && !this.animalBehaviorVariantStr.equals("RandomGen")){throw new IllegalArgumentException("Wrong animal behavior variant");}
        if(this.startingNumberOfGrassInt<0){throw new IllegalArgumentException("Wrong starting number of grass");}
        if(this.energyFromOneGrassInt<1){throw new IllegalArgumentException("Wrong energy from one grass");}
        if(this.numberOfGrassPerDayInt<0){throw new IllegalArgumentException("Wrong number of grass per day");}
        if(!this.grassGrowthVariantStr.equals("ForestedEquators") && !this.grassGrowthVariantStr.equals("ToxicCorpses")){throw new IllegalArgumentException("Wrong grass growth variant");}
        if(this.RefreshTimeInt<50){throw new IllegalArgumentException("Wrong refresh time");}
    }

    @Override
    public void start(Stage primaryStage){

        //CHOOSE CONFIGURATIONS
        this.chooseVariants.setOnAction((action) -> {
            try {
                setVariant(this.chooseVariants.getValue());
            } catch (IOException e) {
                out.println(e);
            }
        });

        //START APPLICATION BUTTON

        this.StartButton.setOnAction((click) -> {

            //isVerified for validation confirmation
            boolean isVerified=true;


            //TAKE DATA
            this.mapWidthInt= Integer.parseInt(this.MapWidth.getText());
            this.mapHeightInt= Integer.parseInt(this.MapHeight.getText());
            this.mapVariantStr=(String) this.MapVariant.getValue();
            this.energyFromOneGrassInt = Integer.parseInt(this.EnergyFromOneGrass.getText());
            this.numberOfGrassPerDayInt = Integer.parseInt(this.NumberOfGrassPerDay.getText());
            this.startingNumberOfGrassInt = Integer.parseInt(this.StartingNumberOfGrass.getText());
            this.grassGrowthVariantStr = (String) this.GrassGrowthVariant.getValue();
            this.startingNumberOfAnimalInt = Integer.parseInt(this.StartingNumberOfAnimals.getText());
            this.startingAnimalEnergyInt = Integer.parseInt(this.StartingAnimalEnergy.getText());
            this.energyNeededForCopulationInt = Integer.parseInt(this.EnergyNeededForCopulation.getText());
            this.energyWastedOnNewAnimalInt = Integer.parseInt(this.EnergyWastedOnNewAnimal.getText());
            this.minMaxMutationsInt = new Vector2d(Integer.parseInt(this.MinNumberOfMutationsInNewAnimal.getText()),Integer.parseInt(this.MaxNumberOfMutationsInNewAnimal.getText()));
            this.animalGenomeLengthInt = Integer.parseInt(this.AnimalGenomeLength.getText());
            this.animalBehaviorVariantStr = (String) this.AnimalsBehaviorVariant.getValue();
            this.mutationVariantStr = (String) this.MutationVariant.getValue();
            this.energyLostPerDayInt = Integer.parseInt(this.EnergyLostPerDay.getText());
            this.RefreshTimeInt= Integer.parseInt(this.RefreshTime.getText());

            //IF NOT VERIFIED SET FALSE
            try {
                this.verificationOfArguments();
            }catch(IllegalArgumentException exception){
                this.verificationMessage.setText(exception.getMessage());
                isVerified=false;
            }


            //START IF OK

            if(isVerified) {
                this.verificationMessage.setText("");
                AbstractWorldMap map = AbstractWorldMapFactory.getAbstractWorldMap(this.mapVariantStr,mapWidthInt, mapHeightInt, energyFromOneGrassInt, numberOfGrassPerDayInt
                        , startingNumberOfGrassInt, grassGrowthVariantStr, startingNumberOfAnimalInt, startingAnimalEnergyInt,
                        energyNeededForCopulationInt, energyWastedOnNewAnimalInt, minMaxMutationsInt,
                        animalGenomeLengthInt, animalBehaviorVariantStr, mutationVariantStr, energyLostPerDayInt);

                App app = null;
                try {
                    app = new App(map, RefreshTimeInt);
                    if (SaveStat.isSelected()) {
                        app.setDataCsv();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                app.start();
            }
        });


        Scene scene = new Scene(this.FinalStage,1000,1000);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                System.exit(0);
            }
        });

    }
}
