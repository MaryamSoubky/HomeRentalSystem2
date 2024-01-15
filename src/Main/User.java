package Main;
import java.io.Serializable;

public class User implements Serializable{
//    static int userID;
//    private int nextID = 0;
    private String email;
    private String password;
    private Type type;
    
    // Constructor
    public User(String email, String password) {
		this.email = email;
		this.password = password;
	}
    
    // Setter and Getters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
    public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}