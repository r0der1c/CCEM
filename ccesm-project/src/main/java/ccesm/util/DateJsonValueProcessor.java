/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ccesm.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 *
 * @author Rodrigo Miranda
 */
public class DateJsonValueProcessor implements JsonValueProcessor {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public DateJsonValueProcessor(String formatter) {
        this.formatter = new SimpleDateFormat(formatter);
    }

    @Override
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return process(value, jsonConfig);
    }

    @Override
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        return process(value, jsonConfig);
    }

    private Object process(Object value, JsonConfig jsonConfig) {
        return (value != null)? formatter.format((Date) value): null;
    }
}
