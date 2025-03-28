import React, { useState, FormEvent } from 'react';
import { Zombie } from '../../domains/zombies/zombies.types';

type ZombieEditFormProps = {
    initialZombie: Zombie;
    onSave: (updatedZombie: Zombie) => void;
    onCancel: () => void;
};

export default function ZombieEditForm({
    initialZombie,
    onSave,
    onCancel,
}: ZombieEditFormProps) {
    const [zombie, setZombie] = useState<Zombie>({ ...initialZombie });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        if (!name) return;

        if (
            name === 'point_de_vie' ||
            name === 'degat_attaque' ||
            name === 'id_map'
        ) {
            // Conversion en nombre entier
            setZombie((prev) => ({ ...prev, [name]: parseInt(value) }));
        } else if (
            name === 'attaque_par_seconde' ||
            name === 'vitesse_de_deplacement'
        ) {
            // Conversion en nombre flottant
            setZombie((prev) => ({ ...prev, [name]: parseFloat(value) }));
        } else {
            setZombie((prev) => ({ ...prev, [name]: value }));
        }
    };

    const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        onSave(zombie);
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
                    value={zombie.nom}
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
                    value={zombie.point_de_vie}
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
                    value={zombie.attaque_par_seconde}
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
                    value={zombie.degat_attaque}
                    onChange={handleChange}
                    required
                    className="px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-green-500 focus:border-green-500"
                />
            </div>

            <div className="flex flex-col">
                <label htmlFor="vitesse_de_deplacement" className="mb-1 text-sm font-medium text-gray-700">
                    Vitesse de déplacement
                </label>
                <input
                    id="vitesse_de_deplacement"
                    name="vitesse_de_deplacement"
                    type="number"
                    step="0.01"
                    value={zombie.vitesse_de_deplacement}
                    onChange={handleChange}
                    required
                    className="px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-green-500 focus:border-green-500"
                />
            </div>

            <div className="flex flex-col">
                <label htmlFor="id_map" className="mb-1 text-sm font-medium text-gray-700">
                    ID Map
                </label>
                <input
                    id="id_map"
                    name="id_map"
                    type="number"
                    value={zombie.id_map || ''}
                    onChange={handleChange}
                    className="px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-green-500 focus:border-green-500"
                />
            </div>

            <div className="flex flex-col">
                <label htmlFor="chemin_image" className="mb-1 text-sm font-medium text-gray-700">
                    Image URL
                </label>
                <input
                    id="chemin_image"
                    name="chemin_image"
                    type="text"
                    value={zombie.chemin_image}
                    onChange={handleChange}
                    placeholder="/images/zombies/image.png"
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