/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author skanton
 */
public class DisplayOhje {
    
    private String nimi;
    private Integer jarjestys;
    private Integer maara;
    public String ohje;
    
    public DisplayOhje(String nimi, Integer jarjestys, Integer maara, String ohje) {
        this.nimi= nimi;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }
    
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    
    public void setjarjestys(Integer j) {
        this.jarjestys = j;
    }
    
    public void setMaara(Integer maara) {
        this.maara = maara;
    }
    
    public void setOhje(String ohje) {
        this.ohje = ohje;
    }
    
    public String getNimi() {
        return this.nimi;
    }
    
    
    public Integer getJarjestys() {
        return this.jarjestys;
    }
    
    public Integer getMaara() {
        return this.maara;
    }
    
    public String getOhje() {
        return this.ohje;
    }
}
    

