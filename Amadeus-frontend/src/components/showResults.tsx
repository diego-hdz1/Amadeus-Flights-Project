import { Card, Col, Row } from "antd";
import axios from "axios";
import { useEffect, useState } from "react";


const ShowResults: React.FC = () => {

    const [data, setData] = useState<any[]>([]);

    const fetchData = () => {
        const url = `http://localhost:8080/flights?departureAirportCode=SYD&arrivalAirportCode=BKK&departureDate=2025-05-02&returnDate=2025-05-05&adults=1&nonStop=false&currencyCode=EUR`;
        axios.get(url).then((response)=>{
          setData(response.data);
          console.log(data);
        }).catch(error =>{console.log(error);})
        }    

        useEffect(()=>{
            if(location.pathname === "/prueba"){
            fetchData();}
          }, [location]);

    return (

        <div>
            {data.map((flight)=>(
                <Card key={flight.flightId} onClick={()=> console.log(flight.flightId)}>   
                <p>{flight.flightId}</p>
                <p>{flight.initialDepartureDate}</p>
                <p>{flight.finalArrivalDate}</p>
                <p>{flight.airlineName} ({flight.airlineCode})</p>
                <p>{flight.totalTime}</p>
                <p>{flight.totalPrice}</p>
                <p>{flight.pricePerTraveler}</p>
                <p>{flight.currency}</p>
                </Card>
            ))}
        </div>
    );
};

export default ShowResults;