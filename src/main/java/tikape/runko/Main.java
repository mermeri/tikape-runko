package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AinesosaDao;
import tikape.runko.database.Database;
import tikape.runko.database.DrinkkiDao;
import tikape.runko.domain.Ainesosa;

public class Main {

    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        Database database = new Database("jdbc:sqlite:db/drinkkiarkisto.db");
        database.init();

        DrinkkiDao drinkkiDao = new DrinkkiDao(database);
        AinesosaDao ainesosaDao = new AinesosaDao(database);

        get("/alku", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alku", drinkkiDao.findAll());

            return new ModelAndView(map, "alku");
        }, new ThymeleafTemplateEngine());

        get("/raaka-aineet/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raaka-aineet", drinkkiDao.findAll());

            return new ModelAndView(map, "raaka-aineet");
        }, new ThymeleafTemplateEngine());

        get("/drinkinraaka-aineet/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkinraaka-aineet", drinkkiDao.findAll());

            return new ModelAndView(map, "drinkinraaka-aineet");
        }, new ThymeleafTemplateEngine());

        get("/drinkit/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "drinkit");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/raaka-aineet/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raaka-aineet", ainesosaDao.saveOrUpdate(new Ainesosa(null, req.queryParams("nimi"))));
            res.redirect("/raaka-aineet/");

            return new ModelAndView(map, "raaka-aineet");
        }, new ThymeleafTemplateEngine());
        
        //tässä templatena post komento
        Spark.post("/raaka-aineet/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raaka-aineet", ainesosaDao.saveOrUpdate(new Ainesosa(null, req.queryParams("nimi"))));
            res.redirect("/raaka-aineet/");

            return new ModelAndView(map, "raaka-aineet");
        }, new ThymeleafTemplateEngine());
    }
}
