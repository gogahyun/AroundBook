package com.ggh_dev.AroundBook.api;

import com.ggh_dev.AroundBook.domain.item.Item;
import com.ggh_dev.AroundBook.domain.item.ItemImage;
import com.ggh_dev.AroundBook.service.ItemImageService;
import com.ggh_dev.AroundBook.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/itemImage")
@Slf4j
public class ItemImageApiController {
    private final ItemService itemService;
    private final ItemImageService itemImageService;

    @GetMapping
    public List getItemImage(){

        List<Item> items = itemService.find4Items();
        List<ItemImage> images = itemImageService.findItemListImages(items);

        List<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
        for(int i=0;i<4;i++){
            HashMap<String, String> map = new HashMap<>();
            map.put("id",Long.toString(items.get(i).getId()));
            map.put("title",items.get(i).getTitle());
            map.put("price",Integer.toString(items.get(i).getPrice()));
            map.put("image",images.get(i).getStoreFileName());

            myList.add(map);
        }

        return myList;
    }
}
