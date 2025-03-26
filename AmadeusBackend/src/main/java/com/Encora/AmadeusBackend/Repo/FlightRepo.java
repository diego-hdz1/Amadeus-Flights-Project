package com.Encora.AmadeusBackend.Repo;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class FlightRepo implements FlightRepoInterface{
    private static final Map<String, String> aeropuertos = new HashMap<>();

    static {
        aeropuertos.put("TPE", "Xiamen, China");
        aeropuertos.put("HAN", "Hanoi, Vietnam");
        aeropuertos.put("HKG", "Hong Kong, China");
        aeropuertos.put("SGN", "Tan Son Nhat, Vietnam");
        aeropuertos.put("XMN", "Xiamen Gaoqi, China");
        aeropuertos.put("MNL", "Manila, Philippines");
        aeropuertos.put("ATL", "Atlanta, EE.UU.");
        aeropuertos.put("LAX", "Los Angeles, EE.UU.");
        aeropuertos.put("ORD", "Chicago, EE.UU.");
        aeropuertos.put("DFW", "Dallas, EE.UU.");
        aeropuertos.put("DEN", "Denver, EE.UU.");
        aeropuertos.put("JFK", "Nueva York, EE.UU.");
        aeropuertos.put("SFO", "San Francisco, EE.UU.");
        aeropuertos.put("SEA", "Seattle, EE.UU.");
        aeropuertos.put("MIA", "Miami, EE.UU.");
        aeropuertos.put("LAS", "Las Vegas, EE.UU.");
        aeropuertos.put("YYZ", "Toronto, Canadá");
        aeropuertos.put("YVR", "Vancouver, Canadá");
        aeropuertos.put("MEX", "Ciudad de México, México");
        aeropuertos.put("CUN", "Cancún, México");

        aeropuertos.put("GRU", "São Paulo, Brasil");
        aeropuertos.put("GIG", "Rio de Janeiro, Brasil");
        aeropuertos.put("EZE", "Buenos Aires, Argentina");
        aeropuertos.put("BOG", "Bogota, Colombia");
        aeropuertos.put("LIM", "Lima, Peru");
        aeropuertos.put("SCL", "Santiago, Chile");
        aeropuertos.put("UIO", "Quito, Ecuador");
        aeropuertos.put("GYE", "Guayaquil, Ecuador");
        aeropuertos.put("MVD", "Montevideo, Uruguay");

        aeropuertos.put("LHR", "Londres, Reino Unido");
        aeropuertos.put("LGW", "Londres Gatwick, Reino Unido");
        aeropuertos.put("CDG", "Paris, Francia");
        aeropuertos.put("FRA", "Francfort, Alemania");
        aeropuertos.put("MUC", "Munich, Alemania");
        aeropuertos.put("MAD", "Madrid, España");
        aeropuertos.put("BCN", "Barcelona, España");
        aeropuertos.put("AMS", "Amsterdam, Países Bajos");
        aeropuertos.put("IST", "Estambul, Turquía");
        aeropuertos.put("VIE", "Viena, Austria");
        aeropuertos.put("ZRH", "Zurich, Suiza");
        aeropuertos.put("CPH", "Copenhague, Dinamarca");
        aeropuertos.put("DUB", "Dublín, Irlanda");
        aeropuertos.put("OSL", "Oslo, Noruega");

        aeropuertos.put("HND", "Tokio Haneda, Japón");
        aeropuertos.put("NRT", "Tokio Narita, Japón");
        aeropuertos.put("PEK", "Pekín, China");
        aeropuertos.put("PVG", "Shanghái, China");
        aeropuertos.put("CAN", "Cantón, China");
        aeropuertos.put("ICN", "Seúl, Corea del Sur");
        aeropuertos.put("SIN", "Singapur");
        aeropuertos.put("BKK", "Bangkok, Tailandia");
        aeropuertos.put("KUL", "Kuala Lumpur, Malasia");
        aeropuertos.put("DEL", "Delhi, India");
        aeropuertos.put("BOM", "Bombay, India");
        aeropuertos.put("CGK", "Yakarta, Indonesia");

        aeropuertos.put("DXB", "Dubai, Emiratos Árabes Unidos");
        aeropuertos.put("AUH", "Abu Dabi, Emiratos Árabes Unidos");
        aeropuertos.put("DOH", "Doha, Catar");
        aeropuertos.put("JED", "Yeda, Arabia Saudita");
        aeropuertos.put("RUH", "Riad, Arabia Saudita");
        aeropuertos.put("TLV", "Tel Aviv, Israel");

        aeropuertos.put("JNB", "Johannesburgo, Sudáfrica");
        aeropuertos.put("CPT", "Ciudad del Cabo, Sudáfrica");
        aeropuertos.put("NBO", "Nairobi, Kenia");
        aeropuertos.put("CAI", "El Cairo, Egipto");
        aeropuertos.put("CMN", "Casablanca, Marruecos");
        aeropuertos.put("ADD", "Addis Abeba, Etiopía");

        aeropuertos.put("SYD", "Sídney, Australia");
        aeropuertos.put("MEL", "Melbourne, Australia");
        aeropuertos.put("AKL", "Auckland, Nueva Zelanda");
        aeropuertos.put("BNE", "Brisbane, Australia");
        aeropuertos.put("PER", "Perth, Australia");
    }

    public String getCity(String codigoIATA) {
        return aeropuertos.getOrDefault(codigoIATA, "");
    }

}
