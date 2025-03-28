import { useNavigate } from 'react-router-dom';
import PlantsVsZombiesGame from '../../jeu_pvz/PlantsVsZombiesGame';

export default function GamePage() {
  const navigate = useNavigate();

  const handleExitGame = () => {
    navigate('/editor');
  };

  return (
    <div className="w-full h-screen">
      <PlantsVsZombiesGame onExit={handleExitGame} />
    </div>
  );
}