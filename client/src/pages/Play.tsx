import { useMancalaGame } from "../contexts/MancalaGameContext";
import { useState } from "react";
import { isGameState } from "../types";
import { playGamePit } from "../services/api";
import { startGame } from "../services/api";
import { Alert } from "../components/Alert";
import classNames from "classnames";

export const Play = () => {
    const { gameState, setGameState } = useMancalaGame();
    const [alert, setAlert] = useState<string | null>(null);

    const playPit = async (pitIndex: Number) => {
        const result = await playGamePit(pitIndex);

        if (isGameState(result)) {
            setGameState(result);
        } else {
            setAlert(`${result.statusCode} ${result.statusText}`);
        }
    }

    const restartGame = async () => {
        const result = await startGame(gameState!.players[0].name, gameState!.players[1].name);

        if (isGameState(result)) {
            setGameState(result);
        } else {
            setAlert(`${result.statusCode} ${result.statusText}`);
        }
    }

    function validBowl(player: number, pit: number) {
        return (gameState!.players[player].hasTurn && gameState!.players[player].pits[pit].nrOfStones > 0)
    }

    const pitStyling = "rounded-full bg-pit text-neutral-50 shadow-innerPit text-3xl font-semibold"
    const kalahaStyling = "p-10 m-5 w-32"
    const bowlStyling = "p-10 m-10 w-24 "

    return <div className="`relative h-full w-screen bg-cover bg-center bg-no-repeat p-12 text-center bg-tabletop">
        {gameState!.gameStatus.endOfGame ?
            <h1 className="content-center text-center text-2xl font-bold">The winner is {gameState!.gameStatus.winner}
                <br />
                <button
                    className={classNames(
                        "rounded border-2",
                        "border-neutral-50",
                        "mt-5",
                        "bg-sogyo",
                        "px-7",
                        "pb-[8px]",
                        "pt-[10px]",
                        "text-sm",
                        "font-medium",
                        "uppercase",
                        "leading-normal",
                        "text-neutral-50",
                        "transition duration-150",
                        "ease-in-out",
                        "hover:border-neutral-100",
                        "hover:text-neutral-100",
                        "focus:border-neutral-100",
                        "focus:text-neutral-100",
                        "focus:outline-none",
                        "focus:ring-0",
                        "active:border-neutral-200",
                        "active:text-neutral-200",
                        "hover:bg-opacity-50"
                    )}
                    type="button"
                    onClick={() => restartGame()}
                >
                    Play again?
                </button>
            </h1>
            :
            <h1 className="content-center text-center text-2xl font-bold">The turn is for: {gameState!.players[0].hasTurn ? gameState!.players[0].name : gameState!.players[1].name}</h1>
        }
        {alert && <Alert text={alert} onClick={() => setAlert(null)} />}
        <div className="flex justify-center mt-20">
            <div className="flex justify-center justify-items-center w-min p-5 bg-playboard rounded-3xl shadow-2xl">
                <div className="flex w-1/6 justify-end">
                    <button
                        className={classNames(pitStyling, kalahaStyling)}
                        type="button"

                        disabled
                    >
                        {gameState!.players[1].pits[6].nrOfStones}
                    </button>
                </div>

                <div className="flex-col content-center justify-center">
                    <div className="flex justify-center">
                        <button
                            className={classNames(pitStyling, bowlStyling, {"hover:brightness-125" : validBowl(1, 5)})}
                            type="button"

                            onClick={() => playPit(12)}
                            disabled={!validBowl(1, 5)}
                        >
                            {gameState!.players[1].pits[5].nrOfStones}
                        </button>
                        <button
                            className={classNames(pitStyling, bowlStyling, {"hover:brightness-125" : validBowl(1, 4)})}
                            type="button"

                            onClick={() => playPit(11)}
                            disabled={!validBowl(1, 4)}
                        >
                            {gameState!.players[1].pits[4].nrOfStones}
                        </button>
                        <button
                            className={classNames(pitStyling, bowlStyling, {"hover:brightness-125" : validBowl(1, 3)})}
                            type="button"

                            onClick={() => playPit(10)}
                            disabled={!validBowl(1, 3)}
                        >
                            {gameState!.players[1].pits[3].nrOfStones}
                        </button>
                        <button
                            className={classNames(pitStyling, bowlStyling, {"hover:brightness-125" : validBowl(1, 2)})}
                            type="button"

                            onClick={() => playPit(9)}
                            disabled={!validBowl(1, 2)}
                        >
                            {gameState!.players[1].pits[2].nrOfStones}
                        </button>
                        <button
                            className={classNames(pitStyling, bowlStyling, {"hover:brightness-125" : validBowl(1, 1)})}
                            type="button"

                            onClick={() => playPit(8)}
                            disabled={!validBowl(1, 1)}
                        >
                            {gameState!.players[1].pits[1].nrOfStones}
                        </button>
                        <button
                            className={classNames(pitStyling, bowlStyling, {"hover:brightness-125" : validBowl(1, 0)})}
                            type="button"

                            onClick={() => playPit(7)}
                            disabled={!validBowl(1, 0)}
                        >
                            {gameState!.players[1].pits[0].nrOfStones}
                        </button>
                    </div>
                    <div className="flex justify-center">
                        <button
                            className={classNames(pitStyling, bowlStyling, {"hover:brightness-125" : validBowl(0, 0)})}
                            type="button"

                            onClick={() => playPit(0)}
                            disabled={!validBowl(0, 0)}
                        >
                            {gameState!.players[0].pits[0].nrOfStones}
                        </button>
                        <button
                            className={classNames(pitStyling, bowlStyling, {"hover:brightness-125" : validBowl(0, 1)})}
                            type="button"

                            onClick={() => playPit(1)}
                            disabled={!validBowl(0, 1)}
                        >
                            {gameState!.players[0].pits[1].nrOfStones}
                        </button>
                        <button
                            className={classNames(pitStyling, bowlStyling, {"hover:brightness-125" : validBowl(0, 2)})}
                            type="button"

                            onClick={() => playPit(2)}
                            disabled={!validBowl(0, 2)}
                        >
                            {gameState!.players[0].pits[2].nrOfStones}
                        </button>
                        <button
                            className={classNames(pitStyling, bowlStyling, {"hover:brightness-125" : validBowl(0, 3)})}
                            type="button"

                            onClick={() => playPit(3)}
                            disabled={!validBowl(0, 3)}
                        >
                            {gameState!.players[0].pits[3].nrOfStones}
                        </button>
                        <button
                            className={classNames(pitStyling, bowlStyling, {"hover:brightness-125" : validBowl(0, 4)})}
                            type="button"

                            onClick={() => playPit(4)}
                            disabled={!validBowl(0, 4)}
                        >
                            {gameState!.players[0].pits[4].nrOfStones}
                        </button>
                        <button
                            className={classNames(pitStyling, bowlStyling, {"hover:brightness-125" : validBowl(0, 5)})}
                            type="button"

                            onClick={() => playPit(5)}
                            disabled={!validBowl(0, 5)}
                        >
                            {gameState!.players[0].pits[5].nrOfStones}
                        </button>
                    </div>
                </div>
                <div className="flex w-1/6 justify-start">
                    <button
                        className={classNames(pitStyling, kalahaStyling)}
                        type="button"

                        disabled
                    >
                        {gameState!.players[0].pits[6].nrOfStones}
                    </button>
                </div>
            </div>
        </div>
    </div>
};

