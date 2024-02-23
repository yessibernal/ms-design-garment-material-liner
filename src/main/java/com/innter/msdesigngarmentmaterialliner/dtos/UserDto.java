package com.innter.msdesigngarmentmaterialliner.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String userName;
    private List<RolDto> roles;
}
