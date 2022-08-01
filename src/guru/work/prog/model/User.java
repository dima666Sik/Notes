package guru.work.prog.model;

public class User {
    private String name;
    private String login;
    private StringBuilder password;
    private long id_note;

    public User(String name, String login, StringBuilder password, long id_note) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.id_note = id_note;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public StringBuilder getPassword() {
        return new StringBuilder(password);
    }

    public void clearPassword(){
        this.password = null;
    }

    public long getId_note() {
        return id_note;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password=" + password +
                ", id_note=" + id_note +
                '}';
    }
}
