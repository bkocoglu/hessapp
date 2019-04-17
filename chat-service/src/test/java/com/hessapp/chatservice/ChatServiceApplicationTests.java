package com.hessapp.chatservice;

import com.hessapp.chatservice.dao.LastSeen;
import com.hessapp.chatservice.dao.Message;
import com.hessapp.chatservice.repositoriy.LastSeenRepository;
import com.hessapp.chatservice.repositoriy.MessageRepository;
import com.hessapp.chatservice.service.ChatService;
import com.hessapp.chatservice.service.GetMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatServiceApplicationTests {

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	LastSeenRepository lastSeenRepository;

	@Autowired
	GetMessageService messageService;

	@Autowired
	ChatService chatService;

	@Test
	public void contextLoads() {
		/*
		Message message1 = new Message(20, "@hs-admin", "deneme", LocalDateTime.of(2018,11,14,22,50));
		Message message2 = new Message(20, "@hs-admin", "deneme", LocalDateTime.of(2018,11,14,22,15));
		Message message3 = new Message(20, "@hs-admin", "deneme", LocalDateTime.of(2018,11,14,22,23));
		Message message4 = new Message(20, "@hs-admin", "deneme", LocalDateTime.of(2018,11,14,22,13));
		Message message5 = new Message(20, "@hs-admin", "deneme", LocalDateTime.of(2018,11,14,22,42));
		Message message6 = new Message(20, "@hs-admin", "deneme", LocalDateTime.of(2018,11,14,22,5));
		Message message7 = new Message(20, "@hs-admin", "deneme", LocalDateTime.of(2018,11,14,22,1));
		Message message8 = new Message(20, "@hs-admin", "deneme", LocalDateTime.of(2018,11,14,22,16));
		Message message9 = new Message(20, "@hs-admin", "deneme", LocalDateTime.of(2018,11,14,22,32));

		messageRepository.save(message1);
		messageRepository.save(message2);
		messageRepository.save(message3);
		messageRepository.save(message4);
		messageRepository.save(message5);
		messageRepository.save(message6);
		messageRepository.save(message7);
		messageRepository.save(message8);
		messageRepository.save(message9);
		*/

		//List<Message> messageList = messageRepository.findByGroupIdOrderBySendDate(20);

		List<Message> messageList = messageRepository.findByGroupIdOrderBySendDateDesc(21);

		System.out.println(messageList);

		Iterator<Message> messageIterator = messageList.iterator();

		while (messageIterator.hasNext()){
			Message msg = messageIterator.next();
			System.out.println(msg.toString());
		}
	}

	@Test
	public void getLastSeen() {
		LastSeen lastSeen = new LastSeen();
		lastSeen.setNickname("@hs-admin");
		lastSeen.setLastSeen(LocalDateTime.of(2018, 11, 16, 01, 40));

		lastSeenRepository.save(lastSeen);

		System.out.println(lastSeenRepository.findByNickname("@hs-admin1"));
	}

	@Test
	public void unaspiringCount() {
		Message message1 = new Message(20, "@hs-admin", "deneme", LocalDateTime.of(2018,11,14,22,50));
		Message message2 = new Message(20, "@hs-admin", "deneme", LocalDateTime.of(2018,11,14,22,15));
		Message message3 = new Message(20, "@hs-admin", "deneme", LocalDateTime.of(2018,11,14,22,23));
		Message message4 = new Message(20, "@hs-admin", "deneme", LocalDateTime.of(2018,11,14,22,13));
		Message message5 = new Message(20, "@hs-admin", "deneme", LocalDateTime.of(2018,11,14,22,42));
		Message message6 = new Message(20, "@hs-admin", "deneme", LocalDateTime.of(2018,11,14,22,5));
		Message message7 = new Message(20, "@hs-admin", "deneme", LocalDateTime.of(2018,11,14,22,1));
		Message message8 = new Message(20, "@hs-admin", "deneme", LocalDateTime.of(2018,11,14,22,16));
		Message message9 = new Message(20, "@hs-admin", "deneme", LocalDateTime.of(2018,11,14,22,32));



		LastSeen lastSeen = new LastSeen();
		lastSeen.setNickname("@hs-admin");
		lastSeen.setLastSeen(LocalDateTime.of(2018, 11, 14, 22, 30));

		messageRepository.save(message1);
		messageRepository.save(message2);
		messageRepository.save(message3);
		messageRepository.save(message4);
		messageRepository.save(message5);
		messageRepository.save(message6);
		messageRepository.save(message7);
		messageRepository.save(message8);
		messageRepository.save(message9);
		lastSeenRepository.save(lastSeen);

		List<Message> messages = messageRepository.findByGroupIdOrderBySendDate(20);

		Iterator<Message> messageIterator = messages.iterator();

		while (messageIterator.hasNext()){
			Message msg = messageIterator.next();
			System.out.println(msg.toString());
		}


		List<LastSeen> lastSeens = lastSeenRepository.findByNickname("@hs-admin");
		System.out.println(lastSeens);
		System.out.println(lastSeens.get(0).toString());

		System.out.println(messageService.calculateUnaspiringMessage(messages, lastSeens.get(0).getLastSeen()));

		lastSeenRepository.deleteAll();
		messageRepository.deleteAll();

	}

	@Test
	public void lastSeenUpdate() {
		LastSeen lastSeen = new LastSeen();
		lastSeen.setNickname("@hs-admin");
		lastSeen.setLastSeen(LocalDateTime.of(2018, 11, 16, 01, 40));

		lastSeenRepository.save(lastSeen);

		List<LastSeen> lastSeens = lastSeenRepository.findByNickname("@hs-admin");

		LastSeen lst = lastSeens.get(0);

		lst.setLastSeen(LocalDateTime.of(2020, 11, 16, 01, 40));

		lastSeenRepository.save(lst);
	}

	@Test
	public void insertMessage() {
		Message message = new Message();

		message.setFrom("@hs-ojj50b");
		message.setSendDate(LocalDateTime.now().plusHours(3));
		message.setGroupId(1037);
		message.setBody("Today denemesi son !");

		messageRepository.save(message);
	}

	@Test
	public void deleteGroupMessage() {
		chatService.deleteAllMessage(1037);
	}
}
