package producerconsumer;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
@ToString
@EqualsAndHashCode
public class SoupBowl {
    @Getter
    private boolean empty = true;
    @Setter
    @Getter
    private int seqNum;

    void addSoup() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            log.error("Thread exception: ", e);
        }

        empty = false;
    }

    void eatSoup() {
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            log.error("Thread exception:", e);
        }
        empty = true;
    }

}
