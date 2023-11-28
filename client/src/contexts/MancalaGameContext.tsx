import { createContext, useContext, useState } from "react";
import { GameState } from "../types";

type ContextType = {
    gameState: GameState | undefined,
    setGameState: (gameState: GameState) => void;
}

const MancalaGameContext = createContext<ContextType>({
    gameState: undefined,
    setGameState() { },
});

type Props = React.PropsWithChildren;

export const MancalaGameProvider = (props: Props) => {
    const { children } = props;

    const [gameState, setGameState] = useState<GameState | undefined>(undefined);

    return <MancalaGameContext.Provider value={{
        gameState: gameState,
        setGameState: setGameState
    }}>{children}</MancalaGameContext.Provider>
}

export const useMancalaGame = () => {
    const context = useContext(MancalaGameContext);

    if (context === undefined) {
        throw new Error('useMancalaGame must be used within a MancalaGameProvider');
    }

    return context;
}
