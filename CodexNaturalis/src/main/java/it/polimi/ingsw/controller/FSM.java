package it.polimi.ingsw.controller;

//qui ci sta tutta la roba del controller: quindi gli stati del gioco processa le richieste del client
// e aggiorna il model

public class FSM {
    private State state;

    public FSM(){
//        state = new StartingState(this);
    }

    public FSM(State state){
        //si crea uno stato di quel tipo si potrebbe utilizzare uno switch o qualche libreria che
        //costruisce uno stato del tipo adatto.
//        state = new State();
    }

    //metodo per cambiare stato
    public void changeState(State state){
        this.state = state;
    }

    //bisogna definire quali metodi sono generati dall'utente (tutti)
    //qui ci sono i metodi tipo clickOnDeck ecc..

    // qui sotto vengono implementati eventuali metodi di servizio specifici di un
    // determinato contesto.

}
