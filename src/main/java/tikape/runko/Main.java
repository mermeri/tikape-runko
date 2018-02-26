package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.database.Database;
import tikape.runko.database.DrinkkiDao;
import tikape.runko.domain.RaakaAine;

public class Main {

    public static void main(String[] args) throws Exception {
        

        Database database = new Database("jdbc:sqlite:db/drinkkiarkisto.db");
        
        DrinkkiDao drinkkiDao = new DrinkkiDao(database);
        RaakaAineDao raakaAineDao = new RaakaAineDao(database);
        database.init();

        Spark.get("/alku", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alku", drinkkiDao.findAll());

            return new ModelAndView(map, "alku");
        }, new ThymeleafTemplateEngine());

        Spark.get("/raaka-aineet/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raaka-aineet", raakaAineDao.findAll());
            System.out.println("sivu päivitetty");
            return new ModelAndView(map, "raaka-aineet");
            
        }, new ThymeleafTemplateEngine());

        Spark.get("/drinkinraaka-aineet/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkinraaka-aineet", drinkkiDao.findAll());

            return new ModelAndView(map, "drinkinraaka-aineet");
        }, new ThymeleafTemplateEngine());

        Spark.get("/drinkit/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "drinkit");
        }, new ThymeleafTemplateEngine());
        
        
        
        Spark.post("/raaka-aineet/", (req, res) -> {
            HashMap map = new HashMap<>();
            raakaAineDao.saveOrUpdate(new RaakaAine(-1, req.queryParams("nimi")));
            System.out.println("lisätään aine");
            res.redirect("/raaka-aineet/");
            System.out.println("ohjataan päivittyneelle sivulle");
            return"";
        });
        
    }
}
