
package com.yansi.sCadastro.controller;

import com.yansi.sCadastro.Modelo.ModUsuario;
import javax.swing.JOptionPane;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("acessologin")
public class AcessoRota {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ModUsuario getValidaAcesso (ModUsuario modUsuario) {
        UsuarioCtl useCtl = new UsuarioCtl();
        
        return useCtl.validaLogin(modUsuario);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean getCadastroUsuario (ModUsuario modUsuario) {
        boolean status = false;
        UsuarioCtl useCtl = new UsuarioCtl();
        
        status = useCtl.cadastroUsuario(modUsuario);
        
        return status;
    }
}
