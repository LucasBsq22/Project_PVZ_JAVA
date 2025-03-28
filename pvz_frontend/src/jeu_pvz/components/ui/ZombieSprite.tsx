import React from "react";
import { ZombieOnBoard } from "../logic/useZombiesLogic";
import { getImageUrl } from "../../utils/imageUtils";

interface ZombieSpriteProps {
  zombieInfo: ZombieOnBoard;
  cellSize: number;
  gap: number;
  padding: number;
}

const ZombieSprite: React.FC<ZombieSpriteProps> = ({ zombieInfo, cellSize, gap, padding }) => {
  const { zombieData, pv, row, col } = zombieInfo;
  const maxPV = zombieData.point_de_vie;
  const lifePercent = (pv / maxPV) * 100;

  const topPx = Math.round(padding + row * (cellSize + gap) + (cellSize * 0.1));
  const leftPx = Math.round(padding + col * (cellSize + gap) + (cellSize * 0.1));

  return (
    <div
      style={{
        position: "absolute",
        top: topPx,
        left: leftPx,
        width: cellSize * 0.8,
        height: cellSize * 0.8,
        backgroundImage: `url(${getImageUrl(zombieData.chemin_image)})`,
        backgroundSize: "contain",
        backgroundRepeat: "no-repeat",
      }}
    >
      <div
        style={{
          position: "absolute",
          bottom: 2,
          left: "50%",
          transform: "translateX(-50%)",
          width: "80%",
          height: 5,
          backgroundColor: "red",
          borderRadius: 3,
          overflow: "hidden",
        }}
      >
        <div
          style={{
            width: `${lifePercent}%`,
            height: "100%",
            backgroundColor: "blue",
          }}
        />
      </div>
    </div>
  );
};

export default ZombieSprite;