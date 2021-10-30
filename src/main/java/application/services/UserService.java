package application.services;

import application.models.ChefUser;
import application.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;

    private PasswordEncoder encoder;

    @Autowired
    public UserService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return em.createQuery("SELECT user FROM ChefUser user WHERE user.username = :name", ChefUser.class)
                .setParameter("name", username)
                .getSingleResult();
    }

    public ChefUser getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof ChefUser) {
                return (ChefUser) principal;
            }
        }

        return null;
    }

    @Transactional
    public List<ChefUser> getAllUsers() {
        return em.createQuery("SELECT user FROM ChefUser user", ChefUser.class)
                .getResultList();
    }

    //@Transactional
    public ChefUser getOneUser(String username) {
        try {
            return (ChefUser) loadUserByUsername(username);
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public boolean registerUsers() {
        try {
            String userPw = encoder.encode("user");
            String adminPw = encoder.encode("admin");

            ChefUser user = new ChefUser("user", "user", userPw, UserRole.USER);
            ChefUser admin = new ChefUser("admin", "admin", adminPw, UserRole.ADMIN);

            em.persist(user);
            em.persist(admin);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
