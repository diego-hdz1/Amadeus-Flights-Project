import { Card, Col, Row, Modal} from "antd";
import axios from "axios";
import { useEffect, useState } from "react";

interface ShowResultsProps{
    url:string;
}

const ShowResults: React.FC<ShowResultsProps> = ({
    url
}) => {
    const [data, setData] = useState<any[]>([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [currentCard, setCurrentCard] = useState<any| null>(null);

    const showModal = (segments:any) => {
        console.log(segments);
        setCurrentCard(segments);
        setIsModalOpen(true);
      };

    const handleCancel = () => {
        setIsModalOpen(false);
        setCurrentCard(null);
      };

    const fetchData = () => {
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
                <Card key={flight.flightId} onClick={()=>showModal(flight.segments)} style={{width:"100%"}}>   
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
            <Modal title="Detailed flight information" open={isModalOpen} onCancel={handleCancel}>
            {currentCard && (
                <div style={{overflowY:"auto"}}>
                    {currentCard.map((segment:any)=>(
                        <Card key={segment.flightId}>   
                        <p>{segment.initialDepartureDate}</p>
                        <p>{segment.finalArrivalDate}</p>
                        <p>{segment.initialCityName}</p>
                        <p>{segment.arriveCityName}</p>
                        <p>{segment.initialAirlineCode}</p>
                        <p>{segment.arriveAirlineCode}</p>
                        <p>{segment.carrierCode}</p>
                        <p>{segment.aircraft}</p>
                        <p>{segment.totalDuration}</p>
                        </Card>
                    ))}
                </div>
            )}
            </Modal>   
        </div>
    );
};

export default ShowResults;