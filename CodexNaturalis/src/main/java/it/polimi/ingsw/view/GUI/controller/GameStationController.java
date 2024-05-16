package it.polimi.ingsw.view.GUI.controller;

import it.polimi.ingsw.view.GUI.MultipleResponses;
/** Non ho idea se posso funzionare
public class GameStationController extends MultipleResponses {
    @FXML
    private TabPane gamestation;
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

    @FXML // mi crea la grindPane per 2 giocatori(per ora funziona solo per 2 giocoatori)
    public void initialize(int num1, int num2, boolean front1, boolean front2, Color color1, Color color2) {
        GridPane gridPane1 = new GridPane();
        GridPane gridPane2 = new GridPane();
        // Dimensiona la GridPane
        gridPane.setPrefSize(1842, 938);
        gridPane.setPrefSize(1842, 938);
        for (int row = 0; row < 24; row++) {
            for (int col = 0; col < 24; col++) {
                    if(row == 12 && col == 12){ //mi agginge le carte inziali a le gamestation dei giocatori
                        //mi associa il file png alla Imageview
                        ImageView imageView1 = createImageView(associatorPng2Card(num1, front1));
                        Imageview imageView2 = createImageView(associatorPng2Card(num1, front1));
                        //setto le dimensioni
                        imageView3.setFitHeight(20.0);
                        imageView4.setFitWidth(20.0);
                        //la inserisco nella gridPane
                        gridPane1.setRowIndex(imageView1, row);
                        gridPane1.setColumnIndex(imageView1, col);
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
                imageView1.setOnMouseClicked(event -> {
                            if (event.getButton() == MouseButton.PRIMARY) {
                                // Se il mouse sinistro è stato cliccato:
                                //Bisogna estendere GamestationController con gameflow  e bisogna
                                //passare come argomento di questo metodo le coordinate, questo metodo da aggiungere al gameflow si occuperà di chiamare
                                //placecard del client con l'id la carta selezionata.Infine questo metodo
                                //restituisce l'id della carta e il verso, qui chiamarero poi chiama associator2Card

                            }
                        }
                imageView2.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                            // Se il mouse sinistro è stato cliccato:
                            //Bisogna estendere GamestationController con gameflow  e bisogna
                            //passare come argomento di questo metodo le coordinate, questo metodo da aggiungere al gameflow si occuperà di chiamare
                            //placecard del client con l'id la carta selezionata.Infine questo metodo
                            //restituisce l'id della carta e il verso, qui chiamarero poi chiama associator2Card

                    }
                }
            }
        }
        }
                    //metto nella gamestation anche il segnalino
        ImageView imageView3 = createImageView(amakerAssociator(color1));
        Imageview imageView4 = createImageView(makerAssociator(color2));
        imageView3.setFitHeight(20.0);
        imageView4.setFitWidth(20.0);
        imageView3.setLayoutX(911);
        imageView3.setLayoutY(459);
        imageView4.setLayoutX(911);
        imageView4.setLayoutY(459);
        anchor3.setContent(imageView3);
        anchor4.setContent(imageView4);
        //aggiungo le gridPane all pane che le contiene
        anchor1.setContent(gridPane1);
        anchor2.setContent(gridPane2);
    }
            //crea un immagine e ne assegna le dimensioni
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
            Image imageFront = new Image(associatorPng2Card(num1, true));
            Image imageBack = new Image(associatorPng2Card(num1, false));
            firstCard.setImage(imageFront);
            firstCardBack.setImage(imageBack);

        }else if(pos = 2){
            Image imageFront = new Image(associatorPng2Card(num1, true));
            Image imageBack = new Image(associatorPng2Card(num1, false));
            secondCard.setImage(imageFront);
            secondCardBack.setImage(imageBack);
        }else{
            Image imageFront = new Image(associatorPng2Card(num1, true));
            Image imageBack = new Image(associatorPng2Card(num1, false));
            thirdCard.setImage(imageFront);
            thirdCardBack.setImage(imageBack);
        }

        }

}
*/