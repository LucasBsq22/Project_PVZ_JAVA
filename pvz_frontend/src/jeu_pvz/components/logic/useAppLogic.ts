import { useState, useEffect } from "react";
import { useGameContext } from "../../context/GameContext";
import { Plante } from "../../../domains/plants/plants.types";
import { GameMap } from "../../../domains/maps/maps.types";
import { getPlants } from "../../../domains/plants/plants.api";
import { getMaps } from "../../../domains/maps/maps.api";

interface UseAppLogicResult {
  plants: Plante[];
  selectedMap: GameMap | null;
  money: number;
  collectSun: () => void;
  selectedPlant: Plante | null;
  plantsOnBoard: { [key: string]: Plante | null };
  deleteMode: boolean;
  availableHeight: number;
  handlePlantClick: (Plante: Plante) => void;
  handleCellClick: (row: number, col: number) => void;
  handlePlantRemoved: (cellKey: string) => void;
  setDeleteMode: (mode: boolean) => void;
}

export function useAppLogic(initialMoneyValue?: number): UseAppLogicResult {
  const { gameConfig } = useGameContext();
  const [plants, setPlants] = useState<Plante[]>([]);
  const [selectedMap, setSelectedMap] = useState<GameMap | null>(null);
  const [money, setMoney] = useState<number>(initialMoneyValue || gameConfig.initialMoney);
  const [selectedPlant, setSelectedPlant] = useState<Plante | null>(null);
  const [plantsOnBoard, setPlantsOnBoard] = useState<{ [key: string]: Plante | null }>({});
  const [deleteMode, setDeleteMode] = useState(false);
  const [availableHeight, setAvailableHeight] = useState(window.innerHeight * 0.95);

  useEffect(() => {
    const fetchPlants = async () => {
      try {
        const plantsData = await getPlants();
        setPlants(plantsData);
      } catch (error) {
        console.error("Erreur lors du fetch des plantes:", error);
      }
    };
    fetchPlants();
  }, []);

  useEffect(() => {
    const fetchMapData = async () => {
      try {
        const data = await getMaps();
        if (data.length > 0) setSelectedMap(data[0]);
      } catch (error) {
        console.error("Erreur lors du fetch de la carte:", error);
      }
    };
    fetchMapData();
  }, []);

  useEffect(() => {
    const updateHeight = () => {
      setAvailableHeight(window.innerHeight * 0.95);
    };
    window.addEventListener("resize", updateHeight);
    return () => window.removeEventListener("resize", updateHeight);
  }, []);

  const handlePlantClick = (Plante: Plante) => {
    if (!deleteMode) {
      setSelectedPlant(prevSelected => 
        prevSelected && prevSelected.id_plante === Plante.id_plante ? null : Plante
      );
    }
  };

  const collectSun = () => {
    setMoney((prev) => prev + 25);
  };

  const handleCellClick = (row: number, col: number) => {
    const cellKey = `${row}-${col}`;

    if (deleteMode) {
      setPlantsOnBoard((prev) => {
        if (!prev[cellKey]) return prev; 
        const updatedBoard = { ...prev };
        delete updatedBoard[cellKey];
        return updatedBoard;
      });
    } else if (selectedPlant) {
      const plantToPlace = selectedPlant;
      
      if (money < plantToPlace.cout) {
        return;
      }
      
      setSelectedPlant(null);

      setPlantsOnBoard((prev) => {
        if (prev[cellKey]) {
          return prev; 
        }
        setMoney((prevMoney) => prevMoney - plantToPlace.cout);
        return { ...prev, [cellKey]: plantToPlace };
      });
    }
  };

  const handlePlantRemoved = (cellKey: string) => {
    setPlantsOnBoard((prev) => {
      const updatedBoard = { ...prev };
      delete updatedBoard[cellKey];
      return updatedBoard;
    });
  };

  return {
    collectSun,
    money,
    plants,
    selectedMap,
    selectedPlant,
    plantsOnBoard,
    deleteMode,
    availableHeight,
    handlePlantClick,
    handleCellClick,
    handlePlantRemoved,
    setDeleteMode,
  };
}