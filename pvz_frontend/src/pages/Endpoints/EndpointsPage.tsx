import { useEffect, useState, lazy, Suspense } from 'react'
import { useCreatePlant, useDeletePlant, useGetPlants, useUpdatePlant } from "../../domains/plants/plants.hooks"
import { useCreateZombie, useDeleteZombie, useGetZombies, useUpdateZombie } from "../../domains/zombies/zombies.hooks"
import { useCreateMap, useDeleteMap, useGetMaps, useUpdateMap } from "../../domains/maps/maps.hooks"

const EndpointCard = lazy(() => import('./EndpointCard'))

interface ValidationResult {
  valid: boolean
  message: string
  details?: string[]
}

interface Endpoint {
  id: string
  path: string
  method: 'GET' | 'POST' | 'PUT' | 'DELETE'
  description: string
  status: 'pending' | 'success' | 'failed'
  validationResult?: ValidationResult
}

type Responses = Record<string, unknown>

export default function EndpointsPage(): JSX.Element {
  const [endpoints, setEndpoints] = useState<Endpoint[]>([])
  const [responses, setResponses] = useState<Responses>({})

  const { mutateAsync: getPlants } = useGetPlants()
  const { mutateAsync: getZombies } = useGetZombies()
  const { mutateAsync: getMaps } = useGetMaps()
  const { mutateAsync: createPlant } = useCreatePlant()
  const { mutateAsync: createZombie } = useCreateZombie()
  const { mutateAsync: createMap } = useCreateMap()
  const { mutateAsync: updatePlant } = useUpdatePlant()
  const { mutateAsync: updateZombie } = useUpdateZombie()
  const { mutateAsync: updateMap } = useUpdateMap()
  const { mutateAsync: deletePlant } = useDeletePlant()
  const { mutateAsync: deleteZombie } = useDeleteZombie()
  const { mutateAsync: deleteMap } = useDeleteMap()

  useEffect(() => {
    const mockEndpoints: Endpoint[] = [
      { id: '1', path: '/plantes', method: 'GET', description: 'Récupérer toutes les plantes', status: 'pending' },
      { id: '2', path: '/zombies', method: 'GET', description: 'Récupérer tous les zombies', status: 'pending' },
      { id: '3', path: '/maps', method: 'GET', description: 'Récupérer toutes les maps', status: 'pending' },
      { id: '4', path: '/plantes', method: 'POST', description: 'Créer une nouvelle plante', status: 'pending' },
      { id: '5', path: '/zombies', method: 'POST', description: 'Créer un nouveau zombie', status: 'pending' },
      { id: '6', path: '/maps', method: 'POST', description: 'Créer une nouvelle map', status: 'pending' },
      { id: '7', path: '/plantes/1', method: 'PUT', description: 'Modifier une plante', status: 'pending' },
      { id: '8', path: '/zombies/1', method: 'PUT', description: 'Modifier un zombie', status: 'pending' },
      { id: '9', path: '/maps/1', method: 'PUT', description: 'Modifier une map', status: 'pending' },
      { id: '10', path: '/plantes/1', method: 'DELETE', description: 'Supprimer une plante', status: 'pending' },
      { id: '11', path: '/zombies/1', method: 'DELETE', description: 'Supprimer un zombie', status: 'pending' },
      { id: '12', path: '/maps/1', method: 'DELETE', description: 'Supprimer une map', status: 'pending' },
      { id: '13', path: '/plantes/validation', method: 'GET', description: 'Valider le format des plantes retournées', status: 'pending' },
      { id: '14', path: '/zombies/validation', method: 'GET', description: 'Valider le format des zombies retournés', status: 'pending' },
      { id: '15', path: '/maps/validation', method: 'GET', description: 'Valider le format des maps retournées', status: 'pending' }
    ]
    setEndpoints(mockEndpoints)
  }, [])

  const ensureObject = (data: unknown): unknown => {
    if (typeof data === 'string') {
      try {
        return JSON.parse(data as string) as unknown
      } catch (e) {
        return data
      }
    }
    return data
  }

  const validatePlantStructure = (data: unknown): ValidationResult => {
    const parsedData = ensureObject(data)
    if (!Array.isArray(parsedData) && (typeof parsedData !== 'object' || parsedData === null)) {
      return { valid: false, message: `Données invalides: attendu un objet ou un tableau, reçu ${typeof parsedData}` }
    }
    const items: unknown[] = Array.isArray(parsedData) ? parsedData : [parsedData]
    if (items.length === 0) return { valid: true, message: "Aucune plante à valider" }
    const requiredFields = ['id_plante', 'nom', 'point_de_vie', 'attaque_par_seconde', 'degat_attaque', 'cout', 'soleil_par_seconde', 'effet', 'chemin_image']
    const errors: string[] = []
    for (let i = 0; i < items.length; i++) {
      const item = items[i]
      if (typeof item !== 'object' || item === null) {
        errors.push(`Plante #${i}: attendu un objet, reçu ${typeof item}`)
        continue
      }
      const record = item as Record<string, unknown>
      for (const field of requiredFields) {
        if (!(field in record)) { errors.push(`Plante #${i}: champ '${field}' manquant`); continue }
        const value = record[field]
        if (field === 'id_plante' || field === 'point_de_vie' || field === 'degat_attaque') {
          if (typeof value !== 'number') errors.push(`Plante #${i}: champ '${field}' devrait être un nombre, reçu ${typeof value}`)
        } else if (field === 'attaque_par_seconde' || field === 'cout' || field === 'soleil_par_seconde') {
          if (typeof value !== 'number' && (typeof value !== 'string' || isNaN(Number(value))))
            errors.push(`Plante #${i}: champ '${field}' devrait être un nombre ou une chaîne convertible en nombre, reçu ${typeof value}`)
        } else if (field === 'nom' || field === 'effet' || field === 'chemin_image') {
          if (typeof value !== 'string') errors.push(`Plante #${i}: champ '${field}' devrait être une chaîne, reçu ${typeof value}`)
        }
      }
    }
    return { valid: errors.length === 0, message: errors.length === 0 ? "Toutes les plantes sont valides" : `${errors.length} erreur(s) de validation`, details: errors }
  }

  const validateZombieStructure = (data: unknown): ValidationResult => {
    const parsedData = ensureObject(data)
    if (!Array.isArray(parsedData) && (typeof parsedData !== 'object' || parsedData === null)) {
      return { valid: false, message: `Données invalides: attendu un objet ou un tableau, reçu ${typeof parsedData}` }
    }
    const items: unknown[] = Array.isArray(parsedData) ? parsedData : [parsedData]
    if (items.length === 0) return { valid: true, message: "Aucun zombie à valider" }
    const requiredFields = ['id_zombie', 'nom', 'point_de_vie', 'attaque_par_seconde', 'degat_attaque', 'vitesse_de_deplacement', 'chemin_image']
    const errors: string[] = []
    for (let i = 0; i < items.length; i++) {
      const item = items[i]
      if (typeof item !== 'object' || item === null) {
        errors.push(`Zombie #${i}: attendu un objet, reçu ${typeof item}`)
        continue
      }
      const record = item as Record<string, unknown>
      for (const field of requiredFields) {
        if (!(field in record)) { errors.push(`Zombie #${i}: champ '${field}' manquant`); continue }
        const value = record[field]
        if (field === 'id_zombie' || field === 'point_de_vie' || field === 'degat_attaque') {
          if (typeof value !== 'number') errors.push(`Zombie #${i}: champ '${field}' devrait être un nombre, reçu ${typeof value}`)
        } else if (field === 'attaque_par_seconde' || field === 'vitesse_de_deplacement') {
          if (typeof value !== 'number' && (typeof value !== 'string' || isNaN(Number(value))))
            errors.push(`Zombie #${i}: champ '${field}' devrait être un nombre ou une chaîne convertible en nombre, reçu ${typeof value}`)
        } else if (field === 'nom' || field === 'chemin_image') {
          if (typeof value !== 'string') errors.push(`Zombie #${i}: champ '${field}' devrait être une chaîne, reçu ${typeof value}`)
        }
      }
      if ('id_map' in record && record['id_map'] !== null && record['id_map'] !== undefined) {
        const idMap = record['id_map']
        if (typeof idMap !== 'number') errors.push(`Zombie #${i}: champ 'id_map' devrait être un nombre, reçu ${typeof idMap}`)
      }
    }
    return { valid: errors.length === 0, message: errors.length === 0 ? "Tous les zombies sont valides" : `${errors.length} erreur(s) de validation`, details: errors }
  }

  const validateMapStructure = (data: unknown): ValidationResult => {
    const parsedData = ensureObject(data)
    if (!Array.isArray(parsedData) && (typeof parsedData !== 'object' || parsedData === null)) {
      return { valid: false, message: `Données invalides: attendu un objet ou un tableau, reçu ${typeof parsedData}` }
    }
    const items: unknown[] = Array.isArray(parsedData) ? parsedData : [parsedData]
    if (items.length === 0) return { valid: true, message: "Aucune map à valider" }
    const requiredFields = ['id_map', 'ligne', 'colonne', 'chemin_image']
    const errors: string[] = []
    for (let i = 0; i < items.length; i++) {
      const item = items[i]
      if (typeof item !== 'object' || item === null) {
        errors.push(`Map #${i}: attendu un objet, reçu ${typeof item}`)
        continue
      }
      const record = item as Record<string, unknown>
      for (const field of requiredFields) {
        if (!(field in record)) { errors.push(`Map #${i}: champ '${field}' manquant`); continue }
        const value = record[field]
        if (field === 'id_map' || field === 'ligne' || field === 'colonne') {
          if (typeof value !== 'number') errors.push(`Map #${i}: champ '${field}' devrait être un nombre, reçu ${typeof value}`)
        } else if (field === 'chemin_image') {
          if (typeof value !== 'string') errors.push(`Map #${i}: champ '${field}' devrait être une chaîne, reçu ${typeof value}`)
        }
      }
    }
    return { valid: errors.length === 0, message: errors.length === 0 ? "Toutes les maps sont valides" : `${errors.length} erreur(s) de validation`, details: errors }
  }

  const testEndpoint = async (endpoint: Endpoint): Promise<void> => {
    try {
      let response: unknown
      let validationResult: ValidationResult = { valid: true, message: "Opération réussie" }
      switch (endpoint.method) {
        case "GET":
          if (endpoint.path === "/plantes") {
            response = await getPlants()
            setResponses(prev => ({ ...prev, plantes: response }))
          }
          if (endpoint.path === "/zombies") {
            response = await getZombies()
            setResponses(prev => ({ ...prev, zombies: response }))
          }
          if (endpoint.path === "/maps") {
            response = await getMaps()
            setResponses(prev => ({ ...prev, maps: response }))
          }
          if (endpoint.path === "/plantes/validation" && responses.plantes) {
            validationResult = validatePlantStructure(responses.plantes)
          }
          if (endpoint.path === "/zombies/validation" && responses.zombies) {
            validationResult = validateZombieStructure(responses.zombies)
          }
          if (endpoint.path === "/maps/validation" && responses.maps) {
            validationResult = validateMapStructure(responses.maps)
          }
          break
        case "POST":
          if (endpoint.path === "/plantes") {
            response = await createPlant({ nom: "Nouvelle plante", point_de_vie: 100, attaque_par_seconde: 1.5, degat_attaque: 20, cout: 50, soleil_par_seconde: 5.0, effet: "normal", chemin_image: "/images/plantes/default.png" })
            validationResult = { valid: true, message: "Plante créée avec succès" }
          }
          if (endpoint.path === "/zombies") {
            response = await createZombie({ nom: "Nouveau zombie", point_de_vie: 150, attaque_par_seconde: 1.0, degat_attaque: 15, vitesse_de_deplacement: 1.5, chemin_image: "/images/zombies/default.png", id_map: 1 })
            validationResult = { valid: true, message: "Zombie créé avec succès" }
          }
          if (endpoint.path === "/maps") {
            response = await createMap({ ligne: 5, colonne: 9, chemin_image: "/images/maps/default.png" })
            validationResult = { valid: true, message: "Map créée avec succès" }
          }
          break
        case "PUT":
          if (endpoint.path === "/plantes/1") {
            response = await updatePlant({ id_plante: 1, data: { nom: "Plante modifiée", attaque_par_seconde: 2.0, degat_attaque: 25, cout: 75 } })
            validationResult = { valid: true, message: "Plante modifiée avec succès" }
          }
          if (endpoint.path === "/zombies/1") {
            response = await updateZombie({ id_zombie: 1, data: { nom: "Zombie modifié", degat_attaque: 18, point_de_vie: 180, id_map: 1 } })
            validationResult = { valid: true, message: "Zombie modifié avec succès" }
          }
          if (endpoint.path === "/maps/1") {
            response = await updateMap({ id_map: 1, data: { ligne: 6, colonne: 10 } })
            validationResult = { valid: true, message: "Map modifiée avec succès" }
          }
          break
        case "DELETE":
          if (endpoint.path === "/plantes/1") {
            await deletePlant(1)
            validationResult = { valid: true, message: "Plante supprimée avec succès" }
          }
          if (endpoint.path === "/zombies/1") {
            await deleteZombie(1)
            validationResult = { valid: true, message: "Zombie supprimé avec succès" }
          }
          if (endpoint.path === "/maps/1") {
            await deleteMap(1)
            validationResult = { valid: true, message: "Map supprimée avec succès" }
          }
          break
        default:
          throw new Error(`Unsupported method: ${endpoint.method}`)
      }
      setEndpoints(prev => prev.map(e => e.id === endpoint.id ? { ...e, status: 'success', validationResult } : e))
    } catch (error) {
      setEndpoints(prev =>
        prev.map(e =>
          e.id === endpoint.id
            ? { ...e, status: 'failed', validationResult: { valid: false, message: "Erreur lors de l'exécution de l'opération" } }
            : e
        )
      )
    }
  }

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold">API Endpoints</h1>
      <Suspense fallback={<div>Loading...</div>}>
        <div className="grid gap-4">
          {endpoints.map(endpoint => (
            <EndpointCard key={endpoint.id} endpoint={endpoint} onTest={testEndpoint} />
          ))}
        </div>
      </Suspense>
    </div>
  )
}
