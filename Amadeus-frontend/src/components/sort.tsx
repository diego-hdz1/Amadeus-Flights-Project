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

    const empyState = () =>{
        setData([]);
        console.log("Entro en el empty state");
    }

    const newState = (allData:any) =>{
        setData(allData);
        console.log("Entro en el set state");

    }

    const handleFilter = (e: React.MouseEvent<HTMLButtonElement>) =>{
        e.preventDefault();
        //`http://localhost:8080/sort?orderPrice=${filterPrice}&orderDate=${filterDate}`
        axios.get(`http://localhost:8080/sort?orderPrice=2&orderDate=1`).then((response)=>{
            //const newData = response.data.map((flight:any) => ({...flight}))
            const newData = JSON.parse(JSON.stringify(response.data));
            //empyState();
            //newState(newData);
            setData(newData);
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
                    <option value={1}>Ascending price</option>
                    <option value={2}>Descending price</option>
                </select>
                <select value={filterDate} onChange={(e)=>setFilterDate(Number(e.target.value))}>
                    <option value={0}>Ascending date</option>
                    <option value={1}>Descending date</option>
                </select>
                <button type="button" onClick={handleFilter}>Filter</button>
            </form>
            </div> 
            </div>)}
        </div>
        
    );
};

export default Sort;