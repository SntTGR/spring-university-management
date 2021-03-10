package snttgr.alkemy.challenge.config;

import org.springframework.format.Formatter;
import snttgr.alkemy.challenge.model.Professor;

import java.text.ParseException;
import java.util.Locale;

public class ProfessorFormatter implements Formatter<Professor> {



    @Override
    public Professor parse(String s, Locale locale) throws ParseException {
        throw new ParseException("String to professor not supported",0);
    }

    @Override
    public String print(Professor professor, Locale locale) {
        return professor.getSurname() + " " + professor.getName() + "(" + professor.getId() + ")";
    }

}