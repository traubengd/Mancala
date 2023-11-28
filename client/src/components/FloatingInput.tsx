import classNames from "classnames";
import { ChangeEventHandler } from "react";

type Props = {
    id: string;
    label: string;
    value: string
    onChange: ChangeEventHandler<HTMLInputElement>;
    hasError: boolean
};

export const FloatingInput = (props: Props) => {
    const { id, label, value, onChange, hasError } = props;

    return <div>
        <div className="relative">
            <input type="text" id={id} className={classNames(
                "block",
                "px-2.5",
                "pb-2.5",
                "pt-4",
                "w-full",
                "text-sm",
                "bg-transparent",
                "rounded-lg",
                "border-1",
                "appearance-none",
                "focus:outline-none",
                "focus:ring-0",
                "text-white",
                "peer",
                { "border-gray-600 focus:border-neutral-300": !hasError },
                { "border-red-600 focus:border-red-500": hasError },
            )} placeholder=" " onChange={onChange} value={value} />

            <label htmlFor={id} className={classNames(
                "absolute",
                "text-sm",
                "duration-300",
                "transform -translate-y-4 scale-75 z-10 origin-[0]",
                "bg-gray-900",
                "peer-focus:px-2",
                "px-2",
                "peer-placeholder-shown:scale-100",
                "peer-placeholder-shown:top-1/2",
                "peer-focus:top-2",
                "peer-focus:scale-75",
                "peer-focus:-translate-y-4",
                "top-2 left-1",
                { "text-gray-400 peer-focus:text-neutral-400 peer-placeholder-shown:-translate-y-1/2": !hasError },
                { "text-red-500 peer-focus:text-red-400 peer-placeholder-shown:-translate-y-1/2": hasError })}>
                {label}
            </label>
        </div>
    </div>
};
