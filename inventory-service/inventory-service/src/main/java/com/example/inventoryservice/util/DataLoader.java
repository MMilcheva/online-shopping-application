package com.example.inventoryservice.util;


import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final InventoryRepository inventoryRepository;

    @Override
    public void run(String... args) throws Exception {
        Inventory inventory = new Inventory();
        inventory.setSkuCode("iphone_13");
        inventory.setQuantity(100);

        Inventory inventory1 = new Inventory();
        inventory1.setSkuCode("iphone_13_red");
        inventory1.setQuantity(0);

        inventoryRepository.save(inventory);
        inventoryRepository.save(inventory1);
    }
}


//    @Bean
//    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
//        return args -> {
//            Inventory inventory = new Inventory();
//            inventory.setSkuCode("iphone_13");
//            inventory.setQuantity(100);
//
//            Inventory inventory1 = new Inventory();
//            inventory.setSkuCode("iphone_13_red");
//            inventory.setQuantity(0);
//
//            inventoryRepository.save(inventory);
//            inventoryRepository.save(inventory1);
//        };
//    }
