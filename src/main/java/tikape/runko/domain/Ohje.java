/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author Meri
 */
public class Ohje {
    private Integer drinkki_id;
    private Integer ainesosa_id;
    private Integer jarjestys;
    private String maara;
    private String ohje;
    
    public Ohje(Integer d_id, Integer a_id, Integer jarjestys, String maara, String ohje) {
        this.drinkki_id = d_id;
        this.ainesosa_id = a_id;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }
    
    public void setDrinkki_id(Integer d_id) {
        this.drinkki_id = d_id;
    }
    
    public void setAinesosa_id(Integer a_id) {
        this.ainesosa_id = a_id;
    }
    
    public void setjarjestys(Integer j) {
        this.jarjestys = j;
    }
    
    public void setMaara(String maara) {
        this.maara = maara;
    }
    
    public void setOhje(String ohje) {
        this.ohje = ohje;
    }
    
    public Integer getDrinkki_id() {
        return this.drinkki_id;
    }
    
    public Integer getAinesosa_id() {
        return this.ainesosa_id;
    }
    
    public Integer getJarjestys() {
        return this.jarjestys;
    }
    
    public String getMaara() {
        return this.maara;
    }
    
    public String geOhje() {
        return this.ohje;
    }
}
