import { Card, Col, Row, Modal} from "antd";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

interface ShowResultsProps{
    url:string;
}

const ShowResults: React.FC<ShowResultsProps> = ({
    url
}) => {
    const [data, setData] = useState<any[]>([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [currentCard, setCurrentCard] = useState<any| null>(null);
    const navigator = useNavigate();

    const showModal = (segments:any) => {
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
            <button onClick={()=>navigator("/")}>Return to search</button>
            <p>SORTING !!!</p>
            {Object.entries(
                data.reduce<Record<string, typeof data>>((acc, flight)=>{
                    if(!acc[flight.flightId]){
                        acc[flight.flightId] = [];
                    }
                    acc[flight.flightId].push(flight);
                    return acc;
                }, {})
            ).map(([flightId, flights])=>(
                <Card key={flightId} style={{width:"100%"}}>
                {flights.map((flight:any)=>(
                        <Card
                            key={flight.initialDepartureDate + flight.finalArrivalDate}
                            onClick={()=>showModal(flight.segments)}
                            style={{width:"100%"}}
                        >
                        <p>Initial departure date: {flight.initialDepartureDate}</p>
                        <p>Final arrival date: {flight.finalArrivalDate}</p>
                        <p>{flight.segments[0].initialCityName} ({flight.segments[0].initialAirlineCode}) - {flight.segments[0].arriveCityName} ({flight.segments[0].arriveAirlineCode})</p>
                        <p>{flight.airlineName} ({flight.airlineCode})</p>
                        <p>{flight.totalTime}</p>
                        <p>$ {flight.totalPrice} {flight.currency} total</p>
                        <p>$ {flight.pricePerTraveler} {flight.currency} per traveler</p>
                        </Card>
                    ))}
                </Card>
            ))}

            <Modal title="Detailed flight information" open={isModalOpen} onCancel={handleCancel}>
            {currentCard && (
                <div style={{overflowY:"auto"}}>
                    <Card>
                        <h2>Price breakdown</h2>
                        <p>Base: {currentCard[0].flightDetails.base}</p>
                        <p>Fees: {currentCard[0].flightDetails.fees}</p>
                        <p>Total: {currentCard[0].flightDetails.total}</p>
                        <p>Per traveler</p>
                    </Card>
                    {currentCard.map((segment:any, index:number)=>(
                        <Card key={segment.flightId}>   
                        <h2>Segment {index+1}</h2>
                        <p>{segment.initialDepartureDate} - {segment.finalArrivalDate}</p>
                        <p>{segment.initialCityName} ({segment.initialAirlineCode}) - {segment.arriveCityName} ({segment.arriveAirlineCode})</p>
                        <p>({segment.carrierCode}) {segment.aircraft}</p>
                        <p>{segment.totalDuration}</p>
                        <Card>
                            <h3>Details per travel</h3>
                            <p>Class: {segment.classNumber}</p>
                            <p>Cabin: {segment.cabin}</p>
                            <h4>Amenities:</h4>
                            <ul>
                                {segment.amenities && 
                                    segment.amenities.map((amenity: Record<string, boolean>, index:number) =>
                                    Object.entries(amenity)
                                    .map(([key, value]) => <li key={`${key}-${index}`}>
                                        {key} ({value == true ? `Chargeable` : `Not chargeable`})
                                        </li>)
                                )}
                            </ul>
                        </Card>
                        </Card>
                    ))}
                </div>
            )}
            </Modal>   
        </div>
    );
};

export default ShowResults;