package logic.interfaces;

import logic.exceptions.LogicException;
import transfer.objects.Admin;

public interface AdminManager {

    /**
     * Checks if there is any Admin with the provided credentials.
     *
     * @param admin The {@link Admin} object.
     * @throws LogicException If there is any logic exception during the sign-in process.
     * @return the admin
     */
    public Admin signIn(Admin admin) throws LogicException;

    /**
     * Creates an Admin and stores it in the underlying application storage.
     *
     * @param admin The {@link Admin} object containing the admin data.
     * @throws LogicException If there is any logic exception during the creation process.
     */
    public void createAdmin(Admin admin) throws LogicException;

    /**
     * Updates an admin's data in the underlying application storage.
     *
     * @param admin The {@link Admin} object containing the updated admin data.
     * @throws LogicException If there is any logic exception during the update process.
     */
    public void updateAdmin(Admin admin) throws LogicException;

    /**
     * Deletes an Admin's data from the underlying application storage.
     *
     * @param id    The ID of the {@link Admin} object to be removed.
     * @throws LogicException If there is any logic exception during the removal process.
     */
    public void removeAdmin(Integer id) throws LogicException;
}
