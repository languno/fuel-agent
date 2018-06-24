package de.etino.fuelagent.gasstation.diariosur;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "markers")
public class GasStations {

    List<Marker> marker;

    @ToString
    @XmlType(name = "marker")
    public static class Marker {

        @XmlAttribute(name = "rotulo")
        String brand;

        @XmlAttribute(name = "direccion")
        String address;

        @XmlAttribute(name ="gasoleo_a")
        String dieselPrice;

        @XmlAttribute(name ="gasolina_95_proteccion")
        String gasoline95Price;

        @XmlAttribute(name ="gasolina_98")
        String gasoline98Price;
    }
}
