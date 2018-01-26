
package com.yansi.sCadastro.controller;

import com.yansi.sCadastro.Modelo.ModUsuario;
import java.util.List;
import javax.swing.JOptionPane;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import org.glassfish.jersey.message.internal.MediaTypes;

@Path("acessologin")
public class Login {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ModUsuario getValidaAcesso (ModUsuario modUsuario) {
        String status = "Olá Mundo 1";
        
        JOptionPane.showMessageDialog(null, modUsuario.getNome());
        JOptionPane.showMessageDialog(null, modUsuario.getSenha());
        JOptionPane.showMessageDialog(null, modUsuario.getUsuario());
        JOptionPane.showMessageDialog(null, modUsuario.getIdade());
        JOptionPane.showMessageDialog(null, modUsuario.getId());
        System.out.println(status);
        
        return modUsuario;
    }
}
