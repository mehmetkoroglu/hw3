/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package hw3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import spark.ModelAndView;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.port;
import spark.template.mustache.MustacheTemplateEngine;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());

        Logger logger = LogManager.getLogger(App.class);
        logger.error("naber");

        int port = Integer.parseInt(System.getenv("PORT"));
        port(port);
        logger.error("Current port number:" + port);

        get("/", (req, res) -> "Hello World");

        get("/compute", (req, res) -> {
            Map<String, String> map = new HashMap<String, String>();
            map.put("result", "not computed yet!");
            return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());

        post("/compute", (req, res) -> {
            String input1 = req.queryParams("input1");

            Scanner scanner1 = new Scanner(input1);
            scanner1.useDelimiter("[;\r\n]+");
            ArrayList<Integer> arrayList = new ArrayList<>();
            while (scanner1.hasNext()) {
                int value = Integer.parseInt(scanner1.next().replaceAll("\\s", ""));
                arrayList.add(value);
            }
            scanner1.close();

            String input2 = req.queryParams("input2").replaceAll("\\s", "");
            int value2 = Integer.parseInt(input2);

            String input3 = req.queryParams("input3").replaceAll("\\s", "");
            int value3 = Integer.parseInt(input3);

            String input4 = req.queryParams("input4").replaceAll("\\s", "");
            int value4 = Integer.parseInt(input4);

            boolean result = App.calc(arrayList, value2, value3, value4);

            Map<String, Boolean> map = new HashMap<String, Boolean>();
            map.put("result", result);

            return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());
    }

    public static boolean calc(ArrayList<Integer> arrayList, int a, int b, int c) {
        if (arrayList.isEmpty()) return true;
        if (arrayList.contains(a)) return true;
              
        return false;
    }
}
