import { Card, Col, Row, Modal} from "antd";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

interface ShowSortProps{
    data: any[];
    //setData: React.Dispatch<React.SetStateAction<any[]>>;
    setData: (data:any) => void;
}

const Sort: React.FC<ShowSortProps> = ({
    data,
    setData
}) => {

    const [filterPrice, setFilterPrice] = useState(1);
    const [filterDate, setFilterDate] = useState(1);
    const navigator = useNavigate();

    useEffect(()=>{
        console.log("Updated data", JSON.stringify(data,null,2));
    }, [data]);

    const handleFilter = (e: React.MouseEvent<HTMLButtonElement>) =>{
        e.preventDefault();
        axios.get(`http://localhost:8080/sort?orderPrice=${filterPrice}&orderDate=${filterDate}`).then((response)=>{
            //const newData = response.data.map((flight:any) => ({...flight}))
            
            // const newData = JSON.parse(JSON.stringify(response.data));
            // console.log("Data in JSON from soting", newData);
            //empyState();
            //newState(newData);
            //setData(newData);
            // console.log(response.data);
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
                <h3>Choose your options to filter</h3>
                <select value={filterPrice} onChange={(e)=>setFilterPrice(Number(e.target.value))}>
                    <option value={1}>-</option>
                    <option value={2}>Descending price</option>
                    <option value={3}>Ascending price</option>
                </select>
                <select value={filterDate} onChange={(e)=>setFilterDate(Number(e.target.value))}>
                    <option value={1}>-</option>
                    <option value={2}>Descending date</option>
                    <option value={3}>Ascending date</option>
                </select>
                <button type="button" onClick={handleFilter}>Filter</button>
            </form>
            </div> 
            </div>)}
        </div>
        
    );
};

export default Sort;