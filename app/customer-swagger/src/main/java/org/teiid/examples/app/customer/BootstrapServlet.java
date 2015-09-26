package org.teiid.examples.app.customer;

import io.swagger.jaxrs.config.BeanConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "BootstrapServlet", loadOnStartup = 2)
public class BootstrapServlet extends HttpServlet {

    private static final long serialVersionUID = 410107684638197198L;
    
    @Override
    public void init(ServletConfig paramServletConfig) throws ServletException {
        super.init(paramServletConfig);
        BeanConfig config = new BeanConfig();
        config.setTitle("Customer");
        config.setDescription("Teiid Examples Customer JAX-RS WebService With Swagger");
        config.setVersion("1.0");
        config.setSchemes(new String[] { "http", "https"});
        config.setBasePath("/");
        config.setResourcePackage("org.teiid.examples.app.customer");
        config.setScan(true);
    }


}
