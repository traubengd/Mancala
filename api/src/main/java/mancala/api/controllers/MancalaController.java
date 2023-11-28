package mancala.api.controllers;

import java.util.UUID;

import jakarta.servlet.http.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import mancala.api.models.*;
import mancala.domain.IMancala;
import mancala.domain.IMancalaFactory;
import mancala.domain.IllegalMoveException;
import mancala.persistence.IMancalaRepository;

@Path("/mancala/api")
public class MancalaController {

    private IMancalaFactory factory;
    private IMancalaRepository repository;

    public MancalaController(IMancalaFactory factory, IMancalaRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    @Path("/start")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response start(@Context HttpServletRequest request, StartInputDTO body) {
        if (body == null || body.getPlayer1() == null || body.getPlayer2() == null
                || body.getPlayer1().equals(body.getPlayer2())) {
            return Response.status(400).build();
        } else {
            // Create HTTP session.
            HttpSession session = request.getSession(true);
            // Initialize game.
            IMancala mancala = factory.createNewGame(body.getPlayer1(), body.getPlayer2());

            // Create a unique ID for this game.
            String gameId = UUID.randomUUID().toString();

            // Save the ID in the HTTP session.
            session.setAttribute("gameId", gameId);

            // Save the game in the database.
            repository.save(gameId, mancala);

            // Use the game to create a DTO.
            var output = new MancalaDTO(mancala);

            // Send DTO back in response.
            return Response.status(200).entity(output).build();
        }
    }

    @Path("/play")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response play(@Context HttpServletRequest request, PlayInputDTO body) {
        if (body == null || body.getIndexToPlay() > 13 || body.getIndexToPlay() < 0) {
            return Response.status(400).build();
        } else {
            // Retrieve HTTP session.
            HttpSession session = request.getSession(false);

            // Retrieve game ID.
            String gameId = (String) session.getAttribute("gameId");

            // Retrieve the game from the database
            IMancala mancala = repository.get(gameId);

            try {
                // Play a pit.
                mancala.playPit(body.getIndexToPlay());

                // Save the updated game in the database
                repository.save(gameId, mancala);

                // Use the game to create a DTO.
                MancalaDTO output = new MancalaDTO(mancala);

                // Send DTO back in response.
                return Response.status(200).entity(output).build();
            } catch (IllegalMoveException e) {
                return Response.status(400).build();
            }
        }
    }
}
