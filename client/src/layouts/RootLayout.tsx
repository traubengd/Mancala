import { Outlet, useLocation } from "react-router-dom";
import logo from "../assets/logo.jpg";
import { NavButton } from "../components/NavButton";

export const RootLayout = () => {
    const { pathname } = useLocation();

    return <div className="flex flex-col h-screen ">
        <header className="bg-sogyo shadow-lg flex flex-row p-4">
            <img src={logo} />
            <nav className="pt-4 flex-1 flex flex-row justify-center gap-2">
                <NavButton to="/" text="Play" isActive={pathname === "/"} />
                <NavButton to="/about" text="About" isActive={pathname === "/about"} />
            </nav>
        </header>
        <main className="flex-1">
            <Outlet />
        </main>
    </div>
};
