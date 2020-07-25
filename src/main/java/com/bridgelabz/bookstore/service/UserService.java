package com.bridgelabz.bookstore.service;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.bookstore.exception.UserException;
import com.bridgelabz.bookstore.model.dto.LoginDTO;
import com.bridgelabz.bookstore.model.dto.RegistrationDTO;
import com.bridgelabz.bookstore.model.dto.ResetPasswordDto;
import com.bridgelabz.bookstore.model.dto.RoleDTO;
import com.bridgelabz.bookstore.model.dto.UpdateDTO;

public interface UserService {
	public boolean login(LoginDTO logindto) throws UserException;

	public boolean verify(String token) throws UserException;

	public boolean forgetPassword(String email) throws UserException;

	public boolean resetPassword(ResetPasswordDto resetPassword, String token) throws UserException;

	public boolean registerUser(RegistrationDTO user) throws IOException, UserException;

	public boolean logOut(String token) throws UserException;

	public boolean isSessionActive(String token);

	public boolean updateUser(UpdateDTO updateDTO, String token) throws UserException;

	public boolean deleteFileFromS3Bucket(String fileUrl, String token, String isProfile);

	public String uploadFile(MultipartFile multipartFile, String token, String isProfile);

	public String uploadFileTos3bucket(String fileName, File file, String isProfile);

}
