import React, { useState, FormEvent } from 'react';
import { GameMap } from '../../domains/maps/maps.types';

type MapEditFormProps = {
    initialMap: GameMap;
    onSave: (updatedMap: GameMap) => void;
    onCancel: () => void;
};

export default function MapEditForm({
    initialMap,
    onSave,
    onCancel,
}: MapEditFormProps) {
    const [mapData, setMapData] = useState<GameMap>({ ...initialMap });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        if (!name) return;

        if (name === 'ligne' || name === 'colonne') {
            setMapData((prev) => ({ ...prev, [name]: parseInt(value) }));
        } else {
            setMapData((prev) => ({ ...prev, [name]: value }));
        }
    };

    const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        onSave(mapData);
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col space-y-4">
            <div className="flex flex-col">
                <label htmlFor="ligne" className="mb-1 text-sm font-medium text-gray-700">
                    Lignes
                </label>
                <input
                    id="ligne"
                    name="ligne"
                    type="number"
                    value={mapData.ligne}
                    onChange={handleChange}
                    required
                    className="px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-green-500 focus:border-green-500"
                />
            </div>

            <div className="flex flex-col">
                <label htmlFor="colonne" className="mb-1 text-sm font-medium text-gray-700">
                    Colonnes
                </label>
                <input
                    id="colonne"
                    name="colonne"
                    type="number"
                    value={mapData.colonne}
                    onChange={handleChange}
                    required
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
                    value={mapData.chemin_image}
                    onChange={handleChange}
                    placeholder="/images/maps/image.jpg"
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