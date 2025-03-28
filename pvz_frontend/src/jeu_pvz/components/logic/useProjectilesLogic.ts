import { useState, useEffect, useRef, useCallback } from "react";
import { ZombieOnBoard } from "../logic/useZombiesLogic";
import { useGameContext } from "../../context/GameContext";
import { Plante } from "../../../domains/plants/plants.types";
import { GameMap } from "../../../domains/maps/maps.types";

export interface Projectile {
  instanceId: number;
  row: number;
  col: number;
  speed: number;
  damage: number;
}

export function useProjectilesLogic(
  GameMap: GameMap,
  cellSize: number,
  plantsOnBoard: { [key: string]: Plante | null },
  zombies: ZombieOnBoard[],
  onZombieHit: (zombieId: number, damage: number) => void
) {
  const { gameConfig } = useGameContext();
  const [projectiles, setProjectiles] = useState<Projectile[]>([]);
  const projectileInstanceCounter = useRef(0);
  const REFRESH_RATE_MS = gameConfig.refreshRate;
  
  const spawnProjectile = useCallback((plantRow: number, plantCol: number, damage: number) => {
    const instanceId = ++projectileInstanceCounter.current;
    const projectile: Projectile = {
      instanceId,
      row: plantRow + 0.35,
      col: plantCol + 0.84,
      speed: 0.005 * (20 / REFRESH_RATE_MS),
      damage
    };
    setProjectiles((prev) => [...prev, projectile]);
  }, [REFRESH_RATE_MS]);
  
  useEffect(() => {
    const intervalId = setInterval(() => {
      setProjectiles((prev) => {
        const updated: Projectile[] = [];
        prev.forEach((proj) => {
          const newCol = proj.col + proj.speed;
          let hit = false;
          for (let i = 0; i < zombies.length; i++) {
            const zombie = zombies[i];
            if (Math.floor(zombie.row) === Math.floor(proj.row) && 
                newCol >= zombie.col && 
                newCol - zombie.col < 0.5) {
              onZombieHit(zombie.instanceId, proj.damage);
              hit = true;
              break;
            }
          }
          if (!hit && newCol < GameMap.colonne) {
            updated.push({ ...proj, col: newCol });
          }
        });
        return updated;
      });
    }, REFRESH_RATE_MS);
    
    return () => clearInterval(intervalId);
  }, [zombies, GameMap.colonne, onZombieHit, REFRESH_RATE_MS]);
  
  return { projectiles, spawnProjectile };
}