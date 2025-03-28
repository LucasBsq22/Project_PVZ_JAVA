// Modified Start.tsx
import React from 'react';
import { GameScreen } from '../../types/GameConfig';
import { useGameContext } from '../../context/GameContext';

interface StartScreenProps {
  onStart?: () => void;
  onExit?: () => void;
}

const StartScreen: React.FC<StartScreenProps> = ({ onStart, onExit }) => {
  const { setCurrentScreen } = useGameContext();

  const handleStart = () => {
    if (onStart) {
      onStart();
    } else {
      setCurrentScreen(GameScreen.CONFIG);
    }
  };

  return (
    <div style={{
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      justifyContent: 'center',
      height: '100vh',
      background: 'linear-gradient(135deg, #76c852, #2a8e00)',
      color: 'white',
      fontFamily: 'Arial, sans-serif'
    }}>
      <h1 style={{ fontSize: '48px', marginBottom: '30px', textShadow: '2px 2px 4px rgba(0,0,0,0.5)' }}>
        Plantes vs Zombies
      </h1>
      
      <div style={{ textAlign: 'center', maxWidth: '600px', marginBottom: '40px' }}>
        <p style={{ fontSize: '18px' }}>
          Défendez votre jardin contre les zombies en plaçant stratégiquement des plantes qui lancent des projectiles!
        </p>
      </div>
      
      <div style={{ display: 'flex', gap: '20px' }}>
        <button
          onClick={handleStart}
          style={{
            padding: '15px 30px',
            fontSize: '20px',
            backgroundColor: '#ff9800',
            color: 'white',
            border: 'none',
            borderRadius: '8px',
            cursor: 'pointer',
            boxShadow: '0 4px 8px rgba(0,0,0,0.2)',
            transition: 'background-color 0.2s'
          }}
          onMouseOver={e => e.currentTarget.style.backgroundColor = '#f57c00'}
          onMouseOut={e => e.currentTarget.style.backgroundColor = '#ff9800'}
        >
          Lancer le jeu
        </button>
        
        {onExit && (
          <button
            onClick={onExit}
            style={{
              padding: '15px 30px',
              fontSize: '20px',
              backgroundColor: '#e0e0e0',
              color: '#333',
              border: 'none',
              borderRadius: '8px',
              cursor: 'pointer',
              boxShadow: '0 4px 8px rgba(0,0,0,0.2)',
              transition: 'background-color 0.2s'
            }}
            onMouseOver={e => e.currentTarget.style.backgroundColor = '#d0d0d0'}
            onMouseOut={e => e.currentTarget.style.backgroundColor = '#e0e0e0'}
          >
            Quitter
          </button>
        )}
      </div>
    </div>
  );
};

export default StartScreen;