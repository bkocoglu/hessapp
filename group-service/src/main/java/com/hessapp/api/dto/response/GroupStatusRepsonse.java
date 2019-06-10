package com.hessapp.api.dto.response;

import com.hessapp.api.dto.GroupStatusDeptItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupStatusRepsonse {
    private double totalDebt;           //verecek
    private double totalCredit;        //alacak
    private List<GroupStatusDeptItem> listDebt;
}
