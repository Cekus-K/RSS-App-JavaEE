package pl.cekus.user;

public class UserService {

    private UserRepository repository;

    public UserService() {
        this(new UserRepository());
    }

    UserService(UserRepository repository) {
        this.repository = repository;
    }

    User addUser(User newUser) {
        for (User user : repository.findAll()) {
            if (newUser.getEmail().equals(user.getEmail())) {
                return user;
            }
        }
        return repository.addUser(newUser);
    }
}



