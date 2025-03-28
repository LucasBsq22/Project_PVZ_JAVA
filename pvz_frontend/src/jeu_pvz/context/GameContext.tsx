// Modified GameContext.tsx
import { createContext, useState, useContext, useEffect, ReactNode } from 'react';
import { GameConfig, GameScreen, ZombieTypeConfig } from '../types/GameConfig';
import { getZombies } from '../../domains/zombies/zombies.api';

interface GameContextType {
  currentScreen: GameScreen;
  gameConfig: GameConfig;
  setCurrentScreen: (screen: GameScreen) => void;
  updateGameConfig: (config: Partial<GameConfig>) => void;
  updateZombieTypeConfig: (zombieTypeConfig: ZombieTypeConfig) => void;
  resetToDefaults: () => void;
}

const defaultConfig: GameConfig = {
  zombieSpawnRate: 5000,
  zombieSpawnVariation: 2000,
  refreshRate: 20,
  initialMoney: 100,
  zombieTypes: []
};

const GameContext = createContext<GameContextType>({
  currentScreen: GameScreen.START,
  gameConfig: defaultConfig,
  setCurrentScreen: () => {},
  updateGameConfig: () => {},
  updateZombieTypeConfig: () => {},
  resetToDefaults: () => {}
});

interface GameProviderProps {
  children: ReactNode;
  initialConfig?: Partial<GameConfig>;
  overrideSetCurrentScreen?: (screen: GameScreen) => void;
}

export const GameProvider = ({ 
  children, 
  initialConfig,
  overrideSetCurrentScreen 
}: GameProviderProps) => {
  const [currentScreenInternal, setCurrentScreenInternal] = useState<GameScreen>(GameScreen.START);
  
  // Initialize game config with merged default and initial config
  const [gameConfig, setGameConfig] = useState<GameConfig>({
    ...defaultConfig,
    ...initialConfig
  });

  // Use the external screen control if provided
  const setCurrentScreen = (screen: GameScreen) => {
    if (overrideSetCurrentScreen) {
      overrideSetCurrentScreen(screen);
    } else {
      setCurrentScreenInternal(screen);
    }
  };

  useEffect(() => {
    const loadZombieTypes = async () => {
      try {
        const zombies = await getZombies();
        const zombieConfigs = zombies.map(zombie => ({
          id: zombie.id_zombie,
          nom: zombie.nom,
          spawnWeight: initialConfig?.zombieTypes?.find(z => z.id === zombie.id_zombie)?.spawnWeight || 1,
          enabled: initialConfig?.zombieTypes?.find(z => z.id === zombie.id_zombie)?.enabled !== false
        }));
        
        setGameConfig(prev => ({
          ...prev,
          zombieTypes: zombieConfigs
        }));
      } catch (error) {
        console.error("Failed to load zombie types for configuration:", error);
      }
    };
    
    loadZombieTypes();
  }, [initialConfig]);

  const updateGameConfig = (config: Partial<GameConfig>) => {
    setGameConfig({
      ...gameConfig,
      ...config
    });
  };

  const updateZombieTypeConfig = (zombieTypeConfig: ZombieTypeConfig) => {
    setGameConfig({
      ...gameConfig,
      zombieTypes: gameConfig.zombieTypes.map(ztc => 
        ztc.id === zombieTypeConfig.id ? zombieTypeConfig : ztc
      )
    });
  };

  const resetToDefaults = () => {
    setGameConfig({
      ...defaultConfig,
      zombieTypes: gameConfig.zombieTypes.map(zombie => ({
        ...zombie,
        spawnWeight: 1,
        enabled: true
      }))
    });
  };

  return (
    <GameContext.Provider value={{
      currentScreen: currentScreenInternal,
      gameConfig,
      setCurrentScreen,
      updateGameConfig,
      updateZombieTypeConfig,
      resetToDefaults
    }}>
      {children}
    </GameContext.Provider>
  );
};

export const useGameContext = () => useContext(GameContext);