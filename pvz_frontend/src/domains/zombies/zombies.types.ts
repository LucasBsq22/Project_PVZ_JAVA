// zombies.types.ts
export interface Zombie {
  id_zombie: number;
  nom: string;
  point_de_vie: number;
  attaque_par_seconde: number;
  degat_attaque: number;
  vitesse_de_deplacement: number;
  chemin_image: string;
  id_map?: number; // Optionnel pour correspondre au DTO Java
}