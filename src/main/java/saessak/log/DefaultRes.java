package saessak.log;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DefaultRes<T> {

    private int statusCode;

    private T responseMessage;

    private DefaultRes(final int statusCode, final T responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
    }

    public static <T> DefaultRes<T> res(final int statusCode, final T responseMessage) {
        return new DefaultRes<>(statusCode, responseMessage);
    }
}
