/**
 * Vérifie si l'objet correspond à la structure attendue pour une Plante
 * @param obj L'objet à vérifier
 * @returns {boolean} true si l'objet correspond à la structure, false sinon
 */
export function checkPlanteStructure(obj: any): boolean {
    if (!obj || typeof obj !== 'object') return false;

    // Vérifier les champs obligatoires avec leurs types
    if (obj.id_plante !== null && typeof obj.id_plante !== 'number') return false;
    if (typeof obj.nom !== 'string') return false;
    if (typeof obj.point_de_vie !== 'number') return false;
    if (typeof obj.attaque_par_seconde !== 'number') return false;
    if (typeof obj.degat_attaque !== 'number') return false;
    if (typeof obj.cout !== 'number') return false;
    if (typeof obj.soleil_par_seconde !== 'number') return false;
    if (typeof obj.effet !== 'string') return false;
    if (typeof obj.chemin_image !== 'string') return false;

    return true;
}

/**
 * Compare l'objet reçu avec la structure de Plante
 * @param obj L'objet à vérifier
 * @returns {object} Résultat de la comparaison avec détails
 */
export function comparePlanteStructure(obj: any): { valid: boolean; details: string[] } {
    const details: string[] = [];

    if (!obj || typeof obj !== 'object') {
        return { valid: false, details: ['L\'objet n\'est pas valide'] };
    }

    // Vérifier chaque champ avec son type attendu
    if (!('id_plante' in obj)) details.push("Champ 'id_plante' manquant");
    else if (obj.id_plante !== null && typeof obj.id_plante !== 'number') details.push("Champ 'id_plante' devrait être un nombre ou null");

    if (!('nom' in obj)) details.push("Champ 'nom' manquant");
    else if (typeof obj.nom !== 'string') details.push("Champ 'nom' devrait être une chaîne");

    if (!('point_de_vie' in obj)) details.push("Champ 'point_de_vie' manquant");
    else if (typeof obj.point_de_vie !== 'number') details.push("Champ 'point_de_vie' devrait être un nombre");

    if (!('attaque_par_seconde' in obj)) details.push("Champ 'attaque_par_seconde' manquant");
    else if (typeof obj.attaque_par_seconde !== 'number') details.push("Champ 'attaque_par_seconde' devrait être un nombre (float)");

    if (!('degat_attaque' in obj)) details.push("Champ 'degat_attaque' manquant");
    else if (typeof obj.degat_attaque !== 'number') details.push("Champ 'degat_attaque' devrait être un nombre");

    if (!('cout' in obj)) details.push("Champ 'cout' manquant");
    else if (typeof obj.cout !== 'number') details.push("Champ 'cout' devrait être un nombre");

    if (!('soleil_par_seconde' in obj)) details.push("Champ 'soleil_par_seconde' manquant");
    else if (typeof obj.soleil_par_seconde !== 'number') details.push("Champ 'soleil_par_seconde' devrait être un nombre (float)");

    if (!('effet' in obj)) details.push("Champ 'effet' manquant");
    else if (typeof obj.effet !== 'string') details.push("Champ 'effet' devrait être une chaîne");

    if (!('chemin_image' in obj)) details.push("Champ 'chemin_image' manquant");
    else if (typeof obj.chemin_image !== 'string') details.push("Champ 'chemin_image' devrait être une chaîne");

    return { valid: details.length === 0, details };
}

/**
 * Vérifie si l'objet correspond à la structure attendue pour un Zombie
 * @param obj L'objet à vérifier
 * @returns {boolean} true si l'objet correspond à la structure, false sinon
 */
export function checkZombieStructure(obj: any): boolean {
    if (!obj || typeof obj !== 'object') return false;

    // Vérifier les champs obligatoires avec leurs types
    if (obj.id_zombie !== null && typeof obj.id_zombie !== 'number') return false;
    if (typeof obj.nom !== 'string') return false;
    if (typeof obj.point_de_vie !== 'number') return false;
    if (typeof obj.attaque_par_seconde !== 'number') return false;
    if (typeof obj.degat_attaque !== 'number') return false;
    if (typeof obj.vitesse_de_deplacement !== 'number') return false;
    if (typeof obj.chemin_image !== 'string') return false;
    
    // id_map est optionnel, mais s'il est présent, il doit être un nombre ou null
    if ('id_map' in obj && obj.id_map !== null && typeof obj.id_map !== 'number') return false;

    return true;
}

/**
 * Compare l'objet reçu avec la structure de Zombie
 * @param obj L'objet à vérifier
 * @returns {object} Résultat de la comparaison avec détails
 */
export function compareZombieStructure(obj: any): { valid: boolean; details: string[] } {
    const details: string[] = [];

    if (!obj || typeof obj !== 'object') {
        return { valid: false, details: ['L\'objet n\'est pas valide'] };
    }

    // Vérifier chaque champ avec son type attendu
    if (!('id_zombie' in obj)) details.push("Champ 'id_zombie' manquant");
    else if (obj.id_zombie !== null && typeof obj.id_zombie !== 'number') details.push("Champ 'id_zombie' devrait être un nombre ou null");

    if (!('nom' in obj)) details.push("Champ 'nom' manquant");
    else if (typeof obj.nom !== 'string') details.push("Champ 'nom' devrait être une chaîne");

    if (!('point_de_vie' in obj)) details.push("Champ 'point_de_vie' manquant");
    else if (typeof obj.point_de_vie !== 'number') details.push("Champ 'point_de_vie' devrait être un nombre");

    if (!('attaque_par_seconde' in obj)) details.push("Champ 'attaque_par_seconde' manquant");
    else if (typeof obj.attaque_par_seconde !== 'number') details.push("Champ 'attaque_par_seconde' devrait être un nombre (float)");

    if (!('degat_attaque' in obj)) details.push("Champ 'degat_attaque' manquant");
    else if (typeof obj.degat_attaque !== 'number') details.push("Champ 'degat_attaque' devrait être un nombre");

    if (!('vitesse_de_deplacement' in obj)) details.push("Champ 'vitesse_de_deplacement' manquant");
    else if (typeof obj.vitesse_de_deplacement !== 'number') details.push("Champ 'vitesse_de_deplacement' devrait être un nombre (float)");

    if (!('chemin_image' in obj)) details.push("Champ 'chemin_image' manquant");
    else if (typeof obj.chemin_image !== 'string') details.push("Champ 'chemin_image' devrait être une chaîne");

    if ('id_map' in obj && obj.id_map !== null && typeof obj.id_map !== 'number') {
        details.push("Champ 'id_map' devrait être un nombre ou null");
    }

    return { valid: details.length === 0, details };
}

/**
 * Vérifie si l'objet correspond à la structure attendue pour une Map
 * @param obj L'objet à vérifier
 * @returns {boolean} true si l'objet correspond à la structure, false sinon
 */
export function checkMapStructure(obj: any): boolean {
    if (!obj || typeof obj !== 'object') return false;

    // Vérifier les champs obligatoires avec leurs types
    if (obj.id_map !== null && typeof obj.id_map !== 'number') return false;
    if (typeof obj.ligne !== 'number') return false;
    if (typeof obj.colonne !== 'number') return false;
    if (typeof obj.chemin_image !== 'string') return false;

    return true;
}

/**
 * Compare l'objet reçu avec la structure de GameMap
 * @param obj L'objet à vérifier
 * @returns {object} Résultat de la comparaison avec détails
 */
export function compareMapStructure(obj: any): { valid: boolean; details: string[] } {
    const details: string[] = [];

    if (!obj || typeof obj !== 'object') {
        return { valid: false, details: ['L\'objet n\'est pas valide'] };
    }

    // Vérifier chaque champ avec son type attendu
    if (!('id_map' in obj)) details.push("Champ 'id_map' manquant");
    else if (obj.id_map !== null && typeof obj.id_map !== 'number') details.push("Champ 'id_map' devrait être un nombre ou null");

    if (!('ligne' in obj)) details.push("Champ 'ligne' manquant");
    else if (typeof obj.ligne !== 'number') details.push("Champ 'ligne' devrait être un nombre");

    if (!('colonne' in obj)) details.push("Champ 'colonne' manquant");
    else if (typeof obj.colonne !== 'number') details.push("Champ 'colonne' devrait être un nombre");

    if (!('chemin_image' in obj)) details.push("Champ 'chemin_image' manquant");
    else if (typeof obj.chemin_image !== 'string') details.push("Champ 'chemin_image' devrait être une chaîne");

    return { valid: details.length === 0, details };
}