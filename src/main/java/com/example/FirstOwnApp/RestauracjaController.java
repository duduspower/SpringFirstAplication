package com.example.FirstOwnApp;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestauracjaController{

    public RestauracjaController() {
    }

    @GetMapping("/get")
    @ResponseBody
    public String getRestauracja(@RequestParam String id) {
        Restauracja restauracja = new Restauracja();
        return restauracja.getRestauracja(restauracja, Long.valueOf(id));
    }

    @GetMapping("/getRestauracjeCount")
    @ResponseBody
    public String getRestauracjeCount(){
        Restauracja restauracja = new Restauracja();
        return Long.toString(restauracja.getRestauracjaCount(restauracja));
    }

    @GetMapping("/get/all")
    @ResponseBody
    public String getRestauracjaAll() {
        Restauracja restauracja = new Restauracja();
        return restauracja.getRestauracjaAll(restauracja);
    }

    @GetMapping("/get/all/list")
    @ResponseBody
    public String getRestauracjaList() {
        Restauracja restauracja = new Restauracja();
        return restauracja.displayRestauracje();
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public String  delete(@RequestParam long id) throws ConfigDataResourceNotFoundException {
        Restauracja restauracja = new Restauracja();
        restauracja.deleteRestauracja(id);
        return "Usunięto restaurację o id : " + id;
    }
}
