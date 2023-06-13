package springbootwebsocket.contract;

import java.util.List;

public class ConnectionResponse {

    private String serverUpdate;

    private List<String> connectionList;

    public ConnectionResponse(String serverUpdate, List<String> connectionList) {
        this.serverUpdate = serverUpdate;
        this.connectionList = connectionList;
    }

    public ConnectionResponse() {
    }

    public String getServerUpdate() {
        return serverUpdate;
    }

    public void setServerUpdate(String serverUpdate) {
        this.serverUpdate = serverUpdate;
    }

    public List<String> getConnectionList() {
        return connectionList;
    }

    public void setConnectionList(List<String> connectionList) {
        this.connectionList = connectionList;
    }
}
