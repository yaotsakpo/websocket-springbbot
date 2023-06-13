package springbootwebsocket.contract;

public class Message {

	private String userConnectedID;

	private String message;

	public Message() {
	}

	public Message(String message, String userConnectedID) {
		this.message = message;
		this.userConnectedID = userConnectedID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserConnectedID() {
		return userConnectedID;
	}

	public void setUserConnectedID(String userConnectedID) {
		this.userConnectedID = userConnectedID;
	}

	@Override
	public String toString() {
		return "user: <b> " + userConnectedID + " </b> | message= <b>" + message + " </b>";
	}
}
