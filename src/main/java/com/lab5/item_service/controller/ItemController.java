package com.lab5.item_service.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lab5.item_service.model.Item;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final List<Item> items = new ArrayList<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public ItemController() {
        items.add(new Item(idGen.getAndIncrement(), "Headphones", 49.99));
        items.add(new Item(idGen.getAndIncrement(), "Mouse", 19.99));
    }

    // // Simple in-memory list (no database needed)
    // private List<String> items = new ArrayList<>(List.of("Book", "Laptop", "Phone"));
    
    // @GetMapping
    // public List<String> getItems() {
    //     return items;
    // }

    @GetMapping
    public List<Item> getAll() {
        return items;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item create(@RequestBody Item item) {
        item.setId(idGen.getAndIncrement());
        items.add(item);
        return item;
    }

    // @PostMapping
    // public ResponseEntity<String> addItem(@RequestBody String item) {
    //     items.add(item);
    //     return ResponseEntity.status(HttpStatus.CREATED).body("Item added: " + item);
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<String> getItem(@PathVariable int id) {
    //     if (id < 0 || id >= items.size())
    //         return ResponseEntity.notFound().build();
    //     return ResponseEntity.ok(items.get(id));
    // }

    @GetMapping("/{id}")
    public Item getById(@PathVariable Long id) {
        return items.stream()
                .filter(i -> Objects.equals(i.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Item not found: " + id));
    }
}