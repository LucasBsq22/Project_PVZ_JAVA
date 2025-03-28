import React from 'react';
import PlantList from './PlantList';
import GameBoard from './GameBoard';
import DeleteModeToggle from './DeleteModeToggle';
import { useAppLogic } from '../logic/useAppLogic';
import { useGameContext } from '../../context/GameContext';
import { GameScreen } from '../../types/GameConfig';

interface GameComponentProps {
  onExit?: () => void;
}

const GameComponent: React.FC<GameComponentProps> = ({ onExit }) => {
  const { gameConfig, setCurrentScreen } = useGameContext();
  
  const {
    plants,
    selectedMap,
    money,
    collectSun,
    selectedPlant,
    plantsOnBoard,
    deleteMode,
    availableHeight,
    handlePlantClick,
    handleCellClick,
    handlePlantRemoved,
    setDeleteMode,
  } = useAppLogic(gameConfig.initialMoney);

  const handleExit = () => {
    if (onExit) {
      onExit();
    } else {
      setCurrentScreen(GameScreen.START);
    }
  };

  if (!selectedMap) {
    return <div>Chargement...</div>;
  }

  return (
    <div
      style={{
        display: 'flex',
        height: '100vh',
        padding: '10px',
        boxSizing: 'border-box',
        backgroundColor: '#f0f0f0',
      }}
    >
      <div
        style={{
          width: '20%',
          height: '100%',
          padding: '10px',
          boxSizing: 'border-box',
          display: 'flex',
          flexDirection: 'column',
          backgroundColor: '#e0e0e0',
        }}
      >
        <PlantList
          plants={plants}
          onPlantClick={handlePlantClick}
          selectedPlant={selectedPlant}
          availableHeight={availableHeight}
          money={money}
        />
        <div style={{ marginTop: '10px', display: 'flex', flexDirection: 'column', gap: '10px' }}>
          <DeleteModeToggle deleteMode={deleteMode} setDeleteMode={setDeleteMode} />
          <button
            onClick={handleExit}
            style={{
              backgroundColor: '#ff5722',
              color: 'white',
              padding: '10px',
              border: 'none',
              borderRadius: '5px',
              cursor: 'pointer',
            }}
          >
            Quitter le jeu
          </button>
        </div>
      </div>

      <div
        style={{
          flex: 1,
          height: '100%',
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          padding: '10px',
          boxSizing: 'border-box',
        }}
      >
        <GameBoard
          GameMap={selectedMap}
          plantsOnBoard={plantsOnBoard}
          onCellClick={handleCellClick}
          availableHeight={availableHeight}
          onPlantRemoved={handlePlantRemoved}
          collectSun={collectSun}
        />
      </div>
    </div>
  );
};

export default GameComponent;