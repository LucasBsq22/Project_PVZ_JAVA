import React from 'react';
import { useGameContext } from '../../context/GameContext';
import { GameScreen } from '../../types/GameConfig';

interface ConfigScreenProps {
  onBack?: () => void;
  onStart?: () => void;
}

const ConfigScreen: React.FC<ConfigScreenProps> = ({ onBack, onStart }) => {
  const { gameConfig, updateGameConfig, updateZombieTypeConfig, setCurrentScreen, resetToDefaults } = useGameContext();

  const handleBack = () => {
    if (onBack) {
      onBack();
    } else {
      setCurrentScreen(GameScreen.START);
    }
  };

  const handleStart = () => {
    if (onStart) {
      onStart();
    } else {
      setCurrentScreen(GameScreen.GAME);
    }
  };

  return (
    <div style={{
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      padding: '30px',
      backgroundColor: '#f5f5f5',
      minHeight: '100vh',
      fontFamily: 'Arial, sans-serif'
    }}>
      <h1 style={{ color: '#2a8e00', marginBottom: '30px' }}>Configuration du jeu</h1>
      
      <div style={{ 
        backgroundColor: 'white',
        borderRadius: '10px',
        padding: '20px',
        width: '100%',
        maxWidth: '800px',
        boxShadow: '0 4px 8px rgba(0,0,0,0.1)'
      }}>
        <h2 style={{ color: '#ff9800', borderBottom: '2px solid #eee', paddingBottom: '10px' }}>Paramètres généraux</h2>
        
        <div style={{ marginBottom: '20px' }}>
          <label style={{ display: 'block', marginBottom: '8px', fontWeight: 'bold' }}>
            Soleil initial:
          </label>
          <input
            type="number"
            value={gameConfig.initialMoney}
            onChange={(e) => updateGameConfig({ initialMoney: parseInt(e.target.value) })}
            style={{ padding: '8px', width: '100px', borderRadius: '4px', border: '1px solid #ddd' }}
            min="50"
            max="1000"
          />
        </div>
        
        <div style={{ marginBottom: '20px' }}>
          <label style={{ display: 'block', marginBottom: '8px', fontWeight: 'bold' }}>
            Taux de rafraîchissement (ms):
          </label>
          <input
            type="number"
            value={gameConfig.refreshRate}
            onChange={(e) => updateGameConfig({ refreshRate: parseInt(e.target.value) })}
            style={{ padding: '8px', width: '100px', borderRadius: '4px', border: '1px solid #ddd' }}
            min="10"
            max="100"
            step="5"
          />
          <span style={{ marginLeft: '10px', color: '#666', fontSize: '14px' }}>
            (Valeur plus basse = jeu plus fluide mais plus exigeant)
          </span>
        </div>
        
        <div style={{ marginBottom: '20px' }}>
          <label style={{ display: 'block', marginBottom: '8px', fontWeight: 'bold' }}>
            Temps moyen d'apparition des zombies (ms):
          </label>
          <input
            type="number"
            value={gameConfig.zombieSpawnRate}
            onChange={(e) => updateGameConfig({ zombieSpawnRate: parseInt(e.target.value) })}
            style={{ padding: '8px', width: '100px', borderRadius: '4px', border: '1px solid #ddd' }}
            min="1000"
            max="20000"
            step="500"
          />
        </div>
        
        <div style={{ marginBottom: '20px' }}>
          <label style={{ display: 'block', marginBottom: '8px', fontWeight: 'bold' }}>
            Variation du temps d'apparition (ms):
          </label>
          <input
            type="number"
            value={gameConfig.zombieSpawnVariation}
            onChange={(e) => updateGameConfig({ zombieSpawnVariation: parseInt(e.target.value) })}
            style={{ padding: '8px', width: '100px', borderRadius: '4px', border: '1px solid #ddd' }}
            min="0"
            max="5000"
            step="100"
          />
        </div>
        
        <h2 style={{ color: '#ff9800', borderBottom: '2px solid #eee', paddingBottom: '10px', marginTop: '30px' }}>Types de zombies</h2>
        
        {gameConfig.zombieTypes.length > 0 ? (
          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(250px, 1fr))', gap: '15px', marginTop: '20px' }}>
            {gameConfig.zombieTypes.map(zombie => (
              <div key={zombie.id} style={{ 
                padding: '15px',
                backgroundColor: zombie.enabled ? '#f9f9f9' : '#e5e5e5',
                borderRadius: '8px',
                border: '1px solid #ddd'
              }}>
                <div style={{ display: 'flex', alignItems: 'center', marginBottom: '10px' }}>
                  <input
                    type="checkbox"
                    checked={zombie.enabled}
                    onChange={(e) => updateZombieTypeConfig({
                      ...zombie,
                      enabled: e.target.checked
                    })}
                    style={{ marginRight: '10px' }}
                  />
                  <span style={{ fontWeight: 'bold' }}>{zombie.nom}</span>
                </div>
                
                <div>
                  <label style={{ display: 'block', marginBottom: '5px', fontSize: '14px' }}>
                    Fréquence d'apparition:
                  </label>
                  <input
                    type="range"
                    min="0.1"
                    max="5"
                    step="0.1"
                    value={zombie.spawnWeight}
                    disabled={!zombie.enabled}
                    onChange={(e) => updateZombieTypeConfig({
                      ...zombie,
                      spawnWeight: parseFloat(e.target.value)
                    })}
                    style={{ width: '100%' }}
                  />
                  <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '12px', color: '#666' }}>
                    <span>Rare</span>
                    <span>{zombie.spawnWeight.toFixed(1)}</span>
                    <span>Fréquent</span>
                  </div>
                </div>
              </div>
            ))}
          </div>
        ) : (
          <p style={{ textAlign: 'center', padding: '20px', color: '#666' }}>
            Chargement des types de zombies...
          </p>
        )}
        
        <div style={{ display: 'flex', justifyContent: 'space-between', marginTop: '30px' }}>
          <button
            onClick={resetToDefaults}
            style={{
              padding: '10px 20px',
              backgroundColor: '#f5f5f5',
              border: '1px solid #ddd',
              borderRadius: '4px',
              cursor: 'pointer'
            }}
          >
            Réinitialiser
          </button>
          
          <div>
            <button
              onClick={handleBack}
              style={{
                padding: '10px 20px',
                backgroundColor: '#f5f5f5',
                border: '1px solid #ddd',
                borderRadius: '4px',
                marginRight: '10px',
                cursor: 'pointer'
              }}
            >
              Retour
            </button>
            
            <button
              onClick={handleStart}
              style={{
                padding: '10px 20px',
                backgroundColor: '#4caf50',
                color: 'white',
                border: 'none',
                borderRadius: '4px',
                cursor: 'pointer'
              }}
            >
              Commencer
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ConfigScreen;