package ru.morou.api;

import ru.morou.api.Exceptions.EmailExistsException;
import ru.morou.api.Exceptions.InvalidCredentialsException;
import ru.morou.api.Exceptions.InvalidTokenException;
import ru.morou.api.Exceptions.UserExistsException;
import ru.morou.api.Info.FileInfo;
import ru.morou.api.Info.UserInfo;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.util.List;


// использую как оглавление к списку исключений (тут еще не все)
// FIXME: 2019-02-26 Нужно все еще раз обдумать и связать с индикатарами

public interface MainService {

    void addNewUser(String username, String password, String firstName, String lastName, String email)
        throws UserExistsException, EmailExistsException;

    String getToken(String username, String password) throws InvalidCredentialsException;

    UserInfo getUserInfo(String token) throws InvalidTokenException;

    List<FileInfo> getFiles(String token) throws InvalidTokenException;

    FileInfo getFile(String token, String id) throws InvalidTokenException;

    void deleteFile(String token, String id) throws InvalidTokenException;

    byte[] downloadFilePart(String token, String id, int offset) throws InvalidTokenException;

    FileInfo uploadFile(String token, String filename, Cipher cipher, InputStream in) throws InvalidTokenException;
}