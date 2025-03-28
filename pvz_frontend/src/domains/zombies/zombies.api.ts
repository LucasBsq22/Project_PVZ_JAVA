import { axiosClient } from "../axiosClient.ts";
import { Zombie } from "./zombies.types.ts";

const API_URL = '/zombies';

export async function getZombies(): Promise<Zombie[]> {
  const response = await axiosClient.get(API_URL);
  return response.data;
}

export async function getZombie(id_zombie: number): Promise<Zombie> {
  const response = await axiosClient.get(`${API_URL}/${id_zombie}`);
  return response.data;
}

export async function createZombie(zombieData: Omit<Zombie, 'id_zombie'>): Promise<Zombie> {
  const response = await axiosClient.post(API_URL, zombieData);
  return response.data;
}

export async function updateZombie(id_zombie: number, zombieData: Partial<Zombie>): Promise<Zombie> {
  const response = await axiosClient.put(`${API_URL}/${id_zombie}`, zombieData);
  return response.data;
}

export async function deleteZombie(id_zombie: number): Promise<void> {
  await axiosClient.delete(`${API_URL}/${id_zombie}`);
}