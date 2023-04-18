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

    public void setOrderPickingStartTime() {
        this.orderPickingStartTime = this.completeBy.minus(this.pickingTime);
    }
}
