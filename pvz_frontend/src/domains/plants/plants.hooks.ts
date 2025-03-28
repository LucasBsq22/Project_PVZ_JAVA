import { useMutation } from '@tanstack/react-query';
import {
  getPlants,
  getPlant,
  createPlant,
  updatePlant,
  deletePlant,
} from './plants.api'; 
import type { Plante } from './plants.types';

export const useGetPlants = () => {
  return useMutation({
    mutationFn: getPlants,
  });
};

export const useGetPlant = () => {
  return useMutation({
    mutationFn: (id_plante: number) => getPlant(id_plante),
  });
};

export const useCreatePlant = () => {
  return useMutation({
    mutationFn: (planteData: Omit<Plante, 'id_plante'>) => createPlant(planteData),
  });
};

export const useUpdatePlant = () => {
  return useMutation({
    mutationFn: (params: { id_plante: number; data: Partial<Plante> }) =>
      updatePlant(params.id_plante, params.data),
  });
};

export const useDeletePlant = () => {
  return useMutation({
    mutationFn: (id_plante: number) => deletePlant(id_plante),
  });
};