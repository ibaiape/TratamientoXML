package com.example.dm2.tratamientoxml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class RssParserDomTiempo {
    private URL rssURL;

    public RssParserDomTiempo(String url){
        try{
            this.rssURL =new URL (url);
        }catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String parse() {
        //Instanciamos la fabrica para DOM
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        String str = "";
        try {
            //Creamos un nuevo parser DOM
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Realizamos la lectura completa del XML
            Document dom = builder.parse(this.getInputStream());
            //Nos posicionamos en el nodo principal del árbol (<rss>)
            Element root = dom.getDocumentElement();
            //Localizamos todos los elemntos <item>
            NodeList items = root.getElementsByTagName("dia");
            //Recorremos la lista de noticias
            Node item = items.item(0);

            NodeList datosDia = item.getChildNodes();

            for (int i=0; i<datosDia.getLength(); i++){
                Node dato = datosDia.item(i);
                try{
                    Element e = (Element)dato;
                    String periodo = e.getAttribute("periodo");
                    if(periodo.equals("00-24")){
                        String etiqueta = dato.getNodeName();
                        if (etiqueta.equals("prob_precipitacion")){
                            String texto = obtenerTexto(dato);
                            str += "Probabilidad de precipitación: "+texto+"%\n";
                        }
                        else if (etiqueta.equals("cota_nieve_prov")){
                            String texto = obtenerTexto(dato).length()>0 ? obtenerTexto(dato) : "0";
                            str += "Cuota de nieve provinciál: "+texto+"M\n";
                        }
                        else if (etiqueta.equals("estado_cielo")){
                            String desc  = e.getAttribute("descripcion");
                            String texto = dato.getFirstChild().getNodeValue();
                            str += "Estado del cielo: "+desc+" "+texto+"%\n";
                        }
                        else if (etiqueta.equals("viento")) {
                            str += "Viento: ";
                            NodeList datosViento = dato.getChildNodes();
                            for (int x=0; x<datosViento.getLength(); x++){
                                Node datoViento = datosViento.item(x);
                                String texto = obtenerTexto(datoViento);
                                str += texto + " ";
                            }
                            str += "\n";
                        }
                        else if (etiqueta.equals("temperatura")){
                            str += "Temperatura: ";
                            NodeList datosTemperatura = dato.getChildNodes();
                            for (int x=0; x<datosTemperatura.getLength(); x++){
                                Node datoTemperatura = datosTemperatura.item(x);
                                String texto = obtenerTexto(datoTemperatura);
                                str += texto + " ";
                            }
                            str += "\n";
                        }
                    }
                }catch (ClassCastException e){
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return str;
    }
    public String obtenerTexto (Node dato) {

        StringBuilder texto = new StringBuilder();
        NodeList fragmentos = dato.getChildNodes();

        for (int k=0; k<fragmentos.getLength(); k++) {
            texto.append(fragmentos.item(k).getNodeValue());
        }

        return texto.toString();

    }
    private InputStream getInputStream() {
        try {
            return rssURL.openConnection().getInputStream();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}