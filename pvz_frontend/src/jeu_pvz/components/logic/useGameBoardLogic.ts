// useGameBoardLogic.ts

import { useState, useEffect, useRef, useCallback } from "react";
import { ZombieOnBoard } from "../logic/useZombiesLogic";
import { GameMap } from "../../../domains/maps/maps.types";
import { Plante } from "../../../domains/plants/plants.types";

interface UseGameBoardLogicProps {
  GameMap: GameMap;
  plantsOnBoard: { [key: string]: Plante | null };
  availableHeight: number;
  onPlantRemoved: (cellKey: string) => void;
}

export function useGameBoardLogic({
  GameMap,
  plantsOnBoard,
  availableHeight,
  onPlantRemoved,
}: UseGameBoardLogicProps) {
  const { ligne, colonne } = GameMap;
  const [cellSize, setCellSize] = useState(50);
  const [plantHealth, setPlantHealth] = useState<{ [key: string]: number }>({});
  const lastDamageUpdate = useRef<{ [key: string]: number }>({});

  useEffect(() => {
    const gap = 2;
    const padding = 10;
    const updateSize = () => {
      const maxWidth = window.innerWidth * 0.9;
      const maxHeight = availableHeight;
      const sizeByWidth = (maxWidth - (colonne - 1) * gap - 2 * padding) / colonne;
      const sizeByHeight = (maxHeight - (ligne - 1) * gap - 2 * padding) / ligne;
      const newCellSize = Math.floor(Math.min(sizeByWidth, sizeByHeight));
      setCellSize(newCellSize > 0 ? newCellSize : 0);
    };
    updateSize();
    window.addEventListener("resize", updateSize);
    return () => window.removeEventListener("resize", updateSize);
  }, [ligne, colonne, availableHeight]);

  // Initialisation de la santé des plantes
  useEffect(() => {
    const newHealth: { [key: string]: number } = {};
    Object.keys(plantsOnBoard).forEach((cellKey) => {
      if (plantsOnBoard[cellKey] && plantHealth[cellKey] == null) {
        // Valeur initiale de la plante
        newHealth[cellKey] = plantsOnBoard[cellKey]!.point_de_vie || 100;
      }
    });
    if (Object.keys(newHealth).length > 0) {
      setPlantHealth((prev) => ({ ...prev, ...newHealth }));
    }
  }, [plantsOnBoard, plantHealth]);

  const updatePlantHealth = useCallback((cellKey: string, newHealth: number) => {
    if (newHealth <= 0) {
      onPlantRemoved(cellKey);
      setPlantHealth((prev) => {
        const updated = { ...prev };
        delete updated[cellKey];
        return updated;
      });
      console.log(`Plante en ${cellKey} détruite (0 PV)`);
    } else {
      setPlantHealth((prev) => ({
        ...prev,
        [cellKey]: Math.max(newHealth, 0),
      }));
      console.log(`Plante en ${cellKey} prend des dégâts, PV restant: ${newHealth}`);
    }
  }, [onPlantRemoved]);

  const inflictDamage = useCallback((cellKey: string, damage: number) => {

    const currentHealth = plantHealth[cellKey]
      || (plantsOnBoard[cellKey] ? plantsOnBoard[cellKey]!.point_de_vie || 100 : 100);

    const newHealth = currentHealth - damage;
    updatePlantHealth(cellKey, newHealth);
  }, [plantHealth, plantsOnBoard, updatePlantHealth]);

  function detectCollisions(zombies: ZombieOnBoard[], deltaTime: number): ZombieOnBoard[] {
    const damageAccumulated: { [cellKey: string]: number } = {};
  
    const newZombies = zombies.map((zombie) => {
      const zombieRow = Math.floor(zombie.row);
      const cellCol = Math.floor(zombie.col);
      const cellKey = `${zombieRow}-${cellCol}`;
  
      const Plante = plantsOnBoard[cellKey];
      if (Plante) {
        const collisionThreshold = cellCol + 0.5;
        if (zombie.isAttacking || zombie.col <= collisionThreshold) {
          const baseDamage = (zombie.zombieData.attaque_par_seconde * zombie.zombieData.degat_attaque) || 10;
          const damageThisFrame = baseDamage * deltaTime;
          damageAccumulated[cellKey] = (damageAccumulated[cellKey] || 0) + damageThisFrame;
          return { ...zombie, isAttacking: true, col: collisionThreshold };
        }
      }
      return { ...zombie, isAttacking: false };
    });
  
    Object.entries(damageAccumulated).forEach(([cellKey, totalDamage]) => {
      inflictDamage(cellKey, totalDamage);
    });
  
    return newZombies;
  }
  
  

  return {
    cellSize,
    plantHealth,
    updatePlantHealth,
    detectCollisions,
  };
}
