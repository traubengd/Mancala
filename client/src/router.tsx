import { createBrowserRouter } from "react-router-dom";
import { RootLayout } from "./layouts/RootLayout";
import { ErrorPage } from "./pages/ErrorPage";
import { About } from "./pages/About";
import { Mancala } from "./pages/Mancala";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <RootLayout />,
        errorElement: <ErrorPage />,
        children: [
            {
                index: true,
                element: <Mancala />
            },
            {
                path: "about",
                element: <About />
            }
        ]
    }
]);
