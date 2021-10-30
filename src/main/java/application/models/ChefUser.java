package application.models;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class ChefUser implements UserDetails {

    @Id
    private String username;

    private String nickname;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole authority;

    //private String authority;

    @CreationTimestamp
    private LocalDateTime regTime;

    private boolean isLocked;

   /* @OneToMany(mappedBy = "author")
    private List<Recipe> ownRecipes;*/

    // ha ManyToMany, akkor csak egyik oldalon lehet mappedBy
    /*@ManyToMany//(mappedBy = "likedBy")
    private List<Recipe> favoriteRecipes;*/

    public ChefUser() {
        //ownRecipes = new ArrayList<>();
        //favoriteRecipes = new ArrayList<>();
    }

    public ChefUser(String username, String nickname, String password, UserRole authority) {
        this();
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.authority = authority;
    }

    /*public ChefUser(String username, String nickname, String password, String authority, List<Recipe> ownRecipes, List<Recipe> favoriteRecipes) {
        this(username, nickname, password, authority);
        this.ownRecipes = ownRecipes;
        this.favoriteRecipes = favoriteRecipes;
    }*/

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isLocked;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isLocked;
    }

    @Override
    public boolean isEnabled() {
        return !isLocked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new ArrayList<>();

        for (UserAuth auth : authority.AUTHS) {
            list.add(new SimpleGrantedAuthority(auth.toString()));
        }

        return list;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getAuthority() {
        return authority;
    }

    public void setAuthority(UserRole authority) {
        this.authority = authority;
    }

    public LocalDateTime getRegTime() {
        return regTime;
    }

    public void setRegTime(LocalDateTime regTime) {
        this.regTime = regTime;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    /*public List<Recipe> getOwnRecipes() {
        return ownRecipes;
    }

    public void setOwnRecipes(List<Recipe> ownRecipes) {
        this.ownRecipes = ownRecipes;
    }

    public List<Recipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(List<Recipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }*/

}
