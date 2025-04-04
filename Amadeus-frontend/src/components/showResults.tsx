import { Card, Modal, Spin} from "antd";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

interface ShowResultsProps{
    url:string;
    data: any[];
    setData: (data:any) => void;
}

const ShowResults: React.FC<ShowResultsProps> = ({
    url,
    data,
    setData
}) => {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [currentCard, setCurrentCard] = useState<any| null>(null);
    const [currency, setCurrency] = useState("");
    const [loading, setLoading] = useState(true);
    const [currentPriceSegegment, setCurrentPriceSegment] = useState(0);
    const navigator = useNavigate();

    const showModal = (segments:any, totalPrice:any) => {
        setCurrentCard(segments);
        setCurrentPriceSegment(totalPrice);
        setIsModalOpen(true);
      };

    const handleCancel = () => {
        setIsModalOpen(false);
        setCurrentCard(null);
      };

    const fetchData = () => {
        axios.get(url).then((response)=>{
        if(response.data.length===0){
            console.log("Error");
            navigator("/error");
          }
          setLoading(false);
          setCurrency(response.data[0].currency);
          setData(response.data);
          console.log(data);

        }).catch(error =>{console.log(error);})
    }    

    useEffect(()=>{
        if(location.pathname === "/showResult"){
        fetchData();
    }
    }, [location]);

    return (
        
        <div>
            {loading ? (
                <div style={{textAlign:'center', marginTop:'80px', width:"90rem"}}>
                    <Spin size="large"></Spin>
                    <h2>Loading results...</h2>
                </div>
            ) : (

            <div key={JSON.stringify(data)} style={{width:"90rem"}}>
            
            {data &&(
            <div>
                {Array.from(
                    data.reduce<Map<String, typeof data>>((acc,flight) =>{
                        if(!acc.has(flight.flightId)){
                            acc.set(flight.flightId, []);
                        }
                        acc.get(flight.flightId)!.push(flight);
                        return acc;
                    }, new Map())
                ).map(([flightId, flights], index) =>(
                    <div key={`${flightId}-${index}`}>
                        <Card className="flight-group-card">
                            <h2>Flight option {index+1}</h2>
                            {flights.map((flight:any, index:any)=>(
                                 <Card
                                 key={`${flight.flightId}+${flight.finalArrivalDate}+${Math.random()}`}
                                 onClick={()=>showModal(flight.segments, flight.pricePerTraveler)}
                                 className="flight-card"
                                >
                                {index == 0 ? <h4>Departure flight</h4> : <h4>Return flight</h4>}
                                <p>Initial departure date: {flight.initialDepartureDate}&emsp;&emsp;&emsp;&emsp;&emsp;Final arrival date: {flight.finalArrivalDate}</p> 
                                {/* <p>Final arrival date: {flight.finalArrivalDate}</p> */}
                                <p>{flight.segments[0].initialCityName} ({flight.segments[0].initialAirlineCode}) - {flight.segments[(flight.segments.length)-1].arriveCityName} ({flight.segments[(flight.segments.length)-1].arriveAirlineCode})</p>
                                <p>{flight.airlineName} ({flight.airlineCode})</p>
                                <p>Total duration: {flight.totalTime} H</p>    
                                </Card>
                            ))}
                        <Card className="flight-card-results">
                            <h4>Price per flight:</h4>
                            <p className="price">$ {flights[0].pricePerTraveler} {flights[0].currency} per traveler</p>
                            <p className="price">$ {flights[0].totalPrice} {flights[0].currency} total</p>       
                        </Card>
                        </Card>
                    </div>
                ))}
            </div>
            )}

            <Modal title="Detailed flight information" open={isModalOpen} onCancel={handleCancel} className="modal">
            {currentCard && (
                <div className="modal-content">
                    <Card className="price-breakdown">
                        <h2>Price breakdown</h2>
                        <p>Base: ${currentCard[0].flightDetails.base} {currency}</p>
                        <p>Fees: ${currentCard[0].flightDetails.fees} {currency}</p>
                        <p>Per traveler: $ {currentPriceSegegment} {currency}</p>
                        <p className="price-total">Total: ${currentCard[0].flightDetails.total} {currency}</p>
                    </Card>
                    {currentCard.map((segment:any, index:number)=>(
                        <Card key={segment.flightId} className="segment-card">   
                        <h2>Segment {index+1}</h2>
                        <p>{segment.initialDepartureDate} - {segment.finalArrivalDate}</p>
                        <p>{segment.initialCityName} ({segment.initialAirlineCode}) - {segment.arriveCityName} ({segment.arriveAirlineCode})</p>
                        <p>({segment.carrierCode}) {segment.aircraft}</p>
                        <p>Total duration: {segment.totalDuration}</p>
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
        )}
        </div>

    );
};

export default ShowResults;