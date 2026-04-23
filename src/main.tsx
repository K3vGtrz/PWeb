import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.tsx'
import 'primereact/resources/themes/soho-dark/theme.css'
import "primeicons/primeicons.css"
import "primeflex/primeflex.css"

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>,
)
