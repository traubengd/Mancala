import { useMancalaGame } from "../contexts/MancalaGameContext";
import { Play } from "./Play";
import { Start } from "./Start";

export const Mancala = () => {
    const { gameState } = useMancalaGame();
    return gameState ? <Play /> : <Start />;
};
