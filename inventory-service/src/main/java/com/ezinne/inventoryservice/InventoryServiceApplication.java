package com.ezinne.inventoryservice;

import com.ezinne.inventoryservice.model.Inventory;
import com.ezinne.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory utaba1 = new Inventory();
			utaba1.setSkuCode("Utaba_1");
			utaba1.setQuantity(0);
			inventoryRepository.save(utaba1);

			Inventory utaba2 = new Inventory();
			utaba2.setSkuCode("Utaba_2");
			utaba2.setQuantity(0);
			inventoryRepository.save(utaba2);
		};
	}

}
