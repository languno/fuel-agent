package de.etino.fuelagent.gasstation;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "gas_station")
public class GasStation {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Exclude
    Long id;

    private String countryCode;
    private String province;
    private String brand;
    private String address;
    private String latitude;
    private String longitude;
}
