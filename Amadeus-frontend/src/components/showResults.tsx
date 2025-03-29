import { Card, Col, Row, Modal} from "antd";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

interface ShowResultsProps{
    url:string;
    data: any[];
    setData: React.Dispatch<React.SetStateAction<any[]>>;
}

const ShowResults: React.FC<ShowResultsProps> = ({
    url,
    data,
    setData
}) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [currentCard, setCurrentCard] = useState<any| null>(null);
    const [filterPrice, setFilterPrice] = useState(1);
    const [filterDate, setFilterDate] = useState(1);
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
        if(location.pathname === "/showResult"){
        fetchData();}
    }, [location]);

    const handleFilter = async () =>{
        console.log(filterPrice);
        console.log(filterDate);
        //`http://localhost:8080/sort?orderPrice=${filterPrice}&orderDate=${filterDate}`
        await axios.get(`http://localhost:8080/sort?orderPrice=2&orderDate=1`).then((response)=>{
            setData(response.data);
            console.log(response.data);
            console.log(data);
            // navigator("/loading");
        }).catch(error =>{console.log(error);})
    }

    return (
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
                <button onClick={()=>handleFilter}>Filter</button>
            </form>
        </div>
            {Object.entries(
                data.reduce<Record<string, typeof data>>((acc, flight)=>{
                    if(!acc[flight.flightId]){
                        acc[flight.flightId] = [];
                    }
                    acc[flight.flightId].push(flight);
                    return acc;
                }, {})
            ).map(([flightId, flights])=>(
                <Card key={flightId} className="flight-group-card">
                {flights.map((flight:any)=>(
                        <Card
                            key={flight.initialDepartureDate + flight.finalArrivalDate}
                            onClick={()=>showModal(flight.segments)}
                            className="flight-card"
                        >
                        <p>Initial departure date: {flight.initialDepartureDate}</p>
                        <p>Final arrival date: {flight.finalArrivalDate}</p>
                        <p>{flight.segments[0].initialCityName} ({flight.segments[0].initialAirlineCode}) - {flight.segments[0].arriveCityName} ({flight.segments[0].arriveAirlineCode})</p>
                        <p>{flight.airlineName} ({flight.airlineCode})</p>
                        <p>{flight.totalTime}</p>
                        <p className="price">$ {flight.pricePerTraveler} {flight.currency} per traveler</p>
                        <p className="price">$ {flight.totalPrice} {flight.currency} total</p>
                        </Card>
                    ))}
                </Card>
            ))}

            <Modal title="Detailed flight information" open={isModalOpen} onCancel={handleCancel} className="modal">
            {currentCard && (
                <div className="modal-content">
                    <Card className="price-breakdown">
                        <h2>Price breakdown</h2>
                        <p>Base: {currentCard[0].flightDetails.base}</p>
                        <p>Fees: {currentCard[0].flightDetails.fees}</p>
                        <p>Per traveler</p>
                        <p className="price-total">Total: {currentCard[0].flightDetails.total}</p>
                    </Card>
                    {currentCard.map((segment:any, index:number)=>(
                        <Card key={segment.flightId} className="segment-card">   
                        <h2>Segment {index+1}</h2>
                        <p>{segment.initialDepartureDate} - {segment.finalArrivalDate}</p>
                        <p>{segment.initialCityName} ({segment.initialAirlineCode}) - {segment.arriveCityName} ({segment.arriveAirlineCode})</p>
                        <p>({segment.carrierCode}) {segment.aircraft}</p>
                        <p>{segment.totalDuration}</p>
                        <Card className="travel-details">
                            <h3>Details per travel</h3>
                            <p>Class: {segment.classNumber}</p>
                            <p>Cabin: {segment.cabin}</p>
                            <h4>Amenities:</h4>
                            <ul>
                                {segment.amenities && 
                                    segment.amenities.map((amenity: Record<string, boolean>, index:number) =>
                                    Object.entries(amenity)
                                    .map(([key, value]) => <li key={`${key}-${index}`} className={value ? "chargeable" : "not-chargeable"}>
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