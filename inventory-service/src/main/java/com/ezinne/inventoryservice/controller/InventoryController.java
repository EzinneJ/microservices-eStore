package com.ezinne.inventoryservice.controller;

import com.ezinne.inventoryservice.model.InventoryResponse;
import com.ezinne.inventoryservice.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@AllArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{skuCode}")
    public List<InventoryResponse> isInStock(@PathVariable List<String> skuCode) {
       return inventoryService.isInStock(skuCode);
    }
}
