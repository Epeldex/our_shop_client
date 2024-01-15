package logic.interfaces;

import entities.Tag;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.List;
import javax.ejb.Local;

/**
 * EJB Local Interface for managing Tag entity CRUD operations.
 *
 * @author Alexander Epelde
 */
public interface TagManager {

    /**
     * Updates a tag's information in the underlying application storage.
     *
     * @param tag The {@link Tag} object containing the details to be updated.
     * @throws UpdateException If there is any exception during processing.
     */
    public void updateTag(Tag tag) throws UpdateException;

    /**
     * Deletes a tag from the underlying application storage.
     *
     * @param tagId The ID of the tag to be deleted.
     * @throws DeleteException If there is any exception during processing.
     */
    public void deleteTag(Integer tagId) throws DeleteException;

    /**
     * Retrieves a list of all tags from the application data storage.
     *
     * @return A List of {@link Tag} objects.
     * @throws ReadException If there is any exception during processing.
     */
    public List<Tag> selectAllTags() throws ReadException;

    /**
     * Retrieves a tag by its ID from the application data storage.
     *
     * @param tagId The ID of the tag to be retrieved.
     * @return The {@link Tag} object containing tag data.
     * @throws ReadException If there is any exception during processing.
     */
    public Tag selectTagById(Integer tagId) throws ReadException;

    /**
     * Inserts a new tag into the underlying application storage.
     *
     * @param tag The {@link Tag} object containing the details of the new tag.
     * @throws CreateException If there is any exception during processing.
     */
    public void insertTag(Tag tag) throws CreateException;

}
