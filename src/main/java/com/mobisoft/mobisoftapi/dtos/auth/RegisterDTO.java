package com.mobisoft.mobisoftapi.dtos.auth;

import com.mobisoft.mobisoftapi.enums.user.UserRole;

public record RegisterDTO(String login, String password, String name, UserRole role) {

}
