//---order class---//

package org.ISF;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class Order {

    private String orderId;
    private BigDecimal orderValue;
    private Duration pickingTime;
    private LocalTime completeBy;
    private transient LocalTime orderPickingStartTime;

    public static LocalTime calculateOrderPickingStartTime(LocalTime completeBy, Duration pickingTime) {
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), completeBy);
        LocalDateTime resultDateTime = dateTime.minus(pickingTime);
        LocalTime resultTime = resultDateTime.toLocalTime();
        return resultTime;
    }
}
