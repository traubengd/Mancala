import { GameState } from "../types";

export async function startGame(player1: string, player2: string) {
    const response = await fetch("mancala/api/start", {
        method: "POST",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            player1: player1,
            player2: player2,
        }),
    });

    if (response.ok) {
        const gameState = await response.json();
        return gameState as GameState;
    } else {
        return {
            statusCode: response.status,
            statusText: response.statusText
        };
    }
}

export async function playGamePit(pitIndex:Number) {
    const response = await fetch ("mancala/api/play", {
        method: "POST",
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            indexToPlay: pitIndex,
        }),
    });

    if (response.ok) {
        const gameState = await response.json();
        return gameState as GameState;
    } else {
        return {
            statusCode: response.status,
            statusText: response.statusText
        };
    }
}
