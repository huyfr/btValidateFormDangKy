package service;

import model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    public Page<User> findAll(Pageable pageable);

    public User findById(Integer id);

    public void save(User user);

    public void  remove(Integer id);
}
