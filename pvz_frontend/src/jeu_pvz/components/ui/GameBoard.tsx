// GameBoard.tsx
import React, { useEffect } from "react";
import { useGameBoardLogic } from "../logic/useGameBoardLogic";
import { useZombiesLogic } from "../logic/useZombiesLogic";
import { useProjectilesLogic } from "../logic/useProjectilesLogic";
import Cell from "./Cell";
import ZombieSprite from "./ZombieSprite";
import ProjectileSprite from "./ProjectileSprite";
import { GameMap } from "../../../domains/maps/maps.types";
import { Plante } from "../../../domains/plants/plants.types";

interface GameBoardProps {
  GameMap: GameMap;
  plantsOnBoard: { [key: string]: Plante | null };
  onCellClick: (row: number, col: number) => void;
  availableHeight: number;
  onPlantRemoved: (cellKey: string) => void;
  collectSun: () => void;
}

const GameBoard: React.FC<GameBoardProps> = ({
  GameMap,
  plantsOnBoard,
  onCellClick,
  availableHeight,
  onPlantRemoved,
  collectSun
}) => {
  const { cellSize, plantHealth, detectCollisions } = useGameBoardLogic({
    GameMap,
    plantsOnBoard,
    availableHeight,
    onPlantRemoved
  });
  const { zombiesOnBoard, updateZombieHealth, spawnZombie } = useZombiesLogic(GameMap, detectCollisions);
  const handleZombieHit = (zombieId: number, damage: number) => {
    updateZombieHealth(zombieId, damage);
  };
  const { projectiles, spawnProjectile } = useProjectilesLogic(GameMap, cellSize, plantsOnBoard, zombiesOnBoard, handleZombieHit);
  useEffect(() => {
    const intervalId = setInterval(() => {
      Object.entries(plantsOnBoard).forEach(([cellKey, Plante]) => {
        if (Plante && Plante.degat_attaque > 0) {
          const [row, col] = cellKey.split("-").map(Number);
          spawnProjectile(row, col, Plante.degat_attaque);
        }
      });
    }, 1000);
    return () => clearInterval(intervalId);
  }, [plantsOnBoard, spawnProjectile]);
  const gap = 2;
  const padding = 10;
  const { ligne, colonne, chemin_image } = GameMap;
  const boardWidth = cellSize * colonne + (colonne - 1) * gap + 2 * padding;
  const boardHeight = cellSize * ligne + (ligne - 1) * gap + 2 * padding;
  return (
    <div
      style={{
        position: "relative",
        width: boardWidth,
        height: boardHeight,
        backgroundColor: "#4caf50",
        margin: "auto",
        overflow: "hidden",
        boxSizing: "border-box"
      }}
    >
      <div
        style={{
          position: "absolute",
          top: padding,
          left: padding,
          width: boardWidth - 2 * padding,
          height: boardHeight - 2 * padding,
          display: "grid",
          gridTemplateColumns: `repeat(${colonne}, ${cellSize}px)`,
          gridTemplateRows: `repeat(${ligne}, ${cellSize}px)`,
          gap: `${gap}px`,
          boxSizing: "border-box"
        }}
      >
        {Array.from({ length: ligne * colonne }).map((_, index) => {
          const row = Math.floor(index / colonne);
          const col = index % colonne;
          const cellKey = `${row}-${col}`;
          const Plante = plantsOnBoard[cellKey];
          const currentHealth = plantHealth[cellKey] ?? (Plante ? Plante.point_de_vie : 0);
          const maxHealth = Plante ? Plante.point_de_vie || 100 : 100;
          const healthPercent = (currentHealth / maxHealth) * 100;
          return (
            <Cell
              key={cellKey}
              row={row}
              col={col}
              backgroundImage={chemin_image}
              Plante={Plante}
              onClick={onCellClick}
              collectSun={collectSun}
              cellSize={cellSize}
              point_de_vie={healthPercent}
            />
          );
        })}
      </div>
      {zombiesOnBoard.map((z) => (
        <ZombieSprite key={z.instanceId} zombieInfo={z} cellSize={cellSize} gap={gap} padding={padding} />
      ))}
      {projectiles.map((p) => (
        <ProjectileSprite key={p.instanceId} projectile={p} cellSize={cellSize} gap={gap} padding={padding} />
      ))}
    </div>
  );
};

export default GameBoard;
