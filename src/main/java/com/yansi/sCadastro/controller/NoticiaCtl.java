/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yansi.sCadastro.controller;

import com.yansi.sCadastro.modelo.Configuracao;
import com.yansi.sCadastro.modelo.ModNoticia;
import com.yansi.sCadastro.modelo.ModToken;
import com.yansi.sCadastro.util.ArquivoProperties;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.json.JsonObject;
import static javax.ws.rs.core.HttpHeaders.USER_AGENT;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author programador
 */
public class NoticiaCtl {

    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(NoticiaCtl.class.getName());

    ArrayList<ModNoticia> exibicaoTotal(ModToken modToken) {
        Configuracao configuracao = new Configuracao();
        ArquivoProperties conf = new ArquivoProperties();
        ArrayList<ModNoticia> arrNoticia = new ArrayList<ModNoticia>();
        JSONObject respostaNoticia;
        try {
            configuracao = conf.configInfo();
        } catch (IOException ex) {
            logger.error(NoticiaCtl.class.getName() + " " + ex);
            Logger.getLogger(NoticiaCtl.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("LINHA 37 " + configuracao.getTokenNoticia());

        try {
            respostaNoticia = sendGet(configuracao);
            arrNoticia = lerJson(respostaNoticia, modToken);
        } catch (Exception ex) {
            Logger.getLogger(NoticiaCtl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return arrNoticia;
    }

    // HTTP GET request
    private JSONObject sendGet(Configuracao configuracao) throws Exception {

        String url = configuracao.getUrlNoticia() + "/" + configuracao.getTokenNoticia();

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        JSONObject respostaServer;
        response.append("{retorno:");
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        response.append("}");
        in.close();
        System.out.println(response.toString());
        respostaServer = new JSONObject(response.toString());

        return respostaServer;

    }

    private ArrayList<ModNoticia> lerJson(JSONObject respostaNoticia, ModToken modToken) {
        ArrayList<ModNoticia> arrayNot = new ArrayList<ModNoticia>();

        JSONObject conteinerNoticia;

        JSONArray retorno = respostaNoticia.getJSONArray("retorno");
        System.out.println(retorno.length());
        for (int i = 0; i < retorno.length(); i++) {
            
            System.out.println(retorno.get(i).toString());
            conteinerNoticia = new JSONObject(retorno.get(i).toString());
            System.out.println(conteinerNoticia);
            ModNoticia modNoticia = new ModNoticia();
            
            if (modToken.getStatus().equals(conteinerNoticia.getString("cargo"))) {
                modNoticia.setId(conteinerNoticia.getInt("id"));
                modNoticia.setData(conteinerNoticia.getString("data"));
                modNoticia.setCargo(conteinerNoticia.getString("cargo"));
                modNoticia.setTitulo(conteinerNoticia.getString("titulo"));
                modNoticia.setConteudo(conteinerNoticia.getString("conteudo"));
            }

            arrayNot.add(modNoticia);
        }

        return arrayNot;
    }

}
