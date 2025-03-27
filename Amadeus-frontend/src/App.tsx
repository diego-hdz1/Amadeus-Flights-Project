import './App.css'
import { BrowserRouter, Routes, Route, useLocation } from "react-router-dom";
import ShowResults from './components/showResults'
import Search from './components/search'
import { useState } from 'react';

function App() {
  const [url, setUrl] = useState<string>("");

  return (
    <>
      <BrowserRouter>
        <Routes >
          <Route path='/' element={ <Search handleSetUrl={setUrl} /> }></Route>
          <Route path='/prueba' element={ <ShowResults url={url}/> }></Route>
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
