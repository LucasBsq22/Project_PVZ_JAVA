// plants.types.ts
export enum PlanteEffet {
  Normal = 'normal',
  SlowLow = 'slow low',
  SlowMedium = 'slow medium',
  SlowStop = 'slow stop'
}

export interface Plante {
  id_plante: number;
  nom: string;
  point_de_vie: number;
  attaque_par_seconde: number;
  degat_attaque: number;
  cout: number;
  soleil_par_seconde: number;
  effet: string; // Utiliser string au lieu de l'enum pour correspondre au DTO Java
  chemin_image: string;
}