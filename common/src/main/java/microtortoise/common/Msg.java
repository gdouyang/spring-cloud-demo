package microtortoise.common;

import lombok.Data;

@Data
public class Msg<R> {
	private Integer code;
	private String message;
	private R result;
}
