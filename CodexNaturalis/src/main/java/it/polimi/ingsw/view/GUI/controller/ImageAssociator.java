package it.polimi.ingsw.view.GUI.controller;
import it.polimi.ingsw.model.gameDataManager.*;
//questa classe serve ad associare gli oggetti del model con la loro rappresentazione png
//ImageAssociator viene impiegata per associare le carte con la loro rappresentazione grafica
//e anche per associare i maker con la loro rappresentazione grafica
//questa classe verrà poi impiegata nei controller della Gui per settare la grafica in base alla
//carta che è stata selezionata
public class ImageAssociator {

    public static String associatorPng2Card(String id, boolean isFront) {

        // Converte il valore booleano in una rappresentazione stringa
        String secondAttributeString = isFront ? "true" : "false";

        // Combina i valori degli attributi in un'unica stringa per lo switch-case
        String combinedAttributes = id + "-" + secondAttributeString;
        return switch (combinedAttributes) {
            case "1-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/001.png";
            case "1-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/001.png";
            case "2-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/002.png";
            case "2-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/002.png";
            case "3-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/003.png";
            case "3-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/003.png";
            case "4-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/004.png";
            case "4-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/004.png";
            case "5-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/005.png";
            case "5-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/005.png";
            case "6-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/006.png";
            case "6-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/006.png";
            case "7-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/007.png";
            case "7-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/007.png";
            case "8-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/008.png";
            case "8-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/008.png";
            case "9-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/009.png";
            case "9-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/009.png";
            case "10-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/010.png";
            case "10-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/010.png";
            case "11-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/011.png";
            case "11-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/011.png";
            case "12-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/012.png";
            case "12-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/012.png";
            case "13-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/013.png";
            case "13-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/013.png";
            case "14-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/014.png";
            case "14-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/014.png";
            case "15-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/015.png";
            case "15-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/015.png";
            case "16-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/016.png";
            case "16-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/016.png";
            case "17-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/017.png";
            case "17-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/017.png";
            case "18-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/018.png";
            case "18-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/018.png";
            case "19-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/019.png";
            case "19-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/019.png";
            case "20-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/020.png";
            case "20-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/020.png";
            case "21-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/021.png";
            case "21-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/021.png";
            case "22-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/022.png";
            case "22-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/022.png";
            case "23-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/023.png";
            case "23-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/023.png";
            case "24-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/024.png";
            case "24-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/024.png";
            case "25-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/025.png";
            case "25-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/025.png";
            case "26-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/026.png";
            case "26-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/026.png";
            case "27-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/027.png";
            case "27-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/027.png";
            case "28-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/028.png";
            case "28-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/028.png";
            case "29-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/029.png";
            case "29-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/029.png";
            case "30-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/030.png";
            case "30-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/030.png";
            case "31-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/031.png";
            case "31-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/031.png";
            case "32-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/032.png";
            case "32-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/032.png";
            case "33-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/033.png";
            case "33-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/033.png";
            case "34-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/034.png";
            case "34-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/034.png";
            case "35-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/035.png";
            case "35-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/035.png";
            case "36-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/036.png";
            case "36-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/036.png";
            case "37-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/037.png";
            case "37-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/037.png";
            case "38-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/038.png";
            case "38-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/038.png";
            case "39-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/039.png";
            case "39-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/039.png";
            case "40-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/040.png";
            case "40-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/040.png";
            case "41-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/041.png";
            case "41-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/041.png";
            case "42-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/042.png";
            case "42-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/042.png";
            case "43-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/043.png";
            case "43-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/043.png";
            case "44-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/044.png";
            case "44-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/044.png";
            case "45-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/045.png";
            case "45-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/045.png";
            case "46-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/046.png";
            case "46-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/046.png";
            case "47-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/047.png";
            case "47-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/047.png";
            case "48-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/048.png";
            case "48-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/048.png";
            case "49-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/049.png";
            case "49-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/049.png";
            case "50-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/050.png";
            case "50-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/050.png";
            case "51-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/051.png";
            case "51-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/051.png";
            case "52-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/052.png";
            case "52-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/052.png";
            case "53-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/053.png";
            case "53-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/053.png";
            case "54-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/054.png";
            case "54-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/054.png";
            case "55-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/055.png";
            case "55-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/055.png";
            case "56-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/056.png";
            case "56-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/056.png";
            case "57-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/057.png";
            case "57-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/057.png";
            case "58-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/058.png";
            case "58-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/058.png";
            case "59-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/059.png";
            case "59-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/059.png";
            case "60-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/060.png";
            case "60-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/060.png";
            case "61-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/061.png";
            case "61-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/061.png";
            case "62-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/062.png";
            case "62-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/062.png";
            case "63-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/063.png";
            case "63-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/063.png";
            case "64-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/064.png";
            case "64-false" -> "src/main/resources/images/images /images/CODEX_cards_gold_back/064.png";
            case "65-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/065.png";
            case "65-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/065.png";
            case "66-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/066.png";
            case "66-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/066.png";
            case "67-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/067.png";
            case "67-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/067.png";
            case "68-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/068.png";
            case "68-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/068.png";
            case "69-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/069.png";
            case "69-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/069.png";
            case "70-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/070.png";
            case "70-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/070.png";
            case "71-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/071.png";
            case "71-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/071.png";
            case "72-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/072.png";
            case "72-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/072.png";
            case "73-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/073.png";
            case "73-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/073.png";
            case "74-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/074.png";
            case "74-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/074.png";
            case "75-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/075.png";
            case "75-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/075.png";
            case "76-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/076.png";
            case "76-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/076.png";
            case "77-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/077.png";
            case "77-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/077.png";
            case "78-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/078.png";
            case "78-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/078.png";
            case "79-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/079.png";
            case "79-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/079.png";
            case "80-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/080.png";
            case "80-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/080.png";
            case "81-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/081.png";
            case "81-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/081.png";
            case "82-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/082.png";
            case "82-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/082.png";
            case "83-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/083.png";
            case "83-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/083.png";
            case "84-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/084.png";
            case "84-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/084.png";
            case "85-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/085.png";
            case "85-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/085.png";
            case "86-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/086.png";
            case "86-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/086.png";
            case "87-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/087.png";
            case "87-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/087.png";
            case "88-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/088.png";
            case "88-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/088.png";
            case "89-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/089.png";
            case "89-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/089.png";
            case "90-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/090.png";
            case "90-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/090.png";
            case "91-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/091.png";
            case "91-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/091.png";
            case "92-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/092.png";
            case "92-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/092.png";
            case "93-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/093.png";
            case "93-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/093.png";
            case "94-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/094.png";
            case "94-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/094.png";
            case "95-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/095.png";
            case "95-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/095.png";
            case "96-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/096.png";
            case "96-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/096.png";
            case "97-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/097.png";
            case "97-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/097.png";
            case "98-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/098.png";
            case "98-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/098.png";
            case "99-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/099.png";
            case "99-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/099.png";
            case "100-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/100.png";
            case "100-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/100.png";
            case "101-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/101.png";
            case "101-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/101.png";
            case "102-true" -> "src/main/resources/images/images/images/CODEX_cards_gold_front/102.png";
            case "102-false" -> "src/main/resources/images/images/images/CODEX_cards_gold_back/102.png";
            default -> throw new IllegalStateException("Unexpected value: " + combinedAttributes);
        };
    }

    public static String makerAssociator(Color color){
        return switch(color){
            case YELLOW -> "src/main/resources/images/images/images/CODEX_pion_jaune.png";
            case BLACK -> "src/main/resources/images/images/images/CODEX_pion_noir.png";
            case RED -> "src/main/resources/images/images/images/CODEX_pion_rouge.png";
            case GREEN -> "src/main/resources/images/images/images/CODEX_pion_vert.png";
            case BLUE -> "src/main/resources/images/images/images/CODEX_pion_bleu.png";
        };


    }


}
