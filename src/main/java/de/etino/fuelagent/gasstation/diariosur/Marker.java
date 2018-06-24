package de.etino.fuelagent.gasstation.diariosur;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@Data
@XmlType(name = "marker")
@XmlAccessorType(XmlAccessType.FIELD)
public class Marker {

    @XmlAttribute(name = "rotulo")
    private String brand;

    @XmlAttribute(name = "direccion")
    private String address;

    @XmlAttribute(name ="gasoleo_a")
    private String dieselPrice;

    @XmlAttribute(name ="gasolina_95_proteccion")
    private String gasoline95Price;

    @XmlAttribute(name ="gasolina_98")
    private String gasoline98Price;
}
