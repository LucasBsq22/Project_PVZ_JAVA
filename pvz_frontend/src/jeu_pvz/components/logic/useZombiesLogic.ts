import { useState, useEffect, useRef } from "react";
import { useGameContext } from "../../context/GameContext";
import { Zombie } from "../../../domains/zombies/zombies.types";
import { GameMap } from "../../../domains/maps/maps.types";
import { getZombies } from "../../../domains/zombies/zombies.api";

export interface ZombieOnBoard {
  instanceId: number;
  row: number;
  col: number;
  pv: number;
  zombieData: Zombie;
  isAttacking: boolean;
}

export function useZombiesLogic(
  GameMap: GameMap,
  detectCollisions: (zombies: ZombieOnBoard[], deltaTime: number) => ZombieOnBoard[]
) {
  const { gameConfig } = useGameContext();
  const [zombiesCatalog, setZombiesCatalog] = useState<Zombie[]>([]);
  const [zombiesOnBoard, setZombiesOnBoard] = useState<ZombieOnBoard[]>([]);
  const zombieInstanceCounter = useRef(0);

  const REFRESH_RATE_MS = gameConfig.refreshRate;
  const AVERAGE_SPAWN_MS = gameConfig.zombieSpawnRate;
  const SPAWN_VARIATION_MS = gameConfig.zombieSpawnVariation;

  useEffect(() => {
    const fetchZombies = async () => {
      try {
        const allZombies = await getZombies();
        setZombiesCatalog(allZombies);
      } catch (error) {
        console.error("Erreur lors du fetch des zombies:", error);
      }
    };
    fetchZombies();
  }, []);

  const getNextZombieInstanceId = () => {
    zombieInstanceCounter.current += 1;
    return zombieInstanceCounter.current;
  };

  const spawnZombie = (zombieType: Zombie, row: number, col: number) => {
    const uniqueId = getNextZombieInstanceId();
    setZombiesOnBoard((prev) => [
      ...prev,
      {
        instanceId: uniqueId,
        row,
        col,
        pv: zombieType.point_de_vie,
        zombieData: zombieType,
        isAttacking: false,
      },
    ]);
  };

  const updateZombieHealth = (zombieId: number, damage: number) => {
    setZombiesOnBoard((prev) => {
      return prev
        .map((z) => {
          if (z.instanceId === zombieId) {
            return { ...z, pv: z.pv - damage };
          }
          return z;
        })
        .filter((z) => z.pv > 0);
    });
  };

  const moveZombies = () => {
    const timeStep = REFRESH_RATE_MS / 1000;
    setZombiesOnBoard((prev) => {
      let movedZombies = prev.map((z) => {
        if (z.isAttacking) return z;
        const speed = z.zombieData.vitesse_de_deplacement || 0;
        const newCol = z.col - speed * timeStep;
        return { ...z, col: newCol };
      });
      movedZombies = movedZombies.filter((z) => z.col >= -2);
      const collidedZombies = detectCollisions(movedZombies, timeStep);
      return collidedZombies.filter((z) => z.pv > 0);
    });
  };

  useEffect(() => {
    const intervalId = setInterval(() => {
      moveZombies();
    }, REFRESH_RATE_MS);
    return () => clearInterval(intervalId);
  }, [GameMap, detectCollisions, REFRESH_RATE_MS]);

  useEffect(() => {
    let spawnTimeout: ReturnType<typeof setTimeout>;
    const scheduleNextSpawn = () => {
      const randomDelay =
        AVERAGE_SPAWN_MS + (Math.random() * SPAWN_VARIATION_MS - SPAWN_VARIATION_MS / 2);
      spawnTimeout = setTimeout(() => {
        if (zombiesCatalog.length > 0 && GameMap.colonne > 0 && GameMap.ligne > 0) {
          const enabledZombies = gameConfig.zombieTypes
            .filter(zt => zt.enabled)
            .map(zt => ({
              zombie: zombiesCatalog.find(z => z.id_zombie === zt.id),
              weight: zt.spawnWeight
            }))
            .filter(item => item.zombie) as { zombie: Zombie, weight: number }[];
          
          if (enabledZombies.length > 0) {
            const totalWeight = enabledZombies.reduce((sum, item) => sum + item.weight, 0);
            let randomValue = Math.random() * totalWeight;
            
            let selectedZombie: Zombie | null = null;
            for (const item of enabledZombies) {
              randomValue -= item.weight;
              if (randomValue <= 0) {
                selectedZombie = item.zombie;
                break;
              }
            }
            
            if (selectedZombie) {
              const randomRow = Math.floor(Math.random() * GameMap.ligne);
              const spawnCol = GameMap.colonne;
              spawnZombie(selectedZombie, randomRow, spawnCol);
            }
          }
        }
        scheduleNextSpawn();
      }, randomDelay);
    };
    
    if (zombiesCatalog.length > 0 && GameMap.colonne > 0 && GameMap.ligne > 0) {
      scheduleNextSpawn();
    }
    
    return () => clearTimeout(spawnTimeout);
  }, [GameMap, zombiesCatalog, AVERAGE_SPAWN_MS, SPAWN_VARIATION_MS, gameConfig.zombieTypes]);

  return { zombiesCatalog, zombiesOnBoard, spawnZombie, updateZombieHealth };
}