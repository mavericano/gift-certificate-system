package com.epam.esm.core.dto;

import com.epam.esm.core.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends RepresentationModel<UserDto> {

    private long id;

    private String username;

//    private List<OrderDto> orders;
}
