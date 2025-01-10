/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: "#0a66c2",
        primaryColorDark: "#004182",
        secondary: '#F5F5F5',
        darkGray: '#00000099'
      },
      boxShadow: {
        box: '0 4px 12px rgba(0,0,0,0.15)'
      },
      keyframes: {
        slide: {
          '50%': { left: '-4rem' }
        },
        spin: {
          from: { transform: 'rotate(0deg)' },
          to: { transform: 'rotate(360deg)' }
        }
      },
      animation: {
        slide: 'slide 1.5s ease infinite',
        spin: 'spin 1s linear infinite'
      }
    },
  },
  plugins: [],
}
