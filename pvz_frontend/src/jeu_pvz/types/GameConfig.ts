export interface GameConfig {
    zombieSpawnRate: number;
    zombieSpawnVariation: number;
    refreshRate: number;
    initialMoney: number;
    zombieTypes: ZombieTypeConfig[];
  }
  
  export interface ZombieTypeConfig {
    id: number;
    nom: string;
    spawnWeight: number;
    enabled: boolean;
  }
  
  export enum GameScreen {
    START,
    CONFIG,
    GAME
  }