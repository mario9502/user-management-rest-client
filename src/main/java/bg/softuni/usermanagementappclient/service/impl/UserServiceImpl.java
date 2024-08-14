package bg.softuni.usermanagementappclient.service.impl;

import bg.softuni.usermanagementappclient.model.dto.UserInfoDto;
import bg.softuni.usermanagementappclient.model.dto.UserNoIdInfoDto;
import bg.softuni.usermanagementappclient.service.UserService;
import bg.softuni.usermanagementappclient.util.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    private final RestClient restClient;
    private final RestTemplate restTemplate;

    @Autowired
    public UserServiceImpl(RestClient restClient, RestTemplateBuilder restTemplateBuilder) {
        this.restClient = restClient;
        this.restTemplate = restTemplateBuilder
                        .errorHandler(new RestTemplateResponseErrorHandler())
                        .build();
    }

    @Override
    public UserInfoDto add(UserNoIdInfoDto user) {

        ResponseEntity<UserInfoDto> entity = restClient
                .post()
                .uri("users/add")
                .body(user)
                .retrieve()
                .toEntity(UserInfoDto.class);

        return entity.getBody();
    }

    @Override
    public List<UserInfoDto> getAll() {

        return restClient
                .get()
                .uri("/users/all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @Override
    public UserInfoDto getById(UUID id) {

        return restClient
                .get()
                .uri("users/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(UserInfoDto.class);
    }

    @Override
    public UserNoIdInfoDto getByEmail(String email) {

//        URI url = ServletUriComponentsBuilder
//                .fromUriString("http://localhost:8081/users/email={email}")
//                .buildAndExpand(email)
//                .toUri();
//
//        ResponseEntity<UserShortInfoDto> forEntity = restTemplate.getForEntity(url, UserShortInfoDto.class);

        return restClient
                .get()
                .uri("users/email={email}", email)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(UserNoIdInfoDto.class);
    }

    @Override
    public List<UserNoIdInfoDto> getAllSorted() {

        return restClient
                .get()
                .uri("/users/allSorted")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @Override
    public List<UserNoIdInfoDto> findAllWithYearOfBirthBefore(int year) {

        RestClient.ResponseSpec retrieve = restClient
                .get()
                .uri("/users/all/year={year}", year)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();

        return retrieve.body(new ParameterizedTypeReference<>() {});
    }

    @Override
    public UserNoIdInfoDto editByEmail(String email, UserNoIdInfoDto editedInfo) {

        ResponseEntity<UserNoIdInfoDto> response = restClient
                .put()
                .uri("users/email={email}", email)
                .body(editedInfo)
                .retrieve()
                .toEntity(UserNoIdInfoDto.class);

        return response.getBody();
    }

    @Override
    public void delete(String email) {

        restClient
                .delete()
                .uri("users/{email}", email)
                .retrieve();
    }

}
