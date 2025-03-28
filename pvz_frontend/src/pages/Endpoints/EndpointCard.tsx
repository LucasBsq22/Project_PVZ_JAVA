import {CheckCircle, AlertCircle, Play} from 'lucide-react'
export type ValidationResult = {valid: boolean; message: string; details?: any}
export type Endpoint = {
  id: string
  path: string
  method: 'GET' | 'POST' | 'PUT' | 'DELETE'
  description: string
  status: 'pending' | 'success' | 'failed'
  validationResult?: ValidationResult
}
type EndpointCardProps = {
  endpoint: Endpoint
  onTest: (endpoint: Endpoint) => void
}
export default function EndpointCard({endpoint, onTest}: EndpointCardProps) {
  return (
    <div className="bg-white p-4 rounded-lg shadow-md flex items-center justify-between">
      <div>
        <div className="flex items-center space-x-2">
          <span className={`px-2 py-1 rounded text-sm font-medium ${endpoint.method === "GET" ? "bg-blue-100 text-blue-800" : endpoint.method === "POST" ? "bg-green-100 text-green-800" : endpoint.method === "PUT" ? "bg-yellow-100 text-yellow-800" : "bg-red-100 text-red-800"}`}>
            {endpoint.method}
          </span>
          <span className="font-mono">{endpoint.path}</span>
        </div>
        <p className="text-gray-600 mt-1">{endpoint.description}</p>
        {endpoint.validationResult && (
          <div className={`mt-2 p-2 rounded text-sm ${endpoint.validationResult.valid ? "bg-green-50 text-green-800" : "bg-red-50 text-red-800"}`}>
            <p>{endpoint.validationResult.message}</p>
            {endpoint.validationResult.details && endpoint.validationResult.details.length > 0 && (
              <ul className="mt-1 list-disc list-inside">
                {endpoint.validationResult.details.slice(0, 3).map((error: string, idx: number) => (
                  <li key={idx}>{error}</li>
                ))}
                {endpoint.validationResult.details.length > 3 && (
                  <li>... et {endpoint.validationResult.details.length - 3} autre(s) erreur(s)</li>
                )}
              </ul>
            )}
          </div>
        )}
      </div>
      <div className="flex items-center space-x-4">
        {endpoint.status === "success" && <CheckCircle className="text-green-500 h-5 w-5" />}
        {endpoint.status === "failed" && <AlertCircle className="text-red-500 h-5 w-5" />}
        <button onClick={() => onTest(endpoint)} className="flex items-center space-x-1 px-3 py-1 bg-green-600 text-white rounded-md hover:bg-green-700">
          <Play className="h-4 w-4" />
          <span>Test</span>
        </button>
      </div>
    </div>
  )
}
