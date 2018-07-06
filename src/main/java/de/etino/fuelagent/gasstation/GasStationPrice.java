package de.etino.fuelagent.gasstation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gas_station_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GasStation gasStation;

    private LocalDateTime dateTime;
    private String dieselPrice;
    private String gasoline95Price;
    private String gasoline98Price;
}
