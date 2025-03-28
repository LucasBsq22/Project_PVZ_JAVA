import { axiosClient } from "../axiosClient.ts";
import { Plante } from "./plants.types.ts";

const API_URL = '/plantes';

export async function getPlants(): Promise<Plante[]> {
  const response = await axiosClient.get(API_URL);
  return response.data;
}

export async function getPlant(id_plante: number): Promise<Plante> {
  const response = await axiosClient.get(`${API_URL}/${id_plante}`);
  return response.data;
}

export async function createPlant(plantData: Omit<Plante, 'id_plante'>): Promise<Plante> {
  const response = await axiosClient.post(API_URL, plantData);
  return response.data;
}

export async function updatePlant(id_plante: number, plantData: Partial<Plante>): Promise<Plante> {
  const response = await axiosClient.put(`${API_URL}/${id_plante}`, plantData);
  return response.data;
}

export async function deletePlant(id_plante: number): Promise<void> {
  await axiosClient.delete(`${API_URL}/${id_plante}`);
}