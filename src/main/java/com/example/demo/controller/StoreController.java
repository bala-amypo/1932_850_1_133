package com.example.demo.controller;

import com.example.demo.entity.Store;
import com.example.demo.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController
// @RequestMapping("/api/stores")
// public class StoreController {

//     private final StoreService service;

//     public StoreController(StoreService service) {
//         this.service = service;
//     }

//     @PostMapping
//     @ResponseStatus(HttpStatus.CREATED)
//     public Store create(@RequestBody Store store) {
//         return service.createStore(store);
//     }

//     @GetMapping("/{id}")
//     public Store get(@PathVariable Long id) {
//         return service.getStoreById(id);
//     }

//     @GetMapping
//     public List<Store> getAll() {
//         return service.getAllStores();
//     }

//     @PutMapping("/{id}")
//     public Store update(@PathVariable Long id, @RequestBody Store store) {
//         return service.updateStore(id, store);
//     }

//     @DeleteMapping("/{id}")
//     @ResponseStatus(HttpStatus.NO_CONTENT)
//     public void deactivate(@PathVariable Long id) {
//         service.deactivateStore(id);
//     }
// }

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService service;

    public StoreController(StoreService service) {
        this.service = service;
    }

    @PostMapping
    public Store create(@RequestBody Store store) {
        return service.createStore(store); // returns 200 OK
    }

    @GetMapping("/{id}")
    public Store get(@PathVariable Long id) {
        return service.getStoreById(id);
    }

    @GetMapping
    public List<Store> getAll() {
        return service.getAllStores();
    }

    @PutMapping("/{id}")
    public Store update(@PathVariable Long id, @RequestBody Store store) {
        return service.updateStore(id, store);
    }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable Long id) {
        service.deactivateStore(id);
    }
}
