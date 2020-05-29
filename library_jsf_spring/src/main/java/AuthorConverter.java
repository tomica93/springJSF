import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.library.test.AuthorRepository;
import com.library.test.model.Author;
import com.library.test.model.Book;

@Component
@FacesConverter(forClass = Book.class)
public class AuthorConverter implements Converter {

	@Autowired
	public AuthorRepository authorRepository;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null) {
			try {
				Long authorId = Long.parseLong(value);    //Long.parseLong(value); //parselong ili valueOf
				return authorRepository.findOne(authorId);
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value instanceof Author) {
			return ((Long) ((Author) value).getId()).toString();
		}
		return null;
	}
}
