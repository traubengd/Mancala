import classNames from "classnames";
import { To, useNavigate } from "react-router-dom";

type Props = {
    to: To;
    text: string;
    isActive: boolean;
};
export const NavButton = (props: Props) => {
    const { to, text, isActive } = props;
    const navigate = useNavigate();

    return (<button className={classNames(
        "py-1 px-3 rounded-full text-xl border-4",
        "hover:text-neutral-800 ", "hover:bg-neutral-50", "hover:border-neutral-50 duration-300",
        { "text-neutral-300 bg-sogyo border-neutral-300": !isActive },
        { "text-neutral-800 bg-neutral-50 border-neutral-50": isActive })}
        onClick={() => navigate(to)}>
        {text}
    </button>)
}
