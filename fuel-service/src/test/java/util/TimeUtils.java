package util;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeUtils {

    public static final LocalDateTime REFERENCE_DATE_TIME = LocalDateTime.of(2000, 6, 15, 12, 0);

    public static final ZoneId REFERENCE_ZONE = ZoneId.systemDefault();

    public static Clock getFixedClock() {
        return Clock.fixed(REFERENCE_DATE_TIME.atZone(REFERENCE_ZONE).toInstant(), REFERENCE_ZONE);
    }
}
