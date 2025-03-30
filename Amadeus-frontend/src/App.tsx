import './App.css'
import { BrowserRouter, Routes, Route, useLocation } from "react-router-dom";
import ShowResults from './components/showResults'
import Search from './components/search'
import Header from './components/header'
import Loading from './components/loading'
import Sort from './components/sort'

import { useState } from 'react';

function App() {
  const [url, setUrl] = useState<string>("");
  const [data, setData] = useState<any[]>([]);

  return (
    <div>
      <BrowserRouter>
      < Header/>
        <Routes >
          <Route path='/' element={ <div>  <Search handleSetUrl={setUrl} /> </div> }></Route>
          <Route path='/loading' element={<Loading/>}></Route>
          <Route path='/showResult' element={ <div> <Sort data={data} setData={setData}/> <ShowResults url={url} data={data} setData={setData}/> </div> }></Route>
        </Routes>
      </BrowserRouter>
    </div>
  )
}

export default App