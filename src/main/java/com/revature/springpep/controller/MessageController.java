package com.revature.springpep.controller;

import com.revature.springpep.exceptions.AccountException;
import com.revature.springpep.exceptions.MessageException;
import com.revature.springpep.model.Message;
import com.revature.springpep.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @ExceptionHandler({AccountException.class, MessageException.class})
    public ResponseEntity<Object> handleCustomExceptions(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

        @PostMapping
        public ResponseEntity<Message> createMessage(@RequestBody Message message, @RequestParam Integer postedByAccountId) {
            Optional<Message> messageResponse = Optional.ofNullable(messageService.createMessage(message, postedByAccountId));
            if (messageResponse.isPresent()) {
                return ResponseEntity.ok(messageResponse.get());
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        @GetMapping
        public ResponseEntity<List<Message>> getAllMessages() {
            return ResponseEntity.ok(messageService.findAllMessages());
        }

        @GetMapping("/{messageId}")
        public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
            Optional<Message> messageResponse = messageService.findMessageById(messageId);
            if (messageResponse.isPresent()) {
                return ResponseEntity.ok(messageResponse.get());
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        @DeleteMapping("/{messageId}")
        public ResponseEntity<Message> deleteMessage(@PathVariable Integer messageId) {
            Optional<Message> messageResponse = messageService.deleteMessageById(messageId);
            if (messageResponse.isPresent()) {
                return ResponseEntity.ok(messageResponse.get());
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        @PatchMapping("/{messageId}")
        public ResponseEntity<Message> updateMessage(@PathVariable Integer messageId, @RequestBody String newText) {
            Optional<Message> messageResponse = messageService.updateMessageTextById(messageId, newText);
            if (messageResponse.isPresent()) {
                return ResponseEntity.ok(messageResponse.get());
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<List<Message>> getMessagesByAccount(@PathVariable Integer accountId) {
        Optional<List<Message>> messagesResponse = Optional.ofNullable(messageService.findMessagesByPostedBy(accountId));
        if (messagesResponse.isPresent()) {
            return ResponseEntity.ok(messagesResponse.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

