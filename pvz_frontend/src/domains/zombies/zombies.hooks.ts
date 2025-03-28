import { useMutation } from '@tanstack/react-query';
import {
  getZombies,
  getZombie,
  createZombie,
  updateZombie,
  deleteZombie,
} from './zombies.api';
import type { Zombie } from './zombies.types';

export const useGetZombies = () => {
  return useMutation({
    mutationFn: getZombies,
  });
};

export const useGetZombie = () => {
  return useMutation({
    mutationFn: (id_zombie: number) => getZombie(id_zombie),
  });
};

export const useCreateZombie = () => {
  return useMutation({
    mutationFn: (zombieData: Omit<Zombie, 'id_zombie'>) => createZombie(zombieData),
  });
};

export const useUpdateZombie = () => {
  return useMutation({
    mutationFn: (params: { id_zombie: number; data: Partial<Zombie> }) =>
      updateZombie(params.id_zombie, params.data),
  });
};

export const useDeleteZombie = () => {
  return useMutation({
    mutationFn: (id_zombie: number) => deleteZombie(id_zombie),
  });
};