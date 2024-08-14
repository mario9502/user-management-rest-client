package bg.softuni.usermanagementappclient.service;

import bg.softuni.usermanagementappclient.model.dto.UserInfoDto;
import bg.softuni.usermanagementappclient.model.dto.UserNoIdInfoDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserInfoDto add(UserNoIdInfoDto user);

    List<UserInfoDto> getAll();

    UserInfoDto getById(UUID id);

    UserNoIdInfoDto getByEmail(String email);

    List<UserNoIdInfoDto> getAllSorted();

    List<UserNoIdInfoDto> findAllWithYearOfBirthBefore(int year);

    UserNoIdInfoDto editByEmail(String email, UserNoIdInfoDto editedInfo);

    void delete(String email);
}
