package logic.interfaces;

import ui.exceptions.SignInException;
import ui.exceptions.SignUpException;

import logic.exceptions.LogicException;
import transfer.objects.Admin;

/**
 * The {@code AdminManager} interface defines the operations that can be
 * performed on an {@link Admin} object within the context of a user management
 * system. It offers methods to sign in, create, update, and delete an admin.
 * This interface acts as a contract for implementing classes to provide the
 * necessary administrative functionalities in an application.
 *
 * Each method is designed to interact with the underlying application storage
 * and handle specific administrative tasks. The {@code signIn} method is used
 * for authenticating administrators, while {@code createAdmin},
 * {@code updateAdmin}, and {@code removeAdmin} methods are used for managing
 * admin accounts. These methods throw specific exceptions to handle scenarios
 * where the operations cannot be completed as expected, providing robust error
 * handling in the administrative process of the application.
 */
public interface AdminManager {

    /**
     * Checks if there's any Admin with this credentials.
     *
     * @return The {@link Admin} object containing the {@link Admin} data.
     * @throws SignInException If there is any Exception the process.
     */
    public Admin signIn(Admin admin) throws SignInException;

    /**
     * Creates an Admin and stores it in the underlying application storage.
     *
     * @param admin The {@link Admin} object containing the admin data.
     * @throws SignUpException If there is any exception during the creation
     * process.
     */
    public void createAdmin(Admin admin) throws SignUpException;

    /**
     * Updates an admin's data in the underlying application storage.
     *
     * @param admin The {@link Admin} object containing the updated admin data.
     * @throws LogicException If there is any logic exception during the update
     * process.
     */
    public void updateAdmin(Admin admin) throws LogicException;

    /**
     * Deletes an Admin's data from the underlying application storage.
     *
     * @param id The ID of the {@link Admin} object to be removed.
     * @throws LogicException If there is any logic exception during the removal
     * process.
     */
    public void removeAdmin(Integer id) throws LogicException;
}
