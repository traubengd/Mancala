package mancala.persistence;

import java.util.HashMap;

import mancala.domain.IMancala;

public class InMemoryMancalaRepository implements IMancalaRepository {
    HashMap<String, IMancala> mancalaGames = new HashMap<String, IMancala>();

    @Override
    public void save(String key, IMancala game) {
        mancalaGames.put(key, game);
    }

    @Override
    public IMancala get(String key) {
        return mancalaGames.get(key);
    }
    
}
