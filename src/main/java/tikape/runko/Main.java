package tikape.runko;

import java.util.HashMap;
import java.util.stream.Collectors;
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

        Spark.get("/lisaadrinkkiaine/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findOne(Integer.parseInt(req.params(":id"))));
            map.put("RaakaAineet", raakaAineDao.findAll());
            System.out.println("sivu päivitetty");
            return new ModelAndView(map, "lisaadrinkkiaine");

        }, new ThymeleafTemplateEngine());

        Spark.get("/drinkit/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findOne(Integer.parseInt(req.params(":id"))));

            map.put("Ohjeet", ohjeDao.findAllO(req.params(":id")));

            System.out.println(ohjeDao.findOne(Integer.parseInt(req.params(":id"))));
            return new ModelAndView(map, "drinkinraaka-aineet");
        }, new ThymeleafTemplateEngine());

        Spark.get("/drinkit/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findAll());
            map.put("RaakaAineet", raakaAineDao.findAll());

            return new ModelAndView(map, "drinkit");
        }, new ThymeleafTemplateEngine());
        Spark.get("/drinkit/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findAll());
            map.put("RaakaAineet", raakaAineDao.findAll());

            return new ModelAndView(map, "drinkit");
        }, new ThymeleafTemplateEngine());

        Spark.get("/error/", (req, res) -> {
            HashMap map = new HashMap<>();

            return new ModelAndView(map, "error");
        }, new ThymeleafTemplateEngine());

        Spark.post("/raaka-aineet/:id/delete", (req, res) -> {
            
                HashMap map = new HashMap<>();
                if (req.params(":id").isEmpty()) {
                    throw new Exception();
                }

                ohjeDao.deleteRaakaAine(Integer.parseInt(req.params(":id")));
                raakaAineDao.delete(Integer.parseInt(req.params(":id")));
                System.out.println("poistetaan aine");
                res.redirect("/raaka-aineet/");
                System.out.println("ohjataan päivittyneelle sivulle");

            return "";

        });

        Spark.post("/:id/delete", (req, res) -> {
            try {
                HashMap map = new HashMap<>();
                if (req.params(":id").isEmpty()) {
                    throw new Exception();
                }
                if (ohjeDao.findOne(Integer.parseInt(req.params(":id"))) != null) {
                    ohjeDao.delete(Integer.parseInt(req.params(":id")));
                }

                drinkkiDao.delete(Integer.parseInt(req.params(":id")));
                System.out.println("poistetaan drinkki");
                res.redirect("/");
                System.out.println("ohjataan päivittyneelle sivulle");
            } catch (Exception e) {
                res.redirect("/error/");
            }

            return "";
        });

        Spark.post("/:id/drinkinraaka-aineet", (req, res) -> {
            HashMap map = new HashMap<>();

            res.redirect("/drinkinraaka-aineet/");
            System.out.println("ohjataan sivulle");
            return "";
        });

        Spark.post("/drinkit/", (req, res) -> {
            try {
                HashMap map = new HashMap<>();
                if (req.queryParams("aine").isEmpty()) {
                    throw new Exception();
                }
                drinkkiDao.saveOrUpdate(new Drinkki(-1, req.queryParams("aine")));
                System.out.println("lisätään drinkki");
                res.redirect("/drinkit/");
                System.out.println("ohjataan päivittyneelle sivulle");
            } catch (Exception e) {
                res.redirect("/error/");
            }

            return "";
        });

        Spark.post("/lisaadrinkkiaine/:id", (req, res) -> {
            try {
                HashMap map = new HashMap<>();
                System.out.println(req.queryParams("numero"));
                System.out.println(req.params(":id"));
                System.out.println(req.queryParams("jarjestys"));
                System.out.println(req.queryParams("maara"));
                System.out.println(req.queryParams("ohje"));
                if (req.params(":id").isEmpty() || req.queryParams("numero").isEmpty()
                        || req.queryParams("jarjestys").isEmpty()
                        || req.queryParams("maara").isEmpty()
                        || req.queryParams("ohje").isEmpty()) {
                    throw new Exception();
                }
                ohjeDao.saveOrUpdate(new Ohje(Integer.parseInt(req.params(":id")), Integer.parseInt(req.queryParams("numero")),
                        Integer.parseInt(req.queryParams("jarjestys")),
                        Integer.parseInt(req.queryParams("maara")),
                        req.queryParams("ohje")));

                res.redirect("/lisaadrinkkiaine/" + req.params("id"));
            } catch (Exception e) {
                res.redirect("/error/");
            }

            return "";
        });

        Spark.post("/raaka-aineet/", (req, res) -> {
            try {
                HashMap map = new HashMap<>();
                if (req.queryParams("aine").isEmpty()) {
                    throw new Exception();
                }
                raakaAineDao.saveOrUpdate(new RaakaAine(-1, req.queryParams("aine")));
                System.out.println("lisätään drinkki");
                res.redirect("/raaka-aineet/");
                System.out.println("ohjataan päivittyneelle sivulle");
            } catch (Exception e) {
                res.redirect("/error/");
            }
            return "";

        });

    }
}
