package com.example.Library.Service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Library.Entity.Books;
import com.example.Library.Repository.BooksRepository;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Optional;

@Service
public class BooksService {

    @Autowired
    private BooksRepository booksRepository;

    final private ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode createOrUpdateBooks(JsonNode jsonNode) {

        ObjectNode response = objectMapper.createObjectNode();

        Books books;

        if(jsonNode.has("booksId")){
            Long booksId = jsonNode.get("booksId").asLong();
            Optional<Books> booksOptional = booksRepository.findById(booksId);

            if(booksOptional.isEmpty()){
                response.put("status","error");
                response.put("message","Invalid book Id");

                return response;
            }else{
                books = booksOptional.get();
            }
        }else{
            books = new Books();
        }
        books.setAuthor(jsonNode.has("author")?jsonNode.get("author").asText():null);
        books.setCategory(jsonNode.has("category")?jsonNode.get("category").asText():null);
        books.setTitle(jsonNode.has("title")?jsonNode.get("title").asText():null);
        books.setPublisher(jsonNode.has("publisher")?jsonNode.get("publisher").asText():null);
        books.setTotalCopies(jsonNode.has("totalCopies")?jsonNode.get("totalCopies").asInt():null);
        books.setAvailableCopies(jsonNode.has("availableCopies")?jsonNode.get("availableCopies").asInt():null);

        booksRepository.save(books);

        response.put("status","success");
        response.put("message","books data created or updated successfully");

        return response;
    }

    public JsonNode getAllBooks() {

        List<Books> booksList= booksRepository.findAll();
        return objectMapper.valueToTree(booksList);
    }

    public JsonNode getBooksById(Long booksId) {

        ObjectNode response = objectMapper.createObjectNode();
        Optional<Books>optionalBooks = booksRepository.findById(booksId);
        if(optionalBooks.isEmpty()){
            response.put("status","error");
            response.put("message","No books available  for given id: "+booksId);
            return response;
        }else{
            Books books = optionalBooks.get();
            return objectMapper.valueToTree(books);
        }
    }

    public JsonNode deleteBooksById(Long booksId) {
        ObjectNode response = objectMapper.createObjectNode();

        Optional<Books>optionalBooks = booksRepository.findById(booksId);

        if(optionalBooks.isPresent()){
            Books books = optionalBooks.get();
            booksRepository.delete(books);

            response.put("status","success");
            response.put("message","Data deleted successfully");
        }else{
            response.put("status","error");
            response.put("message","Books is not available with given id");
        }
        return response;
    }

    public JsonNode searchByAuthor(String author) {

        ObjectNode response = objectMapper.createObjectNode();

        List<Books>books = booksRepository.findByAuthorContainingIgnoreCase(author);

        if(books.isEmpty()){
            response.put("status","error");
            response.put("message","No books found for the author: "+author);
        }else {
            response.put("status","success");
            response.set("books", objectMapper.valueToTree(books));


        }

        return response;
    }

    public JsonNode searchByCategory(String category) {

        ObjectNode response = objectMapper.createObjectNode();

        List<Books>books = booksRepository.findByCategoryContainingIgnoreCase(category);

        if(books.isEmpty()){
            response.put("status","error");
            response.put("message","No books found for this category: "+category);
        }else {
            response.put("status","success");
            response.set("books", objectMapper.valueToTree(books));


        }

        return response;
    }

    public JsonNode searchByTitle(String title) {

        ObjectNode response = objectMapper.createObjectNode();

        List<Books>books = booksRepository.findByTitleContainingIgnoreCase(title);

        if(books.isEmpty()){
            response.put("status","error");
            response.put("message","No books found for this title: "+title);
        }else {
            response.put("status","success");
            response.set("books", objectMapper.valueToTree(books));



        }

        return response;
    }
}
