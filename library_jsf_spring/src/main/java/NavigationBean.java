import java.io.Serializable;

import org.springframework.web.context.annotation.SessionScope;


@SessionScope
public class NavigationBean implements Serializable{

	private String page;
	private static final long serialVersionUID = 1L;
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}	
}
