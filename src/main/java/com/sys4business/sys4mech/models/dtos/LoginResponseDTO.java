package com.sys4business.sys4mech.models.dtos;

import java.util.List;

public record LoginResponseDTO(String token, List<String> permissions) {

}
