package com.example.shop.repos;

import com.example.shop.model.Goods;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

public interface GoodsRepository extends CrudRepository<Goods, Integer> {

   List<Goods> findAllByDate( Instant date);

   @Transactional
   void deleteAllByDate(Instant date);

}
