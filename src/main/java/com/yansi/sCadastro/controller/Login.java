
package com.yansi.sCadastro.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/acessologin")
public class Login {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String validaAcesso () {
        String status = null;
        
        return status;
    }
}
