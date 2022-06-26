package jpabook.jpashop.service;


import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 아이템저장() throws Exception {
        //given
        Item item = new Album();
        item.setName("앨범");
        item.setPrice(30000);
        item.setStockQuantity(10);
        //when
        itemService.saveItem(item);
        Long savedId = item.getId();
        //then
        Item savedItem = itemRepository.findOne(savedId);
        assertThat(savedItem).isEqualTo(item);
    }

    @Test
    public void 아이템수량증가() throws Exception {
        //given
        Item item = new Album();
        item.setName("앨범");
        item.setPrice(30000);
        item.setStockQuantity(10);
        //when
        item.addStock(10);
        //then
        int stockQuantity = item.getStockQuantity();
        assertThat(stockQuantity).isEqualTo(20);
    }

    @Test
    public void 아이템수량감소성공() throws Exception {
        //given
        Item item = new Album();
        item.setName("앨범");
        item.setPrice(30000);
        item.setStockQuantity(10);
        //when
        item.removeStock(5);
        //then
        int stockQuantity = item.getStockQuantity();
        assertThat(stockQuantity).isEqualTo(5);
    }

    @Test
    public void 아이템수량감소실패() throws Exception {
        //given
        Item item = new Album();
        item.setName("앨범");
        item.setPrice(30000);
        item.setStockQuantity(10);
        //when
        assertThrows(NotEnoughStockException.class, ()->
                item.removeStock(15));
        //then
    }
}