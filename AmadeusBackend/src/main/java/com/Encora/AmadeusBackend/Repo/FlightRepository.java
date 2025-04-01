package com.Encora.AmadeusBackend.Repo;

import com.Encora.AmadeusBackend.Model.Flight;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FlightRepository implements FlightRepositoryInterface {
    private static final Map<String, String> airport = new HashMap<>();
    private static final Map<String, String> cities = new HashMap<>();
    public List<Flight> cachedList = new ArrayList<>();

    static {
        airport.put("AVV", "Avalon, Australia");
        airport.put("TPE", "Xiamen, China");
        airport.put("HAN", "Hanoi, Vietnam");
        airport.put("HKG", "Hong Kong, China");
        airport.put("SGN", "Tan Son Nhat, Vietnam");
        airport.put("XMN", "Xiamen Gaoqi, China");
        airport.put("MNL", "Manila, Philippines");
        airport.put("ATL", "Atlanta, EE.UU.");
        airport.put("LAX", "Los Angeles, EE.UU.");
        airport.put("ORD", "Chicago, EE.UU.");
        airport.put("DFW", "Dallas, EE.UU.");
        airport.put("DEN", "Denver, EE.UU.");
        airport.put("JFK", "Nueva York, EE.UU.");
        airport.put("SFO", "San Francisco, EE.UU.");
        airport.put("SEA", "Seattle, EE.UU.");
        airport.put("MIA", "Miami, EE.UU.");
        airport.put("LAS", "Las Vegas, EE.UU.");
        airport.put("YYZ", "Toronto, Canadá");
        airport.put("YVR", "Vancouver, Canadá");
        airport.put("MEX", "Ciudad de México, México");
        airport.put("CUN", "Cancún, México");
        airport.put("GRU", "São Paulo, Brasil");
        airport.put("GIG", "Rio de Janeiro, Brasil");
        airport.put("EZE", "Buenos Aires, Argentina");
        airport.put("BOG", "Bogota, Colombia");
        airport.put("LIM", "Lima, Peru");
        airport.put("SCL", "Santiago, Chile");
        airport.put("UIO", "Quito, Ecuador");
        airport.put("GYE", "Guayaquil, Ecuador");
        airport.put("MVD", "Montevideo, Uruguay");
        airport.put("LHR", "Londres, Reino Unido");
        airport.put("LGW", "Londres Gatwick, Reino Unido");
        airport.put("CDG", "Paris, Francia");
        airport.put("FRA", "Francfort, Alemania");
        airport.put("MUC", "Munich, Alemania");
        airport.put("MAD", "Madrid, España");
        airport.put("BCN", "Barcelona, España");
        airport.put("AMS", "Amsterdam, Países Bajos");
        airport.put("IST", "Estambul, Turquía");
        airport.put("VIE", "Viena, Austria");
        airport.put("ZRH", "Zurich, Suiza");
        airport.put("CPH", "Copenhague, Dinamarca");
        airport.put("DUB", "Dublín, Irlanda");
        airport.put("OSL", "Oslo, Noruega");
        airport.put("HND", "Tokio Haneda, Japón");
        airport.put("NRT", "Tokio Narita, Japón");
        airport.put("PEK", "Pekín, China");
        airport.put("PVG", "Shanghái, China");
        airport.put("CAN", "Cantón, China");
        airport.put("ICN", "Seúl, Corea del Sur");
        airport.put("SIN", "Singapur");
        airport.put("BKK", "Bangkok, Tailandia");
        airport.put("KUL", "Kuala Lumpur, Malasia");
        airport.put("DEL", "Delhi, India");
        airport.put("BOM", "Bombay, India");
        airport.put("CGK", "Yakarta, Indonesia");
        airport.put("DXB", "Dubai, Emiratos Árabes Unidos");
        airport.put("AUH", "Abu Dabi, Emiratos Árabes Unidos");
        airport.put("DOH", "Doha, Catar");
        airport.put("JED", "Yeda, Arabia Saudita");
        airport.put("RUH", "Riad, Arabia Saudita");
        airport.put("TLV", "Tel Aviv, Israel");
        airport.put("JNB", "Johannesburgo, Sudáfrica");
        airport.put("CPT", "Ciudad del Cabo, Sudáfrica");
        airport.put("NBO", "Nairobi, Kenia");
        airport.put("CAI", "El Cairo, Egipto");
        airport.put("CMN", "Casablanca, Marruecos");
        airport.put("ADD", "Addis Abeba, Etiopía");
        airport.put("SYD", "Sídney, Australia");
        airport.put("MEL", "Melbourne, Australia");
        airport.put("AKL", "Auckland, Nueva Zelanda");
        airport.put("BNE", "Brisbane, Australia");
        airport.put("PER", "Perth, Australia");
    }

    public String getCity(String codigoIATA) {
        return airport.getOrDefault(codigoIATA, "");
    }

}
