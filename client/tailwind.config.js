/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,jsx,ts,tsx}",],
  theme: {
    extend: {
      backgroundImage: {
        mancala: "url(/ai_generated_mancala_game.png)",
        pit: "url(/lightwood.jpg)",
        playboard: "url(/darkwood.jpg)",
        tabletop: "url(/tabletop.jpg)"
      },
      colors: {
        sogyo: "#007936"
      },
      boxShadow: {
        innerPit: 'inset 0 7px 7px 0 rgb(0 0 0 / 0.5)'
      }
    },
  },
  plugins: [
    require('@tailwindcss/forms'),
    require('@tailwindcss/typography')
  ],
}
