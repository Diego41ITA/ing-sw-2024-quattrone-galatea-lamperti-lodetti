package it.polimi.ingsw.view.GUI;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * this class is useful to pass information between different classes without increase the coupling.
 * it is similar to Class.MultipleResponses, but I prefer to have two different classes for two different tasks.
 * This class now collects only card records, but it could be easily extended to manage other types of data.
 * @author Alessandro Lodetti
 */
public class DbCardInfo {
    private static DbCardInfo dbCardInfo;
    private LinkedBlockingQueue<CardRecord> cardRecords;

    private DbCardInfo(){
        this.cardRecords = new LinkedBlockingQueue<>();
    }

    public static DbCardInfo getInstance(){
        if(DbCardInfo.dbCardInfo == null){
            DbCardInfo.dbCardInfo = new DbCardInfo();
        }
        return DbCardInfo.dbCardInfo;
    }

    public void addRecord(CardRecord cardRecord){
        try {
            this.cardRecords.put(cardRecord);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public CardRecord readCardRecord(){
        try {
            return this.cardRecords.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
