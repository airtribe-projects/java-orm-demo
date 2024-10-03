package com.airtribe.orm.demo.controllers;

import com.airtribe.orm.demo.Exceptions.InvalidInputException;
import com.airtribe.orm.demo.Exceptions.ResourceNotFoundException;
import com.airtribe.orm.demo.constants.Constants;
import com.airtribe.orm.demo.enums.ResponseType;
import com.airtribe.orm.demo.models.requests.users.UserCreationRequestDto;
import com.airtribe.orm.demo.models.requests.users.UserUpdateRequestDto;
import com.airtribe.orm.demo.models.responses.users.UserDeleteResponseDto;
import com.airtribe.orm.demo.models.responses.users.UserDetailsResponseDto;
import com.airtribe.orm.demo.models.responses.base.GenericResponse;
import com.airtribe.orm.demo.services.users.UserService;
import com.airtribe.orm.demo.utils.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/user")
public class UserController {

    private final UserService userService;
    private final ValidationUtils validationUtils;

    public UserController(UserService userService, ValidationUtils validationUtils) {
        this.userService = userService;
        this.validationUtils = validationUtils;
    }

    @GetMapping(path = "/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Fetch User Details", description = "Fetch the details of a user")
    public GenericResponse<UserDetailsResponseDto> fetchUserDetails(@RequestParam("user-id") String userId) {
        UserDetailsResponseDto userDetailsResponseDto = null;
        try {
            validationUtils.validateId(userId);
            userDetailsResponseDto = userService.getUser(userId);
        } catch (InvalidInputException e) {
            return GenericResponse.<UserDetailsResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        } catch (ResourceNotFoundException e) {
            return GenericResponse.<UserDetailsResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<UserDetailsResponseDto>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(userDetailsResponseDto)
                .build();
    }

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create User", description = "Create a new user")
    public GenericResponse<UserDetailsResponseDto> createUser(@RequestBody UserCreationRequestDto userCreationRequestDto) {
        UserDetailsResponseDto userDetailsResponseDto = null;
        try {
            validationUtils.validateUserCreationRequest(userCreationRequestDto);
            userDetailsResponseDto = userService.createUser(userCreationRequestDto);

        } catch (InvalidInputException e) {
            return GenericResponse.<UserDetailsResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<UserDetailsResponseDto>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(userDetailsResponseDto)
                .build();
    }

    @Operation(summary = "Update User", description = "Update the details of a user")
    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<UserDetailsResponseDto> updateUser(@RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        UserDetailsResponseDto userDetailsResponseDto = null;
        try {
            validationUtils.validateUserUpdateRequest(userUpdateRequestDto);
            userDetailsResponseDto = userService.updateUser(userUpdateRequestDto);

        } catch (InvalidInputException e) {
            return GenericResponse.<UserDetailsResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        } catch (ResourceNotFoundException e) {
            return GenericResponse.<UserDetailsResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<UserDetailsResponseDto>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(userDetailsResponseDto)
                .build();
    }

    @Operation(summary = "Delete User", description = "delete the user")
    @PutMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<UserDeleteResponseDto> deleteUser(@RequestParam("user-id") String userId) {
        UserDeleteResponseDto userDeleteResponseDto = null;
        try {
            validationUtils.validateId(userId);
            userDeleteResponseDto = userService.deleteUser(userId);

        } catch (InvalidInputException e) {
            return GenericResponse.<UserDeleteResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        } catch (ResourceNotFoundException e) {
            return GenericResponse.<UserDeleteResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<UserDeleteResponseDto>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(userDeleteResponseDto)
                .build();
    }

    @Operation(summary = "Force Delete User", description = " force delete the user")
    @PutMapping(path = "/delete/force", produces = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse<UserDeleteResponseDto> forceDeleteUser(@RequestParam("user-id") String userId) {
        UserDeleteResponseDto userDeleteResponseDto = null;
        try {
            validationUtils.validateId(userId);
            userDeleteResponseDto = userService.forceDeleteUser(userId);

        } catch (InvalidInputException e) {
            return GenericResponse.<UserDeleteResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        } catch (ResourceNotFoundException e) {
            return GenericResponse.<UserDeleteResponseDto>builder()
                    .responseType(ResponseType.FAILED)
                    .responseCode(e.getErrorCode())
                    .responseMessage(e.getErrorMessage())
                    .build();
        }

        return GenericResponse.<UserDeleteResponseDto>builder()
                .responseType(ResponseType.SUCCESS)
                .responseCode(Constants.DEFAULT_SUCCESS_RESPONSE_CODE)
                .responseMessage(Constants.DEFAULT_SUCCESS_RESPONSE_MESSAGE)
                .data(userDeleteResponseDto)
                .build();
    }
}
