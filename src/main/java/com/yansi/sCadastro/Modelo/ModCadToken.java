/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yansi.sCadastro.Modelo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author programador
 */
@XmlRootElement
public class ModCadToken {
    
    ModUsuario cad;
    ModToken usuario;

    public ModUsuario getCad() {
        return cad;
    }

    public void setCad(ModUsuario cad) {
        this.cad = cad;
    }

    public ModToken getUsuario() {
        return usuario;
    }

    public void setUsuario(ModToken usuario) {
        this.usuario = usuario;
    }
    
    
}
