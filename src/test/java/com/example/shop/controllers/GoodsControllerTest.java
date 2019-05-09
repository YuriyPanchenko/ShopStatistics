package com.example.shop.controllers;

import com.example.shop.Application;
import com.example.shop.model.Goods;
import com.example.shop.utils.InstantDateFormat;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = {Application.class, InstantDateFormat.class}
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:h2.properties")
public class GoodsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static Random random = new Random();

    private Matcher<Collection<Goods>> goodsMatcher(Collection<Goods> expected) {
        return new Matcher<Collection<Goods>>() {
            @Override
            public boolean matches(Object o) {
                if (o instanceof Collection) {
                    Collection ol = (Collection) o;
                    if (ol.size() != expected.size()) {
                        return false;
                    }
                    if (ol.isEmpty()) {
                        return true;
                    }

                    if (ol.iterator().next() instanceof Goods) {
                        Collection<Goods> goodsList = (Collection<Goods>) ol;
                        Iterator<Goods> expectedI = expected.iterator();
                        Iterator<Goods> actualI = goodsList.iterator();

                        while (expectedI.hasNext()) {
                            Goods expectedElem = expectedI.next();
                            Goods actualElem = actualI.next();

                            // compare by name since equals() not implemented; id changes on DB insert
                            if (!expectedElem.getName().equals(actualElem.getName())) {
                                return false;
                            }
                        }
                        return true;
                    }
                    return false;
                }
                return false;
            }

            @Override
            public void describeMismatch(Object o, Description description) {
                if (o instanceof Collection) {
                    Collection ol = (Collection) o;
                    if (ol.size() != expected.size()) {
                        description.appendText("Collection sizes do not match!");
                    }
                    else if (!ol.isEmpty() && !(ol.iterator().next() instanceof Goods)) {
                        description.appendText("Collection<Goods> expected, got another type parameter");

                    }
                } else {
                    description.appendText("Types mismatch: Collection expected");
                }
            }

            @Override
            public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Goods lists do not match");
            }
        };
    }


    @Test
    public void testSortedAllGoods() throws Exception {
        Instant now = Instant.now();
        List<Goods> goods = Stream.of(
                now.plus(2, ChronoUnit.DAYS),
                now.minus(2, ChronoUnit.DAYS),
                now
        )
                .map(ins -> new Goods(42.0, ins, "UAH"))
                .peek(g -> g.setName(g.getDate()))
                .collect(Collectors.toList());

        for (Goods g : goods) {
            mockMvc.perform(post("/")
                    .param("name", g.getName())
                    .param("price", Double.toString(g.getPrice()))
                    .param("date", g.getDate())
                    .param("currency", g.getCurrency())
            );
        }

        goods.sort(Comparator.comparing(Goods::getDate));

        mockMvc.perform(get("/showAllGoods"))
                .andExpect(model().attribute("sortedList", goodsMatcher(goods)))
                .andReturn();
    }

}