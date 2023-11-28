import { isRouteErrorResponse, useRouteError } from "react-router-dom";

const convertError = (error: unknown) => {
    if (isRouteErrorResponse(error)) {
        return `${error.status} ${error.statusText}`;
    }

    if (error instanceof Error) {
        return error.message;
    }

    if (typeof error === 'string') {
        return error;
    }

    console.error(error);
    return "Unknown error";
}

export const ErrorPage = () => {
    const error = useRouteError();
    const errorMessage = convertError(error);

    return (
        <div id="error-page">
            <h1>Oops!</h1>
            <p>Sorry, an unexpected error has occurred.</p>
            <p>
                <i>{errorMessage}</i>
            </p>
        </div>
    );
}
