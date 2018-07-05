package de.etino.fuelagent.gasstation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "gas_station_price")
public class GasStationPrice {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "gas_station_id")
    private GasStation gasStation;

    private LocalDateTime dateTime;
    private String dieselPrice;
    private String gasoline95Price;
    private String gasoline98Price;
}
