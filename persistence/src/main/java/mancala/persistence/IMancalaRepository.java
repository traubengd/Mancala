package mancala.persistence;

import mancala.domain.IMancala;

public interface IMancalaRepository {
    /**
     * Method to save a Mancala game under a given key
     * 
     * @param key  The key under which the game should be saved
     * @param game The game to be saved
     */
    void save(String key, IMancala game);

    /**
     * Method to retrieve a Mancala game under a given key
     * 
     * @param key The key for retrieval
     * @return The game saved under the key
     */
    IMancala get(String key);

}
