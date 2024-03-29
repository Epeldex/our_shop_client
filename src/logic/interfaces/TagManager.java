package logic.interfaces;

import java.util.List;
import logic.exceptions.LogicException;
import transfer.objects.Tag;

/**
 * Interface for managing Tag operations. This interface defines methods for
 * updating, deleting, retrieving, and inserting tags in the underlying
 * application storage.
 *
 * @author Alexander Epelde
 */
public interface TagManager {

    /**
     * Updates a tag's information in the underlying application storage.
     *
     * @param tag The {@link Tag} object containing the details to be updated.
     * @throws LogicException If there is any exception during the update
     * process.
     */
    public void updateTag(Tag tag) throws LogicException;

    /**
     * Deletes a tag from the underlying application storage.
     *
     * @param tagId The ID of the tag to be deleted.
     * @throws LogicException If there is any exception during the delete
     * process.
     */
    public void deleteTag(Integer tagId) throws LogicException;

    /**
     * Retrieves a list of all tags from the application data storage.
     *
     * @return A List of {@link Tag} objects.
     * @throws LogicException If there is any exception during the retrieval
     * process.
     */
    public List<Tag> selectAllTags() throws LogicException;

    /**
     * Retrieves a tag by its ID from the application data storage.
     *
     * @param tagId The ID of the tag to be retrieved.
     * @return The {@link Tag} object containing tag data.
     * @throws LogicException If there is any exception during the retrieval
     * process.
     */
    public Tag selectTagById(Integer tagId) throws LogicException;

    /**
     * Inserts a new tag into the underlying application storage.
     *
     * @param tag The {@link Tag} object containing the details of the new tag.
     * @throws LogicException If there is any exception during the insertion
     * process.
     */
    public void insertTag(Tag tag) throws LogicException;

}
