import { useEffect, useState } from 'react';
import { Dialog } from '@headlessui/react';
import { Map as MapIcon, Skull, Sprout, Plus } from 'lucide-react';

import { Plante, PlanteEffet } from '../../domains/plants/plants.types';
import { Zombie } from '../../domains/zombies/zombies.types';
import { GameMap } from '../../domains/maps/maps.types';
import { useGetPlants, useUpdatePlant, useCreatePlant } from '../../domains/plants/plants.hooks';
import { useGetZombies, useUpdateZombie, useCreateZombie } from '../../domains/zombies/zombies.hooks';
import { useGetMaps, useUpdateMap, useCreateMap } from '../../domains/maps/maps.hooks';
import PlantEditForm from './PlantEditForm';
import ZombieEditForm from './ZombieEditForm';
import MapEditForm from './MapEditForm';
import { getImageUrl } from './imageUtils';

const DEFAULT_PLACEHOLDER = "https://via.placeholder.com/400x300?text=Image+Non+Disponible";

type EditorTab = 'plants' | 'zombies' | 'maps';
type ModalMode = 'create' | 'edit';

export default function EditorPage() {
  const [activeTab, setActiveTab] = useState<EditorTab>('plants');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalMode, setModalMode] = useState<ModalMode>('edit');
  const [selectedItem, setSelectedItem] = useState<Plante | Zombie | GameMap | null>(null);
  
  const [plants, setPlants] = useState<Plante[]>([]);
  const [zombies, setZombies] = useState<Zombie[]>([]);
  const [maps, setMaps] = useState<GameMap[]>([]);
  
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const { mutateAsync: fetchPlants } = useGetPlants();
  const { mutateAsync: fetchZombies } = useGetZombies();
  const { mutateAsync: fetchMaps } = useGetMaps();
  
  const { mutateAsync: updatePlantData } = useUpdatePlant();
  const { mutateAsync: updateZombieData } = useUpdateZombie();
  const { mutateAsync: updateMapData } = useUpdateMap();

  const { mutateAsync: createPlantData } = useCreatePlant();
  const { mutateAsync: createZombieData } = useCreateZombie();
  const { mutateAsync: createMapData } = useCreateMap();

  const defaultPlant: Omit<Plante, 'id_plante'> = {
    nom: "Nouvelle plante",
    point_de_vie: 100,
    attaque_par_seconde: 1.0,
    degat_attaque: 20,
    cout: 50,
    soleil_par_seconde: 5.0,
    effet: PlanteEffet.Normal,
    chemin_image: "/images/plantes/default.png"
  };

  const defaultZombie: Omit<Zombie, 'id_zombie'> = {
    nom: "Nouveau zombie",
    point_de_vie: 150,
    attaque_par_seconde: 0.5,
    degat_attaque: 15,
    vitesse_de_deplacement: 1.0,
    chemin_image: "/images/zombies/default.png"
  };

  const defaultMap: Omit<GameMap, 'id_map'> = {
    ligne: 5,
    colonne: 9,
    chemin_image: "/images/maps/default.png"
  };

  const loadData = async () => {
    setLoading(true);
    setError(null);
    
    try {
      switch (activeTab) {
        case 'plants':
          const plantsData = await fetchPlants();
          setPlants(plantsData);
          break;
        case 'zombies':
          const zombiesData = await fetchZombies();
          setZombies(zombiesData);
          break;
        case 'maps':
          const mapsData = await fetchMaps();
          setMaps(mapsData);
          break;
      }
    } catch (err) {
      console.error('Erreur lors du chargement des données:', err);
      setError('Erreur lors du chargement des données. Veuillez réessayer.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, [activeTab]);

  const tabs = [
    { id: 'plants', label: 'Plants', icon: Sprout },
    { id: 'zombies', label: 'Zombies', icon: Skull },
    { id: 'maps', label: 'Maps', icon: MapIcon },
  ];

  const handleEdit = (item: Plante | Zombie | GameMap) => {
    setSelectedItem(item);
    setModalMode('edit');
    setIsModalOpen(true);
  };

  const handleCreate = () => {
    let newItem: any;
    
    switch (activeTab) {
      case 'plants':
        newItem = { ...defaultPlant, id_plante: null };
        break;
      case 'zombies':
        newItem = { ...defaultZombie, id_zombie: null };
        break;
      case 'maps':
        newItem = { ...defaultMap, id_map: null };
        break;
    }
    
    setSelectedItem(newItem);
    setModalMode('create');
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setSelectedItem(null);
  };

  const handleSave = async (updatedItem: Plante | Zombie | GameMap) => {
    try {
      if (modalMode === 'edit') {
        if (isPlant(updatedItem)) {
          await updatePlantData({
            id_plante: updatedItem.id_plante,
            data: updatedItem
          });
        } else if (isZombie(updatedItem)) {
          await updateZombieData({
            id_zombie: updatedItem.id_zombie,
            data: updatedItem
          });
        } else if (isMap(updatedItem)) {
          await updateMapData({
            id_map: updatedItem.id_map,
            data: updatedItem
          });
        }
      } else {
        if (isPlant(updatedItem)) {
          const { id_plante, ...plantData } = updatedItem;
          await createPlantData(plantData as Omit<Plante, 'id_plante'>);
        } else if (isZombie(updatedItem)) {
          const { id_zombie, ...zombieData } = updatedItem;
          await createZombieData(zombieData as Omit<Zombie, 'id_zombie'>);
        } else if (isMap(updatedItem)) {
          const { id_map, ...mapData } = updatedItem;
          await createMapData(mapData as Omit<GameMap, 'id_map'>);
        }
      }
      
      await loadData();
      closeModal();
    } catch (err) {
      console.error('Erreur lors de la sauvegarde:', err);
      alert('Erreur lors de la sauvegarde. Veuillez réessayer.');
    }
  };

  const isPlant = (item: any): item is Plante => item && 'id_plante' in item;
  const isZombie = (item: any): item is Zombie => item && 'id_zombie' in item;
  const isMap = (item: any): item is GameMap => item && 'id_map' in item && 'ligne' in item && 'colonne' in item;

  const renderCard = (item: Plante | Zombie | GameMap) => {
    let imageUrl = '';
    let title = '';
    let details: JSX.Element | null = null;

    if (isPlant(item)) {
      imageUrl = item.chemin_image;
      title = item.nom;
      details = (
        <>
          <p>Point de vie: {item.point_de_vie}</p>
          <p>Dégat d'attaque: {item.degat_attaque}</p>
          <p>Coût: {item.cout}</p>
        </>
      );
    } else if (isZombie(item)) {
      imageUrl = item.chemin_image;
      title = item.nom;
      details = (
        <>
          <p>Point de vie: {item.point_de_vie}</p>
          <p>Dégat d'attaque: {item.degat_attaque}</p>
        </>
      );
    } else if (isMap(item)) {
      imageUrl = item.chemin_image;
      title = `Map #${item.id_map}`;
      details = (
        <>
          <p>Lignes: {item.ligne}</p>
          <p>Colonnes: {item.colonne}</p>
        </>
      );
    }

    const displayUrl = typeof getImageUrl === 'function' ? getImageUrl(imageUrl) : imageUrl;

    const key = isPlant(item) ? `plant-${item.id_plante}` : 
                isZombie(item) ? `zombie-${item.id_zombie}` : 
                isMap(item) ? `map-${item.id_map}` : 
                `item-${Math.random()}`;

    return (
      <div
        key={key}
        className="bg-white rounded-lg shadow-md overflow-hidden"
      >
        <div className="w-full h-48 overflow-hidden relative">
          <img
            src={displayUrl}
            alt={title}
            className="w-full h-full object-cover"
            onError={(e) => {
              const target = e.target as HTMLImageElement;
              target.onerror = null;
              target.src = DEFAULT_PLACEHOLDER;
            }}
          />
        </div>
        <div className="p-4">
          <h3 className="text-lg font-semibold">{title}</h3>
          <div className="mt-2 space-y-1 text-sm text-gray-600">{details}</div>
          <button
            onClick={() => handleEdit(item)}
            className="mt-4 w-full bg-green-600 text-white py-2 rounded-md hover:bg-green-700"
          >
            Editer
          </button>
        </div>
      </div>
    );
  };

  const renderContent = () => {
    if (loading) {
      return (
        <div className="flex justify-center items-center h-64">
          <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-green-500"></div>
        </div>
      );
    }

    if (error) {
      return (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative">
          <strong className="font-bold">Erreur!</strong>
          <span className="block sm:inline"> {error}</span>
          <button 
            onClick={loadData}
            className="mt-2 px-4 py-2 bg-red-200 text-red-800 rounded hover:bg-red-300"
          >
            Réessayer
          </button>
        </div>
      );
    }

    const currentData = activeTab === 'plants' ? plants : 
                       activeTab === 'zombies' ? zombies : maps;
    
    if (currentData.length === 0) {
      return (
        <div className="text-center py-10 bg-gray-50 rounded-lg">
          <p className="text-gray-500">Aucune donnée disponible pour le moment.</p>
        </div>
      );
    }

    return (
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {activeTab === 'plants' && plants.map((plant) => renderCard(plant))}
        {activeTab === 'zombies' && zombies.map((zombie) => renderCard(zombie))}
        {activeTab === 'maps' && maps.map((mapItem) => renderCard(mapItem))}
      </div>
    );
  };

  return (
    <div className="space-y-6">
      <div className="border-b border-gray-200">
        <nav className="flex space-x-8">
          {tabs.map((tab) => (
            <button
              key={tab.id}
              onClick={() => setActiveTab(tab.id as EditorTab)}
              className={`py-4 px-1 inline-flex items-center space-x-2 border-b-2 font-medium text-sm
                ${
                  activeTab === tab.id
                    ? 'border-green-500 text-green-600'
                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                }`}
            >
              <tab.icon className="h-5 w-5" />
              <span>{tab.label}</span>
            </button>
          ))}
        </nav>
      </div>

      <div className="flex justify-between items-center">
        <h2 className="text-2xl font-bold">
          {activeTab === 'plants' ? 'Plantes' : activeTab === 'zombies' ? 'Zombies' : 'Maps'}
        </h2>
        <button
          onClick={handleCreate}
          className="flex items-center space-x-2 px-4 py-2 bg-green-600 text-white rounded-md hover:bg-green-700"
        >
          <Plus className="h-5 w-5" />
          <span>Créer {activeTab === 'plants' ? 'une plante' : activeTab === 'zombies' ? 'un zombie' : 'une map'}</span>
        </button>
      </div>

      {renderContent()}

      {isModalOpen && (
        <Dialog
          open={isModalOpen}
          onClose={closeModal}
          className="fixed inset-0 z-10 overflow-y-auto"
          initialFocus={undefined}
        >
          <div className="flex items-center justify-center min-h-screen p-4">
            <Dialog.Overlay className="fixed inset-0 bg-black bg-opacity-30" />

            <div className="relative bg-white rounded-lg p-6 max-w-md w-full mx-4 z-20">
              <Dialog.Title className="text-lg font-medium mb-4">
                {modalMode === 'create' 
                  ? `Créer ${activeTab === 'plants' ? 'une plante' : activeTab === 'zombies' ? 'un zombie' : 'une map'}`
                  : selectedItem 
                    ? (isMap(selectedItem) 
                        ? `Map #${selectedItem.id_map}` 
                        : selectedItem.nom) 
                    : 'Édition'
                }
              </Dialog.Title>

              {selectedItem && isPlant(selectedItem) && (
                <PlantEditForm
                  initialPlant={selectedItem}
                  onSave={handleSave}
                  onCancel={closeModal}
                />
              )}

              {selectedItem && isZombie(selectedItem) && (
                <ZombieEditForm
                  initialZombie={selectedItem}
                  onSave={handleSave}
                  onCancel={closeModal}
                />
              )}

              {selectedItem && isMap(selectedItem) && (
                <MapEditForm
                  initialMap={selectedItem}
                  onSave={handleSave}
                  onCancel={closeModal}
                />
              )}
            </div>
          </div>
        </Dialog>
      )}
    </div>
  );
}