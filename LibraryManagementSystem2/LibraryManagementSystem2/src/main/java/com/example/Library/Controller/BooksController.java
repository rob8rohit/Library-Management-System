package com.example.Library.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Library.Service.BooksService;

import tools.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/books")
public class BooksController {

    @Autowired
    private BooksService booksService;

    @PostMapping("/add")
    public JsonNode createOrUpdateBooks(@RequestBody JsonNode jsonNode){
        return booksService.createOrUpdateBooks(jsonNode);
    }

    @GetMapping("/getAll")
    public JsonNode getAllBooks(){
        return booksService.getAllBooks();
    }

    @GetMapping("/{booksId}")
    public JsonNode getBooksById(@PathVariable Long booksId){
        return booksService.getBooksById(booksId);
    }

    @DeleteMapping("/{booksId}")
    public JsonNode deleteBooksById(@PathVariable Long booksId){
        return booksService.deleteBooksById(booksId);
    }

    @GetMapping("/search/author")
    public JsonNode searchByAuthor(@RequestParam String author){
        return booksService.searchByAuthor(author);
    }

    @GetMapping("/search/category")
    public JsonNode searchByCategory(@RequestParam String category){
        return booksService.searchByCategory(category);
    }

    @GetMapping("/search/title")
    public JsonNode searchByTitle(@RequestParam String title){
        return booksService.searchByTitle(title);
    }
}
