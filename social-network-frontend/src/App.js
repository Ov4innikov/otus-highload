import logo from './logo.svg'
import './App.css'
import {Routes, Route, Link} from 'react-router-dom'
import Header from "./Header"
import Home from "./Home"
import About from "./About"
import 'bootstrap/dist/css/bootstrap.min.css';
import $ from 'jquery';
import Popper from 'popper.js';
import 'bootstrap/dist/js/bootstrap.bundle.min';

function App() {
    return (
        <div>
            <Header/>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/about" element={<About/>}/>
            </Routes>
        </div>
    );
}

export default App;
