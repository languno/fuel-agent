package de.etino.fuelagent.gasstation;

import lombok.Data;

@Data
public class GasStation {
    private String brand;
    private String address;
    private String dieselPrice;
    private String gasoline95Price;
    private String gasoline98Price;
}
