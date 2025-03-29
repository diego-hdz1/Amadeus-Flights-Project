import './App.css'
import { BrowserRouter, Routes, Route, useLocation } from "react-router-dom";
import ShowResults from './components/showResults'
import Search from './components/search'
import Header from './components/header'
import { useState } from 'react';

function App() {
  const [url, setUrl] = useState<string>("");
  const [data, setData] = useState<any[]>([]);

  return (
    <>
      <BrowserRouter>
        <Routes >
          <Route path='/' element={ <div> <Header/> <Search handleSetUrl={setUrl} /> </div> }></Route>
          {/* <Route path='loading' element={<div>Hola</div>}></Route> */}
          <Route path='/showResult' element={ <div> <Header/> <ShowResults url={url} data={data} setData={setData}/> </div> }></Route>
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
