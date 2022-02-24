package com.example.FirstOwnApp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

@Entity
@Table(name = "restauracja")
public class Restauracja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "post_code")
    private String post_code;

    @Column(name = "street")
    private String street;

    @Column(name = "local_number")
    private String local_number;

    @Column(name = "web_page")
    private String web_page;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    private final String url = "jdbc:postgresql://localhost:5454/restauracje";
    private final String user = "postgres";
    private final String password = "Bulka191";

    private ArrayList<Restauracja> restauracje = new ArrayList<>();

    public Restauracja(){}

    public Restauracja(Long id, String name, String city, String post_code, String street, String local_number, String web_page, String phone, String email) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.post_code = post_code;
        this.street = street;
        this.local_number = local_number;
        this.web_page = web_page;
        this.phone = phone;
        this.email = email;
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void insertRestauracja(Restauracja restauracja,long id) {
        String SQL = "INSERT INTO restauracja(id,name,city,post_code,street,local_number,web_page,phone,email)"
                + "VALUES(?,?,?,?,?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setBigDecimal(1, BigDecimal.valueOf(restauracja.getId()));
            pstmt.setString(2, restauracja.getName());
            pstmt.setString(3, restauracja.getCity());
            pstmt.setString(4, restauracja.getPost_code());
            pstmt.setString(5, restauracja.getStreet());
            pstmt.setString(6, restauracja.getLocal_number());
            pstmt.setString(7, restauracja.getWeb_page());
            pstmt.setString(8, restauracja.getPhone());
            pstmt.setString(9, restauracja.getEmail());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void saveRestauracjeToList(){
        restauracje.clear();
        Restauracja restauracja = new Restauracja();
        for(int i = 0; i < getRestauracjaCount(restauracja) + 1; i++){
            restauracje.add(i,getRestauracja(i));
        }
        restauracje.remove(0);
    }

    public void addNextRestauracja(long id){
        restauracje.add((int) id,getRestauracja(id));
    }

    public String displayRestauracje(){
        saveRestauracjeToList();
        return restauracje.toString();
    }

    public void updateRestauracja(Restauracja restauracja,long id) {
        String SQL = "UPDATE restauracja SET name = ?, city = ?, post_code = ?, street = ?, local_number = ?, web_page = ?, phone = ?, email = ?"
                + "WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, restauracja.getName());
            pstmt.setString(2, restauracja.getCity());
            pstmt.setString(3, restauracja.getPost_code());
            pstmt.setString(4, restauracja.getStreet());
            pstmt.setString(5, restauracja.getLocal_number());
            pstmt.setString(6, restauracja.getWeb_page());
            pstmt.setString(7, restauracja.getPhone());
            pstmt.setString(8, restauracja.getEmail());
            pstmt.setBigDecimal(9, BigDecimal.valueOf(restauracja.getId()));

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String getRestauracja(Restauracja restauracja,Long id) {
        String SQL = "SELECT id,name,city,post_code,street,local_number,web_page,phone,email" +
                " FROM restauracja " +
                "WHERE id = ?";
        String str = "";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setBigDecimal(1, BigDecimal.valueOf(id));
            ResultSet rs = pstmt.executeQuery();
            str = displayRestauracja(rs);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return str;
    }

    public Restauracja getRestauracja(long id) {
        String SQL = "SELECT id,name,city,post_code,street,local_number,web_page,phone,email" +
                " FROM restauracja " +
                "WHERE id = ?";

        Restauracja restauracja = null;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setBigDecimal(1, BigDecimal.valueOf(id));
            ResultSet rs = pstmt.executeQuery();
            restauracja = getData(rs);
            restauracja.toString();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return restauracja;
    }

    public Restauracja getData(ResultSet rs) throws SQLException {
        long id = 0;
        String name = "", city = "", post_code = "", street = "", local_number = "", web_page = "", phone = "", email = "";
        while (rs.next()) {
            id = Long.parseLong(String.valueOf(rs.getString("id")));
            name = rs.getString("name");
            city = rs.getString("city");
            post_code = rs.getString("post_code");
            street = rs.getString("street");
            local_number = rs.getString("local_number");
            web_page = rs.getString("web_page");
            phone = rs.getString("phone");
            email = rs.getString("email");
        }
        System.out.println(id + "," + name + "," + city + "," + post_code + "," + street + "," + local_number + "," + web_page + "," + phone + "," + email);
        Restauracja restauracja = new Restauracja(id, name, city, post_code, street, local_number, web_page, phone, email);
        return restauracja;
    }

    public String getRestauracjaAll(Restauracja restauracja) {
        String SQL = "SELECT id,name,city,post_code,street,local_number,web_page,phone,email FROM restauracja";
        String str = "";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            // display actor information
            str = displayRestauracje(rs);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return str;
    }

    private String displayRestauracja(ResultSet rs) throws SQLException {
        System.out.println("Restauracje : ");
        String str = "{\nRestauracje\n";
        while (rs.next()) {
            System.out.println(
                    "ID : " + rs.getString("id") + "\t" +
                            "Name :" + rs.getString("name") + "\t" +
                            "City : " + rs.getString("city") + "\t" +
                            "Post Code : " + rs.getString("post_code") + "\t" +
                            "Street : " + rs.getString("street") + "\t" +
                            "Local Number : " + rs.getString("local_number") + "\t" +
                            "Web Page : " + rs.getString("web_page") + "\t" +
                            "Phone : " + rs.getString("phone") + "\t" +
                            "Email : " + rs.getString("email")
            );
            str += "\n\t{\n\t\t" +
                    "id             :" + rs.getString("id") + "\n\t\t" +
                    "name           :" + rs.getString("name") + "\n\t\t" +
                    "city           :" + rs.getString("city") + "\n\t\t" +
                    "post_code      :" + rs.getString("post_code") + "\n\t\t" +
                    "street         :" + rs.getString("street") + "\n\t\t" +
                    "local_number   :" + rs.getString("local_number") + "\n\t\t" +
                    "web_page       :" + rs.getString("web_page") + "\n\t\t" +
                    "phone          :" + rs.getString("phone") + "\n\t\t" +
                    "email          :" + rs.getString("email") + "\n\t}\n";
        }
        str +="}";
        return str;
    }

    private String displayRestauracje(ResultSet rs) throws SQLException {
        System.out.println("Restauracje : ");
        String str = "{\nRestauracje";
        while (rs.next()) {
            System.out.println(
                    "ID : " + rs.getString("id") + "\t" +
                    "Name :" + rs.getString("name") + "\t" +
                    "City : " + rs.getString("city") + "\t" +
                    "Post Code : " + rs.getString("post_code") + "\t" +
                    "Street : " + rs.getString("street") + "\t" +
                    "Local Number : " + rs.getString("local_number") + "\t" +
                    "Web Page : " + rs.getString("web_page") + "\t" +
                    "Phone : " + rs.getString("phone") + "\t" +
                    "Email : " + rs.getString("email")
            );
            str += "\n\t{\n\t\t" +
                    "id             :" + rs.getString("id") + "\n\t\t" +
                    "name           :" + rs.getString("name") + "\n\t\t" +
                    "city           :" + rs.getString("city") + "\n\t\t" +
                    "post_code      :" + rs.getString("post_code") + "\n\t\t" +
                    "street         :" + rs.getString("street") + "\n\t\t" +
                    "local_number   :" + rs.getString("local_number") + "\n\t\t" +
                    "web_page       :" + rs.getString("web_page") + "\n\t\t" +
                    "phone          :" + rs.getString("phone") + "\n\t\t" +
                    "email          :" + rs.getString("email") + "\n\t}\n";
        }
        str +="}";
        return str;
    }

    public long getRestauracjaCount(Restauracja restauracja) {
        String SQL = "SELECT count(*) FROM restauracja";

        long value = 0;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {
            rs.next();
            value = rs.getInt(1);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return value;
    }

    public void deleteRestauracja(long id) {
        String SQL = "DELETE FROM restauracja" +
                " WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setBigDecimal(1, BigDecimal.valueOf(id));

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLocal_number() {
        return local_number;
    }

    public void setLocal_number(String local_number) {
        this.local_number = local_number;
    }

    public String getWeb_page() {
        return web_page;
    }

    public void setWeb_page(String web_page) {
        this.web_page = web_page;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        return "Restauracja{" +
                "  \n\tid           : " + this.id +
                ", \n\tname         : " + this.name + '\'' +
                ", \n\tcity         : " + this.city + '\'' +
                ", \n\tpost_code    : " + this.post_code + '\'' +
                ", \n\tstreet       : " + this.street + '\'' +
                ", \n\tlocal_number : " + this.local_number + '\'' +
                ", \n\tweb_page     : " + this.web_page + '\'' +
                ", \n\tphone        : " + this.phone + '\'' +
                ", \n\temail        : " + this.email + '\'' + "\n}\n\n";
    }

    public void print(){
        System.out.println("Restauracja " + this.id + " : ");
        System.out.println("ID : " + this.id);
        System.out.println("Name : " + this.name);
        System.out.println("City : " + this.city);
        System.out.println("Post Code : " + this.post_code);
        System.out.println("Street : " + this.street);
        System.out.println("Local Number : " + this.local_number);
        System.out.println("Web Page : " + this.web_page);
        System.out.println("Phone : " + this.phone);
        System.out.println("Email : " + this.email);
    }
}
