import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  build: {
    rollupOptions: {
      input: [
        resolve(__dirname, './index.html'),
        resolve(__dirname, './pages/movie.html')
      ]
    }
  }
})
