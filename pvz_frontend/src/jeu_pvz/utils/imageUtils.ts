// URL de base de l'API
const baseUrl = 'http://localhost:8080/CoursEpfBack';

// Base64 de l'image de placeholder (petit image grise avec texte "Image Non Disponible")
const BASE64_PLACEHOLDER = "data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSI0MDAiIGhlaWdodD0iMzAwIiB2aWV3Qm94PSIwIDAgNDAwIDMwMCI+PHJlY3Qgd2lkdGg9IjQwMCIgaGVpZ2h0PSIzMDAiIGZpbGw9IiNlMWUxZTEiLz48dGV4dCB4PSIxNTAiIHk9IjE1MCIgZm9udC1mYW1pbHk9InNhbnMtc2VyaWYiIGZvbnQtc2l6ZT0iMjAiIGZpbGw9IiM0QTRBNEEiPkltYWdlIE5vbiBEaXNwb25pYmxlPC90ZXh0Pjwvc3ZnPg==";

export const DEFAULT_PLACEHOLDER = BASE64_PLACEHOLDER;

/**
 * Transforme un chemin d'image en URL complète
 * @param path Chemin de l'image
 * @returns URL complète de l'image
 */
export const getImageUrl = (path: string): string => {
  // Si le chemin est vide ou null, retourne le placeholder par défaut
  if (!path) {
    return DEFAULT_PLACEHOLDER;
  }
  
  // Si c'est déjà une URL complète, la retourner
  if (path.startsWith('http://') || path.startsWith('https://')) {
    return path;
  }
  
  // Si le chemin commence par /, ajouter l'URL de base
  if (path.startsWith('/')) {
    return `${baseUrl}${path}`;
  }
  
  // Sinon, ajouter l'URL de base avec /
  return `${baseUrl}/${path}`;
};

/**
 * Gestionnaire d'erreur pour les images
 * À utiliser avec l'événement onError des balises img
 */
export const handleImageError = (event: React.SyntheticEvent<HTMLImageElement>): void => {
  const target = event.target as HTMLImageElement;
  target.onerror = null; // Important pour éviter les boucles infinies
  target.src = DEFAULT_PLACEHOLDER;
};