package com.revature.springpep.service;

import com.revature.springpep.exceptions.AccountException;
import com.revature.springpep.exceptions.MessageException;
import com.revature.springpep.model.Account;
import com.revature.springpep.model.Message;
import com.revature.springpep.repository.AccountRepository;
import com.revature.springpep.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message, Integer postedByAccountId) {
        if (message.getMessageText() == null || message.getMessageText().trim().isEmpty() || message.getMessageText().length() > 255) {
            throw new MessageException("Message text must not be empty and must be under 255 characters");
        }

        Account postedByAccount = accountRepository.findById(postedByAccountId)
                .orElseThrow(() -> new AccountException("No account found with the provided ID"));

        message.setPostedBy(postedByAccount);
        message.setTimePostedEpoch(Instant.ofEpochSecond(System.currentTimeMillis()));

        return messageRepository.save(message);
    }

    public List<Message> findAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> findMessageById(Integer messageId) {
        return messageRepository.findById(messageId);
    }

    public Optional<Message> deleteMessageById(Integer messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        message.ifPresent(messageRepository::delete);
        return message;
    }

    public Optional<Message> updateMessageTextById(Integer messageId, String newText) {
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()) {
            if (newText == null || newText.trim().isEmpty() || newText.length() > 255) {
                throw new MessageException("New message text must not be empty and must be under 255 characters");
            }
            Message updatedMessage = message.get();
            updatedMessage.setMessageText(newText);
            messageRepository.save(updatedMessage);
        }
        return message;
    }

    public List<Message> findMessagesByPostedBy(Integer accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        return account.map(messageRepository::findByPostedBy).orElse(Collections.emptyList());
    }

}
