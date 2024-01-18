package logic.interfaces;

import java.util.List;
import logic.exceptions.LogicException;
import transfer.objects.User;

import exceptions.*;

/**
 * Interface for managing {@link User} operations. This interface defines
 * methods for finding users by ID or username, retrieving active users,
 * retrieving a list of all users, updating passwords, signing in, creating,
 * updating, and removing users in the underlying application storage.
 */

public interface UserManager {

    /**
     * Finds a {@link User} by its id.
     *
     * @param id The id of the user to be found.
     * @return The {@link User} object containing user data.
     * @throws LogicException If there is any LogicException during the process.
     */
    public User findUserById(Integer id) throws LogicException;

    /**
     * Finds a {@link User} by its username.
     *
     * @param username The username of the user to be found.
     * @return The {@link User} object containing user data.
     * @throws LogicException If there is any LogicException during the process.
     */
    public User findUserByUsername(String username) throws LogicException;

    /**
     * Finds a {@link User} by its state (active).
     *
     * @param active The state of the user as a boolean.
     * @return The {@link User} object containing user data.
     * @throws LogicException If there is any LogicException during the process.
     */
    public List<User> findUserByActive(Boolean active) throws LogicException;

    /**
     * Finds a List of {@link User} objects containing data for all users in the
     * application data storage.
     *
     * @return A List of {@link User} objects.
     * @throws LogicException If there is any LogicException during the process.
     */
    public List<User> findAllUsers() throws LogicException;

    /**
     * Modifies the password of a specific {@link User}.
     *
     * @param user {@link User} object with the updatedPassword.
     * @throws LogicException If there is any LogicException during the process.
     */

    public void updatePassword(User user) throws LogicException;

    /**
     * Checks if there's any user with these credentials.
     *
     * @param user The {@link User} with the signIn information.
     * @throws LogicException If there is any LogicException during the process.
     * @return the transfer.objects.User
     */
    public User signIn(User user) throws LogicException;

    /**
     * Creates a User and stores it in the underlying application storage.
     *
     * @param user The {@link User} object containing the user data.
     * @throws LogicException If there is any LogicException during the process.
     */
    public void createUser(User user) throws LogicException;

    /**
     * Updates a user's data in the underlying application storage.
     *
     * @param user The {@link User} object containing the user data.
     * @throws LogicException If there is any LogicException during the process.
     */
    public void updateUser(User user) throws LogicException;

    /**
     * Deletes a user's data in the underlying application storage.
     *
     * @param id The ID of the {@link User} object to be removed.
     * @throws LogicException If there is any LogicException during the process.
     */
    public void removeUser(Integer id) throws LogicException;
}
