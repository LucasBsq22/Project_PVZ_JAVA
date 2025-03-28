import React, { useState } from 'react';
import { GameProvider } from './context/GameContext';
import { GameScreen } from './types/GameConfig';
import StartScreen from './components/ui/Start';
import ConfigScreen from './components/ui/ConfigScreen';
import GameComponent from './components/ui/Game';

interface PlantsVsZombiesGameProps {
  onExit?: () => void;
  initialConfig?: any;
}

const PlantsVsZombiesGame: React.FC<PlantsVsZombiesGameProps> = ({ onExit, initialConfig }) => {
  const [currentScreen, setCurrentScreen] = useState<GameScreen>(GameScreen.START);

  const handleExit = () => {
    if (onExit) {
      onExit();
    }
  };

  const renderScreen = () => {
    switch (currentScreen) {
      case GameScreen.START:
        return <StartScreen onStart={() => setCurrentScreen(GameScreen.CONFIG)} onExit={handleExit} />;
      case GameScreen.CONFIG:
        return <ConfigScreen 
          onBack={() => setCurrentScreen(GameScreen.START)} 
          onStart={() => setCurrentScreen(GameScreen.GAME)} 
        />;
      case GameScreen.GAME:
        return <GameComponent 
          onExit={() => setCurrentScreen(GameScreen.START)} 
        />;
      default:
        return <StartScreen onStart={() => setCurrentScreen(GameScreen.CONFIG)} onExit={handleExit} />;
    }
  };

  return (
    <GameProvider overrideSetCurrentScreen={setCurrentScreen} initialConfig={initialConfig}>
      <div className="plants-vs-zombies-game">
        {renderScreen()}
      </div>
    </GameProvider>
  );
};

export default PlantsVsZombiesGame;