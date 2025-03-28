import { useState, useEffect } from "react";

export function usePlantListLogic(availableHeight: number): number {
  const [plantSize, setPlantSize] = useState(100);

  useEffect(() => {
    const updateSize = () => {
      const maxWidth = window.innerWidth * 0.2; 
      const newPlantSize = Math.min(maxWidth / 4, availableHeight / 3);
      setPlantSize(newPlantSize);
    };

    updateSize();
    window.addEventListener("resize", updateSize);
    return () => window.removeEventListener("resize", updateSize);
  }, [availableHeight]);

  return plantSize;
}
