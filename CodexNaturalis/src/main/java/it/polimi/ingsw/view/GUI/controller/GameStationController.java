package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.model.gameDataManager.Color;
import it.polimi.ingsw.view.GUI.Gui;
import it.polimi.ingsw.view.input.InputGui;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.event.*;
import javafx.scene.text.Text;

import static it.polimi.ingsw.view.GUI.ImageAssociator.associatorPng2Card;
import static it.polimi.ingsw.view.GUI.ImageAssociator.makerAssociator;
//non è definitivo ma è una buona base
public class GameStationController extends InputGui {
    @FXML
    private Text gameId;
    @FXML
    private Tab secondPlayer;
    @FXML
    private Pane anchor1;
    @FXML
    private Pane anchor2;
    @FXML
    private ImageView firstCard;
    @FXML
    private ImageView secondCard;
    @FXML
    private ImageView thirdCard;
    @FXML
    private ImageView firstCardBack;
    @FXML
    private ImageView secondCardBack;
    @FXML
    private ImageView thirdCardBack;
    @FXML
    private Text yourLastTurn;
    //sono tutte le immagini che conterrano i maker nella point table
    @FXML
    private ImageView image0;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;
    @FXML
    private ImageView image4;
    @FXML
    private ImageView image5;
    @FXML
    private ImageView image6;
    @FXML
    private ImageView image7;
    @FXML
    private ImageView image8;
    @FXML
    private ImageView image9;
    @FXML
    private ImageView image10;
    @FXML
    private ImageView image11;
    @FXML
    private ImageView image12;
    @FXML
    private ImageView image13;
    @FXML
    private ImageView image14;
    @FXML
    private ImageView image15;
    @FXML
    private ImageView image16;
    @FXML
    private ImageView image17;
    @FXML
    private ImageView image18;
    @FXML
    private ImageView image19;
    @FXML
    private ImageView image20;
    @FXML
    private ImageView image21;
    @FXML
    private ImageView image22;
    @FXML
    private ImageView image23;
    @FXML
    private ImageView image24;
    @FXML
    private ImageView image25;
    @FXML
    private ImageView image26;
    @FXML
    private ImageView image27;
    @FXML
    private ImageView image28;
    //è un array che contiene tuelle le imageview della pointTable
    //mi serve per poter selezionare la imageview corrispondente al punteggio
    private ImageView[] imageViews = new ImageView[29];

    public void setSecondPlayer(String name){
        secondPlayer.setText(name);
    }

    //mi aggiorna il contenuto di gameId(serve a settare id del game nella gamestation)
    public void setGameId(String id){
        gameId.setText(id);
    }

    // mi crea la grindPane per 2 giocatori(per ora funziona solo per 2 giocoatori) inoltre mi inserisce le carte iniziali nella gamestation
    public void initialize(int num1, int num2, boolean front1, boolean front2, Color color1, Color color2) {
        //rende invisibile il messaggio di last turn
        yourLastTurn.setVisible(false);
        //crea le GridPane
        GridPane gridPane1 = new GridPane();
        GridPane gridPane2 = new GridPane();
        // Dimensiona la GridPane
        gridPane1.setPrefSize(1842, 938);
        gridPane1.setPrefSize(1842, 938);
        for (int row = 0; row < 24; row++) {
            for (int col = 0; col < 24; col++) {
                    if(row == 12 && col == 12){ //mi agginge le carte inziali a le gamestation dei giocatori
                        //mi associa il file png alla Imageview e mi setta le dimensioni
                        ImageView imageView1 = createImageView(associatorPng2Card(String.valueOf(num1), front1));
                        ImageView imageView2 = createImageView(associatorPng2Card(String.valueOf(num1), front2));
                        //la inserisco nella gridPane
                        gridPane1.setRowIndex(imageView1, row);
                        gridPane2.setColumnIndex(imageView1, col);
                        gridPane2.setRowIndex(imageView2, row);
                        gridPane2.setColumnIndex(imageView2, col);
                        gridPane1.getChildren().add(imageView1);
                        gridPane2.getChildren().add(imageView2);
                    }else{
                        ImageView imageView1 = new ImageView();
                        ImageView imageView2 = new ImageView();
                        imageView1.setFitHeight(41.0);
                        imageView1.setFitWidth(77.0);
                        imageView2.setFitHeight(41.0);
                        imageView2.setFitWidth(77.0);
                        gridPane1.setRowIndex(imageView1, row);
                        gridPane1.setColumnIndex(imageView1, col);
                        gridPane2.setRowIndex(imageView2, row);
                        gridPane2.setColumnIndex(imageView2, col);
                        gridPane1.getChildren().add(imageView1);
                        gridPane1.getChildren().add(imageView1);
                        imageView1.setOnMouseClicked(primaryMouseEvent -> {
                                    if (primaryMouseEvent.getButton() == MouseButton.PRIMARY) {
                                        // Se il mouse sinistro è stato cliccato:
                                        //ottengo l'id e il verso della carta pescata dalla multipleResponses in gui tramite getLast
                                         Image imageCard = new Image(associatorPng2Card(multipleResponses.getLast(), Boolean.parseBoolean(multipleResponses.get(multipleResponses.size()-2))));
                                        imageView1.setImage(imageCard);
                                        imageView1.setFitWidth(100);  // Imposta la larghezza
                                        imageView1.setFitHeight(100);
                                        imageView1.setOnMouseClicked(event -> {
                                            event.consume(); // Consuma l'evento per evitare ulteriori azioni
                                        });
                                    }
                                });
                        imageView2.setOnMouseClicked(primaryMouseEvent -> {
                        if (primaryMouseEvent.getButton() == MouseButton.PRIMARY) {
                            Image imageCard = new Image(associatorPng2Card(multipleResponses.getLast(), Boolean.parseBoolean(multipleResponses.get(multipleResponses.size()-2))));
                            imageView2.setImage(imageCard);
                            imageView2.setFitWidth(100);  // Imposta la larghezza
                            imageView2.setFitHeight(100);
                            imageView2.setOnMouseClicked(event -> {
                                event.consume(); // Consuma l'evento per evitare ulteriori azioni
                            });
                            }
                        });
                    }
        }
        }
                    //metto nella gamestation anche il segnalino(compreso quello nero)
        ImageView imageView3 = createImageView(makerAssociator(color1));
        ImageView imageView4 = createImageView(makerAssociator(color2));
        ImageView imageView5 = createImageView(makerAssociator(Color.BLACK));
        imageView3.setFitHeight(20.0);
        imageView3.setFitWidth(20.0);
        imageView4.setFitHeight(20.0);
        imageView4.setFitWidth(20.0);
        imageView5.setFitHeight(20.0);
        imageView5.setFitWidth(20.0);
        //imposto il layout di imageview3 e imagevie4
        imageView3.setLayoutX(911);
        imageView3.setLayoutY(459);
        imageView4.setLayoutX(911);
        imageView4.setLayoutY(459);
        imageView5.setLayoutX(931);
        imageView5.setLayoutY(480);
        //aggiungo i maker al pane
        anchor1.getChildren().add(imageView3);
        anchor2.getChildren().add(imageView4);
        anchor2.getChildren().add(imageView5);
        //aggiungo le gridPane all pane che le contiene
        anchor1.getChildren().add(gridPane1);
        anchor2.getChildren().add(gridPane2);
    }
    //crea un immagine e ne assegna le dimensioni, restituisce un immagine che verrà settata per le carte iniziali
    private ImageView createImageView(String imagePath) {
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(50);
        imageView.setFitHeight(90);

    // Gestione dell'evento del click del mouse sull'ImageView (disabilitata temporaneamente)
    imageView.setOnMouseClicked(event -> {
    event.consume(); // Consuma l'evento per evitare ulteriori azioni
    });
    return imageView;
    }
    //mi setta la mano in base alla carta pescata utilizzando il suo num e la posizione in cui devo sostituire la carta
    public void setHand(int num, int pos){
        if(pos == 1){
            Image imageFront = new Image(associatorPng2Card(String.valueOf(num), true));
            Image imageBack = new Image(associatorPng2Card(String.valueOf(num), false));
            firstCard.setImage(imageFront);
            firstCardBack.setImage(imageBack);
            firstCard.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    //aggiungo l'id della carta e il suo verso nella multiple responses(serve per capire quale voglio mettere nella gamestation)
                    multipleResponses.add(String.valueOf(num));
                    multipleResponses.add(String.valueOf(true));
                }
            });
            firstCardBack.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    //aggiungo l'id della carta e il suo verso nella multiple responsesserve per capire quale voglio mettere nella gamestation)
                    multipleResponses.add(String.valueOf(num));
                    multipleResponses.add(String.valueOf(false));
                }
            });
        }else if(pos == 2){
            Image imageFront = new Image(associatorPng2Card(String.valueOf(num), true));
            Image imageBack = new Image(associatorPng2Card(String.valueOf(num), false));
            secondCard.setImage(imageFront);
            secondCardBack.setImage(imageBack);
            secondCard.setOnMouseClicked(event -> {
                        if (event.getButton() == MouseButton.PRIMARY) {
                            //aggiungo l'id della carta e il suo verso nella multiple responses(serve per capire quale voglio mettere nella gamestation)
                            multipleResponses.add(String.valueOf(num));
                            multipleResponses.add(String.valueOf(true));
                        }
            });
            secondCardBack.setOnMouseClicked(event -> {
                        if (event.getButton() == MouseButton.PRIMARY) {
                            //aggiungo l'id della carta e il suo verso nella multiple responses(serve per capire quale voglio mettere nella gamestation)
                            multipleResponses.add(String.valueOf(num));
                            multipleResponses.add(String.valueOf(false));
                        }
            });
        }else{
            Image imageFront = new Image(associatorPng2Card(String.valueOf(num), true));
            Image imageBack = new Image(associatorPng2Card(String.valueOf(num), false));
            thirdCard.setImage(imageFront);
            thirdCardBack.setImage(imageBack);
            thirdCard.setOnMouseClicked(event -> {
                        if (event.getButton() == MouseButton.PRIMARY) {
                            //aggiungo l'id della carta e il suo verso nella multiple responses(serve per capire quale voglio mettere nella gamestation)
                            multipleResponses.add(String.valueOf(num));
                            multipleResponses.add(String.valueOf(true));
                        }
                    });
                    thirdCardBack.setOnMouseClicked(event -> {
                        if (event.getButton() == MouseButton.PRIMARY) {
                            //aggiungo l'id della carta e il suo verso nella multiple responses(serve per capire quale voglio mettere nella gamestation)
                            multipleResponses.add(String.valueOf(num));
                            multipleResponses.add(String.valueOf(false));
                        }
                    });
        }

    }
    //mi rende visibile il messaggio che è l'ultimo turno
    public void setLastTurn(){
        yourLastTurn.setVisible(true);

    }
    //inserisce le image0 all'interno del imageView array
    //il tag@FXML indica che questo metodo verrà lanciato in automatico quando si aprirà l'interfaccia associata a questo controller
    @FXML
    public void initializeImageArray(){
        imageViews[0] = image0;
        imageViews[1] = image1;
        imageViews[2] = image2;
        imageViews[3] = image3;
        imageViews[4] = image4;
        imageViews[5] = image5;
        imageViews[6] = image6;
        imageViews[7] = image7;
        imageViews[8] = image8;
        imageViews[9] = image9;
        imageViews[10] = image10;
        imageViews[11] = image11;
        imageViews[12] = image12;
        imageViews[13] = image13;
        imageViews[14] = image14;
        imageViews[15] = image15;
        imageViews[16] = image16;
        imageViews[17] = image17;
        imageViews[18] = image18;
        imageViews[19] = image19;
        imageViews[20] = image20;
        imageViews[21] = image21;
        imageViews[22] = image22;
        imageViews[23] = image23;
        imageViews[24] = image24;
        imageViews[25] = image25;
        imageViews[26] = image26;
        imageViews[27] = image27;
        imageViews[28] = image28;
    }

    //assegna alla imageview corrispondende al punteggio il maker del colore adeguato
    public void MakerInPointTable(Color color, int point){
        ImageView image = imageViews[point];
        Image imageMaker = new Image(makerAssociator(color));
        image.setImage(imageMaker);
    }

}