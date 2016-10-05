/**
 * 
 */
package com.attendance.util;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author 523696
 *
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDate> {
	@Override
	public void serialize(LocalDate arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
		arg1.writeString(arg0.toString());
	}

}