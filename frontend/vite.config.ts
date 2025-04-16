import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'


// https://vite.dev/config/
export default defineConfig(({ mode }) => {
  return {
    plugins: [vue()],
    build: {
      rollupOptions: {
        input: [
          resolve(__dirname, './index.html'),
          resolve(__dirname, './pages/movie.html'),
          resolve(__dirname, './pages/newOrder.html'),
          resolve(__dirname, './pages/space.html'),
        ],
      },
    },
    server: {
      proxy: mode === 'development' ? {
        '/api': {
          target: 'http://localhost:8080',
          changeOrigin: true,
        }
      } : undefined,
    },
  };
})
