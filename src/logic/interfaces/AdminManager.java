package logic.interfaces;

import exceptions.*;

import logic.exceptions.LogicException;
import transfer.objects.Admin;

public interface AdminManager {

    /**
     * Checks if there's any Admin with this credentials.
     *
     * @param Adminname The {@link Admin} object's Adminname.
     * @param password The {@link Admin} object's password
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
