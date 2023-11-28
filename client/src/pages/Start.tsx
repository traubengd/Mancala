import { useState } from "react";
import { useMancalaGame } from "../contexts/MancalaGameContext";
import { startGame } from "../services/api";
import { isGameState } from "../types";
import { FloatingInput } from "../components/FloatingInput";
import classNames from "classnames";
import { Alert } from "../components/Alert";

export const Start = () => {
    const { setGameState } = useMancalaGame();

    const [player1, setPlayer1] = useState("");
    const [player2, setPlayer2] = useState("");
    const [alert, setAlert] = useState<string | null>(null);
    const valid = player1 !== "" && player2 !== "" && player1 !== player2;

    const onSubmit = async () => {
        const result = await startGame(player1, player2);

        if (isGameState(result)) {
            setGameState(result);
        } else {
            setAlert(`${result.statusCode} ${result.statusText}`);
        }
    }

    return (
        <div className="`relative h-full w-screen bg-cover bg-center bg-no-repeat p-12 text-center bg-mancala">
            <div className="absolute2 bottom-0 left-0 right-0 top-0 h-full w-full overflow-hidden bg-fixed bg-black/60">
                <div className="flex h-full items-center justify-center">
                    <div className="text-white">
                        <h2 className="mb-4 text-4xl font-semibold">Welcome to Mancala</h2>
                        <h4 className="mb-6 text-xl font-semibold">Enter the players name to start</h4>

                        {alert && <Alert text={alert} onClick={() => setAlert(null)} />}
                        <form>
                            <ol>
                                <li className="mt-4">
                                    <FloatingInput
                                        id="player1"
                                        label="Name of player 1"
                                        value={player1}
                                        onChange={e => setPlayer1(e.target.value)}
                                        hasError={player1 !== "" && player2 !== "" && player1 === player2}
                                    />
                                </li>
                                <li className="mt-4">
                                    <FloatingInput
                                        id="player2"
                                        label="Name of player 2"
                                        value={player2}
                                        onChange={e => setPlayer2(e.target.value)}
                                        hasError={player1 !== "" && player2 !== "" && player1 === player2}
                                    />
                                </li>
                                <li>
                                    {player1 && player2 && player1 === player2 && <p className="mt-2 text-sm text-red-400">
                                        <span className="font-medium">Names must be unique</span>
                                    </p>}
                                </li>
                                <li className="mt-4">
                                    <button
                                        type="button"
                                        className={classNames(
                                            "rounded border-2",
                                            "border-neutral-50",
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
                                            "hover:bg-neutral-100",
                                            "hover:bg-opacity-10",
                                            { "cursor-not-allowed opacity-30": !valid }
                                        )}
                                        data-te-ripple-init
                                        data-te-ripple-color="light"
                                        disabled={!valid}
                                        onClick={() => onSubmit()}
                                    >
                                        Play
                                    </button>
                                </li>
                            </ol>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};