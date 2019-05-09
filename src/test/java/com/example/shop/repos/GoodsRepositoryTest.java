package com.example.shop.repos;

import com.example.shop.model.Goods;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class GoodsRepositoryTest {

    @Autowired
    private GoodsRepository goodsRepository;


    @Test
    public void testDeleteAllByDate() {

        Instant now = Instant.now();

        List<Goods> goods = Stream.of(
                now,
                now,
                now.minus(2, ChronoUnit.DAYS)
        )
                .map(ins -> new Goods(42.0, ins, "UAH"))
                .collect(Collectors.toList());

        goodsRepository.saveAll(goods);
        goodsRepository.deleteAllByDate(now);
        Iterator<Goods> allRemaining = goodsRepository.findAll().iterator();
        AtomicInteger counter = new AtomicInteger(0);
        allRemaining.forEachRemaining((i) -> counter.incrementAndGet());

        assertEquals(counter.get(), 1);
    }
}