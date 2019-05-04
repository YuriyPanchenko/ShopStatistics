package com.example.shop.controllers;


import com.example.shop.model.Goods;
import com.example.shop.repos.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class GoodsController {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private DateFormat instantDateFormat;

    @GetMapping(path = "/")
    public String idx(){
        return "index";

    }

    @PostMapping("/")
    public String addNewGoods(@RequestParam String name,
                              @RequestParam double price,
                              @RequestParam String date,
                              @RequestParam String currency) throws ParseException {
        Date dateObj = instantDateFormat.parse(date);
        Goods goods = new Goods(name, price, dateObj.toInstant(), currency);
        goodsRepository.save(goods);

        return "index";


    }

    /*@PostMapping(path="/filter")
    public @ResponseBody
    ModelAndView getAllGoods(@RequestBody String filter) {
        // This returns a JSON or XML with the users

        Iterable<Goods> all = goodsRepository.findAll();
        return new ModelAndView("allGoods.mustache", Collections.singletonMap("list", all));
    }*/

    @GetMapping(path = "/showAllGoods")
    public String sortedAllGoods(Map<String, Object> model){
        List<Goods> goods = (List<Goods>) goodsRepository.findAll();

        model.put("sortedList", goods.stream().sorted(Comparator.comparing(Goods::getDate)).collect(Collectors.toList()));

        return "sorted";
     }


     @GetMapping(path = "/findGoods")
    public String findByDate() throws ParseException {

        return "/findByDate";
     }

     @PostMapping(path = "/findGoods")
     public String  fidedByDateGoods( @RequestParam String date, Map<String, Object> model) throws ParseException {
        //new SimpleDateFormat(yyyy-MM-dd) -> instantDateFormat
         Date dateObj = instantDateFormat.parse(date);
         List goodsWithnNecessaryDay = goodsRepository.findAllByDate(dateObj.toInstant());
         model.put("dateList", goodsWithnNecessaryDay);
         model.put("date", date);
         return "allGoodsInNecessaryDate";

    }

//variable in parh
    @PostMapping(path = "/deleteByDate/{date}")
    public String deleteByDate(@PathVariable String date) throws ParseException {

        Instant time = instantDateFormat.parse(date).toInstant();
        goodsRepository.deleteAllByDate(time);

        return "index";
    }


}
