# Mancala
Fullstack web-application for a two-player implementation of the ancient game of Mancala. Created over the course of multiple modules within the software engineering trainee programme at Sogyo.

## Repository structure

In this repository, we find the following files and directories.

```
api
   *Java subproject for running the webserver*
client
   *Typescript-based front-end*
domain
   *Java subproject for the Mancala domain*
persistence
   *Java subproject for interacting with the database*
.gitignore
README.md
settings.gradle
...
```

## The back-end

The back-end is a Java API, which is served using a simple [Jetty server](https://en.wikipedia.org/wiki/Jetty_(web_server)). The whole project can be run using the gradle wrapper:

```bash
# Build the project
./gradlew build
# Run all unit tests
./gradlew test
# Run the Jetty server
./gradlew run
```

If you run the server, it listens at `http://localhost:8080`.

## The front-end

The front-end (found in the `client` folder) is a [React](https://react.dev/) project, which is served using [Vite](https://vitejs.dev/). 

Styling is done using [tailwindcss](https://tailwindcss.com/). Before running it, dependencies must be installed:

```bash
cd client
npm install
```

After that, the scripts from `package.json` can do the following:

```bash
# Start the front-end server
npm run dev
# Check code for common mistakes and style conventions
npm run lint
# Create a production-worthy build of the client
npm run build
```

The front-end server listens at `http://localhost:5173`. 
