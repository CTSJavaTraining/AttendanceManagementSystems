/**
 * 
 */
package com.attendance.util;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author 523696
 *
 */
public class LocalDateTimeDeserializer  extends JsonDeserializer<LocalDate> {
	 @Override
	    public LocalDate deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
	        return LocalDate.parse(arg0.getText());
	    }

}
