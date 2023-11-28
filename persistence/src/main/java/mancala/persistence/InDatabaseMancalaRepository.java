package mancala.persistence;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;

import mancala.domain.IMancala;

public class InDatabaseMancalaRepository implements IMancalaRepository {

    @Override
    public void save(String key, IMancala game) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(game);
            oos.flush();
            byte[] gameData = bos.toByteArray();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mancala", "root", "sogyo");
            Statement statement;
            Blob gameBlob = connection.createBlob();
            gameBlob.setBytes(1, gameData);
            statement = connection.createStatement();
            statement.executeQuery("INSERT mancala.games (game, gamekey) VALUES (" + gameBlob + ", " + key + ")");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public IMancala get(String key) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mancala", "root", "sogyo");
            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("SELECT game FROM mancala.games WHERE gamekey=" + key);
            byte[] gameData = (byte[]) resultSet.getObject(0);
            ByteArrayInputStream baip = new ByteArrayInputStream(gameData);
            ObjectInputStream ois = new ObjectInputStream(baip);
            IMancala game = (IMancala) ois.readObject();
            return game;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}
