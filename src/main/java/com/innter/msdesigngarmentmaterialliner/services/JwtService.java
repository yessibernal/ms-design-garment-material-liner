package com.innter.msdesigngarmentmaterialliner.services;

import com.innter.msdesigngarmentmaterialliner.dtos.RolDto;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.List;

public interface JwtService {

    String extractUserName(String token) throws NoSuchAlgorithmException, InvalidKeySpecException;

    boolean isTokenValid(String token) throws NoSuchAlgorithmException, InvalidKeySpecException;

    List<RolDto> extractScopes(String token) throws NoSuchAlgorithmException, InvalidKeySpecException;

    LocalDateTime extractDate(String token) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
