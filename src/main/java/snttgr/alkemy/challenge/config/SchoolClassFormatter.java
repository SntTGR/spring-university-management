package snttgr.alkemy.challenge.config;

import org.springframework.format.Formatter;
import snttgr.alkemy.challenge.model.SchoolClass;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class SchoolClassFormatter implements Formatter<SchoolClass> {

    static private final int minimizeSize = 10;

    @Override
    public SchoolClass parse(String s, Locale locale) throws ParseException {
        throw new ParseException("String to class not supported",0);
    }

    @Override
    public String print(SchoolClass schoolClass, Locale locale) {
        return formatClass(schoolClass);
    }

    private String formatClass(SchoolClass schoolClass){

        if(schoolClass.getName().length() > minimizeSize) return schoolClass.getName().substring(0, minimizeSize) + "... (" + schoolClass.getId() + ")";

        return schoolClass.getName() + "(" + schoolClass.getId() + ")";
    }
}
