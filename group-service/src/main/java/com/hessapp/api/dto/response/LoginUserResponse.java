package com.hessapp.api.dto.response;

import com.hessapp.api.dto.GroupPattern;
import com.hessapp.api.model.Group;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginUserResponse {
    private List<GroupPattern> groups;

}
