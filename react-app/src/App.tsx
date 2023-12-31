import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import axios  from 'axios'

function App() {
  const [count, setCount] = useState(0)
  useEffect(() => {
    const get = async () => {
      const api = axios.create({baseURL: import.meta.env.VITE_API_URL})
      const response = await api.get("/env")
      console.log(response.data)
    }
    get()
  })

  return (
    <>
      <header id='appHeader'>
        <div className='content'>
          <h5>Logo</h5>
        <nav>
          <ul>
            <li>BLOG</li>
            <li>ABOUT</li>
            <li>DOCS</li>
          </ul>
        </nav>
        <button className='dashboardBtn'>Dashboard</button>
        </div>
      </header>
      <div>
        <a href="https://vitejs.dev" title='Vite link' target="_blank" >
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        <button type='button' className='btn' onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.tsx</code> and save to test HMR
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  )
}

export default App
