import React from "react";
import { Plante } from "../../types/Plant";
import { useSunLogic } from "../logic/useSunLogic";
import { getImageUrl, handleImageError } from "../../utils/imageUtils";

interface CellProps {
  row: number;
  col: number;
  backgroundImage: string;
  Plante?: Plante | null;
  onClick: (row: number, col: number) => void;
  collectSun: () => void;
  cellSize: number;
  point_de_vie: number; // ici, c'est le pourcentage de vie
}

const Cell: React.FC<CellProps> = ({
  row,
  col,
  backgroundImage,
  Plante,
  onClick,
  collectSun,
  cellSize,
  point_de_vie,
}) => {
  const { hasSun, sunTimer, setHasSun } = useSunLogic(Plante);

  return (
    <div
      style={{
        width: cellSize,
        height: cellSize,
        backgroundImage: `url(/data/${backgroundImage})`,
        backgroundSize: "cover",
        border: "1px solid white",
        cursor: "pointer",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        position: "relative",
      }}
      onClick={() => onClick(row, col)}
    >
      {Plante && (
        <img
          src={getImageUrl(Plante.chemin_image)}
          alt={Plante.nom}
          onError={handleImageError}
          style={{
            width: "80%",
            height: "80%",
            objectFit: "contain",
          }}
        />
      )}
      {Plante && (
        <div
          style={{
            position: "absolute",
            bottom: "5px",
            left: "50%",
            transform: "translateX(-50%)",
            width: `${cellSize * 0.8}px`,
            height: "6px",
            backgroundColor: "#ff0000",
            borderRadius: "3px",
            overflow: "hidden",
            border: "1px solid #000",
          }}
        >
          <div
            style={{
              width: `${point_de_vie}%`,
              height: "100%",
              backgroundColor: "#00ff00",
              transition: "width 0.1s linear",
            }}
          />
        </div>
      )}

      {Plante?.soleil_par_seconde! > 0 && (
        <div
          style={{
            position: "absolute",
            top: "5px",
            left: "50%",
            transform: "translateX(-50%)",
            fontSize: "12px",
            color: "white",
            backgroundColor: "rgba(0, 0, 0, 0.5)",
            padding: "3px 6px",
            borderRadius: "8px",
          }}
        >
          {sunTimer}s
        </div>
      )}
      {hasSun && (
        <div
          onClick={(e) => {
            e.stopPropagation();
            collectSun();
            setHasSun(false);
          }}
          style={{
            position: "absolute",
            bottom: `${cellSize * 0.1}px`,
            left: "50%",
            transform: "translateX(-50%)",
            width: `${cellSize * 0.6}px`,
            height: `${cellSize * 0.6}px`,
            backgroundImage: `url(${getImageUrl("images/soleil.png")})`,
            backgroundSize: "contain",
            backgroundRepeat: "no-repeat",
            cursor: "pointer",
          }}
        />
      )}
    </div>
  );
};

export default Cell;