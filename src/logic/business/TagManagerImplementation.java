package logic.business;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import logic.exceptions.LogicException;
import logic.interfaces.TagManager;
import rest.TagRESTClient;
import transfer.objects.Tag;

/**
 * This class implements {@link TagManager} business logic interface using a
 * RESTful web client to access business logic in an application server.
 *
 * @author Alexander Epelde
 */
public class TagManagerImplementation implements TagManager {

    //REST tags web client
    private TagRESTClient webClient;
    private static final Logger LOGGER = Logger.getLogger("your.package.name");

    /**
     * Create a TagManagerImplementation object. It constructs a web client for
     * accessing a RESTful service that provides business logic in an
     * application server.
     */
    public TagManagerImplementation() {
        webClient = new TagRESTClient();
    }

    /**
     * Updates a tag's information in the underlying application storage.
     *
     * @param tag The {@link Tag} object containing the details to be updated.
     * @throws LogicException If there is any exception during processing.
     */
    @Override
    public void updateTag(Tag tag) throws LogicException {
        try {
            LOGGER.info("TagManager: Updating tag with ID " + tag.getTag_id() + " via REST service.");
            webClient.update(tag);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "TagManager: Exception updating tag, {0}", ex.getMessage());
            throw new LogicException("Error updating tag:\n" + ex.getMessage());
        }
    }

    /**
     * Deletes a tag from the underlying application storage.
     *
     * @param tagId The ID of the tag to be deleted.
     * @throws LogicException If there is any exception during processing.
     */
    @Override
    public void deleteTag(Integer tagId) throws LogicException {
        try {
            LOGGER.info("TagManager: Deleting tag with ID " + tagId + " via REST service.");
            webClient.delete(String.valueOf(tagId));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "TagManager: Exception deleting tag, {0}", ex.getMessage());
            throw new LogicException("Error deleting tag:\n" + ex.getMessage());
        }
    }

    /**
     * Retrieves a list of all tags from the application data storage.
     *
     * @return A List of {@link Tag} objects.
     * @throws LogicException If there is any exception during processing.
     */
    @Override
    public List<Tag> selectAllTags() throws LogicException {
        List<Tag> tags = null;
        try {
            LOGGER.info("TagManager: Retrieving all tags from REST service.");
            tags = webClient.findAll(new GenericType<List<Tag>>() {
            });
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "TagManager: Exception retrieving all tags, {0}", ex.getMessage());
            throw new LogicException("Error retrieving all tags:\n" + ex.getMessage());
        }
        return tags;
    }

    /**
     * Retrieves a tag by its ID from the application data storage.
     *
     * @param tagId The ID of the tag to be retrieved.
     * @return The {@link Tag} object containing tag data.
     * @throws LogicException If there is any exception during processing.
     */
    @Override
    public Tag selectTagById(Integer tagId) throws LogicException {
        Tag tag = null;
        try {
            LOGGER.info("TagManager: Retrieving tag with ID " + tagId + " from REST service.");
            tag = webClient.find(Tag.class, String.valueOf(tagId));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "TagManager: Exception retrieving tag by ID, {0}", ex.getMessage());
            throw new LogicException("Error retrieving tag by ID:\n" + ex.getMessage());
        }
        return tag;
    }

    /**
     * Inserts a new tag into the underlying application storage.
     *
     * @param tag The {@link Tag} object containing the details of the new tag.
     * @throws LogicException If there is any exception during processing.
     */
    @Override
    public void insertTag(Tag tag) throws LogicException {
        try {
            LOGGER.info("TagManager: Inserting new tag via REST service.");
            webClient.create(tag);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "TagManager: Exception inserting new tag, {0}", ex.getMessage());
            throw new LogicException("Error inserting new tag:\n" + ex.getMessage());
        }
    }
}
