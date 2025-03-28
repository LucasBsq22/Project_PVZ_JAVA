import React, { useState, FormEvent } from 'react';
import { Plante, PlanteEffet } from '../../domains/plants/plants.types';

type PlantEditFormProps = {
    initialPlant: Plante;
    onSave: (updatedPlant: Plante) => void;
    onCancel: () => void;
};

export default function PlantEditForm({
    initialPlant,
    onSave,
    onCancel,
}: PlantEditFormProps) {
    const [plant, setPlant] = useState<Plante>({ ...initialPlant });

    const handleChange = (
        e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
    ) => {
        const { name, value } = e.target;
        if (!name) return;

        if (
            name === 'point_de_vie' ||
            name === 'degat_attaque' ||
            name === 'cout' ||
            name === 'attaque_par_seconde' ||
            name === 'soleil_par_seconde'
        ) {
            // Conversion en nombre pour les champs numériques
            const numValue = name === 'attaque_par_seconde' || name === 'soleil_par_seconde' 
                ? parseFloat(value) 
                : parseInt(value);
                
            setPlant((prev) => ({ ...prev, [name]: numValue }));
        } else {
            setPlant((prev) => ({ ...prev, [name]: value }));
        }
    };

    const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        onSave(plant);
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col space-y-4">
            <div className="flex flex-col">
                <label htmlFor="nom" className="mb-1 text-sm font-medium text-gray-700">
                    Nom
                </label>
                <input
                    id="nom"
                    name="nom"
                    type="text"
                    value={plant.nom}
                    onChange={handleChange}
                    required
                    className="px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-green-500 focus:border-green-500"
                />
            </div>

            <div className="flex flex-col">
                <label htmlFor="point_de_vie" className="mb-1 text-sm font-medium text-gray-700">
                    Point de vie
                </label>
                <input
                    id="point_de_vie"
                    name="point_de_vie"
                    type="number"
                    value={plant.point_de_vie}
                    onChange={handleChange}
                    required
                    className="px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-green-500 focus:border-green-500"
                />
            </div>

            <div className="flex flex-col">
                <label htmlFor="degat_attaque" className="mb-1 text-sm font-medium text-gray-700">
                    Dégât d'attaque
                </label>
                <input
                    id="degat_attaque"
                    name="degat_attaque"
                    type="number"
                    value={plant.degat_attaque}
                    onChange={handleChange}
                    required
                    className="px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-green-500 focus:border-green-500"
                />
            </div>

            <div className="flex flex-col">
                <label htmlFor="cout" className="mb-1 text-sm font-medium text-gray-700">
                    Coût
                </label>
                <input
                    id="cout"
                    name="cout"
                    type="number"
                    value={plant.cout}
                    onChange={handleChange}
                    required
                    className="px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-green-500 focus:border-green-500"
                />
            </div>

            <div className="flex flex-col">
                <label htmlFor="attaque_par_seconde" className="mb-1 text-sm font-medium text-gray-700">
                    Attaque par seconde
                </label>
                <input
                    id="attaque_par_seconde"
                    name="attaque_par_seconde"
                    type="number"
                    step="0.01"
                    value={plant.attaque_par_seconde}
                    onChange={handleChange}
                    required
                    className="px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-green-500 focus:border-green-500"
                />
            </div>

            <div className="flex flex-col">
                <label htmlFor="soleil_par_seconde" className="mb-1 text-sm font-medium text-gray-700">
                    Soleil par seconde
                </label>
                <input
                    id="soleil_par_seconde"
                    name="soleil_par_seconde"
                    type="number"
                    step="0.01"
                    value={plant.soleil_par_seconde}
                    onChange={handleChange}
                    className="px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-green-500 focus:border-green-500"
                />
            </div>

            <div className="flex flex-col">
                <label htmlFor="effet" className="mb-1 text-sm font-medium text-gray-700">
                    Effet
                </label>
                <select
                    id="effet"
                    name="effet"
                    value={plant.effet}
                    onChange={handleChange}
                    className="px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-green-500 focus:border-green-500"
                >
                    {Object.values(PlanteEffet).map((effetValue) => (
                        <option key={effetValue} value={effetValue}>
                            {effetValue}
                        </option>
                    ))}
                </select>
            </div>

            <div className="flex flex-col">
                <label htmlFor="chemin_image" className="mb-1 text-sm font-medium text-gray-700">
                    Image URL
                </label>
                <input
                    id="chemin_image"
                    name="chemin_image"
                    type="text"
                    value={plant.chemin_image}
                    onChange={handleChange}
                    placeholder="/images/plantes/image.png"
                    className="px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-green-500 focus:border-green-500"
                />
            </div>

            <div className="flex justify-end gap-2 mt-4">
                <button
                    type="button"
                    onClick={onCancel}
                    className="px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500"
                >
                    Annuler
                </button>
                <button
                    type="submit"
                    className="px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500"
                >
                    Enregistrer
                </button>
            </div>
        </form>
    );
}