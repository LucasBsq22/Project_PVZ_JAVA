import React from "react";
import { Projectile } from "../logic/useProjectilesLogic";
import { getImageUrl } from "../../utils/imageUtils";

interface ProjectileSpriteProps {
  projectile: Projectile;
  cellSize: number;
  gap: number;
  padding: number;
}

const ProjectileSprite: React.FC<ProjectileSpriteProps> = ({ projectile, cellSize, gap, padding }) => {
  const projectileWidth = cellSize * 0.3;
  const left = padding + projectile.col * (cellSize + gap) - projectileWidth / 2;
  const top = padding + projectile.row * (cellSize + gap) - projectileWidth / 2;
  return (
    <div
      style={{
        position: "absolute",
        left: left,
        top: top,
        width: projectileWidth,
        height: projectileWidth,
        backgroundImage: `url(${getImageUrl("images/projectile_mickael_jordan.png")})`,
        backgroundSize: "contain",
        backgroundRepeat: "no-repeat",
        pointerEvents: "none"
      }}
    />
  );
};

export default ProjectileSprite;