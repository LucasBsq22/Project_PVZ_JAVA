import { Link, Outlet } from 'react-router-dom';
import { Sprout, Skull, Map, Code } from 'lucide-react';

export default function Layout() {
  return (
    <div className="min-h-screen bg-gray-100">
      <nav className="bg-green-800 text-white shadow-lg">
        <div className="max-w-7xl mx-auto px-4">
          <div className="flex items-center justify-between h-16">
            <div className="flex items-center space-x-8">
              <Link to="/" className="flex items-center space-x-2 font-bold text-xl">
                <Sprout className="h-6 w-6" />
                <span>PvZ Endpoint Testeur</span>
              </Link>
              <div className="flex space-x-4">
                <Link to="/endpoints" className="flex items-center space-x-1 hover:text-green-200">
                  <Code className="h-5 w-5" />
                  <span>Endpoints</span>
                </Link>
                <Link to="/editor" className="flex items-center space-x-1 hover:text-green-200">
                  <Skull className="h-5 w-5" />
                  <span>Editeur</span>
                </Link>
                <Link to="/game" className="flex items-center space-x-1 hover:text-green-200">
                  <Map className="h-5 w-5" />
                  <span>Game</span>
                </Link>
              </div>
            </div>
          </div>
        </div>
      </nav>
      <main className="max-w-7xl mx-auto px-4 py-6">
        <Outlet />
      </main>
    </div>
  );
}