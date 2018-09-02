import { GasStation } from "./gasStation";
import { GasStationPrice } from "../gasStationPrice/gasStationPrice";

export class GasStationWithPrice {
  gasStation: GasStation;
  currentPrice?: GasStationPrice;
}
