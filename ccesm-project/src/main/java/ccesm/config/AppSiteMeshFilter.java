package ccesm.config;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class AppSiteMeshFilter extends ConfigurableSiteMeshFilter {

	private final String MAIN_DECORATOR_DEFAULT_URL = "/WEB-INF/decorators/mainDecorator.jsp";

	  @Override
	  protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
	       builder
	       .addDecoratorPath("/home",MAIN_DECORATOR_DEFAULT_URL);
	 }
}