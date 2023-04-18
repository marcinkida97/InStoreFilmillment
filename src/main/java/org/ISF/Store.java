//---store configuration---//

package org.ISF;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Store {

    private List<String> pickers;
    private LocalTime pickingStartTime;
    private LocalTime pickingEndTime;
}
