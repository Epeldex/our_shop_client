package logic.business;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.GenericType;

import exceptions.LogicException;
import exceptions.SignInException;
import exceptions.SignUpException;
import logic.interfaces.UserManager;
import rest.UserRESTClient;
import transfer_objects.User;

public class UserManagerImplementation implements UserManager {

    //REST users web client
    private UserRESTClient webClient;
    private static final Logger LOGGER=Logger.getLogger("UserManagerImplementation");

    /**
     * Create a UserManagerImplementation object. It constructs a web client for 
     * accessing a RESTful service that provides business logic in an application
     * server.
     */
    public UserManagerImplementation(){
        webClient=new UserRESTClient();
    }

    @Override
    public User findUserById(Integer id) throws LogicException {
        try{
            LOGGER.log(Level.INFO,"");
            //Send user data to web client for creation. 
            return webClient.findUserById(new GenericType<User>() {}, id.toString());
        }catch(Exception e){
            LOGGER.log(Level.SEVERE,
            "UserManager: Exception finding user. id["+id+"].\n"+
            e.getMessage());
            throw new LogicException(e.getMessage());
        }
    }

    @Override
    public User findUserByUsername(String username) throws LogicException {
        try{
            LOGGER.log(Level.INFO,"");
            //Send user data to web client for creation. 
            return webClient.findUserByUsername(new GenericType<User>() {}, username);
        }catch(Exception e){
            LOGGER.log(Level.SEVERE,
            "UserManager: Exception finding user. username["+username+"].\n"+
            e.getMessage());
            throw new LogicException(e.getMessage());
        }
    }

    @Override
    public List<User> findUserByActive(Boolean active) throws LogicException {
        String isActive = active ? "ACTIVE" : "";
        try{
            LOGGER.log(Level.INFO,"");
            
            //Send user data to web client for creation. 
            return webClient.findUserByActive(new GenericType<List<User>>() {}, isActive);
        }catch(Exception e){
            LOGGER.log(Level.SEVERE,
            "UserManager: Exception finding all["+isActive+"] users.\n"+
            e.getMessage());
            throw new LogicException(e.getMessage());
        }
    }

    @Override
    public List<User> findAllUsers() throws LogicException {
        try{
            LOGGER.log(Level.INFO,"");
            //Send user data to web client for creation. 
            return webClient.findAllUsers(new GenericType<List<User>>() {});
        }catch(Exception e){
            LOGGER.log(Level.SEVERE,
            "UserManager: Exception retriving all users.\n"+
            e.getMessage());
            throw new LogicException(e.getMessage());
        }
    }

    @Override
    public void updatePassword(Integer id, String password) throws LogicException {
        updateUser(findUserById(id));
    }

    @Override
    public User signIn(String username, String password) throws SignInException {
        try{
            LOGGER.log(Level.INFO,"");
            //Send user data to web client for creation. 
            return webClient.signIn(new User(username, password), new GenericType<User>(){});
        }catch(Exception e){
            LOGGER.log(Level.SEVERE,
            "UserManager: Exception signing in user. username["+username+"].\n"+
            e.getMessage());
            throw new SignInException();
        }
    }

    @Override
    public void createUser(User user) throws SignUpException {
        try{
            LOGGER.log(Level.INFO,"");
            //Send user data to web client for creation. 
            webClient.createUser(user);
        }catch(Exception e){
            LOGGER.log(Level.SEVERE,
            "UserManager: Exception signing up user. id["+user.getUsername()+"].\n"+
            e.getMessage());
            throw new SignUpException();
        }
    }

    @Override
    public void updateUser(User user) throws LogicException {
        try{
            LOGGER.log(Level.INFO,"");
            webClient.updateUser(user);
        }catch(Exception e){
            LOGGER.log(Level.SEVERE,
            "UserManager: Exception updating user. id["+user.getId()+"].\n"+
            e.getMessage());
            throw new LogicException(e.getMessage());
        }
    }

    @Override
    public void removeUser(Integer id) throws LogicException {
        try{
            LOGGER.log(Level.INFO,"Removing user. id["+id+"]");
            webClient.deleteUser(id.toString());
        }catch(Exception e){
            LOGGER.log(Level.SEVERE,
            "UserManager: Exception deleting user. id["+id+"].\n"+
            e.getMessage());
            throw new LogicException(e.getMessage());
        }
    }

}
