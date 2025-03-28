import React from "react";
import { Plante } from "../../types/Plant";
import { getImageUrl, handleImageError } from "../../utils/imageUtils";

interface PlantItemProps {
  Plante: Plante;
  onClick: (Plante: Plante) => void;
  isSelected: boolean;
  isDisabled: boolean;
  plantSize: number;
}

const PlantItem: React.FC<PlantItemProps> = ({ Plante, onClick, isSelected, isDisabled, plantSize }) => {
  return (
    <div
      className="Plante-item"
      onClick={() => !isDisabled && onClick(Plante)}
      style={{
        cursor: isDisabled ? "not-allowed" : "pointer",
        border: isSelected ? "3px solid #ff9800" : "1px solid #ccc",
        borderRadius: "8px",
        padding: "10px",
        margin: "5px",
        width: plantSize,
        height: plantSize * 1.2,
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "space-between",
        textAlign: "center",
        backgroundColor: isDisabled ? "#eeeeee" : isSelected ? "#fff3e0" : "white",
        opacity: isDisabled ? 0.7 : 1,
        position: "relative", 
        overflow: "hidden", 
      }}
    >
      <div
        style={{
          position: "absolute",
          top: "5px",
          right: "5px",
          backgroundColor: isDisabled ? "#f4a261" : "#ffcc00",
          color: "black",
          padding: "4px 6px",
          borderRadius: "8px",
          fontSize: "12px",
          fontWeight: "bold",
          zIndex: 2, 
        }}
      >
        {Plante.cout}💰
      </div>

      {Plante.chemin_image && (
        <img
          src={getImageUrl(Plante.chemin_image)}
          alt={Plante.nom}
          onError={handleImageError}
          style={{
            width: "80%",
            height: "60%",
            objectFit: "contain",
            position: "relative", 
            zIndex: 1, 
          }}
        />
      )}

      <h3 style={{ fontSize: "14px", margin: "5px 0", zIndex: 1 }}>{Plante.nom}</h3>
    </div>
  );
};

export default PlantItem;