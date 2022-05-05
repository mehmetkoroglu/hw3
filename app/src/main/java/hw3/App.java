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

        Logger logger = LogManager.getLogger(App.class); // hata mesajlarını görebilmemizi sağlar
        logger.error("Error!");

        String systemPort = System.getenv("PORT"); // spark ve heroku farklı portlarda çalıştığı için
        int port = systemPort != null ? Integer.parseInt(systemPort) : 4567; // gerekli port ayarlaması
        port(port);
        logger.error("Current port number: " + port);

        // ana sayfa içeriği
        get("/", (req, res) -> "<h1>Hello World</h1><a href='/compute'>Go to Compute Page</a>");

        get("/compute", (req, res) -> { // hesaplama sayfası yolu dinamik içeriği
            Map<String, String> map = new HashMap<String, String>();
            map.put("result", "not computed yet!");
            return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());

        post("/compute", (req, res) -> { // hesaplama sayfasından gelen istekleri değerlendiren fonksiyon
            String input1 = req.queryParams("input1");

            Scanner scanner = new Scanner(input1);
            scanner.useDelimiter("[;\r\n]+");
            ArrayList<Integer> arrayList = new ArrayList<>();
            while (scanner.hasNext()) {
                int value = Integer.parseInt(scanner.next().replaceAll("\\s", ""));
                arrayList.add(value);
            }
            scanner.close();

            String input2 = req.queryParams("input2").replaceAll("\\s", "");
            int value2 = Integer.parseInt(input2);

            String input3 = req.queryParams("input3").replaceAll("\\s", "");
            int value3 = Integer.parseInt(input3);

            String input4 = req.queryParams("input4");

            boolean result = App.calc(arrayList, value2, value3, input4);

            Map<String, Boolean> map = new HashMap<String, Boolean>();
            map.put("result", result);

            return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());
    }

    // gelen parametrelere göre dört işlemden birini yapıp gelen dizide olup
    // olmadığını kontrol eden fonksiyon
    public static boolean calc(ArrayList<Integer> arrayList, int count1, int count2, String calcType) {

        if (arrayList.isEmpty()) { // gelen dizi boş ise true döndür
            return true;
        }
        if (calcType.equals("+")) { // gelen işlem türü toplama ise gelen parametreler toplanır
            return arrayList.contains(count1 + count2); // ve sonuç dizide var ise true döndürür.
        }
        if (calcType.equals("-")) { // gelen işlem türü çıkarma ise gelen parametreler çıkarılır
            return arrayList.contains(count1 - count2); // ve sonuç dizide var ise true döndürür.
        }
        if (calcType.equals("*")) { // gelen işlem türü çarpma ise gelen parametreler çarpılır
            return arrayList.contains(count1 * count2); // ve sonuç dizide var ise true döndürür.
        }
        if (calcType.equals("/")) { // gelen işlem türü bölme ise gelen parametreler bölünür
            return arrayList.contains(count1 / count2); // ve sonuç dizide var ise true döndürür.
        }

        return false; // hiç bir şart sağlanmazsa varsayışan olarak false döndürür.
    }
}
