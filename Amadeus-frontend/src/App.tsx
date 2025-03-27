import './App.css'
import { BrowserRouter, Routes, Route, useLocation } from "react-router-dom";
import ShowResults from './components/showResults'
import Search from './components/search'

function App() {
  //const location = useLocation();

  return (
    <>
      <BrowserRouter>
        <Routes >
          <Route path='/' element={ <Search/> }></Route>
          <Route path='/prueba' element={ <ShowResults/> }></Route>
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
