package it.polimi.ingsw.view.GUI;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;


/**
 * this class is useful to pass information between different classes without increase the coupling.
 * it is similar to Class.MultipleResponses, but I prefer to have two different classes for two different tasks.
 * This class now collects only card records, but it could be easily extended to manage other types of data.
 * @author Alessandro Lodetti
 */
public class DbCardInfo {
    private static DbCardInfo dbCardInfo;
    private final ConcurrentHashMap<String, LinkedBlockingQueue<CardRecord>> cardsRecordsMap;
    private final ConcurrentHashMap<String, LinkedBlockingQueue<List<String>>> winnersRecordsMap;

    private DbCardInfo() {
        this.cardsRecordsMap = new ConcurrentHashMap<>();
        this.winnersRecordsMap = new ConcurrentHashMap<>();
    }

    // Singleton instance getter
    public static synchronized DbCardInfo getInstance() {
        if (dbCardInfo == null) {
            dbCardInfo = new DbCardInfo();
        }
        return dbCardInfo;
    }

    /**
     * Add a card record which contains some useful information especially for the GUI
     *
     * @param cardRecord the record
     * @param gameId     the game identifier
     */
    public void addCardRecord(CardRecord cardRecord, String gameId) {
        try {
            // Fetch or create the queue for the given gameId
            LinkedBlockingQueue<CardRecord> queue = cardsRecordsMap.computeIfAbsent(gameId, k -> new LinkedBlockingQueue<>());
            // Add the card record to the queue
            queue.put(cardRecord);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Reset the interrupted status
            throw new RuntimeException("Thread was interrupted while adding record", e);
        }
    }

    /**
     * Reads the record for a given gameId: be careful that this method blocks the calling thread until there is a
     * readable record
     *
     * @param gameId the game identifier
     * @return the read card record
     */
    public CardRecord readCardRecord(String gameId) {
        try {
            // Fetch the queue for the given gameId
            LinkedBlockingQueue<CardRecord> queue = cardsRecordsMap.get(gameId);
            if (queue != null) {
                // Take the card record from the queue
                return queue.take();
            } else {
                throw new RuntimeException("No records found for gameId: " + gameId);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Reset the interrupted status
            throw new RuntimeException("Thread was interrupted while reading record", e);
        }
    }

    /**
     * Add the list of winners to the DB: useful to pass arguments without increasing coupling
     *
     * @param winners the list of winners
     * @param gameId  the game identifier
     */
    public void addWinners(List<String> winners, String gameId) {
        try {
            LinkedBlockingQueue<List<String>> queue = winnersRecordsMap.computeIfAbsent(gameId, k -> new LinkedBlockingQueue<>());
            queue.put(winners);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while adding winners", e);
        }
    }

    /**
     * Reads the list of winners for a given gameId: be careful that this method blocks the calling thread
     * until there is a readable list
     *
     * @param gameId the game identifier
     * @return the list of winners
     */
    public List<String> readWinners(String gameId) {
        try {
            LinkedBlockingQueue<List<String>> queue = winnersRecordsMap.get(gameId);
            if (queue != null) {
                return queue.take();
            } else {
                throw new RuntimeException("No winners found for gameId: " + gameId);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while reading winners", e);
        }
    }
}
