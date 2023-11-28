import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import { RouterProvider } from 'react-router-dom'
import { router } from './router.tsx'
import { MancalaGameProvider } from './contexts/MancalaGameContext.tsx'

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <MancalaGameProvider>
      <RouterProvider router={router} />
    </MancalaGameProvider>
  </React.StrictMode>,
)
