package mancala.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import jakarta.servlet.http.*;
import jakarta.ws.rs.core.*;
import mancala.api.models.*;
import mancala.domain.IMancalaFactory;
import mancala.domain.MancalaFactory;
import mancala.persistence.IMancalaRepository;
import mancala.persistence.InMemoryMancalaRepository;

public class MancalaControllerTest {

    private MancalaController controller;
    private HttpSession session;
    private String gameId;

    @BeforeEach
    private void setUp() {
        createMockSession();
        createController();
    }

    @Test
    public void startingMancalaIsAllowed() {
        var response = executeStartRequest();

        assertEquals(200, response.getStatus());
    }

    @Test
    public void startingMancalaWithAnEmptyRequestIsNotAllowed() {
        var response = executeEmptyStartRequest();

        assertEquals(400, response.getStatus());
    }

    @Test
    public void startingMancalaWithAnEmptyPlayerNamesIsNotAllowed() {
        var response = executeEmptyPlayerNamesStartRequest();

        assertEquals(400, response.getStatus());
    }

    @Test
    public void startingMancalaWithSamePlayerNamesIsNotAllowed() {
        var response = executeSameNameStartRequest();

        assertEquals(400, response.getStatus());
    }

    @Test
    public void startingMancalaReturnsAGameWithoutAWinner() {
        var response = executeStartRequest();
        var responseBody = (MancalaDTO) response.getEntity();
        var gameState = responseBody.getGameStatus();

        assertFalse(gameState.getEndOfGame());
        assertNull(gameState.getWinner());
    }

    @Test
    public void startingMancalaReturnsThePlayerData() {
        var response = executeStartRequest();
        var responseBody = (MancalaDTO) response.getEntity();
        var players = responseBody.getPlayers();

        assertEquals(2, players.length);
        assertEquals("Mario", players[0].getName());
        assertEquals("Luigi", players[1].getName());
    }

    @Test
    public void startingMancalaReturnsThePits() {
        var response = executeStartRequest();
        var responseBody = (MancalaDTO) response.getEntity();
        var players = responseBody.getPlayers();

        assertEquals(7, players[0].getPits().length);
        assertEquals(0, players[0].getPits()[0].getIndex());
        assertEquals(7, players[1].getPits().length);
        assertEquals(7, players[1].getPits()[0].getIndex());
    }

    @Test
    public void startingMancalaSavesAGameId() {
        executeStartRequest();

        assertNotNull(gameId);
    }

    @Test
    public void playingABowlIsAllowed() {
        executeStartRequest();
        var response = executePlayRequest(0);

        assertEquals(200, response.getStatus());
    }

    @Test
    public void playingANotExistingBowlIsNotAllowed() {
        executeStartRequest();
        var responseTooHigh = executePlayRequest(16);
        var responseNegative = executePlayRequest(-2);

        assertEquals(400, responseTooHigh.getStatus());
        assertEquals(400, responseNegative.getStatus());
    }

    @Test
    public void playingAKalahaIsNotAllowed() {
        executeStartRequest();
        var responseFirstPlayerKalaha = executePlayRequest(6);
        var responseSecondPlayerKalaha = executePlayRequest(13);

        assertEquals(400, responseFirstPlayerKalaha.getStatus());
        assertEquals(400, responseSecondPlayerKalaha.getStatus());
    }

    @Test
    public void playingAnOpponentBowlIsNotAllowed() {
        executeStartRequest();
        var response = executePlayRequest(8);

        assertEquals(400, response.getStatus());
    }

    @Test
    public void playingAnEmptyBowlIsNotAllowed() {
        executeStartRequest();
        executePlayRequest(0);
        executePlayRequest(7);
        var response = executePlayRequest(0);

        assertEquals(400, response.getStatus());
    }

    @Test
    public void playingABowlYieldsAnUpdatedGameState() {
        executeStartRequest();
        var response = executePlayRequest(0);
        var responseBody = (MancalaDTO) response.getEntity();
        var player1Pits = responseBody.getPlayers()[0].getPits();

        assertEquals(0, player1Pits[0].getNrOfStones());
        assertEquals(5, player1Pits[1].getNrOfStones());
        assertEquals(5, player1Pits[2].getNrOfStones());
        assertEquals(5, player1Pits[3].getNrOfStones());
        assertEquals(5, player1Pits[4].getNrOfStones());
        assertEquals(4, player1Pits[5].getNrOfStones());
    }

    @Test
    public void doingMultipleMovesIsAllowed() {
        executeStartRequest();
        executePlayRequest(0);
        var response = executePlayRequest(7);

        assertEquals(200, response.getStatus());
    }

    private void createController() {
        IMancalaFactory factory = new MancalaFactory();
        IMancalaRepository repository = new InMemoryMancalaRepository();
        controller = new MancalaController(factory, repository);
    }

    private void createMockSession() {
        session = mock(HttpSession.class);
        doAnswer(invocation -> {
            if (invocation.getArgument(0).equals("gameId")) {
                gameId = invocation.getArgument(1);
            }
            return null;
        }).when(session).setAttribute(any(String.class), any(String.class));
        when(session.getAttribute("gameId")).thenAnswer(invocation -> gameId);
    }

    private Response executeStartRequest() {
        var request = mock(HttpServletRequest.class);
        when(request.getSession(any(Boolean.class))).thenReturn(session);
        return controller.start(request, startInput("Mario", "Luigi"));
    }

    //illegal start request
    private Response executeEmptyStartRequest() {
        var request = mock(HttpServletRequest.class);
        when(request.getSession(any(Boolean.class))).thenReturn(session);
        return controller.start(request, null);
    }

    //illegal start request
    private Response executeEmptyPlayerNamesStartRequest() {
        var request = mock(HttpServletRequest.class);
        when(request.getSession(any(Boolean.class))).thenReturn(session);
        return controller.start(request, startInput(null, null));
    }

    //illegal start request
    private Response executeSameNameStartRequest() {
        var request = mock(HttpServletRequest.class);
        when(request.getSession(any(Boolean.class))).thenReturn(session);
        return controller.start(request, startInput("Mario", "Mario"));
    }

    private Response executePlayRequest(int indexToPlay) {
        var request = mock(HttpServletRequest.class);
        when(request.getSession(any(Boolean.class))).thenReturn(session);
        return controller.play(request, playInput(indexToPlay));
    }

    private StartInputDTO startInput(String player1, String player2) {
        var input = new StartInputDTO();
        input.setPlayer1(player1);
        input.setPlayer2(player2);
        return input;
    }

    private PlayInputDTO playInput(int indexToPlay) {
        var input = new PlayInputDTO();
        input.setIndexToPlay(indexToPlay);
        return input;
    }
}