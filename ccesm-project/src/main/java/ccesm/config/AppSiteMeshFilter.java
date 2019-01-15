package ccesm.config;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class AppSiteMeshFilter extends ConfigurableSiteMeshFilter {

	private final String MAIN_DECORATOR_DEFAULT_URL = "/WEB-INF/decorators/classic/decoratorAdministrador.jsp";

	  @Override
	  protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
	       builder
	       		.addDecoratorPath("/admin**",MAIN_DECORATOR_DEFAULT_URL);
	 }
}