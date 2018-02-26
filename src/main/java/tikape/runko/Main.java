package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.database.Database;
import tikape.runko.database.DrinkkiDao;
import tikape.runko.database.OhjeDao;
import tikape.runko.domain.RaakaAine;
import tikape.runko.domain.Ohje;
import tikape.runko.domain.Drinkki;

public class Main {

    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        Database database = new Database("jdbc:sqlite:db/drinkkiarkisto.db");
        
        DrinkkiDao drinkkiDao = new DrinkkiDao(database);
        RaakaAineDao raakaAineDao = new RaakaAineDao(database);
        OhjeDao ohjeDao = new OhjeDao(database);
        
        database.init();

        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findAll());

            return new ModelAndView(map, "alku");
        }, new ThymeleafTemplateEngine());

        Spark.get("/raaka-aineet/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("RaakaAineet", raakaAineDao.findAll());
            System.out.println("sivu päivitetty");
            return new ModelAndView(map, "raaka-aineet");
            
        }, new ThymeleafTemplateEngine());


        Spark.get("/drinkit/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findOne(Integer.parseInt(req.params(":id"))));

            return new ModelAndView(map, "drinkinkinraaka-aineet");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/drinkit/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "drinkit");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/raaka-aineet/", (req, res) -> {
            HashMap map = new HashMap<>();
            raakaAineDao.saveOrUpdate(new RaakaAine(-1, req.queryParams("aine")));
            System.out.println("lisätään aine");
            res.redirect("/raaka-aineet/");
            System.out.println("ohjataan päivittyneelle sivulle");
            return"";
        });
        
        
    }
}
