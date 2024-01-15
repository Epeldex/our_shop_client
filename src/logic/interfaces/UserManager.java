package logic.interfaces;

import java.util.List;

import entities.User;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import javax.ejb.Local;

public interface UserManager {

    /**
     * Finds a {@link User} by its id.
     *
     * @param id The id of the user to be found.
     * @return The {@link User} object containing user data.
     * @throws ReadException If there is any Exception the process.
     */
    public User findUserById(Integer id) throws ReadException;

    /**
     * Finds a {@link User} by its username.
     *
     * @param username
     * @return The {@link User} object containing user data.
     * @throws ReadException If there is any Exception the process.
     */
    public User findUserByUsername(String username) throws ReadException;

    /**
     * Finds a {@link User} by its state (active).
     *
     * @param active The state of the user as a boolean.
     * @return The {@link User} object containing user data.
     * @throws ReadException If there is any Exception the process.
     */
    public List<User> findUserByActive(Boolean active) throws ReadException;

    /**
     * Finds a List of {@link User} objects containing data for all users in the
     * application data storage.
     *
     * @return A List of {@link User} objects.
     * @throws ReadException If there is any Exception the process.
     */
    public List<User> findAllUsers() throws ReadException;

    /**
     * Modifies the password of a specific {@link User}.
     *
     * @param id Id of the {@link User} whose password is to be changed.
     * @return A List of {@link User} objects.
     * @param password New password of the {@link User}.
     * @throws UpdateException
     */
    public void updatePassword(Integer id, String password) throws UpdateException;

    /**
     * Checks if there's any user with this credentials.
     *
     * @param username The {@link User} object's username.
     * @param password The {@link User} object's password
     * @return The {@link User} object containing the {@link User} data.
     * @throws UpdateException If there is any Exception the process.
     */
    public User signIn(String username, String password) throws ReadException;

    /**
     * Creates a User and stores it in the underlying application storage.
     *
     * @param user The {@link User} object containing the user data.
     * @throws CreateException If there is any Exception the process.
     */
    public void createUser(User user) throws CreateException;

    /**
     * Updates a user's data in the underlying application storage.
     *
     * @param user The {@link User} object containing the user data.
     * @throws UpdateException If there is any Exception the process.
     */
    public void updateUser(User user) throws UpdateException;

    /**
     * Deletes a user's data in the underlying application storage.
     *
     * @param user The {@link User} object containing the user data.
     * @throws DeleteException If there is any Exception the process.
     */
    public void removeUser(Integer id) throws DeleteException;
}
