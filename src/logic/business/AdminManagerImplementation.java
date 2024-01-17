package logic.business;

import java.time.LocalDate;

import exceptions.LogicException;
import exceptions.SignInException;
import exceptions.SignUpException;
import logic.interfaces.AdminManager;
import transfer_objects.Admin;

public class AdminManagerImplementation implements AdminManager {

    @Override
    public void updateLastAccess(Integer id, LocalDate date) throws LogicException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateLastAccess'");
    }

    @Override
    public Admin signIn(String username, String password) throws SignInException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'signIn'");
    }

    @Override
    public void createAdmin(Admin admin) throws SignUpException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createAdmin'");
    }

    @Override
    public void updateAdmin(Admin admin) throws LogicException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAdmin'");
    }

    @Override
    public void removeAdmin(Integer id) throws LogicException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAdmin'");
    }

}
