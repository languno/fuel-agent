package de.etino.fuelagent.gasstation.diariosur;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "markers")
public class Markers {
    List<Marker> marker;
}
