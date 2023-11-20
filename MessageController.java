package ru.myHome.untitled_4_13;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class MessageController {

    private Message message;

    private List<Message> messages = new ArrayList<>(Arrays.asList(
            new Message(1, "OK", "All good", LocalDateTime.of(1999, 2,3, 10, 32, 00)),
            new Message(2, "ERROR", "It is error", LocalDateTime.of(2002, 2,2, 12, 45, 00)),
            new Message(3, "HELP", "For help read the tutorial", LocalDateTime.of(2005, 4,8, 21, 30, 00)),
            new Message(4, "DAMAGE", "everything is bad, nothing works", LocalDateTime.of(1978, 6,5, 22, 00, 00))
    ));

    //получение сообщения по id
    @GetMapping("/messages/{id}")
    public Optional<Message> findMessageById(@PathVariable int id) {
        return messages.stream().filter(m -> m.getId() == id).findFirst();
    }

    //добавление нового сообщения
    @PostMapping("/messages")
    public Message addMessage(@RequestBody Message message) {
        messages.add(message);
        return message;
    }

    //удаление сообщения по id
    @DeleteMapping("/messages/{id}")
    public void deleteMessage(@PathVariable int id) {
        messages.removeIf(m -> m.getId() == id);
    }

    //обновление информации в сообщении по id
    @PutMapping("/messages/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable int id, @RequestBody Message message) {
        int index = - 1;
        for (Message m : messages) {
            if (m.getId() == id) {
                index = messages.indexOf(m);
                messages.set(index, message);
            }
        }
        return index == -1
                ? new ResponseEntity<>(addMessage(message), HttpStatus.CREATED)
                : new ResponseEntity<>(message, HttpStatus.OK);
    }

    //вывести список сообщений
    @GetMapping("/messages")
    public Iterable<Message> getMessages() {
        return messages;
    }

    @GetMapping("/message")
    public String helloMessage() {
        return "Its message!";
    }
}
