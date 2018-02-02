package com.yansi.sCadastro.controller;

import com.yansi.sCadastro.Modelo.ModCadToken;
import com.yansi.sCadastro.Modelo.ModUsuario;
import com.yansi.sCadastro.Modelo.ModToken;
import com.yansi.sCadastro.util.JwtUtil;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("acessologin")
public class AcessoRota {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ModToken getValidaAcesso(ModUsuario modUsuario) {
        UsuarioCtl useCtl = new UsuarioCtl();
        AutorizadorCtl autCtl = new AutorizadorCtl();
        ModToken modToken = new ModToken();
        ModUsuario modUse;

        modToken.setToken("0");
        modToken.setUsuario("0");

        modUse = useCtl.validaLogin(modUsuario);

        if (modUse.getId() != 0) {
            System.out.println(modUse.getUsuario());
            String token = JwtUtil.create(modUse.getUsuario());
            if (autCtl.salvaToken(modUse, token)) {
                modToken.setToken(token);
                modToken.setUsuario(modUse.getNome());
                modToken.setStatus(modUse.getStatus()); 
            }
        }

        return modToken;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/cadastro")
    public String getCadastroUsuario(ModUsuario modUsuario) {
        String status = "";
        UsuarioCtl useCtl = new UsuarioCtl();

        status = useCtl.cadastroUsuario(modUsuario);

        return status;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/edidatUsario")
    public String getEditarUsuario(ModCadToken modUsuario) {
        UsuarioCtl useCtl = new UsuarioCtl();
        AutorizadorCtl autCtl = new AutorizadorCtl();
        String status;
        
        if (autCtl.validaToken(modUsuario.getUsuario().getToken())) {
            status = useCtl.editarUsuario(modUsuario.getCad(), modUsuario.getUsuario().getStatus());
        } else {
            status = "i";
        }
                
        return status;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/exibicao/{token}")
    public ArrayList<ModUsuario> getExibicaoUser(@PathParam("token") String token) {
        UsuarioCtl useCtl = new UsuarioCtl();
        AutorizadorCtl autCtl = new AutorizadorCtl();
        
        if (autCtl.validaToken(token)) {
            return useCtl.exibicaoTotal();
        } else {
            ModUsuario modUser = new ModUsuario();
            ArrayList<ModUsuario> arrayUser = new ArrayList<ModUsuario>();
            //arrayUser.add(modUser);
            return arrayUser;
        }   
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete/{token}")
    public void getDeleteToken(@PathParam("token") String token) {
        boolean status = false;
        AutorizadorCtl autCtl = new AutorizadorCtl();
        
        status = autCtl.deleteToken(token);
           
    }
}
