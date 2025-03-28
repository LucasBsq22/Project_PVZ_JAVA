import React from "react";
import { usePlantListLogic } from "../logic/usePlantListLogic";
import PlantItem from "./PlantItem";
import { Plante } from "../../types/Plant";

interface PlantListProps {
  plants: Plante[];
  onPlantClick: (Plante: Plante) => void;
  selectedPlant: Plante | null;
  availableHeight: number;
  money: number; 
}

const PlantList: React.FC<PlantListProps> = ({ plants, onPlantClick, selectedPlant, availableHeight, money }) => {
  const plantSize = usePlantListLogic(availableHeight);

  return (
    <div
      className="Plante-list"
      style={{
        display: "flex",
        flexWrap: "wrap",
        gap: "10px",
        justifyContent: "center",
        alignItems: "center",
        height: availableHeight,
        overflowY: "auto",
      }}
    >
      <h2>Or disponible : {money}</h2>

      {plants.map((Plante) => (
        <PlantItem
          key={Plante.id_plante}
          Plante={Plante}
          onClick={onPlantClick}
          isSelected={selectedPlant !== null && selectedPlant.id_plante === Plante.id_plante}
          isDisabled={money < Plante.cout}
          plantSize={plantSize} 
        />
      ))}
    </div>
  );
};

export default PlantList;