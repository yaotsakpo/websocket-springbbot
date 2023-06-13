package springbootwebsocket.controller;

import springbootwebsocket.contract.ConnectionResponse;
import springbootwebsocket.contract.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageController {

	List<String> ConnectedUsersList = new ArrayList<>();

	@MessageMapping("/conversation/{secretID}")
	@SendTo("/topic/conversation/{secretID}")
	public String conversation(Message message, @DestinationVariable String secretID) throws Exception {
		return message.toString() + "@ <b>"+getCurrentLocalDateTimeString() +"</b>";
	}

	@MessageMapping("/connection/{secretID}")
	@SendTo("/topic/connectedUsersUpdate/{secretID}")
	public ConnectionResponse connectedUsersUpdate(String connectedUser, @DestinationVariable String secretID) throws Exception {
		ConnectedUsersList.add(connectedUser);
		return new ConnectionResponse("User "+ connectedUser +" connected to your space", ConnectedUsersList);
	}

	@MessageMapping("/disconnection/{secretID}")
	@SendTo("/topic/connectedUsersUpdate/{secretID}")
	public ConnectionResponse disconnectUsersUpdate(String connectedUser, @DestinationVariable String secretID) throws Exception {
		ConnectedUsersList.remove(connectedUser);
		return new ConnectionResponse("User "+ connectedUser +" disconnected from your space", ConnectedUsersList);
	}


	public String getCurrentLocalDateTimeString() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
		return now.format(formatter);
	}

}
