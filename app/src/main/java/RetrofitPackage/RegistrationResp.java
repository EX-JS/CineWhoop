
package RetrofitPackage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RegistrationResp implements Serializable {


    private String reply;

    private String token;
    private String session_time;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getSession_time() {
        return session_time;
    }

    public void setSession_time(String session_time) {
        this.session_time = session_time;
    }
}
