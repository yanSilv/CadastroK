
package com.yansi.sCadastro.myapp;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("rest")
public class MyApp extends ResourceConfig {
    
    
    public MyApp () {
        packages("com.yansi.sCadastro.controller");
    }
}
