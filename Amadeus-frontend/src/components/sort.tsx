import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {PORT, SERVER} from '../constants';


interface ShowSortProps{
    data: any[];
    setData: (data:any) => void;
}

const Sort: React.FC<ShowSortProps> = ({
    data,
    setData
}) => {

    const [filterPrice, setFilterPrice] = useState(1);
    const [filterDate, setFilterDate] = useState(1);
    const navigator = useNavigate();

    const handleFilter = (e: React.MouseEvent<HTMLButtonElement>) =>{
        e.preventDefault();
        axios.get(`http://${SERVER}:${PORT}/sort?orderPrice=${filterPrice}&orderDate=${filterDate}`).then((response)=>{
            setData([...response.data]);
        }).catch(error =>{console.log(error);})
    }

    return (
        <div>
            {data &&(
            <div> 
            <button onClick={()=>navigator("/")} className="return-button">Return to search</button>
            <div> 
            <form className="add-form">
                <h3>Choose your options to sort</h3>
                <select value={filterPrice} onChange={(e)=>setFilterPrice(Number(e.target.value))}>
                    <option value={1}>-</option>
                    <option value={2}>Descending price</option>
                    <option value={3}>Ascending price</option>
                </select>
                <select value={filterDate} onChange={(e)=>setFilterDate(Number(e.target.value))}>
                    <option value={1}>-</option>
                    <option value={3}>Descending date</option>
                    <option value={2}>Ascending date</option>
                </select>
                <button type="button" onClick={handleFilter}>Filter</button>
            </form>
            </div> 
            </div>)}
        </div>
        
    );
};

export default Sort;