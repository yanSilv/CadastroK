/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yansi.sCadastro.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.yansi.sCadastro.modelo.Configuracao;
import java.io.File;

/**
 *
 * @author programador
 */
public class ArquivoProperties {
    
    
    public ArquivoProperties () {
    }
    
    /**
     * Ler o arquivo de configuração na pasta properties
     * @return 
     * @throws java.io.IOException
    */
    public static Properties getProp() throws IOException {
        Properties props = new Properties();      
        FileInputStream file = new FileInputStream("/sProdutos/API/config.cfg");
        props.load(file);
        return props;

    }
        
    //Ler o arquivo propriedades.
    public String campos (String campo) throws IOException {
        String retorno;
        Properties prop = getProp();
        retorno = prop.getProperty(campo);
        return retorno;
    }
    
    //Carrega todas as informações do arquivo de propriedades.
    public Configuracao configInfo () throws IOException {
        ArquivoProperties  arqPropre = new ArquivoProperties();
        Configuracao config = new Configuracao();
        
        //Configurações do ambiente
        config.setTokenNoticia(arqPropre.campos("Noticia"));
        config.setUrlNoticia(arqPropre.campos("UrlNoticia"));

        return config;
    }
}
