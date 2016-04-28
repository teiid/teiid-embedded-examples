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
        
        config.setResourcePackage("org.teiid.examples.app.customer");
        config.setSchemes(new String[] { "http", "https"});  
        config.setTitle("Customer");
        config.setVersion("1.0");
        config.setDescription("Teiid Examples Customer JAX-RS WebService With Swagger");
        
        config.setContact("teiid.org");
        config.setLicense("Apache 2.0");
        config.setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html");
        
        config.setHost("localhost:8080");
        config.setBasePath("/");
        
        config.setScan(true);
    }


}
