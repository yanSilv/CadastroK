package com.yansi.sCadastro.controller;

import com.yansi.sCadastro.modelo.ModCadToken;
import com.yansi.sCadastro.modelo.ModNoticia;
import com.yansi.sCadastro.modelo.ModUsuario;
import com.yansi.sCadastro.modelo.ModToken;
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
import org.apache.log4j.Logger;

@Path("acessologin")
public class AcessoRota {
    
    final static Logger logger = Logger.getLogger(AcessoRota.class.getName());

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
            logger.info("A- Acesso ao sistema: "+ modUsuario.getUsuario());
        } else {
            logger.warn("T- Tesntativa de acessar o sistema: "+ modUsuario.getUsuario());
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
        
        logger.info("C- Cadastro ao sistema: "+ modUsuario.getUsuario() +" Retorno " + status);

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
            
            logger.info("E- "+modUsuario.getUsuario().getNome()+" Alterou dos dados do usuario "+ modUsuario.getCad().getUsuario());
            
            status = useCtl.editarUsuario(modUsuario.getCad(), modUsuario.getUsuario().getStatus());
        
        } else {
            logger.warn("Ex- Token expirado"+ modUsuario.getUsuario().getNome());
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
            logger.info("Co- consulta realizada com sucesso");
            return useCtl.exibicaoTotal();
        } else {
            logger.info("CE- Nenhum usario cadastrado");
            ModUsuario modUser = new ModUsuario();
            ArrayList<ModUsuario> arrayUser = new ArrayList<ModUsuario>();
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
        
        logger.info("S- usuario saiu do sistema");
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/noticia/{token}")
    public ArrayList<ModNoticia> getNoticia(@PathParam("token") String token) {
        NoticiaCtl notCtl = new NoticiaCtl();
        AutorizadorCtl autCtl = new AutorizadorCtl();
        ModToken modToken = new ModToken();
        
        if (autCtl.validaToken(token)) {
            logger.info("Co- Consultando Noticias");
            return notCtl.exibicaoTotal(autCtl.busca(token));
        } else {
            logger.info("CE- Nenhum usario cadastrado");
            //ModNoticia modNot = new ModNoticia();
            ArrayList<ModNoticia> arrayUser = new ArrayList<ModNoticia>();
            return arrayUser;
        }   
    }
}
