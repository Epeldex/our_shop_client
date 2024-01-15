package logic.interfaces;

import java.time.LocalDate;


public interface AdminManager {

    /**
     * Modifies the last access of a specific {@link Admin}.
     *
     * @param id Id of the {@link Admin} whose password is to be changed.
     * @return A List of {@link Admin} objects.
     * @param date New date of the {@link Admin}.
     * @throws UpdateException
     */
    public void updateLastAccess(Integer id, LocalDate date) throws UpdateException;

    /**
     * Checks if there's any Admin with this credentials.
     *
     * @param Adminname The {@link Admin} object's Adminname.
     * @param password The {@link Admin} object's password
     * @return The {@link Admin} object containing the {@link Admin} data.
     * @throws UpdateException If there is any Exception the process.
     */
    public Admin signIn(String username, String password) throws ReadException;

    /**
     * Creates an Admin and stores it in the underlying application storage.
     *
     * @param admin The {@link Admin} object containing the admin data.
     * @throws CreateException If there is any Exception the process.
     */
    public void createAdmin(Admin admin) throws CreateException;

    /**
     * Updates an admin's data in the underlying application storage.
     *
     * @param admin The {@link Admin} object containing the admin data.
     * @throws UpdateException If there is any Exception the process.
     */
    public void updateAdmin(Admin admin) throws UpdateException;

    /**
     * Deletes an Admin's data in the underlying application storage.
     *
     * @param admin The {@link Admin} object containing the Admadminin data.
     * @throws DeleteException If there is any Exception the process.
     */
    public void removeAdmin(Integer id) throws DeleteException;
}
