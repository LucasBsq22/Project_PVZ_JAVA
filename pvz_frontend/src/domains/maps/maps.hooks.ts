import { useMutation } from '@tanstack/react-query';
import {
  getMaps,
  getMap,
  createMap,
  updateMap,
  deleteMap,
} from './maps.api';
import type { GameMap } from './maps.types';

export const useGetMaps = () => {
  return useMutation({
    mutationFn: getMaps,
  });
};

export const useGetMap = () => {
  return useMutation({
    mutationFn: (id_map: number) => getMap(id_map),
  });
};

export const useCreateMap = () => {
  return useMutation({
    mutationFn: (mapData: Omit<GameMap, 'id_map'>) => createMap(mapData),
  });
};

export const useUpdateMap = () => {
  return useMutation({
    mutationFn: (params: { id_map: number; data: Partial<GameMap> }) =>
      updateMap(params.id_map, params.data),
  });
};

export const useDeleteMap = () => {
  return useMutation({
    mutationFn: (id_map: number) => deleteMap(id_map),
  });
};