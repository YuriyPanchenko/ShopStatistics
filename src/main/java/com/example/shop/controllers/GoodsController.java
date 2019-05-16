package com.example.shop.controllers;


import com.example.shop.model.Goods;
import com.example.shop.repos.GoodsRepository;
import com.example.shop.servise.CurrencyApiService;
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
    
    @Autowired
    private CurrencyApiService currencyApiService;

    @GetMapping(path = "/")
    public String idx(Map<String, Object> model){
        model.put("nowFormatted", instantDateFormat.format(Date.from(Instant.now())));
        return "index";

    }


    @PostMapping("/")
    public String addNewGoods(@RequestParam String name,
                              @RequestParam double price,
                              @RequestParam String date,
                              @RequestParam String currency,
                              Map<String , Object> model) throws ParseException {
        Date dateObj = instantDateFormat.parse(date);
        Goods goods = new Goods(name, price, dateObj.toInstant(), currency);
        goodsRepository.save(goods);
        model.put("nowFormatted", instantDateFormat.format(Date.from(Instant.now())));
        return "index";


    }

    @GetMapping(path = "/showAllGoods")
    public String sortedAllGoods(Map<String, Object> model){
        List<Goods> goods = goodsRepository.findByOrderByDate();

        model.put("sortedList", goods);

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

        return "findByDate";
    }

    @GetMapping(path = "/totalYearProfit")
    public String totalYearProfit(){
        return "/totalProfit";
    }

    @PostMapping(path = "/totalYearProfit")
    public String showYearProfit(@RequestParam String currency, @RequestParam String year, Map<String, Object> model){

        List <Goods> goods = (List<Goods>) goodsRepository.findAll();
        double total = goods.stream()
                .filter(c -> c.getDate().toString().substring(0, 4).equals(year))/*get(ChronoField.YEAR)*/
                .map(x -> x.getPrice()*currencyApiService.getPrice(x.getCurrency(), currency, x.getInstantDate()))
                .reduce(0d, (a, b) -> a + b);
        model.put("total", Math.round(total));
        model.put("currency", currency);
            return "totalProfit";
    }


}
