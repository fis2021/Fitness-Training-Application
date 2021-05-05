package org.fta.Models;
import org.dizitart.no2.objects.Id;

public class ClientModel {

    @Id
    private String Role, password, username;

    public ClientModel(String Role, String password, String username){
        this.Role = Role;
        this.password = password;
        this.username = username;
    }

    public String getRole() {
        return Role;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setRole(String role) {
        Role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientModel that = (ClientModel) o;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return Role != null ? Role.equals(that.Role) : that.Role == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (Role != null ? Role.hashCode() : 0);
        return result;
    }
}
