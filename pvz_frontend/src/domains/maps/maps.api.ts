import { axiosClient } from '../axiosClient.ts'; 
import { GameMap } from "./maps.types.ts";

const MAPS_API_URL = '/maps';

export async function getMaps(): Promise<GameMap[]> {
  const response = await axiosClient.get(MAPS_API_URL);
  return response.data;
}

export async function getMap(id_map: number): Promise<GameMap> {
  const response = await axiosClient.get(`${MAPS_API_URL}/${id_map}`);
  return response.data;
}

export async function createMap(mapData: Omit<GameMap, 'id_map'>): Promise<GameMap> {
  const response = await axiosClient.post(MAPS_API_URL, mapData);
  return response.data;
}

export async function updateMap(id_map: number, mapData: Partial<GameMap>): Promise<GameMap> {
  const response = await axiosClient.put(`${MAPS_API_URL}/${id_map}`, mapData);
  return response.data;
}

export async function deleteMap(id_map: number): Promise<void> {
  await axiosClient.delete(`${MAPS_API_URL}/${id_map}`);
}