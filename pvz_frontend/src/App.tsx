import { BrowserRouter, Routes, Route } from "react-router-dom";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import Layout from "./components/Layout";
import EndpointsPage from "./pages/Endpoints/EndpointsPage.tsx";
import EditorPage from "./pages/Editor/EditorPage.tsx";
import GamePage from "./pages/Game/GamePage.tsx";

const queryClient = new QueryClient();

export const App = () => {
    return (
        <QueryClientProvider client={queryClient}>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Layout />}>
                        <Route index element={<EndpointsPage />} />
                        <Route path="endpoints" element={<EndpointsPage />} />
                        <Route path="editor" element={<EditorPage />} />
                        <Route path="game" element={<GamePage />} />
                    </Route>
                </Routes>
            </BrowserRouter>
        </QueryClientProvider>
    );
};
