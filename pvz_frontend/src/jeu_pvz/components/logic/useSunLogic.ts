import { useState, useEffect } from "react";
import { Plante } from "../../types/Plant";

export function useSunLogic(Plante?: Plante | null) {
  const [hasSun, setHasSun] = useState(false);
  const [sunTimer, setSunTimer] = useState(0);

  useEffect(() => {
    let generationInterval: number | null = null;
    let countdownInterval: number | null = null;

    if (Plante?.soleil_par_seconde && Plante.soleil_par_seconde > 0) {
      const interval = (1000 * 25 * 25) / Plante.soleil_par_seconde;
      const sunDisplayDuration = interval * 0.2;

      setSunTimer(interval / 1000);

      generationInterval = window.setInterval(() => {
        setHasSun(true);
        window.setTimeout(() => {
          setHasSun(false);
        }, sunDisplayDuration);
        setSunTimer(interval / 1000);
      }, interval);

      countdownInterval = window.setInterval(() => {
        setSunTimer((prev) => (prev > 0 ? prev - 1 : 0));
      }, 1000);
    }

    return () => {
      if (generationInterval) window.clearInterval(generationInterval);
      if (countdownInterval) window.clearInterval(countdownInterval);
    };
  }, [Plante]);

  return { hasSun, sunTimer, setHasSun };
}
